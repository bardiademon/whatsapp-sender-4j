package com.bardiademon.whatsapp.sender.controller.connector;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import it.auties.whatsapp.api.QrHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public interface QrHandle extends QrHandler
{

    static QrHandler toByte(final OnByteQr onByteQr)
    {
        return qr -> new Thread(() ->
        {
            final BitMatrix bitMatrix = createMatrix(qr);
            final BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try
            {
                ImageIO.write(bufferedImage , "png" , outputStream);
                new Thread(() -> onByteQr.onByte(new ByteArrayInputStream(outputStream.toByteArray()))).start();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private static BitMatrix createMatrix(String qr)
    {
        try
        {
            MultiFormatWriter writer = new MultiFormatWriter();
            return writer.encode(qr , BarcodeFormat.QR_CODE , 800 , 800 , Map.of(EncodeHintType.MARGIN , 3 , EncodeHintType.ERROR_CORRECTION , ErrorCorrectionLevel.L));
        }
        catch (WriterException var4)
        {
            throw new UnsupportedOperationException("Cannot create qr code" , var4);
        }
    }
}
