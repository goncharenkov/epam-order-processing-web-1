package epam.api.rest;

import epam.domain.Product;
import epam.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/epam/v1/products")
@Api(tags = {"product"})
public class ProductController extends AbstractRestHandler {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add product to the order")
    public void addProductToOrder(@RequestBody Product product, HttpServletRequest request, HttpServletResponse response) {
        Product addedProduct = this.productService.addProduct(product);
        response.setHeader("Location", request.getRequestURL().append("/").append(addedProduct.getId()).toString());
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single product")
    public
    @ResponseBody
    Product getProduct(@ApiParam(value = "The ID of the product.", required = true)
                       @PathVariable("orderId") int orderId,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.productService.getProduct(orderId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete the product.")
    public void deleteProduct(@ApiParam(value = "The ID of the existing order resource.", required = true)
                              @PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.productService.getProduct(id));
        this.productService.deleteProduct(id);
    }
}
