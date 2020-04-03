package com.example.stockinformationtask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.stockinformationtask.entity.Datum;
import com.example.stockinformationtask.entity.Search_Entity;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ArrayList<Datum>> data;

    //we will call this method to get the data
    public LiveData<ArrayList<Datum>> getStockListData() {
        //if the list is null
        if (data == null) {
            data = new MutableLiveData<ArrayList<Datum>>();
            //we will load it asynchronously from server in this method
        }
        //finally we will return the list
        return data;
    }

    public void fetchStockInfo(String str_search_param) {
        loadStockData(str_search_param);
    }

    private void loadStockData(String str_search_param) {
        final APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        Call<Search_Entity> call= apiService.getSearchResults(str_search_param,Constants.SEARCH_BY,Constants.LIMIT,Constants.PAGE, Constants.API_TOKEN);
        call.enqueue(new Callback<Search_Entity>() {
            @Override
            public void onResponse(Call<Search_Entity>call, Response<Search_Entity> response) {
                if(response.isSuccessful() && response.body().getData()!=null) {
                    data.setValue((ArrayList<Datum>) response.body().getData());
                } else {

                }
            }
            @Override
            public void onFailure(Call<Search_Entity>call, Throwable t) {

            }
        });
    }


}
