package io.spring.lab.warehouse.item;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.spring.lab.warehouse.SpringTestBase;

import static io.spring.lab.warehouse.WarehousePersistenceConfig.testItemsData;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ItemControllerTest extends SpringTestBase {

    @MockBean
    ItemService items;

    @Autowired
    MockMvc mvc;

    @Test
    public void shouldGetAllItems() throws Exception {
        doReturn(testItemsData())
                .when(items).findAll();

        mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(4)))
                .andExpect(jsonPath("$.[0].name").value("A"))
                .andExpect(jsonPath("$.[0].price").value("40.0"));
    }
}
