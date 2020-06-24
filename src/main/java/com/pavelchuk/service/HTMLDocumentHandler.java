package com.pavelchuk.service;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HTMLDocumentHandler {

    private final static String CHARSET_NAME = "UTF-8";
    private final static String REGEX = "[,\\.\\s!?;:\\[\\]\\(\\)\\n\\r\\t\"']";
    private final static Logger logger = Logger.getLogger(HTMLDocumentHandler.class);


    /**
     * Парсинг текста и запись в карту слово->количество
     * @param inputFile html-файл
     * @return Карта слово->количество
     */

    public Map<String, Long> parseText(File inputFile) {
        Document document = null;
        try{
            document = Jsoup.parse(inputFile, CHARSET_NAME);
        } catch(IOException e){
            logger.error(String.format("Ошибка при чтении файла %s", inputFile.getName()));
        }
        Element body = document.body();
        if(body != null){
            Map<String, Long> wordCountMap = new HashMap<>();
            String bodyInnerText = body.ownText().toLowerCase();
            addWords(bodyInnerText, wordCountMap);
            Elements bodyInnerElements = body.children();
            bodyInnerElements.iterator().forEachRemaining(element -> {
                String innerText = element.text().toLowerCase();
                addWords(innerText, wordCountMap);
            });
            return wordCountMap;
        }
        throw new IllegalArgumentException("Данная HTML старница не содержит тега body");
    }

    /**
     * Добавление в карту статистики по словам
     * @param text рассматриваемый текст
     * @param wordCountMap карта со статистикой
     */
    private void addWords(String text, Map<String, Long> wordCountMap){
        List<String> words = Stream.of(text.split(REGEX))
                .filter(str -> !str.isEmpty()).collect(Collectors.toList());
        words.forEach(word -> {
            if(wordCountMap.containsKey(word)){
                wordCountMap.put(word, wordCountMap.get(word) + 1);
            }
            else{
                wordCountMap.put(word, 1L);
            }
        });
    }


}
