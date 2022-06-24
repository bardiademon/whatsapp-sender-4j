package com.bardiademon.whatsapp.sender.view;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class View extends JFrame
{
    private boolean ready = false;

    private OnClose onClose;

    protected void setView(final String title)
    {
        setLocationRelativeTo(null);
        setTitle(title);
        setVisible(true);
        setResizable(false);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                onClose();
            }
        });

        ready = true;
    }

    public void setOnClose(OnClose onClose)
    {
        this.onClose = onClose;
    }

    protected OnClose getOnClose()
    {
        return onClose;
    }

    protected void onClose()
    {
        if (onClose != null) onClose.closed();
    }

    public void close()
    {
        dispose();
        ready = false;
        onClose();
        System.gc();
    }

    public boolean isReady()
    {
        return ready;
    }

    public interface OnClose
    {
        void closed();
    }
}
