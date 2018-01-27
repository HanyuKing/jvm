public class ValitileTest{
    public static void main(String[] args) throws Exception {
        MyThread t = new MyThread();
        t.start();
        Thread.sleep(1000);
        t.stopMe();
        Thread.sleep(1000);
    }

    public static class MyThread extends Thread {
        private volatile boolean stop = false;

        public void stopMe() {
            stop = true;
            //System.out.println(Thread.currentThread().getId() + " stopMe: " + stop);
        }

        public void run() {
            int i = 0;
            while (!stop) {
                i++;
                //System.out.println(Thread.currentThread().getId() + " stopRun: " + stop);
            }
            System.out.println("stop thread -> count: " + i);
        }
    }
}
