package ru.navisale.pages;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.navisale.util.Parsers;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CartPage {

    public CartItem findItemByName(String productName) {
        SelenideElement li = $$x("//*[@id='basket-root']//li[.//a]")
                .findBy(text(productName))
                .shouldBe(visible);

        return new CartItem(li);
    }

    // В сайдбаре это выглядит как "4 товара"
    public int getSidebarTotalItems() {
        SelenideElement total = $("#basket-root")
                .$$("*")
                .findBy(matchText("\\b\\d+\\s+товар(а|ов)?\\b"))
                .shouldBe(visible);

        return Parsers.parseTotalItems(total.getText());
    }

    // Сумма количеств только по отмеченным чекбоксом товарам
    public int sumSelectedItemsQuantities() {
        int sum = 0;

        // только реальные товары (у них есть checkbox)
        ElementsCollection productLis =
                $$x("//*[@id='basket-root']//li[.//input[@type='checkbox']]");

        for (SelenideElement li : productLis) {
            SelenideElement cb = li.$("input[type='checkbox']");
            if (cb.exists() && cb.isSelected()) {
                String v = li.$("div.quantity input")
                        .shouldBe(visible)
                        .getValue();
                sum += Integer.parseInt(v.trim());
            }
        }
        return sum;
    }

    public static class CartItem {
        private final SelenideElement root;

        public CartItem(SelenideElement root) {
            this.root = root;
        }

        public int getQty() {
            // в li есть ДВА input (desktop/mobile), берём тот, который видимый
            String v = root.$$ ("input")
                    .filterBy(visible)
                    .first()
                    .getValue(); // или getAttribute("value")

            return Integer.parseInt(v.trim());
        }

        public String getTitleLine() {
            return root.$("a").shouldBe(visible).getText().trim();
        }

        public int getPriceRub() {
            // текущая цена: control-400 + font-semibold, рядом ₽, и НЕ внутри line-through
            SelenideElement price = root.$x(
                    ".//span[contains(@class,'control-400') and contains(@class,'font-semibold') " +
                            "and not(ancestor::span[contains(@class,'line-through')]) " +
                            "and following-sibling::span[normalize-space()='₽']][1]"
            ).should(exist);

            return Parsers.parseRubPrice(price.getText());
        }

        private String getParamsLine() {
            SelenideElement params = root.$("div.body-300");
            return params.exists() ? params.getText() : "";
        }

        public String getSize() {
            var m = java.util.regex.Pattern.compile("Размер\\s*:?\\s*([^,\\n]+)").matcher(getParamsLine());
            return m.find() ? m.group(1).trim() : "";
        }

        public String getColor() {
            var m = java.util.regex.Pattern.compile("Цвет\\s*:?\\s*([^,\\n]+)").matcher(getParamsLine());
            return m.find() ? m.group(1).trim() : "";
        }

    }
}