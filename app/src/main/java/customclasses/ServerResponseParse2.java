package customclasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shery on 8/11/2017.
 */

public class ServerResponseParse2 implements Serializable {
    @SerializedName("RepaymentInfo")
    private ArrayList<Repayment> repaymentArrayList = new ArrayList<>();

    public ArrayList<Repayment> getRepaymentArrayList() {
        return repaymentArrayList;
    }
}
