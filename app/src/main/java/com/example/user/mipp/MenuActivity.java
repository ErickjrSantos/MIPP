package com.example.user.mipp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

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

        ArrayList<String> unidades = new ArrayList<>();
        ArrayList<String> departamentos = new ArrayList<>();

        unidades.add(" ");
        unidades.add("Interlagos");
        unidades.add("Taboão");
        unidades.add("Morumbi");
        unidades.add("Bolonha");

        departamentos.add(" ");
        departamentos.add("1 - GERAL");
        departamentos.add("2 - HORTIFRUTI");
        departamentos.add("3 - PADARIA");
        departamentos.add("4 - AÇOUGUE");
        departamentos.add("5 - FRIOS");
        departamentos.add("6 - PREPARAÇÃO");
        departamentos.add("7 - NOBRE");
        departamentos.add("8 - ROTISSERIE");

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
                int selectedIndexUn = spUn.getSelectedItemPosition();
                int selectedIndexDp = spDp.getSelectedItemPosition();

                SharedPreferences settings = getSharedPreferences(MIPP_PREFERENCES, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("unidade", selectedIndexUn);
                editor.putInt("departamento", selectedIndexDp);
                editor.commit();

                Intent goToMIPP = new Intent(getApplicationContext(), MainActivity.class);

                goToMIPP.putExtra("unidade",selectedIndexUn);
                goToMIPP.putExtra("departamento",selectedIndexDp);

                startActivity(goToMIPP);
            }

        });
    }
}
