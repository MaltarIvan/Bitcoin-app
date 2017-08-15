package hr.apps.maltar.bitcoin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import hr.apps.maltar.bitcoin.entities.BitcoinStatus;
import hr.apps.maltar.bitcoin.restClients.BitcoinStatusRestClient;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainScreenActivity extends AppCompatActivity {
    private TextView timeTextView;
    private TextView currencyStatusTextView;
    private ImageView arrowImage;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy");

    private static final String API_URL_BASE = "https://www.bitstamp.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        timeTextView = (TextView) findViewById(R.id.time_text_view);
        currencyStatusTextView = (TextView) findViewById(R.id.currency_status_text_view);
        arrowImage = (ImageView) findViewById(R.id.arrow_image_view);

        Button refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        getData();
    }

    private void refreshData() {
        getData();
    }

    private void getData() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_URL_BASE)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        BitcoinStatusRestClient client =  retrofit.create(BitcoinStatusRestClient.class);

        Call<BitcoinStatus> call = client.getBitcoinStatus();

        call.enqueue(new Callback<BitcoinStatus>() {
            @Override
            public void onResponse(Call<BitcoinStatus> call, Response<BitcoinStatus> response) {
                Log.d("SUCCESS", response.toString());
                BitcoinStatus bitcoinStatus = response.body();

                timeTextView.setText(simpleDateFormat.format(new Date()));
                currencyStatusTextView.setText(bitcoinStatus.getLast());

                if (Double.valueOf(bitcoinStatus.getLast()) >= Double.valueOf(bitcoinStatus.getVwap())) {
                    arrowImage.setImageResource(R.drawable.up_arrow);
                } else {
                    arrowImage.setImageResource(R.drawable.down_arrow);
                }
            }

            @Override
            public void onFailure(Call<BitcoinStatus> call, Throwable t) {
                Log.d("FAILURE", t.toString());
            }
        });
    }
}
