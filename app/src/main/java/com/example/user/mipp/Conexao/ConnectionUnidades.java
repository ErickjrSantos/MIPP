package com.example.user.mipp.Conexao;

import android.os.AsyncTask;

import com.example.user.mipp.Modelo.Save;
import com.example.user.mipp.Modelo.UnDepto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by user on 20/09/17.
 */

public class ConnectionUnidades extends AsyncTask{
    private String url = "http://192.168.0.221:70/MIPP/buscaUnidades.php";

    @Override
    protected UnDepto doInBackground(Object[] params) {
        UnDepto undepto = new UnDepto();
        try {
            HttpURLConnection con;
            if(Save.TestConnection(url) != null){
                con = Save.TestConnection(url);
            }else{
                url = "http://187.35.128.157:70/MIPP/buscaUnidades.php";
                con = Save.TestConnection(url);
            }

            StringBuilder response = new StringBuilder();

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

                JSONArray JArrayUnidades = jsonObjt.getJSONArray("unidade");
                JSONArray JArrayDeptos = jsonObjt.getJSONArray("departamento");
                for(int i = 0; i < JArrayUnidades.length(); i++){
                    undepto.unidade.add((String)JArrayUnidades.get(i));
                }
                for(int i = 0; i < JArrayDeptos.length(); i++){
                    undepto.departamento.add((String)JArrayDeptos.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return undepto;
    }



}
