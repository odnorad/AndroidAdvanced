package at.technikumwien.maps.data.remote;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import at.technikumwien.maps.data.OnDataLoadedCallback;
import at.technikumwien.maps.data.model.DrinkingFountain;
import at.technikumwien.maps.util.DownloadAsyncTask;

/**
 * Created by nicoleang on 17.10.17.
 */

public class AsyncTaskJsonObjectDrinkingFountainRepo implements DrinkingFountainRepo {
    @Override
    public void loadDrinkingFountains(OnDataLoadedCallback<List<DrinkingFountain>> callback) {
        new DownloadAsyncTask<List<DrinkingFountain>>(BASE_URL + GET_DRINKING_FOUNTAINS_PATH, callback){

            protected List<DrinkingFountain> parseJson(String json) throws JSONException {

                JSONObject jsonObject = new JSONObject(json);
                JSONArray features = jsonObject.getJSONArray("features");
                List<DrinkingFountain> drinkingFountains = new ArrayList<>(features.length());

                for (int i = 0; i < features.length(); i++) {

                    JSONObject drinkingFountainItem = features.getJSONObject(i);

                    //orderd by drinkingFountaionItem
                    JSONObject geometry = drinkingFountainItem.getJSONObject("geomtry");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");

                    JSONObject properties = drinkingFountainItem.getJSONObject("properties");
                    String name = properties.getString("NAME");

                    String id = drinkingFountainItem.getString("id");

                    double lat = coordinates.getDouble(1);
                    double lng = coordinates.getDouble(0);

                    drinkingFountains.add(DrinkingFountain.create(id, name, lat, lng));
                }

                return drinkingFountains;
            }
        }.execute();

    }
}
