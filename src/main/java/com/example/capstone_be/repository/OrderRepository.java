package com.example.capstone_be.repository;

import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    String FIND_ORDER_BY_TOUR_ID = "SELECT * FROM orders AS o INNER JOIN time_book_details AS tbdt ON tbdt.time_id = o.time_id\n" +
            "INNER JOIN daybooks AS dbs ON dbs.day_book_id = tbdt.day_book_id\n" +
            "INNER JOIN tours AS ts ON ts.tour_id = dbs.tour_id WHERE ts.tour_id=:tour_id";
    @Modifying
    @Query(value = "UPDATE orders SET status_order=:status_order WHERE order_id=:order_id",nativeQuery = true)
    void updateStatus(String status_order,UUID order_id);

    @Query(value = "SELECT * FROM orders WHERE order_id=:order_id",nativeQuery = true)
    Order getOrderByOrderId(UUID order_id);

    @Query(value = "SELECT * FROM orders WHERE orders.order_id_block_chain=:order_id_block_chain AND orders.public_key=:public_key",nativeQuery = true)
    Order getOrderByPublicKey(String order_id_block_chain,String public_key);

    @Query(value = FIND_ORDER_BY_TOUR_ID,nativeQuery = true)
    List<Order> getListOrderByTourId(@Param("tour_id")Long tour_id);



}
