package ru.gb.internetshop.core.controllers;

import api.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.internetshop.core.converters.ProductConverter;
import ru.gb.internetshop.core.entities.Product;
import ru.gb.internetshop.core.exceptions.AppError;
import ru.gb.internetshop.core.exceptions.ResourceNotFoundException;
import ru.gb.internetshop.core.repositories.specifications.ProductsSpecifications;
import ru.gb.internetshop.core.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {

    ProductService productService;
    ProductConverter productConverter;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    @Operation(
            summary = "Запрос на получение списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Продукты не найдены", responseCode = "404"
                    )
            }
    )
    @GetMapping
    public Page<ProductDto> findAll(@RequestParam (name="p",defaultValue = "1") Integer page,
                                    @RequestParam (name="title_part",required = false) String titlePart,
                                    @RequestParam (name="min_price",required = false) Integer minPrice,
                                    @RequestParam (name="max_price",required = false) Integer maxPrice) {
        if (page<1){
            page=1;
        }
        Specification<Product> specification=Specification.where(null);
        if (titlePart!=null){
            specification=specification.and(ProductsSpecifications.titleLike(titlePart));
        }
        if (minPrice!=null){
            specification=specification.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice!=null){
            specification=specification.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        return productService.findAll(page-1,5,specification).map(productConverter::entityToProductDto);
    }

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productConverter.entityToProductDto(productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id:" + id + " не найден")));
    }


    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "200"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDto product) {
        productService.addProduct(product);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @Operation(
            summary = "Запрос на удаление продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно удален", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

}
