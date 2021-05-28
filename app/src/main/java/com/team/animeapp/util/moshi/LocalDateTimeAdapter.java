package com.team.animeapp.util.moshi;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.time.LocalDateTime;

public class LocalDateTimeAdapter {

    @ToJson
    String toJson (LocalDateTime localDateTime)
    {
        return localDateTime.toString();
    }

    @FromJson
    LocalDateTime fromJson(String datetime)
    {
        return  LocalDateTime.now();
    }
}
