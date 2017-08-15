package hr.apps.maltar.bitcoin.entities.bitcoinStatusEnteties;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Maltar on 15.8.2017..
 */

public class Time {

    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("updatedISO")
    @Expose
    private String updatedISO;
    @SerializedName("updateduk")
    @Expose
    private String updateduk;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(String updatedISO) {
        this.updatedISO = updatedISO;
    }

    public String getUpdateduk() {
        return updateduk;
    }

    public void setUpdateduk(String updateduk) {
        this.updateduk = updateduk;
    }

}