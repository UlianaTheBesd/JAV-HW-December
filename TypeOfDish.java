/*
Класс TypeOfDish.
(Блюдо и время на его приготовление).
*/

public class TypeOfDish {
    private final String name; // *final = const.
    private final int reqTime;

    public TypeOfDish(String name, int time) {
        this.name = name;
        this.reqTime = time;
    }

    public String getName() {
        return name;
    }

    public int getReqTime() {
        return reqTime;
    }
}
