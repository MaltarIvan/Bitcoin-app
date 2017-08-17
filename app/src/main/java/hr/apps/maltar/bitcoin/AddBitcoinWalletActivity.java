package hr.apps.maltar.bitcoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AddBitcoinWalletActivity extends AppCompatActivity {
    private static final int ENTER_BTC_WALLET_ADDRESS_REQUEST = 0;

    private final static String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bitcoin_wallet);

        final EditText addBTCWalletAddress = (EditText) findViewById(R.id.enter_btc_wallet_address_edit_text);
        //addBTCWalletAddress.setText("1EWD4jswmKECgwBjkkfjHijcHPf4dSiViy");
        Button enterAddressButton = (Button) findViewById(R.id.enter_btc_wallet_address_button);

        enterAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredAddress = addBTCWalletAddress.getText().toString();
                if (validateBitcoinAddress(enteredAddress)) {
                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.bitcoin_wallet_key), enteredAddress);
                    setResult(ENTER_BTC_WALLET_ADDRESS_REQUEST, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong address format!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }

    private boolean validateBitcoinAddress(String addr) {
        if (addr.length() < 26 || addr.length() > 35) return false;
        byte[] decoded = decodeBase58(addr, 58, 25);
        if (decoded == null) return false;

        byte[] hash = sha256(decoded, 0, 21, 2);

        return Arrays.equals(Arrays.copyOfRange(hash, 0, 4), Arrays.copyOfRange(decoded, 21, 25));
    }

    private byte[] decodeBase58(String input, int base, int len) {
        byte[] output = new byte[len];
        for (int i = 0; i < input.length(); i++) {
            char t = input.charAt(i);

            int p = ALPHABET.indexOf(t);
            if (p == -1) return null;
            for (int j = len - 1; j >= 0; j--, p /= 256) {
                p += base * (output[j] & 0xFF);
                output[j] = (byte) (p % 256);
            }
            if (p != 0) return null;
        }

        return output;
    }

    private byte[] sha256(byte[] data, int start, int len, int recursion) {
        if (recursion == 0) return data;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Arrays.copyOfRange(data, start, start + len));
            return sha256(md.digest(), 0, 32, recursion - 1);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
