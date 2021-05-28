package com.team.animeapp.di;


import com.squareup.moshi.Moshi;
import com.team.animeapp.data.sources.remote.JikanApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    private static final String base_url  = "https://api.jikan.moe/v3/";

    @Provides
    @Singleton
    Retrofit provideRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl(base_url)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Provides
    JikanApi provideJikanApi(Retrofit retrofit)
    {
        return retrofit.create(JikanApi.class);
    }

}
