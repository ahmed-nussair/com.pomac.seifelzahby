package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.CartApi;
import com.pomac.seifelzahby.model.responses.DeletingFromCartResponse;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeletingFromCartViewModel extends ViewModel {

    private MutableLiveData<DeletingFromCartResponse> deletingFromCartResponseMutableLiveData;
    private Disposable disposable;

    public LiveData<DeletingFromCartResponse> getDeletingCartItemResponse(int cartId, String sessionCode) {
        if (deletingFromCartResponseMutableLiveData == null) {
            deletingFromCartResponseMutableLiveData = new MutableLiveData<>();
            deleteFromCart(cartId, sessionCode);
        }
        return deletingFromCartResponseMutableLiveData;
    }

    private void deleteFromCart(int cartId, String sessionCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CartApi api = retrofit.create(CartApi.class);

        Observable<DeletingFromCartResponse> observable = api.deleteFromCart(cartId, sessionCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable = observable.subscribe(response -> {
            deletingFromCartResponseMutableLiveData.setValue(response);
        }, e -> Log.e("nussair", Objects.requireNonNull(e.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
