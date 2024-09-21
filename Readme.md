# markdown文本批量匹配替换：正则模式

需求：obsidian文档的图片路径大都是![[Paste image 时间戳]]格式，在标准的markdown编辑器中是无法识别的

我现在有
- 图片路径为![[Paste image 时间戳]]格式的markdown文本
- 以及图片名为Paste image 时间戳.png的图片
- vscode编辑器

我需要将所有文本中的形如![[Paste image 时间戳]]转换为标准的markdown语法：`![pngName](image/regex/pngName)`，那么可以这样做
正则识别：
```shell
!\[\[(.*?)\]\]
```
正则替换：
```shell
![$1](image/regex/$1)
```
我遇到的问题是：vscode不支持图片名字中带空格

解决方案如下：
# 步骤1： 首先将所有的图片名中的空格替换为短横线-
bat脚本
```shell
@echo off
setlocal enabledelayedexpansion

REM 进入unused-images文件夹
cd unused-images

REM 遍历文件夹中的所有文件
for %%f in (*.*) do (
    set "filename=%%f"
    REM 替换文件名中的空格为短横线
    set "newfilename=!filename: =-!"
    REM 如果文件名有变化，则重命名
    if not "!filename!"=="!newfilename!" (
        ren "%%f" "!newfilename!"
        echo Renamed "%%f" to "!newfilename!"
    )
)

pause

```
# 步骤2（关键）：执行[MarkdownFileProcessor.java](src%2Fmain%2Fjava%2Forg%2Flyflexi%2Ftxtreplace%2FMarkdownFileProcessor.java)
自动识别文本中的![[Paste image 时间戳]]，全部替换为![[Paste-image-时间戳]]

# 步骤3：正则替换
正则识别：
```shell
!\[\[(.*?)\]\]
```
正则替换：
```shell
![$1](image/regex/$1)
```

# 步骤4：在md文件的当前目录下新建附件文件夹
将图片放进image/regex中，vscode就可识别

# 步骤5：clear unused images
最后一步使用我写的另一个工具完成：markdown工程中无用图片清理工具
> https://github.com/lyflexi/clear-unused-images-in-markdowns
