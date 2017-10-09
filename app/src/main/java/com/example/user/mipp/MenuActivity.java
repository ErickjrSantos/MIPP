package com.example.user.mipp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.user.mipp.Conexao.ConnectionIDs;
import com.example.user.mipp.Conexao.ConnectionUnidades;
import com.example.user.mipp.Modelo.UnDepto;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MenuActivity extends AppCompatActivity {

    private static final String MIPP_PREFERENCES = "MIPPPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        int selectedIndexUn;
        int selectedIndexDp;

        SharedPreferences settings = getSharedPreferences(MIPP_PREFERENCES, 0);
        selectedIndexDp = settings.getInt("departamento", 0);
        selectedIndexUn = settings.getInt("unidade", 0);


        if(selectedIndexDp != 0 && selectedIndexUn != 0){
            Intent goToMIPP = new Intent(getApplicationContext(), MainActivity.class);

            goToMIPP.putExtra("unidade",selectedIndexUn);
            goToMIPP.putExtra("departamento",selectedIndexDp);

            startActivity(goToMIPP);
        }

        final Spinner spUn = (Spinner) findViewById(R.id.spinnerUnidade);
        final Spinner spDp = (Spinner) findViewById(R.id.spinnerDepto);

        //Evento ao clicar no botão das unidades
        spUn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ConnectionUnidades conun = new ConnectionUnidades();
                UnDepto unDepto = new UnDepto();
                try {
                    unDepto = (UnDepto) conun.execute(getApplicationContext()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                ArrayList<String> unidades;
                unidades = unDepto.unidade;

                ArrayAdapter<String> adptUn = new ArrayAdapter<>(MenuActivity.this, R.layout.spinner_mipp, unidades);
                adptUn.setDropDownViewResource(R.layout.spinner_dropdown_mipp);
                spUn.setAdapter(adptUn);

                return false;
            }

        });

        //Evento ao clicar no botão dos departamentos
        spDp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ConnectionUnidades conun = new ConnectionUnidades();
                UnDepto unDepto = new UnDepto();
                try {
                    unDepto = (UnDepto) conun.execute(getApplicationContext()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                ArrayList<String> departamentos;
                departamentos = unDepto.departamento;

                ArrayAdapter<String> adptDp = new ArrayAdapter<>(MenuActivity.this, R.layout.spinner_mipp, departamentos);
                adptDp.setDropDownViewResource(R.layout.spinner_dropdown_mipp);
                spDp.setAdapter(adptDp);
                return false;
            }

        });

        Button btnEntrar = (Button) findViewById(R.id.btnIniciaMIPP);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedIndexUn = spUn.getSelectedItem().toString();
                String selectedIndexDp = spDp.getSelectedItem().toString();

                ConnectionUnidades conun = new ConnectionUnidades();
                UnDepto unDepto = new UnDepto();
                try {
                    unDepto = (UnDepto) conun.execute(getApplicationContext()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                ArrayList<String> unidades;
                unidades = unDepto.unidade;
                ArrayList<String> departamentos;
                departamentos = unDepto.departamento;

                if((selectedIndexDp.equals(" ") || selectedIndexUn.equals(" ")) && unidades.contains(selectedIndexUn) && departamentos.contains(selectedIndexDp)){
                    Toast.makeText(MenuActivity.this, "Selecione Departamento e unidade!!", Toast.LENGTH_SHORT).show();
                }else {
                    int[] ids = null;
                    ConnectionIDs CID = new ConnectionIDs();
                    try {
                        ids = (int[]) CID.execute(selectedIndexUn, selectedIndexDp, getApplicationContext()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences settings = getSharedPreferences(MIPP_PREFERENCES, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("unidade", ids[0]);
                    editor.putInt("departamento", ids[1]);
                    editor.apply();

                    Intent goToMIPP = new Intent(getApplicationContext(), MainActivity.class);

                    goToMIPP.putExtra("unidade", ids[0]);
                    goToMIPP.putExtra("departamento", ids[1]);

                    startActivity(goToMIPP);
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
