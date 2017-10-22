package at.technikumwien.maps.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import at.technikumwien.maps.data.model.DrinkingFountain;

/**
 * Created by nicoleang on 22.10.17.
 *
 * Create a DrinkingFountainDao with three operations
 ▪ Inserting a list of drinking fountains
 ▪ Deleting all drinking fountains
 ▪ Loading all drinking fountains
 */

@Dao
public interface DrinkingFountainDao {

    @Query("SELECT * FROM drinking_fountain")
    List<DrinkingFountain> getDrinkingFountain();

    @Query("DELETE * FROM drinking_fountain")
    void deleteAll();

    @Insert
    void insertAll(List<DrinkingFountain> drinkingFountains);
}
