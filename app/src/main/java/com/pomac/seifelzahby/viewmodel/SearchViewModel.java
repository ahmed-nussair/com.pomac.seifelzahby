package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.SearchApi;
import com.pomac.seifelzahby.model.responses.SearchResponse;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<SearchResponse> mutableLiveData;
    private Disposable disposable;

    public LiveData<SearchResponse> getSearchResponse(String term) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            searchFor(term);
        }
        return mutableLiveData;
    }

    private void searchFor(String term) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        SearchApi searchApi = retrofit.create(SearchApi.class);

        Observable<SearchResponse> observable = searchApi.getSearchResults(term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable = observable.subscribe(response -> mutableLiveData.setValue(response),
                e -> Log.e("nussair", Objects.requireNonNull(e.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
