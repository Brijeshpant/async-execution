import com.brij.*;
import com.brij.model.User;
import com.brij.model.UserDashboard;
import com.brij.service.*;
import org.junit.*;

public class UserDashboardWithDifferentExecutorsTest {
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
    public void shouldFailToGetDashboardWithSimpleExecutorWhenUserDecentExists() throws InterruptedException {
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("User not found", RuntimeException.class, () -> {
            ExampleApplication exampleApplication = new ExampleApplication(new SimpleExecutor(orderService, userService, productService));
            UserDashboard userDetails = exampleApplication.getDashBoard(1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldGetDashboardWithEmptyProductAndOrderSimpleExecutor() throws InterruptedException {
        final User user1 = userService.createUser(1, "User1");
        ExampleApplication exampleApplication = new ExampleApplication(new SimpleExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        UserDashboard userDetails = exampleApplication.getDashBoard(1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(user1, userDetails.getUser());
        Assert.assertEquals(0, userDetails.getAllProducts().size());
        Assert.assertEquals(0, userDetails.getUserOrders().size());

    }

    @Test
    public void shouldGetDashboardWithProductAndOrderWithSimpleExecutor() throws InterruptedException {
        User user1 = userService.createUser(1, "User1");
        productService.creteProduct(1, "product1");
        productService.creteProduct(2, "product2");
        orderService.addOrder(1, 1);
        orderService.addOrder(2, 2);
        ExampleApplication exampleApplication = new ExampleApplication(new SimpleExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        UserDashboard userDetails = exampleApplication.getDashBoard(1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(user1, userDetails.getUser());
        Assert.assertEquals(2, userDetails.getAllProducts().size());
        Assert.assertEquals(1, userDetails.getUserOrders().size());

    }

    @Test
    public void shouldFailToGetDashboardWithRunnableExecutorWhenUserDoesntExists() throws InterruptedException {
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("User not found", RuntimeException.class, () -> {
            ExampleApplication exampleApplication = new ExampleApplication(new JavaThreadExecutor(orderService, userService, productService));
            UserDashboard userDetails = exampleApplication.getDashBoard(1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldGetDashboardWithEmptyProductAndOrderWithRunnableExecutor() throws InterruptedException {
        final User user1 = userService.createUser(1, "User1");
        ExampleApplication exampleApplication = new ExampleApplication(new JavaThreadExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        UserDashboard userDetails = exampleApplication.getDashBoard(1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(user1, userDetails.getUser());
        Assert.assertEquals(0, userDetails.getAllProducts().size());
        Assert.assertEquals(0, userDetails.getUserOrders().size());

    }


    @Test
    public void shouldGetDashboardWithProductAndOrderWithRunnableExecutor() throws InterruptedException {
        User user1 = userService.createUser(1, "User1");
        productService.creteProduct(1, "product1");
        productService.creteProduct(2, "product2");
        orderService.addOrder(1, 1);
        orderService.addOrder(2, 2);
        ExampleApplication exampleApplication = new ExampleApplication(new JavaThreadExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        UserDashboard userDetails = exampleApplication.getDashBoard(1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(user1, userDetails.getUser());
        Assert.assertEquals(2, userDetails.getAllProducts().size());
        Assert.assertEquals(1, userDetails.getUserOrders().size());

    }

    @Test
    public void shouldFailToGetDashboardWhenUserDoesntExistsWithParallelStreamExecutor() throws InterruptedException {
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("User not found", RuntimeException.class, () -> {
            ExampleApplication exampleApplication = new ExampleApplication(new ParallelStreamExecutor(orderService, userService, productService));
            UserDashboard userDetails = exampleApplication.getDashBoard(1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldGetDashboardWithEmptyProductAndOrderWithParallelStreamExecutor() throws InterruptedException {
        final User user1 = userService.createUser(1, "User1");
        ExampleApplication exampleApplication = new ExampleApplication(new ParallelStreamExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();

        UserDashboard userDetails = exampleApplication.getDashBoard(1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(user1, userDetails.getUser());
        Assert.assertEquals(0, userDetails.getAllProducts().size());
        Assert.assertEquals(0, userDetails.getUserOrders().size());

    }


    @Test
    public void shouldGetDashboardWithProductAndOrderWithParallelStreamExecutor() throws InterruptedException {
        User user1 = userService.createUser(1, "User1");
        productService.creteProduct(1, "product1");
        productService.creteProduct(2, "product2");
        orderService.addOrder(1, 1);
        orderService.addOrder(2, 2);
        ExampleApplication exampleApplication = new ExampleApplication(new ParallelStreamExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        UserDashboard userDetails = exampleApplication.getDashBoard(1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(user1, userDetails.getUser());
        Assert.assertEquals(2, userDetails.getAllProducts().size());
        Assert.assertEquals(1, userDetails.getUserOrders().size());

    }

    @Test
    public void shouldFailToGetDashboardExecutorWhenUserDecentExistsWithCompletableFuture() throws InterruptedException {
        long timeBeforeStart = System.currentTimeMillis();
        Assert.assertThrows("User not found", RuntimeException.class, () -> {
            ExampleApplication exampleApplication = new ExampleApplication(new CompletableFutureExecutor(orderService, userService, productService));
            UserDashboard userDetails = exampleApplication.getDashBoard(1);
        });
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
    }

    @Test
    public void shouldGetDashboardWithEmptyProductAndOrderWithCompletableFuture() throws InterruptedException {
        final User user1 = userService.createUser(1, "User1");
        ExampleApplication exampleApplication = new ExampleApplication(new CompletableFutureExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        UserDashboard userDetails = exampleApplication.getDashBoard(1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(user1, userDetails.getUser());
        Assert.assertEquals(0, userDetails.getAllProducts().size());
        Assert.assertEquals(0, userDetails.getUserOrders().size());

    }

    @Test
    public void shouldGetDashboardWithProductAndOrderWithCompletableFuture() throws InterruptedException {
        User user1 = userService.createUser(1, "User1");
        productService.creteProduct(1, "product1");
        productService.creteProduct(2, "product2");
        orderService.addOrder(1, 1);
        orderService.addOrder(2, 2);
        ExampleApplication exampleApplication = new ExampleApplication(new CompletableFutureExecutor(orderService, userService, productService));
        long timeBeforeStart = System.currentTimeMillis();
        UserDashboard userDetails = exampleApplication.getDashBoard(1);
        long timeNow = System.currentTimeMillis();
        long totalExecutionTime = timeNow - timeBeforeStart;
        System.out.println("Execution time " + totalExecutionTime);
        Assert.assertEquals(user1, userDetails.getUser());
        Assert.assertEquals(2, userDetails.getAllProducts().size());
        Assert.assertEquals(1, userDetails.getUserOrders().size());

    }



}