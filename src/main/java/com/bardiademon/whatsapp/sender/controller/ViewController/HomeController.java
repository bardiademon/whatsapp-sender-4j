package com.bardiademon.whatsapp.sender.controller.ViewController;

import com.bardiademon.whatsapp.sender.controller.QrCodeViewerController;
import com.bardiademon.whatsapp.sender.controller.connector.Connector;
import com.bardiademon.whatsapp.sender.controller.connector.ConnectorStatus;
import com.bardiademon.whatsapp.sender.model.Message;
import com.bardiademon.whatsapp.sender.model.Message.Media;
import com.bardiademon.whatsapp.sender.view.Home;
import it.auties.whatsapp.model.contact.ContactJid;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeController extends Home implements ConnectorStatus
{
    private final Connector connector = new Connector();

    private final Message message = new Message();

    private Selection selection;

    private QrCodeViewerController qrCodeViewerController;

    public HomeController()
    {
        SwingUtilities.invokeLater(() -> setView("Whatsapp sender - bardiademon"));
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

        SwingUtilities.invokeLater(() -> btnConnect.setText(btnText));
    }

    @Override
    protected void onClickBtnClearConnection()
    {

    }

    @Override
    protected void onClickBtnClearLog()
    {

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

    }

    @Override
    protected void onClickBtnSaveLog()
    {

    }

    @Override
    protected void onClickBtnSendMessage()
    {
        final String phone = checkPhone();
        if (phone != null)
        {
            if (notEmpty(message.getText()) || Media.checkEmpty(message.getImage()) || Media.checkEmpty(message.getVideo()) || Media.checkEmpty(message.getDocument()))
            {
                message.setPhone(phone);
                send();
            }
            else setStatus("Text or Media is empty");
        }
    }

    private boolean notEmpty(final String val)
    {
        return val != null && !val.isEmpty();
    }

    private void send()
    {
        new Thread(() -> connector.whatsapp.hasWhatsapp(connector.getContactJid(message.getPhone())).thenAccept(hasWhatsappResponses ->
        {
            if (hasWhatsappResponses.size() > 0 && hasWhatsappResponses.get(0).hasWhatsapp())
            {
            }
            else setStatus(String.format("This phone{%s} does not have whatsapp" , message.getPhone()));
        })).start();
    }

    private String checkPhone()
    {
        String phone = txtPhone.getText();
        if (phone != null && !phone.isEmpty())
        {
            if (phone.matches("(^(989)|^(\\\\+989)|^(09)|^(9))(\\d+)") && phone.length() >= 10 && phone.length() <= 13)
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

        SwingUtilities.invokeLater(() -> btnConnect.setText("Disconnect"));

        System.out.println("Connected");
    }

    @Override
    public void onQrCode(InputStream inputStream)
    {
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

    public enum Selection
    {
        TEXT, IMAGE, VIDEO, DOCUMENT
    }
}
