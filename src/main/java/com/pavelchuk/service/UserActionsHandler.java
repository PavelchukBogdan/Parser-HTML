package com.pavelchuk.service;

import java.util.Scanner;

public class UserActionsHandler {

    private static final String EXIT_COMMAND = "EXIT";


    public void printWelcome() {
        System.out.printf("Введите адрес страницы или %S, если хотите выйти\n", EXIT_COMMAND);
    }


    /**
     * Ввод url пользователем
     * @return Введенный адрес веб страницы
     */
    public String acceptUrl() {
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        if(url.equals(EXIT_COMMAND)){
            System.out.println("До свидания!");
            System.exit(0);
        }
        return url;
    }

    /**
     * Ввод пользователем имени файла для сохранения
     * @return Имя файла для сохранения
     */
    public String acceptFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название файла для сохранения html страницы");
        String fileName = scanner.nextLine();
        return fileName;
    }

}
