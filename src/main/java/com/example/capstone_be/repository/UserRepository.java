package com.example.capstone_be.repository;

import com.example.capstone_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    String FIND_USER_BY_ORDERS = "SELECT * FROM users INNER JOIN tours ON tours.user_id = users.user_id\n" +
            "INNER JOIN daybooks ON daybooks.tour_id = tours.tour_id\n" +
            "INNER JOIN time_book_details ON time_book_details.day_book_id = daybooks.day_book_id\n" +
            "INNER JOIN orders ON orders.time_id = time_book_details.time_id\n" +
            "WHERE orders.order_id=:order_id";
    User findByUserEmailIgnoreCase(String emailId);

    Boolean existsByUserEmail(String user_email);

    @Query(value = "SELECT * FROM users WHERE user_email=:user_email", nativeQuery = true)
    User getUserByUserEmail(String user_email);

    @Query(value = "SELECT * FROM users WHERE user_id=:user_id", nativeQuery = true)
    User getUserByUserId(UUID user_id);

    @Modifying
    @Query(value = "UPDATE users SET role=:role WHERE users.user_id =:user_id",nativeQuery = true)
    void updateRole(UUID user_id, String role);

    @Query(value = "SELECT user_password FROM users WHERE user_email=:user_email", nativeQuery = true)
    String getPasswordByEmail(String user_email);

    @Modifying
    @Query(value = "UPDATE users SET user_name=:user_name, description=:description,address=:address,phone_number=:phone_number,language=:language,url_image=:url_image WHERE users.user_id=:user_id",nativeQuery = true)
    void updateProfile(String user_name,String description, String address,String phone_number,String language,String url_image,UUID user_id);

    @Query(value = "SELECT * FROM users INNER JOIN tours ON tours.user_id = users.user_id\n" +
            "WHERE tours.tour_id=:tour_id",nativeQuery = true)
    User getUserByTourId(Long tour_id);

    @Query(value = FIND_USER_BY_ORDERS,nativeQuery = true)
    User getUserByOrderId(@Param("order_id")UUID order_id);
}