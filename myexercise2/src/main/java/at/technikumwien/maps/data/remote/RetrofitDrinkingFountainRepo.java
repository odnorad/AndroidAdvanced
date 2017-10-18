package at.technikumwien.maps.data.remote;

import java.util.ArrayList;
import java.util.List;

import at.technikumwien.maps.AppDependencyManager;
import at.technikumwien.maps.data.OnDataLoadedCallback;
import at.technikumwien.maps.data.model.DrinkingFountain;
import at.technikumwien.maps.data.remote.retrofit.response.RetrofitDrinkingFountainApi;
import at.technikumwien.maps.data.remote.retrofit.response.response.DrinkingFountainResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitDrinkingFountainRepo implements DrinkingFountainRepo {

    private final RetrofitDrinkingFountainApi drinkingFountainApi;

    public RetrofitDrinkingFountainRepo(AppDependencyManager manager) {
        this.drinkingFountainApi = manager.getRetrofitDrinkingFountainApi();
    }

    @Override
    public void loadDrinkingFountains(final OnDataLoadedCallback<List<DrinkingFountain>> callback) {
        drinkingFountainApi.getDrinkingFountain().enqueue(new Callback<DrinkingFountainResponse>() {
            @Override
            public void onResponse(Call<DrinkingFountainResponse> call, Response<DrinkingFountainResponse> response) {
                if(response.isSuccessful()){
                    callback.onDataLoaded(response.body().getDrinkingFountainList());
                } else {
                    callback.onDataLoadError(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<DrinkingFountainResponse> call, Throwable throwable) {
                callback.onDataLoadError(throwable);
            }
        });
    }
}