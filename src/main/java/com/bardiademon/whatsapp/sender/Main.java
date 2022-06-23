package com.bardiademon.whatsapp.sender;

import com.bardiademon.whatsapp.sender.controller.QrCodeViewerController;
import com.bardiademon.whatsapp.sender.controller.connector.Connector;
import com.bardiademon.whatsapp.sender.controller.connector.ReadyToUse;
import com.bardiademon.whatsapp.sender.view.QrCodeViewerView;
import com.bardiademon.whatsapp.sender.view.View;

import javax.swing.SwingUtilities;
import java.io.InputStream;

public class Main
{
    private static QrCodeViewerController qrCodeViewerController;

    public static void main(String[] args)
    {
        final Connector connector = new Connector();

        SwingUtilities.invokeLater(() -> qrCodeViewerController = new QrCodeViewerController(() ->
        {

        }));


        connector.connectToWhatsapp(new ReadyToUse()
        {
            @Override
            public void onReady()
            {
                System.out.println("Ready");
            }

            @Override
            public void onQrCode(InputStream inputStream)
            {
                qrCodeViewerController.setImage(inputStream);
            }
        });
    }
}