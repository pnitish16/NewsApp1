package com.nitish.newsapp.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.nitish.newsapp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ProportionalImageView extends AppCompatImageView {

    @IntDef({Aspect.WIDTH, Aspect.HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Aspect {
        int WIDTH = 0;
        int HEIGHT = 1;
        int AUTO = 2;
    }

    private static final String TAG = ProportionalImageView.class.getSimpleName();

    public static final int DEFAULT_RATIO = 1;

    private int aspect;
    private float aspectRatio;

    public ProportionalImageView(Context context) {
        super(context);
    }

    public ProportionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProportionalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        switch (aspect) {
            case Aspect.AUTO:
                if (height > width) {
                    if (width == 0) {
                        return;
                    }
                    aspect = Aspect.WIDTH;
                    aspectRatio = Math.round((double) height / width);
                    setMeasuredDimensionByHeight(height);
                } else {
                    if (height == 0) {
                        return;
                    }
                    aspect = Aspect.HEIGHT;
                    aspectRatio = Math.round((double) width / height);
                    setMeasuredDimensionByWidth(width);
                }
                break;
            case Aspect.WIDTH:
                setMeasuredDimensionByHeight(height);
                break;
            case Aspect.HEIGHT:
            default:
                setMeasuredDimensionByWidth(width);
                break;
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProportionalImageView);
        try {
            aspect = a.getInt(R.styleable.ProportionalImageView_ari_aspect, Aspect.HEIGHT);
            aspectRatio = a.getFloat(R.styleable.ProportionalImageView_ari_ratio, DEFAULT_RATIO);
        } finally {
            a.recycle();
        }
    }

    private void setMeasuredDimensionByWidth(int width) {
        setMeasuredDimension(width, (int) (width * aspectRatio));
    }

    private void setMeasuredDimensionByHeight(int height) {
        setMeasuredDimension((int) (height * aspectRatio), height);
    }

    public void setAspectRatio(float ratio) {
        aspectRatio = ratio;
        requestLayout();
    }

    public void setAspect(@Aspect int aspect) {
        this.aspect = aspect;
        requestLayout();
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    @Aspect
    public int getAspect() {
        return aspect;
    }
}
