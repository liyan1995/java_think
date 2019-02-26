package concurrent;

import java.io.IOException;

/**
 * create by YanL on 2019/1/25
 */
public class ResponsiveUI extends Thread {
    private static volatile double d = 1;

    private ResponsiveUI() {
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        while (true) {
            d = d + (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) throws IOException {
        new ResponsiveUI();
        int read = System.in.read();
        System.out.println(read);
        System.out.println(d);
    }
}
