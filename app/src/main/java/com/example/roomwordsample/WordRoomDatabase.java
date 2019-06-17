package com.example.roomwordsample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.strictmode.InstanceCountViolation;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static volatile WordRoomDatabase INSTANCE;

    public abstract WordDao wordDao(); //defining

    static WordRoomDatabase getDatabase(final Context context) { // WordRoomDatabase a singleton

        if (INSTANCE == null) {

            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    //create database

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, "word_database").addCallback(sRoomDatabaseCallback).build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
    private final WordDao mDao;

    PopulateDbAsync(WordRoomDatabase db) {
        mDao = db.wordDao();
    }
    @Override
    protected Void doInBackground(Void... params) {

        mDao.deleteAll();
        Word word = new Word("Hello");
        mDao.insert(word);
        word = new Word("World");
        mDao.insert(word);
        word = new Word("DUDDE");
        mDao.insert(word);

        return null;
    }
}


}






