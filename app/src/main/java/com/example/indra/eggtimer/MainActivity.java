package com.example.indra.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timeSeekBar;
    TextView timeTextView;
    CountDownTimer countDownTimer;
    Button btnCount;
    int second;
    MediaPlayer mediaPlayer ;


    boolean isCountingDown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        initTimeSeekBar();

        SetTimeSeekBarOnChange();

        mediaPlayer = MediaPlayer.create(this, R.raw.horn);

    }

    public void startCountDown(View view) {

        if (!isCountingDown) {
            countDownTimer = new CountDownTimer(second * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    timeTextView.setText(secondsToTimeString((int) l / 1000));
                    second = (int) Math.ceil(l / 1000);
                }

                @Override
                public void onFinish() {
                    mediaPlayer.start();
                    timeTextView.setText("00:00");
                    btnCount.setText("START");
                }
            }.start();
            isCountingDown = true;
            btnCount.setText("STOP");

        } else {
            countDownTimer.cancel();
            isCountingDown = false;
            btnCount.setText("START");
        }


    }

    private void initUI() {
        timeSeekBar = findViewById(R.id.timeSeekBar);
        timeTextView = findViewById(R.id.timerTextView);
        btnCount = findViewById(R.id.button);
    }

    private void initTimeSeekBar() {
        timeSeekBar.setMax(1200);

        timeSeekBar.incrementProgressBy(15);
    }

    private void SetTimeSeekBarOnChange() {
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = i / 15;
                i = i * 15;

                second = i;

                timeTextView.setText(secondsToTimeString(second));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String secondsToTimeString(int second) {

        int hours = second / 3600;
        int remainder = second - hours * 3600;

        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        return String.format("%02d", mins) + ':' + String.format("%02d", secs);
    }
}
