package com.example.capstone_be.controller;

import com.example.capstone_be.dto.order.OrderDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @GetMapping("/block-chain/")
    public ResponseEntity<?> blockchainSOLANA(){
//        var connection = new Connection("https://api.devnet.solana.com");
//        var wallet = new PublicKey("CDkZeuWohvZd3EB5GFfjayHiwj9otqdYoUKkECjBSxXv");
//        var balance = connection.getAccountInfo(wallet);
//        System.out.println("Balance in Lamports: " + balance);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/block/")
    public ResponseEntity<?> BLCTransaction() throws RpcException {
//        var connection = new Connection("https://api.devnet.solana.com");
//        var blockhash = connection.getLatestBlockhash();
//        // fund this account in case it is empty
//        var sender = Keypair.fromSecretKey(Base58.decode("4gEr3pvB7JKx34zXZ6HV5Q6ankt3g1mVW3DSjNzHauGXc5JsMTFDktp3quccTHGUkvFkjftJqGHGHMRJQTmzrHoX"));
//        var receiver = new PublicKey("FsrdMjgeTSmrAwppaGahn8vB7BFCAY8qA7ym3AJtPFYJ");
//        var instruction = new TransferInstruction(sender.getPublicKey(), receiver, 1000);
//        var transaction = new Transaction(
//                blockhash,
//                instruction,
//                sender.getPublicKey()
//        );
//        transaction.sign(sender);
//        var signature = connection.sendTransaction(transaction);
//        System.out.println("Transaction Signature: " + signature);

        var sender = Keypair.fromSecretKey(Base58.decode("3bhef8h2bj2cXQpA9UV7etxpcT1pJxspQcSCSuPVUEop6ERLLhY7fea9stNqoS7E4UoKQXPbBoMuDELs5EYebSNm"));
        Account signer = new Account(sender.getSecret());


        PublicKey toPublicKey = new PublicKey("5CJk1xzPAb8PAVEpR7yoxiZ6ua7otaNYacupgqY2MJES");

        // select cluster
        RpcClient client = new RpcClient(Cluster.DEVNET);

        // prepare transaction
        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.transfer(signer.getPublicKey(), toPublicKey, 1000));

        String signature = client.getApi().sendTransaction(transaction, signer);
//        System.out.println("tx signature: " + signature);
        System.out.println("tx signature: " + client.getApi().getProgramAccounts(PublicKey.valueOf("GXgcmDFGKQkkHanL6rijmjaTigVNDmQigavv3mK2MSUG")));

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
