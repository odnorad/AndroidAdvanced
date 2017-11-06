package at.technikumwien.maps.util.managers;

import java.util.List;

import at.technikumwien.maps.AppDependencyManager;
import at.technikumwien.maps.data.OnDataLoadedCallback;
import at.technikumwien.maps.data.OnOperationSuccessfulCallback;
import at.technikumwien.maps.data.local.DrinkingFountainRepo;
import at.technikumwien.maps.data.model.DrinkingFountain;
import at.technikumwien.maps.data.remote.DrinkingFountainApi;
import at.technikumwien.maps.data.remote.response.DrinkingFountainResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by nicoleang on 23.10.17.
 */

public class SyncManager {

    private DrinkingFountainApi drinkingFountainApi;
    private DrinkingFountainRepo drinkingFountainRepo;

    public SyncManager(AppDependencyManager manager){
        drinkingFountainApi = manager.getDrinkingFountainApi();
        drinkingFountainRepo = manager.getDrinkingFountainRepo();
    }

    public Cancelable syncDrinkingFountain(final OnOperationSuccessfulCallback callback,
                                     final OnDataLoadedCallback<List<DrinkingFountain>> dataLoadedCallback){
        drinkingFountainApi.getDrinkingFountains().enqueue(new Callback<DrinkingFountainResponse>() {
            @Override
            public void onResponse(Call<DrinkingFountainResponse> call, Response<DrinkingFountainResponse> response) {

                if(response.isSuccessful()){
                    List<DrinkingFountain> drinkingFountainList = response.body().getDrinkingFountainList();
                    drinkingFountainRepo.refreshList(callback, drinkingFountainList);
                    dataLoadedCallback.onDataLoaded(drinkingFountainList);
                }
            }

            @Override
            public void onFailure(Call<DrinkingFountainResponse> call, Throwable throwable) {
                callback.onOperationError(throwable);
                dataLoadedCallback.onDataLoadError(throwable);
            }
        });
        return null;

    }
}
