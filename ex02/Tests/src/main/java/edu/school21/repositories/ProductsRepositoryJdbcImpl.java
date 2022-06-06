package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<Product>();

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT IDENTIFIER, NAME, PRICE FROM PRODUCT");
            while (resultSet.next()) {
                products.add(new Product(resultSet.getLong("IDENTIFIER"), resultSet.getString("NAME"),
                        resultSet.getInt("PRICE")));
            }
        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return products;
    }
    public Optional<Product> findById(Long id) {
        Optional<Product> result = Optional.empty();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE IDENTIFIER=" + id);
            while (resultSet.next()) {
                result = Optional.ofNullable(new Product(resultSet.getLong("IDENTIFIER"), resultSet.getString("NAME"),
                        resultSet.getInt("PRICE")));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }
    public void update(Product product) {
        long id = product.getIdentifier();
        String name = product.getName();
        int price = product.getPrice();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM PRODUCT WHERE IDENTIFIER=" + id);
            statement.executeUpdate("INSERT INTO PRODUCT (IDENTIFIER, NAME, PRICE) VALUES (" + id + ", '" + name + "', " + price + ");");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void save(Product product) {
        long id = product.getIdentifier();
        String name = product.getName();
        int price = product.getPrice();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO PRODUCT (IDENTIFIER, NAME, PRICE) VALUES (" + id + ", '" + name + "', " + price + ");");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void delete (Long id) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM PRODUCT WHERE IDENTIFIER=" + id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
