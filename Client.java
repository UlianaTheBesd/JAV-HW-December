/*
Класс Client.
(Клиент в ресторане).
- имеет уникальный id заказа (Atomic, чтобы не было ошибок ввиду конкуренции) : idOfOrder.
- каждые 2-7 секунд делает заказ : queue.put(order).
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Thread {

    private static final AtomicInteger idOfOrder = new AtomicInteger(0);

    private final Random random = new Random();
    private final BlockingQueue<Order> queue;
    private final List<TypeOfDish> menu;


    public Client(BlockingQueue<Order> queue, List<TypeOfDish> menu) {
        this.queue = queue;
        this.menu = menu;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(2000 + random.nextInt(5000)); // 2–7 сек

                TypeOfDish dish = menu.get(random.nextInt(menu.size()));

                int id = idOfOrder.incrementAndGet();
                Order order = new Order(id, dish);

                queue.put(order);
                System.out.println("Client ordered: " + dish.getName() + ". Id: " + order.getId() + ".");
            }
        } catch (InterruptedException e) {
            interrupt();
        }
    }
}
