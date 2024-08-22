package com.doow.rubbish.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGameView extends View {
    private static final int GRID_SIZE = 30; // 游戏格子大小
    private static final int NUM_ROWS = 20; // 行数
    private static final int NUM_COLS = 10; // 列数
    private static final int INITIAL_SNAKE_LENGTH = 5; // 初始蛇长度

    private List<Point> snake; // 蛇的坐标
    private Point food; // 食物的坐标
    private Direction direction = Direction.RIGHT; // 蛇移动方向
    private boolean isGameOver = false;

    private Paint snakePaint, foodPaint, backgroundPaint;

    //贪吃蛇
    public SnakeGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        snakePaint = new Paint();
        snakePaint.setColor(Color.GREEN);

        foodPaint = new Paint();
        foodPaint.setColor(Color.RED);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLACK);

        snake = new ArrayList<>();
        resetGame();
    }

    private void resetGame() {
        snake.clear();
        snake.add(new Point(NUM_COLS / 2, NUM_ROWS / 2));

        placeFood();

        isGameOver = false;
        direction = Direction.RIGHT;
        startGameLoop();
    }

    private void placeFood() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(NUM_COLS);
            y = random.nextInt(NUM_ROWS);
        } while (isSnakeCell(x, y));

        food = new Point(x, y);
    }

    private boolean isSnakeCell(int x, int y) {
        for (Point point : snake) {
            if (point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }

    private void startGameLoop() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isGameOver) {
                    moveSnake();
                    invalidate();
                    handler.postDelayed(this, 200); // 游戏速度控制
                }
            }
        }, 200);
    }

    private void moveSnake() {
        Point newHead = getNextHead();
        if (isValidMove(newHead)) {
            snake.add(0, newHead);

            // 检查是否吃到食物
            if (newHead.equals(food)) {
                placeFood();
            } else {
                snake.remove(snake.size() - 1);
            }
        } else {
            isGameOver = true;
        }
    }

    private Point getNextHead() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);
        switch (direction) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }
        return newHead;
    }

    private boolean isValidMove(Point newHead) {
        if (newHead.x < 0 || newHead.x >= NUM_COLS || newHead.y < 0 || newHead.y >= NUM_ROWS) {
            return false; // 撞墙
        }

        for (Point point : snake) {
            if (point.equals(newHead)) {
                return false; // 撞到自己
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        // 绘制食物
        canvas.drawRect(food.x * GRID_SIZE, food.y * GRID_SIZE,
                (food.x + 1) * GRID_SIZE, (food.y + 1) * GRID_SIZE, foodPaint);

        // 绘制蛇
        for (Point point : snake) {
            canvas.drawRect(point.x * GRID_SIZE, point.y * GRID_SIZE,
                    (point.x + 1) * GRID_SIZE, (point.y + 1) * GRID_SIZE, snakePaint);
        }

        if (isGameOver) {
            // 游戏结束时显示文字提示
            Paint gameOverPaint = new Paint();
            gameOverPaint.setColor(Color.WHITE);
            gameOverPaint.setTextSize(100);
            gameOverPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Game Over", getWidth() / 2f, getHeight() / 2f, gameOverPaint);
        }
    }

    public void changeDirection(Direction newDirection) {
        if (!isOppositeDirection(newDirection)) {
            direction = newDirection;
        }
    }

    private boolean isOppositeDirection(Direction newDirection) {
        return (direction == Direction.UP && newDirection == Direction.DOWN) ||
                (direction == Direction.DOWN && newDirection == Direction.UP) ||
                (direction == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (direction == Direction.RIGHT && newDirection == Direction.LEFT);
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
