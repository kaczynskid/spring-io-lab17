package io.spring.lab.warehouse.item;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemService items;

    @GetMapping
    List<ItemRepresentation> findAll() {
        return items.findAll().stream()
                .map(ItemRepresentation::of)
                .collect(toList());
    }
}
