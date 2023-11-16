package br.unisep.edu.pdb3.orderservice.service;

import br.unisep.edu.pdb3.orderservice.dto.InventoryResponse;
import br.unisep.edu.pdb3.orderservice.dto.OrderLineItemsDto;
import br.unisep.edu.pdb3.orderservice.dto.OrderRequest;
import br.unisep.edu.pdb3.orderservice.model.Order;
import br.unisep.edu.pdb3.orderservice.model.OrderLineItems;
import br.unisep.edu.pdb3.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest
                .getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order
                .getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponseArray = webClientBuilder
                .build()
                .post()
                .uri("http://inventory-service/api/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(skuCodes)
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);
        if (allProductInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Produto sem estoque");
        }
    }
}
