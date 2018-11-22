package io.spring.lab.store.item;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import io.spring.lab.cloud.ConditionalOnFeignClient;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@Component
@ConditionalOnFeignClient
@AllArgsConstructor
public class DeclarativeItemsClient implements ItemsClient {

    private final FeignItemsClient items;

    private final ItemStreamsBinding binding;

    @Override
    public ItemRepresentation findOne(long id) {
        ItemRepresentation representation = items.findOne(APPLICATION_JSON_UTF8_VALUE, id);
        log.info("Declarative client got item from instance: {}", representation.getInstanceId());
        return representation;
    }

    @Override
    public void updateStock(ItemStockUpdate changes) {
        log.info("Declarative client updates item {} stock by {}", changes.getId(), changes.getCountDiff());

        //items.updateStock(changes.getId(), new FeignItemStockUpdate(changes.getCountDiff()));

        binding.checkoutItem().send(MessageBuilder.withPayload(changes).build());
    }

    @FeignClient(name = "warehouse", path = "/items")
    interface FeignItemsClient {

        @GetMapping(path = "/{id}")
        ItemRepresentation findOne(@RequestHeader("Accept") String accept,
                                   @PathVariable("id") long id);

        @PutMapping("/{id}/stock")
        ItemRepresentation updateStock(@PathVariable("id") long id,
                                       @RequestBody FeignItemStockUpdate changes);
    }

    @Value
    static class FeignItemStockUpdate {
        private int countDiff;
    }
}
