package hr.apps.maltar.bitcoin.entities.niceHash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Maltar on 16.8.2017..
 */

public class Datum {

    @SerializedName("a")
    @Expose
    private String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

}
