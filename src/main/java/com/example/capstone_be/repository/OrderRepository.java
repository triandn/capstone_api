package com.example.capstone_be.repository;

import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.model.Order;
import org.hibernate.type.NumericBooleanType;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    String FIND_ORDER_BY_TOUR_ID = "SELECT * FROM orders AS o INNER JOIN time_book_details AS tbdt ON tbdt.time_id = o.time_id\n" +
            "INNER JOIN daybooks AS dbs ON dbs.day_book_id = tbdt.day_book_id\n" +
            "INNER JOIN tours AS ts ON ts.tour_id = dbs.tour_id WHERE ts.tour_id=:tour_id";
    String FIND_ORDER_BY_OWNER = "SELECT * FROM orders AS o INNER JOIN time_book_details AS tbdt ON tbdt.time_id = o.time_id \n" +
            "INNER JOIN daybooks AS dbt ON dbt.day_book_id = tbdt.day_book_id\n" +
            "INNER JOIN tours AS t ON t.tour_id = dbt.tour_id\n" +
            "INNER JOIN users AS u ON u.user_id = t.user_id\n" +
            "WHERE u.role='OWNER' AND u.user_id=:user_id";
    @Modifying
    @Query(value = "UPDATE orders SET status_order=:status_order WHERE order_id=:order_id",nativeQuery = true)
    void updateStatus(String status_order,UUID order_id);

    @Query(value = "SELECT * FROM orders WHERE order_id=:order_id",nativeQuery = true)
    Order getOrderByOrderId(UUID order_id);

    @Query(value = "SELECT * FROM orders WHERE orders.order_id_block_chain=:order_id_block_chain AND orders.public_key=:public_key",nativeQuery = true)
    Order getOrderByPublicKey(String order_id_block_chain,String public_key);

    @Query(value = FIND_ORDER_BY_TOUR_ID,nativeQuery = true)
    List<Order> getListOrderByTourId(@Param("tour_id")Long tour_id);

    @Query(value = "SELECT * FROM orders WHERE EXTRACT(MONTH FROM created_at)=:month AND user_id=:user_id",nativeQuery = true)
    List<Order> getOrderByMonth(int month,UUID user_id);

    @Query(value = FIND_ORDER_BY_OWNER,nativeQuery = true)
    Page<Order> getListOrderByOwner(@Param("user_id")UUID user_id, Pageable pageable);

    @Query(value = "SELECT SUM(price) AS total_revenue\n" +
            "FROM orders\n" +
            "WHERE EXTRACT(DAY FROM order_date) =:day_value AND status_order = 'SUCCESS' AND user_id=:user_id",nativeQuery = true)
    Double calVenueOneDay(int day_value, UUID user_id);
}
