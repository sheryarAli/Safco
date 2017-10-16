package customclasses;

/**
 * Created by shery on 11/17/2016.
 */

public class MicrofinanceCustomers {

    public static final String TABLE_NAME = "microfinance_customers";
    public static final String TABLE_NAME2 = "microfinance_customers_assets";
    public static final String TABLE_NAME3 = "microfinance_customers_guaranteers";
    public static final String TABLE_NAME4 = "microfinance_customers_familymembers";
    public static final String KEY_CUSTOMER_ID = "CustomerId";
    public static final String KEY_CUSTOMER_FP_IMG = "CustomerFPImg";
    public static final String KEY_CUSTOMER_FP_IMG_TEMP = "CustomerFPImgTemp";
    public static final String KEY_EMP_NAME = "Employee_Name";
    public static final String KEY_CUSTOMER_ADD_BY = "Customer_Add_By";
    public static final String KEY_CUSTOMER_TYPE = "CustomerType";
    public static final String KEY_GUARDIAN_TYPE = "GuardianType";
    public static final String KEY_GUARDIAN = "Guardian";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_DOB = "DateOfBirth";
    public static final String KEY_OLD_NIC_NUMBER = "OldNICNumber";
    public static final String KEY_TOKEN_NUMBER = "TokenNumber";
    public static final String KEY_NIC_TYPE = "NICType";
    public static final String KEY_FAMILY_NUMBER = "FamilyNumber";
    public static final String KEY_IS_DECEASED = "IsDeceased";
    public static final String KEY_IS_DISABLED = "IsDisabled";
    public static final String KEY_DECEASED_DATE = "DeceasedDate";
    public static final String KEY_NIC_EXPIRY_DATE = "NICExpiryDate";
    public static final String KEY_GUARDIAN_NIC_NUMBER = "GuardianNICNumber";
    public static final String KEY_GUARDIAN_OLD_NIC_NUMBER = "GuardianOldNICNumber";
    public static final String KEY_GUARDIAN_TOKEN_NUMBER = "GuardianTokenNumber";
    public static final String KEY_GUARDIAN_NIC_TYPE = "GuardianNICType";
    public static final String KEY_MARITAL_STATUS = "MaritalStatus";
    public static final String KEY_RELIGION = "Religion";
    public static final String KEY_EDUCATION = "Education";
    public static final String KEY_EDUCATION_DETAILS = "EducationDetails";
    public static final String KEY_HOUSE_STATUS = "HouseStatus";
    public static final String KEY_HOUSE_TYPE = "HouseType";
    public static final String KEY_NUMBER_0F_CHILDREN = "NumberOfChildren";
    public static final String KEY_NUMBER_0F_SCHOOL_GOING_CHILDREN = "NumberOfSchoolGoingChildren";
    public static final String KEY_ADDRESS_PERMANENT_COUNTRY = "Address_Permanent_Country";
    public static final String KEY_ADDRESS_PERMANENT_STATE = "Address_Permanent_State";
    public static final String KEY_ADDRESS_PERMANENT_DISTRICT_NAME = "Address_Permanent_District_Name";
    public static final String KEY_ADDRESS_PERMANENT_CITY = "Address_Permanent_City";
    public static final String KEY_ADDRESS_PERMANENT_TALUKA_NAME = "Address_Permanenta_Taluka_Name";
    public static final String KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE = "Address_Permanent_Mohalla_Village";
    public static final String KEY_ADDRESS_PERMANENT_ADDRESS = "Address_Permanent_Address";
    public static final String KEY_ADDRESS_PERMANENT_UC_NAME = "Address_Permanent_UC_Name";
    public static final String KEY_ADDRESS_PERMANENT_ZIPCODE = "Address_Permanent_ZipCode";
    public static final String KEY_ADDRESS_PRESENT_COUNTRY = "Address_Present_Country";
    public static final String KEY_ADDRESS_PRESENT_STATE = "Address_Present_State";
    public static final String KEY_ADDRESS_PRESENT_DISTRICT_NAME = "Address_Present_District_Name";
    public static final String KEY_ADDRESS_PRESENT_CITY = "Address_Present_City";
    public static final String KEY_ADDRESS_PRESENT_TALUKA_NAME = "Address_Present_Taluka_Name";
    public static final String KEY_ADDRESS_PRESENT_UC_NAME = "Address_Present_UC_Name";
    public static final String KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE = "Address_Present_Mohalla_Village";
    public static final String KEY_ADDRESS_PRESENT_ADDRESS = "Address_Present_Address";
    public static final String KEY_ADDRESS_PRESENT_ZIPCODE = "Address_Present_ZipCode";
    public static final String KEY_PRESENT_NUM_OF_YEARS = "Address_Present_NumberOfYears";
    public static final String KEY_DET_BANK_ACC = "Details_BankAccount";
    public static final String KEY_CUS_ADD_DATETIME = "CustomerAddedDateTime";
    public static final String KEY_NO_OF_LOANS = "NumberOfLoans";
    public static final String KEY_EMPLOYEE_STATION_NAME = "EmployeeStationName";
    public static final String KEY_DATA_SYNCUP = "IsSyncedUp";



