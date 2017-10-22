package at.technikumwien.maps.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import at.technikumwien.maps.data.model.DrinkingFountain;

/**
 * Created by nicoleang on 22.10.17.
 *
 * Create an AppDatabase class, which provides the DrinkingFountainDao
 */

@Database(entities = {DrinkingFountain.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract DrinkingFountainDao drinkingFountainDao();
}
