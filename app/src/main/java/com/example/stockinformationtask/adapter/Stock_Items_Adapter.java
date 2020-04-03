package com.example.stockinformationtask.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stockinformationtask.R;
import com.example.stockinformationtask.roomDatabase.DataBaseSearchEntity;
import java.util.List;


public class Stock_Items_Adapter extends RecyclerView.Adapter<Stock_Items_Adapter.ViewHolder>  {

    private List<DataBaseSearchEntity> dataBaseSearchEntityList;
    private Context context;
    private OnItemclickListner onItemclickListner;
    private int num = 1;

    public Stock_Items_Adapter(Context context, List<DataBaseSearchEntity> dataBaseSearchEntityList, Stock_Items_Adapter.OnItemclickListner onItemclickListner) {
        this.context=context;
        this.dataBaseSearchEntityList = dataBaseSearchEntityList;
        this.onItemclickListner = onItemclickListner;
    }

    public interface OnItemclickListner {
        void onItemClick(int status, int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_items_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final DataBaseSearchEntity dataBaseSearchEntity = dataBaseSearchEntityList.get(position);

        if(!TextUtils.isEmpty(dataBaseSearchEntity.getName())) {
            holder.name_textview.setText(dataBaseSearchEntity.getName());
        }
        if(!TextUtils.isEmpty(dataBaseSearchEntity.getSymbol())) {
            holder.symbol_textview.setText(dataBaseSearchEntity.getSymbol());
        }
        if(!TextUtils.isEmpty(dataBaseSearchEntity.getPrice())) {
            holder.price_textview.setText(dataBaseSearchEntity.getPrice() + " " +dataBaseSearchEntity.getCurrency());
        }

        holder.card_view_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemclickListner!=null) {
                    onItemclickListner.onItemClick(1,position);

                }
            }
        });


        holder.card_view_click.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(onItemclickListner!=null) {
                    onItemclickListner.onItemClick(2,position);
                }
                return false;


            }
        });




    }

    //diplaying latest 1o values to adapter
    @Override
    public int getItemCount() {

        if(num*5 > dataBaseSearchEntityList.size()){
            return dataBaseSearchEntityList.size();
        }else{
            return num*5;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name_textview, symbol_textview, price_textview;
        private CardView card_view_click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_textview = itemView.findViewById(R.id.name_textview);
            symbol_textview = itemView.findViewById(R.id.symbol_textview);
            price_textview = itemView.findViewById(R.id.price_textview);
            card_view_click = itemView.findViewById(R.id.card_view_click);

        }


    }
}
