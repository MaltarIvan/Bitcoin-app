package hr.apps.maltar.bitcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import hr.apps.maltar.bitcoin.ListAdapters.NiceHashResultsAdapter;
import hr.apps.maltar.bitcoin.entities.niceHash.NiceHashStats;
import hr.apps.maltar.bitcoin.restClients.NiceHashStatusRestClient;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NiceHashActivity extends AppCompatActivity {
    private static final int ENTER_BTC_WALLET_ADDRESS_REQUEST = 0;
    private static final String API_URL_BASE = "https://api.nicehash.com/";

    private static String bitcoinWallet;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    private TextView bitcoinWalletTextView;
    private ListView resultList;
    private NiceHashResultsAdapter niceHashResultsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nicehash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add) {
            Intent intent = new Intent(this, AddBitcoinWalletActivity.class);
            startActivityForResult(intent, ENTER_BTC_WALLET_ADDRESS_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_hash);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferencesEditor = sharedPreferences.edit();

        resultList = (ListView) findViewById(R.id.nice_hash_result_list);
        bitcoinWalletTextView = (TextView) findViewById(R.id.address_text_view);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNiceHashStatus();
            }
        });

        bitcoinWallet = sharedPreferences.getString(getString(R.string.bitcoin_wallet_key), null);

        if (bitcoinWallet == null) {
            Intent intent = new Intent(this, AddBitcoinWalletActivity.class);
            startActivityForResult(intent, ENTER_BTC_WALLET_ADDRESS_REQUEST);
        } else {
            bitcoinWalletTextView.setText(bitcoinWallet);
        }

        getNiceHashStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENTER_BTC_WALLET_ADDRESS_REQUEST) {
            bitcoinWallet = data.getStringExtra(getString(R.string.bitcoin_wallet_key));
            sharedPreferencesEditor.putString(getString(R.string.bitcoin_wallet_key), bitcoinWallet);
            sharedPreferencesEditor.commit();
            bitcoinWalletTextView.setText(bitcoinWallet);
        }
    }

    private void getNiceHashStatus() {
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

        NiceHashStatusRestClient client =  retrofit.create(NiceHashStatusRestClient.class);

        Call<NiceHashStats> call = client.getNiceHashStatus(bitcoinWallet, (long) 0);

        call.enqueue(new Callback<NiceHashStats>() {
            @Override
            public void onResponse(Call<NiceHashStats> call, Response<NiceHashStats> response) {
                Log.d("SUCCESS", response.toString());
                niceHashResultsAdapter = new NiceHashResultsAdapter(getApplicationContext(), response.body().getResult().getCurrent());
                resultList.setAdapter(niceHashResultsAdapter);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<NiceHashStats> call, Throwable t) {
                Log.d("FAILURE", t.toString());
                Toast.makeText(getApplicationContext(), "Try refreshing in 30 sec.", Toast.LENGTH_LONG).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
