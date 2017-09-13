package com.example.user.mipp.Modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;


/**
 * Created by user on 11/09/17.
 */

public class Save {
    static Save saved;
    Drawable grade;

    public static Save getInstance(){
        if(saved == null)
            saved = new Save();
        return saved;
    }

    public void setGrade(Drawable grade){
        this.grade = grade;
    }

    public Drawable getGrade(){
        return this.grade;
    }

    public static Drawable decode(String code){
        byte[] decodeString = Base64.decode(code,Base64.DEFAULT);
        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        BitmapDrawable draImg = new BitmapDrawable(decodeByte);
        return draImg;
    }

}
