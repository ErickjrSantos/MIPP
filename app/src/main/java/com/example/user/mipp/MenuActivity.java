package com.example.user.mipp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ArrayList<String> unidades = new ArrayList<>();
        ArrayList<String> departamentos = new ArrayList<>();

        unidades.add("Interlagos");
        unidades.add("Taboão");
        unidades.add("Morumbi");
        unidades.add("Bolonha");

        departamentos.add("1 - GERAL");
        departamentos.add("2 - HORTIFRUTI");
        departamentos.add("3 - PADARIA");
        departamentos.add("4 - AÇOUGUE");
        departamentos.add("5 - FRIOS");
        departamentos.add("6 - PREPARAÇÃO");
        departamentos.add("7 - NOBRE");
        departamentos.add("8 - ROTISSERIE");

        ArrayAdapter<String> adptUn = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, unidades);
        ArrayAdapter<String> adptDp = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, departamentos);
        adptUn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adptDp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spUn = (Spinner) findViewById(R.id.spinnerUnidade);
        Spinner spDp = (Spinner) findViewById(R.id.spinnerDepto);

        spUn.setAdapter(adptUn);
        spDp.setAdapter(adptDp);
    }
}
