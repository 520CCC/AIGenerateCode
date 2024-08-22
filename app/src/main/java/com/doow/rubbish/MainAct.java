package com.doow.rubbish;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainAct extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建返回按钮ImageView
        TextView  textView = new TextView(this);
//        ivBack.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT
//        ));
        textView.setPadding(dpToPx(20),dpToPx(80),dpToPx(20),0);
        textView.setText("Remind:\\nGoogle Play Store Packaging Tools\n" + "1. RubbishCode: Rubbish code generator, including drawable, layout, string, manifests, java\n" + "2. RubbishCodeDoctor: Rubbish code generator does not use xml, such as layout, all implemented with java code\n" + "3. ProguardGenerateClass: Obfuscated file generator, increasing the difficulty of decompilation\n" + "4. SecretCode: String encryption tool\n" + "5. LU: Internationalization multi-language tool, not a system solution\n" + "6. build.gradle contains batch modification file names\n" + "\n" + "\n"  + "google play store上包工具\n" + "1，RubbishCode： 垃圾代码生成器，包含drawable,layout,string,manifests,java\n" + "\n" + "2，RubbishCodeDoctor： 垃圾代码生成器不用xml,比如layout,，全部用java代码实现\n" + "\n" + "3，ProguardGenerateClass： 混淆文件生成器，增加反编译难度\n" + "\n" + "4，SecretCode：字符串加密工具\n" + "\n" + "5，LU：国际化多语言工具，不是系统方案\n" + "\n" + "6,build.gradle包含批量修改文件名");
setContentView(textView);


    }
}
