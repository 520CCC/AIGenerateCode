package com.doow.rubbish.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.doow.rubbish.R;


public class CircularProgressIndicator extends View {
     Drawable vectorDrawable;
     Matrix rotationMatrix;
     float rotationDegree = 0f;
     final float rotationSpeed = 5f;


    public CircularProgressIndicator(Context context) {
        super(context);
        init();
    }

    public CircularProgressIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        vectorDrawable = VectorDrawableCompat.create(getResources(), R.drawable.loading, null);
        rotationMatrix = new Matrix();
        post(rotationRunnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        canvas.save();
        rotationMatrix.setRotate(rotationDegree, centerX, centerY);
        canvas.concat(rotationMatrix);
        vectorDrawable.setBounds(0, 0, getWidth(), getHeight());
        vectorDrawable.draw(canvas);
        canvas.restore();
    }

    private final Runnable rotationRunnable = new Runnable() {
        @Override
        public void run() {
            rotationDegree += rotationSpeed;
            rotationDegree %= 360; // Keep rotationDegree within 0-359 degrees

            invalidate();
            postDelayed(this, 16); // Update every 16ms (approximately 60 frames per second)
        }
    };

    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

}

