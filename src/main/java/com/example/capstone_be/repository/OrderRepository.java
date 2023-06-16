package com.example.capstone_be.repository;

import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Modifying
    @Query(value = "UPDATE orders SET status_order=:status_order WHERE order_id=:order_id",nativeQuery = true)
    void updateStatus(String status_order,UUID order_id);

    @Query(value = "SELECT * FROM orders WHERE order_id=:order_id",nativeQuery = true)
    Order getOrderByOrderId(UUID order_id);

    @Query(value = "SELECT * FROM orders WHERE orders.order_id_block_chain=:order_id_block_chain AND orders.public_key=:public_key",nativeQuery = true)
    Order getOrderByPublicKey(String order_id_block_chain,String public_key);

}
