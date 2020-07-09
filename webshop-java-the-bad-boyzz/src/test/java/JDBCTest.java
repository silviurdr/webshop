import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBCTest {
    @Test
    public void findProductTest() throws IOException, SQLException {
        ProductDao productDao = ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
        SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
        Product product=new Product(1, "Amazon Fire", (float) 49.9,"USD","Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.",productCategoryDao.find(1),supplierDao.find(1),"product_1.jpg" );
        Product result = productDao.find(1);
        assertEquals(result.toString(), product.toString());
    }

    @Test
    public void getProductsBySupplierTest() throws IOException,SQLException{
        ProductDao productDao = ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
        SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
        Product product1=new Product(1, "Amazon Fire", (float) 49.9,"USD","Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.",productCategoryDao.find(1),supplierDao.find(1),"product_1.jpg" );
        Product product2=new Product(2, "Amazon Fire HD 8", (float) 89,"USD","Latest Fire HD 8 tablet is a great value for media consumption.",productCategoryDao.find(1),supplierDao.find(1),"product_3.jpg" );
        Product product3=new Product(3, "Lenovo IdeaPad Miix 700", (float) 479,"USD","Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports.",productCategoryDao.find(1),supplierDao.find(1),"product_2.jpg" );
        List<Product> products = new ArrayList<>(Arrays.asList(product1,product2,product3));
        List result=productDao.getBy(supplierDao.find(1));
        assertEquals(result.toString(),products.toString());
    }

    @Test
    public void getProductsByCategoryTest() throws IOException, SQLException{
        ProductDao productDao = ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
        SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
        Product product1=new Product(1, "Amazon Fire", (float) 49.9,"USD","Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.",productCategoryDao.find(1),supplierDao.find(1),"product_1.jpg" );
        Product product2=new Product(2, "Amazon Fire HD 8", (float) 89,"USD","Latest Fire HD 8 tablet is a great value for media consumption.",productCategoryDao.find(1),supplierDao.find(1),"product_3.jpg" );
        Product product3=new Product(3, "Lenovo IdeaPad Miix 700", (float) 479,"USD","Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports.",productCategoryDao.find(1),supplierDao.find(1),"product_2.jpg" );
        List<Product> products = new ArrayList<>(Arrays.asList(product1,product2,product3));
        List result=productDao.getBy(productCategoryDao.find(1));
        assertEquals(result.toString(),products.toString());
    }

    @Test
    public void getProductsByCategoryAndSupplierTest() throws IOException,SQLException{
        ProductDao productDao = ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
        SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
        Product product1=new Product(1, "Amazon Fire", (float) 49.9,"USD","Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.",productCategoryDao.find(1),supplierDao.find(1),"product_1.jpg" );
        Product product2=new Product(2, "Amazon Fire HD 8", (float) 89,"USD","Latest Fire HD 8 tablet is a great value for media consumption.",productCategoryDao.find(1),supplierDao.find(1),"product_3.jpg" );
        Product product3=new Product(3, "Lenovo IdeaPad Miix 700", (float) 479,"USD","Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports.",productCategoryDao.find(1),supplierDao.find(1),"product_2.jpg" );
        List<Product> products = new ArrayList<>(Arrays.asList(product1,product2,product3));
        List result=productDao.getBy(productCategoryDao.find(1),supplierDao.find(1));
        assertEquals(result.toString(),products.toString());
    }

    @Test
    public void getAllProductsTest() throws IOException,SQLException{
        ProductDao productDao = ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
        SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
        Product product1=new Product(1, "Amazon Fire", (float) 49.9,"USD","Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.",productCategoryDao.find(1),supplierDao.find(1),"product_1.jpg" );
        Product product2=new Product(2, "Amazon Fire HD 8", (float) 89,"USD","Latest Fire HD 8 tablet is a great value for media consumption.",productCategoryDao.find(1),supplierDao.find(1),"product_3.jpg" );
        Product product3=new Product(3, "Lenovo IdeaPad Miix 700", (float) 479,"USD","Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports.",productCategoryDao.find(1),supplierDao.find(1),"product_2.jpg" );
        Product product4=new Product(4,"HP Pavilion",(float) 700,"USD","HP Laptop. Great value at a greater price\", notebook, amazon.",productCategoryDao.find(2),supplierDao.find(2),"product_4.jpg");
        List<Product> products = new ArrayList<>(Arrays.asList(product1,product2,product3,product4));
        List result=productDao.getAll();
        assertEquals(result.toString(),products.toString());
    }

}
