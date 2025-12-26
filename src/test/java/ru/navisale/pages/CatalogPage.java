package ru.navisale.pages;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CatalogPage {

    public CatalogPage openMenCatalog() {
        $$("a, span, button")
                .findBy(text("Мужчинам"))
                .shouldBe(visible)
                .click();

        // якорь: мы точно на странице "Мужская одежда"
        $("h1").shouldHave(text("Мужская"));

        return this;
    }

    public CatalogPage openCategory(String categoryName) {
        $$("a.rubrics-catalog__sub-link")
                .findBy(text(categoryName))
                .shouldBe(visible)
                .click();

        return this;
    }

    public ProductPage openFirstProduct() {
        $$("a")
                .findBy(attributeMatching("href", ".*/product/.*"))
                .shouldBe(visible)
                .scrollIntoView(true)
                .click();

        return new ProductPage();
    }

}
