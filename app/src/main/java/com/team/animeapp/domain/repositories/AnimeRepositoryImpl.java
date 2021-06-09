package com.team.animeapp.domain.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.models.network.GenreResponse;
import com.team.animeapp.data.models.network.SearchResponse;
import com.team.animeapp.data.models.network.TopAnimeResponse;
import com.team.animeapp.data.sources.remote.JikanApi;
import com.team.animeapp.domain.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRepositoryImpl implements AnimeRepository {

    private static final String TAG = AnimeRepositoryImpl.class.getSimpleName();

    private JikanApi jikanApi;

    private MutableLiveData<Result<List<Anime>>> searchResults;

    private MutableLiveData<List<Anime>> genreResults = new MutableLiveData<>();



    @Inject
    public AnimeRepositoryImpl(JikanApi jikanApi)
    {
        this.jikanApi = jikanApi;

        searchResults = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<Anime>> getTopRanked() {
        MutableLiveData<List<Anime>> topAnime = new MutableLiveData<>();

        jikanApi.getTopRanked().enqueue(new Callback<TopAnimeResponse>() {
            @Override
            public void onResponse(Call<TopAnimeResponse> call, Response<TopAnimeResponse> response) {
                topAnime.postValue(response.body().topAnimeList);
            }

            @Override
            public void onFailure(Call<TopAnimeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request().url());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return topAnime;
    }




    @Override
    public LiveData<Result<List<Anime>>> searchAnime(String query, List<Integer> genres) {

        if(query.isEmpty())
        {
            searchResults.postValue(Result.Success(new ArrayList<>()));
            return searchResults;
        }

        searchResults.postValue(Result.Loading());

        jikanApi.searchAnime(query,genres).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.body() != null)
                {
                    searchResults.postValue(Result.Success(response.body().results));
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });

        return searchResults;
    }

}
