package br.unisep.edu.pdb3.orderservice.listener;

import br.unisep.edu.pdb3.orderservice.event.OrderPlacedEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPlacedEventListener {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    @EventListener
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        log.info("Evento de gravar ordem recebido, enviando OrderPlacedEvent para notificationTopic: {}", event.getOrderNumber());

        // Create Observation for Kafka Template
        try {
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, OrderPlacedEvent>> future = kafkaTemplate.send("notificationTopic",
                        new OrderPlacedEvent(event.getOrderNumber()));
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro enviando mensagem ao Kafka", e);
        }
    }
}
