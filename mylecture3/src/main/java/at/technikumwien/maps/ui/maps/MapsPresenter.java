package at.technikumwien.maps.ui.maps;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import at.technikumwien.maps.AppDependencyManager;
import at.technikumwien.maps.data.OnDataLoadedCallback;
import at.technikumwien.maps.data.OnOperationSuccessfulCallback;
import at.technikumwien.maps.data.local.AppDatabase;
import at.technikumwien.maps.data.local.DrinkingFountainRepo;
import at.technikumwien.maps.data.model.DrinkingFountain;
import at.technikumwien.maps.data.remote.DrinkingFountainApi;
import at.technikumwien.maps.data.remote.response.DrinkingFountainResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MapsPresenter extends MvpBasePresenter<MapsView> {

    private final DrinkingFountainApi drinkingFountainApi;
    private final DrinkingFountainRepo drinkingFountaionRepo;

    public MapsPresenter(AppDependencyManager manager, AppDatabase appDatabase, DrinkingFountainRepo drinkingFountaionRepo) {
        drinkingFountainApi = manager.getDrinkingFountainApi();
        this.drinkingFountaionRepo = manager.getDrinkingFountainRepo();
    }

    public void loadDrinkingFountains() {
        drinkingFountainApi.getDrinkingFountains().enqueue(new Callback<DrinkingFountainResponse>() {
            @Override
            public void onResponse(Call<DrinkingFountainResponse> call, Response<DrinkingFountainResponse> response) {
                if(response.isSuccessful()) {
                    if(isViewAttached()) {
                        //Daten holen
                        getView().showDrinkingFountains(response.body().getDrinkingFountainList());
                    }
                    saveDataInRepo(response.body().getDrinkingFountainList());
                } else {
                    if(isViewAttached()) {
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

    public void loadDataLocalStorage(){
        drinkingFountaionRepo.loadAll(new OnDataLoadedCallback<List<DrinkingFountain>>() {
            @Override
            public void onDataLoaded(List<DrinkingFountain> data) {
                if(data.isEmpty()){
                    loadDataFromWebService();
                } else {
                    if(isViewAttached()){
                        getView().showDrinkingFountains(data);
                    }
                }
            }

            @Override
            public void onDataLoadError(Throwable throwable) {
                if(isViewAttached()){
                    getView().showLoadingError(throwable);
                }
            }
        });
    }

    public void loadDataFromWebService(){
        drinkingFountainApi.getDrinkingFountains().enqueue(new Callback<DrinkingFountainResponse>() {
            @Override
            public void onResponse(Call<DrinkingFountainResponse> call, Response<DrinkingFountainResponse> response) {
                if(response.isSuccessful()){
                    getView().showDrinkingFountains(response.body().getDrinkingFountainList());
                } else {
                    if(isViewAttached()) {
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

    // saveDataLocalStorage == saveDataLocalRepo ?
    public void saveDataInLocalStorage(List<DrinkingFountain> list){
            drinkingFountaionRepo.refreshList(new OnOperationSuccessfulCallback() {
                @Override
                public void onOperationSuccessful() {

                }

                @Override
                public void onOperationError(Throwable throwable) {

                }
            }, list);
    }

    public void saveDataInRepo(List<DrinkingFountain> list){
        drinkingFountaionRepo.refreshList(new OnOperationSuccessfulCallback() {
            @Override
            public void onOperationSuccessful() {

            }

            @Override
            public void onOperationError(Throwable throwable) {

            }
        }, list);
    }
}
