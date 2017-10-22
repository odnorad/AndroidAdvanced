package at.technikumwien.maps.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import at.technikumwien.maps.data.model.DrinkingFountain;

/**
 * Created by nicoleang on 18.10.17.
 */

@Database( entities = { DrinkingFountain.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DrinkingFountainDao drinkingFountainDao();
}
