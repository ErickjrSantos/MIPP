package com.example.user.mipp.Conexao;

import android.os.AsyncTask;

import com.example.user.mipp.Modelo.Produto;
import com.example.user.mipp.Modelo.Tela;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 01/09/17.
 */

public class Connection extends AsyncTask {

    String url = "http://187.35.128.157:70/MIPP/buscarTelas.php";
    public ArrayList<Produto> produtos = new ArrayList<>();
    public ArrayList<Tela>telas = new ArrayList<>();


    @Override
    protected ArrayList<Tela> doInBackground(Object[] params) {

        try {



            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            StringBuffer response = new StringBuffer();

            //dados POST
            String urlParameters = "codL=" + params[0] + "&codS=" + params[1];

            //Cria POST
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();


            int responseCode = con.getResponseCode();
            System.out.println("Codigo de resposta: " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String JsonStr = response.toString();


            if (JsonStr != null || JsonStr != "") {
                JSONObject jsonObjt = new JSONObject(JsonStr);

                int quantTelas = jsonObjt.getInt("quantTelas");

                JSONArray JArrayTelas = jsonObjt.getJSONArray("telas");
                for (int x = 0; x < quantTelas; x++) {
                    Tela tela = new Tela();
                    int quantProduto = JArrayTelas.getJSONObject(x).getInt("quantProdutos");
                    int codigo = JArrayTelas.getJSONObject(x).getInt("codigo");
                    int timer = JArrayTelas.getJSONObject(x).getInt("timer");

                    tela.setQtdProdutos(quantProduto);
                    tela.setCodigo(codigo);
                    tela.setTimer(timer);



                    JSONArray JArrayProduto = JArrayTelas.getJSONObject(x).getJSONArray("produtos");


                    for (int j = 0; j < quantProduto; j++) {

                        Produto produto = new Produto();


                        String codB = JArrayProduto.getJSONObject(j).getString("codigo");
                        String nomeProduto = JArrayProduto.getJSONObject(j).getString("descricao");
                        String preco = JArrayProduto.getJSONObject(j).getString("preco");

                        produto.setPreco(preco);
                        produto.setNomeProduto(nomeProduto);
                        produto.setCod(codB);

                        tela.produtos.add(produto);

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

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}