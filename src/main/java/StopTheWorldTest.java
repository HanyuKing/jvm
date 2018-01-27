import java.util.HashMap;

public class StopTheWorldTest {

    public static class MyThread extends Thread {
        HashMap map = new HashMap();

        @Override
        public void run() {
            try {
                int count = 0;
                while (true) {
                    count++;
                    if(map.size() * 512 / 1024 / 1024 >= 625) {
                        System.out.println("clear map " + count + " " + map.size());
                        map.clear();
                    }
                    for(int i = 0; i < 100; i++) {
                        byte[] b1 = new byte[512];
                        map.put(System.nanoTime(), b1);
                    }
                    Thread.sleep(1);
                }
            } catch (Exception e) {

            } finally {

            }
        }
    }

    public static void main(String[] ars) {
        MyThread t = new MyThread();
        PrintThread p = new PrintThread();
        t.start();
        p.start();
    }

    public static class PrintThread extends Thread {
        public static final long startTime = System.currentTimeMillis();

        @Override
        public void run() {
            try {
                while (true) {
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(100);
                }
            } catch (Exception e) {

            }
        }
    }
}
