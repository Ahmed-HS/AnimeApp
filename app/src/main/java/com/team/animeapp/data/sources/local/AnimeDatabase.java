package com.team.animeapp.data.sources.local;

import com.team.animeapp.data.models.network.Anime;

import java.util.List;

public interface AnimeDatabase {
    void addToWatchLater(Anime anime);

    void removeFromWatchLater(Anime anime);

    boolean isInWatchLater(Anime anime);

    List<Anime> getWatchLaterList();
}
