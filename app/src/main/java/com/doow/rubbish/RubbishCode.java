package com.doow.rubbish;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//垃圾代码生成器，包含drawable,layout,string,manifests,java

//使用方法：选中这个文件，右键编译，编译好后通过project方式打开就可以找到生成的代码，不是通过android目录结构
public class RubbishCode {
    static String packageBase = "com.generate.code";  //生成java类根包名

    static int packageCount = 20; //生成包数量 30
    static int activityCountPerPackage = 20;  //每个包下生成Activity类数量  30

    static String[] views = new String[]{"TextView", "EditText", "Button", "ImageView", "ProgressBar", "SeekBar",
            "CheckBox", "RadioButton", "ToggleButton", "Spinner", "ListView", "GridView", "ScrollView", "DatePicker",
            "TimePicker", "RatingBar", "Switch", "TableLayout", "FrameLayout", "LinearLayout", "RelativeLayout",
            "ViewFlipper"};
    static Random random = new Random();
    static String rootpath;
    static char[] abc = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    static char[] color = "0123456789abcdef".toCharArray();
    static List<String> activityList = new ArrayList<>();
    static List<String> stringList = new ArrayList<>();

    static public void main(String[] args) throws IOException {
        initpath();
        activityList.clear();
        stringList.clear();

        //生成成类
        generateClasses();
        //生成资源
        generateStringsFile();

    }

    static void initpath() {
        String basepath = new String(packageBase);
        rootpath = basepath + "/" + basepath.replace(".", "/");
        File file = new File(basepath);
        if (file.exists()) {
            file.delete();
        }
    }


    static void generateClasses() throws IOException {
        for (int i = 0; i < packageCount; i++) {
            String packageName = rootpath + "/" + generateName();
            //生成Activity
            for (int j = 0; j < activityCountPerPackage; j++) {
                String activityPreName = generateName();
                generateActivity(packageName, activityPreName);
            }
        }
        //所有Activity生成完了
        generateManifest();
    }


    //普通类
    static List<String> generateClass(String packageName, String className) throws IOException {
        List<String> fields = new ArrayList<>();
        String content = "package  " + packageBase + ";\n" + "\n" + "import java.lang.Exception;\n" + "import java" +
                ".lang.RuntimeException;\n" + "import java.lang.String;\n" + "import java.lang.System;\n" + "import " +
                "java.util.Date;\n" + "\n" + "public class " + className + "  {\n";

        int t = random.nextInt(20);
        for (int i = 0; i < t; i++) {
            String name = generateName();
            fields.add(name);
            content = content + "\npublic String " + name + ";";
        }


        content = content + "\n      public " + className + "() {\n" + "        }\n}\n";

        File drawableFile = new File(packageName + "/" + className + ".java");
        writeStringToFile(drawableFile, content);
        return fields;
    }

    static void generateActivity(String packageName, String activityPreName) throws IOException {
        String className = String.valueOf(abc[random.nextInt(abc.length)]).toUpperCase() + activityPreName + "Activity";
        String layoutName = "layout_" + activityPreName;
        List<String> textIds = generateLayout(layoutName);  //生成layout

        String stringsxml = generateName().toLowerCase(); //生成strings字符串
        stringList.add(stringsxml);

        String otherclassName = String.valueOf(abc[random.nextInt(abc.length)]).toUpperCase() + generateName();
        List<String> fieldlist = generateClass(packageName, otherclassName);
        String widget = views[random.nextInt(views.length)];
        String content = "package  " + packageBase + "." + activityPreName + ";\n" + "\n" + "import android.app" +
                ".Activity;\n" + "import android.os.Bundle;\n" + "import " + packageBase + ".R;\n" + "import java" +
                ".lang.Exception;\n" + "import java.lang.Override;\n" + "import java.lang.RuntimeException;\n" +
                "import java.lang.String;\n";
        content = content + "import android.widget." + widget + ";\n";
        content = content + "import android.view.View;\n" + "import android.widget.TextView;;\n" + "import java.lang" +
                ".System;\n" + "import android.widget.Toast;\n" + "import java.util.Date;\n" + "\n" + "public class " + className + " extends Activity {\n" + "    @Override\n" + "    protected void onCreate(Bundle savedInstanceState) {\n" + "        super.onCreate(savedInstanceState);\n" + "        setContentView(R.layout." + layoutName + ");\n";

        for (int i = 0; i < textIds.size(); i++) {
            String name = generateName();
            content = content + "     View  " + name + " = findViewById(R.id." + textIds.get(i) + ");\n" + "         "
                    + name + ".setOnClickListener(new View.OnClickListener() {\n" + "            @Override\n" + "    " +
                    "        public void onClick(View v) {\n" + name + ".setVisibility(View.INVISIBLE);\n" + "       " +
                    "     }\n" + "        });\n";
        }

        String otherclassNamefeild = generateName();
        String methodName = generateName();

        content = content + "\n" + methodName + "();\n" //本类方法
                + otherclassName + "   " + otherclassNamefeild + " =     new " + otherclassName + "();\n";
        for (int i = 0; i < fieldlist.size(); i++) {
            content = content + otherclassNamefeild + "." + fieldlist.get(i) + " =\"" + generateBigValue() + "\";\n";
        }
        content = content + " Toast.makeText(" + className + ".this,getString(R.string." + stringsxml + "),Toast" +
                ".LENGTH_SHORT).show();\n" + "    }";

        //其它方法
        int bwe = random.nextInt(20) + 3;
        for (int j = 0; j < bwe; j++) {//生成方法
            String methodNamenext = generateName();

            if (j != bwe - 1) {
                content = content + "\n" + " void " + methodName + "() {" + "\n         " + methodNamenext + "();\n"
                        //调用下一个方法
                        + "}"; //方法末尾
            } else {
                String name = generateName();
                content =
                        content + "\n" + " void " + methodName + "() {\n" + widget + " " + name + "   = new " + widget + "(" + className + ".this);\n" + name + ".setVisibility(View.VISIBLE);\n" + "}"; //方法末尾
            }
            methodName = methodNamenext;

        }
        content = content + "}"; //类末尾

        File drawableFile = new File(packageName + "/" + className + ".java");
        writeStringToFile(drawableFile, content);
        String actpath = packageName.replace(packageBase + "/", "").replace("/", ".") + "." + className;
        activityList.add(actpath);
    }


