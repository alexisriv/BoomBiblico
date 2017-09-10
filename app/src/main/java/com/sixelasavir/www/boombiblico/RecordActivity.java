package com.sixelasavir.www.boombiblico;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sixelasavir.www.boombiblico.greendao.model.DaoSession;
import com.sixelasavir.www.boombiblico.greendao.model.GamerRecord;
import com.sixelasavir.www.boombiblico.greendao.model.GamerRecordDao;

public class RecordActivity extends AppCompatActivity {

    private TextView numberRecordTextView;
    private TextView timeRecordTextView;
    private Intent intentNextAction;
    private Bundle bundle;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        bundle = getIntent().getExtras();

        loadResources();
        setValuesInTextView(bundle.getString(QuestionActivity.RECORD), bundle.getString(QuestionActivity.TIMER_RECORD_STRING));

        daoSession = ((AppBoomBiblico) getApplication()).getDaoSession();
    }


    private void loadResources() {
        numberRecordTextView = (TextView) findViewById(R.id.number_record_text_view);
        timeRecordTextView = (TextView) findViewById(R.id.time_record_text_view);
    }

    private void setValuesInTextView(String textNumber, String textTime) {
        numberRecordTextView.setText(textNumber);
        timeRecordTextView.setText(textTime);
    }

    public void saveRecord(View v) {
        onCreateDialog().show();
    }

    public void exitRecord(View v) {
        finish();
    }

    public void nextPlay(View v) {
        intentNextAction = new Intent(this, QuestionActivity.class);
        intentNextAction.putExtra(MenuActivity.TYPE, bundle.getString(MenuActivity.TYPE));
        intentNextAction.putExtra(LevelActivity.NUMBER_LEVEL, bundle.getInt(LevelActivity.NUMBER_LEVEL));
        intentNextAction.putExtra(LevelActivity.NUMBER_OF_QUESTIONS, bundle.getInt(LevelActivity.NUMBER_OF_QUESTIONS));
        startActivity(intentNextAction);
        finish();
    }

    public Dialog onCreateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.save_recod_dialog,null);
        builder.setTitle(R.string.title_dialog_record_save).
                setView(view)
                .setMessage(R.string.questions_record_save)
                .setPositiveButton(R.string.record_save_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GamerRecordDao gamerRecordDao = daoSession.getGamerRecordDao();
                        GamerRecord gamerRecord = new GamerRecord();
                        String name = ((EditText) view.findViewById(R.id.name_record_save)).getText().toString();

                        if(name != null && !name.trim().isEmpty()){
                            gamerRecord.setNameGamer(name);
                            gamerRecord.setType(bundle.getString(MenuActivity.TYPE));
                            gamerRecord.setLevel(bundle.getInt(LevelActivity.NUMBER_LEVEL));
                            gamerRecord.setNumber(bundle.getInt(LevelActivity.NUMBER_OF_QUESTIONS));
                            gamerRecord.setRecordGamer(bundle.getInt(QuestionActivity.RECORD_VALUE));
                            gamerRecord.setTimerRecordGamer(bundle.getString(QuestionActivity.TIMER_RECORD_STRING));

                            gamerRecordDao.save(gamerRecord);
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),R.string.save_toast, Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"El Nombre no puede ser nulo", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.record_save_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

}
