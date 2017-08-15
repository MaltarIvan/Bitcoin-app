package hr.apps.maltar.bitcoin.entities.bitcoinStatusEnteties;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Maltar on 15.8.2017..
 */

public class Bpi {

    @SerializedName("USD")
    @Expose
    private USD uSD;
    @SerializedName("GBP")
    @Expose
    private GBP gBP;
    @SerializedName("EUR")
    @Expose
    private EUR eUR;

    public USD getUSD() {
        return uSD;
    }

    public void setUSD(USD uSD) {
        this.uSD = uSD;
    }

    public GBP getGBP() {
        return gBP;
    }

    public void setGBP(GBP gBP) {
        this.gBP = gBP;
    }

    public EUR getEUR() {
        return eUR;
    }

    public void setEUR(EUR eUR) {
        this.eUR = eUR;
    }

}
