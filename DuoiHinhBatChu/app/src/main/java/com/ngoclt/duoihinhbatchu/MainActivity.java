package com.ngoclt.duoihinhbatchu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final int NUMBER_BUTTON = 16;
    public static final int NUMBER_PICSTURE = 12;

    public static final int ANSWER_TRUE = 1;
    public static final int ANSWER_FALSE = -1;
    public static final int ANSWERING = 0;

    public static final int NUMBER_TURN_RANDOM = 10;

    public static final int INCREASE_SCORE = 100;
    public static final int DECREASE_HEART = 101;
    public static final int NUMBER_HEART = 3;
    public static final int SCORE_CORRECT_INCREASE = 100;

    private Button[] mBtnAnswer;
    private Button[] mBtnQuestion;
    private String correctAnswerTest = "HOIDONG";
    private String correctAnswerToast = "HỘI ĐỒNG";
    private int[] idQuestion = new int[]{R.id.btn_question_0, R.id.btn_question_1, R.id.btn_question_2, R.id.btn_question_3, R.id.btn_question_4, R.id.btn_question_5, R.id.btn_question_6, R.id.btn_question_7, R.id.btn_question_8, R.id.btn_question_9, R.id.btn_question_10, R.id.btn_question_11, R.id.btn_question_12, R.id.btn_question_13, R.id.btn_question_14, R.id.btn_question_15};
    private int[] idAnswer = new int[]{R.id.btn_answer_0, R.id.btn_answer_1, R.id.btn_answer_2, R.id.btn_answer_3, R.id.btn_answer_4, R.id.btn_answer_5, R.id.btn_answer_6, R.id.btn_answer_7, R.id.btn_answer_8, R.id.btn_answer_9, R.id.btn_answer_10, R.id.btn_answer_11, R.id.btn_answer_12, R.id.btn_answer_13, R.id.btn_answer_14, R.id.btn_answer_15};
    private int[] drawablePicture = new int[]{R.drawable.canthiep, R.drawable.cattuong, R.drawable.chieutre, R.drawable.danhlua, R.drawable.danong, R.drawable.giandiep, R.drawable.giangmai, R.drawable.hongtam, R.drawable.nambancau, R.drawable.thattinh, R.drawable.tichphan, R.drawable.xakep};
    private String[] correctAnswer = new String[]{"CANTHIEP", "CATTUONG", "CHIEUTRE", "DANHLUA", "DANONG", "GIANDIEP", "GIANGMAI", "HONGTAM", "NAMBANCAU", "THATTINH", "TICHPHAN", "XAKEP"};
    private String[] arrayToastCorrectAnswer = new String[]{"CAN THIỆP", "CÁT TƯỜNG", "CHIẾU TRE", "ĐÁNH LỪA", "ĐÀN ÔNG", "GIÁN ĐIỆP", "GIANG MAI", "HỒNG TÂM", "NAM BÁN CẦU", "THẤT TÌNH", "TÍCH PHÂN", "XÀ KÉP"};
    private char[] arrayCharacter;
    private char[] arrayCharacterRandom;
    private int[] arraySaveIndex;
    private int sequenceButtonAnswer = 0;
    private ImageView mIVQuestion;
    private TextView mTVScore;
    private TextView mTVHeart;
    private Random random;
    private Button mBtnNext;
    private int numsHeart = NUMBER_HEART;
    private int score = 0;
    private boolean isRunning = true;
    private int checkAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnAnswer = new Button[NUMBER_BUTTON];
        mBtnQuestion = new Button[NUMBER_BUTTON];

        mIVQuestion = (ImageView) findViewById(R.id.iv_question);
        mTVScore = (TextView) findViewById(R.id.txt_score);
        mTVHeart = (TextView) findViewById(R.id.txt_heart);
        mBtnNext = (Button) findViewById(R.id.btn_next);

        for (int i = 0; i < NUMBER_BUTTON; i++) {
            mBtnAnswer[i] = (Button) findViewById(idAnswer[i]);
            mBtnAnswer[i].setOnClickListener(this);
        }

        for (int i = 0; i < NUMBER_BUTTON; i++) {
            mBtnQuestion[i] = (Button) findViewById(idQuestion[i]);
            mBtnQuestion[i].setOnClickListener(this);
        }

        mBtnNext.setOnClickListener(this);
        initThread();
        startGame();
    }

    private void initThread() {
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {
                if (checkAnswer == ANSWER_TRUE) {
                    score += SCORE_CORRECT_INCREASE;
                    Message msg = new Message();
                    msg.what = INCREASE_SCORE;
                    msg.arg1 = score;
                    msg.setTarget(mHandler);
                    msg.sendToTarget();
                    checkAnswer = ANSWERING;
                }
                if (checkAnswer == ANSWER_FALSE) {
                    numsHeart--;
                    Message msg = new Message();
                    msg.what = DECREASE_HEART;
                    msg.arg1 = numsHeart;
                    msg.setTarget(mHandler);
                    msg.sendToTarget();
                    checkAnswer = ANSWERING;
                }
                if (numsHeart == 0) {
                    isRunning = false;
                    Intent intent = new Intent(MainActivity.this, MenuGame.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INCREASE_SCORE:
                    mTVScore.setText(score + "");
                    break;
                case DECREASE_HEART:
                    mTVHeart.setText(numsHeart + "");
                    break;
                default:
                    break;
            }

        }
    };

    private void startGame() {
        hideButton();
        subStringCorrectAnswer();
        setTextForQuestionButton();
    }

    private void clearButton() {
        for (int i = 0; i < arrayCharacter.length; i++) {
            mBtnAnswer[i].setText("");
        }
    }

    private void setTextForQuestionButton() {
        for (int i = 0; i < NUMBER_TURN_RANDOM; i++) {
            int a = random.nextInt(arrayCharacter.length);
            int b = random.nextInt(arrayCharacterRandom.length);
            char temp;
            temp = arrayCharacter[a];
            arrayCharacter[a] = arrayCharacterRandom[b];
            arrayCharacterRandom[b] = temp;
        }

        for (int i = 0; i < arrayCharacter.length; i++) {
            mBtnQuestion[i].setText(arrayCharacter[i] + "");
        }

        int j = 0;
        for (int i = arrayCharacter.length; i < NUMBER_BUTTON; i++) {
            mBtnQuestion[i].setText(arrayCharacterRandom[j] + "");
            j++;
        }
    }


    private void subStringCorrectAnswer() {
        for (int i = 0; i < correctAnswerTest.length(); i++) {
            arrayCharacter[i] = correctAnswerTest.charAt(i);
        }
        for (int i = 0; i < arrayCharacterRandom.length; i++) {
            int randInt = randNumber(65, 90);
            char randChar = (char) randInt;
            arrayCharacterRandom[i] = randChar;
        }
    }

    private int randNumber(int min, int max) {
        random = new Random();
        int range = max - min + 1;
        int randNum = min + random.nextInt(range);
        return randNum;
    }

    private void hideButton() {
        arrayCharacter = new char[correctAnswerTest.length()];
        arrayCharacterRandom = new char[NUMBER_BUTTON - correctAnswerTest.length()];
        arraySaveIndex = new int[correctAnswerTest.length()];

        for (int i = 0; i < NUMBER_BUTTON; i++) {
            mBtnAnswer[i].setVisibility(View.VISIBLE);

        }

        for (int i = arrayCharacter.length; i < NUMBER_BUTTON; i++) {
            mBtnAnswer[i].setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < idQuestion.length; i++) {
            if (v.getId() == idQuestion[i] && mBtnQuestion[i].getText().equals("") == false && sequenceButtonAnswer < arrayCharacter.length) {
                mBtnAnswer[sequenceButtonAnswer].setText(mBtnQuestion[i].getText());
                mBtnQuestion[i].setText("");
                arraySaveIndex[sequenceButtonAnswer] = i;
                break;
            }
            if (v.getId() == idQuestion[i] && mBtnQuestion[i].getText().equals("")) {
                sequenceButtonAnswer--;
            }
        }

        for (int i = 0; i < idAnswer.length; i++) {
            if (v.getId() == idAnswer[i] && mBtnAnswer[i].getText().equals("") == false) {
                mBtnQuestion[arraySaveIndex[i]].setText(mBtnAnswer[i].getText());
                mBtnAnswer[i].setText("");
                sequenceButtonAnswer -= 2;
                break;
            }
            if (v.getId() == idAnswer[i] && mBtnAnswer[i].getText().equals("")) {
                sequenceButtonAnswer--;
            }
        }

        if (v.getId() == R.id.btn_next) {
            changeImage();
            mBtnNext.setVisibility(View.INVISIBLE);
            sequenceButtonAnswer--;
        }

        if (sequenceButtonAnswer <= arrayCharacter.length) {
            sequenceButtonAnswer++;
        }

        if (sequenceButtonAnswer == arrayCharacter.length) {
            checkAnswer();
        }
    }

    private void changeImage() {
        random = new Random();
        int indexPicsRandom = random.nextInt(NUMBER_PICSTURE);
        mIVQuestion.setImageResource(drawablePicture[indexPicsRandom]);
        correctAnswerTest = correctAnswer[indexPicsRandom];
        correctAnswerToast = arrayToastCorrectAnswer[indexPicsRandom];
        startNewTurn();
    }

    private void startNewTurn() {
        sequenceButtonAnswer = 0;
        clearButton();
        startGame();
    }

    private void checkAnswer() {
        String answer = "";
        for (int i = 0; i < arrayCharacter.length; i++) {
            answer += mBtnAnswer[i].getText();
        }
        if (answer.equals(correctAnswerTest)) {
            Toast.makeText(MainActivity.this, "CHÍNH XÁC, ĐÁP ÁN LÀ : " + correctAnswerToast, Toast.LENGTH_SHORT).show();
            checkAnswer = ANSWER_TRUE;
        } else {
            Toast.makeText(MainActivity.this, "SAI BÉT , ĐÁP ÁN LÀ : " + correctAnswerToast, Toast.LENGTH_SHORT).show();
            checkAnswer = ANSWER_FALSE;
        }
        mBtnNext.setVisibility(View.VISIBLE);
    }
}
