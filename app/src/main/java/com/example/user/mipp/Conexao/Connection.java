package com.example.user.mipp.Conexao;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.user.mipp.Modelo.Produto;
import com.example.user.mipp.Modelo.Save;
import com.example.user.mipp.Modelo.Tela;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;


public class Connection extends AsyncTask {

    private String url = "http://192.168.0.221:70/MIPP/buscarTelas.php";
    private ArrayList<Tela> telas = new ArrayList<>();


    @Override
    protected ArrayList<Tela> doInBackground(Object[] params) {
        int responseCode;
        int quantProduto, codigo, timer;
        String imagem;
        Save save = Save.getInstance();
        String urlParameters = "codL=" + params[0] + "&codS=" + params[1];

        try {

            HttpURLConnection con;
            if(Save.TestConnection(url) != null){
                con = Save.TestConnection(url, urlParameters);
            }else{
                url = "http://187.35.128.157:70/MIPP/buscaUnidades.php";
                con = Save.TestConnection(url, urlParameters);
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

                int quantTelas = jsonObjt.getInt("quantTelas");

                String background = jsonObjt.getString("background");
                Drawable fundo = Save.decode(background);
                save.setGrade(fundo);

                JSONArray JArrayTelas = jsonObjt.getJSONArray("telas");
                for (int x = 0; x < quantTelas; x++) {
                    quantProduto = JArrayTelas.getJSONObject(x).getInt("quantProdutos");
                    codigo = JArrayTelas.getJSONObject(x).getInt("codigo");
                    timer = JArrayTelas.getJSONObject(x).getInt("timer");
                    imagem = JArrayTelas.getJSONObject(x).getString("imagem");

                    Tela tela = new Tela(codigo, timer, imagem);
                    JSONArray JArrayProduto = JArrayTelas.getJSONObject(x).getJSONArray("produtos");

                    for (int j = 0; j < quantProduto; j++) {

                        Produto produto = new Produto();


                        String codB = JArrayProduto.getJSONObject(j).getString("codigo");
                        String nomeProduto = JArrayProduto.getJSONObject(j).getString("descricao");
                        String preco = JArrayProduto.getJSONObject(j).getString("preco");

                        produto.setPreco(preco);
                        produto.setNomeProduto(nomeProduto);
                        produto.setCod(codB);

                        tela.addProduto(produto);

                    }
                    telas.add(tela);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return telas;
    }
}