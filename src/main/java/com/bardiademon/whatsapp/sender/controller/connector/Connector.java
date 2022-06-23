package com.bardiademon.whatsapp.sender.controller.connector;

import it.auties.whatsapp.api.QrHandler;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.api.WhatsappListener;

import java.util.Timer;
import java.util.TimerTask;

public final class Connector implements WhatsappListener
{

    private static final int CONNECTION_ID = 1212;

    private Whatsapp whatsapp;
    private ReadyToUse readyToUse;

    private boolean onLogged, onContact, onChat;

    private boolean disconnect;

    public Connector()
    {
    }

    public void connectToWhatsapp(final ReadyToUse readyToUse)
    {
        this.readyToUse = readyToUse;

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
    }

    @Override
    public void onLoggedIn()
    {
        System.out.println("onLoggedIn");
        onLogged = true;
        checkReadyToUse();
    }

    @Override
    public void onContacts()
    {
        System.out.println("onContacts");
        onContact = true;
        checkReadyToUse();
    }

    @Override
    public void onChats()
    {
        System.out.println("onChats");
        onChat = true;
        checkReadyToUse();
    }

    @Override
    public QrHandler onQRCode()
    {
        return QrHandle.toByte(readyToUse::onQrCode);
    }

    private void checkReadyToUse()
    {
        if (onLogged && onChat && onContact) new Thread(readyToUse::onReady).start();
    }
}
