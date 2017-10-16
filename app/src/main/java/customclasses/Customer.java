package customclasses;

/**
 * Created by shery on 11/24/2016.
 */

public class Customer {
    private int CustomerId, CustomerType, GuardianType, MaritalStatus, Religion,
            HouseType, HouseStatus, status, NumberOfChildren, syncStatus,
            NumberOfSchoolGoingChildren, IsDeceased, IsDisabled, Education,
            NumberOfLoans, stationId;

    private byte[] FPImage;
    private String FPImageTemp;
    private String FPImageStr;
    private String NICNumber;
    private String FirstName;
    private String LastName;
    private String Guardian;
    private String Gender;
    private String DateOfBirth;
    private String OldNICNumber;
    private String TokenNumber;
    private String NICType;
    private String FamilyNumber;
    private String DeceasedDate;
    private String regionName;
    private String stationName;
    private String NICExpiryDate;
    private String GuardianNICNumber;
    private String GuardianOldNICNumber;
    private String GuardianTokenNumber;
    private String GuardianNICType;
    private String PhoneNumber;
    private String MobileNumber;
    private String EmailAddress;
    private String EducationDetails;
    private String customerGPSLocation;
    private String Address_Permanent_Country;
    private String Address_Permanent_State;
    private String Address_Permanent_City;
    private String Address_Permanent_Mohalla_Village;
    private String Address_Permanent_Address;
    private String Address_Permanent_ZipCode;
    private String Address_Present_Country;
    private String Address_Present_State;
    private String Address_Present_City;
    private String Address_Present_Mohalla_Village;
    private String Address_Present_Address;
    private String Address_Present_ZipCode;
    private String Address_Present_District_Name;
    private String Address_Permanent_District_Name;
    private String Address_Permanent_Taluka_Name;
    private String Address_Permanent_UC_Name;
    private String Address_Present_Taluka_Name;
    private String Address_Present_UC_Name;
    private String Address_Present_NumberOfYears;
    private String Details_BankAccount;
    private String CustomerAddedDateTime;

    public String getCustomerGPSLocation() {
        return customerGPSLocation;
    }

