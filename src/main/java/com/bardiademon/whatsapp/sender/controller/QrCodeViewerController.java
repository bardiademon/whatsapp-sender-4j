package com.bardiademon.whatsapp.sender.controller;

import com.bardiademon.whatsapp.sender.view.QrCodeViewerView;
import com.bardiademon.whatsapp.sender.view.View;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.InputStream;

public final class QrCodeViewerController extends QrCodeViewerView
{

    public QrCodeViewerController(final View.OnClose onClose)
    {
        setOnClose(onClose);
        SwingUtilities.invokeLater(QrCodeViewerController.super::setView);
    }

    public void setImage(InputStream inputStream)
    {
        new Thread(() -> SwingUtilities.invokeLater(() ->
        {
            if (isReady())
            {
                try
                {
                    qrCodeViewer.setImage(inputStream);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        })).start();
    }
}
