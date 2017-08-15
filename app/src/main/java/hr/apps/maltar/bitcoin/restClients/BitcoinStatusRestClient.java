package hr.apps.maltar.bitcoin.restClients;

import hr.apps.maltar.bitcoin.entities.bitcoinStatusEnteties.BitcoinStatus;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Maltar on 15.8.2017..
 */

public interface BitcoinStatusRestClient {
    @GET("/v1/bpi/currentprice.json")
    Call<BitcoinStatus> getBitcoinStatus();
}
