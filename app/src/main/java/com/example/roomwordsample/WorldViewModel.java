package com.example.roomwordsample;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WorldViewModel extends AndroidViewModel {
    private WordRepository mRepository; // member variable
    private LiveData<List<Word>> mAllWords; // member variable

    public WorldViewModel(Application application){    //this constructor gets a reference to the repository and gets the lists of words from the repository
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }
    LiveData<List<Word>> getAllWords(){ //getter method
        return mAllWords;
    }

    public void insert(Word word){
        mRepository.insert(word);
    }
}
