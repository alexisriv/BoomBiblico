package com.sixelasavir.www.boombiblico;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class LevelActivity extends AppCompatActivity {

    private GridView levelGridView;
    private Intent intent;
    private static final boolean  activeDialog = false;

    public static final String NUMBER_LEVEL = "number.level";
    public static final String NUMBER_OF_QUESTIONS = "number.of.questions";
    private Intent intentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        this.loadResources();
        intent = getIntent();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        levelGridView.setAdapter(new LevelAdapter(this, intent.getStringExtra(MenuActivity.TYPE)));
        levelGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(activeDialog)
                    onCreateDialog(position).show();
                else{
                    intentQuestion = new Intent(LevelActivity.this,QuestionActivity.class);
                    intentQuestion.putExtra(MenuActivity.TYPE, intent.getStringExtra(MenuActivity.TYPE));
                    intentQuestion.putExtra(NUMBER_LEVEL, position);
                    intentQuestion.putExtra(NUMBER_OF_QUESTIONS, 0);
                    startActivity(intentQuestion);
                    LevelActivity.this.finish();
                }
            }
        });
    }

    public void loadResources(){
        this.levelGridView = (GridView) findViewById(R.id.level_gridview);
    }


    public Dialog onCreateDialog(final int level) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_modal_participation)
                .setItems(R.array.level_type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intentQuestion = new Intent(LevelActivity.this,QuestionActivity.class);
                        intentQuestion.putExtra(MenuActivity.TYPE, intent.getStringExtra(MenuActivity.TYPE));
                        intentQuestion.putExtra(NUMBER_LEVEL, level);
                        intentQuestion.putExtra(NUMBER_OF_QUESTIONS, which);
                        startActivity(intentQuestion);
                        finish();
                    }
                });
        return builder.create();
    }




}
