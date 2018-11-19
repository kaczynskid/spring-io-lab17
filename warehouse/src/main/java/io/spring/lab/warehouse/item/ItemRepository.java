package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    boolean isEmpty();

    Optional<Item> findOne(long id);

    List<Item> findAll();

    Item save(Item item);
}
