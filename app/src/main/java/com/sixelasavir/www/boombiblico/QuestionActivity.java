package com.sixelasavir.www.boombiblico;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {


    public static final String RECORD = "record";
    public static final String TIMER_RECORD_STRING = "timer.record.string";
    public static final String RECORD_VALUE = "record.value";

    private List<Question> questions;
    private List<Integer> answeredQuestions;
    private int numberQuestion;
    private int counter;
    private String answ = "";
    private int answPosition;
    private String stringTime;
    private int numberAnsw = 0;

    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;

    private MediaPlayer mPlayerCorrect;
    private MediaPlayer mPlayerIncorrect;

    private CardView valueCardView;
    private LinearLayout questionCardView;
    private TextView valueTextView;
    private TextView questionTextView;
    private TextView optionATextView;
    private TextView optionBTextView;
    private TextView optionCTextView;
    private TextView optionDTextView;
    private TextView timerRecordInit;


    private CardView optionALinearLayout;
    private CardView optionBLinearLayout;
    private CardView optionCLinearLayout;
    private CardView optionDLinearLayout;


    private Button afterButton;
    private Button pauseButton;

    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        bundle = getIntent().getExtras();
        mPlayerCorrect = MediaPlayer.create(getApplicationContext(), R.raw.correct_audio);
        mPlayerCorrect.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayerIncorrect = MediaPlayer.create(getApplicationContext(), R.raw.incorrect_audio);
        mPlayerIncorrect.setAudioStreamType(AudioManager.STREAM_MUSIC);

        answeredQuestions = new ArrayList<Integer>();
        switch (bundle.getString(MenuActivity.TYPE)) {
            case MenuActivity.TYPE_PLAYER_AVENTURERO:
                questions = Data.loadDataAventureros(bundle.getInt(LevelActivity.NUMBER_LEVEL));
                break;
            case MenuActivity.TYPE_PLAYER_CONQUISTADOR:
                questions = Data.loadDataConquistadores(bundle.getInt(LevelActivity.NUMBER_LEVEL));
                break;
            case MenuActivity.TYPE_PLAYER_GUIA_MAYOR:
                questions = Data.loadDataGuiasMayores(bundle.getInt(LevelActivity.NUMBER_LEVEL));
                break;
        }

        counter = 1;
        switch (bundle.getInt(LevelActivity.NUMBER_OF_QUESTIONS)) {
            case 0:
                numberQuestion = 5;
                break;
            case 1:
                numberQuestion = 10;
                break;
            case 2:
                numberQuestion = 15;
                break;
            default:
                numberQuestion = 7;
                break;
        }

        this.loadResources();
        questionCardView.setVisibility(CardView.GONE);

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            long count = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                count = (millisUntilFinished / 1000) - 1;
                Log.d("onTick", Long.toString(millisUntilFinished));
                valueTextView.setText(Long.toString(count));

                if (count == 0) {
                    valueTextView.setText(R.string.go);
                }
            }

            @Override
            public void onFinish() {
                valueCardView.setVisibility(CardView.GONE);
                questionCardView.setVisibility(CardView.VISIBLE);
                loadQuestion();
            }
        };
        countDownTimer.start();
    }

    public void loadResources() {
        valueCardView = (CardView) findViewById(R.id.value_card_view);
        valueTextView = (TextView) findViewById(R.id.value_text_view);
        questionCardView = (LinearLayout) findViewById(R.id.question_card_view);
        questionTextView = (TextView) findViewById(R.id.question_text_view);
        afterButton = (Button) findViewById(R.id.after_button);
        pauseButton = (Button) findViewById(R.id.pause_button);
        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionALinearLayout.setEnabled(false);
                optionBLinearLayout.setEnabled(false);
                optionCLinearLayout.setEnabled(false);
                optionDLinearLayout.setEnabled(false);

                Thread breakTime = new Thread() {
                    @Override
                    public void run() {
                        try {
                            final String answCorrect = questions.get(answPosition).getAnswer();

                            synchronized (this) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pauseClock();
                                        afterButton.setEnabled(false);
                                        pauseButton.setEnabled(false);
                                        if (answ == Data.ANSWER_A && answ != answCorrect) {
                                            optionALinearLayout.setBackgroundColor(getResources().getColor(R.color.colorIncorrect));
                                            mPlayerIncorrect.start();
                                        } else if (answ == Data.ANSWER_B && answ != answCorrect) {
                                            optionBLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorIncorrect));
                                            mPlayerIncorrect.start();
                                        } else if (answ == Data.ANSWER_C && answ != answCorrect) {
                                            optionCLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorIncorrect));
                                            mPlayerIncorrect.start();
                                        } else if (answ == Data.ANSWER_D && answ != answCorrect) {
                                            optionDLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorIncorrect));
                                            mPlayerIncorrect.start();
                                        } else if (answ == answCorrect) {
                                            mPlayerCorrect.start();
                                            numberAnsw++;
                                        } else if (answ == "") {
                                            mPlayerIncorrect.start();
                                        }

                                        switch (answCorrect) {
                                            case Data.ANSWER_A:
                                                optionALinearLayout.setBackgroundColor(getResources().getColor(R.color.colorCorrect));
                                                break;
                                            case Data.ANSWER_B:
                                                optionBLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorCorrect));
                                                break;
                                            case Data.ANSWER_C:
                                                optionCLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorCorrect));
                                                break;
                                            case Data.ANSWER_D:
                                                optionDLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorCorrect));
                                                break;
                                        }
                                        answ = "";
                                    }
                                });
                                wait(3000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadQuestion();
                                        optionALinearLayout.setEnabled(true);
                                        optionBLinearLayout.setEnabled(true);
                                        optionCLinearLayout.setEnabled(true);
                                        optionDLinearLayout.setEnabled(true);
                                        afterButton.setEnabled(true);
                                        pauseButton.setEnabled(true);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Log.e(getClass().getSimpleName(), e.getMessage());
                            e.getStackTrace();
                        }

                    }
                };
                breakTime.start();

            }
        });

        optionATextView = (TextView) findViewById(R.id.option_a_text_view);
        optionBTextView = (TextView) findViewById(R.id.option_b_text_view);
        optionCTextView = (TextView) findViewById(R.id.option_c_text_view);
        optionDTextView = (TextView) findViewById(R.id.option_d_text_view);

        optionALinearLayout = (CardView) findViewById(R.id.option_a_linear_layout);
        optionALinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionALinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                optionBLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionCLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionDLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                answ = Data.ANSWER_A;
            }
        });
        optionBLinearLayout = (CardView) findViewById(R.id.option_b_linear_layout);
        optionBLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionALinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionBLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                optionCLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionDLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                answ = Data.ANSWER_B;
            }
        });
        optionCLinearLayout = (CardView) findViewById(R.id.option_c_linear_layout);
        optionCLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionALinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionBLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionCLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                optionDLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                answ = Data.ANSWER_C;
            }
        });
        optionDLinearLayout = (CardView) findViewById(R.id.option_d_linear_layout);
        optionDLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionALinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionBLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionCLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                optionDLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                answ = Data.ANSWER_D;
            }
        });
        timerRecordInit = (TextView) findViewById(R.id.timer_record_init);
    }

    public void loadQuestion() {

        if (counter <= numberQuestion) {
            int i = answeredQuestions();
            answPosition = i;
            questionTextView.setText(questions.get(i).getQuestion());
            optionATextView.setText(questions.get(i).getOptionA());
            optionBTextView.setText(questions.get(i).getOptionB());
            optionCTextView.setText(questions.get(i).getOptionC());
            optionDTextView.setText(questions.get(i).getOptionD());

            optionALinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            optionBLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            optionCLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            optionDLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            counter++;
            startClock();
        } else {
            customHandler.removeCallbacks(updateTimerThread);
            Intent intentRecord = new Intent(this, RecordActivity.class);
            intentRecord.putExtra(MenuActivity.TYPE, bundle.getString(MenuActivity.TYPE));
            intentRecord.putExtra(LevelActivity.NUMBER_LEVEL, bundle.getInt(LevelActivity.NUMBER_LEVEL));
            intentRecord.putExtra(LevelActivity.NUMBER_OF_QUESTIONS, bundle.getInt(LevelActivity.NUMBER_OF_QUESTIONS));
            intentRecord.putExtra(RECORD, Integer.toString(numberAnsw).concat("/").concat(Integer.toString(numberQuestion)));
            intentRecord.putExtra(RECORD_VALUE, numberAnsw);
            intentRecord.putExtra(TIMER_RECORD_STRING, stringTime);
            startActivity(intentRecord);
            finish();
        }
    }

    public int randomPositionQuestion() {
        return (int) (Math.random() * 20);
    }

    private int answeredQuestions() {
        int i;
        while (true) {
            i = randomPositionQuestion();
            if (!answeredQuestions.contains(Integer.valueOf(i))) {
                answeredQuestions.add(i);
                return i;
            }
        }

    }


    private Runnable updateTimerThread = new Runnable() {

        int secs;
        int mins;
        int milliseconds;

        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            Log.d("updateTimerThread", Long.toString(timeInMilliseconds));
            secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 1000);
            stringTime = Integer.toString(mins).concat(":").concat(String.format("%02d", secs)).concat(":").concat(String.format("%03d", milliseconds));
            timerRecordInit.setText(
                    stringTime
            );

            customHandler.postDelayed(this, 0);
        }
    };

    private void pauseClock() {
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
    }

    private void startClock() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    public void pausePlay(View v) {
        questionCardView.setVisibility(CardView.GONE);
        pauseClock();
        onCreateDialog().show();
    }

    public Dialog onCreateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_dialog_pause)
                .setMessage(R.string.questions_pause_play)
                .setPositiveButton(R.string.pause_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QuestionActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.pause_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        questionCardView.setVisibility(CardView.VISIBLE);
                        startClock();

                    }
                });
        return builder.create();
    }

}
