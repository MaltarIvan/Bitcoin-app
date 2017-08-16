package hr.apps.maltar.bitcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NiceHashActivity extends AppCompatActivity {
    private static String bitcoinWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_hash);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.contains(getString(R.string.bitcoin_wallet_key))) {
            Toast.makeText(this, "ne postoji!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddBitcoinWalletActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "postoji!", Toast.LENGTH_SHORT).show();
        }
    }
}
