package com.example.capstone_be.controller;

import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.dto.tour.UpdateTimeTourDto;
import com.example.capstone_be.model.Order;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.service.OrderService;
import org.bitcoinj.core.Base58;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.Transaction;
import org.p2p.solanaj.programs.SystemProgram;
import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcClient;
//import org.sol4k.*;
import org.p2p.solanaj.rpc.RpcException;
import org.sol4k.Keypair;
import org.sol4k.instruction.TransferInstruction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all/")
    public ResponseEntity<List<OrderDto>> getAllOrder() {
        final List<OrderDto> orderDtoList = orderService.getAllOrder();
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }
    @GetMapping("/create-request/{orderId}/{status}")
    public ResponseEntity<?> createRequest(@PathVariable UUID orderId, @PathVariable String status) {
        orderService.requestFromAdmin(status,orderId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PatchMapping("/order-update/{id}")
    public Order updateOrder(@PathVariable UUID id, @RequestBody Map<String,Object> fields) {
        return orderService.updateOrderByField(id,fields);
    }

    @GetMapping("/authorize-order/{order_id_blockchain}/{public_key}")
    public ResponseEntity<?> authorizeOrder(@PathVariable String order_id_blockchain,@PathVariable String public_key) {
        orderService.authorizeOrder(order_id_blockchain,public_key);
        return new ResponseEntity<>("Authorize Success", HttpStatus.OK);
    }
}
