package at.technikumwien.maps.data.remote.retrofit.response;

import at.technikumwien.maps.data.remote.DrinkingFountainRepo;
import at.technikumwien.maps.data.remote.retrofit.response.response.DrinkingFountainResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nicoleang on 18.10.17.
 *
 * Create a RetrofitDrinkingFountainApi class that defines the Retrofit interface
 * for the web service and uses the DrinkingFountainResponse as return value
 */

public interface RetrofitDrinkingFountainApi {
    @GET(DrinkingFountainRepo.GET_DRINKING_FOUNTAINS_PATH)
    Call<DrinkingFountainResponse> getDrinkingFountain();
}
