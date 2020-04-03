package com.example.stockinformationtask.roomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface SearchDao {
    @Query("SELECT * FROM dataBaseSearchEntity")
    List<DataBaseSearchEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DataBaseSearchEntity dataBaseSearchEntity);

    @Delete
    void delete(DataBaseSearchEntity dataBaseSearchEntity);

    @Update
    void update(DataBaseSearchEntity dataBaseSearchEntity);


    @Query("SELECT * FROM databaseforexentity")
    List<DataBaseForexEntity> getAllForex();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DataBaseForexEntity databaseforexentity);

    @Delete
    void delete(DataBaseForexEntity databaseforexentity);

    @Update
    void update(DataBaseForexEntity databaseforexentity);
}
