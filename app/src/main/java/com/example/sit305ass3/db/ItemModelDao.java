package com.example.sit305ass3.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sit305ass3.db.ItemModel;

import java.util.List;

@Dao
public interface ItemModelDao
{
    @Query("SELECT * FROM itemmodel")
    List<ItemModel> getAllItems();

    @Query("SELECT * FROM itemmodel WHERE category = :category")
    List<ItemModel> getItemsByCategory(String category);

    @Query("SELECT * FROM itemmodel WHERE id = :id")
    ItemModel getItemByid(int id);

    @Query("DELETE FROM itemmodel WHERE id = :id")
    void deleteItemById(int id);
    @Insert
    void insertItem(ItemModel... itemModels);

    @Delete
    void delete(ItemModel itemModel);


}
