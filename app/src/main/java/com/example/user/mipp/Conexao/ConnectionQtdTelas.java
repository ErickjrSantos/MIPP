package com.example.user.mipp.Conexao;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.user.mipp.Modelo.Produto;
import com.example.user.mipp.Modelo.Save;
import com.example.user.mipp.Modelo.Tela;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;


public class ConnectionQtdTelas extends AsyncTask {

    private String url = "http://192.168.0.221:70/MIPP/getQtdTelas.php";


    @Override
    protected Object doInBackground(Object[] params) {
        Save save = Save.getInstance();
        String urlParameters = "codL=" + params[0] + "&codS=" + params[1];

        try {

            HttpURLConnection con;
            if(Save.TestConnection(url) != null){
                con = Save.TestConnection(url, urlParameters);
            }else{
                url = "http://187.35.128.157:70/MIPP/getQtdTelas.php";
                con = Save.TestConnection(url, urlParameters);
            }

            StringBuilder response = new StringBuilder();
            BufferedReader in = null;
            if (con != null) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            String inputLine;

            if (in != null) {
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
            String JsonStr = response.toString();
            JSONObject jsonObjt = new JSONObject(JsonStr);

            int quantTelas = jsonObjt.getInt("quantTelas");
            save.setQuantTelas(quantTelas);
            String background = jsonObjt.getString("background");
            Drawable fundo = Save.decode(background);
            save.setGrade(fundo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}