package epam.api.rest;

import epam.domain.Order;
import epam.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/epam/v1/orders")
@Api(tags = {"order"})
public class OrderController extends AbstractRestHandler {
    private static final String webServer2Url = "http://localhost:8090/epam/v1/orders/";

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single order.")
    public
    @ResponseBody
    Order getOrder(@ApiParam(value = "The ID of the order.", required = true)
                   @PathVariable("id") int id,
                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        Order order = this.orderService.getOrder(id);
        return order;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an order.")
    public void createOrder(@RequestBody Order order, HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", order.getName());
        jsonObject.put("description", order.getDescription());

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(jsonObject, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange( webServer2Url, HttpMethod.POST, entity, String.class).getBody();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete an order.")
    public void deleteOrder(@ApiParam(value = "The ID of the existing order.", required = true)
                            @PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(webServer2Url + id, HttpMethod.DELETE, entity, String.class).getBody();
    }

}
