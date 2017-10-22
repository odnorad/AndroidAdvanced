package at.technikumwien.maps.data.local;

import java.util.List;

import at.technikumwien.maps.AppDependencyManager;
import at.technikumwien.maps.data.OnDataLoadedCallback;
import at.technikumwien.maps.data.OnOperationSuccessfulCallback;
import at.technikumwien.maps.data.model.DrinkingFountain;
import at.technikumwien.maps.util.LoadDataAsyncTask;
import at.technikumwien.maps.util.OperationAsyncTask;

/**
 * Created by nicoleang on 18.10.17.
 */

public class RoomDrinkingFountainRepo implements DrinkingFountainRepo {

    private final AppDatabase appDatabase;

    public RoomDrinkingFountainRepo(AppDependencyManager manager) {
        this.appDatabase = manager.getAppDatabase();
    }


    @Override
    public void refreshList(OnOperationSuccessfulCallback callback, final List<DrinkingFountain> drinkingFountains) {
        new OperationAsyncTask(callback){

            @Override
            public void doOperation() throws Throwable {
                appDatabase.drinkingFountainDao().deleteAll();
                appDatabase.drinkingFountainDao().insert(drinkingFountains);
                appDatabase.drinkingFountainDao().update(drinkingFountains);
            }
        } .execute();
    }

    @Override
    public void loadAll(OnDataLoadedCallback<List<DrinkingFountain>> callback) {
        new LoadDataAsyncTask<List<DrinkingFountain>>(callback){

            @Override
            public List<DrinkingFountain> loadData() throws Throwable {
                return appDatabase.drinkingFountainDao().findAllDrinkingFountain();
            }
        }.execute();
    }

    public void increasCounter(String id){

    }
}
