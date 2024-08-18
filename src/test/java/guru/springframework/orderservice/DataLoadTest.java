package guru.springframework.orderservice;

import guru.springframework.orderservice.domain.*;
import guru.springframework.orderservice.repositories.CustomerRepository;
import guru.springframework.orderservice.repositories.OrderHeaderRepository;
import guru.springframework.orderservice.repositories.ParentRepository;
import guru.springframework.orderservice.repositories.ProductRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by jt on 5/28/22.
 */
@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataLoadTest {
    final String PRODUCT_D1 = "Product 1";
    final String PRODUCT_D2 = "Product 2";
    final String PRODUCT_D3 = "Product 3";

    final String TEST_CUSTOMER = "TEST CUSTOMER";

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ParentRepository parentRepository;

    @Test
    void testN_PlusOneProblem() {

        Customer customer = customerRepository.findCustomerByCustomerNameIgnoreCase(TEST_CUSTOMER).get();

        IntSummaryStatistics totalOrdered = orderHeaderRepository.findAllByCustomer(customer).stream()
                .flatMap(orderHeader -> orderHeader.getOrderLines().stream())
                .collect(Collectors.summarizingInt(ol -> ol.getQuantityOrdered()));

        System.out.println("total ordered: " + totalOrdered.getSum());
    }

    @Test
    void testOrderLinesLazyVsEager() {
        OrderHeader orderHeader = orderHeaderRepository.getById(400l);

        System.out.println("Order Id is: " + orderHeader.getId());

        System.out.println("Some other stuff");
        System.out.println("Customer Name is: " + orderHeader.getCustomer().getCustomerName());
        System.out.println("Order Lines: " + orderHeader.getOrderLines().size());
        System.out.println("Some other stuff 2");
    }


    @Test
    void testFooLazyVsEager() {
        OrderHeader orderHeader = orderHeaderRepository.getById(4l);

        System.out.println("Order Id is: " + orderHeader.getId());

        System.out.println("Some other stuff");
        System.out.println("Foo size: " + orderHeader.getFoos().size());
        System.out.println("Some other stuff 2");
    }

    @Test
    void testFooLazyVsEagerWithAllData() {
        List<OrderHeader> allOrderHeaders = orderHeaderRepository.findAll();

        for (OrderHeader orderHeader : allOrderHeaders) {

            for (Foo foo : orderHeader.getFoos()) {
                int abc = foo.getAmount();
            }
            //System.out.println("Order Id is: " + orderHeader.getId());
            //System.out.println("Foo size: " + orderHeader.getFoos().size());
        }
    }

    @Test
    void testParentN_PlusOneProblem() {

        List<Parent> parents = parentRepository.findAll();
        IntSummaryStatistics totalChildAmount = parents.stream()
                .flatMap(parent -> parent.getChildren().stream())
                .collect(Collectors.summarizingInt(Child::getAmount));

        System.out.println("Patents size: " + parents.size());
        System.out.println("total child amount: " + totalChildAmount.getSum());
    }

    @Test
    void testParentLazyVsEagerWithAllData() {
        List<Parent> allParents = parentRepository.findAll();

        for (Parent parent : allParents) {
//            for (Child child : parent.getChildren()) {
//               // int abc = child.getAmount();
//            }
        }
    }


    @Disabled
    @Rollback(value = false)
    @Test
    void testDataLoader() {
        List<Product> products = loadProducts();
        Customer customer = loadCustomers();

        int ordersToCreate = 99;

        for (int i = 0; i < ordersToCreate; i++) {
            System.out.println("Creating order #: " + i);
            saveOrder(customer, products);
        }

        orderHeaderRepository.flush();
    }

    @Disabled
    @Rollback(value = false)
    @Test
    void testParentDataLoader() {
        Random random = new Random();

        int parentsToCreate = 30000;

        for (int i = 0; i < parentsToCreate; i++) {
            System.out.println("Creating parent #: " + i);
            Parent parent = new Parent();

            for (int j = 0; j < 3; j++) {
                Child child = new Child();
                child.setAmount(random.nextInt(100));
                parent.addChild(child);
            }
            parentRepository.save(parent);
        }
    }

    private OrderHeader saveOrder(Customer customer, List<Product> products) {
        Random random = new Random();

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        products.forEach(product -> {
            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(product);
            orderLine.setQuantityOrdered(random.nextInt(20));
            //orderHeader.getOrderLines().add(orderLine);  // this is the problem
            orderHeader.addOrderLine(orderLine);
        });

        for (int i = 0; i < 3; i++) {
            Foo foo = new Foo();
            foo.setAmount(random.nextInt(100));
            //orderHeader.getFoos().add(foo);  // this is the problem
            orderHeader.addFoo(foo);
        }

        return orderHeaderRepository.save(orderHeader);
    }

    //This version has no performance effect on the query so similar to the previous version!
    @Disabled
    @Rollback(value = false)
    @Test
    void testDataLoaderBulkVersion() {
        List<Product> products = loadProducts();
        Customer customer = loadCustomers();

        int ordersToCreate = 10;

        List<OrderHeader> orderHeaders = generateBulkOrders(customer, products, ordersToCreate);

        orderHeaderRepository.saveAll(orderHeaders);
        orderHeaderRepository.flush();
    }

    private List<OrderHeader> generateBulkOrders(Customer customer, List<Product> products, int ordersToCreate) {
        List<OrderHeader> orderHeaders = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < ordersToCreate; i++) {
            OrderHeader orderHeader = new OrderHeader();
            orderHeader.setCustomer(customer);


            for (int j = 0; j < 500; j++) {
                Foo foo = new Foo();
                foo.setAmount(random.nextInt(100));
                //orderHeader.getFoos().add(foo);  // this is the problem
                orderHeader.addFoo(foo);
            }

            orderHeaders.add(orderHeader);
        }

        return orderHeaders;
    }


    private Customer loadCustomers() {
        return getOrSaveCustomer(TEST_CUSTOMER);
    }

    private Customer getOrSaveCustomer(String customerName) {
        return customerRepository.findCustomerByCustomerNameIgnoreCase(customerName)
                .orElseGet(() -> {
                    Customer c1 = new Customer();
                    c1.setCustomerName(customerName);
                    c1.setEmail("test@example.com");
                    Address address = new Address();
                    address.setAddress("123 Main");
                    address.setCity("New Orleans");
                    address.setState("LA");
                    c1.setAddress(address);
                    return customerRepository.save(c1);
                });
    }

    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();

        products.add(getOrSaveProduct(PRODUCT_D1));
        products.add(getOrSaveProduct(PRODUCT_D2));
        products.add(getOrSaveProduct(PRODUCT_D3));

        return products;
    }

    private Product getOrSaveProduct(String description) {
        return productRepository.findByDescription(description)
                .orElseGet(() -> {
                    Product p1 = new Product();
                    p1.setDescription(description);
                    p1.setProductStatus(ProductStatus.NEW);
                    return productRepository.save(p1);
                });
    }

}
