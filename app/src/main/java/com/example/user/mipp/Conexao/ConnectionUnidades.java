package com.example.user.mipp.Conexao;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.user.mipp.Modelo.Produto;
import com.example.user.mipp.Modelo.Save;
import com.example.user.mipp.Modelo.Tela;
import com.example.user.mipp.Modelo.UnDepto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 20/09/17.
 */

public class ConnectionUnidades extends AsyncTask{
    private String url = "http://187.35.128.157:70/MIPP/buscaUnidades.php";

    @Override
    protected UnDepto doInBackground(Object[] params) {
        UnDepto undepto = new UnDepto();
        try {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            StringBuilder response = new StringBuilder();

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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
