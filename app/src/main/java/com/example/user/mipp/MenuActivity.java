package com.example.user.mipp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        ArrayList<String> unidades;
        ArrayList<String> departamentos;

        ConnectionUnidades conun = new ConnectionUnidades();
        UnDepto unDepto = new UnDepto();


        try {
            unDepto = (UnDepto) conun.execute(getApplicationContext()).get();

        } catch (Exception e) {
            e.printStackTrace();
        }


        unidades = unDepto.unidade;
        departamentos = unDepto.departamento;

        ArrayAdapter<String> adptUn = new ArrayAdapter<String>(
                this, R.layout.spinner_mipp, unidades);
        final ArrayAdapter<String> adptDp = new ArrayAdapter<String>(
                this, R.layout.spinner_mipp, departamentos);
        adptUn.setDropDownViewResource(R.layout.spinner_dropdown_mipp);
        adptDp.setDropDownViewResource(R.layout.spinner_dropdown_mipp);

        final Spinner spUn = (Spinner) findViewById(R.id.spinnerUnidade);
        final Spinner spDp = (Spinner) findViewById(R.id.spinnerDepto);

        spUn.setAdapter(adptUn);
        spDp.setAdapter(adptDp);

        Button btnEntrar = (Button) findViewById(R.id.btnIniciaMIPP);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedIndexUn = spUn.getSelectedItem().toString();
                String selectedIndexDp = spDp.getSelectedItem().toString();
                if(selectedIndexDp.equals(" ") || selectedIndexUn.equals(" ") ){
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
                    finish();
                }
            }

        });
    }


}
