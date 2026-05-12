package com.example.inventorybffservice.service;

import com.example.inventorybffservice.document.InventoryDocument;
import com.example.inventorybffservice.repository.InventorySearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventorySearchServiceTest {

    @Mock
    private InventorySearchRepository repository;

    @InjectMocks
    private InventorySearchService service;

    @Test
    void shouldReturnInventorySuccessfully() {

        InventoryDocument document =
                new InventoryDocument();

        document.setItemId("ITEM1001");

        document.setName("iPhone 20");

        document.setDescription("Apple Mobile");

        document.setCategory("mobile");

        document.setAvailableStock(100);

        document.setReservedStock(20);

        when(repository.findAll())
                .thenReturn(List.of(document));

        Iterable<InventoryDocument> result =
                service.getInventory();

        List<InventoryDocument> inventory =
                (List<InventoryDocument>) result;

        assertFalse(inventory.isEmpty());

        assertEquals(
                "ITEM1001",
                inventory.get(0).getItemId()
        );

        assertEquals(
                "iPhone 20",
                inventory.get(0).getName()
        );

        assertEquals(
                100,
                inventory.get(0).getAvailableStock()
        );

        verify(repository, times(1))
                .findAll();
    }
}
