package com.example.capstone_be.controller;

import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.model.User;
import com.example.capstone_be.repository.UserRepository;
import com.example.capstone_be.response.OrderRespone;
import com.example.capstone_be.service.OrderService;
import com.example.capstone_be.util.common.CommonFunction;
import io.jsonwebtoken.Claims;
//import org.sol4k.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/all/")
    public ResponseEntity<List<OrderDto>> getAllOrder() {
        final List<OrderDto> orderDtoList = orderService.getAllOrder();
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }
    @GetMapping("/all/owner")
    public ResponseEntity<?> getAllOrderOwner(HttpServletRequest request,
                                                           @RequestParam(defaultValue = "1") Integer pageNo,
                                                           @RequestParam(defaultValue = "5") Integer pageSize) {
        String bearerToken = CommonFunction.getBearToken(request);
        Claims claims = CommonFunction.getClaims(bearerToken);
        String email = claims.getSubject();
        User user = userRepository.getUserByUserEmail(email);
        final OrderRespone orderRespone = orderService.getListOrderByOwner(user.getUserId(),pageNo,pageSize);
        return new ResponseEntity<>(orderRespone, HttpStatus.OK);
    }
    @GetMapping("/create-request/{orderId}/{status}")
    public ResponseEntity<?> createRequest(@PathVariable UUID orderId, @PathVariable String status) {
        orderService.requestFromAdmin(status,orderId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PatchMapping("/order-update/{id}")
    public ResponseEntity<?>  updateOrder(@PathVariable UUID id, @RequestBody Map<String,Object> fields) {
        orderService.updateOrderByField(id,fields);
        return new ResponseEntity<>("UPDATE SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/authorize-order/{order_id_blockchain}/{public_key}")
    public ResponseEntity<?> authorizeOrder(@PathVariable String order_id_blockchain,@PathVariable String public_key) {
        String result = orderService.authorizeOrder(order_id_blockchain,public_key);
        if(result.equals("SUCCESS"))
        {
            return new ResponseEntity<>("Authorize Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Authorize Fail", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-list-order/{tour_id}")
    public ResponseEntity<?> getOrderListByTourId(@PathVariable String tour_id) {
        final List<OrderDto> orderDtoList = orderService.getListOrderByTourId(Long.valueOf(tour_id));
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }
}
