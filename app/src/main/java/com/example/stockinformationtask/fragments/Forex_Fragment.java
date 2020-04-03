package com.example.stockinformationtask.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stockinformationtask.BackgroundService;
import com.example.stockinformationtask.R;
import com.example.stockinformationtask.activities.Forex_Details_Activity;
import com.example.stockinformationtask.adapter.Forex_Items_Adapter;
import com.example.stockinformationtask.adapter.Forex_Saved_Items_Adapter;
import com.example.stockinformationtask.entity.Forex_Names;
import com.example.stockinformationtask.roomDatabase.DataBaseForexEntity;
import com.example.stockinformationtask.roomDatabase.DatabaseClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Forex_Fragment extends Fragment implements Forex_Items_Adapter.OnItemclickListner,
                                                        Forex_Saved_Items_Adapter.OnItemclickListnerforex{

    @BindView(R.id.forex_itens_recycler_view)
    RecyclerView forex_items_recycler_view;
    @BindView(R.id.fab_button)
    FloatingActionButton fab_button;
    @BindView(R.id.no_data_text_view)
    TextView no_data_text_view;


    private BottomSheetDialog dialog;
    private RecyclerView forex_recycler_view;
    private  ArrayList<Forex_Names> forex_namesList=new ArrayList<Forex_Names>();
    private Forex_Items_Adapter forex_items_adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Forex_Items_Adapter.OnItemclickListner onItemclickListner;

    private String[] Symbol_Name=new String[]{"USD", "GBP","EUR","JPY","NZD","CHF"};


    private String str_forex_symbol;
    private List<DataBaseForexEntity> dataBaseForexEntities;
    private Forex_Saved_Items_Adapter forex_saved_items_adapter;
    private Forex_Saved_Items_Adapter.OnItemclickListnerforex onItemclickListnerforex;
    private RecyclerView.LayoutManager layoutManagerforex;
    private String str_forex_base;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_forex, container, false);
        ButterKnife.bind(this, rootview);
        onItemclickListner=this;
        onItemclickListnerforex=this;
        getActivity().startService(new Intent(getActivity(), BackgroundService.class));
        layoutManagerforex=new LinearLayoutManager(getActivity());
        forex_items_recycler_view.setLayoutManager(layoutManagerforex);

        getForexSavedList();

        return rootview;
    }

    @OnClick(R.id.fab_button)
    void fab_button(View view) {
        forexList();
    }


//displaying forex list in dialog using bottom sheet
    private void forexList() {
        View dialogView = getLayoutInflater().inflate(R.layout.forex_details_custom_layout, null);
         dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(dialogView);
        forex_recycler_view=(RecyclerView)dialogView.findViewById(R.id.forex_recycler_view);
        layoutManager=new LinearLayoutManager(getActivity());
        forex_recycler_view.setLayoutManager(layoutManager);
        populatingtoRecyclerView();
        dialog.show();
    }

//after clicking add data to fragment
    @Override
    public void onItemClick(int status, int position) {
        if(status==1){
            str_forex_symbol=forex_namesList.get(position).getForex_symbol();
            saveForexItems();
        }

    }

//populating forex base values to recyclerview
    private void populatingtoRecyclerView() {
        forex_namesList = new ArrayList<Forex_Names>();
        for (int i = 0; i <Symbol_Name.length; i++) {
            Forex_Names forex_names = new Forex_Names();
            forex_names.setForex_symbol(Symbol_Name[i]);
            forex_namesList.add(forex_names);
        }
        forex_items_adapter = new Forex_Items_Adapter(getActivity(),forex_namesList,onItemclickListner);
        forex_recycler_view.setAdapter(forex_items_adapter);
    }


    //saving click items to local DB
    public void saveForexItems() {
        class saveForexItemsTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DataBaseForexEntity dataBaseForexEntity= new DataBaseForexEntity();
                dataBaseForexEntity.setForex_symbol(str_forex_symbol);
                //adding to database
                DatabaseClient.getInstance(getActivity()).
                        getAppDatabase()
                        .searchDao()
                        .insertAll(dataBaseForexEntity);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
               dialog.dismiss();
                getForexSavedList();

            }
        }
        new saveForexItemsTask().execute();


    }


    //getting forex saved items values from local db
    private void getForexSavedList() {
        class getForexSavedListTask extends AsyncTask<Void, Void, List<DataBaseForexEntity>> {
            @Override
            protected List<DataBaseForexEntity> doInBackground(Void... voids) {
                dataBaseForexEntities = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .searchDao()
                        .getAllForex();
                return dataBaseForexEntities;
            }

            @Override
            protected void onPostExecute(List<DataBaseForexEntity> dataBaseForexEntities) {
                super.onPostExecute(dataBaseForexEntities);
                populatingtoRecyclerView(dataBaseForexEntities);

            }
        }
        new getForexSavedListTask().execute();
    }


 //populating forex saved items to recyclerview
    private void populatingtoRecyclerView(List<DataBaseForexEntity> dataBaseForexEntities) {
        if(dataBaseForexEntities==null || dataBaseForexEntities.size()==0){
            no_data_text_view.setVisibility(View.VISIBLE);
            forex_items_recycler_view.setVisibility(View.GONE);
        }else {
            no_data_text_view.setVisibility(View.GONE);
            forex_items_recycler_view.setVisibility(View.VISIBLE);
            Collections.reverse(dataBaseForexEntities);
            forex_saved_items_adapter = new Forex_Saved_Items_Adapter(getActivity(),dataBaseForexEntities,onItemclickListnerforex);
            forex_items_recycler_view.setAdapter(forex_saved_items_adapter);
        }

    }


    //individual clicking of forex saved items
    @Override
    public void onItemClickforex(int status, int position) {
        if(status==1){
            str_forex_base=dataBaseForexEntities.get(position).getForex_symbol();
            Intent intent=new Intent(getActivity(), Forex_Details_Activity.class);
            SharedPreferences sharedpreferences = getActivity().getSharedPreferences("Forex Symbol Details", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("str_forex_base", str_forex_base);
            editor.commit();
            startActivity(intent);
        }else {}

    }




}
