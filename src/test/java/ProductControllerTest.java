import com.fasterxml.jackson.databind.ObjectMapper;
import epam.Application;
import epam.domain.Order;
import epam.domain.Product;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ProductControllerTest {

    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/epam/v1/products";

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Ignore
    @Test
    public void test() throws Exception {
        Order order = mockOrder("new order");
        byte[] orderJson = toJson(order);

        //create order
        MvcResult order1 = mvc.perform(MockMvcRequestBuilders.post("/epam/v1/orders")
                        .content(orderJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int orderId = getResourceIdFromUrl(order1.getResponse().getRedirectedUrl());

        order.setOrderId(orderId);
        Product product = mockProduct("test", order);
        byte[] productJson = toJson(product);

        //assign product to the order
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/epam/v1/products")
                .content(productJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        int id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //get product by id
        mvc.perform(MockMvcRequestBuilders.get("/epam/v1/products/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is((int) id)))
                .andExpect(jsonPath("$.order.orderId", is((int) product.getOrderId(order))))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));

        //delete product
        mvc.perform(MockMvcRequestBuilders.delete("/epam/v1/products/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return result -> {
            Pattern pattern = Pattern.compile(expectedUrlPattern + "/1");
            Assert.assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
        };
    }

    private int getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }

    private Product mockProduct(String prefix, Order order) {
        Product product = new Product();
        product.setDescription(prefix + "_description");
        product.setName(prefix + "_name");
        product.setOrder(order);
        return product;
    }

    private Order mockOrder(String prefix) {
        Order order = new Order();
        order.setDescription(prefix + "_description");
        order.setName(prefix + "_name");
        return order;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
