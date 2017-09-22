package com.example.user.mipp.Conexao;

import android.os.AsyncTask;

import com.example.user.mipp.Modelo.Save;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ConnectionIDs extends AsyncTask {
    private String url = "http://192.168.0.221:70/MIPP/unDeptoID.php";

    @Override
    protected int[] doInBackground(Object[] params) {
        int[] ids = new int[2];
        try {
            HttpURLConnection con;
            String urlParameters = "un=" + params[0] + "&depto=" + params[1];
            if(Save.TestConnection(url) != null){
                con = Save.TestConnection(url, urlParameters);
            }else{
                url = "http://187.35.128.157:70/MIPP/unDeptoID.php";
                con = Save.TestConnection(url, urlParameters);
            }

            StringBuilder response = new StringBuilder();
            BufferedReader in = null;
            if (con != null) {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
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

            ids[0] = jsonObjt.getInt("unID");
            ids[1] = jsonObjt.getInt("deptoID");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ids;
    }
}
