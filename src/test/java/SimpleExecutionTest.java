import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class SimpleExecutionTest {

    private BiFunction<Integer, Integer, Integer> sum = (a, b) -> {
//        if (a > 10) throw new RuntimeException("Failed to process");
        delayProcess(1000);
        return a + b;
    };
    private BiFunction<Integer, Integer, Integer> multiply = (a, b) -> {
        delayProcess(1000);
        return a * b;
    };

    private void delayProcess(long ms) {
        String name = Thread.currentThread().getName();
        System.out.printf("Started thread %s%n", name);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread is completed %s%n", name);
    }

    @Test
    void shouldRunTaskAsynchronously() throws InterruptedException, ExecutionException {

        // ==> sum of 2 nos :
        // ==> square of sum (a+b)*(a+b)
        //==> sum of square a*a + b*b  => 2, 3
        // ==> sum of square of 3 nos a*a +b*b +c*c
        // ==> Error handling

        System.out.printf("Started main thread %s%n", Thread.currentThread().getName());

        long startTime = System.currentTimeMillis();

//        Thread thread = new Thread(() -> sum.apply(2, 3));
//        thread.start();
//        thread.join();

        ExecutorService service = Executors.newFixedThreadPool(2);
//
//        Future<Integer> submit = service.submit(() -> sum.apply(2, 3));
//        Integer result = submit.get();

//        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> sum.apply(2, 3), service);
////        completableFuture.thenAcceptAsync((x)->  System.out.printf("Final result is %s%n", x)).join();
//        completableFuture.exceptionally((x)-> 0).
//        whenComplete((x, e) -> {
//            if (Objects.nonNull(e)) {
//                System.out.printf("Failed to process %s%n", e);
//            } else {
//                System.out.printf("Final result is %s%n", x);
//            }
//        }).join();

        int result = sumOfSquare(2, 3, 4);
        System.out.printf("Final result is %s%n", result);
        long endTime = System.currentTimeMillis();
        System.out.printf("Time taken %s%n", endTime - startTime);
//
    }


    private int squareOfSum(int a, int b) throws ExecutionException, InterruptedException {
//        ExecutorService service = Executors.newFixedThreadPool(2);
//        Future<Integer> submit = service.submit(() -> sum.apply(a, b));
//        Integer s = submit.get();
//        Future<Integer> submit1 = service.submit(() -> multiply.apply(s, s));
//        Integer result = submit1.get();

        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> sum.apply(a, b));
        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> multiply.apply(a, b));
//        Integer result = cf1.thenApply((x) -> multiply.apply(x, x)).join();
//        Integer result = cf1.thenApplyAsync((x) -> multiply.apply(x, x)).join();
        Integer result = cf1.thenCompose((x) ->
                CompletableFuture.supplyAsync(() -> multiply.apply(x, x))
        ).join();

        System.out.printf("result is %s%n", result);
        return result;
    }

    private int sumOfSquare(int a, int b) throws ExecutionException, InterruptedException {

//        ExecutorService service = Executors.newFixedThreadPool(2);
//        Future<Integer> submit = service.submit(() -> multiply.apply(a, a));
//        Future<Integer> submit1 = service.submit(() -> multiply.apply(b, b));
//        Integer s = submit.get();
//        Integer s1 = submit1.get();
//        Future<Integer> res = service.submit(() -> sum.apply(s, s1));
//        Integer result = res.get();

        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> multiply.apply(a, a));
        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> multiply.apply(b, b));
//        Integer result = CompletableFuture.supplyAsync(() -> sum.apply(cf1.join(), cf2.join())).join();

        Integer result = cf1.thenCombine(cf2, (x, y) -> sum.apply(x, y)).join();

        //TODO
        return result;
    }

    private int sumOfSquare(int a, int b, int c) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> submit = service.submit(() -> multiply.apply(a, a));
        Future<Integer> submit1 = service.submit(() -> multiply.apply(b, b));

//        Future<Integer> submit2 = service.submit(() -> multiply.apply(b, b));
//        Integer s = submit.get();
//        Integer s1 = submit1.get();
//        Integer s2 = submit2.get();
//        Future<Integer> res = service.submit(() -> sum.apply(s, s1));
//        Future<Integer> submit3 = service.submit(() -> sum.apply(res.get(), s2));
//        Integer result = submit3.get();

        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> multiply.apply(a, a));
        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> multiply.apply(b, b));
        CompletableFuture<Integer> cf3 = CompletableFuture.supplyAsync(() -> multiply.apply(c, c));
//        CompletableFuture.allOf(cf1, cf2, cf3).join();
//        Integer result = CompletableFuture.supplyAsync(() -> sum.apply(cf1.join(), cf2.join())).thenCompose((x) ->
//                CompletableFuture.supplyAsync(() -> sum.apply(cf3.join(), x))).join();
        Integer result = Stream.of(cf1, cf2, cf3).reduce((f1, f2) ->
                f1.thenCombine(f2, (x, y) -> sum.apply(x, y))).
                orElse(CompletableFuture.completedFuture(0)).join();
        System.out.printf("result is %s%n", result);
        return result;
    }


}
