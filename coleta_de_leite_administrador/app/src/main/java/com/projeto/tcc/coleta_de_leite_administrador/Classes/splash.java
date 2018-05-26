package com.projeto.tcc.coleta_de_leite_administrador.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.projeto.tcc.coleta_de_leite_administrador.R;

/**
 * Created by raphael on 06/12/16.
 */

public class splash extends AppCompatActivity {
    private static int SPLASH_DISPLAY_LENGTH=3000 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(it);
                finish();
            }
        },SPLASH_DISPLAY_LENGTH);

}
}