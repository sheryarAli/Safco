package customclasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shery on 9/28/2017.
 */

public class RepaymentLog {
    public static final String REPAYMENT_LOG_TABLE = "RepaymentLog";
    private int id;
    private int loanId;
    @SerializedName("GroupId")
    private String customerGroupId;
    private int StationId;
    private String customerName;
    private String NICNumber;
    private String Penalty;
    private String ProccessingFees;
    private String repaymentAmount;
    private String RepaymentDateTime;
    private int isSyncedUp;

    public static final String createRepaymentLogTable = "Create Table If Not Exists RepaymentLog (\n" +
            "    id integer primary key autoincrement,\n" +
            "    loanId int(11), " +
            "    customerGroupId text, " +
            "    StationId integer, " +
            "    NICNumber text, " +
            "    customerName text, " +
            "    RepaymentAmount text, " +
            "    RepaymentDateTime text, " +
            "    Penalty text, " +
            "    ProccessingFees text, " +
            "    IsSyncedUp smallint(6) DEFAULT '0' " +
            ")";

    public int getStationId() {
        return StationId;
    }

    public void setStationId(int stationId) {
        StationId = stationId;
    }

    public String getNICNumber() {
        return NICNumber;
    }

    public void setNICNumber(String NICNumber) {
        this.NICNumber = NICNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getRepaymentDateTime() {
        return RepaymentDateTime;
    }

    public void setRepaymentDateTime(String repaymentDateTime) {
        RepaymentDateTime = repaymentDateTime;
    }

    public String getPenalty() {
        return Penalty;
    }

    public void setPenalty(String penalty) {
        Penalty = penalty;
    }

    public String getProccessingFees() {
        return ProccessingFees;
    }

    public void setProccessingFees(String proccessingFees) {
        ProccessingFees = proccessingFees;
    }

    public int getIsSyncedUp() {
        return isSyncedUp;
    }

    public void setIsSyncedUp(int isSyncedUp) {
        this.isSyncedUp = isSyncedUp;
    }
}
