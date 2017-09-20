package com.example.user.mipp.Conexao;

import android.os.AsyncTask;

import com.example.user.mipp.Modelo.UnDepto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 20/09/17.
 */

public class ConnectionIDs extends AsyncTask {
    private String url = "http://187.35.128.157:70/MIPP/unDeptoID.php";

    @Override
    protected int[] doInBackground(Object[] params) {
        int[] ids = new int[2];
        try {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            StringBuilder response = new StringBuilder();

            //dados POST
            String urlParameters = "un=" + params[0] + "&depto=" + params[1];

            //Cria POST
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String JsonStr = response.toString();


            if (JsonStr != null) {
                JSONObject jsonObjt = new JSONObject(JsonStr);

                ids[0] = jsonObjt.getInt("unID");
                ids[1] = jsonObjt.getInt("deptoID");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ids;
    }
}
