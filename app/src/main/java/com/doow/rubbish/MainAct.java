package com.doow.rubbish;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainAct extends BaseActivity {
    String Name = "https://h.bettigre.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("SecretCode", Name);
        // 创建返回按钮ImageView
        ImageView ivBack = new ImageView(this);
        ivBack.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        setContentView(R.layout.layout_873757c81e7e4e97b0b7fcf501d38e1f);
        ImageView image = findViewById(R.id.image);
        image.setImageDrawable(getDrawable(R.drawable.drawable_b62545641827434ba506d8a0ae83eda6));
        LinearLayout bg = findViewById(R.id.bg);
        bg.setBackground(getDrawable(R.drawable.drawable_432dcb284eee4572abaa3b991a2873e3));

    }
}
