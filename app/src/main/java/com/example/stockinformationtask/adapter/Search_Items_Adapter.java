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
import com.example.stockinformationtask.entity.Datum;

import java.util.List;


public class Search_Items_Adapter extends RecyclerView.Adapter<Search_Items_Adapter.ViewHolder>  {

    private List<Datum> datumList;
    private Context context;
    private OnItemclickListner onItemclickListner;

    public Search_Items_Adapter(Context context, List<Datum> datumList, Search_Items_Adapter.OnItemclickListner onItemclickListner) {
        this.context=context;
        this.datumList = datumList;
        this.onItemclickListner = onItemclickListner;

    }

    public interface OnItemclickListner {
        void onItemClick(int status, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_items_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Datum datum = datumList.get(position);

        if(!TextUtils.isEmpty(datum.getName())) {
            holder.name_textview.setText(datum.getName());
        }
        if(!TextUtils.isEmpty(datum.getSymbol())) {
            holder.symbol_textview.setText(datum.getSymbol());
        }
        if(!TextUtils.isEmpty(datum.getPrice())) {
            holder.price_textview.setText(datum.getPrice());
        }

        holder.card_view_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemclickListner!=null) {
                    onItemclickListner.onItemClick(1,position);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return datumList.size();

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
