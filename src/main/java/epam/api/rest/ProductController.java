package epam.api.rest;

import epam.domain.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/epam/v1/products")
@Api(tags = {"product"})
public class ProductController extends AbstractRestHandler {
    private static final String webServer2Url = "http://localhost:8090/epam/v1/products/";

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add product to the order")
    public void addProductToOrder(@RequestBody Product product, HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", product.getName());
        jsonObject.put("description", product.getDescription());

        HttpEntity<JSONObject> entity = new HttpEntity<>(jsonObject, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange( webServer2Url, HttpMethod.POST, entity, String.class).getBody();

        response.setHeader("Location", request.getRequestURL().append("/").append(product.getOrder().getOrderId()).toString());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete the product.")
    public void deleteProduct(@ApiParam(value = "The ID of the existing order resource.", required = true)
                              @PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(webServer2Url + id, HttpMethod.DELETE, entity, String.class).getBody();

    }
}
