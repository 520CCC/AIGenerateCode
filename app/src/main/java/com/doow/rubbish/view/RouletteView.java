package com.doow.rubbish.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.doow.rubbish.R;

//转盘
public class RouletteView extends View {
    private int numSections = 12;
    private Paint paint;
    private float rotationAngle = 0f;
    private float targetAngle = 0f;
    private float arrowAngle = 0f;
    private boolean spinning = false;
    private String[] prizes = {
            "奖品1", "奖品2", "奖品3", "奖品4", "奖品5", "奖品6",
            "奖品7", "奖品8", "奖品9", "奖品10", "奖品11", "奖品12"
    };
    private int[] prizeImages = {
            R.drawable.game30, R.drawable.game30, R.drawable.game30,
            R.drawable.game31, R.drawable.game31, R.drawable.game31,
            R.drawable.game32, R.drawable.game32, R.drawable.game32,
            R.drawable.game33, R.drawable.game33, R.drawable.game33
    };
    private Bitmap arrowBitmap; // 箭头图片

    public RouletteView(Context context) {
        super(context);
        init();
    }

    public RouletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        arrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_up);

        // 设置触摸事件监听器
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 当用户触摸屏幕时启动旋转动画
                if (event.getAction() == MotionEvent.ACTION_DOWN && !spinning) {
                    startSpinning();
                }
                return true;
            }
        });
    }

    private void drawArrow(Canvas canvas, float x, float y) {
        float arrowWidth = arrowBitmap.getWidth();
        float arrowHeight = arrowBitmap.getHeight();
        float left = x - arrowWidth / 2f;
        float top = y - arrowHeight;
        canvas.drawBitmap(arrowBitmap, left, top, null);
    }

    private void startSpinning() {
        spinning = true;

        // 随机选择一个奖品
        int randomIndex = (int) (Math.random() * numSections);
        // 计算旋转到奖品位置的角度
        float sectionAngle = 360f / numSections;
        float randomOffset = (float) (Math.random() * 30 - 15); // 添加随机偏移以使动画更自然
        targetAngle = randomIndex * sectionAngle + randomOffset + 720f; // 至少旋转2圈
        arrowAngle = targetAngle + 90f; // 箭头指向奖品

        // 创建旋转动画
        final ValueAnimator rotationAnimator = ValueAnimator.ofFloat(rotationAngle, targetAngle);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.setDuration(3000); // 设置旋转动画持续时间
        rotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotationAngle = (float) animation.getAnimatedValue();
                invalidate(); // 通知视图进行重绘
            }
        });

        // 在动画结束后选定奖品
        rotationAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                selectPrize(randomIndex);
            }
        });

        rotationAnimator.start();
    }

    private void selectPrize(int index) {
        spinning = false;
        // TODO: 处理获得奖品的逻辑，例如显示对话框或其他操作
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = width;
        int radius = Math.min(width, height) / 2; // 使用宽度和高度的最小值作为半径

        canvas.save(); // 保存画布状态
        canvas.rotate(rotationAngle, width / 2f, height / 2f); // 根据角度旋转画布

        float sectionAngle = 360f / numSections;
        float halfSectionAngle = sectionAngle / 2f;

        for (int i = 0; i < numSections; i++) {
            // 绘制奖品扇形
            paint.setColor(getRandomColor());
            canvas.drawArc(0, 0, width, height, i * sectionAngle, sectionAngle, true, paint);

            // 绘制奖品图片和文字
            drawPrizeItem(canvas, i * sectionAngle, halfSectionAngle, prizes[i], prizeImages[i], radius,dpToPx(24),dpToPx(24));
        }

        // 绘制箭头
        drawArrow(canvas, width / 2f, height / 2f - radius);

        canvas.restore(); // 恢复画布状态
    }

    private int getRandomColor() {
        int[] colors = {
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN,
                Color.GRAY, Color.DKGRAY, Color.LTGRAY
        };
        return colors[(int) (Math.random() * colors.length)];
    }

    private void drawPrizeItem(Canvas canvas, float startAngle, float sweepAngle, String text, int imageResource, int radius, int desiredWidth, int desiredHeight) {
        // 计算奖品项的位置
        float centerX = getWidth() / 2f;
        float centerY = getWidth() / 2f; // 使 centerY 与 centerX 相等，确保是正圆形
        float itemRadius = radius * 0.7f; // 根据半径计算奖品项的位置

// 计算奖品图片的位置
        float imageX = centerX + (float) (itemRadius * Math.cos(Math.toRadians(90 - startAngle - sweepAngle / 2f)));
        float imageY = centerY - (float) (itemRadius * Math.sin(Math.toRadians(90 - startAngle - sweepAngle / 2f)));


        // 将画布移动到奖品项的中心位置
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(startAngle + sweepAngle / 2f);

        // 绘制奖品图片
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), imageResource);

        // 计算缩放比例
        float scaleWidth = (float) desiredWidth / originalBitmap.getWidth();
        float scaleHeight = (float) desiredHeight / originalBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 创建缩放后的位图
        Bitmap scaledBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);

        float left = -scaledBitmap.getWidth() / 2f; // 计算图片左边的坐标
        float top = -scaledBitmap.getHeight() / 2f; // 计算图片顶部的坐标
        canvas.drawBitmap(scaledBitmap, left, top, null);

        // 绘制奖品文字
        paint.setColor(Color.WHITE);
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.CENTER);

        // 计算文字的居中坐标
        float textY = -itemRadius + (paint.getTextSize() / 2f); // 使文字垂直居中
        canvas.drawText(text, 0, textY, paint);

        // 恢复画布状态
        canvas.restore();
    }



    protected int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


}
