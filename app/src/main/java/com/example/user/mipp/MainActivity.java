package com.example.user.mipp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.user.mipp.Conexao.ConnectionQtdTelas;
import com.example.user.mipp.Conexao.ConnectionTela;
import com.example.user.mipp.Modelo.Save;
import com.example.user.mipp.Modelo.Tela;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #timer} milliseconds.
     */



    private static int loja;
    private static int departamento;
    int jTime = 0;
    boolean vTest = false;
    private static int timer = 4000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 600;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run(){

            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

            );
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
    protected void onRestart() {
        carregaProdutos();
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loja = getIntent().getExtras().getInt("unidade");
        departamento = getIntent().getExtras().getInt("departamento");

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

        Save.Network(getApplicationContext());
        if(!Save.havesInternet()){
            LimparTela();
        }

        verifyStoragePermissions(this);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
         //playVideo();
         carregaProdutos();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(600);
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

    @Override
    public void onBackPressed(){
        finish();
    }

    public void carregaProdutos() {
        new CountDownTimer(timer, 100) {
            public void onTick(long millisUntilFinished) {
                if (!vTest) {
                    //havia apenas limpa tela...mas este comando tambem coloca um fundo para referencia
                    LimparTela();
                }
            }

            public void onFinish() {
                try {
                    // Add tratamento de conexao para apresentar outra tela....
                    if(!Save.havesInternet()){
                        while (!Save.havesInternet()) {
                            Save.Network(getApplicationContext());
                            LimparTela();
                        }
                        jTime = 0;
                    }

                    if (jTime == 0) {
                        ConnectionQtdTelas CQT = new ConnectionQtdTelas();
                        CQT.execute(loja, departamento, getApplicationContext()).get();
                        FrameLayout fundo = (FrameLayout) findViewById(R.id.fundo);
                        Drawable img = Save.getInstance().getGrade();
                        fundo.setBackground(img);
                    }

                    if (jTime == Save.getInstance().getQuantTelas()) {
                        jTime = 0;
                    }

                    ConnectionTela CLP = new ConnectionTela();
                    Tela tela = (Tela) CLP.execute(loja, departamento, jTime + 1, getApplicationContext()).get();

                    int qtdProd = tela.getProdutos().size();

                    if(tela.getTipoMidia().equals("imagem")){
                        VideoView vv = (VideoView) findViewById(R.id.videoView);
                        vv.setVisibility(View.INVISIBLE);
                        Drawable img = tela.getImagem();
                        ImageView animation = (ImageView) findViewById(R.id.animation);
                        animation.setBackground(img);
                    }

                    timer = tela.getTimer();
                    timer = timer * 1000;
                    int corProduto;

                    //Se a midia for Video executar apenas este if...
                    if(tela.getTipoMidia().equals("video")){
                        VideoView vv = (VideoView) findViewById(R.id.videoView);
                        vv.setVisibility(View.VISIBLE);
                        playVideo();
                        LimparTela();
                    }else {

                        if (tela.getQtdProdutos() >= 0) {
                            for (int i = 0; i < qtdProd; i++) {
                                //Se o produto estiver em promoção, ele recebe a cor contida em R.color.Promocao
                                //Se nao, recebe a cor contida em R.color.Normal
                                if (tela.getProdutos().get(i).isPromocao()) {
                                    corProduto = Color.parseColor(tela.getCorPromo());

                                } else {
                                    corProduto = Color.parseColor(tela.getCorNormal());
                                }

                                int textcodigo = getResources().getIdentifier("codigo" + (i + 1), "id", getPackageName());//R.id.codigo1
                                TextView textViewcodigo = (TextView) findViewById(textcodigo);
                                textViewcodigo.setTextColor(corProduto);
                                String cod = tela.getProdutos().get(i).getCod();
                                textViewcodigo.setText(cod);

                                int textdescri = getResources().getIdentifier("descricao" + (i + 1), "id", getPackageName());
                                TextView textView = (TextView) findViewById(textdescri);
                                textView.setTextColor(corProduto);
                                String descricao = tela.getProdutos().get(i).getNomeProduto();
                                textView.setText(descricao);

                                int textpeso = getResources().getIdentifier("peso" + (i + 1), "id", getPackageName());
                                TextView textViewpeso = (TextView) findViewById(textpeso);
                                textViewpeso.setTextColor(corProduto);
                                String preco = tela.getProdutos().get(i).getPreco();
                                preco = "R$ " + preco.replace('.', ',');

                                textViewpeso.setText(preco);
                            }

                            for (int i = qtdProd; i < 17; i++) {

                                int textcodigo = getResources().getIdentifier("codigo" + (i + 1), "id", getPackageName());//R.id.codigo1
                                TextView textViewcodigo = (TextView) findViewById(textcodigo);
                                String cod = "";
                                textViewcodigo.setText(cod);

                                int textdescri = getResources().getIdentifier("descricao" + (i + 1), "id", getPackageName());
                                TextView textView = (TextView) findViewById(textdescri);
                                String descricao = "";
                                textView.setText(descricao);

                                int textpeso = getResources().getIdentifier("peso" + (i + 1), "id", getPackageName());
                                TextView textViewpeso = (TextView) findViewById(textpeso);
                                String preco = "";
                                textViewpeso.setText(preco);
                            }
                        } else {
                            timer = 0;
                        }
                    }
                    if (jTime < Save.getInstance().getQuantTelas()) {
                        jTime++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Save.Network(getApplicationContext());
                if(!Save.havesInternet()) {
                    LimparTela();
                }
                carregaProdutos();
            }

        }.start();
    }

    void LimparTela(){

        for (int i = 1; i <= 17; i++) {

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

            ImageView animation = (ImageView) findViewById(R.id.animation);
            animation.setBackground(null);
            animation.requestFocus();
            ConnectionTela CLP = new ConnectionTela();

        try {
                if(Save.havesInternet()) {
                    Tela tela = (Tela) CLP.execute(loja, departamento, jTime + 1, getApplicationContext()).get();

                    if (tela.getTipoMidia().equals("video")) {
                        int descri = getResources().getIdentifier("descricao" + (8), "id", getPackageName());
                        TextView textViewdesc = (TextView) findViewById(descri);
                        String descricao = "";
                        textViewdesc.setText(descricao);

                    } else {
                        int descri = getResources().getIdentifier("descricao" + (8), "id", getPackageName());
                        TextView textViewdesc = (TextView) findViewById(descri);
                        String descricao = "Redefinindo Produtos";
                        textViewdesc.setText(descricao);
                        textViewdesc.setTextColor(Color.parseColor("#FFFFFFFF"));

                        FrameLayout fundo = (FrameLayout) findViewById(R.id.fundo);
                        fundo.setBackground(getResources().getDrawable(R.drawable.teste));
                    }
                }else{
                    dialogTestConnection();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void  playVideo(){

        try {
            String url = "http://192.168.0.221:70/MIPP/teste.mp4"; //Environment.getExternalStorageDirectory() + "/Convert.mp4"  ;//"android.resource://" + getPackageName() + "/" + R.raw.teste; // your URL here
           // MediaController mc = new MediaController(this);
            VideoView vv = (VideoView) findViewById(R.id.videoView);
            vv.setVisibility(View.VISIBLE);
            //vv.setMediaController(mc);
            vv.setVideoPath(url);
            vv.requestFocus();
            vv.start();
            //mc.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public ProgressDialog dialog;

    public void dialogTestConnection(){

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Verificando Rede... ");
        dialog.setMessage("Procurando produtos !");
        dialog.show();
        dialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (!Save.havesInternet()) {
                        Thread.sleep(10000);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                dialog.dismiss();
                onStop();
            }
        }).start();

    }

}
