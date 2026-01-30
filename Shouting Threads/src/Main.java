import java.util.Scanner;
import java.util.Random;

class ExampleThread implements Runnable{
    // unique id for each thread
    private final int id;
    // number of shouts per thread
    private final int shouts;
    // list of phrases to randomly choose from
    private final String[] phrases = {
            "There is no greatest prime number.",
            "The next statement is true.",
            "The previous statement is false.",
            "Does the set of all sets contain itself?"
    };

    private final Random randomNumber = new Random();

    // constructor to store thread ids and how many times each thread should shout
    ExampleThread(int id, int shouts){
        this.id = id;
        this.shouts = shouts;
    }

    private void busyWait() {
        // choose from 3-6 cycles randomly
        int cycles = 3 + randomNumber.nextInt(4);

        // main logic for busy wait, after each shout this executes a busy wait loop for 3-6 cycles
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
            // picks random phrase to shout from the array
            String phrase = phrases[randomNumber.nextInt(phrases.length)];
            System.out.println("Shouter " + id + ": " + phrase);

            // call busy wait after each shout
            busyWait();
        }

    }
}


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // ask user for number of threads
        System.out.print("Enter the number of threads: ");
        int T = scanner.nextInt();

        // ask user for number of shouts per thread
        System.out.print("Enter the number of times each thread should shout: ");
        int S = scanner.nextInt();

        // array to store thread
        Thread[] threads = new Thread[T];

        // create and start T amount of threads
        for (int i = 0; i < T; i++){
            threads[i] = new Thread(new ExampleThread(i, S));
            threads[i].start();
        }

        // wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }
    }
}