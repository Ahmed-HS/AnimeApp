package com.team.animeapp.ui.searchfragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.domain.Result;
import com.team.animeapp.domain.repositories.AnimeRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchViewModel extends ViewModel {

    private AnimeRepository animeRepository;

    public LiveData<Result<List<Anime>>> searchResults;

    @Inject
    SearchViewModel(AnimeRepository animeRepository)
    {
        this.animeRepository = animeRepository;
        searchResults = animeRepository.searchAnime("",null);
    }

    public void searchAnime(String query, List<Integer> genres)
    {
        searchResults = animeRepository.searchAnime(query,genres);
    }

}
