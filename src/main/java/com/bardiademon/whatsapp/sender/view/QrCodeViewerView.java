package com.bardiademon.whatsapp.sender.view;

import com.bardiademon.whatsapp.sender.io.Path;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class QrCodeViewerView extends View
{
    protected QrCodeViewer qrCodeViewer;

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents()
    {

        javax.swing.JLabel lblPleaseScan = new javax.swing.JLabel();
        qrCodeViewer = new QrCodeViewer();

        lblPleaseScan.setFont(new Font("Tahoma" , Font.PLAIN , 24)); // NOI18N
        lblPleaseScan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPleaseScan.setText("Please scan QR Code");

        javax.swing.GroupLayout qrCodeViewerLayout = new javax.swing.GroupLayout(qrCodeViewer);
        qrCodeViewer.setLayout(qrCodeViewerLayout);
        qrCodeViewerLayout.setHorizontalGroup(qrCodeViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0 , 0 , Short.MAX_VALUE));
        qrCodeViewerLayout.setVerticalGroup(qrCodeViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0 , 389 , Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(qrCodeViewer , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addComponent(lblPleaseScan , javax.swing.GroupLayout.DEFAULT_SIZE , 399 , Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup().addGap(6 , 6 , 6).addComponent(lblPleaseScan , javax.swing.GroupLayout.DEFAULT_SIZE , 33 , Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(qrCodeViewer , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

        pack();
    }// </editor-fold>

    public QrCodeViewerView()
    {
    }

    protected void setView()
    {
        initComponents();
        super.setView("Qr code viewer");
    }

    protected static class QrCodeViewer extends JPanel
    {
        private BufferedImage image = null;

        private Image defaultImage;

        public QrCodeViewer()
        {
            final File defaultImageFile = new File(Path.get(Path.IMAGES , Path.IMG_LOADING_NAME));
            if (defaultImageFile.exists())
            {
                try
                {
                    defaultImage = new ImageIcon(defaultImageFile.toURI().toURL()).getImage();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }

        public void setImage(final InputStream inputStream) throws IOException
        {
            image = ImageIO.read(inputStream);
            repaint();
        }

        @Override
        public void paint(Graphics g)
        {
            super.paint(g);

            if (image != null) g.drawImage(image , 0 , 0 , getWidth() , getHeight() , this);
            else g.drawImage(defaultImage , 0 , 0 , getWidth() , getHeight() , this);
        }
    }
}
