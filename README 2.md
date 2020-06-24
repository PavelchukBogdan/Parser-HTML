##Описание

Консольное приложение для парсинга веб страниц,
с сохранением статистики по количеству слов.

##Запуск приложения

$ mvn liquibase:update - инициализация базы данных
$ mvn clean compile assembly:single - создание jar файла
$ java -jar target/pars-1.0-SNAPSHOT-jar-with-dependencies.jar - запуск приложения

P.S Приложение нуждается в СУБД Postgres





