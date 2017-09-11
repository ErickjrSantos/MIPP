package com.example.user.mipp;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.user.mipp.Conexao.Connection;
import com.example.user.mipp.Modelo.Tela;
import java.util.ArrayList;

import static com.example.user.mipp.R.drawable.gradepadaria;


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

        ImageView imageView = (ImageView) findViewById(R.id.animation);
        imageView.setBackgroundResource(R.drawable.animation);

        AnimationDrawable animation =(AnimationDrawable) imageView.getBackground();
        animation.start();
        animation.setEnterFadeDuration(1000);
        animation.setExitFadeDuration(1000);


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

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
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


        new CountDownTimer(timer, 1000) {

            public void onTick(long millisUntilFinished) {


                if (vTest==false) {
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
                    ArrayList<Tela> telas = (ArrayList<Tela>) CLP.execute(1,3).get();

                    if(jTime < telas.size()){
                        int qtdProd = telas.get(jTime).produtos.size();

                        ImageView fundo = (ImageView) findViewById(R.id.fundo);





                        for (int i = 0; i < qtdProd; i++) {

                            int textcodigo = getResources().getIdentifier("codigo" + (i+1), "id", getPackageName());//R.id.codigo1
                            TextView textViewcodigo = (TextView) findViewById(textcodigo);
                            String cod = telas.get(jTime).produtos.get(i).getCod();
                            textViewcodigo.setText(cod);

                            int textdescri = getResources().getIdentifier("descricao" + (i+1), "id", getPackageName());
                            TextView textView = (TextView) findViewById(textdescri);
                            String descricao = telas.get(jTime).produtos.get(i).getNomeProduto();
                            textView.setText(descricao);

                            int textpeso = getResources().getIdentifier("peso" + (i+1), "id", getPackageName());
                            TextView textViewpeso = (TextView) findViewById(textpeso);
                            String preco = telas.get(jTime).produtos.get(i).getPreco();
                            textViewpeso.setText("R$ " + preco);


                        }
                            for (int i = qtdProd+1; i < 17; i++) {

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
                        jTime ++;


                    }else {
                        jTime=0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

                carregaProdutos();
            }
        }.start();




    }
}
