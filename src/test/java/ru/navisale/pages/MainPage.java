package ru.navisale.pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class MainPage {

    public MainPage openPage() {
        open("/");
        return this;
    }

    public CatalogPage openCatalogFromHeader() {
        // кнопка "Каталог"
        $(".header-rubrics-toggler")
                .shouldBe(visible)
                .click();

        // якорь: mega-menu открылось
        $("[data-selector='rubrics:layout']")
                .shouldBe(visible);

        return new CatalogPage();
    }

}
