package com.sixelasavir.www.boombiblico;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    private boolean onBackQuestion = false;

    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;

    private MediaPlayer mPlayerCorrect;
    private MediaPlayer mPlayerIncorrect;

    private CardView valueCardView;
    private RelativeLayout questionRelativeLayout;
    private TextView valueTextView;
    private TextView questionTextView;
    private TextView optionATextView;
    private TextView optionBTextView;
    private TextView optionCTextView;
    private TextView optionDTextView;
    private TextView timerRecordInit;
    private TextView countAnswerTextView;


    private LinearLayout optionALinearLayout;
    private LinearLayout optionBLinearLayout;
    private LinearLayout optionCLinearLayout;
    private LinearLayout optionDLinearLayout;


    private Button afterButton;

    private Bundle bundle;

    private MenuItem opt_pause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        bundle = getIntent().getExtras();
        mPlayerCorrect = MediaPlayer.create(getApplicationContext(), R.raw.sound_correct);
        mPlayerCorrect.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayerIncorrect = MediaPlayer.create(getApplicationContext(), R.raw.sound_incorrect);
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
                numberQuestion = 20;
                break;
        }

        this.loadResources();
        questionRelativeLayout.setVisibility(CardView.GONE);

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
                opt_pause.setVisible(true);
                questionRelativeLayout.setVisibility(CardView.VISIBLE);
                onBackQuestion = true;
                loadQuestion();
            }
        };
        countDownTimer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_question, menu);
        opt_pause = menu.findItem(R.id.ic_pause);
        opt_pause.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_pause:{
                pausePlay();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (onBackQuestion) {
            pausePlay();
        }else {
            super.onBackPressed();
        }
    }

    public void loadResources() {
        valueCardView = (CardView) findViewById(R.id.value_card_view);
        valueTextView = (TextView) findViewById(R.id.value_text_view);
        questionRelativeLayout = (RelativeLayout) findViewById(R.id.question_card_view);
        questionTextView = (TextView) findViewById(R.id.question_text_view);
        countAnswerTextView = (TextView) findViewById(R.id.count_answer);
        afterButton = (Button) findViewById(R.id.after_button);
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
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void run() {
                                        pauseClock();
                                        afterButton.setEnabled(false);
                                        opt_pause.setEnabled(false);
                                        if (answ == Data.ANSWER_A && answ != answCorrect) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                optionALinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_incorrect));
                                            }else {
                                                optionALinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_incorrect));
                                            }
                                            mPlayerIncorrect.start();
                                        } else if (answ == Data.ANSWER_B && answ != answCorrect) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                optionBLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_incorrect));
                                            }else {
                                                optionBLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_incorrect));
                                            }    mPlayerIncorrect.start();
                                        } else if (answ == Data.ANSWER_C && answ != answCorrect) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                optionCLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_incorrect));
                                            }else {
                                                optionCLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_incorrect));
                                            }
                                            mPlayerIncorrect.start();
                                        } else if (answ == Data.ANSWER_D && answ != answCorrect) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                optionDLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_incorrect));
                                            }else {
                                                optionDLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_incorrect));
                                            }
                                            mPlayerIncorrect.start();
                                        } else if (answ == answCorrect) {
                                            mPlayerCorrect.start();
                                            numberAnsw++;
                                        } else if (answ == "") {
                                            mPlayerIncorrect.start();
                                        }

                                        switch (answCorrect) {
                                            case Data.ANSWER_A:
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                    optionALinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_correct));
                                                }else {
                                                    optionALinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_correct));
                                                }
                                                break;
                                            case Data.ANSWER_B:
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                    optionBLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_correct));
                                                }else {
                                                    optionBLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_correct));
                                                }
                                                break;
                                            case Data.ANSWER_C:
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                    optionCLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_correct));
                                                }else {
                                                    optionCLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_correct));
                                                }
                                                break;
                                            case Data.ANSWER_D:
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                    optionDLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_correct));
                                                }else {
                                                    optionDLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_correct));
                                                }
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
                                        opt_pause.setEnabled(true);
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

        optionALinearLayout = (LinearLayout) findViewById(R.id.option_a_linear_layout);
        optionALinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    optionALinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_selectec));
                    optionBLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionCLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionDLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                }else {
                    optionALinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_selectec));
                    optionBLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionCLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionDLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                }

                answ = Data.ANSWER_A;
            }
        });
        optionBLinearLayout = (LinearLayout) findViewById(R.id.option_b_linear_layout);
        optionBLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    optionALinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionBLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_selectec));
                    optionCLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionDLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                }else {
                    optionALinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionBLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_selectec));
                    optionCLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionDLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                }
                answ = Data.ANSWER_B;
            }
        });
        optionCLinearLayout = (LinearLayout) findViewById(R.id.option_c_linear_layout);
        optionCLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    optionALinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionBLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionCLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_selectec));
                    optionDLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                }else {
                    optionALinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionBLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionCLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_selectec));
                    optionDLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                }
                answ = Data.ANSWER_C;
            }
        });
        optionDLinearLayout = (LinearLayout) findViewById(R.id.option_d_linear_layout);
        optionDLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    optionALinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionBLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionCLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionDLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_selectec));
                }else {
                    optionALinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionBLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionCLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                    optionDLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_selectec));
                }
                answ = Data.ANSWER_D;
            }
        });
        timerRecordInit = (TextView) findViewById(R.id.timer_record_init);
    }

    public void loadQuestion() {

        if (counter <= numberQuestion) {
            int i = answeredQuestions();
            answPosition = i;
            countAnswerTextView.setText(counter+"/"+numberQuestion);
            questionTextView.setText(questions.get(i).getQuestion());
            optionATextView.setText(questions.get(i).getOptionA());
            optionBTextView.setText(questions.get(i).getOptionB());
            optionCTextView.setText(questions.get(i).getOptionC());
            optionDTextView.setText(questions.get(i).getOptionD());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                optionALinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                optionBLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                optionCLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                optionDLinearLayout.setBackground(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
            }else {
                optionALinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                optionBLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                optionCLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
                optionDLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.borders_answer_no_selectec));
            }
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
        return (int) (Math.random() * questions.size());
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

    private void pausePlay() {
        questionRelativeLayout.setVisibility(CardView.GONE);
        pauseClock();
        opt_pause.setVisible(false);
        onCreateDialog().show();
    }

    public Dialog onCreateDialog() {
         final AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle(R.string.title_dialog_pause)
                .setCancelable(false)
                .setMessage(R.string.questions_pause_play)
                .setPositiveButton(R.string.pause_continue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        opt_pause.setVisible(true);
                        questionRelativeLayout.setVisibility(CardView.VISIBLE);
                        startClock();
                    }
                })
                .setNegativeButton(R.string.pause_exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        Dialog aDialog = builder.create();
        aDialog.setCanceledOnTouchOutside(false);
        return aDialog;
    }

}