    public static final String KEY_CUSTOMER_ASSET_ID = "CustomerAssetId";
    public static final String KEY_ASSET_NAME = "AssetName";
    public static final String KEY_CUSTOMER_ASSET_NAME_ID = "_CustomerAssetNameId";
    public static final String KEY_ASSET_QUANTITY = "AssetQuantity";
    public static final String KEY_ASSET_VALUE = "AssetValue";
    public static final String KEY_ASSET_OWNER = "AssetOwner";
    public static final String KEY_NOTES = "Notes";
    public static final String KEY_ASSET_ADD_DATETIME = "AssetAddedDateTime";

    public static final String KEY_CUSTOMER_GUARANTEER_ID = "CustomerGuaranteerId";
    public static final String KEY_LOAN_ID = "LoanId";
    public static final String KEY_FULL_NAME = "FullName";
    public static final String KEY_NIC_NUMBER = "NICNumber";
    public static final String KEY_CONTACT_NUMBER = "ContactNumber";
    public static final String KEY_JOB_TYPE = "JobType";
    public static final String KEY_JOB_DESC = "JobDescription";
    public static final String KEY_BUSINESS_ADDRESS = "BusinessAddress";
    public static final String KEY_GUARANTEER_ADD_DATETIME = "GuaranteerAddedDateTime";

    public static final String KEY_CUSTOMER_FMEMBER_ID = "CustomerFamilyMemberId";
    public static final String KEY_CUSTOMER_GPS_LOC = "CustomerGPSLocation";
    public static final String KEY_CUSTOMER_AGE = "Age";
    public static final String KEY_MONTHLY_EARNING = "MonthlyEarning";
    public static final String KEY_CUSTOMER_RELATION = "RelationshipWithCustomer";
    public static final String KEY_INCOME_SOURCE = "SourceOfIncome";
    public static final String KEY_FMEMBER_ADD_DATETIME = "FamilyMemberAddedDateTime";


    public static String createMicrofinanceCustomers = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            "  " + KEY_CUSTOMER_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + OrganizationStaions.KEY_STATION_ID + " int(11) DEFAULT '0',\n" +
            "  " + KEY_EMP_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_EMPLOYEE_STATION_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + OrganizationEmployees.KEY_REGION_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_CUSTOMER_TYPE + " tinyint(1) DEFAULT '1',\n" +
            "  " + OrganizationEmployees.KEY_FIRSTNAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + OrganizationEmployees.KEY_LASTNAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_GUARDIAN_TYPE + " tinyint(3) DEFAULT '0',\n" +
            "  " + KEY_GUARDIAN + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_GENDER + " char(1) DEFAULT 'M',\n" +
            "  " + KEY_DOB + " date DEFAULT NULL,\n" +
            "  " + OrganizationEmployees.KEY_NICNUMBER + " varchar(30) DEFAULT NULL,\n" +
            "  " + KEY_OLD_NIC_NUMBER + " varchar(30) DEFAULT NULL,\n" +
            "  " + KEY_TOKEN_NUMBER + " varchar(30) DEFAULT NULL,\n" +
            "  " + KEY_NIC_TYPE + " char(10) DEFAULT NULL,\n" +
            "  " + KEY_FAMILY_NUMBER + " varchar(100) DEFAULT NULL,\n" +
            "  " + KEY_IS_DECEASED + " tinyint(3) DEFAULT '0',\n" +
            "  " + KEY_IS_DISABLED + " tinyint(3) DEFAULT '0',\n" +
            "  " + KEY_DECEASED_DATE + " date DEFAULT NULL,\n" +
            "  " + KEY_NIC_EXPIRY_DATE + " date DEFAULT NULL,\n" +
            "  " + KEY_GUARDIAN_NIC_NUMBER + " varchar(30) DEFAULT NULL,\n" +
            "  " + KEY_GUARDIAN_OLD_NIC_NUMBER + " varchar(30) DEFAULT NULL,\n" +
            "  " + KEY_GUARDIAN_TOKEN_NUMBER + " varchar(30) DEFAULT NULL,\n" +
            "  " + KEY_GUARDIAN_NIC_TYPE + " char(10) DEFAULT NULL,\n" +
            "  " + KEY_MARITAL_STATUS + " tinyint(3) DEFAULT '1',\n" +
            "  " + KEY_RELIGION + " tinyint(3) DEFAULT '1',\n" +
            "  " + KEY_EDUCATION + " tinyint(3) DEFAULT '1',\n" +
            "  " + KEY_EDUCATION_DETAILS + " text,\n" +
            "  " + KEY_HOUSE_STATUS + " tinyint(3) DEFAULT '1',\n" +
            "  " + KEY_HOUSE_TYPE + " tinyint(3) DEFAULT '0',\n" +
            "  " + KEY_NUMBER_0F_CHILDREN + " smallint(6) DEFAULT '0',\n" +
            "  " + KEY_NUMBER_0F_SCHOOL_GOING_CHILDREN + " smallint(6) DEFAULT '0',\n" +
            "  " + KEY_CUSTOMER_GPS_LOC + " text,\n" +
            "  " + KEY_ADDRESS_PERMANENT_COUNTRY + " varchar(255) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PERMANENT_STATE + " varchar(255) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PERMANENT_DISTRICT_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PERMANENT_CITY + " varchar(255) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PERMANENT_TALUKA_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PERMANENT_UC_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PERMANENT_ADDRESS + " text,\n" +
            "  " + KEY_ADDRESS_PERMANENT_ZIPCODE + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PRESENT_COUNTRY + " varchar(255) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PRESENT_STATE + " varchar(255) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PRESENT_DISTRICT_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PRESENT_CITY + " varchar(255) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PRESENT_TALUKA_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PRESENT_UC_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS_PRESENT_ADDRESS + " text,\n" +
            "  " + KEY_ADDRESS_PRESENT_ZIPCODE + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_PRESENT_NUM_OF_YEARS + " tinyint(3) DEFAULT '1',\n" +
            "  " + OrganizationEmployees.KEY_PHONENO + " varchar(20) DEFAULT NULL,\n" +
            "  " + OrganizationEmployees.KEY_MOBILENO + " varchar(20) DEFAULT NULL,\n" +
            "  " + OrganizationEmployees.KEY_EMAILADD + " varchar(255) DEFAULT NULL,\n" +
            "  " + KEY_DET_BANK_ACC + " text,\n" +
            "  " + KEY_CUSTOMER_FP_IMG + " BLOB,\n" +
            "  " + KEY_CUSTOMER_FP_IMG_TEMP + " text DEFAULT NULL,\n" +
            "  " + OrganizationEmployees.KEY_STATUS + " tinyint(3) DEFAULT '1',\n" +
            "  " + OrganizationEmployees.KEY_NOTES + " text,\n" +
            "  " + KEY_CUS_ADD_DATETIME + " datetime DEFAULT NULL,\n" +
            "  " + KEY_DATA_SYNCUP + " smallint(6) DEFAULT '0',\n" +
            "  " + KEY_NO_OF_LOANS + " smallint(6) DEFAULT '0'\n" +
            ")";

