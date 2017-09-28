package com.example.user.mipp.Conexao;

import android.content.Context;
import android.os.AsyncTask;

import com.example.user.mipp.Modelo.Produto;
import com.example.user.mipp.Modelo.Save;
import com.example.user.mipp.Modelo.Tela;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ConnectionTela extends AsyncTask{
    private String url = "http://192.168.0.221:70/MIPP/getTela.php";


    @Override
    protected Tela doInBackground(Object[] params) {
        String urlParameters = "codL=" + params[0] + "&codS=" + params[1] + "&codT=" + params[2];
        Context context = (Context) params[3];
        Tela t;

        try {

            HttpURLConnection con = Save.TestConnection(context, url, urlParameters);
            if(con == null){
                url = "http://187.35.128.157:70/MIPP/getTela.php";
                con = Save.TestConnection(context, url, urlParameters);
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

            int codT = jsonObjt.getInt("codigo");
            int timer = jsonObjt.getInt("timer");
            String imagem = jsonObjt.getString("imagem");
            int quantProdutos = jsonObjt.getInt("quantProdutos");
            String corNormal = jsonObjt.getString("corNormal");
            String corPromo = jsonObjt.getString("corPromo");

            t = new Tela(codT, timer, imagem, corNormal, corPromo);

            JSONArray JArrayProduto = jsonObjt.getJSONArray("produtos");
            for(int i = 0; i < quantProdutos; i++){
                String codP = JArrayProduto.getJSONObject(i).getString("codigo");
                String desc = JArrayProduto.getJSONObject(i).getString("descricao");
                String preco = JArrayProduto.getJSONObject(i).getString("preco");
                boolean promocao = (JArrayProduto.getJSONObject(i).getInt("promocao") == 1);

                Produto p = new Produto(codP, desc, preco, promocao);

                t.addProduto(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
}
