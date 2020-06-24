package com.pavelchuk;


import com.pavelchuk.service.DbHandler;
import com.pavelchuk.service.HTMLDocumentHandler;
import com.pavelchuk.service.UserActionsHandler;
import com.pavelchuk.service.WebPageHandler;

import java.io.File;
import java.util.Map;

public class Application {


    public static void main(String[] args)  {
        UserActionsHandler userActionsHandler = new UserActionsHandler();
        WebPageHandler webPageHandler = new WebPageHandler();
        HTMLDocumentHandler htmlDocumentHandler = new HTMLDocumentHandler();
        DbHandler dbHandler = new DbHandler();
        while(true) {
            userActionsHandler.printWelcome();
            String url = userActionsHandler.acceptUrl();
            String fileName = userActionsHandler.acceptFileName();
            File htmlFile = webPageHandler.downloadPage(url, fileName);
            Map<String, Long> woordCountMap = htmlDocumentHandler.parseText(htmlFile);
            dbHandler.insertWordStatistic(url, woordCountMap);

        }
    }

}








