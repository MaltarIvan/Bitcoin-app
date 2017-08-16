package hr.apps.maltar.bitcoin.entities.niceHash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maltar on 16.8.2017..
 */

public class Result {

    @SerializedName("current")
    @Expose
    private List<Current> current = null;
    @SerializedName("nh_wallet")
    @Expose
    private Boolean nhWallet;
    @SerializedName("past")
    @Expose
    private List<Past> past = null;
    @SerializedName("payments")
    @Expose
    private List<Object> payments = null;
    @SerializedName("addr")
    @Expose
    private String addr;

    public List<Current> getCurrent() {
        return current;
    }

    public void setCurrent(List<Current> current) {
        this.current = current;
    }

    public Boolean getNhWallet() {
        return nhWallet;
    }

    public void setNhWallet(Boolean nhWallet) {
        this.nhWallet = nhWallet;
    }

    public List<Past> getPast() {
        return past;
    }

    public void setPast(List<Past> past) {
        this.past = past;
    }

    public List<Object> getPayments() {
        return payments;
    }

    public void setPayments(List<Object> payments) {
        this.payments = payments;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

}
