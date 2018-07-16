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

public abstract class ScaleTouchListener implements View.OnTouchListener, View.OnClickListener {

    private final int DURATION = 100;
    private final float SCALE = 0.85f;
    private final float ALPHA = 0.75f;
    private long touchTime, diffTime;

    ObjectAnimator
            scaleXDownAnimator, scaleXUpAnimator,
            scaleYDownAnimator, scaleYUpAnimator,
            alphaDownAnimator, alphaUpAnimator;
    AnimatorSet downSet, upSet;
    AnimatorListenerAdapter finalAnimationListener;

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

    private void createAnimators(final View v) {
        alphaDownAnimator = ObjectAnimator.ofFloat(v, "alpha", config.getAlpha());
        alphaUpAnimator = ObjectAnimator.ofFloat(v, "alpha", 1.0f);
        scaleXDownAnimator = ObjectAnimator.ofFloat(v, "scaleX", config.getScaleDown());
        scaleXUpAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1.0f);
        scaleYDownAnimator = ObjectAnimator.ofFloat(v, "scaleY", config.getScaleDown());
        scaleYUpAnimator = ObjectAnimator.ofFloat(v, "scaleY", 1.0f);

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
                onClick(v);
            }
        };
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setRect(v);
                touchTime = System.currentTimeMillis();
                if (!pressed) {
                    effect(v, true);
                    pressed = true;
                    released = false;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!released) {
                    setRect(v);
                    if (!insideView(event)) {
                        effect(v, false);
                        released = true;
                        pressed = false;
                    }
                }
                return false;
            case MotionEvent.ACTION_UP:
                if (!released) {
                    setRect(v);
                    if (insideView(event)) {
                        upSet.addListener(finalAnimationListener);
                    }
                    effect(v, false);
                    released = true;
                    pressed = false;
                }
                return false;
            default: return false;
        }
    }

    private boolean insideView(MotionEvent event){
        int x= (int)event.getRawX();
        int y= (int)event.getRawY();
        return x>=getRect().left && x<=getRect().right && y<=getRect().bottom && y>=getRect().top;
    }

    private void setRect(View v) {
        if (rect == null) {
            rect = new Rect();
            v.getGlobalVisibleRect(rect);
        }
    }

    private Rect getRect() {
        return rect;
    }

    private void effect(View v, boolean press) {

        if (!createdAnimators) {
            createAnimators(v);
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
