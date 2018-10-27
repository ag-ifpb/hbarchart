package ag.lib.hbarchart;

import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HDoubleBarChart extends LinearLayout {
    private static final String NS = "http://schemas.android.com/apk/res/android";
    private String colorBar0 = "#f7ac6a";
    private String colorBar1 = "#e67e22";
    private int defaultBarHeight = 24;
    private int progress0 = 1;
    private int progress1 = 1;
    //
    private GradientDrawable bar0;
    private GradientDrawable bar1;
    private ImageView img0;
    private ImageView img1;
    //
    private float maxValue = 0.0f;
    private float previousValue = 0.0f;
    private float currentValue = 0.0f;
    //
    private ValueAnimator animator0;
    private ValueAnimator animator1;

    private void resize0(int w){
        bar0.setSize(w, defaultBarHeight);
        img0.setImageDrawable(null);
        img0.setImageDrawable(bar0);
        progress0 = w;
    }

    private void resize1(int w){
        bar1.setSize(w, defaultBarHeight);
        img1.setImageDrawable(null);
        img1.setImageDrawable(bar1);
        progress1 = w;
    }

    private void resizeAnim0(int w){
        if (animator0.isRunning()) animator0.end();
        animator0.setIntValues(progress0, w);
        animator0.start();
    }

    private void resizeAnim1(int w){
        if (animator1.isRunning()) animator1.end();
        animator1.setIntValues(progress1, w);
        animator1.start();
    }

    private void calculate0(boolean animated){
        //
        DisplayMetrics displayMetrics = new DisplayMetrics();
         (((Activity) getContext()).getWindowManager()).
                getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        float fw = (width*70f)/100f;
        //bar0
        int w0 = 1;
        if (maxValue > 0){
            w0 = Math.round((fw*previousValue/maxValue)) + 1;
        }
        if (animated){
            resizeAnim0(w0);
        } else {
            resize0(w0);
        }
    }

    private void calculate1(boolean animated){
        //
        DisplayMetrics displayMetrics = new DisplayMetrics();
        (((Activity) getContext()).getWindowManager()).
                getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        float fw = (width*70f)/100f;
        //bar1
        int w1 = 1;
        if (maxValue > 0){
            w1 = Math.round((fw*currentValue/maxValue)) + 1;
        }
        if (animated){
            resizeAnim1(w1);
        } else {
            resize1(w1);
        }
        //
        invalidate();
        requestLayout();
    }

    private void setColors(){
        bar0.setColor(Color.parseColor(colorBar0));
        bar1.setColor(Color.parseColor(colorBar1));
    }

    private int maxBarHeight(float h){
        if (h < 0) {
            return defaultBarHeight/2;
        } else {
            return Math.round(h/2);
        }
    }

    public HDoubleBarChart(Context context) {
        super(context);
    }

    public HDoubleBarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //orientation
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.RIGHT);
        //height
        String heightStr = attrs.getAttributeValue(NS, "layout_height");
        float height = DimensionConverter.stringToDimension(heightStr, getResources().getDisplayMetrics());
        defaultBarHeight = maxBarHeight(height);
        //drawables
        bar0 = (GradientDrawable) context.getResources().getDrawable(R.drawable.bar0);
        bar1 = (GradientDrawable) context.getResources().getDrawable(R.drawable.bar1);
        //set bars sizes
        bar0.setSize(progress0, defaultBarHeight);
        bar1.setSize(progress1+10, defaultBarHeight);
        //images 0
        img0 = new ImageView(context);
        img0.setImageDrawable(bar0);
        img0.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, maxBarHeight(height)
        ));
        addView(img0);
        //images 1
        img1 = new ImageView(context);
        img1.setImageDrawable(bar1);
        img1.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, maxBarHeight(height)
        ));
        addView(img1);
        //
        animator0 = new ValueAnimator();
        animator0.setDuration(100);
        animator0.setInterpolator(new LinearInterpolator());
        animator0.setEvaluator(new IntEvaluator());
        animator0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v = (int) animation.getAnimatedValue();
                resize0(v);
            }
        });
        //
        animator1 = new ValueAnimator();
        animator1.setDuration(100);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setEvaluator(new IntEvaluator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v = (int) animation.getAnimatedValue();
                resize1(v);
            }
        });
        //
        Log.d("HDoubleBarChart-Debug", "HDoubleBarChart created");
    }

    public void setMaxValue(float value){
        if (value < 0) {
            throw new IllegalArgumentException("Max value must be greater or equal than zero");
        }
        this.maxValue = value;
        calculate0(false);
    }

    public void setPreviousValue(float value) {
        if (value < 0) {
            throw new IllegalArgumentException("Max value must be greater or equal than zero");
        }
        if (maxValue < value) {
            maxValue = value;
        }
        previousValue = value;
        calculate0(true);
    }

    public void setCurrentValue(float value){
        if (value < 0 || value > maxValue){
            throw new IllegalArgumentException("Max value must be greater or equal than zero and lower than max value");
        }
        currentValue = value;
        calculate1(true);
    }

    public void setPreviousBarColor(String color){
        colorBar0 = color;
        setColors();
    }

    public void setCurrentBarColor(String color){
        colorBar1 = color;
        setColors();
    }

}
