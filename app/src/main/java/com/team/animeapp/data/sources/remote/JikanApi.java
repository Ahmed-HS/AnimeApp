package com.team.animeapp.data.sources.remote;

import androidx.lifecycle.LiveData;

import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.models.network.GenreResponse;
import com.team.animeapp.data.models.network.SearchResponse;
import com.team.animeapp.data.models.network.TopAnimeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JikanApi {

    @GET("top/anime")
    Call<TopAnimeResponse> getTopRanked();

    @GET("anime/{id}")
    LiveData<Anime> getAnimeById(@Path("id") int id);

    @GET("search/anime?limit=30")
    Call<SearchResponse> searchAnime(@Query("q") String query , @Query("genre") List<Integer> genres);

    @GET("genre/anime/{id}")
    Call<GenreResponse> getAnimeInGenre(@Path("id") int genreId);

}
