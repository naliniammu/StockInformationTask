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
import com.example.stockinformationtask.entity.Forex_Names;

import java.util.List;


public class Forex_Items_Adapter extends RecyclerView.Adapter<Forex_Items_Adapter.ViewHolder>  {

    private List<Forex_Names> forex_data;
    private Context context;
    private OnItemclickListner onItemclickListner;

    public Forex_Items_Adapter(Context context, List<Forex_Names> forex_data, Forex_Items_Adapter.OnItemclickListner onItemclickListner) {
        this.context=context;
        this.forex_data = forex_data;
        this.onItemclickListner = onItemclickListner;

    }

    public interface OnItemclickListner {
        void onItemClick(int status, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forex_custom_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Forex_Names forexData = forex_data.get(position);

        if(!TextUtils.isEmpty(forexData.getForex_symbol())) {
            holder.name_textview.setText(forexData.getForex_symbol());
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
        return forex_data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name_textview;
        private CardView card_view_click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_textview = itemView.findViewById(R.id.name_textview);
            card_view_click = itemView.findViewById(R.id.card_view_click);

        }


    }
}
