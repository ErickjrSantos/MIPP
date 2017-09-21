package com.example.user.mipp.Modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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

    public static HttpURLConnection TestConnection(String url){
        HttpURLConnection con;
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setConnectTimeout(1000);
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.flush();
            wr.close();
        }catch (Exception ex){
            return null;
        }
        return con;
    }

    public static HttpURLConnection TestConnection(String url, String urlParameters){
        HttpURLConnection con;
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setConnectTimeout(1000);
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
        }catch (Exception ex){
            return null;
        }
        return con;
    }

}
