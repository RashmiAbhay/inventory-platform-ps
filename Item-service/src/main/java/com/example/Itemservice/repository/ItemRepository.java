package com.example.Itemservice.repository;


import com.example.Itemservice.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository
        extends MongoRepository<Item, String>,
        ItemCustomRepository {
}
