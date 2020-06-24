package com.pavelchuk.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class WebPageHandlerTest {

    private static final String URL = "https://www.simbirsoft.com/";
    private static final String FILE_NAME = "test.html";

    @Test
    void downloadPage() {
        WebPageHandler webPageHandler = new WebPageHandler();

        File file = webPageHandler.downloadPage(URL,FILE_NAME);
        assertTrue(file.exists());
    }

    @AfterAll
    static void  deleteFile(){
        File file = new File(FILE_NAME);
        file.delete();

    }
}