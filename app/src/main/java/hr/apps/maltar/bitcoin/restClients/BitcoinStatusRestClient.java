package hr.apps.maltar.bitcoin.restClients;

import hr.apps.maltar.bitcoin.entities.BitcoinStatus;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Maltar on 15.8.2017..
 */

public interface BitcoinStatusRestClient {
    @GET("/api/v2/ticker/{currency}/")
    Call<BitcoinStatus> getBitcoinStatus(@Path("currency") String currency);
}
