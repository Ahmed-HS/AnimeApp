package com.team.animeapp.data.sources.remote;

import androidx.lifecycle.LiveData;

import com.team.animeapp.data.models.network.Anime;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JikanApi {

    @GET("top/anime")
    LiveData<List<Anime>> getTopRanked();

    @GET("anime/{id}")
    LiveData<Anime> getAnimeById(@Path("id") int id);

    LiveData<List<Anime>> searchAnime(@Query("q") String query , @Query("genre") List<Integer> genres);


}
