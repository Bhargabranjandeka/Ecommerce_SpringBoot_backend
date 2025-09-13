package com.EcomMic.App.repositories;

import com.EcomMic.App.model.CartItem;
import com.EcomMic.App.model.Product;
import com.EcomMic.App.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    void deleteByUserId(Long userId);
}
