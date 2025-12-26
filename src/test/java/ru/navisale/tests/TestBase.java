package ru.navisale.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public abstract class TestBase {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://navisale.ru";

        Configuration.browser = "firefox";

        Configuration.browserSize = "1440x900";
        Configuration.timeout = 15_000;
        Configuration.pageLoadTimeout = 60_000;
    }
}