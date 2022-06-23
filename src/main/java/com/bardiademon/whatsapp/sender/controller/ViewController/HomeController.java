package com.bardiademon.whatsapp.sender.controller.ViewController;

import com.bardiademon.whatsapp.sender.controller.QrCodeViewerController;
import com.bardiademon.whatsapp.sender.controller.connector.Connector;
import com.bardiademon.whatsapp.sender.controller.connector.ConnectorStatus;
import com.bardiademon.whatsapp.sender.model.Message;
import com.bardiademon.whatsapp.sender.model.Message.Media;
import com.bardiademon.whatsapp.sender.model.PhoneNumberImport;
import com.bardiademon.whatsapp.sender.view.Home;
import it.auties.whatsapp.model.message.standard.DocumentMessage;
import it.auties.whatsapp.model.message.standard.ImageMessage;
import it.auties.whatsapp.model.message.standard.TextMessage;
import it.auties.whatsapp.model.message.standard.VideoMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeController extends Home implements ConnectorStatus
{
    private final Connector connector = new Connector();

    private final Message message = new Message();

    private boolean sendingMessage;

    private Selection selection;

    private QrCodeViewerController qrCodeViewerController;

    private List<PhoneNumberImport> phoneNumberImports;
    private Timer timerSendListMessage;
    private boolean timerSendListMessageIsRunning;
    private boolean cancelImportNumber;
    private boolean sendingListMessage = false;

    private static final int MAX_MIN_WAIT_SEND_LIST_MESSAGE = 5;

    public HomeController()
    {
        SwingUtilities.invokeLater(() ->
        {
            setView("Whatsapp sender - bardiademon");
            onClickBtnText();
        });
    }

    @Override
    protected void onClickBtnConnect()
    {
        final String btnText;

        if (connector.isConnected())
        {
            connector.disconnect();
            btnText = "Disconnecting...";
        }
        else
        {
            connector.connectToWhatsapp(this);
            btnText = "Connecting...";
        }

        setStatus(btnText);
        SwingUtilities.invokeLater(() -> btnConnect.setText(btnText));
    }

    @Override
    protected void onClickBtnClearConnection()
    {

    }

    @Override
    protected void onClickBtnClearLog()
    {
        SwingUtilities.invokeLater(lstLogModel::clear);
    }

    @Override
    protected void onClickBtnDocumentChooser()
    {
        selection = Selection.DOCUMENT;
        SwingUtilities.invokeLater(() ->
        {
            setSelection();
            Media media = message.getDocument();
            if (media != null)
            {
                txtMessage.setText(media.getTest());
                lblValPath.setText(media.getPath());
            }
            else setEmptyMedia();
        });
    }

    @Override
    protected void onClickBtnFileChooser()
    {
        if (selection != null)
        {
            if (!selection.equals(Selection.TEXT))
            {
                final String mediaPath = switch (selection)
                        {
                            case IMAGE -> (message.getImage() != null ? message.getImage().getPath() : null);
                            case VIDEO -> (message.getVideo() != null ? message.getVideo().getPath() : null);
                            case DOCUMENT -> (message.getDocument() != null ? message.getDocument().getPath() : null);
                            default -> null;
                        };

                SwingUtilities.invokeLater(() ->
                {
                    final JFileChooser chooser = new JFileChooser(mediaPath == null ? null : new File(mediaPath));

                    final FileNameExtensionFilter filter = switch (selection)
                            {
                                case IMAGE -> new FileNameExtensionFilter("Image file" , "jpg" , "jpeg" , "png");
                                case VIDEO -> new FileNameExtensionFilter("Video file" , "mp4");
                                default -> null;
                            };

                    if (filter != null) chooser.setFileFilter(filter);


                    final int dialogResult = chooser.showOpenDialog(null);

                    if (dialogResult == JFileChooser.OPEN_DIALOG)
                    {
                        switch (selection)
                        {
                            case IMAGE ->
                            {
                                Media media = message.getImage();
                                if (media == null) media = new Media();
                                media.setPath(chooser.getSelectedFile().getAbsolutePath());
                                message.setImage(media);
                                onClickBtnImageChooser();
                            }
                            case VIDEO ->
                            {
                                Media media = message.getVideo();
                                if (media == null) media = new Media();
                                media.setPath(chooser.getSelectedFile().getAbsolutePath());
                                message.setVideo(media);
                                onClickBtnVideoChooser();
                            }
                            case DOCUMENT ->
                            {
                                Media media = message.getDocument();
                                if (media == null) media = new Media();
                                media.setPath(chooser.getSelectedFile().getAbsolutePath());
                                message.setDocument(media);
                                onClickBtnDocumentChooser();
                            }
                        }
                    }

                });
            }
        }
        else setStatus(String.format("Please select %s" , Arrays.toString(Selection.values())));
    }

    @Override
    protected void onClickBtnImageChooser()
    {
        selection = Selection.IMAGE;
        SwingUtilities.invokeLater(() ->
        {
            setSelection();
            Media media = message.getImage();
            if (media != null)
            {
                txtMessage.setText(media.getTest());
                lblValPath.setText(media.getPath());
            }
            else setEmptyMedia();
        });
    }

    private void setEmptyMedia()
    {
        SwingUtilities.invokeLater(() ->
        {
            txtMessage.setText("");
            lblValPath.setText("");
        });
    }

    @Override
    protected void onChangeTxtMessage()
    {
        if (selection != null)
        {
            switch (selection)
            {
                case TEXT -> message.setText(txtMessage.getText());
                case IMAGE ->
                {
                    Media media = message.getImage();
                    if (media == null) media = new Media();
                    media.setTest(txtMessage.getText());
                    message.setImage(media);
                }
                case VIDEO ->
                {
                    Media media = message.getDocument();
                    if (media == null) media = new Media();
                    media.setTest(txtMessage.getText());
                    message.setVideo(media);
                }
                case DOCUMENT ->
                {
                    Media media = message.getVideo();
                    if (media == null) media = new Media();
                    media.setTest(txtMessage.getText());
                    message.setDocument(media);
                }
            }
        }
    }

    @Override
    protected void onClickBtnImportPhone()
    {
        if (phoneNumberImports == null || phoneNumberImports.size() == 0)
        {
            cancelImportNumber = false;
            timerSendListMessageIsRunning = false;

            setStatus("Import number");
            final JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setFileFilter(new FileNameExtensionFilter("JSON file" , "json"));
            final int dialogResult = chooser.showOpenDialog(null);
            if (dialogResult == JFileChooser.OPEN_DIALOG)
            {
                final File selectedFile = chooser.getSelectedFile();
                try
                {
                    final String jsonStr = Files.readString(selectedFile.toPath());

                    try
                    {
                        final JSONArray jsonNumber = new JSONArray(jsonStr);

                        phoneNumberImports = new ArrayList<>();
                        for (final Object item : jsonNumber)
                        {
                            if (item instanceof final JSONObject jsonItem)
                            {
                                final PhoneNumberImport phoneNumberImport = new PhoneNumberImport();
                                phoneNumberImport.setName(jsonItem.getString("name"));
                                phoneNumberImport.setPhone(jsonItem.getString("phone"));
                                setStatus(phoneNumberImport.toString());
                                phoneNumberImports.add(phoneNumberImport);
                            }
                        }
                        if (phoneNumberImports.size() > 0)
                        {
                            setStatus("Successfully import number!");
                            setLblNumberOfNumbers(null);
                            SwingUtilities.invokeLater(() -> btnImportPhone.setText("Clear import"));
                        }
                        else throw new IOException("Not found info");
                    }
                    catch (JSONException e)
                    {
                        setStatus("Invalid json file: " + e.getMessage());
                        phoneNumberImports.clear();
                        phoneNumberImports = null;
                    }
                }
                catch (IOException e)
                {
                    setStatus("Error load json number: " + e.getMessage());
                }

            }
        }
        else stopImportNumber();
    }

    private void stopImportNumber()
    {
        if (phoneNumberImports != null)
        {
            phoneNumberImports.clear();
            phoneNumberImports = null;
        }
        setLblNumberOfNumbers(null);

        cancelImportNumber = true;
        if (timerSendListMessage != null)
        {
            timerSendListMessage.cancel();
            timerSendListMessage = null;
        }
        timerSendListMessageIsRunning = false;

        SwingUtilities.invokeLater(() -> btnImportPhone.setText("Import phones"));
    }

    private void setLblNumberOfNumbers(final String time)
    {
        new Thread(() ->
        {
            int counterSend = 0;
            if (phoneNumberImports != null && phoneNumberImports.size() > 0)
            {
                for (final PhoneNumberImport phoneNumberImport : phoneNumberImports)
                    if (phoneNumberImport.isSend()) counterSend++;
            }

            final int finalCounterSend = counterSend;
            SwingUtilities.invokeLater(() ->
            {
                final StringBuilder value = new StringBuilder(String.format("%d / %d" , finalCounterSend , (phoneNumberImports != null ? phoneNumberImports.size() : 0)));
                if (notEmpty(time)) value.append(' ').append(time);
                lblNumberOfPhones.setText(value.toString());
            });
        }).start();
    }

    @Override
    protected void onClickBtnSaveLog()
    {

    }

    @Override
    protected void onClickBtnSendMessage()
    {
        if (phoneNumberImports == null || phoneNumberImports.size() == 0) send(null);
        else sendListMessage();
    }

    private void sendListMessage()
    {
        if (sendingListMessage)
        {
            setStatus("Sending message is running...");
            return;
        }
        new Thread(() ->
        {
            try
            {
                sendingListMessage = true;
                for (PhoneNumberImport phoneNumberImport : phoneNumberImports)
                {
                    if (cancelImportNumber) break;

                    txtPhone.setText(phoneNumberImport.getPhone());
                    timerSendListMessageIsRunning = true;
                    send(HomeController.this::setTimer);
                    while (HomeController.this.timerSendListMessageIsRunning)
                    {
                        try
                        {
                            Thread.sleep(100);
                        }
                        catch (InterruptedException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                    phoneNumberImport.setSend(true);
                }
            }
            catch (Exception ignored)
            {
            }

            sendingListMessage = false;
            setStatus("Completed!");
            stopImportNumber();
        }).start();
    }

    private void setTimer()
    {
        new Thread(() ->
        {
            if (timerSendListMessage != null) timerSendListMessage.cancel();
            timerSendListMessage = new Timer();

            final AtomicInteger sec = new AtomicInteger(0);
            final AtomicInteger min = new AtomicInteger(new Random().nextInt(MAX_MIN_WAIT_SEND_LIST_MESSAGE));
            timerSendListMessage.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    if (!timerSendListMessageIsRunning)
                    {
                        timerSendListMessage.cancel();
                        return;
                    }

                    if (sec.get() <= 0)
                    {
                        if (min.get() <= 0)
                        {
                            min.set(0);
                            sec.set(0);
                            timerSendListMessageIsRunning = false;
                            timerSendListMessage.cancel();
                        }
                        else
                        {
                            sec.set(59);
                            min.decrementAndGet();
                        }
                    }
                    else sec.decrementAndGet();

                    final String secStr = sec.get() < 10 ? "0" + sec.get() : String.valueOf(sec.get());
                    final String minStr = min.get() < 10 ? "0" + min.get() : String.valueOf(min.get());
                    setLblNumberOfNumbers(String.format("%s:%s" , minStr , secStr));
                }
            } , 1000 , 1000);
        }).start();
    }

    private boolean notEmpty(final String val)
    {
        return val != null && !val.isEmpty();
    }

    private void send(final CompletedMessageSend completedMessageSend)
    {
        new Thread(() ->
        {
            final String phone = checkPhone();
            if (phone != null)
            {
                message.setPhone(phone);
                if (notEmpty(message.getText()) || Media.checkEmpty(message.getImage()) || Media.checkEmpty(message.getVideo()) || Media.checkEmpty(message.getDocument()))
                {
                    connector.hasWhatsapp(message.getPhone() , has ->
                    {
                        if (has)
                        {
                            for (final String item : orderOfSubmission)
                            {
                                final Selection selection = Selection.valueOf(item);
                                switch (selection)
                                {
                                    case TEXT -> sendText();
                                    case IMAGE -> sendImage();
                                    case VIDEO -> setVideo();
                                    case DOCUMENT -> sendDocument();
                                }

                                while (sendingMessage)
                                {
                                    try
                                    {
                                        Thread.sleep(100);
                                    }
                                    catch (InterruptedException e)
                                    {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                        else setStatus(String.format("This phone{%s} does not have whatsapp" , message.getPhone()));

                        if (completedMessageSend != null) completedMessageSend.onCompleted();
                    });
                }
                else
                {
                    if (completedMessageSend != null) completedMessageSend.onCompleted();
                    setStatus("Text or Media is empty");
                }
            }
            else
            {
                if (completedMessageSend != null) completedMessageSend.onCompleted();
            }
        }).start();
    }

    private void sendText()
    {
        sendingMessage = true;
        new Thread(() ->
        {
            if (notEmpty(message.getText()))
            {
                try
                {
                    setStatus(String.format("Text message sending to {%s}" , message.getPhone()));

                    connector.whatsapp.sendMessage(connector.getContactJid(message.getPhone()) , TextMessage.newTextMessage().text(message.getText()).description("Powered by bardiademon.com").create()).thenAccept(messageInfo ->
                    {
                        setStatus(String.format("Text message sent to {%s}" , message.getPhone()));
                        sendingMessage = false;
                    });
                }
                catch (Exception e)
                {
                    setStatus(String.format("Error send text message to {%s}: %s" , message.getPhone() , e.getMessage()));
                    sendingMessage = false;
                }
            }
            else sendingMessage = false;
        }).start();
    }

    private void sendImage()
    {
        sendingMessage = true;
        new Thread(() ->
        {
            if (Media.checkEmpty(message.getImage()))
            {
                setStatus(String.format("Image message sending to {%s}" , message.getPhone()));

                try (final InputStream inputStream = new File(message.getImage().getPath()).toURI().toURL().openStream())
                {
                    connector.whatsapp.sendMessage(connector.getContactJid(message.getPhone()) , ImageMessage.newImageMessage().storeId(connector.whatsapp.store().id()).media(inputStream.readAllBytes()).caption(message.getImage().getTest()).create()).thenAccept(messageInfo ->
                    {
                        setStatus(String.format("Image message sent to {%s}" , message.getPhone()));
                        sendingMessage = false;
                    });
                }
                catch (Exception e)
                {
                    setStatus(String.format("Error send image message to {%s}: %s" , message.getPhone() , e.getMessage()));
                    sendingMessage = false;
                }
            }
            else sendingMessage = false;
        }).start();
    }

    private void setVideo()
    {
        sendingMessage = true;
        new Thread(() ->
        {
            if (Media.checkEmpty(message.getVideo()))
            {
                setStatus(String.format("Video message sending to {%s}" , message.getPhone()));

                try (final InputStream inputStream = new File(message.getImage().getPath()).toURI().toURL().openStream())
                {
                    connector.whatsapp.sendMessage(connector.getContactJid(message.getPhone()) , VideoMessage.newVideoMessage().storeId(connector.whatsapp.store().id()).media(inputStream.readAllBytes()).caption(message.getImage().getTest()).create()).thenAccept(messageInfo ->
                    {
                        setStatus(String.format("Video message sent to {%s}" , message.getPhone()));
                        sendingMessage = false;
                    });
                }
                catch (Exception e)
                {
                    setStatus(String.format("Error send video message to {%s}: %s" , message.getPhone() , e.getMessage()));
                    sendingMessage = false;
                }
            }
            else sendingMessage = false;
        }).start();
    }

    private void sendDocument()
    {
        sendingMessage = true;
        new Thread(() ->
        {
            if (Media.checkEmpty(message.getDocument()))
            {
                setStatus(String.format("Document message sending to {%s}" , message.getPhone()));

                try (final InputStream inputStream = new File(message.getImage().getPath()).toURI().toURL().openStream())
                {
                    connector.whatsapp.sendMessage(connector.getContactJid(message.getPhone()) , DocumentMessage.newDocumentMessage().storeId(connector.whatsapp.store().id()).media(inputStream.readAllBytes()).create()).thenAccept(messageInfo ->
                    {
                        setStatus(String.format("Document message sent to {%s}" , message.getPhone()));
                        sendingMessage = false;
                    });
                }
                catch (Exception e)
                {
                    setStatus(String.format("Error send document message to {%s}: %s" , message.getPhone() , e.getMessage()));
                    sendingMessage = false;
                }
            }
            else sendingMessage = false;
        }).start();
    }

    private String checkPhone()
    {
        String phone = txtPhone.getText();
        if (phone != null && !phone.isEmpty())
        {
            if (phone.matches("(^(989)|^(\\+989)|^(09)|^(9))(\\d+)") && phone.length() >= 10 && phone.length() <= 13)
            {
                int substring = -1;
                if (phone.startsWith("+98")) substring = 3;
                else if (phone.startsWith("0")) substring = 1;

                if (substring > 0)
                {
                    phone = phone.substring(substring);
                    phone = "98" + phone;
                }
                return phone;
            }
            else setStatus(String.format("invalid phone number {%s}" , phone));
        }
        else setStatus("Phone number is empty");

        return null;
    }

    @Override
    protected void onClickBtnVideoChooser()
    {
        selection = Selection.VIDEO;
        SwingUtilities.invokeLater(() ->
        {
            setSelection();
            Media video = message.getVideo();
            if (video != null)
            {
                txtMessage.setText(video.getTest());
                lblValPath.setText(video.getPath());
            }
            else setEmptyMedia();
        });
    }

    @Override
    protected void onClickBtnMoveBottomSelection()
    {
        final int selectedIndex = lstOrderOfSubmission.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < orderOfSubmission.length - 1)
        {
            SwingUtilities.invokeLater(() ->
            {
                final String tmpSelection = orderOfSubmission[selectedIndex];
                final String tmpSelectionNext = orderOfSubmission[selectedIndex + 1];

                orderOfSubmission[selectedIndex] = tmpSelectionNext;
                orderOfSubmission[selectedIndex + 1] = tmpSelection;

                updateOrder();

                lstOrderOfSubmission.setSelectedIndex(selectedIndex + 1);
            });
        }
    }

    @Override
    protected void onClickBtnMoveTopSelection()
    {
        final int selectedIndex = lstOrderOfSubmission.getSelectedIndex();
        if (selectedIndex >= 1)
        {
            SwingUtilities.invokeLater(() ->
            {
                final String tmpSelection = orderOfSubmission[selectedIndex];
                final String tmpSelectionPre = orderOfSubmission[selectedIndex - 1];

                orderOfSubmission[selectedIndex] = tmpSelectionPre;
                orderOfSubmission[selectedIndex - 1] = tmpSelection;

                updateOrder();
                lstOrderOfSubmission.setSelectedIndex(selectedIndex - 1);
            });
        }
    }

    @Override
    protected void onClickBtnText()
    {
        selection = Selection.TEXT;
        SwingUtilities.invokeLater(() ->
        {
            setSelection();
            txtMessage.setText(message.getText());
            lblValPath.setText("");
        });
    }

    private void setSelection()
    {
        SwingUtilities.invokeLater(() -> lblValSelected.setText(String.format("%s is selected" , selection.name().toLowerCase())));
    }

    @Override
    public void onReady()
    {
        if (qrCodeViewerController != null) qrCodeViewerController.close();

        setStatus("Connected!");

        SwingUtilities.invokeLater(() -> btnConnect.setText("Disconnect"));
    }

    @Override
    public void onQrCode(InputStream inputStream)
    {
        setStatus("Qr code");
        if (qrCodeViewerController == null) qrCodeViewerController = new QrCodeViewerController(null);
        qrCodeViewerController.setImage(inputStream);
    }

    @Override
    public void onDisconnect()
    {
        SwingUtilities.invokeLater(() -> btnConnect.setText("Connect"));
    }

    private void setStatus(final String status)
    {
        SwingUtilities.invokeLater(() ->
        {
            lblValStatus.setText(status);
            lstLogModel.addElement(status);
        });
    }

    private interface CompletedMessageSend
    {
        void onCompleted();
    }

    public enum Selection
    {
        TEXT, IMAGE, VIDEO, DOCUMENT
    }
}
