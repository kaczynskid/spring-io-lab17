package io.spring.lab.warehouse.item;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class ItemRepresentationTest {

    @Autowired
    ResourceLoader loader;

    @Autowired
    JacksonTester<ItemRepresentation> tester;

    @Test
    public void shouldSerialize() throws IOException {
        // given
        ItemRepresentation representation =
                new ItemRepresentation("testItem", 10, BigDecimal.valueOf(1250, 2), "testInstance");

        // when
        JsonContent<ItemRepresentation> json = tester.write(representation);

        // then
        assertThat(json).extractingJsonPathStringValue("@.name").isEqualTo("testItem");
        assertThat(json).extractingJsonPathNumberValue("@.count").isEqualTo(10);
        assertThat(json).extractingJsonPathNumberValue("@.price").isEqualTo(12.5);
        assertThat(json).extractingJsonPathStringValue("@.instanceId").isEqualTo("testInstance");
    }

    @Test
    public void shouldDeserialize() throws IOException {
        // given
        Resource json = loader.getResource("classpath:item-representation.json");

        // when
        ItemRepresentation representation = tester.read(json).getObject();

        // then
        assertThat(representation).isNotNull();
        assertThat(representation.getName()).isEqualTo("A");
        assertThat(representation.getStock()).isEqualTo(100);
        assertThat(representation.getPrice()).isEqualTo(BigDecimal.valueOf(40.0));
    }
}
