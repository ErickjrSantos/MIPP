package com.example.user.mipp;

import android.app.Activity;
import android.widget.TextView;

import com.example.user.mipp.Conexao.Connection;
import com.example.user.mipp.Modelo.Tela;

import java.util.ArrayList;

/**
 * Created by user on 01/09/17.
 */

public class CarregaTela extends Activity implements  Runnable {
    int jTime = 0;
    @Override
    public void run() {
        try {

            Connection CLP = new Connection();
            ArrayList<Tela> telas = (ArrayList<Tela>) CLP.execute(1, 3).get();

            for (jTime = 0; jTime <= telas.size(); jTime++) {

                int tempo = 3000; //telas.get(j).getTimer();



                for (int i = 1; i < telas.get(jTime).produtos.size(); i++) {


                    int textcodigo = getResources().getIdentifier("codigo" + (i), "id", getPackageName());//R.id.codigo1
                    TextView textViewcodigo = (TextView) findViewById(textcodigo);
                    String cod = telas.get(jTime).produtos.get(i).getCod();
                    textViewcodigo.setText(cod);


                    int textdescri = getResources().getIdentifier("descricao" + (i), "id", getPackageName());
                    TextView textView = (TextView) findViewById(textdescri);
                    String descricao = telas.get(jTime).produtos.get(i).getNomeProduto();
                    textView.setText(descricao);

                    int textpeso = getResources().getIdentifier("peso" + (i), "id", getPackageName());
                    TextView textViewpeso = (TextView) findViewById(textpeso);
                    String preco = telas.get(jTime).produtos.get(i).getPreco();
                    textViewpeso.setText("R$ " + preco);



                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
