package hr.apps.maltar.bitcoin.entities.niceHash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Maltar on 16.8.2017..
 */

public class NiceHashStats {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("method")
    @Expose
    private String method;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
