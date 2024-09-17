package ar.unrn.tp.jpa.services;

import ar.unrn.tp.api.ProductService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.model.Brand;
import ar.unrn.tp.domain.model.Category;
import ar.unrn.tp.domain.model.Product;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final TransactionService transactionService;

    @Override
    public void crearProducto(String codigo, String descripcion, float precio, Long id_categoria, Long id_marca) {
        this.transactionService.executeInTransaction(em -> {

            validateCode(em, codigo);

            try {
                Product newProduct = new Product(Integer.parseInt(codigo),
                        descripcion,
                        getCategoryById(em, id_categoria),
                        getBrandById(em, id_marca),
                        precio);

                em.persist(newProduct);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El código del producto debe ser un número válido.", e);

            } catch (Exception e) {
                throw new RuntimeException("Error al crear el producto: " + e.getMessage(), e);
            }
        });

    }

    @Override
    public void modificarProducto(Long idProducto, String descripcion, String categoria, String marca, double price) {

        this.transactionService.executeInTransaction(em -> {
            String queryGetProducts = "SELECT p FROM Product p WHERE p.id = :idProduct";
            TypedQuery<Product> query = em.createQuery(queryGetProducts, Product.class);
            query.setParameter("idProduct", idProducto);

            try {
                Product productDB = query.getSingleResult();

                productDB.updateDescription(descripcion);
                productDB.updateCategory(Category.valueOf(categoria.toUpperCase()));
                productDB.updateBrand(Brand.valueOf(marca.toUpperCase()));
                productDB.updatePrice(price);

            } catch (NoResultException e) {
                throw new EntityNotFoundException("No se encontró ningún producto en la base de datos.");
            }
        });

    }

    @Override
    public List<Product> listarProductos() {
        List<Product> products = new ArrayList<>();

        this.transactionService.executeInTransaction(em -> {
            String queryGetProducts = "SELECT p FROM Product p";
            TypedQuery<Product> query = em.createQuery(queryGetProducts, Product.class);

            products.addAll(query.getResultList());

            if (products.isEmpty())
                throw new RuntimeException("No hay productos cargados");
        });

        return products;
    }

    private void validateCode(EntityManager em, String code) {

        String sql = "SELECT p FROM Product p WHERE p.code = :productCode";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        query.setParameter("productCode", code);

        List<Product> productos = query.getResultList();

        if (!productos.isEmpty()) {
            throw new IllegalArgumentException("Ya existe un producto con el código: " + code);
        }
    }

    private Brand getBrandById(EntityManager em, Long id) {

        String sql = "SELECT b FROM BrandEntity b WHERE b.id = :id_brand";
        TypedQuery<Brand> query = em.createQuery(sql, Brand.class);
        query.setParameter("id_brand", id);

        try {
            return query.getSingleResult();

        } catch (NoResultException e) {
            throw new EntityNotFoundException("Marca con ID " + id + " no encontrado.");

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Se encontraron múltiples marcas para este id: " + id);
        }
    }

    private Category getCategoryById(EntityManager em, Long id) {

        String sql = "SELECT ce FROM CategoryEntity ce WHERE ce.id = :id_category";
        TypedQuery<Category> query = em.createQuery(sql, Category.class);
        query.setParameter("id_category", id);

        try {
            return query.getSingleResult();

        } catch (NoResultException e) {
            throw new EntityNotFoundException("Categoria con ID " + id + " inexistente.");

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Se encontraron múltiples categorias para el id: " + id);
        }
    }

}
