/*
Класс Waiter.
(Официант).
- ждёт результат от CompletableFuture : order.getF().get().
- "отдаёт" назад клиенту.
 */

import java.util.concurrent.*;

public class Waiter extends Thread {

    private final BlockingQueue<Order> queue;
    private final Kitchen kitchen;

    public Waiter(String name,
                  BlockingQueue<Order> queue,
                  Kitchen kitchen) {
        super(name);
        this.queue = queue;
        this.kitchen = kitchen;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = queue.take();

                System.out.println("Waiter " + getName() + " took order. Id: " + order.getId() + ".");

                kitchen.getMealDone(order);

                String result = order.getF().get();

                Thread.sleep(1); // Для правильного вывода - чтобы Waiter не "брал заказ" раньше.
                System.out.println("Waiter " + getName() + " brought: " + result + ". Id: " + order.getId() + ".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) { // XXX
            e.printStackTrace(); // Печатаем весь лог.
        }
    }
}
