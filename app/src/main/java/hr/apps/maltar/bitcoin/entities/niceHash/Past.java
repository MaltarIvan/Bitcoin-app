package hr.apps.maltar.bitcoin.entities.niceHash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maltar on 16.8.2017..
 */

public class Past {

    @SerializedName("data")
    @Expose
    private List<List<Integer>> data = null;
    @SerializedName("algo")
    @Expose
    private Integer algo;

    public List<List<Integer>> getData() {
        return data;
    }

    public void setData(List<List<Integer>> data) {
        this.data = data;
    }

    public Integer getAlgo() {
        return algo;
    }

    public void setAlgo(Integer algo) {
        this.algo = algo;
    }

}
