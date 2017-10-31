package com.example.user.mipp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.user.mipp.Modelo.Save;

/**
 * Created by user on 30/10/17.
 */

public class ReiniciaFiveMinuts extends AsyncTask {

    private ProgressDialog dialog;
    private Context context;

    public ReiniciaFiveMinuts(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {


    }



    @Override
    protected Object doInBackground(Object[] objects) {

        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long l) {
                if(!Save.havesInternet()){
                   Save.Network(context);
                }else if(Save.havesInternet()){
                   String test = "Connection ok";
                   onPostExecute(test);
                }
            }
            @Override
            public void onFinish() {
                Toast.makeText(context, "DESLIGANDO", Toast.LENGTH_SHORT).show();
            }
        }.start();

        return " ";
    }

    @Override
    protected void onPostExecute(Object o) {
        MainActivity teste = new MainActivity();
        teste.carregaProdutos();
        Toast.makeText(context, "Voltando"+ o , Toast.LENGTH_SHORT).show();
    }
}
