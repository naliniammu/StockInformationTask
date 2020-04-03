package com.example.stockinformationtask.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.stockinformationtask.APIClient;
import com.example.stockinformationtask.APIInterface;
import com.example.stockinformationtask.Constants;
import com.example.stockinformationtask.R;
import com.example.stockinformationtask.entity.Forex_Entity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forex_Details_Activity extends AppCompatActivity {
    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.heading_textview)
    TextView heading_textview;
    @BindView(R.id.aed_textview)
    TextView aed_textview;
    @BindView(R.id.afn_textview)
    TextView afn_textview;
    @BindView(R.id.all_textview)
    TextView all_textview;
    @BindView(R.id.amd_textview)
    TextView amd_textview;
    @BindView(R.id.ang_textview)
    TextView ang_textview;

    private String str_aed_textview,str_afn_textview,str_all_textview,str_amd_textview,str_ang_textview;

    private ProgressDialog progressDoalog;
    private String str_forex_base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forex__details_);

        ButterKnife.bind(this);
        SharedPreferences sharedpreferences = getSharedPreferences("Forex Symbol Details", Context.MODE_PRIVATE);
        str_forex_base=sharedpreferences.getString("str_forex_base","");
        progressDoalog = new ProgressDialog(Forex_Details_Activity.this);
        progressDoalog.setMessage(getApplicationContext().getString(R.string.loading));
        progressDoalog.show();
        getIndividualForexDetailsfromAPICall();

    }

    @OnClick(R.id.back_image)
    void back_image(View view) {
        onBackPressed();
    }


    //getting individual base details based on base value
    public void getIndividualForexDetailsfromAPICall() {
        final APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        Call<Forex_Entity> call= apiService.getForexDetails(str_forex_base, Constants.API_TOKEN);
        call.enqueue(new Callback<Forex_Entity>() {
            @Override
            public void onResponse(Call<Forex_Entity>call, Response<Forex_Entity> response) {
                if(response.isSuccessful() && response.body().getData()!=null) {
                    progressDoalog.dismiss();
                    str_aed_textview=response.body().getData().getAED();
                    str_afn_textview=response.body().getData().getAFN();
                    str_all_textview=response.body().getData().getALL();
                    str_amd_textview=response.body().getData().getAMD();
                    str_ang_textview=response.body().getData().getANG();
                    populatingtoViews();

                } else {

                }
            }
            @Override
            public void onFailure(Call<Forex_Entity>call, Throwable t) {

            }
        });

    }



//populating to textviews
    private void populatingtoViews() {
        heading_textview.setText(str_forex_base + getApplicationContext().getString(R.string.base_details));
        aed_textview.setText("AED: "+str_aed_textview);
        afn_textview.setText("AFN: "+str_afn_textview);
        all_textview.setText("ALL: "+str_all_textview);
        amd_textview.setText("AMD: "+str_amd_textview);
        ang_textview.setText("ANG: "+str_ang_textview);


    }

}
