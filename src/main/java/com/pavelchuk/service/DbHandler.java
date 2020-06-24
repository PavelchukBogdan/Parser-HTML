package com.pavelchuk.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class DbHandler {


    private final static Logger logger = Logger.getLogger(DbHandler.class);

    private static final String PROP_FILE_NAME = "db.properties";


    /**
     * Вставка стастических данных в таблицу
     * @param url адрес рассматриваемоей веб стрницы
     * @param wordCountStatistic Карта со статистикой слово->количество
     */

    public void insertWordStatistic(String url, Map<String, Long> wordCountStatistic) {

        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
        if(inputStream != null){
            try{
                properties.load(inputStream);
            } catch(IOException e){
                logger.error(String.format("Ошибка при загрузке файла %s", PROP_FILE_NAME));
            }
        }
        else{
            logger.error(String.format("Файл %s не найден", PROP_FILE_NAME));
        }

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String urlDb = properties.getProperty("url");

        try(Connection connection = DriverManager.getConnection(urlDb, user, password)){

            logger.info("Подключение к базе данных выполнено успешно");
            insertIntoSiteTable(connection, url);
            insertIntoWordStatisticTable(connection, wordCountStatistic, url);
            logger.info("Таблицы обновлены успешно");
        } catch(SQLException e){
            logger.error("Ошибка подключения к базе данных");
        }
    }

    /**
     * Вставка в таблицу  site
     * @param connection содениние с базой данных
     * @param url Адрес рассматриваемой веб страницы
     */

    private void insertIntoSiteTable(Connection connection, String url) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO site(url) values (?)");
            statement.setString(1, url);
            statement.executeUpdate();
        } catch(SQLException e){
            logger.error("Не удалось обновить таблицу site");
        }
    }

    /**
     * Вставка в табилцу word_statistic
     * @param connection Соедениние с базой данных
     * @param wordCountStatistic  Карта со статистикой слово->количество
     * @param url Адрес рассматриваемой веб страницы
     */

    private void insertIntoWordStatisticTable(Connection connection, Map<String, Long> wordCountStatistic, String url) {

        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO word_statistic(word, count, site_url) values (?, ?, ?) ");
            for(Map.Entry<String, Long> entry : wordCountStatistic.entrySet()){
                preparedStatement.setString(1, entry.getKey());
                preparedStatement.setLong(2, entry.getValue());
                preparedStatement.setString(3, url);
                preparedStatement.executeUpdate();
            }
        } catch(SQLException e){
            logger.error("Не удалось обновить таблицу word_statistic");
        }


    }


}

