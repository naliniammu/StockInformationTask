package com.example.stockinformationtask;

import com.example.stockinformationtask.entity.Forex_Entity;
import com.example.stockinformationtask.entity.Search_Entity;
import com.example.stockinformationtask.entity.Stock_Details;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("stock_search")
    Call<Search_Entity> getSearchResults(@Query("search_term") String search_term,
                                         @Query("search_by") String search_by,
                                         @Query("limit") int limit,
                                         @Query("page") int page,
                                         @Query("api_token") String api_token);



    @GET("stock")
    Call<Stock_Details> getStockDetails(@Query("symbol") String symbol,
                                        @Query("api_token") String api_token );

    @GET("forex")
    Call<Forex_Entity> getForexDetails(@Query("base") String base,
                                       @Query("api_token") String api_token );





}
