package ru.navisale.tests;

import org.junit.jupiter.api.Test;
import ru.navisale.model.Product;
import ru.navisale.pages.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AddToCartUiTest extends TestBase {

    @Test
    void addThreeProductsToCart_withChecks() {

        // 1 товар (qty > 1)
        Product p1 = new MainPage()
                .openPage()
                .openCatalogFromHeader()
                .openMenCatalog()
                .openCategory("Футболки и майки")
                .openFirstProduct()
                .readProductCard();

        CartPage cart = new ProductPage()
                .addToCart()
                .setQuantity(2)
                .goToCart();

        // 2 товар
        Product p2 = new MainPage()
                .openPage()
                .openCatalogFromHeader()
                .openMenCatalog()
                .openCategory("Брюки")
                .openFirstProduct()
                .readProductCard();

        cart = new ProductPage()
                .addToCart()
                .setQuantity(1)
                .goToCart();

        // 3 товар
        Product p3 = new MainPage()
                .openPage()
                .openCatalogFromHeader()
                .openMenCatalog()
                .openCategory("Костюмы")
                .openFirstProduct()
                .readProductCard();

        cart = new ProductPage()
                .addToCart()
                .setQuantity(1)
                .goToCart();

        //
        // ПРОВЕРКИ ПО ТЗ
        //

        //DEBUG
        //System.out.println("p1: name=" + p1.name + ", size=" + p1.size + ", color=" + p1.color + ", priceRub=" + p1.priceRub);
        //System.out.println("p2: name=" + p2.name + ", size=" + p2.size + ", color=" + p2.color + ", priceRub=" + p2.priceRub);
        //System.out.println("p3: name=" + p3.name + ", size=" + p3.size + ", color=" + p3.color + ", priceRub=" + p3.priceRub);

        // (1) Название и параметры (размер/цвет, если доступны)
        var cartItem1 = cart.findItemByName(p1.name);
        //System.out.println("cartItem1 qty=" + cartItem1.getQty() + ", priceRub=" + cartItem1.getPriceRub());
        assertThat(cartItem1.getTitleLine()).contains(p1.name);
        if (p1.color != null && !p1.color.isBlank()) assertThat(cartItem1.getColor()).contains(p1.color);
        if (p1.size  != null && !p1.size.isBlank())  assertThat(cartItem1.getSize()).contains(p1.size);

        var cartItem2 = cart.findItemByName(p2.name);
        //System.out.println("cartItem2 qty=" + cartItem2.getQty() + ", priceRub=" + cartItem2.getPriceRub());
        assertThat(cartItem2.getTitleLine()).contains(p2.name);
        if (p2.color != null && !p2.color.isBlank()) assertThat(cartItem2.getColor()).contains(p2.color);
        if (p2.size  != null && !p2.size.isBlank())  assertThat(cartItem2.getSize()).contains(p2.size);

        var cartItem3 = cart.findItemByName(p3.name);
        //System.out.println("cartItem3 qty=" + cartItem3.getQty() + ", priceRub=" + cartItem3.getPriceRub());
        assertThat(cartItem3.getTitleLine()).contains(p3.name);
        if (p3.color != null && !p3.color.isBlank()) assertThat(cartItem3.getColor()).contains(p3.color);
        if (p3.size  != null && !p3.size.isBlank())  assertThat(cartItem3.getSize()).contains(p3.size);

        // (2) Общее количество выбранного товара в корзине = итоговому кол-ву в sidebar
        int sumQty = cart.sumSelectedItemsQuantities();
        int sidebarQty = cart.getSidebarTotalItems();
        //System.out.println("sumQty=" + sumQty + ", sidebarQty=" + sidebarQty);
        assertThat(sidebarQty).isEqualTo(sumQty);

        // (3) Для каждого товара сравнить ценник (страница товара vs корзина)
        assertThat(cartItem1.getPriceRub()).isEqualTo(p1.priceRub * cartItem1.getQty());
        assertThat(cartItem2.getPriceRub()).isEqualTo(p2.priceRub * cartItem2.getQty());
        assertThat(cartItem3.getPriceRub()).isEqualTo(p3.priceRub * cartItem3.getQty());
    }
}
