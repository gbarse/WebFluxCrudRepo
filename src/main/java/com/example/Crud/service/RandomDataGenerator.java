package com.example.Crud.service;

import java.util.Random;


public class RandomDataGenerator {

    private static final String[] FIRST_NAMES = {"Gor", "Bob", "Lall", "Vasya", "Torgom", "Sara"};
    private static final String[] LAST_NAMES = {"Dadada", "BBBA", "Johnson", "Xcho", "Ku", "Varzgen"};
    private static final Random RANDOM = new Random();

    public static String getRandomFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
    }

    public static String getRandomLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }
}