    public static String createMicrofinanceCustomersAsset = "   CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " (\n" +
            "  " + KEY_CUSTOMER_ASSET_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + KEY_CUSTOMER_ID + " int(11) DEFAULT NULL,\n" +
            "  " + KEY_ASSET_NAME + " varchar(200) DEFAULT NULL,\n" +
            "  " + KEY_CUSTOMER_ASSET_NAME_ID + " tinyint(3) DEFAULT '1',\n" +
            "  " + KEY_ASSET_QUANTITY + " varchar(100) DEFAULT '1',\n" +
            "  " + KEY_ASSET_VALUE + " varchar(100) DEFAULT NULL,\n" +
            "  " + KEY_ASSET_OWNER + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_NOTES + " text,\n" +
            "  " + KEY_ASSET_ADD_DATETIME + " datetime DEFAULT NULL\n" +
            ")";

    public static String createMicrofinanceCustomersGuaranteers = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + " (\n" +
            "  " + KEY_CUSTOMER_GUARANTEER_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + KEY_CUSTOMER_ID + " int(11) DEFAULT '0',\n" +
            "  " + KEY_LOAN_ID + " int(11) DEFAULT '0',\n" +
            "  " + KEY_FULL_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_NIC_NUMBER + " varchar(20) DEFAULT NULL,\n" +
            "  " + OrganizationEmployees.KEY_ADDRESS + " text,\n" +
            "  " + KEY_CONTACT_NUMBER + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_JOB_TYPE + " text,\n" +
            "  " + KEY_JOB_DESC + " text,\n" +
            "  " + KEY_BUSINESS_ADDRESS + " text,\n" +
            "  " + KEY_NOTES + " text,\n" +
            "  " + KEY_GUARANTEER_ADD_DATETIME + " datetime DEFAULT NULL,\n" +
            "  " + OrganizationEmployees.KEY_STATUS + " tinyint(3) DEFAULT '1'\n" +
            ")";

    public static String createMicrofinanceCustomersFMember = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME4 + " (\n" +
            "  " + KEY_CUSTOMER_FMEMBER_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + KEY_CUSTOMER_ID + " int(11) DEFAULT NULL,\n" +
            "  " + KEY_FULL_NAME + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_NIC_NUMBER + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_GENDER + " char(1) DEFAULT 'M',\n" +
            "  " + KEY_CUSTOMER_AGE + " smallint(6) DEFAULT '0',\n" +
            "  " + KEY_MONTHLY_EARNING + " decimal(10,2) DEFAULT '0.00',\n" +
            "  " + KEY_EDUCATION + " text,\n" +
            "  " + KEY_CUSTOMER_RELATION + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_INCOME_SOURCE + " varchar(100) DEFAULT NULL,\n" +
            "  " + KEY_BUSINESS_ADDRESS + " text,\n" +
            "  " + KEY_FMEMBER_ADD_DATETIME + " datetime DEFAULT NULL\n" +
            ")";

    /*


 */


}
