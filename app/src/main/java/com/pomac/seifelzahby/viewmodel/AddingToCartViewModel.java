package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.CartApi;
import com.pomac.seifelzahby.model.responses.AddingToCartResponse;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddingToCartViewModel extends ViewModel {

    private MutableLiveData<AddingToCartResponse> addingToCartResponse;
    private Disposable disposable;

    public LiveData<AddingToCartResponse> getAddingToCartResponse(String sessionCode, int productId, int quantiy) {
        if (addingToCartResponse == null) {
            addingToCartResponse = new MutableLiveData<>();
            onItemAdded(sessionCode, productId, quantiy);
        }
        return addingToCartResponse;
    }

    private void onItemAdded(String sessionCode, int productId, int quantiy) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CartApi cartApi = retrofit.create(CartApi.class);

        Observable<AddingToCartResponse> observable = cartApi.addToCart(productId, quantiy, sessionCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable = observable.subscribe(response -> addingToCartResponse.setValue(response),
                error -> Log.e("nussair", Objects.requireNonNull(error.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
