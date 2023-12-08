package j2ee_project.dao.catalog.product;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.catalog.FeaturedProduct;
import j2ee_project.model.catalog.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.lang.reflect.Type;
import java.util.*;

/**
 * Class that interacts with the database to edit the Product table in the database or get data from it
 *
 * @author Robin Meneust
 */
public class ProductDAO {
    /**
     * Create a query string from a list of filters for the Product table and its params
     *
     * @param queryStrStart Beginning of the query to which filters are added
     * @param name          The products' name must match with this name (it's case-insensitive, and it can be just a part of a searched word: e.g. 'Ch' will return 'chess' products)
     * @param category      The products' category must match with it (exactly like the name filter)
     * @param minPrice      The products' price must be greater or equal to this value
     * @param maxPrice      The products' price must be lesser or equal to this value
     * @return Hashmap containing the query (key "query") associated to the provided filters whose values are stored in a list in the hashmap in the same order as in the query string (key "params")
     */
    public static HashMap<String,Object> getQueryString(String queryStrStart, String name, String category, String minPrice, String maxPrice) {
        LinkedList<String> listParams = new LinkedList<>();

        String queryStr = queryStrStart;
        String queryStrPart2 = "";
        boolean isNotFirstFilter = false;

        // Check the filters and add them to the query if they are valid

        if(name != null && !name.trim().isEmpty()) {
            isNotFirstFilter = true;
            queryStrPart2 += "p.name LIKE ?1";
            listParams.add("%"+name+"%");
        }

        if(category != null && !category.trim().isEmpty()) {
            if(isNotFirstFilter) {
                queryStrPart2 += " AND ";
            } else {
                isNotFirstFilter = true;
            }
            queryStrPart2 += "c.name LIKE ?"+(listParams.size()+1);
            listParams.add("%"+category+"%");
        }

        if(minPrice != null && !minPrice.trim().isEmpty() && maxPrice != null && !maxPrice.trim().isEmpty()) {
            if(isNotFirstFilter) {
                queryStrPart2 += " AND ";
            } else {
                isNotFirstFilter = true;
            }
            queryStrPart2 += "(p.unitPrice BETWEEN ?"+(listParams.size()+1)+" AND ?"+(listParams.size()+2)+")";
            listParams.add(minPrice);
            listParams.add(maxPrice);
        }

        // Add the filters if there is at least one that is valid
        if(!queryStrPart2.isEmpty()) {
            queryStr += " WHERE " + queryStrPart2;
        }

        HashMap<String,Object> result = new HashMap<>(2);
        result.put("query", queryStr);
        result.put("params", listParams);
        return result;
    }

    /**
     * Get a list of products that match the given filters
     *
     * @param begin    Index of the first element (e.g. begin=4 will ignore the 3 first products)
     * @param size     Max number of elements returned
     * @param name     The products' name must match with this name (it's case-insensitive, and it can be just a part of a searched word: e.g. 'Ch' will return 'chess' products)
     * @param category The products' category must match with it (exactly like the name filter)
     * @param minPrice The products' price must be greater or equal to this value
     * @param maxPrice The products' price must be lesser or equal to this value
     * @return List of products that match with all of those filters
     */
    public static List<Product> getProducts(int begin, int size, String name, String category, String minPrice, String maxPrice) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        HashMap<String,Object> queryObj = getQueryString("FROM Product AS p LEFT JOIN Category AS c ON p.category.id = c.id",name, category, minPrice, maxPrice);
        String queryStr = "";
        if(queryObj.get("query") instanceof String) {
            queryStr = (String) queryObj.get("query");
        }

        List<String> params = null;
        if(queryObj.get("params") != null) {
            params = (List<String>) queryObj.get("params");
        }

        if(params == null || queryStr == "") {
            return new ArrayList<>();
        }

        TypedQuery<Product> query = entityManager.createQuery(queryStr, Product.class);
        for(int i=0; i<params.size(); i++) {
            query.setParameter(i+1,params.get(i));
        }

        List<Product> products = query.setFirstResult(begin).setMaxResults(size).getResultList();

        transaction.commit();
        entityManager.close();
        return products;
    }

    /**
     * Get products list
     *
     * @return the list of products
     */
    public static List<Product> getProducts(){
        int size = Math.toIntExact(ProductDAO.getSize());
        return ProductDAO.getProducts(0,size,null,null,null,null);
    }

    /**
     * Get a product from its ID
     *
     * @param productId ID of the searched product
     * @return Product whose ID matched with the one provided
     */
    public static Product getProduct(int productId) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Product product = null;
        try {
            product = entityManager.createQuery("FROM Product WHERE id=:productId", Product.class).setParameter("productId", productId).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return product;
    }

    /**
     * Get the list of the featured products
     *
     * @return Featured products
     */
    public static List<FeaturedProduct> getFeaturedProducts() {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<FeaturedProduct> featuredProducts = entityManager.createQuery("FROM FeaturedProduct", FeaturedProduct.class).getResultList();

        transaction.commit();
        entityManager.close();
        return featuredProducts;
    }

    /**
     * Get the total number of products
     *
     * @param name     The products' name must match with this name (it's case-insensitive, and it can be just a part of a searched word: e.g. 'Ch' will return 'chess' products)
     * @param category The products' category must match with it (exactly like the name filter)
     * @param minPrice The products' price must be greater or equal to this value
     * @param maxPrice The products' price must be lesser or equal to this value
     * @return Number of products
     */
    public static Long getSize(String name, String category, String minPrice, String maxPrice) {
        HashMap<String,Object> queryObj = getQueryString("SELECT COUNT(*) FROM Product AS p LEFT JOIN Category AS c ON p.category.id = c.id", name, category, minPrice, maxPrice);

        String queryStr = "";
        if(queryObj.get("query") instanceof String) {
            queryStr = (String) queryObj.get("query");
        }

        List<String> params = null;
        if(queryObj.get("params") != null) {
            params = (List<String>) queryObj.get("params");
        }

        if(params == null || queryStr == "") {
            return 0L;
        }

        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Long> query = entityManager.createQuery(queryStr, Long.class);
        for(int i=0; i<params.size(); i++) {
            query.setParameter(i+1,params.get(i));
        }

        Long size = 0L;
        try {
            size = query.getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return size;
    }

    /**
     * Get the total number of products.
     *
     * @return the number of products
     */
    public static Long getSize(){
        return ProductDAO.getSize(null,null,null,null);
    }

    /**
     * Delete product whose ID is given
     *
     * @param productId the product id
     */
    public static void deleteProduct(int productId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Product product = entityManager.createQuery("FROM Product WHERE id=:productId", Product.class).setParameter("productId", productId).getSingleResult();
            entityManager.remove(product);

            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        entityManager.close();
    }

    /**
     * Add product
     *
     * @param product the product added
     */
    public static void addProduct(Product product){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(product);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Set the image path of the product whose id is given
     *
     * @param productId the product id
     * @param path      the path
     */
    public static void setProductImagePath(int productId, String path) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if(path==null || path.trim().isEmpty()) {
            return;
        }

        try {
            Product product = entityManager.createQuery("FROM Product WHERE id=:productId", Product.class).setParameter("productId", productId).getSingleResult();
            product.setImagePath(path);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        entityManager.close();
    }
}
