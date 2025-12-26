package ru.navisale.model;

import java.util.Objects;

public class Product {
    public final String name;
    public final String color; // может быть null/пусто, если не нашли на странице
    public final String size;  // может быть null/пусто
    public final int priceRub; // цена в рублях (3146)

    public Product(String name, String color, String size, int priceRub) {
        this.name = name;
        this.color = color;
        this.size = size;
        this.priceRub = priceRub;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", priceRub=" + priceRub +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return priceRub == product.priceRub
                && Objects.equals(name, product.name)
                && Objects.equals(color, product.color)
                && Objects.equals(size, product.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, size, priceRub);
    }
}
