package com.example.stockinformationtask.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockinformationtask.BackgroundService;
import com.example.stockinformationtask.R;
import com.example.stockinformationtask.activities.Search_Activity;
import com.example.stockinformationtask.activities.Stock_Details_Activity;
import com.example.stockinformationtask.adapter.Stock_Items_Adapter;
import com.example.stockinformationtask.roomDatabase.DataBaseSearchEntity;
import com.example.stockinformationtask.roomDatabase.DatabaseClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Stock_Fragment extends Fragment implements Stock_Items_Adapter.OnItemclickListner{
    @BindView(R.id.stocks_recycler_view)
    RecyclerView stocks_recycler_view;
    @BindView(R.id.fab_button)
    FloatingActionButton fab_button;
    @BindView(R.id.no_data_text_view)
    TextView no_data_text_view;

    private List<DataBaseSearchEntity> dataBaseSearchEntities;
    private Stock_Items_Adapter stock_items_adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Stock_Items_Adapter.OnItemclickListner onItemclickListner;
    private String str_symbol;
    private DataBaseSearchEntity databasedeleteEntity;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_stock, container, false);
        ButterKnife.bind(this, rootview);
        onItemclickListner=this;
        layoutManager=new LinearLayoutManager(getActivity());
        stocks_recycler_view.setLayoutManager(layoutManager);
        getActivity().startService(new Intent(getActivity(), BackgroundService.class));
        getStockList();
        return rootview;
    }

    @OnClick(R.id.fab_button)
    void fab_button(View view) {
        Intent intent=new Intent(getActivity(), Search_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }



    //populating saved stock items to recyclerview
    private void populatingtoRecyclerView(List<DataBaseSearchEntity> dataBaseSearchEntities) {
        if(dataBaseSearchEntities==null || dataBaseSearchEntities.size()==0){
            no_data_text_view.setVisibility(View.VISIBLE);
            stocks_recycler_view.setVisibility(View.GONE);
        }else {
            Collections.reverse(dataBaseSearchEntities);
            no_data_text_view.setVisibility(View.GONE);
            stocks_recycler_view.setVisibility(View.VISIBLE);
            stock_items_adapter = new Stock_Items_Adapter(getActivity(),dataBaseSearchEntities,onItemclickListner);
            stocks_recycler_view.setAdapter(stock_items_adapter);
            stock_items_adapter.notifyDataSetChanged();
        }
    }


    //individual item clicking of saved items
    @Override
    public void onItemClick(int status, int position) {
        if(status==1){
          str_symbol=dataBaseSearchEntities.get(position).getSymbol();
            Intent intent=new Intent(getActivity(), Stock_Details_Activity.class);
            SharedPreferences sharedpreferences = getActivity().getSharedPreferences("Stock Symbol Details", Context.MODE_PRIVATE);
           SharedPreferences.Editor editor = sharedpreferences.edit();
           editor.putString("str_symbol", str_symbol);
           editor.commit();
           startActivity(intent);
        }else if(status==2){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            if(dataBaseSearchEntities==null || dataBaseSearchEntities.size()==0) {
            }else {
                builder.setTitle("Are you sure you want to delelte this item ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databasedeleteEntity=dataBaseSearchEntities.get(position);
                        deleteTask(databasedeleteEntity);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }

        } else { }

    }

    //getting save stock list data from local DB
    private void getStockList() {
        class GetStock extends AsyncTask<Void, Void, List<DataBaseSearchEntity>> {
            @Override
            protected List<DataBaseSearchEntity> doInBackground(Void... voids) {
                dataBaseSearchEntities = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .searchDao()
                        .getAll();
                return dataBaseSearchEntities;
            }
            @Override
            protected void onPostExecute(List<DataBaseSearchEntity> dataBaseSearchEntities) {
                super.onPostExecute(dataBaseSearchEntities);
                populatingtoRecyclerView(dataBaseSearchEntities);


            }
        }
        new GetStock().execute();
    }

    //deleting particular selected Item from DB
    private void deleteTask(final DataBaseSearchEntity databasedeleteEntity) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getActivity()).getAppDatabase()
                        .searchDao()
                        .delete(databasedeleteEntity);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();

            }
        }

        new DeleteTask().execute();
        getStockList();


    }



}
