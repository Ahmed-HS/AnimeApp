package com.team.animeapp.domain.repositories;

import androidx.lifecycle.LiveData;

import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.domain.Result;

import java.util.List;

import retrofit2.http.Path;
import retrofit2.http.Query;


public interface AnimeRepository {

    LiveData<List<Anime>> getTopRanked();

    LiveData<Result<List<Anime>>> searchAnime(String query , List<Integer> genres);

    LiveData<List<Anime>> getAnimeInGenre(int genreId);
}
