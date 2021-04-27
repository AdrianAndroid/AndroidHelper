import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadDemo {

    public static void main(String[] args) {
        System.out.println("hello wolrd!");
        final ExecutorService fix = Executors.newFixedThreadPool(10);
        final ExecutorService cache = Executors.newCachedThreadPool();
        final ExecutorService single = Executors.newSingleThreadExecutor();

        fix.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });
        fix.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

}
