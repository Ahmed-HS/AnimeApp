package com.team.animeapp.data.sources.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.team.animeapp.data.models.network.Anime;

import java.util.ArrayList;
import java.util.List;

public class AnimeDatabaseImpl extends SQLiteOpenHelper implements AnimeDatabase {

    private static final String AnimeDatabaseName= "AnimeDatabase";

    private static final String AnimeTableName= "Anime";
    private static final String AnimeIdColumn= "id";
    private static final String AnimeTitleColumn= "title";
    private static final String AnimeImageColumn= "imageUrl";

    public AnimeDatabaseImpl(@Nullable Context context) {
        super(context, AnimeDatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + AnimeTableName + " (" +
                AnimeIdColumn + " INTEGER PRIMARY KEY," +
                AnimeTitleColumn + " TEXT," +
                AnimeImageColumn + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addToWatchLater(Anime anime)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues animeRow = new ContentValues();

        animeRow.put(AnimeIdColumn,anime.malId);
        animeRow.put(AnimeTitleColumn,anime.title);
        animeRow.put(AnimeImageColumn,anime.imageUrl);

        db.insert(AnimeTableName,null,animeRow);

        db.close();
    }

    public void removeFromWatchLater(Anime anime)
    {
        SQLiteDatabase db = getWritableDatabase();

        String selection = AnimeIdColumn + " = ?";
        String[] selectionArgs = { String.valueOf(anime.malId)};

        db.delete(AnimeTableName,selection,selectionArgs);

        db.close();
    }

    @Override
    public boolean isInWatchLater(Anime anime) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from Anime where id = " + anime.malId,null);
        if(cursor != null)
        {
            return cursor.getCount() != 0;
        }

        db.close();

        return false;
    }

    public List<Anime> getWatchLaterList()
    {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Anime",null);

        ArrayList<Anime> result = new ArrayList<>();

        if(cursor!= null)
        {
            cursor.moveToFirst();

            while (!cursor.isAfterLast())
            {
                Anime anime = new Anime();
                anime.malId = cursor.getInt(0);
                anime.title = cursor.getString(1);
                anime.imageUrl = cursor.getString(2);
                result.add(anime);
                cursor.moveToNext();
            }
        }

        db.close();

        return result;
    }
}
