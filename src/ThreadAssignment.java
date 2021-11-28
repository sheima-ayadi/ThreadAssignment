public class ThreadAssignment {

    static class Counter {
        void count() {
            for (int i=350;i>0;i--) //Implement the count method
                System.out.println(i);
            System.out.println("finish!");
        }
    }

    static class MyThread extends Thread {
        private final Counter counter;

        public MyThread(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            synchronized (counter){   //Synchronized
                counter.count();
            }
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();

        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);
        
        //Manage race condition so that the counting doesn't overlap
        t1.start();
        try{
            t1.join();
        }catch(InterruptedException e ){
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        t2.start();
        try{
            t2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        //Print 'Done !' when both threads terminate
        System.out.println("Done!");
    }
}
