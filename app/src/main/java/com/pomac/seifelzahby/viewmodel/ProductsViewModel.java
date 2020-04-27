package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.ProductsApi;
import com.pomac.seifelzahby.model.responses.ProductsResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsViewModel extends ViewModel {

    private MutableLiveData<ProductsResponse> productsResponse;
    private Disposable disposable;

    public LiveData<ProductsResponse> getProductsResponse(int categoryId) {
        if (productsResponse == null) {
            productsResponse = new MutableLiveData<>();
            loadProductsResponse(categoryId);
        }
        return productsResponse;
    }

    private void loadProductsResponse(int categoryId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ProductsApi productsApi = retrofit.create(ProductsApi.class);

        Observable<ProductsResponse> observable = productsApi.getProducts(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable = observable.subscribe(
                response -> productsResponse.setValue(response),
                e -> Log.e("nussair", e.toString())
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
