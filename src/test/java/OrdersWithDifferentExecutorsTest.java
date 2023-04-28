import com.brij.*;
import com.brij.model.Order;
import com.brij.service.*;
import com.brij.service.impl.OrderServiceImpl;
import com.brij.service.impl.ProductServiceImpl;
import com.brij.service.impl.UserServiceImpl;
import org.junit.*;

public class OrdersWithDifferentExecutorsTest {
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
    public void shouldFailToCreateOrderForNoProductWithSimpleExecutor() throws Exception {
        ExampleApplication exampleApplication = new ExampleApplication(new SimpleExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("Product not found", RuntimeException.class, () -> {
            exampleApplication.createOrder(1, 1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldFailToCreateOrderForNoUserWithSimpleExecutor() throws Exception {
        productService.creteProduct(1, "product1");
        ExampleApplication exampleApplication = new ExampleApplication(new SimpleExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("User not found", RuntimeException.class, () -> {
            exampleApplication.createOrder(1, 1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldCreateOrderWithSimpleExecutor() throws Exception {
        productService.creteProduct(1, "product1");
        userService.createUser(1, "user1");
        ExampleApplication exampleApplication = new ExampleApplication(new SimpleExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Order order = exampleApplication.createOrder(1, 1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(1, order.getUser());
        Assert.assertEquals(1, order.getProduct());

    }

    @Test
    public void shouldFailToCreateOrderForNoProductWithRunnableExecutor() throws Exception {
        ExampleApplication exampleApplication = new ExampleApplication(new JavaThreadExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("Product not found", RuntimeException.class, () -> {
            exampleApplication.createOrder(1, 1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldFailToCreateOrderForNoUserWithRunnableExecutor() throws Exception {
        productService.creteProduct(1, "product1");
        ExampleApplication exampleApplication = new ExampleApplication(new JavaThreadExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("User not found", RuntimeException.class, () -> {
            exampleApplication.createOrder(1, 1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldCreateOrderWithRunnableExecutor() throws Exception {
        productService.creteProduct(1, "product1");
        userService.createUser(1, "user1");
        ExampleApplication exampleApplication = new ExampleApplication(new JavaThreadExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Order order = exampleApplication.createOrder(1, 1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(1, order.getUser());
        Assert.assertEquals(1, order.getProduct());

    }


    @Test
    public void shouldFailToCreateOrderForNoProductWithParallelStreamExecutor() throws Exception {
        ExampleApplication exampleApplication = new ExampleApplication(new ParallelStreamExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("Product not found", RuntimeException.class, () -> {
            exampleApplication.createOrder(1, 1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldFailToCreateOrderForNoUserWithParallelStreamExecutor() throws Exception {
        productService.creteProduct(1, "product1");
        ExampleApplication exampleApplication = new ExampleApplication(new ParallelStreamExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("User not found", RuntimeException.class, () -> {
            exampleApplication.createOrder(1, 1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldCreateOrderWithParallelStreamExecutor() throws Exception {
        productService.creteProduct(1, "product1");
        userService.createUser(1, "user1");
        ExampleApplication exampleApplication = new ExampleApplication(new ParallelStreamExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Order order = exampleApplication.createOrder(1, 1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(1, order.getUser());
        Assert.assertEquals(1, order.getProduct());

    }

    @Test
    public void shouldFailToCreateOrderForNoProductWithCompletableFutureExecutor() throws Exception {
        ExampleApplication exampleApplication = new ExampleApplication(new CompletableFutureExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("Product not found", RuntimeException.class, () -> {
            exampleApplication.createOrder(1, 1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldFailToCreateOrderForNoUserWithCompletableFutureExecutor() throws Exception {
        productService.creteProduct(1, "product1");
        ExampleApplication exampleApplication = new ExampleApplication(new CompletableFutureExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("User not found", RuntimeException.class, () -> {
            exampleApplication.createOrder(1, 1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldCreateOrderWithCompletableFutureExecutor() throws Exception {
        productService.creteProduct(1, "product1");
        userService.createUser(1, "user1");
        ExampleApplication exampleApplication = new ExampleApplication(new CompletableFutureExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        Order order = exampleApplication.createOrder(1, 1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(1, order.getUser());
        Assert.assertEquals(1, order.getProduct());

    }


}