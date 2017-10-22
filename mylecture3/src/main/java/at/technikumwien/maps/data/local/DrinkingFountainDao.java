package at.technikumwien.maps.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import at.technikumwien.maps.data.model.DrinkingFountain;

/**
 * Created by nicoleang on 18.10.17.
 */

@Dao
public interface DrinkingFountainDao {

    @Insert
    void insert(List<DrinkingFountain> drinkingFountains);

    @Query("SELECT * FROM drinking_fountain")
    List<DrinkingFountain> findAllDrinkingFountain();

    @Query("DELETE FROM drinking_fountain")
    void deleteAll();

    @Update
    void update(List<DrinkingFountain> drinkingFountains);

}
