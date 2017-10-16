package customclasses;

import java.io.Serializable;

/**
 * Created by shery on 11/25/2016.
 */

public class FamilyMember implements Serializable {

    private int CustomerFamilyMemberId, CustomerId, Age;
    private double MonthlyEarning;
    private String FullName, NICNumber, RelationshipWithCustomer, SourceOfIncome, FamilyMemberAddedDateTime, Gender, Education, BusinessAddress;



    public String getBusinessAddress() {
        return BusinessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        BusinessAddress = businessAddress;
    }

    public int getCustomerFamilyMemberId() {
        return CustomerFamilyMemberId;
    }

    public void setCustomerFamilyMemberId(int customerFamilyMemberId) {
        CustomerFamilyMemberId = customerFamilyMemberId;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public double getMonthlyEarning() {
        return MonthlyEarning;
    }

    public void setMonthlyEarning(double monthlyEarning) {
        MonthlyEarning = monthlyEarning;
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

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getRelationshipWithCustomer() {
        return RelationshipWithCustomer;
    }

    public void setRelationshipWithCustomer(String relationshipWithCustomer) {
        RelationshipWithCustomer = relationshipWithCustomer;
    }

    public String getSourceOfIncome() {
        return SourceOfIncome;
    }

    public void setSourceOfIncome(String sourceOfIncome) {
        SourceOfIncome = sourceOfIncome;
    }

    public String getFamilyMemberAddedDateTime() {
        return FamilyMemberAddedDateTime;
    }

    public void setFamilyMemberAddedDateTime(String familyMemberAddedDateTime) {
        FamilyMemberAddedDateTime = familyMemberAddedDateTime;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
