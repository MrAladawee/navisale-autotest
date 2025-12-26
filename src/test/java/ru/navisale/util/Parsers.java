package ru.navisale.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parsers {

    private static final Pattern FIRST_NUMBER = Pattern.compile("(\\d[\\d\\s\\u00A0]*)");

    // "9 854 ₽ 4 927 ₽" -> 9854 (берем первую цену)
    public static int parseRubPrice(String text) {
        if (text == null) return 0;
        String digits = text
                .replace("\u00A0", " ")
                .replaceAll("[^0-9]", ""); // оставляем только цифры
        if (digits.isBlank()) return 0;
        return Integer.parseInt(digits);
    }

    public static int parseTotalItems(String text) {
        if (text == null) return 0;

        var m1 = Pattern.compile("(\\d+)\\s*шт").matcher(text);
        if (m1.find()) return Integer.parseInt(m1.group(1));

        var m2 = Pattern.compile("(\\d+)\\s*товар").matcher(text);
        if (m2.find()) return Integer.parseInt(m2.group(1));

        String digits = text.replaceAll("[^0-9]", " ").trim();
        if (digits.isBlank()) return 0;
        return Integer.parseInt(digits.split("\\s+")[0]);
    }
}