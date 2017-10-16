package customclasses;

import java.io.Serializable;

/**
 * Created by shery on 11/25/2016.
 */

public class Guaranteer implements Serializable{

    private int CustomerGuaranteerId,CustomerId, LoanId, Status;
    private String FullName, NICNumber, ContactNumber, Address, JobType, JobDescription, BusinessAddress, GuaranteerAddedDateTime, Notes;

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        this.Notes = notes;
    }

    public int getCustomerGuaranteerId() {
        return CustomerGuaranteerId;
    }

    public void setCustomerGuaranteerId(int customerGuaranteerId) {
        CustomerGuaranteerId = customerGuaranteerId;
    }

    public int getLoanId() {
        return LoanId;
    }

    public void setLoanId(int loanId) {
        LoanId = loanId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getNICNumber() {
        return NICNumber;
    }

    public void setNICNumber(String NICNumber) {
        this.NICNumber = NICNumber;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getJobDescription() {
        return JobDescription;
    }

    public void setJobDescription(String jobDescription) {
        JobDescription = jobDescription;
    }

    public String getBusinessAddress() {
        return BusinessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        BusinessAddress = businessAddress;
    }

    public String getGuaranteerAddedDateTime() {
        return GuaranteerAddedDateTime;
    }

    public void setGuaranteerAddedDateTime(String guaranteerAddedDateTime) {
        GuaranteerAddedDateTime = guaranteerAddedDateTime;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }
}
