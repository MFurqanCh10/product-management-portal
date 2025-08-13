package com.furqan.crud_demo_inventory.dao;

import com.furqan.crud_demo_inventory.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDao{

    // field for entity manager
    private EntityManager entityManager;

    //inject using constructor

    public ProductDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //implement save method



    @Transactional
    @Override
    public Product save(Product theProduct) {
    entityManager.merge(theProduct);


        return theProduct;
    }

    @Override
    public Product findByID(Integer Id) {

        return entityManager.find(Product.class, Id);
    }

    @Override
    public List<Product> findAll() {
    // create query
        TypedQuery<Product> theQuery = entityManager.createQuery("FROM Product",Product.class);

        //return query results

        return theQuery.getResultList();
    }

    @Override
    public List<Product> findByItem_Vendor_Number(String theItem_Vendor_Number) {
        //create query
        TypedQuery<Product> theQuery = entityManager.createQuery(
                       "FROM Product WHERE  Item_Vendor_Number=:theData", Product.class);

        //set query parameters
        theQuery.setParameter("theData", theItem_Vendor_Number);

        //return query results
        return theQuery.getResultList();

    }

    @Override
    @Transactional
    public void update(Product theProduct) {

    entityManager.merge(theProduct);

    }

    @Override
    @Transactional
    public void delete(Integer Id) {

      // retrieve product
        Product theProduct = entityManager.find(Product.class, Id);

        //delete product
        entityManager.remove(theProduct);

    }

    @Override
    @Transactional
    public int deleteAll() {
    int numRowsDeleted = entityManager.createQuery("DELETE FROM Product").executeUpdate();
        return numRowsDeleted;
    }

    @Override
    public void deleteById(int theId) {
       //find product by id
        Product theProduct = entityManager.find(Product.class, theId);

        //remove product
        entityManager.remove(theProduct);
    }

    @Override
    public List<Product> searchProducts(String Item_Name, String Sku_Number) {


            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> product = cq.from(Product.class);

            // Create default "true" predicates (no filtering)
            Predicate namePredicate = cb.conjunction();
            Predicate skuPredicate = cb.conjunction();

            // Add name filter if provided
            if (Item_Name != null && !Item_Name.isEmpty()) {
                namePredicate = cb.like(
                        cb.lower(product.get("Item_Name")),
                        "%" + Item_Name.toLowerCase() + "%"
                );
            }

            // Add SKU filter if provided
            if (Sku_Number != null && !Sku_Number.isEmpty()) {
                skuPredicate = cb.like(
                        cb.lower(product.get("Sku_Number")),
                        "%" + Sku_Number.toLowerCase() + "%"
                );
            }

            // Combine with OR condition
            cq.where(cb.or(namePredicate, skuPredicate));
            return entityManager.createQuery(cq).getResultList();
        }
    }



