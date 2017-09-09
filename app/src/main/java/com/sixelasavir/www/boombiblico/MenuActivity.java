package com.sixelasavir.www.boombiblico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        this.intentLevel = new Intent(this, LevelActivity.class);
    }

    public void clickAventureros(View view) {
        intentLevel.putExtra("type", TYPE_PLAYER_AVENTURERO);
        startActivity(intentLevel);
    }

    public void clickConquistadores(View view) {
        intentLevel.putExtra("type", TYPE_PLAYER_CONQUISTADOR);
        startActivity(intentLevel);
    }

    public void clickGuiasMayores(View view) {
        intentLevel.putExtra("type", TYPE_PLAYER_GUIA_MAYOR);
        startActivity(intentLevel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cup, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_medal:
                Intent intent = new Intent(this, ListRecordActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
