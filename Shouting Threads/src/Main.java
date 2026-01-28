import java.util.Scanner;
import java.util.Random;

class ExampleThread implements Runnable{
    private final int id;
    private final int shouts;
    private final String[] phrases = {
            "There is no greatest prime number.",
            "The next statement is true.",
            "The previous statement is false.",
            "Does the set of all sets contain itself?"
    };

    private final Random randomNumber = new Random();
    ExampleThread(int id, int shouts){
        this.id = id;
        this.shouts = shouts;
    }

    //
    private void busyWait() {
        int cycles = 3 + randomNumber.nextInt(4);
        boolean condition = true;
        while (condition){
            Thread.yield();
            condition = false;
        }
        for (int i = 0; i < cycles; i++){
            Thread.yield();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < shouts; i++){
            String phrase = phrases[randomNumber.nextInt(phrases.length)];
            System.out.println("Shouter " + id + ": " + phrase);

            busyWait();
        }

    }
}
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of threads: ");
        int T = scanner.nextInt();


        System.out.print("Enter the number of times each thread should shout: ");
        int S = scanner.nextInt();



        Thread[] threads = new Thread[T];

        for (int i = 0; i < T; i++){
            threads[i] = new Thread(new ExampleThread(i, S));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }


        
    }
}