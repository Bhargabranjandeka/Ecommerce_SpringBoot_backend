package com.EcomMic.App.repositories;

import com.EcomMic.App.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {
}