    /**
     * 生成layout
     */

    static List<String> generateLayout(String layoutName) throws IOException {
        List<String> textids = new ArrayList<>();
        String drawableName = generateName().toLowerCase();
        generateDrawable(drawableName);

        String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<LinearLayout " +
                "xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" + "    android:layout_width=\"" + random.nextInt(1000) + "dp\"\n" + "    android:layout_height=\"" + random.nextInt(1000) + "dp\"\n" + "    android:orientation=\"vertical\">\n";
        int t = random.nextInt(20);

        for (int i = 0; i < t; i++) {
            String id = generateName();
            textids.add(id);
            String widget = views[random.nextInt(views.length)];
            content = content + "   <" + widget + "\n" + "        android:id=\"@+id/" + id + "\"\n" + "        " +
                    "android:layout_width=\"" + random.nextInt(1000) + "dp\"\n" + "        android:layout_height=\"" + random.nextInt(1000) + "dp\"\n" + "        android:text=\"" + generateName() + "\" \n" + "        android:src=\"@drawable/" + drawableName + "\" \n";
            if (widget.equals("LinearLayout")) {
                if (random.nextInt(10) % 2 == 0) {
                    content = content + "android:orientation=\"horizontal\"";
                } else {
                    content = content + "android:orientation=\"horizontal\"";
                }
            }

            content = content + "/>\n";
        }

        content = content + "   </LinearLayout>\n";

        File layoutFile = new File(packageBase + "/res/layout/" + layoutName + ".xml");
        writeStringToFile(layoutFile, content);

        return textids;
    }

    static void writeStringToFile(File file, String data) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    /**
     * 生成Manifest
     */
    static void generateManifest() throws IOException {
        File manifestFile = new File(packageBase + "/AndroidManifest.xml");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < activityList.size(); i++) {
            sb.append("        <activity android:name=\"" + activityList.get(i) + "\"/>\n");
        }
        writeStringToFile(manifestFile, sb.toString());

    }


    //生成名字
    static String generateName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(abc[random.nextInt(abc.length)]);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    static String generateBigValue() {
        char[] abc1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKMNLOPQRSTUVWZYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < random.nextInt(1000); i++) {
            sb.append(abc1[random.nextInt(abc1.length)]);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }


    //    生成strings.xml
    static void generateStringsFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringList.size(); i++) {
            sb.append("<string name=\"" + stringList.get(i) + "\">" + generateBigValue() + "</string>\n");
        }

        File stringFile = new File(packageBase + "/res/values/strings.xml");
        writeStringToFile(stringFile, sb.toString());
    }

    //    生成Drawable
    static void generateDrawable(String drawableName) throws IOException {
        String content = "<vector xmlns:android=\"http://schemas.android.com/apk/res/android\"\n  " + " android:width" +
                "=\"" + random.nextInt(100) + "dp\"\n" + " android:height=\"" + random.nextInt(100) + "dp\"\n" + " " +
                "android:viewportWidth=\"" + random.nextInt(100) + "\"\n" + " android:viewportHeight=\"" + random.nextInt(100) + "\"\n" + ">\n" + "     <path\n" + "  android:fillColor=\"" + generateColor() + "\"\n" + "   android:pathData=\"M";
        int t = random.nextInt(40);
        for (int i = 0; i < t; i++) {
            if (i != t - 1) {
                content = content + random.nextInt(100) + ",";
            } else {
                content = content + random.nextInt(100);
            }
        }
        content = content + "z\" />\n" + "</vector>\n" + "\n";

        File drawableFile = new File(packageBase + "/res/drawable/" + drawableName + ".xml");
        writeStringToFile(drawableFile, content);
    }

    //    生成颜色代码
    static String generateColor() {
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        for (int i = 0; i < 6; i++) {
            sb.append(color[random.nextInt(color.length)]);
        }
        return sb.toString();
    }
}