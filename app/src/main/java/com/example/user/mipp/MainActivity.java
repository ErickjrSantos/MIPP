package com.example.user.mipp;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.user.mipp.Conexao.Connection;
import com.example.user.mipp.Modelo.Save;
import com.example.user.mipp.Modelo.Tela;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #timer} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private static int loja;
    private static int departamento;


    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static int timer = 4000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.codigo1);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        loja = 1;
        departamento = 5;

        TextView txtSetor = (TextView) findViewById(R.id.nomeSetor);
        switch (departamento){
            case 1:
                txtSetor.setText(getResources().getString(R.string.setor1));
                break;
            case 2:
                txtSetor.setText(getResources().getString(R.string.setor2));
                break;
            case 3:
                txtSetor.setText(getResources().getString(R.string.setor3));
                break;
            case 4:
                txtSetor.setText(getResources().getString(R.string.setor4));
                break;
            case 5:
                txtSetor.setText(getResources().getString(R.string.setor5));
                break;
            case 6:
                txtSetor.setText(getResources().getString(R.string.setor6));
                break;
            case 7:
                txtSetor.setText(getResources().getString(R.string.setor7));
                break;
            case 8:
                txtSetor.setText(getResources().getString(R.string.setor8));
                break;
        }

        try {
            Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Futura Std Medium.otf");
            txtSetor.setTypeface(typeFace);
        }catch(Exception e){
            e.printStackTrace();
        }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        Connection CLP = new Connection();
        try {
            CLP.execute(loja,departamento).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        FrameLayout fundo = (FrameLayout) findViewById(R.id.fundo);
        Drawable img = Save.getInstance().getGrade();
        fundo.setBackground(img);

        carregaProdutos();




    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }



    int jTime = 0;
    boolean vTest = false;


    public void carregaProdutos() {


        new CountDownTimer(timer, 10) {

            public void onTick(long millisUntilFinished) {


                if (!vTest) {
                    for (int i = 1; i < 18; i++) {

                        int textcodigo = getResources().getIdentifier("codigo" + (i), "id", getPackageName());//R.id.codigo1
                        TextView textViewcodigo = (TextView) findViewById(textcodigo);
                        String cod = "";
                        textViewcodigo.setText(cod);

                        int textdescri = getResources().getIdentifier("descricao" + (i), "id", getPackageName());
                        TextView textView = (TextView) findViewById(textdescri);
                        String descricao = "";
                        textView.setText(descricao);

                        int textpeso = getResources().getIdentifier("peso" + (i), "id", getPackageName());
                        TextView textViewpeso = (TextView) findViewById(textpeso);
                        String preco = "";
                        textViewpeso.setText(preco);
                        vTest=true;
                    }
                }

            }

            public void onFinish() {
                try {

                    Connection CLP = new Connection();
                    ArrayList<Tela> telas = (ArrayList<Tela>) CLP.execute(loja,departamento).get();

                    if(jTime >= telas.size()){
                        jTime=0;
                    }

                    int qtdProd = telas.get(jTime).getProdutos().size();

                    /*FrameLayout fundo = (FrameLayout) findViewById(R.id.fundo);
                    Drawable img = telas.get(jTime).getImagem();
                    fundo.setBackground(img);*/
                    //Essa grade não muda. Colocar no onCreate()
                    //Utilizar aqui a imagem interna, do ImageView de ID animation
                    Drawable img = telas.get(jTime).getImagem();
                    ImageView animation = (ImageView) findViewById(R.id.animation);
                    animation.setBackground(img);

                    timer = telas.get(jTime).getTimer();
                    timer = timer * 1000;

                    for (int i = 0; i < qtdProd; i++) {

                        int textcodigo = getResources().getIdentifier("codigo" + (i+1), "id", getPackageName());//R.id.codigo1
                        TextView textViewcodigo = (TextView) findViewById(textcodigo);
                        String cod = telas.get(jTime).getProdutos().get(i).getCod();
                        textViewcodigo.setText(cod);

                        int textdescri = getResources().getIdentifier("descricao" + (i+1), "id", getPackageName());
                        TextView textView = (TextView) findViewById(textdescri);
                        String descricao = telas.get(jTime).getProdutos().get(i).getNomeProduto();
                        textView.setText(descricao);

                        int textpeso = getResources().getIdentifier("peso" + (i+1), "id", getPackageName());
                        TextView textViewpeso = (TextView) findViewById(textpeso);
                        String preco = telas.get(jTime).getProdutos().get(i).getPreco();
                        preco = preco.replace('.',',');
                        textViewpeso.setText("R$ " + preco);
                    }

                    for (int i = qtdProd; i < 17; i++) {

                        int textcodigo = getResources().getIdentifier("codigo" + (i+1), "id", getPackageName());//R.id.codigo1
                        TextView textViewcodigo = (TextView) findViewById(textcodigo);
                        String cod = "";
                        textViewcodigo.setText(cod);

                        int textdescri = getResources().getIdentifier("descricao" + (i+1), "id", getPackageName());
                        TextView textView = (TextView) findViewById(textdescri);
                        String descricao = "";
                        textView.setText(descricao);

                        int textpeso = getResources().getIdentifier("peso" + (i+1), "id", getPackageName());
                        TextView textViewpeso = (TextView) findViewById(textpeso);
                        String preco = "";
                        textViewpeso.setText(preco);
                    }
                    if(jTime < telas.size()) {
                        jTime++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                carregaProdutos();
            }
        }.start();




    }
}
