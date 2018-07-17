package libs.mjn.testscaletouchlistener;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import libs.mjn.scaletouchlistener.ScaleTouchListener;

public class MainActivity extends AppCompatActivity {

    CardView cv_dog;
    TextView tv_submit;
    FloatingActionButton fab_share, fab_heart, fab_dislike;
    AppCompatSeekBar sb_duration, sb_scale, sb_alpha;
    TextView tv_durationValue, tv_scaleValue, tv_alphaValue;
    ScaleTouchListener listener_dog, listener_submit, listener_share, listener_heart, listener_dislike;
    ScaleTouchListener.Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews(){
        cv_dog = (CardView) findViewById(R.id.cv_dog);

        tv_submit = (TextView) findViewById(R.id.tv_submit);
        setTypeFaceRegular(tv_submit);
        fab_share = (FloatingActionButton) findViewById(R.id.fab_share);
        fab_heart = (FloatingActionButton) findViewById(R.id.fab_heart);
        fab_dislike = (FloatingActionButton) findViewById(R.id.fab_dislike);

        sb_duration = (AppCompatSeekBar) findViewById(R.id.sb_duration);
        sb_duration.setMax(1000);
        tv_durationValue = (TextView) findViewById(R.id.tv_durationValue);
        sb_duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_durationValue.setText("Value: "+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                config.setDuration(seekBar.getProgress());
                updateListeners(config);
            }
        });

        sb_scale = (AppCompatSeekBar) findViewById(R.id.sb_scale);
        sb_scale.setMax(100);
        tv_scaleValue = (TextView) findViewById(R.id.tv_scaleValue);
        sb_scale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_scaleValue.setText("Value: "+(progress*1.0f/100)+"f");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                config.setScaleDown(seekBar.getProgress()*1.0f/100);
                updateListeners(config);
            }
        });

        sb_alpha = (AppCompatSeekBar) findViewById(R.id.sb_alpha);
        sb_alpha.setMax(100);
        tv_alphaValue = (TextView) findViewById(R.id.tv_alphaValue);
        sb_alpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_alphaValue.setText("Value: "+(seekBar.getProgress()*1.0f/100)+"f");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                config.setAlpha(seekBar.getProgress()*1.0f/100);
                updateListeners(config);
            }
        });

        config = new ScaleTouchListener.Config(100,0.9f,0.4f);
        listener_dog = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Dog Clicked",Toast.LENGTH_SHORT).show();
            }
        };
        listener_submit = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Submit Clicked",Toast.LENGTH_SHORT).show();
            }
        };
        listener_share = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Share Clicked",Toast.LENGTH_SHORT).show();
            }
        };
        listener_heart = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Heart Clicked",Toast.LENGTH_SHORT).show();
            }
        };
        listener_dislike = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Dislike Clicked",Toast.LENGTH_SHORT).show();
            }
        };
        sb_duration.setProgress(config.getDuration());
        sb_scale.setProgress((int) (config.getScaleDown()*100));
        sb_alpha.setProgress((int) (config.getAlpha()*100));

        cv_dog.setOnTouchListener(listener_dog);
        tv_submit.setOnTouchListener(listener_submit);
        fab_share.setOnTouchListener(listener_share);
        fab_heart.setOnTouchListener(listener_heart);
        fab_dislike.setOnTouchListener(listener_dislike);
    }

    private void updateListeners(ScaleTouchListener.Config config){
        listener_dog.setConfig(config);
        listener_submit.setConfig(config);
        listener_share.setConfig(config);
        listener_heart.setConfig(config);
        listener_dislike.setConfig(config);
    }

    private void setTypeFaceRegular(TextView tv){
        tv.setTypeface(Typeface.createFromAsset(getAssets(),"Ubuntu-Regular.ttf"));
    }
}
