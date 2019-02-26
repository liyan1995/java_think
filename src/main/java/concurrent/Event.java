package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * create by YanL on 2019/1/25
 */
public class Event {
    public static void main(String[] args) {
        EventChecker.test(new EventGenerator(), 50);
    }
}

abstract class IntGenerator {
    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}

class EventChecker extends Thread {
    private IntGenerator intGenerator;
    private final int id;

    private EventChecker(IntGenerator intGenerator, int id) {
        this.intGenerator = intGenerator;
        this.id = id;
    }

    @Override
    public void run() {
        while (!intGenerator.isCanceled()) {
            int val = intGenerator.next();
            if (val % 2 == 1) {
                System.out.println(val + " not event!!!!");
                intGenerator.cancel();
            }
        }
    }

    public static void test(IntGenerator intGenerator, int count) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            executorService.execute(new EventChecker(intGenerator, i));
        }
        executorService.shutdown();
    }

    @Override
    public String toString() {
        return this.getName() + id;
    }
}

class EventGenerator extends IntGenerator {
    private int value = 0;
    // 对象自动带锁
    // synchronized为对象锁关键字，所有带synchronized的方法都会锁定对象的属性/资源（非static属性，这是Class的属性）
    public synchronized int next() {
        value++;
        Thread.yield();
        value++;
        return value;
    }
}
