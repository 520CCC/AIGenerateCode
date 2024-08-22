package com.doow.rubbish;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.doow.rubbish.view.CircularProgressIndicator;

//基类
public class BaseActivity extends Activity {
    Context context;
    Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = this;

    }


    void showWaiting() {
        if (progressDialog == null) {
            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setCancelable(true);

            FrameLayout container = new FrameLayout(this);
            container.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

            CircularProgressIndicator progressIndicator = new CircularProgressIndicator(this);
            int indicatorSize = dpToPx(40);
            FrameLayout.LayoutParams indicatorParams = new FrameLayout.LayoutParams(indicatorSize, indicatorSize);
            progressIndicator.setLayoutParams(indicatorParams);

            container.addView(progressIndicator);
            progressDialog.setContentView(container);

            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams layoutParams = progressDialog.getWindow().getAttributes();
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            progressDialog.getWindow().setAttributes(layoutParams);
        }
        progressDialog.show();

    }

    void dismiss() {
        if (progressDialog != null && !isFinishing()) {
            progressDialog.dismiss();
        }
    }

    void shortTosat(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

   protected void startActivity(Class t) {
        startActivity(new Intent(context, t));

    }

    void showMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        shortTosat(msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
     protected int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

}
