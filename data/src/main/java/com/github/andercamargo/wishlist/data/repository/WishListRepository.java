package com.github.andercamargo.wishlist.data.repository;

import com.github.andercamargo.wishlist.data.model.Wishlist;
import com.github.andercamargo.wishlist.data.repository.interfaces.IWishListRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class WishListRepository implements IWishListRepository {
    private final MongoClient client;
    private MongoCollection<Wishlist> wishlistCollection;

    public WishListRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        wishlistCollection = this.client.getDatabase("wishlist").getCollection("wishlist", Wishlist.class);
    }


    @Override
    public Wishlist findByProductNameAndCustomer(String productName, Integer customerId) {
        return wishlistCollection.find(
                and( eq("products.name", productName),
                        eq("customer._id", customerId))
                ).first();
    }

    @Override
    public Wishlist findByProductIdAndCustomer(Integer productId, Integer customerId) {
        return wishlistCollection.find(
                and(elemMatch("products",  eq("_id", productId)),
                        eq("customer._id", customerId))
        ).projection(fields(include("products", "customer"))
        ).first();
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        wishlist.setId(Objects.requireNonNull(wishlistCollection.insertOne(wishlist).getInsertedId().asObjectId().getValue()));
        return wishlist;
    }

    @Override
    public Wishlist update(Wishlist wishlist) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return wishlistCollection.findOneAndReplace(eq("_id",
                wishlist.getId()), wishlist, options);
    }

    @Override
    public Wishlist findAllByCostumer(Integer customerId) {
        return wishlistCollection.find(
                        eq("customer._id", customerId))
                .first();
    }

    @Override
    public long delete(Wishlist wishlist) {
        return  wishlistCollection.deleteOne(
                eq("_id",wishlist.getId())).getDeletedCount();
    }
}