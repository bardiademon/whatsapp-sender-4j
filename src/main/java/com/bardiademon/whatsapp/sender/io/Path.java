package com.bardiademon.whatsapp.sender.io;

import java.io.File;

public final class Path
{
    private Path()
    {
    }

    public static final String IMG_LOADING_NAME = "loading.gif";

    public static final String ROOT = System.getProperty("user.dir"),
            DATA = get(ROOT , "data"), IMAGES = get(DATA , "images"), ICONS = get(IMAGES , "icons");

    public static String get(Object... paths)
    {
        final StringBuilder finalPath = new StringBuilder();
        for (int i = 0, len = paths.length; i < len; i++)
        {
            finalPath.append(paths[i]);
            if ((i + 1) < len)
                finalPath.append(File.separator);
        }
        return finalPath.toString();
    }

    public static String getIcon(final String iconName)
    {
        return get(ICONS , iconName);
    }
}
