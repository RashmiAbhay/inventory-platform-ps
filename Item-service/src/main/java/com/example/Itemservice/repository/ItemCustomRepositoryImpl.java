package com.example.Itemservice.repository;


import com.example.Itemservice.dto.ItemRequest;
import com.example.Itemservice.model.Item;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemCustomRepositoryImpl
        implements ItemCustomRepository {
    public ItemCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Item> searchItems(ItemRequest request) {

        List<Criteria> criteriaList = new ArrayList<>();

        if (request.getName() != null) {
            criteriaList.add(
                    Criteria.where("name")
                            .regex(request.getName(), "i")
            );
        }

        if (request.getCategory() != null) {
            criteriaList.add(
                    Criteria.where("category")
                            .is(request.getCategory())
            );
        }

        if (request.getMinPrice() != null) {
            criteriaList.add(
                    Criteria.where("price")
                            .gte(request.getMinPrice())
            );
        }

        if (request.getMaxPrice() != null) {
            criteriaList.add(
                    Criteria.where("price")
                            .lte(request.getMaxPrice())
            );
        }

        Query query = new Query();

        if (!criteriaList.isEmpty()) {
            query.addCriteria(
                    new Criteria().andOperator(
                            criteriaList.toArray(new Criteria[0])
                    )
            );
        }

        return mongoTemplate.find(query, Item.class);
    }
}
