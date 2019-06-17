package com.example.roomwordsample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) { //constructor for handle to database and initialize member variables
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords(){ //will notify the observer when data is changed
        return mAllWords;
    }

    public void insert (Word word){ //must call this on a non-Ui thread or app will crash room ensures you dont do any long running operations on the main thread,
        new insertAsyncTask(mWordDao).execute(word);
    }


    private static class insertAsyncTask extends AsyncTask<Word,Void,Void>{

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao){
            mAsyncTaskDao = dao;
        }

       @Override
               protected Void doInBackground(final Word... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



}

