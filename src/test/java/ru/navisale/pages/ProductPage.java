package ru.navisale.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.navisale.model.Product;
import ru.navisale.util.Parsers;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ProductPage {

    private final SelenideElement title =
            $("h1").shouldBe(visible);

    private final SelenideElement priceArea =
            $("[data-selector='priceArea']").shouldBe(visible);

    public Product readProductCard() {
        String header = title.getText().replace("\u00A0", " ").trim();

        // 1) вытаскиваем параметры (если вдруг есть)
        String color = "";
        String size = "";

        var mSize = java.util.regex.Pattern
                .compile("Размер\\s*:?\\s*([^;\\n]+)", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(header);
        if (mSize.find()) size = mSize.group(1).trim();

        var mColor = java.util.regex.Pattern
                .compile("Цвет\\s*:?\\s*([^;\\n]+)", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(header);
        if (mColor.find()) color = mColor.group(1).trim();

        // 2) имя = всё до первого "Цвет/Размер/Набор"
        String name = header;
        int cut = Integer.MAX_VALUE;
        for (String marker : new String[]{"Цвет", "Размер", "Набор"}) {
            int i = indexOfIgnoreCase(header, marker);
            if (i >= 0 && i < cut) cut = i;
        }
        if (cut != Integer.MAX_VALUE) {
            name = header.substring(0, cut).trim();
        }
        //System.out.println("priceArea raw text = [" + priceArea.getText() + "]");

        int priceRub = Parsers.parseRubPrice(priceArea.getText());
        return new Product(name, color, size, priceRub);
    }

    private static int indexOfIgnoreCase(String s, String sub) {
        return s.toLowerCase().indexOf(sub.toLowerCase());
    }
    public ProductPage setQuantity(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }

        SelenideElement qtyInput =
                $$("button")
                        .findBy(text("В корзине"))
                        .parent()
                        .$("input")
                        .shouldBe(visible, enabled);

        qtyInput.click();
        qtyInput.sendKeys(Keys.CONTROL, "a");
        qtyInput.sendKeys(String.valueOf(qty));
        qtyInput.sendKeys(Keys.TAB);
        qtyInput.shouldHave(value(String.valueOf(qty)));

        return this;
    }

    public ProductPage addToCart() {
        SelenideElement addBtn =
                $("[data-selector='tw-add-to-cart-btn']")
                        .shouldBe(visible);

        addBtn.click();

        // ждём, что UI переключился в состояние "В корзине"
        $$("button")
                .findBy(text("В корзине"))
                .shouldBe(visible);

        return this;
    }
    public CartPage goToCart() {
        $$("a, button")
                .findBy(text("В корзине"))
                .shouldBe(visible)
                .click();

        return new CartPage();
    }
}
