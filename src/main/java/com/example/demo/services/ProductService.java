package com.example.demo.services;
 
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductImage;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.ProductImagesRepository;
import com.example.demo.repositories.CategoryRepository;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
 
    @Autowired
    private ProductRepository productRepository;
 
    @Autowired
    private ProductImagesRepository productImagesRepository;
 
    @Autowired
    private CategoryRepository categoryRepository;
 
    public List<Product> getProductsByCategory(String categoryName) {
        if (categoryName != null && !categoryName.isEmpty()) {
            Optional<Category> categoryOpt = categoryRepository.findByCategoryName(categoryName);
            if (categoryOpt.isPresent()) {
                Category category = categoryOpt.get();
                return productRepository.findByCategory_CategoryId(category.getCategoryId());
            } else {
                throw new RuntimeException("Category not found");
            }
        } else {
            return productRepository.findAll();
        }
    }
 
    public List<String> getProductImages(Integer productId) {
        List<ProductImage> productImages = productImagesRepository.findByProduct_ProductId(productId);
        List<String> imageUrls = new ArrayList<>();
        for (ProductImage image : productImages) {
            imageUrls.add(image.getImageUrl());
        }
        return imageUrls;
    }
}
