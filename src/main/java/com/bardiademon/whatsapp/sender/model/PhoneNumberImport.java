package com.bardiademon.whatsapp.sender.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PhoneNumberImport
{
    private String name;
    private String phone;
    private boolean send;


    @Override
    public String toString()
    {
        return String.format("Name: %s :: Phone: %s" , name , phone);
    }
}