    public void setCustomerGPSLocation(String customerGPSLocation) {
        this.customerGPSLocation = customerGPSLocation;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getFPImageStr() {
        return FPImageStr;
    }

    public void setFPImageStr(String FPImageStr) {
        this.FPImageStr = FPImageStr;
    }

    public String getFPImageTemp() {
        return FPImageTemp;
    }

    public void setFPImageTemp(String FPImageTemp) {
        this.FPImageTemp = FPImageTemp;
    }

    public byte[] getFPImage() {
        return FPImage;
    }

    public void setFPImage(byte[] FPImage) {
        this.FPImage = FPImage;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    private String Notes;

    public String getStationName() {
        return stationName;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    private boolean IsActivated;

    public boolean isActivated() {
        return IsActivated;
    }

    public void setActivated(boolean activated) {
        IsActivated = activated;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.MobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.EmailAddress = emailAddress;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getNICNumber() {
        return NICNumber;
    }

    public void setNICNumber(String NICNumber) {
        this.NICNumber = NICNumber;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public int getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(int customerType) {
        CustomerType = customerType;
    }

    public int getGuardianType() {
        return GuardianType;
    }

    public void setGuardianType(int guardianType) {
        GuardianType = guardianType;
    }

    public int getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        MaritalStatus = maritalStatus;
    }

    public int getReligion() {
        return Religion;
    }

    public void setReligion(int religion) {
        Religion = religion;
    }

    public int getHouseType() {
        return HouseType;
    }

    public void setHouseType(int houseType) {
        HouseType = houseType;
    }

    public int getHouseStatus() {
        return HouseStatus;
    }

    public void setHouseStatus(int houseStatus) {
        HouseStatus = houseStatus;
    }


    public String getAddress_Present_District_Name() {
        return Address_Present_District_Name;
    }

    public void setAddress_Present_District_Name(String address_Present_District_Name) {
        Address_Present_District_Name = address_Present_District_Name;
    }

    public String getAddress_Permanent_District_Name() {
        return Address_Permanent_District_Name;
    }

    public void setAddress_Permanent_District_Name(String address_Permanent_District_Name) {
        Address_Permanent_District_Name = address_Permanent_District_Name;
    }

    public String getAddress_Permanent_Taluka_Name() {
        return Address_Permanent_Taluka_Name;
    }

    public void setAddress_Permanent_Taluka_Name(String address_Permanent_Taluka_Name) {
        Address_Permanent_Taluka_Name = address_Permanent_Taluka_Name;
    }

    public String getAddress_Permanent_UC_Name() {
        return Address_Permanent_UC_Name;
    }

    public void setAddress_Permanent_UC_Name(String address_Permanent_UC_Name) {
        Address_Permanent_UC_Name = address_Permanent_UC_Name;
    }

    public String getAddress_Present_Taluka_Name() {
        return Address_Present_Taluka_Name;
    }

    public void setAddress_Present_Taluka_Name(String address_Present_Taluka_Name) {
        Address_Present_Taluka_Name = address_Present_Taluka_Name;
    }

    public String getAddress_Present_UC_Name() {
        return Address_Present_UC_Name;
    }

    public void setAddress_Present_UC_Name(String address_Present_UC_Name) {
        Address_Present_UC_Name = address_Present_UC_Name;
    }


    public String getGuardian() {
        return Guardian;
    }

    public void setGuardian(String guardian) {
        Guardian = guardian;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getOldNICNumber() {
        return OldNICNumber;
    }

    public void setOldNICNumber(String oldNICNumber) {
        OldNICNumber = oldNICNumber;
    }

    public String getTokenNumber() {
        return TokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        TokenNumber = tokenNumber;
    }

    public String getNICType() {
        return NICType;
    }

    public void setNICType(String NICType) {
        this.NICType = NICType;
    }

    public String getFamilyNumber() {
        return FamilyNumber;
    }

    public void setFamilyNumber(String familyNumber) {
        FamilyNumber = familyNumber;
    }

    public int getIsDeceased() {
        return IsDeceased;
    }

    public void setIsDeceased(int isDeceased) {
        IsDeceased = isDeceased;
    }

    public int getIsDisabled() {
        return IsDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        IsDisabled = isDisabled;
    }

    public String getDeceasedDate() {
        return DeceasedDate;
    }

    public void setDeceasedDate(String deceasedDate) {
        DeceasedDate = deceasedDate;
    }

    public String getNICExpiryDate() {
        return NICExpiryDate;
    }

    public void setNICExpiryDate(String NICExpiryDate) {
        this.NICExpiryDate = NICExpiryDate;
    }

    public String getGuardianNICNumber() {
        return GuardianNICNumber;
    }

    public void setGuardianNICNumber(String guardianNICNumber) {
        GuardianNICNumber = guardianNICNumber;
    }

    public String getGuardianOldNICNumber() {
        return GuardianOldNICNumber;
    }

    public void setGuardianOldNICNumber(String guardianOldNICNumber) {
        GuardianOldNICNumber = guardianOldNICNumber;
    }

    public String getGuardianTokenNumber() {
        return GuardianTokenNumber;
    }

    public void setGuardianTokenNumber(String guardianTokenNumber) {
        GuardianTokenNumber = guardianTokenNumber;
    }

    public String getGuardianNICType() {
        return GuardianNICType;
    }

    public void setGuardianNICType(String guardianNICType) {
        GuardianNICType = guardianNICType;
    }

    public int getEducation() {
        return Education;
    }

    public void setEducation(int education) {
        Education = education;
    }

    public String getEducationDetails() {
        return EducationDetails;
    }

    public void setEducationDetails(String educationDetails) {
        EducationDetails = educationDetails;
    }

    public String getAddress_Permanent_Country() {
        return Address_Permanent_Country;
    }

    public void setAddress_Permanent_Country(String address_Permanent_Country) {
        Address_Permanent_Country = address_Permanent_Country;
    }

    public String getAddress_Permanent_State() {
        return Address_Permanent_State;
    }

    public void setAddress_Permanent_State(String address_Permanent_State) {
        Address_Permanent_State = address_Permanent_State;
    }

    public String getAddress_Permanent_City() {
        return Address_Permanent_City;
    }

    public void setAddress_Permanent_City(String address_Permanent_City) {
        Address_Permanent_City = address_Permanent_City;
    }

    public String getAddress_Permanent_Mohalla_Village() {
        return Address_Permanent_Mohalla_Village;
    }

    public void setAddress_Permanent_Mohalla_Village(String address_Permanent_Mohalla_Village) {
        Address_Permanent_Mohalla_Village = address_Permanent_Mohalla_Village;
    }

    public String getAddress_Permanent_Address() {
        return Address_Permanent_Address;
    }

    public void setAddress_Permanent_Address(String address_Permanent_Address) {
        Address_Permanent_Address = address_Permanent_Address;
    }

    public String getAddress_Permanent_ZipCode() {
        return Address_Permanent_ZipCode;
    }

    public void setAddress_Permanent_ZipCode(String address_Permanent_ZipCode) {
        Address_Permanent_ZipCode = address_Permanent_ZipCode;
    }

    public String getAddress_Present_Country() {
        return Address_Present_Country;
    }

    public void setAddress_Present_Country(String address_Present_Country) {
        Address_Present_Country = address_Present_Country;
    }

    public String getAddress_Present_State() {
        return Address_Present_State;
    }

    public void setAddress_Present_State(String address_Present_State) {
        Address_Present_State = address_Present_State;
    }

    public String getAddress_Present_City() {
        return Address_Present_City;
    }

    public void setAddress_Present_City(String address_Present_City) {
        Address_Present_City = address_Present_City;
    }

    public String getAddress_Present_Mohalla_Village() {
        return Address_Present_Mohalla_Village;
    }

    public void setAddress_Present_Mohalla_Village(String address_Present_Mohalla_Village) {
        Address_Present_Mohalla_Village = address_Present_Mohalla_Village;
    }

    public String getAddress_Present_Address() {
        return Address_Present_Address;
    }

    public void setAddress_Present_Address(String address_Present_Address) {
        Address_Present_Address = address_Present_Address;
    }

    public String getAddress_Present_ZipCode() {
        return Address_Present_ZipCode;
    }

    public void setAddress_Present_ZipCode(String address_Present_ZipCode) {
        Address_Present_ZipCode = address_Present_ZipCode;
    }

    public String getAddress_Present_NumberOfYears() {
        return Address_Present_NumberOfYears;
    }

    public void setAddress_Present_NumberOfYears(String address_Present_NumberOfYears) {
        Address_Present_NumberOfYears = address_Present_NumberOfYears;
    }

    public String getDetails_BankAccount() {
        return Details_BankAccount;
    }

    public void setDetails_BankAccount(String details_BankAccount) {
        Details_BankAccount = details_BankAccount;
    }

    public String getCustomerAddedDateTime() {
        return CustomerAddedDateTime;
    }

    public void setCustomerAddedDateTime(String customerAddedDateTime) {
        CustomerAddedDateTime = customerAddedDateTime;
    }
}
