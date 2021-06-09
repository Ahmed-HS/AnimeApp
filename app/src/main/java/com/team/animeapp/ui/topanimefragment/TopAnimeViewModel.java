package com.team.animeapp.ui.topanimefragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.domain.repositories.AnimeRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TopAnimeViewModel extends ViewModel {

    private MutableLiveData<List<Anime>> mTopAnime = new MutableLiveData();
    public LiveData<List<Anime>> topAnime = mTopAnime;

    @Inject
    TopAnimeViewModel(AnimeRepository animeRepository){
        topAnime = animeRepository.getTopRanked();
    }
}
