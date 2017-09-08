package com.sixelasavir.www.boombiblico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    public static final String TYPE = "type";
    public static final String TYPE_PLAYER_AVENTURERO = "aventurero";
    public static final String TYPE_PLAYER_CONQUISTADOR = "conquistador";
    public static final String TYPE_PLAYER_GUIA_MAYOR = "guia.mayor";
    private Intent intentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.intentLevel = new Intent(this,LevelActivity.class);
    }

    public void clickAventureros(View view){
        intentLevel.putExtra("type", TYPE_PLAYER_AVENTURERO);
        startActivity(intentLevel);
    }

    public void clickConquistadores(View view){
        intentLevel.putExtra("type", TYPE_PLAYER_CONQUISTADOR);
        startActivity(intentLevel);
    }

    public void clickGuiasMayores(View view){
        intentLevel.putExtra("type", TYPE_PLAYER_GUIA_MAYOR);
        startActivity(intentLevel);
    }
}
