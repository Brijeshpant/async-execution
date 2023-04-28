import com.brij.*;
import com.brij.service.*;
import com.brij.service.impl.OrderServiceImpl;
import com.brij.service.impl.ProductServiceImpl;
import com.brij.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

public class ExecuteWithDifferentExecutorsTest {
    static private ProductService productService;
    static private OrderService orderService;
    static private UserService userService;
    long timeToRun = 1000;

    @BeforeClass
    public static void beforeAllTestMethods() {
        productService = new ProductServiceImpl();
        userService = new UserServiceImpl();
        orderService = new OrderServiceImpl();
    }

    @Before
    public void beforeEachTestMethod() {
    }

    @After
    public void afterEachTestMethod() {
        userService.getUsers().clear();
        try {
            productService.getProducts().clear();

        } catch (Exception e) {

        }
        orderService.getOrders().clear();
    }
    @Test
    void getTimeOfExecutionForSimpleExecutor() throws InterruptedException {
        ExampleApplication exampleApplication = new ExampleApplication(new SimpleExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        exampleApplication.executeWithNoResult(timeToRun);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("simple execution time " + totalExecutionTime);

        Assert.assertTrue(totalExecutionTime > 2 * timeToRun);
    }

    @Test
    void getTimeOfRunnableExecution() throws InterruptedException {
        ExampleApplication exampleApplication = new ExampleApplication(new JavaThreadExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        exampleApplication.executeWithNoResult(timeToRun);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);

        Assert.assertTrue(totalExecutionTime < 2 * timeToRun);
    }

    @Test
    void getTimeOfParallelExecution() throws InterruptedException {
        ExampleApplication exampleApplication = new ExampleApplication(new ParallelStreamExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        exampleApplication.executeWithNoResult(timeToRun);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertTrue(totalExecutionTime < 2 * timeToRun);
    }

    @Test
    void getTimeOfCompletableExecution() throws InterruptedException {
        ExampleApplication exampleApplication = new ExampleApplication(new CompletableFutureExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        exampleApplication.executeWithNoResult(timeToRun);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertTrue(totalExecutionTime < 2 * timeToRun);
    }
}