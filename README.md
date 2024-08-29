# AIGenerateCode

---------------Android--------------中文介绍

我用同一个代码上了100个包到google play，分享一些自己实现的工具，面对google的ai机器审核，之前网上找了的都没法用

1. 集成过程操作简单，新手也能操作
2. 生成代码不但能够编译进aab和apk，还会被调用，即使开了minifyEnabled true和shrinkResources true
3. 针对纯java和xml布局分别有两套方案
4. RubbishCode： 垃圾代码生成，包含drawable,layout,string,manifests,java
5. RubbishCodeDoctor： 垃圾代码生成不用xml,比如layout,，全部用java代码实现
6. ProguardGenerateClass： 混淆文件生成，增加反编译难度
7. SecretCode：字符串加密
8. LU：国际化多国语言，不是系统方案
9. build.gradle包含批量修改文件名
10. 想加微信群的话：Doctor_Gero

   使用方法：
*    1 android studio右键RubbishCode，弹出框里选择 Run 'RubbishCode.main()',就生成了一个包含垃圾代码的项目
*    2 左上角的项目结构从android切换到Project可以找到刚生成的源码
*    3 生成哪些：java,drawable,layout,strings,AndroidManifest~~~~


---------------Android--------------English Doc
I have uploaded 100 packages to Google Play using the same code. I would like to share some tools I have implemented to deal with Google's AI machine audit. The tools I found online before could not be used.

1. The integration process is simple and can be operated by novices.
2. The generated code can not only be compiled into aab and apk, but also be called, even if minifyEnabled true and shrinkResources true are turned on.
3. There are two solutions for pure java and xml layout respectively.
4. RubbishCode: Garbage code generator, including drawable, layout, string, manifests, java.
5. RubbishCodeDoctor: Garbage code generator does not use xml, such as layout, and is implemented entirely in java code.
6. ProguardGenerateClass: Obfuscated file generator, increasing the difficulty of decompilation.
7. SecretCode: String encryption tool.
8. LU: International multi-language tool, not a system solution.
9. build.gradle includes batch modification of file names.
10. If you want to join the WeChat group: tim18158189527.

How to use:

* 1 android In the studio, right-click RubbishCode and select Run 'RubbishCode.main()' in the pop-up box to generate a project containing junk code.
* 2 Switch the project structure from android to Project in the upper left corner to find the newly generated source code.
* 3 What is generated: java, drawable, layout, strings, AndroidManifest