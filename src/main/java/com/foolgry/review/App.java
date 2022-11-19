package com.foolgry.review;

import org.pegdown.PegDownProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(App::generateHtml, 0, 3, TimeUnit.HOURS);
    }

    private static final PegDownProcessor pegDownProcessor = new PegDownProcessor();
    private static final String lineSeparator = "<br>";
    public static final String SOURCE_DIR = "/Users/wangzhi/Nutstore Files/foolgry-note/foolgry";
    public static final String HTML_FILE_DIR = "/Users/wangzhi/Documents/everynote/";
    public static final int PICK_NUMBER = 10;

    public static void generateHtml() {
        try {
            // init file dir
            File dir = new File(SOURCE_DIR);

            List<File> fileList = new ArrayList<>(600);
            // read all md file to fileList
            readMdFile(fileList, dir);

            // picker ten file and merge to one
            String sb = pickerFileAndGetContent(fileList);

            // transfer md to html
            String html = mdToHtml(sb);

            // write a new file
            writeToFile(html);
        } catch (Exception e) {
            System.out.println("error"+e.getMessage());
        }
    }

    private static void writeToFile(String html) throws IOException {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        System.out.println(date);
        Files.write(Paths.get(HTML_FILE_DIR + date + ".html"),
                html.getBytes(StandardCharsets.UTF_8));
    }

    private static String pickerFileAndGetContent(List<File> fileList) throws IOException {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        int count = PICK_NUMBER;
        while (count > 0) {
            int pos = rand.nextInt(fileList.size());
            File file = fileList.get(pos);
            System.out.println(file.getName());
            sb.append(file.getName());
            sb.append(lineSeparator);
            String content = getFileContent(file);
            sb.append(content);
            sb.append(lineSeparator);
            sb.append("-----------------------------------------------------------");
            sb.append(lineSeparator);
            count--;
        }
        return sb.toString();
    }

    private static String getFileContent(File file) throws IOException {
        return String.join(lineSeparator, Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
    }

    private static String mdToHtml(String md) {
        return pegDownProcessor.markdownToHtml(md);
    }

    private static void readMdFile(List<File> fileList, File file) {
        if (file.isFile()) {
            if (file.getName().endsWith(".md")) {
                fileList.add(file);
            }
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            for (File file1 : files) {
                readMdFile(fileList, file1);
            }
        }
    }
}
