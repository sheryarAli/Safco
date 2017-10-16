package customclasses;

import java.io.Serializable;

import static customclasses.MicrofinanceCustomers.KEY_NOTES;

/**
 * Created by shery on 12/9/2016.
 */

public class Loan implements Serializable {
    public static final String LOAN_TABLE = "Loan";
    public static final String KEY_LOAN_ID = "Loan_Id";
    public static final String KEY_LOAN_TYPE = "Loan_Type";
    public static final String KEY_REQ_LOAN = "Requested_Loan";
    public static final String KEY_APP_LOAN = "Approved_Loan";

    public static final String KEY_LOAN_TERM = "Loan_Term";
    public static final String KEY_AMO_REQ_BUSI = "Amount_Required_Business";
    public static final String KEY_PER_CAP_BUSI = "Personal_Capital_Business";
    public static final String KEY_LOAN_STATUS = "Loan_Status";
    public static final String KEY_BUSI_ADD = "Business_Address";
    public static final String KEY_MON_INC = "Monthly_Income";
    public static final String KEY_MON_EXP = "Monthly_Expense";
    public static final String KEY_MON_HH_EXP = "Monthly_Household_Expense";
    public static final String KEY_MON_HH_INC = "Monthly_Household_Income";
    public static final String KEY_SAVING = "Savings";
    public static final String KEY_HH_SAVING = "Household_Saving";
    public static final String KEY_EXI_BUSI = "Existing_Business";
    public static final String KEY_BUSI_EXP = "Business_Experience";
    public static final String KEY_EXP_MON_INC_BUSI = "Expected_Monthly_Income_Business";

    private int loanId, customerId;
    private String LoanTypeId, RequestedLoanAmount, ApprovedLoanAmount, LoanTerm,
            LoanStatus, AmountRequiredForBusiness, PersonalCapitalInBusiness,
            ExpectedMonthlyIncomeFromBusiness, BusinessAddress, MonthlyIncome,
            MonthlyExpense, Household_MonthlyExpense, Household_MonthlyIncome, Savings, Household_Savings,
            ExistingBusiness, ExperienceInBusiness, NICNumber, addedDateTime, Notes;

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        this.Notes = notes;
    }

    public String getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(String addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public String getNICNumber() {
        return NICNumber;
    }

    public void setNICNumber(String NICNumber) {
        this.NICNumber = NICNumber;
    }

    public String getHousehold_MonthlyIncome() {
        return Household_MonthlyIncome;
    }

    public void setHousehold_MonthlyIncome(String household_MonthlyIncome) {
        Household_MonthlyIncome = household_MonthlyIncome;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getLoanTypeId() {
        return LoanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        LoanTypeId = loanTypeId;
    }

    public String getRequestedLoanAmount() {
        return RequestedLoanAmount;
    }

    public void setRequestedLoanAmount(String requestedLoanAmount) {
        RequestedLoanAmount = requestedLoanAmount;
    }

    public String getApprovedLoanAmount() {
        return ApprovedLoanAmount;
    }

    public void setApprovedLoanAmount(String approvedLoanAmount) {
        ApprovedLoanAmount = approvedLoanAmount;
    }

    public String getLoanTerm() {
        return LoanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        LoanTerm = loanTerm;
    }

    public String getLoanStatus() {
        return LoanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        LoanStatus = loanStatus;
    }

    public String getAmountRequiredForBusiness() {
        return AmountRequiredForBusiness;
    }

    public void setAmountRequiredForBusiness(String amountRequiredForBusiness) {
        AmountRequiredForBusiness = amountRequiredForBusiness;
    }

    public String getPersonalCapitalInBusiness() {
        return PersonalCapitalInBusiness;
    }

    public void setPersonalCapitalInBusiness(String personalCapitalInBusiness) {
        PersonalCapitalInBusiness = personalCapitalInBusiness;
    }

    public String getExpectedMonthlyIncomeFromBusiness() {
        return ExpectedMonthlyIncomeFromBusiness;
    }

    public void setExpectedMonthlyIncomeFromBusiness(String expectedMonthlyIncomeFromBusiness) {
        ExpectedMonthlyIncomeFromBusiness = expectedMonthlyIncomeFromBusiness;
    }

    public String getBusinessAddress() {
        return BusinessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        BusinessAddress = businessAddress;
    }

    public String getMonthlyIncome() {
        return MonthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        MonthlyIncome = monthlyIncome;
    }

    public String getMonthlyExpense() {
        return MonthlyExpense;
    }

    public void setMonthlyExpense(String monthlyExpense) {
        MonthlyExpense = monthlyExpense;
    }

    public String getHousehold_MonthlyExpense() {
        return Household_MonthlyExpense;
    }

    public void setHousehold_MonthlyExpense(String household_MonthlyExpense) {
        Household_MonthlyExpense = household_MonthlyExpense;
    }

    public String getSavings() {
        return Savings;
    }

    public void setSavings(String savings) {
        Savings = savings;
    }

    public String getHousehold_Savings() {
        return Household_Savings;
    }

    public void setHousehold_Savings(String household_Savings) {
        Household_Savings = household_Savings;
    }

    public String getExistingBusiness() {
        return ExistingBusiness;
    }

    public void setExistingBusiness(String existingBusiness) {
        ExistingBusiness = existingBusiness;
    }

    public String getExperienceInBusiness() {
        return ExperienceInBusiness;
    }

    public void setExperienceInBusiness(String experienceInBusiness) {
        ExperienceInBusiness = experienceInBusiness;
    }


    public static String createLoanTable = "CREATE TABLE IF NOT EXISTS " + LOAN_TABLE + " ( " +
            " " + KEY_LOAN_ID + " integer primary key AUTOINCREMENT," +
            " " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " integer," +
            " " + KEY_REQ_LOAN + " varchar(60) DEFAULT NULL," +
            " " + KEY_LOAN_TYPE + " varchar(60) DEFAULT NULL," +
            " " + KEY_APP_LOAN + " varchar(60) DEFAULT NULL," +
            " " + KEY_LOAN_TERM + " varchar(60) DEFAULT NULL," +
            " " + KEY_LOAN_STATUS + " varchar(60) DEFAULT NULL," +
            " " + KEY_AMO_REQ_BUSI + " varchar(60) DEFAULT NULL," +
            " " + KEY_PER_CAP_BUSI + " varchar(60) DEFAULT NULL," +
            " " + KEY_EXP_MON_INC_BUSI + " varchar(60) DEFAULT NULL," +
            " " + KEY_BUSI_ADD + " varchar(60) DEFAULT NULL," +
            " " + KEY_MON_INC + " varchar(60) DEFAULT NULL," +
            " " + KEY_MON_EXP + " varchar(60) DEFAULT NULL," +
            " " + KEY_MON_HH_EXP + " varchar(60) DEFAULT NULL," +
            " " + KEY_MON_HH_INC + " varchar(60) DEFAULT NULL," +
            " " + KEY_SAVING + " varchar(60) DEFAULT NULL," +
            " " + KEY_HH_SAVING + " varchar(60) DEFAULT NULL," +
            " " + KEY_EXI_BUSI + " varchar(60) DEFAULT NULL," +
            " " + KEY_BUSI_EXP + " varchar(60) DEFAULT NULL," +
            "  " + KEY_NOTES + " text,\n" +
            " " + MicrofinanceCustomers.KEY_NIC_NUMBER + " varchar(60) DEFAULT NULL," +
            " " + CommonConst.KEY_CUR_ADD_DATE + " varchar(60) DEFAULT NULL" +
            ")";

}
