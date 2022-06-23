package com.bardiademon.whatsapp.sender.controller.connector;

import java.io.InputStream;

public interface ConnectorStatus
{
    void onReady();

    void onDisconnect();

    void onQrCode(final InputStream inputStream);
}
