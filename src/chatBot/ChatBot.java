package chatBot;
import java.util.Scanner;

public class ChatBot {
    private static final String botNameString = "Alister";
    private static final int botYear = 2021;
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        process();
    }

    private static void process() {
        System.out.format("Hello! My name is %s.\nI was created in %d.\n", botNameString, botYear);
        System.out.println("Please, remind me your name.");

        String nick = scan.nextLine();
        System.out.format("What a great name you have, %s!\n", nick);

        System.out.println("Let me guess your age.\n" +
                "Enter remainders of dividing your age by 3, 5 and 7.");

        int age = summAge(scan.nextInt(), scan.nextInt(), scan.nextInt());
        System.out.format("Your age is %d; that's a good time to start programming!\n", age);

        System.out.print("Now I can prove to you that I can count to any number you want. Enter number: ");
        int toNum = scan.nextInt();
        for (int i = 0; i < toNum + 1; i++) {
            System.out.println(i + " !");
        }

        quizProcess();
        System.out.println("Goodbye, have a nice day!");
    }

    private static int summAge(int rem3, int rem5, int rem7) {
        return (rem3 * 70 + rem5 * 21 + rem7 * 15) % 105;
    }

    private static void quizProcess() {
        int rightAnswer = 4;
        int iter = 0;

        System.out.println("Let's test your programming knowledge.\n" +
                "How differ overload from overriding?");
        System.out.println(
                ++iter + ". There is nothing as 'overload'. We use @Overcasting.\n" +
                        ++iter + ". Something bad.\n" +
                        ++iter + ". There is nothing as 'overriding'. We use @Overcasting.\n" +
                        ++iter + ". One is dynamic, other - static");

        while (true) {
            if (scan.nextInt() == rightAnswer) {
                System.out.println("Great, you right!");
                break;
            }
            System.out.println("Please, try again.");
        }
    }

}

