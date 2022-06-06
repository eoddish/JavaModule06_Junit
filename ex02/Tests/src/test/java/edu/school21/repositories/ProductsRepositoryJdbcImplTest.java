package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

class ProductsRepositoryJdbcImplTest {
    final Product product1 = new Product(1L, "bread", 49 );
    final Product product2 = new Product(2L, "potatoes", 29);
    final Product product3 = new Product(3L, "juice", 69 );
    final Product product4 = new Product(4L, "water", 35 );
    final Product product5 = new Product(5L, "tomatoes", 139);
    final List<Product> productsList = Arrays.asList(product1, product2, product3, product4, product5);
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = productsList;
    final Product EXPECTED_FIND_BY_ID_PRODUCT = product2;
    final Product EXPECTED_UPDATED_PRODUCT = new Product(3L, "juice", 79 );
    final Product EXPECTED_SAVED_PRODUCT = new Product(6L, "cereal", 39 );
    final Product EXPECTED_DELETED_PRODUCT = product1;
    DataSource dataSource;


    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("/schema.sql")
                .addScript("/data.sql")
                .build();
    }

    @Test
    void testFindAll() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        Product [] products = new Product[EXPECTED_FIND_ALL_PRODUCTS.size()];
        assertArrayEquals(productsRepository.findAll().toArray(products), EXPECTED_FIND_ALL_PRODUCTS.toArray(products));
    }
    @Test
    void testFindById() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        Product product = productsRepository.findById(EXPECTED_FIND_BY_ID_PRODUCT.getIdentifier()).get();
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT.getIdentifier(), product.getIdentifier());
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT.getName(), product.getName());
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT.getPrice(), product.getPrice());
    }
    @Test
    void testUpdate() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        Product product = productsRepository.findById(EXPECTED_UPDATED_PRODUCT.getIdentifier()).get();
        assertEquals(EXPECTED_UPDATED_PRODUCT.getIdentifier(), product.getIdentifier());
        assertEquals(EXPECTED_UPDATED_PRODUCT.getName(), product.getName());
        assertEquals(EXPECTED_UPDATED_PRODUCT.getPrice(), product.getPrice());
    }
    @Test
    void testSave() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        productsRepository.save(EXPECTED_SAVED_PRODUCT);
        Product product = productsRepository.findById(EXPECTED_SAVED_PRODUCT.getIdentifier()).get();
        assertEquals(EXPECTED_SAVED_PRODUCT.getIdentifier(), product.getIdentifier());
        assertEquals(EXPECTED_SAVED_PRODUCT.getName(), product.getName());
        assertEquals(EXPECTED_SAVED_PRODUCT.getPrice(), product.getPrice());
    }
    @Test
    void testDelete() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        productsRepository.delete(EXPECTED_DELETED_PRODUCT.getIdentifier());
        List<Product> products = productsRepository.findAll();
        for (int i = 0; i < products.size(); i++) {
            assertNotEquals(EXPECTED_DELETED_PRODUCT.getIdentifier(), products.get(i).getIdentifier());
            assertNotEquals(EXPECTED_DELETED_PRODUCT.getName(), products.get(i).getName());
            assertNotEquals(EXPECTED_DELETED_PRODUCT.getPrice(), products.get(i).getPrice());
        }
    }
}
