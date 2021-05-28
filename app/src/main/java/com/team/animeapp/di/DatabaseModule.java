package com.team.animeapp.di;


import android.content.Context;

import com.team.animeapp.data.sources.local.AnimeDatabase;
import com.team.animeapp.data.sources.local.AnimeDatabaseImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    AnimeDatabase provideAnimeDatabase(@ApplicationContext Context context)
    {
        return new AnimeDatabaseImpl(context);
    }

}
