package com.example.stockinformationtask.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.stockinformationtask.R;
import com.example.stockinformationtask.StockViewModel;
import com.example.stockinformationtask.adapter.Search_Items_Adapter;
import com.example.stockinformationtask.entity.Datum;
import com.example.stockinformationtask.roomDatabase.DataBaseSearchEntity;
import com.example.stockinformationtask.roomDatabase.DatabaseClient;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class Search_Activity extends AppCompatActivity implements Search_Items_Adapter.OnItemclickListner {

    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.search_edit_text)
    EditText search_edit_text;
    @BindView(R.id.search_recycler_view)
    RecyclerView search_recycler_view;
    @BindView(R.id.search_image)
    ImageView search_image;

    private String str_search_param;
    private Search_Items_Adapter search_items_adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Search_Items_Adapter.OnItemclickListner onItemclickListner;
    StockViewModel model;

    private String str_symbol,str_name,str_currancy,str_pice,str_stock_exchange_long,str_stock_exchange_short;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        model = ViewModelProviders.of(this).get(StockViewModel.class);
        ButterKnife.bind(this);
        onItemclickListner=this;
        layoutManager=new LinearLayoutManager(getApplicationContext());
        search_recycler_view.setLayoutManager(layoutManager);
        observeForStockInfo();
        model.fetchStockInfo("");
        search_edit_text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                str_search_param= String.valueOf(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            @Override
            public void afterTextChanged(Editable arg0) {
                if(arg0.length()>3) {
                    model.fetchStockInfo(str_search_param);
                }

            }
        });
    }

    @OnClick(R.id.back_image)
    void back_image(View view) {
       onBackPressed();
    }

    public void observeForStockInfo() {
        model.getStockListData().observe(this, data -> {
            search_items_adapter = new Search_Items_Adapter(getApplicationContext(),data,onItemclickListner);
            search_recycler_view.setAdapter(search_items_adapter);
           search_items_adapter.notifyDataSetChanged();
        });
    }


    //individual clicking of saved stock items
    @Override
    public void onItemClick(int status, int position) {
        if(status==1) {
          str_name= model.getStockListData().getValue().get(position).getName();
            str_symbol=model.getStockListData().getValue().get(position).getSymbol();
            str_name=model.getStockListData().getValue().get(position).getName();
            str_currancy=model.getStockListData().getValue().get(position).getCurrency();
            str_pice=model.getStockListData().getValue().get(position).getPrice();
            str_stock_exchange_long=model.getStockListData().getValue().get(position).getStockExchangeLong();
            str_stock_exchange_short=model.getStockListData().getValue().get(position).getStockExchangeShort();
            saveStockItems();
        }else {

        }

    }



    //saving particular stock item into local db
    public void saveStockItems() {
        class saveStockItemsTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DataBaseSearchEntity dataBaseSearchEntity= new DataBaseSearchEntity();
                dataBaseSearchEntity.setSymbol(str_symbol);
                dataBaseSearchEntity.setName(str_name);
                dataBaseSearchEntity.setCurrency(str_currancy);
                dataBaseSearchEntity.setPrice(str_pice);
                dataBaseSearchEntity.setStock_exchange_long(str_stock_exchange_long);
                dataBaseSearchEntity.setStock_exchange_short(str_stock_exchange_short);
                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).
                        getAppDatabase()
                        .searchDao()
                        .insertAll(dataBaseSearchEntity);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                displaying selected Item in Home screen Stock fragment
                Intent intent=new Intent(Search_Activity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        }

        new saveStockItemsTask().execute();
    }
}
