package com.bardiademon.whatsapp.sender.controller.connector;

import it.auties.whatsapp.api.QrHandler;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.api.WhatsappListener;
import it.auties.whatsapp.model.chat.Chat;
import it.auties.whatsapp.model.contact.ContactJid;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public final class Connector implements WhatsappListener
{

    private static final int CONNECTION_ID = 1212;

    public Whatsapp whatsapp;
    private ConnectorStatus connectorStatus;

    private boolean onLogged, onContact, onChat;

    private boolean disconnect;

    public Connector()
    {
    }

    public void connectToWhatsapp(final ConnectorStatus connectorStatus)
    {
        this.connectorStatus = connectorStatus;

        new Thread(() ->
        {
            try
            {
                whatsapp = Whatsapp.newConnection(CONNECTION_ID);
                whatsapp.registerListener(Connector.this);
                whatsapp.connect();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

            new Thread(() ->
            {
                final Timer timer = new Timer();
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        if (disconnect) timer.cancel();
                    }
                } , 1 , 500);
            }).start();
        }).start();
    }

    public void disconnect()
    {
        disconnect = true;
        new Thread(whatsapp::disconnect).start();
    }

    @Override
    public void onLoggedIn()
    {
        onLogged = true;
        checkReadyToUse();
    }

    @Override
    public void onDisconnected(boolean reconnect)
    {
        onChat = false;
        onContact = false;
        onLogged = false;
        connectorStatus.onDisconnect();
    }

    public void clear()
    {
        onChat = false;
        onContact = false;
        onLogged = false;
        whatsapp = null;
        disconnect = false;
    }

    @Override
    public void onContacts()
    {
        onContact = true;
        checkReadyToUse();
    }

    @Override
    public void onChats()
    {
        onChat = true;
        checkReadyToUse();
    }

    @Override
    public QrHandler onQRCode()
    {
        return QrHandle.toByte(connectorStatus::onQrCode);
    }

    private void checkReadyToUse()
    {
        if (onLogged && onChat && onContact) new Thread(connectorStatus::onReady).start();
    }

    public boolean isConnected()
    {
        return (whatsapp != null && onLogged && onContact && onChat && !disconnect);
    }

    public ContactJid getContactJid(final String phone)
    {
        final Optional<Chat> chatByJid = whatsapp.store().findChatByJid(ContactJid.of(String.format("%s@s.whatsapp.net" , phone)));
        return chatByJid.map(Chat::jid).orElse(null);
    }

    public void hasWhatsapp(final String phone , final HasWhatsapp hasWhatsapp)
    {
        if (isConnected())
        {
            final ContactJid contactJid = getContactJid(phone);
            if (contactJid != null)
            {
                new Thread(() -> whatsapp.hasWhatsapp(contactJid).thenAccept(hasWhatsappResponses -> hasWhatsapp.has(hasWhatsappResponses != null && hasWhatsappResponses.size() > 0 && hasWhatsappResponses.get(0).hasWhatsapp()))).start();
            }
            else hasWhatsapp.has(false);
        }
    }

    public interface HasWhatsapp
    {
        void has(final boolean has);
    }
}
