package com.example.it322020;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.it322020.Listeners.OnDetailsAPIListener;
import com.example.it322020.Listeners.OnGameClickAListener;
import com.example.it322020.Listeners.OnSearchAPIListener;
import com.example.it322020.Models.DetailAPIResponse;
import com.example.it322020.Models.SearchAPIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.rawg.io/api/").addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void searchGames(OnSearchAPIListener listener, String game_name)
    {
        getGames getGames = retrofit.create(RequestManager.getGames.class);
        Call<SearchAPIResponse> call = getGames.callGames(game_name);

        call.enqueue(new Callback<SearchAPIResponse>() {
            @Override
            public void onResponse(Call<SearchAPIResponse> call, Response<SearchAPIResponse> response) {
                if (response.isSuccessful())
                {
                    listener.onResponse(response.body());
                }
                else
                {
                    Toast.makeText(context,"couldnt fetch data", Toast.LENGTH_SHORT);
                    return;
                }
            }

            @Override
            public void onFailure(Call<SearchAPIResponse> call, Throwable t) {
            listener.onError(t.getMessage());
            }
        });
    }
    public void searchGamesDetails(OnDetailsAPIListener listener, String slur)
    {
        getGamesDetails getGamesDetails = retrofit.create(RequestManager.getGamesDetails.class);
        Call<DetailAPIResponse> call = getGamesDetails.callGamesDetails(slur);

        call.enqueue(new Callback<DetailAPIResponse>() {
            @Override
            public void onResponse(Call<DetailAPIResponse> call, Response<DetailAPIResponse> response) {
                if (response.isSuccessful())
                {
                    listener.onResponse(response.body());
                }
                else
                {
                    Toast.makeText(context,"couldnt fetch data", Toast.LENGTH_SHORT);
                    return;
                }
            }

            @Override
            public void onFailure(Call<DetailAPIResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public interface getGames {
        @Headers(
                {
                "Accept: Application/json"
                }
        )
        @GET("games?key=3addf073aa2344a9bf43782450d628ae")
        Call<SearchAPIResponse> callGames(@Query("search") String game_name);
    }

    public interface getGamesDetails {
        @Headers(
                {
                        "Accept: Application/json"
                }
        )
        @GET("games/{slur}?key=3addf073aa2344a9bf43782450d628ae")
        Call<DetailAPIResponse> callGamesDetails(@Path("slur") String slur);
    }

}
