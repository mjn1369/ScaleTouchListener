package libs.mjn.scaletouchlistener;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.lang.ref.WeakReference;

public abstract class ScaleTouchListener implements View.OnTouchListener, View.OnClickListener {

    private final int DURATION = 100;
    private final float SCALE = 0.9f;
    private final float ALPHA = 0.4f;
    private long touchTime, diffTime;

    ObjectAnimator
            scaleXDownAnimator, scaleXUpAnimator,
            scaleYDownAnimator, scaleYUpAnimator,
            alphaDownAnimator, alphaUpAnimator;
    AnimatorSet downSet, upSet;
    AnimatorListenerAdapter finalAnimationListener;

    private WeakReference<View> mView;
    private boolean createdAnimators = false;
    private boolean pressed = false, released = true;
    private Rect rect;
    private Config config;

    public ScaleTouchListener() {
        config = new Config(DURATION, SCALE, ALPHA);
    }

    public ScaleTouchListener(Config config) {
        this.config = config;
    }

    private void createAnimators() {
        alphaDownAnimator = ObjectAnimator.ofFloat(mView.get(), "alpha", config.getAlpha());
        alphaUpAnimator = ObjectAnimator.ofFloat(mView.get(), "alpha", 1.0f);
        scaleXDownAnimator = ObjectAnimator.ofFloat(mView.get(), "scaleX", config.getScaleDown());
        scaleXUpAnimator = ObjectAnimator.ofFloat(mView.get(), "scaleX", 1.0f);
        scaleYDownAnimator = ObjectAnimator.ofFloat(mView.get(), "scaleY", config.getScaleDown());
        scaleYUpAnimator = ObjectAnimator.ofFloat(mView.get(), "scaleY", 1.0f);

        downSet = new AnimatorSet();
        downSet.setDuration(config.getDuration());
        downSet.setInterpolator(new AccelerateInterpolator());
        downSet.playTogether(alphaDownAnimator, scaleXDownAnimator, scaleYDownAnimator);

        upSet = new AnimatorSet();
        upSet.setDuration(config.getDuration());
        upSet.setInterpolator(new DecelerateInterpolator());
        upSet.playTogether(alphaUpAnimator, scaleXUpAnimator, scaleYUpAnimator);

        finalAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onClick(mView.get());
            }
        };
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mView==null)
            mView=new WeakReference<>(v);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setRect();
                touchTime = System.currentTimeMillis();
                if (!pressed) {
                    effect(true);
                    pressed = true;
                    released = false;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!released) {
                    setRect();
                    if (!insideView(event)) {
                        effect(false);
                        released = true;
                        pressed = false;
                    }
                }
                return false;
            case MotionEvent.ACTION_UP:
                if (!released) {
                    setRect();
                    if (insideView(event)) {
                        upSet.addListener(finalAnimationListener);
                    }
                    effect(false);
                    released = true;
                    pressed = false;
                }
                return false;
            case MotionEvent.ACTION_CANCEL:
                effect(false);
                released = true;
                pressed = false;
                return false;
            default: return false;
        }
    }

    private boolean insideView(MotionEvent event){
        int x= (int)event.getRawX();
        int y= (int)event.getRawY();
        return x>=getRect().left && x<=getRect().right && y<=getRect().bottom && y>=getRect().top;
    }

    private void setRect() {
        if (rect == null) {
            rect = new Rect();
            mView.get().getGlobalVisibleRect(rect);
        }
    }

    private Rect getRect() {
        return rect;
    }

    private void effect(boolean press) {

        if (!createdAnimators) {
            createAnimators();
            createdAnimators = true;
        }

        if (press) {
            if(upSet!=null)
                upSet.removeAllListeners();
            downSet.cancel();
            downSet.start();
        } else {
            diffTime = System.currentTimeMillis()-touchTime;
            if(diffTime<config.getDuration()){
                upSet.setStartDelay(config.getDuration()-diffTime);
            }
            upSet.cancel();
            upSet.start();
        }
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
        if(mView!=null)
            createAnimators();
    }

    public static class Config {
        private int duration;
        private float scaleDown;
        private float alpha;

        public Config(int duration, float scaleDown, float alpha) {
            this.duration = duration;
            this.scaleDown = scaleDown;
            this.alpha = alpha;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public float getScaleDown() {
            return scaleDown;
        }

        public void setScaleDown(float scaleDown) {
            this.scaleDown = scaleDown;
        }

        public float getAlpha() {
            return alpha;
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }
    }
}
