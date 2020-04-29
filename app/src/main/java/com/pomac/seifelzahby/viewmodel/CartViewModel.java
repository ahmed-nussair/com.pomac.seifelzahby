package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.CartApi;
import com.pomac.seifelzahby.model.responses.CartResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartViewModel extends ViewModel {

    private MutableLiveData<CartResponse> cartResponse;
    private Disposable disposable;

    public LiveData<CartResponse> getCartResponse(String sessionCode) {
        if (cartResponse == null) {
            cartResponse = new MutableLiveData<>();
            loadingCart(sessionCode);
        }
        return cartResponse;
    }

    private void loadingCart(String sessionCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CartApi api = retrofit.create(CartApi.class);

        Observable<CartResponse> observable = api.getCart(sessionCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        disposable = observable.subscribe(response -> cartResponse.setValue(response),
                e -> Log.e("nussair", e.toString()));


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
