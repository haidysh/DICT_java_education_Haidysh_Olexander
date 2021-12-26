package coffeeMachine;
import java.util.Scanner;

public class CoffeeMachine {
    private static final Scanner scan = new Scanner(System.in);
    private static final CoffeeMachineInner machine_inner = new CoffeeMachineInner();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            switch (scan.next().trim().toLowerCase()) {
                case "buy":
                    buy();
                    break;
                case "fill":
                    machine_inner.fill();
                    break;
                case "take":
                    System.out.println("I gave you " + machine_inner.take());
                    break;
                case "remaining":
                    machine_inner.printParams();
                    break;
                case "exit":
                    exit = true;
            }
        }
    }

    private static void buy() {
        try {
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back – to main menu: ");
            String out = scan.next().trim().toLowerCase();
            if (out.equals("back")) return;

            machine_inner.buy(EnumCoffeeType.values()[Integer.parseInt(out) + 1]);
            System.out.println("I have enough resources, making you a coffee!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class CoffeeMachineInner {
    private static final Scanner scan = new Scanner(System.in);

    private int water = 400;
    private int milk = 540;
    private int coffeeBeans = 110;
    private int cups = 9;
    private int money = 550;

    public int take() {
        int temp = money;
        money = 0;
        return temp;
    }

    public void buy(EnumCoffeeType type) throws Exception {
        CoffeeTypeStats stats;
        switch (type) {
            case espresso:
                stats = CoffeeTypes.espresso;
                break;
            case latte:
                stats = CoffeeTypes.latte;
                break;
            case cappuccino:
                stats = CoffeeTypes.cappuccino;
                break;
            default:
                throw new IllegalStateException("Unexpected type: " + type);
        }

        check(stats);

        cups--;
        water -= stats.WATER;
        milk -= stats.MILK;
        coffeeBeans -= stats.COFFEE_BEANS;
        money += stats.PRICE;
    }

    private void check(CoffeeTypeStats stats) throws Exception {
        if (cups < 1) {
            throw new Exception("Sorry, not enough cups!");
        }

        if (water - stats.WATER < 0) {
            throw new Exception("Sorry, not enough water!");
        }

        if (milk - stats.MILK < 0) {
            throw new Exception("Sorry, not enough milk!");
        }

        if (coffeeBeans - stats.COFFEE_BEANS < 0) {
            throw new Exception("Sorry, not enough coffee beans!");
        }
    }

    public void fill() {
        System.out.println("Write how many ml of water you want to add: ");
        water += scan.nextInt();

        System.out.println("Write how many ml of milk the coffee you want to add: ");
        milk += scan.nextInt();

        System.out.println("Write how many grams of coffee beans you want to add: ");
        coffeeBeans += scan.nextInt();

        System.out.println("Write how many disposable coffee cups you want to add: ");
        cups += scan.nextInt();
    }

    public void printParams() {
        System.out.format("\nThe coffee machine has:\n" +
                "%d of water\n" +
                "%d of milk\n" +
                "%d of coffee beans\n" +
                "%d of disposable cups\n" +
                "%d of money\n\n", water, milk, coffeeBeans, cups, money);
    }
}

enum EnumCoffeeType {
    espresso,
    latte,
    cappuccino
}

class CoffeeTypeStats {
    public final int WATER;
    public final int MILK;
    public final int COFFEE_BEANS;
    public final int PRICE;

    CoffeeTypeStats(int water, int milk, int coffeeBeans, int price) {
        WATER = water;
        MILK = milk;
        COFFEE_BEANS = coffeeBeans;
        PRICE = price;
    }
}

class CoffeeTypes {
    public static final CoffeeTypeStats espresso = new CoffeeTypeStats(250, 0, 16, 4);
    public static final CoffeeTypeStats latte = new CoffeeTypeStats(350, 75, 20, 7);
    public static final CoffeeTypeStats cappuccino = new CoffeeTypeStats(200, 100, 12, 6);

    private CoffeeTypes() {

    }
}