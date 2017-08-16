package hr.apps.maltar.bitcoin.entities.niceHash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maltar on 16.8.2017..
 */

public class Current {

    @SerializedName("profitability")
    @Expose
    private String profitability;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("algo")
    @Expose
    private Integer algo;

    public String getProfitability() {
        return profitability;
    }

    public void setProfitability(String profitability) {
        this.profitability = profitability;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getAlgo() {
        return algo;
    }

    public void setAlgo(Integer algo) {
        this.algo = algo;
    }

}
