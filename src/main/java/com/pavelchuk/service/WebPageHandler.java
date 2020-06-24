package com.pavelchuk.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.*;

public class WebPageHandler {


    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 \" +\n" +
            "                                                        \"(KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private final static Logger logger = Logger.getLogger(WebPageHandler.class);

    /**
     * Скачать указанную веб страницу
     * @param url Адрес веб страницы
     * @param fileName Имя файла для сохранения
     * @return Файл, куда была сохранена страница
     */


    public File downloadPage(String url, String fileName) {
        HttpGet req = new HttpGet(url);
        req.setHeader("User-Agent", DEFAULT_USER_AGENT);
        CloseableHttpClient client = HttpClients.createDefault();
        File file = new File(fileName);
        if(file.exists()) {
            logger.warn(String.format("Файл %s уже существует. Програама перезапишет этот файл", fileName));
        }
        try{
            CloseableHttpResponse response = client.execute(req);
            InputStream inputStream = response.getEntity().getContent();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                logger.info(String.format("Начало записи в файл %s", fileName));
                String s;
                while((s = br.readLine()) != null){
                    bw.write(s);
                }
                bw.flush();
                logger.info(String.format("Окончание записи в файл %s", fileName));
            }
        } catch(IOException e){
            logger.error("Ошибка при попытке записи в файл");
        }
        return file;
    }

}
