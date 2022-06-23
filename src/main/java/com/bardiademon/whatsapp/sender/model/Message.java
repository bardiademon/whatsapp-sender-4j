package com.bardiademon.whatsapp.sender.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Message
{
    private String phone;

    private String text;
    private Media image;
    private Media video;
    private Media document;

    @Getter
    @Setter
    public static final class Media
    {
        private String path;
        private String test;

        public static boolean checkEmpty(final Media media)
        {
            return media != null && media.getPath() != null && !media.getPath().isEmpty();
        }
    }
}
