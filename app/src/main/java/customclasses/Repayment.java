package customclasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shery on 8/11/2017.
 */

public class Repayment implements Serializable {
    /*            [LoanID] => 27919
            [custFullName] => Faiz Muhammad  Qambrani
            [EmoloyeeId] =>
            [StatoinName] => Tando Adam Branch
            [ChequeNumber] => 0
            [iOtherFees] => 0.0000
            [dRegistrationFees] => 150
            [dEmergencyFund] => 50
            [dTotalLoanAmount] => 5900.0000
            [Approve_amount] => 5000.0000
            [dServiceChargesRate] => 18.00*/

    public static final String REPAYMENT_TABLE = "Repayment";
    public static final String KEY_ID = "id";
    public static final String KEY_LOAN_ID = "loanId";
    public static final String KEY_EMPLOYEE_ID = "employeeId";
    public static final String KEY_CHEQ_NUMBER = "chequeNumber";
    public static final String KEY_CUSTOMER_NAME = "customerName";
    public static final String KEY_NIC_NUMBER = "NICNumber";
    public static final String KEY_STATION_NAME = "stationName";
    public static final String KEY_OTHER_FEES = "otherFees";
    public static final String KEY_REG_FEES = "registrtaionFees";
    public static final String KEY_EMERGENCY_FUND = "emergencyFund";
    public static final String KEY_TOTAL_LOAN_AMOUNT = "totalLoanAmount";
    public static final String KEY_APP_AMOUNT = "approveAmount";
    public static final String KEY_SERVICE_CHARGES_RATE = "serviceChargesRate";
    public static final String KEY_REP_START_DATE = "repaymentStartDate";
    public static final String KEY_FP_IMAGE_TEMP = "fPImageTemp";
    public static final String KEY_CUSTOMER_GROUP_ID = "customerGroupId";
    public static final String KEY_TOTAL_PAID = "Totalpaid";
    public static final String KEY_LAST_REP_AMOUNT = "LastRepaymentAmount";
    public static final String KEY_LAST_REP_DT = "LastRepaymentDateTime";
    public static final String KEY_CURRENT_REP_AMOUNT = "CurrentRepaymentAmount";
    public static final String KEY_CURRENT_REP_DT = "CurrentRepaymentDateTime";
    public static final String KEY_REP_AMOUNT = "RepaymentAmount";
    public static final String KEY_REP_DT = "RepaymentDateTime";
    public static final String KEY_PENALTY = "Penalty";
    public static final String KEY_PROCESSING_FEES = "ProccessingFees";
    public static final String KEY_DATA_SYNCUP = "IsSyncedUp";


    public static final String createRepaymentTable = "Create Table If Not Exists Repayment (\n" +
            "    id integer primary key autoincrement,\n" +
            "    loanId int(11),\n" +
            "    employeeId int(11),\n" +
            "    StationId integer,\n" +
            "    chequeNumber text,\n" +
            "    customerName text,\n" +
            "    NICNumber text,\n" +
            "    stationName text,\n" +
            "    otherFees text,\n" +
            "    registrtaionFees text,\n" +
            "    emergencyFund text,\n" +
            "    totalLoanAmount text,\n" +
            "    approveAmount text,\n" +
            "    serviceChargesRate text,\n" +
            "    repaymentStartDate text,\n" +
            "    fPImageTemp text,\n" +
            "    customerGroupId text,\n" +
            "    Totalpaid text, " +
            "    LastRepaymentAmount text, " +
            "    LastRepaymentDateTime text, " +
            "    CurrentRepaymentAmount text, " +
            "    CurrentRepaymentDateTime text, " +
            "    RepaymentAmount text, " +
            "    RepaymentDateTime text, " +
            "    Penalty text, " +
            "    ProccessingFees text, " +
            "    IsSyncedUp smallint(6) DEFAULT '0' " +
            ")";


