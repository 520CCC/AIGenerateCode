package com.doow.rubbish;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//混淆文件生成器，增加反编译难度，生成的文件：bt-proguard.txt
public class ProguardGenerateClass {

    //字典样本
    private static List<String> SOURCE = Arrays.asList("अ","आ","इ","ई","उ","ऊ","ऋ","ऌ","ओ","औ","ए","ऐ");
    //字典行数
    private static int LENGTH = 5000;
    //输出路径
    private static final String ROOT_PATH = System.getProperty("user.dir") + "/app/";
    //输出名称
    private static final String FILE_NAME = "bt-proguard.txt";
    private static Random random = new Random();

    public static void main(String[] args) {
        List<String> unicodeList = new ArrayList(SOURCE);
        List<String> outputList = new ArrayList<String>();
        Collections.sort(unicodeList);
        File file = new File(ROOT_PATH, FILE_NAME);
        if (file.exists()) {
            System.out.println("文件已存在，删除");
            file.delete();
        } else {
            System.out.println("文件不存在");
        }

        String encoding = "UTF-8";
        int repeatCount = 0;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int i = 0;
            while (i < LENGTH) {
                String tmp = "";
                int width = random.nextInt(7) + 4;
                for (int j = 0; j < width; j++) {
                    tmp = tmp + getRandomString(unicodeList);
                }
                if (!outputList.contains(tmp)) {
                    i++;
                    outputList.add(tmp);
                    fileOutputStream.write(tmp.getBytes(encoding));
                    if (i < LENGTH) {
                        //最后一行不输入回车
                        fileOutputStream.write('\n');
                    }
                    repeatCount = 0;
                } else {
                    repeatCount++;
                    System.out.println("重复生成的字符串当前行数--->" + i + " 内容---> " + tmp);
                    if (repeatCount == 10000) {
                        System.out.println("连续重复次数超过10000次 已达到最大行数 无法继续生成");
                        break;
                    }
                }
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRandomString(List<String> list) {
        String tm;
        int s = random.nextInt(list.size());
        tm = list.get(s);
        return tm;
    }
}

