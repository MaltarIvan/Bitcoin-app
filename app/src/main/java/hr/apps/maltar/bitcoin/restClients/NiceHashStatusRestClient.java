package hr.apps.maltar.bitcoin.restClients;

import hr.apps.maltar.bitcoin.entities.niceHash.NiceHashStats;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Maltar on 22.8.2017..
 */

public interface NiceHashStatusRestClient {
    @GET("/api?method=stats.provider.ex")
    Call<NiceHashStats> getNiceHashStatus(@Query("addr") String addr, @Query("from") Long timeStamp);
}
