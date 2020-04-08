package com.example.eatitv2.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CartDAO { //database access object
        @Query("SELECT * FROM Cart WHERE uid=:uid")
        Flowable<List<CartItem>>getAllCart(String uid);

        @Query("SELECT COUNT(*) from Cart WHERE uid=:uid")
        Single<Integer> countItemInCart (String uid);

        @Query("SELECT SUM(foodPrice*foodQuantity) + (foodExtraPrice*foodQuantity) WHERE uid=:uid")
        Single<Long> sumPriceInCart (String uid);

        @Query("SELECT * FROM Cart WHERE foodId=:foodId AND uid=:uid")
        Single<CartItem> getItemInCart (String foodId,String uid);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Completable insertOrReplaceAll(CartItem... cartItems);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        Single<Integer> updateCartItems(CartItem cartItems);

        @Delete
        Single<Integer> deleteCartItems(CartItem cartItems);

        @Query("DELETE FROM Cart WHERE uid=:uid")
        Single<Integer> cleanCart (String uid);


}
