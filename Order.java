/*
Класс Order.
(То, что содержит Заказ).
- номер заказа (id) : id.
- тип приготовляемого блюда (TypeOfDish) : dish.
- "флаг" для сообщения между потоками Waiter и Kitchen : CompletableFuture.
 */

import java.util.concurrent.*;

public class Order {
    private final int id;

    // "CompletableFuture" = для общения между Kitchen и Waiter.
    // f.get() - "wait" - поток ждёт complete() и блокируется ("официант стоит и ждёт").
    // f.complete(1) - "notify" - флаг, что всё готово.
    // (CompletableFuture позволяет избежать трудностей с wait/notify).

    CompletableFuture<String> f = new CompletableFuture<>();

    private final TypeOfDish dish;

    public Order(int id, TypeOfDish dish) {
        this.id = id;
        this.dish = dish;
    }

    public int getId() {
        return id;
    }

    public TypeOfDish getDish() {
        return dish;
    }

    public CompletableFuture<String> getF() {
        return f;
    }
}

