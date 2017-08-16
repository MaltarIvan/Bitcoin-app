package hr.apps.maltar.bitcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import hr.apps.maltar.bitcoin.entities.BitcoinStatus;
import hr.apps.maltar.bitcoin.restClients.BitcoinStatusRestClient;
import hr.apps.maltar.bitcoin.settingsActivities.MainSettingsActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainScreenActivity extends AppCompatActivity {
    private ImageView currencySignImage;
    private TextView currencyNameTextView;
    private TextView timeTextView;
    private TextView currencyStatusTextView;
    private ImageView arrowImage;
    private TextView currentBTCPrice;
    private TextView highBTCPrice;
    private TextView lowBTCPrice;
    private TextView vwaTextView;
    private TextView vwaBTCPrice;
    private TextView firstBTCPrice;

    private String currencyPreference;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy");

    private static final String API_URL_BASE = "https://www.bitstamp.net/";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            Intent intent = new Intent(this, MainSettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        timeTextView = (TextView) findViewById(R.id.time_text_view);
        currencySignImage = (ImageView) findViewById(R.id.currency_sign_image);
        currencyNameTextView = (TextView) findViewById(R.id.currency_name_text_view);
        currencyStatusTextView = (TextView) findViewById(R.id.currency_status_text_view);
        arrowImage = (ImageView) findViewById(R.id.arrow_image_view);

        currentBTCPrice = (TextView) findViewById(R.id.current_BTC_price_text_view);
        highBTCPrice = (TextView) findViewById(R.id.high_BTC_price_text_view);
        lowBTCPrice = (TextView) findViewById(R.id.low_BTC_price_text_view);
        vwaTextView = (TextView) findViewById(R.id.vwa_text_view);
        vwaBTCPrice = (TextView) findViewById(R.id.vwa_BTC_price_text_view);
        firstBTCPrice = (TextView) findViewById(R.id.first_BTC_price_text_view);

        Button refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        ImageButton niceHashButton = (ImageButton) findViewById(R.id.enter_nice_hash_button);
        niceHashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NiceHashActivity.class);
                startActivity(intent);
            }
        });

        vwaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Volume-weighted_average_price"));
                startActivity(intent);
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        currencyPreference = preferences.getString(getString(R.string.main_settings_currency_key), getString(R.string.settings_currency_default));

        if (currencyPreference.equals(getString(R.string.main_settings_usd_value))) {
            currencySignImage.setImageResource(R.drawable.dollar_sign2);
            currencyNameTextView.setText("USD: ");
        } else if (currencyPreference.equals(getString(R.string.main_settings_eur_value))) {
            currencySignImage.setImageResource(R.drawable.euro_sign);
            currencyNameTextView.setText("EUR: ");
        }

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

        Call<BitcoinStatus> call = client.getBitcoinStatus(currencyPreference);

        call.enqueue(new Callback<BitcoinStatus>() {
            @Override
            public void onResponse(Call<BitcoinStatus> call, Response<BitcoinStatus> response) {
                Log.d("SUCCESS", response.toString());
                BitcoinStatus bitcoinStatus = response.body();
                setBitcoinStatusToUI(bitcoinStatus);
            }

            @Override
            public void onFailure(Call<BitcoinStatus> call, Throwable t) {
                Log.d("FAILURE", t.toString());
            }
        });
    }

    private void setBitcoinStatusToUI(BitcoinStatus bitcoinStatus) {
        timeTextView.setText(simpleDateFormat.format(new Date()));
        currencyStatusTextView.setText(bitcoinStatus.getLast());
        currentBTCPrice.setText(bitcoinStatus.getLast());
        highBTCPrice.setText(bitcoinStatus.getHigh());
        lowBTCPrice.setText(bitcoinStatus.getLow());
        vwaBTCPrice.setText(bitcoinStatus.getVwap());
        firstBTCPrice.setText(bitcoinStatus.getOpen());

        if (Double.valueOf(bitcoinStatus.getLast()) >= Double.valueOf(bitcoinStatus.getVwap())) {
            arrowImage.setImageResource(R.drawable.up_arrow);
        } else {
            arrowImage.setImageResource(R.drawable.down_arrow);
        }
    }
}
