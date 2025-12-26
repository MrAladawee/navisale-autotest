package ru.navisale.tests;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class SmokeTest extends TestBase {

    @Test
    void openMainPage() {
        open("/");
    }
}
