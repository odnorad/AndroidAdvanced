package at.technikumwien.maps.util.managers;

import android.util.Log;

import java.util.List;

import at.technikumwien.maps.AppDependencyManager;
import at.technikumwien.maps.data.NoOpOnOperationSuccessfulCallback;
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
 * Created by nicoleang on 06.11.17.
 */

public class SyncManager {

    public static String TAG = SyncManager.class.getSimpleName();

    private DrinkingFountainRepo drinkingFountainRepo;
    private DrinkingFountainApi drinkingFountainApi;

    private SyncManager(AppDependencyManager manager){
        drinkingFountainRepo = manager.getDrinkingFountainRepo();
        drinkingFountainApi = manager.getDrinkingFountainApi();
    }

    public Cancelable syncDrinkingFountains(final OnOperationSuccessfulCallback callback, final OnDataLoadedCallback<List<DrinkingFountain>> loadedCallback){
        final Call<DrinkingFountainResponse> c = drinkingFountainApi.getDrinkingFountains();
            c.enqueue(new Callback<DrinkingFountainResponse>() {
                @Override
                public void onResponse(Call<DrinkingFountainResponse> call, Response<DrinkingFountainResponse> response) {
                    if(response.isSuccessful()){
                        List<DrinkingFountain> drinkingFountains = response.body().getDrinkingFountainList();
                        if(loadedCallback != null) {
                            loadedCallback.onDataLoaded(drinkingFountains);
                            drinkingFountainRepo.refreshList(callback, drinkingFountains);
                        } else {
                            HttpException exception = new HttpException(response);
                            callback.onOperationError(exception);
                            if(loadedCallback != null){
                                loadedCallback.onDataLoadError(exception);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<DrinkingFountainResponse> call, Throwable throwable) {
                    callback.onOperationError(throwable);
                    if(loadedCallback != null){
                        loadedCallback.onDataLoadError(throwable);
                    }
                }
            });
        return new CallCancelable(c);
    }

    public Cancelable syncDrinkingFountains(OnOperationSuccessfulCallback callback){
        return syncDrinkingFountains(callback, null);
    }

    public void loadDrinkingFountains(final OnDataLoadedCallback<List<DrinkingFountain>> callback){
        drinkingFountainRepo.loadAll(new OnDataLoadedCallback<List<DrinkingFountain>>() {
            @Override
            public void onDataLoaded(List<DrinkingFountain> data) {
                if(data.isEmpty()){
                    Log.i(TAG, "loadDrinkingFountains(): No local data, starting sync");
                    syncDrinkingFountains(new NoOpOnOperationSuccessfulCallback(), callback );
                }else{
                    Log.i(TAG, "loadDrinkingFountains(): Loaded local data");
                    callback.onDataLoaded(data);
                }
            }

            @Override
            public void onDataLoadError(Throwable throwable) {
                callback.onDataLoadError(throwable);
            }
        });
    }

    private class CallCancelable implements Cancelable {
        private final Call<?> call;

        private CallCancelable(Call<?> call) {
            this.call = call;
        }

        @Override
        public void cancel() {
            call.cancel();
        }

        @Override
        public boolean isCanceled() {
            return call.isCanceled();
        }
    }
}
