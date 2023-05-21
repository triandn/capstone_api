package com.example.capstone_be.repository;

import com.example.capstone_be.dto.payment.PricePaymentDto;
import com.example.capstone_be.model.Guest;
import com.example.capstone_be.model.ImageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {

    String INSERT_GUEST_VALUE = "INSERT INTO public.guests(guest_type, quantity, time_id, user_id)\n" +
            "\tVALUES (:guestType, :quantity,:timeId, :userId)";
    @Modifying
    @Query(value = INSERT_GUEST_VALUE,nativeQuery = true)
    void saveGuest(@Param("guestType") String guestType,@Param("quantity")int quantity,@Param("timeId")UUID timeId, @Param("userId")UUID userId);
}