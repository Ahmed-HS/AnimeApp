package com.team.animeapp.di;


import com.team.animeapp.domain.repositories.AnimeRepository;
import com.team.animeapp.domain.repositories.AnimeRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract AnimeRepository bindAnimeRepository(AnimeRepositoryImpl impl);

}
