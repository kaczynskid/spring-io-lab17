package io.spring.lab.warehouse.item;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.spring.lab.warehouse.WarehouseApplication.INSTANCE_ID;
import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemService items;

    private final Environment env;

    @GetMapping
    List<ItemRepresentation> findAll() {
        List<Item> list = items.findAll();
        log.info("Found {} items.", list.size());
        return list.stream()
                .map(ItemRepresentation::of)
                .map(r -> r.withInstanceId(env.getRequiredProperty(INSTANCE_ID)))
                .collect(toList());
    }

    @GetMapping("/{id}")
    public ItemRepresentation findOne(@PathVariable("id") long id) {
        Item item = items.findOne(id);
        log.info("Found item {}.", item.getName());
        return ItemRepresentation.of(item)
                .withInstanceId(env.getRequiredProperty(INSTANCE_ID));
    }
}
