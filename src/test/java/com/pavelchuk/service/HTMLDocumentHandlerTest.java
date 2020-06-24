package com.pavelchuk.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HTMLDocumentHandlerTest {

    private static final String HTML_FILE_NAME = "test.html";

    @Test
    void parseText1() throws IOException {

        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Test, \"TEST\", 'tESt', test?!, parse; parse: PArSE.\n" +
                "<p>\n" +
                "    Handler? HaNdler', handlEr?\n" +
                "</p>\n" +
                "<h1>\n" +
                "    Parse\n" +
                "</h1>\n" +
                "</body>\n" +
                "</html>";
        HTMLDocumentHandler htmlDocumentHandler = new HTMLDocumentHandler();


        File file = new File(HTML_FILE_NAME);
        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(html);
            fileWriter.flush();

        }
        Map<String,Long> methodMap = htmlDocumentHandler.parseText(file);

        Map<String,Long> testMap = new HashMap<>();
        testMap.put("test",4L);
        testMap.put("parse",4L);
        testMap.put("handler",3L);

        assertTrue ( testMap.entrySet().containsAll(methodMap.entrySet())
                && methodMap.entrySet().containsAll(testMap.entrySet()));


    }


    @Test
    void parseText2() throws IOException {

        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "Parse!!!!!!!!!!!\n" +
                "<div>\n" +
                "    Parse.........'\n" +
                "    <p>\n" +
                "        handler'''\n" +
                "    </p>\n" +
                "    <h1>\n" +
                "        test handler.....!?\n" +
                "    </h1>\n" +
                "</div>\n" +
                "<p>\n" +
                "    \n" +
                "</p>\n" +
                "<h1>\n" +
                "    Parse tEsT?))))\n" +
                "</h1>\n" +
                "</body>\n" +
                "</html>";
        HTMLDocumentHandler htmlDocumentHandler = new HTMLDocumentHandler();


        File file = new File(HTML_FILE_NAME);
        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(html);
            fileWriter.flush();

        }
        Map<String,Long> methodMap = htmlDocumentHandler.parseText(file);

        Map<String,Long> testMap = new HashMap<>();
        testMap.put("test",2L);
        testMap.put("parse",3L);
        testMap.put("handler",2L);



        assertTrue ( testMap.entrySet().containsAll(methodMap.entrySet())
                && methodMap.entrySet().containsAll(testMap.entrySet()));


    }

    @AfterAll
    static void  deleteFile(){
        File file = new File(HTML_FILE_NAME);
        file.delete();

    }
}