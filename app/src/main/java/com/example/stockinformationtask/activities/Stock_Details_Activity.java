package com.example.stockinformationtask.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.stockinformationtask.APIClient;
import com.example.stockinformationtask.APIInterface;
import com.example.stockinformationtask.BackgroundService;
import com.example.stockinformationtask.Constants;
import com.example.stockinformationtask.NotificationHandler;
import com.example.stockinformationtask.R;
import com.example.stockinformationtask.entity.Stock_Details;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.UUID;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class Stock_Details_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.back_image) ImageView back_image;
    @BindView(R.id.symbol_textview)
    TextView symbol_textview;
    @BindView(R.id.name_textview)
    TextView name_textview;
    @BindView(R.id.currancy_textview)
    TextView currancy_textview;
    @BindView(R.id.price_textview)
    TextView price_textview;
    @BindView(R.id.timezone_name_textview)
    TextView timezone_name_textview;
    @BindView(R.id.last_trade_time_textview)
    TextView last_trade_time_textview;
    @BindView(R.id.price_open_textview)
    TextView price_open_textview;
    @BindView(R.id.update_button)
    Button update_button;
    @BindView(R.id.trigger_button)
    Button trigger_button;

    private String str_stock_symbol;
    private String str_symbol,str_name,str_currancy,str_pricce,str_timezone_name,str_last_trade_time,str_price_open;


    private BottomSheetDialog dialog;
    private Spinner spinner;
    private EditText trigger_value_edit_text;
    private  Button Trigger_click;
    String[] trigger_value = { "<", ">", "="};
    private String str_trigger_input_value;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock__details_);
        ButterKnife.bind(this);

        SharedPreferences sharedpreferences = getSharedPreferences("Stock Symbol Details", Context.MODE_PRIVATE);
        str_stock_symbol=sharedpreferences.getString("str_symbol","");
        startService(new Intent(this, BackgroundService.class));
        getIndividualStockDetailsfromAPICall();

    }

    @OnClick(R.id.back_image)
    void back_image(View view) {
        onBackPressed();
    }

    @OnClick(R.id.update_button)
    void update_button(View view){
        getIndividualStockDetailsfromAPICall();
    }
    @OnClick(R.id.trigger_button)
    void triggering_click(View view){
        TriggeringDialog();


    }


    //getting individual stock details from api
    public void getIndividualStockDetailsfromAPICall() {
        final APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        Call<Stock_Details> call= apiService.getStockDetails(str_stock_symbol,Constants.API_TOKEN);
        call.enqueue(new Callback<Stock_Details>() {
            @Override
            public void onResponse(Call<Stock_Details>call, Response<Stock_Details> response) {
                if(response.isSuccessful() && response.body().getData()!=null) {
                    for(int i=0;i<response.body().getData().size();i++){
                        str_symbol=response.body().getData().get(i).getSymbol();
                        str_name=response.body().getData().get(i).getName();
                        str_currancy=response.body().getData().get(i).getCurrency();
                        str_pricce=response.body().getData().get(i).getPrice();
                        str_timezone_name=response.body().getData().get(i).getTimezoneName();
                        str_last_trade_time=response.body().getData().get(i).getLastTradeTime();
                        str_price_open=response.body().getData().get(i).getPriceOpen();
                    }
                    populatingtoViews();

                } else {

                }
            }
            @Override
            public void onFailure(Call<Stock_Details>call, Throwable t) {

            }
        });

    }

    //populating data to textviews
    private void populatingtoViews() {
        symbol_textview.setText("Symbol: "+str_symbol);
        name_textview.setText("Stock Name: "+str_name);
        currancy_textview.setText("Currancy: "+str_currancy);
        price_textview.setText("Price: "+str_pricce);
        timezone_name_textview.setText("Time Zone: "+str_timezone_name);
        last_trade_time_textview.setText("Last Trading Time: "+str_last_trade_time);
        price_open_textview.setText("Price Open: "+str_price_open);
    }


    //displaying forex list in dialog using bottom sheet
    private void TriggeringDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.triggering_custom_dialog, null);
        dialog = new BottomSheetDialog(Stock_Details_Activity.this);
        spinner=(Spinner)dialogView.findViewById(R.id.spinner);
        trigger_value_edit_text=(EditText) dialogView.findViewById(R.id.trigger_value_edit_text);
        Trigger_click=(Button) dialogView.findViewById(R.id.Trigger_click);
        dialog.setContentView(dialogView);
        spinner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,trigger_value);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(arrayAdapter);

        Trigger_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_trigger_input_value=trigger_value_edit_text.getText().toString().trim();
                    Double trigger_result = Double.parseDouble(str_trigger_input_value);
                    Double dynamic_trigger_price = Double.parseDouble(str_pricce);
                     dialog.dismiss();
                    if(trigger_result>=dynamic_trigger_price){
                        //Generate notification string tag
                        String tag = generateKey();
                        int alertTime=1000;
                        Data data = createWorkInputData(Constants.TITLE, getApplicationContext().getString(R.string.stock_value) + str_trigger_input_value, 1);
                        NotificationHandler.scheduleReminder(alertTime, data, tag);
                    }
            }
        });
        dialog.show();
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       String str_compare_Value=trigger_value[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    //broadcast Receiver for updating the trigger

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
            getIndividualStockDetailsfromAPICall();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BackgroundService.COUNTDOWN_BR)); }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }
    @Override
    public void onDestroy() {
        stopService(new Intent(this, BackgroundService.class));
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);

        }
    }


    private Data createWorkInputData(String title, String text, int id){
        return new Data.Builder()
                .putString(Constants.EXTRA_TITLE, title)
                .putString(Constants.EXTRA_TEXT, text)
                .putInt(Constants.EXTRA_ID, id)
                .build();
    }

    private String generateKey(){
        return UUID.randomUUID().toString();
    }



}
