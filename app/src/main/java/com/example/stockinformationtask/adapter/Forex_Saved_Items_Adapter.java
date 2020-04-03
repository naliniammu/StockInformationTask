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
import com.example.stockinformationtask.roomDatabase.DataBaseForexEntity;
import java.util.List;


public class Forex_Saved_Items_Adapter extends RecyclerView.Adapter<Forex_Saved_Items_Adapter.ViewHolder>  {

    private List<DataBaseForexEntity> forex_data;
    private Context context;
    private OnItemclickListnerforex onItemclickListnerforex;
    private int num = 1;

    public Forex_Saved_Items_Adapter(Context context, List<DataBaseForexEntity> forex_data, Forex_Saved_Items_Adapter.OnItemclickListnerforex onItemclickListnerforex) {
        this.context=context;
        this.forex_data = forex_data;
        this.onItemclickListnerforex = onItemclickListnerforex;

    }

    public interface OnItemclickListnerforex {
        void onItemClickforex(int status, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forex_custom_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final DataBaseForexEntity forexData = forex_data.get(position);

        if(!TextUtils.isEmpty(forexData.getForex_symbol())) {
            holder.name_textview.setText(forexData.getForex_symbol());
        }

        holder.card_view_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemclickListnerforex!=null) {
                    onItemclickListnerforex.onItemClickforex(1,position);

                }
            }
        });


    }

    @Override
    public int getItemCount() {

        if(num*5 > forex_data.size()){
            return forex_data.size();
        }else{
            return num*5;
        }

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
