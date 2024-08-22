package com.doow.rubbish.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

//迷宫
public class MazeGameView extends View {
    private int[][] maze; // 迷宫地图
    private int playerX, playerY; // 玩家的位置
    private final int MAZE_SIZE = 5; // 迷宫大小，可以根据需要调整
    private final int CELL_SIZE = 100; // 每个迷宫单元的大小，可以根据需要调整
    private final Paint wallPaint, playerPaint;

    public MazeGameView(Context context) {
        super(context);

        // 初始化迷宫地图
        maze = new int[][]{
                {0, 1, 0, 1, 0},
                {0, 0, 0, 1, 1},
                {1, 1, 0, 0, 1},
                {1, 0, 1, 0, 0},
                {1, 1, 1, 1, 0}
        };

        // 初始化玩家位置
        playerX = 0;
        playerY = 0;

        // 初始化画笔
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStyle(Paint.Style.FILL);

        playerPaint = new Paint();
        playerPaint.setColor(Color.RED);
        playerPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制迷宫地图
        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                if (maze[i][j] == 1) {
                    canvas.drawRect(j * CELL_SIZE, i * CELL_SIZE, (j + 1) * CELL_SIZE, (i + 1) * CELL_SIZE, wallPaint);
                }
            }
        }

        // 绘制玩家角色
        canvas.drawRect(playerX * CELL_SIZE, playerY * CELL_SIZE, (playerX + 1) * CELL_SIZE, (playerY + 1) * CELL_SIZE, playerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            // 根据触摸位置计算玩家移动方向
            int newX = playerX;
            int newY = playerY;

            if (x < playerX * CELL_SIZE) {
                newX = playerX - 1;
            } else if (x >= (playerX + 1) * CELL_SIZE) {
                newX = playerX + 1;
            }

            if (y < playerY * CELL_SIZE) {
                newY = playerY - 1;
            } else if (y >= (playerY + 1) * CELL_SIZE) {
                newY = playerY + 1;
            }

            // 检查是否合法移动
            if (isValidMove(newX, newY)) {
                playerX = newX;
                playerY = newY;
                invalidate(); // 重新绘制画面
            }
        }

        return true;
    }

    private boolean isValidMove(int x, int y) {
        // 检查是否在迷宫范围内，并且不是墙壁
        return x >= 0 && x < MAZE_SIZE && y >= 0 && y < MAZE_SIZE && maze[y][x] == 0;
    }
}
