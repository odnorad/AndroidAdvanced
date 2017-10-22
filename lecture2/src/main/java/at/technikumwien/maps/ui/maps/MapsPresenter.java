package at.technikumwien.maps.ui.maps;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import at.technikumwien.maps.AppDependencyManager;
import at.technikumwien.maps.data.OnDataLoadedCallback;
import at.technikumwien.maps.data.model.DrinkingFountain;
import at.technikumwien.maps.data.remote.DrinkingFountainRepo;
import at.technikumwien.maps.data.remote.retrofit.DrinkingFountainApi;
import at.technikumwien.maps.data.remote.retrofit.response.DrinkingFountainResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MapsPresenter extends MvpBasePresenter<MapsView> {

    private final DrinkingFountainRepo drinkingFountainRepo;
    private final DrinkingFountainApi drinkingFountainApi;

    public MapsPresenter(AppDependencyManager manager) {
        drinkingFountainRepo = manager.getDrinkingFountainRepo();
        drinkingFountainApi = manager.getDrinkingFountainApi();
    }

    public void loadDrinkingFountains() {
        drinkingFountainApi.getDrinkingFountains().enqueue(new Callback<DrinkingFountainResponse>() {
            @Override
            public void onResponse(Call<DrinkingFountainResponse> call, Response<DrinkingFountainResponse> response) {
                if(isViewAttached()) {
                    if(response.isSuccessful()) {
                        getView().showDrinkingFountains(response.body().toDrinkingFountainList());
                    } else {
                        getView().showLoadingError(new HttpException(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<DrinkingFountainResponse> call, Throwable throwable) {
                if(isViewAttached()) {
                    getView().showLoadingError(throwable);
                }
            }
        });
    }
}
