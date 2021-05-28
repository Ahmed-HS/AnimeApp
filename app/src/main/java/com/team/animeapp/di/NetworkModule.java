package com.team.animeapp.di;


import com.squareup.moshi.Moshi;
import com.team.animeapp.data.sources.remote.JikanApi;
import com.team.animeapp.util.moshi.LocalDateTimeAdapter;

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
    Moshi provideMoshi()
    {
        return new Moshi.Builder()
                .add(new LocalDateTimeAdapter())
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Moshi moshi)
    {
        return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }

    @Provides
    JikanApi provideJikanApi(Retrofit retrofit)
    {
        return retrofit.create(JikanApi.class);
    }

}
