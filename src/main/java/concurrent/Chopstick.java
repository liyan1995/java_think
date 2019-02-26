package concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * create by YanL on 2019/2/14
 */
public class Chopstick {
    private boolean taken = false;

    public synchronized void take() throws InterruptedException {
        while (taken) {
            wait();
        }
        taken = true;
    }

    public synchronized void drop() {
        taken = false;
        notifyAll();
    }

    public static void main(String[] args) throws InterruptedException {
        int ponder=1;
        int size=5;
        ExecutorService executorService= Executors.newCachedThreadPool();
        Chopstick[] chopsticks=new Chopstick[size];
        for(int i=0;i<size;i++){
            chopsticks[i]=new Chopstick();
        }
        for(int i=0;i<size;i++){
            executorService.execute(new Philosopher(chopsticks[i],chopsticks[(i+1)%size],i,ponder));
        }
        TimeUnit.MILLISECONDS.sleep(5);
        executorService.shutdown();
    }
}

class Philosopher implements Runnable {

    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random random = new Random();

    private void pause() throws InterruptedException {
        if (ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(random.nextInt(ponderFactor * 10));
    }

    public Philosopher(Chopstick left, Chopstick right, int id, int ponderFactor) {
        this.left = left;
        this.right = right;
        this.id = id;
        this.ponderFactor = ponderFactor;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(this + " " + "thinking");
                pause();
                System.out.println(this + " " + "grabbing left");
                left.take();
                System.out.println(this + " " + "grabbing right");
                right.take();
                System.out.println(this + " " + "eating");
                pause();
                left.drop();
                right.drop();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + id;
    }
}
