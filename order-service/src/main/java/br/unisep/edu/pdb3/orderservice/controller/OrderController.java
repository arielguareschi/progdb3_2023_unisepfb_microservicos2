package br.unisep.edu.pdb3.orderservice.controller;


import br.unisep.edu.pdb3.orderservice.dto.OrderRequest;
import br.unisep.edu.pdb3.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Ordem criada com sucesso!";
    }
}