    @SerializedName("LoanId")
    private int loanId;
    @SerializedName("EmoloyeeId")
    private int employeeId;
    @SerializedName("ChequeNumber")
    private String chequeNumber;
    @SerializedName("custFullName")
    private String customerName;
    @SerializedName("NICNumber")
    private String NICNumber;
    @SerializedName("StatoinName")
    private String stationName;
    @SerializedName("iOtherFees")
    private String otherFees;
    @SerializedName("dRegistrationFees")
    private String registrtaionFees;
    @SerializedName("dEmergencyFund")
    private String emergencyFund;
    @SerializedName("dTotalLoanAmount")
    private String totalLoanAmount;
    @SerializedName("Approve_amount")
    private String approveAmount;
    @SerializedName("dServiceChargesRate")
    private String serviceChargesRate;
    @SerializedName("RepaymentStartDate")
    private String repaymentStartDate;
    @SerializedName("FPImageTemp")
    private String fPImageTemp;
    @SerializedName("CustomerGroupId")
    private String customerGroupId;
    @SerializedName("Totalpaid")
    private String Totalpaid;
    @SerializedName("LastRepaymentAmount")
    private String lastRepaymentAmount;
    @SerializedName("LastRepaymentDateTime")
    private String lastRepaymentDateTime;
    @SerializedName("CurrentRepaymentAmount")
    private String currentRepaymentAmount;
    @SerializedName("CurrentRepaymentDateTime")
    private String currentRepaymentDateTime;
    @SerializedName("StaionId")
    private int stationId;
    private int isSyncedUp;



    private String RepaymentAmount, RepaymentDateTime, ProccessingFees, Penalty;


    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getOtherFees() {
        return otherFees;
    }

    public void setOtherFees(String otherFees) {
        this.otherFees = otherFees;
    }

    public String getRegistrtaionFees() {
        return registrtaionFees;
    }

    public void setRegistrtaionFees(String registrtaionFees) {
        this.registrtaionFees = registrtaionFees;
    }

    public String getEmergencyFund() {
        return emergencyFund;
    }

    public void setEmergencyFund(String emergencyFund) {
        this.emergencyFund = emergencyFund;
    }

    public String getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(String totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public String getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(String approveAmount) {
        this.approveAmount = approveAmount;
    }

    public String getServiceChargesRate() {
        return serviceChargesRate;
    }

    public void setServiceChargesRate(String serviceChargesRate) {
        this.serviceChargesRate = serviceChargesRate;
    }

    public String getRepaymentStartDate() {
        return repaymentStartDate;
    }

    public void setRepaymentStartDate(String repaymentStartDate) {
        this.repaymentStartDate = repaymentStartDate;
    }

    public String getfPImageTemp() {
        return fPImageTemp;
    }

    public void setfPImageTemp(String fPImageTemp) {
        this.fPImageTemp = fPImageTemp;
    }

    public String getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public String getTotalpaid() {
        return Totalpaid;
    }

    public void setTotalpaid(String totalpaid) {
        Totalpaid = totalpaid;
    }

    public String getLastRepaymentAmount() {
        return lastRepaymentAmount;
    }

    public void setLastRepaymentAmount(String lastRepaymentAmount) {
        this.lastRepaymentAmount = lastRepaymentAmount;
    }

    public String getLastRepaymentDateTime() {
        return lastRepaymentDateTime;
    }

    public void setLastRepaymentDateTime(String lastRepaymentDateTime) {
        this.lastRepaymentDateTime = lastRepaymentDateTime;
    }

    public String getCurrentRepaymentAmount() {
        return currentRepaymentAmount;
    }

    public void setCurrentRepaymentAmount(String currentRepaymentAmount) {
        this.currentRepaymentAmount = currentRepaymentAmount;
    }

    public String getCurrentRepaymentDateTime() {
        return currentRepaymentDateTime;
    }

    public void setCurrentRepaymentDateTime(String currentRepaymentDateTime) {
        this.currentRepaymentDateTime = currentRepaymentDateTime;
    }

    public String getRepaymentAmount() {
        return RepaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        RepaymentAmount = repaymentAmount;
    }

    public String getRepaymentDateTime() {
        return RepaymentDateTime;
    }

    public void setRepaymentDateTime(String repaymentDateTime) {
        RepaymentDateTime = repaymentDateTime;
    }

    public String getProccessingFees() {
        return ProccessingFees;
    }

    public void setProccessingFees(String proccessingFees) {
        ProccessingFees = proccessingFees;
    }

    public String getPenalty() {
        return Penalty;
    }

    public void setPenalty(String penalty) {
        Penalty = penalty;
    }

    public String getNICNumber() {
        return NICNumber;
    }

    public void setNICNumber(String NICNumber) {
        this.NICNumber = NICNumber;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getIsSyncedUp() {
        return isSyncedUp;
    }

    public void setIsSyncedUp(int isSyncedUp) {
        this.isSyncedUp = isSyncedUp;
    }
}
