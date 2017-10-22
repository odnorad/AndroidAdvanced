package at.technikumwien.maps.data.local;

import java.util.List;

import at.technikumwien.maps.AppDependencyManager;
import at.technikumwien.maps.data.OnDataLoadedCallback;
import at.technikumwien.maps.data.OnOperationSuccessfulCallback;
import at.technikumwien.maps.data.model.DrinkingFountain;
import at.technikumwien.maps.util.LoadDataAsyncTask;
import at.technikumwien.maps.util.OperationAsyncTask;

/**
 * Created by nicoleang on 22.10.17.
 *
 * Creating an implementation of DrinkingFountainRepo that uses
 * the Room database and DAO we prepared
 */

public class RoomDrinkingFountainRepo implements DrinkingFountainRepo{

    private final DrinkingFountainDao drinkingFountainDao;

    public RoomDrinkingFountainRepo(AppDependencyManager manager) {
        this.drinkingFountainDao = manager.getDb().drinkingFountainDao();
    }

    /*
    * Use the LoadDataAsyncTask and OperationAsyncTask util
    * classes to execute I/O operations on a background thread
    * ▪ Also take a look at how these classes are implemented
    * ▪ refreshList() should first delete all local data and then save all
    * drinking fountains provided as parameter in the database
    * ▪ loadAll() should load all drinking fountains in the database
    * and return the data as list
    * */

    @Override
    public void refreshList(OnOperationSuccessfulCallback callback, final List<DrinkingFountain> drinkingFountains) {
        //public OperationAsyncTask(OnOperationSuccessfulCallback callback) { this.callback = callback; }

        new OperationAsyncTask(callback) {
            @Override
            public void doOperation() throws Throwable {
                drinkingFountainDao.deleteAll();
                drinkingFountainDao.insertAll(drinkingFountains);
            }
        };
    }

    @Override
    public void loadAll(OnDataLoadedCallback<List<DrinkingFountain>> callback) {
        //public LoadDataAsyncTask(OnDataLoadedCallback<T> callback) { this.callback = callback; }
        new LoadDataAsyncTask<List<DrinkingFountain>>(callback) {
            @Override
            public List<DrinkingFountain> loadData() throws Throwable {
                return drinkingFountainDao.getDrinkingFountain();
            }
        }.execute();
    }
}
