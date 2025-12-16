/*
Класс Kitchen.
(Повара).
- управление пулом потоков поваров (shefs): ExecutorService shefs.
- отдача заказов (order) из очереди (queue) на исполнение потоками из пула : getMealDone().
- доделывание остатка заказов после "закрытия" : shutDown().
 */

import org.jetbrains.annotations.NotNull; // Для @NotNull ниже.
import java.util.concurrent.*;

public class Kitchen {

    private final ExecutorService shefs;

    public Kitchen(int shefsCount) {
        this.shefs = Executors.newFixedThreadPool(shefsCount);
    }

    public void getMealDone(@NotNull Order order) {

        // shefs = ExecutorService (пул потоков).
        // submit() метод, который отправляет задачу в пул потоков.

        shefs.submit(() -> { // Лямбда-выражение, https://metanit.com/java/tutorial/9.1.php.
            try {
                System.out.println("Shef is making a meal. Id: " + order.getId() + ".");
                Thread.sleep(order.getDish().getReqTime());
                // order.getF().complete(1); // Если я сделаю CompletableFuture<Integer>.
                order.getF().complete(order.getDish().getName());
                System.out.println("Meal is ready. Id: " + order.getId() + ".");
            } catch (InterruptedException e) {
                order.getF().completeExceptionally(e);
            }
        });
    }

    public void shutdown() {
        shefs.shutdown();
    }
    public void awaitTermination() {
        try {
            shefs.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("ERROR: Kitchen shutdown " + e);
        }
    }
}
