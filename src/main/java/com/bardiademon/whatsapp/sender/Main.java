package com.bardiademon.whatsapp.sender;

import com.bardiademon.whatsapp.sender.controller.ViewController.HomeController;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Main
{
    public static void main(String[] args)
    {
        setLookAndFeel();
        new HomeController();
    }

    private static void setLookAndFeel()
    {
        try
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Windows".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
               InstantiationException ex)
        {
            ex.printStackTrace();
        }
    }
}