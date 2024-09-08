package org.lyflexi.txtreplace;

/**
 * @Description:
 * @Author: lyflexi
 * @project: txt-replace
 * @Date: 2024/9/8 18:08
 */
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class MarkdownFileProcessor {

    public static void main(String[] args) {
        // 当前目录
//        Path currentDir = Paths.get(".");
        Path currentDir = Paths.get("E:\\github\\vsNotes");

        try {
            // 遍历所有的markdown文件
            Files.walk(currentDir)
                    .filter(path -> path.toString().endsWith(".md")) // 只处理 .md 文件
                    .forEach(MarkdownFileProcessor::processFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 处理每一个Markdown文件
    private static void processFile(Path file) {
        try {
            // 读取文件内容
            String content = new String(Files.readAllBytes(file));

            // 正则表达式匹配 `![[attachName]]` 的形式
            Pattern pattern = Pattern.compile("\\!\\[\\[([^\\]]+)\\]\\]");
            Matcher matcher = pattern.matcher(content);

            // 替换捕获组中的空格为短横线
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                String attachName = matcher.group(1);
                String newAttachName = attachName.replace(" ", "-");
                matcher.appendReplacement(sb, "![[" + newAttachName + "]]");
            }
            matcher.appendTail(sb);

            // 将修改后的内容写回文件
            Files.write(file, sb.toString().getBytes());
            System.out.println("Processed file: " + file.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
