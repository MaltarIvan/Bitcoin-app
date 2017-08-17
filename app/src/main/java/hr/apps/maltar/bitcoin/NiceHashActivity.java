package hr.apps.maltar.bitcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NiceHashActivity extends AppCompatActivity {
    private static final int ENTER_BTC_WALLET_ADDRESS_REQUEST = 0;
    private static String bitcoinWallet;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    private TextView bitcoinWalletTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nicehash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("TEST", String.valueOf(R.id.action_add));
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

        bitcoinWalletTextView = (TextView) findViewById(R.id.address_text_view);

        bitcoinWallet = sharedPreferences.getString(getString(R.string.bitcoin_wallet_key), null);

        if (bitcoinWallet == null) {
            Intent intent = new Intent(this, AddBitcoinWalletActivity.class);
            startActivityForResult(intent, ENTER_BTC_WALLET_ADDRESS_REQUEST);
        } else {
            bitcoinWalletTextView.setText(bitcoinWallet);
        }
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
}
