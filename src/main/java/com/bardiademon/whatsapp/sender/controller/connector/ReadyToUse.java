package com.bardiademon.whatsapp.sender.controller.connector;

import java.io.InputStream;

public interface ReadyToUse
{
    void onReady();

    void onQrCode(final InputStream inputStream);
}
