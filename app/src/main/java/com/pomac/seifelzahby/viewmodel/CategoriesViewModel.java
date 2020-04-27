package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.CategoriesApi;
import com.pomac.seifelzahby.model.responses.CategoriesResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriesViewModel extends ViewModel {

    private MutableLiveData<CategoriesResponse> categoriesResponse;

    public LiveData<CategoriesResponse> getCategoreisResponse() {
        if (categoriesResponse == null) {
            categoriesResponse = new MutableLiveData<>();
            loadCategoriesResponse();
        }
        return categoriesResponse;
    }

    private void loadCategoriesResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CategoriesApi api = retrofit.create(CategoriesApi.class);

        Observable<CategoriesResponse> observable = api.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(
                response -> {
                    categoriesResponse.setValue(response);
                }, e -> {
                    Log.e("nussair", e.toString());
                }
        );

    }
}
