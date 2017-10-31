package com.example.user.mipp.Modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Save {
    private static Save saved;
    private Drawable grade;
    private static boolean haveInternet = true;

    public static boolean havesInternet() {
        return haveInternet;
    }

    public static void setHaveInternet(boolean haveInternet) {
        Save.haveInternet = haveInternet;
    }

    public int getQuantTelas() {
        return quantTelas;
    }

    public void setQuantTelas(int quantTelas) {
        this.quantTelas = quantTelas;
    }

    private int quantTelas = 0;

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
        return new BitmapDrawable(decodeByte);
    }

    public  static void Network(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnect = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        haveInternet = isConnect;

    }

    public static HttpURLConnection TestConnection(Context context, String url){
        Network(context);
        HttpURLConnection con;
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            if(url.substring(7,23).equals("192.168.0.221:70"))
                con.setConnectTimeout(200);
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

    public static HttpURLConnection TestConnection(Context context, String url, String urlParameters){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnect = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        haveInternet = isConnect;

        HttpURLConnection con;
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setConnectTimeout(200);
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
