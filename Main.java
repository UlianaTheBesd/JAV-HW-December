/*
Класс Main.
- создание меню, которое отдаётся Client : List<TypeOfDish> menu.
- создание (BlockingQueue) очереди заказов (взаимодействие Waiter + Kitchen) : BlockingQueue<Order> queueOfOrders.
 */

import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<Order> queueOfOrders = new LinkedBlockingQueue<>();

        List<TypeOfDish> menu = List.of(
                new TypeOfDish("SALAD", 2000),
                new TypeOfDish("BAKLAVA", 1000),
                new TypeOfDish("COFFEE", 1500),
                new TypeOfDish("BEEFSTEAK", 4000)
        );

        int shefsCount = 3; // HOW MANY SHEFS ARE THERE?
        Kitchen kitchen = new Kitchen(shefsCount);

        int howManyWaiters = 2; // HOW MANY WAITERS ARE THERE?
        List<Waiter> waiters = new ArrayList<>();

        int howManyClients = 10; // HOW MANY CLIENTS ARE THERE?
        List<Client> clients = new ArrayList<>();

        try {
            for (int i = 0; i < howManyWaiters; i++) {
                Waiter w = new Waiter(("WAITER NUM " + Integer.toString(i+1)), queueOfOrders, kitchen);
                waiters.add(w);
                w.start();
            }

            for (int i = 0; i < howManyClients; i++) {
                Client c = new Client(queueOfOrders, menu);
                clients.add(c);
                c.start();
            }

            Thread.sleep(15_000); // WORKING TIME OF A RESTAURANT.

        } catch (InterruptedException e) {
            System.out.println("ERROR: Main thread interrupted.");
            Thread.currentThread().interrupt();

        } finally {
            for (Client c : clients) {
                c.interrupt();
            }

            for (Waiter w : waiters) {
                w.interrupt();
            }

            kitchen.shutdown();

            System.out.println("Restaurant stopped working.");

            try {
                kitchen.awaitTermination();
            } catch (Exception e) {
                System.out.println("ERROR: Interrupted while waiting for kitchen.");
                Thread.currentThread().interrupt();
            }

            System.out.println("Restaurant is closed!");
        }
    }
}
