package com.doow.rubbish;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//垃圾代码生成器，包含drawable,layout,string,manifests,java

//不用xml,比如layout,，全部用java代码实现的项目
//使用方法：选中这个文件，右键编译，编译好后通过project方式打开就可以找到生成的代码，不是通过android目录结构

public class RubbishCodeDoctor {
    static String packageBase = "api.slotgems.top";  //生成java类根包名

    static int packageCount = 20; //生成包数量 30
    static int activityCountPerPackage = 20;  //每个包下生成Activity类数量  30

    static String[] views = new String[]{"TextView", "EditText", "Button", "ImageView", "ProgressBar",
            "SeekBar", "CheckBox", "RadioButton", "ToggleButton", "Spinner", "ListView",
            "GridView", "ScrollView", "DatePicker", "TimePicker", "RatingBar", "Switch", "TableLayout",
            "FrameLayout", "LinearLayout", "RelativeLayout", "ViewFlipper"};
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

    //普通ShapeDrawable自定义切图
    static String generateShapeDrawableClass(String packageName) throws IOException {
        String className = String.valueOf(abc[random.nextInt(abc.length)]).toUpperCase() + generateName();

        String content = "package  " + packageBase + ";\n\n"
                + "import android.graphics.Canvas;\n"
                + "import android.graphics.Color;\n"

                + "import android.graphics.ColorFilter;\n"
                + "import android.graphics.Paint;\n"
                + "import android.graphics.PixelFormat;\n"
                + "import android.graphics.Rect;\n"
                + "import android.graphics.drawable.ShapeDrawable;\n"
                + "import android.graphics.Path;\n\n"
                + "public class " + className + " extends ShapeDrawable  {\n"
                + "private final Path path;\n"
                + "private final Paint paint;\n"
                + "private Rect bounds;\n"
                + "\n";

        content = content + "\n      public " + className + "() {\n"
                + "    paint = new Paint();\n" +
                "        paint.setColor(Color.WHITE);\n" +
                "        paint.setStyle(Paint.Style.FILL);\n" +
                "        paint.setAntiAlias(true);\n" +
                "\n" +
                "        path = new Path();";
        int count = random.nextInt(300) + 20;
        for (int i = 0; i < count; i++) {
            content = content +
                    "path.lineTo(" + random.nextInt(100) + 5 + "f," + random.nextInt(100) + 5 + "." + random.nextInt(100) + 5 + "f);";
        }
        content = content + "path.close();\n}";
        content = content + "\n" +
                "    @Override\n" +
                "    public void draw(Canvas canvas) {\n" +
                "        if (bounds == null) {\n" +
                "            return;\n" +
                "        }\n" +
                "\n" +
                "        float scaleX = bounds.width() / 24f;\n" +
                "        float scaleY = bounds.height() / 24f;\n" +
                "        canvas.save();\n" +
                "        canvas.scale(scaleX, scaleY);\n" +
                "        canvas.drawPath(path, paint);\n" +
                "        canvas.restore();\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    @Override\n" +
                "    public int getOpacity() {\n" +
                "        return PixelFormat.UNKNOWN;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void onBoundsChange(Rect bounds) {\n" +
                "        super.onBoundsChange(bounds);\n" +
                "        this.bounds = bounds;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int getIntrinsicWidth() {\n" +
                "        return 24;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int getIntrinsicHeight() {\n" +
                "        return 24; \n" +
                "    }\n" + "}\n";


        int t = random.nextInt(20) + 1000;
        content = content.replace("24", t + "");
        File drawableFile = new File(packageName + "/" + className + ".java");
        writeStringToFile(drawableFile, content);
        return className;
    }

    static void generateActivity(String packageName, String activityPreName) throws IOException {
        String className = String.valueOf(abc[random.nextInt(abc.length)]).toUpperCase() + activityPreName + "Activity";
        String stringsxml = generateName().toLowerCase(); //生成strings字符串
        stringList.add(stringsxml);

        String shapeDrawable = generateShapeDrawableClass(packageName);
        String content = "package  " + packageBase + "." + activityPreName + ";\n"
                + "\n" + "import android.app.Activity;\n"
                + "import android.os.Bundle;\n"
                + "import java.lang.Exception;\n"
                + "import java.lang.Override;\n"
                + "import android.graphics.Color;\n"
                + "import java.lang.RuntimeException;\n"
                + "import java.lang.String;\n"
                + "import android.view.View;\n" +
                "import android.view.ViewGroup;\n" +
                "import android.widget.ViewFlipper;\n" +
                "import android.widget.LinearLayout;\n" +
                "import android.widget.FrameLayout;\n" +
                "import android.widget.RelativeLayout;\n" +
                "import android.widget.TableLayout;\n" +
                "import android.widget.Switch;\n" +
                "import android.widget.RatingBar;\n" +
                "import android.widget.TimePicker;\n" +
                "import android.widget.DatePicker;\n" +
                "import android.widget.ScrollView;\n" +
                "import android.widget.GridView;\n" +
                "import android.widget.ListView;\n" +
                "import android.widget.Spinner;\n" +
                "import android.widget.ToggleButton;\n" +
                "import android.widget.RadioButton;\n" +
                "import android.widget.CheckBox;\n" +
                "import android.widget.SeekBar;\n" +
                "import android.widget.ProgressBar;\n" +
                "import android.widget.ImageView;\n" +
                "import android.widget.Button;\n" +
                "import android.widget.EditText;\n" +
                "import android.widget.TextView;\n"
                + "import java.lang.System;\n"
                + "import android.widget.Toast;\n"
                + "import java.util.Date;\n"
                + "\n" + "public class " + className + " extends Activity {\n";

        int count = random.nextInt(300) + 20;
        int index = random.nextInt(19);
        List<String> fields = new ArrayList<>();
        List<String> widgets = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String widget = views[random.nextInt(views.length)];
            widgets.add(widget);
            String name = generateName();
            fields.add(name);
            content = content + "         " + widget + "     " + name + ";\n";
        }


        content = content + "    @Override\n"
                + "    protected void onCreate(Bundle savedInstanceState) {\n"
                + "        super.onCreate(savedInstanceState);\n"
                + "   LinearLayout rootView = new LinearLayout(this);\n" +
                "setContentView(rootView);\n" +
                "        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));\n" +
                "        rootView.setOrientation(LinearLayout.VERTICAL);\n" +
                "        rootView.setBackgroundColor(Color.parseColor(\"" + generateColor() + "\"));";


        for (int i = 0; i < count; i++) {
            String widget = widgets.get(i);
            String name = fields.get(i);
            content = content + "    " + name + "= new " + widget + "(this);\n" +
                    "         " + name + ".setLayoutParams(new LinearLayout.LayoutParams(" + random.nextInt(2000) + "," + random.nextInt(2000) + "));\n" +
                    "         " + name + ".setPadding(" + random.nextInt(200) + "," + random.nextInt(200) + "," + random.nextInt(200) + "," + random.nextInt(200) + ");\n" +
                    "         " + " rootView.addView(" + name + ");\n";
            if (i == index) {
                content = content + name + ".setBackground(new " + shapeDrawable + "());\n";
            } else {
                content = content + name + ".setBackgroundColor(Color.parseColor(\"" + generateColor() + "\"));\n";
            }
            content = content + "\n";
        }
        content = content + "}\n}\n"; //类末尾

        File drawableFile = new File(packageName + "/" + className + ".java");
        writeStringToFile(drawableFile, content);
        String actpath = packageName.replace(packageBase + "/", "").replace("/", ".") + "." + className;
        activityList.add(actpath);
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