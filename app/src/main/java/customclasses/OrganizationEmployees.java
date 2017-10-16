package customclasses;

/**
 * Created by shery on 11/16/2016.
 */

public class OrganizationEmployees {
    public static final String TABLE_NAME = "organization_employees";
    public static final String TABLE_NAME2 = "organization_employees_stations";
    public static final String TABLE_NAME3 = "organization_employees_regions";
    public static final String TABLE_NAME4 = "organization_employeetypes";
    public static final String KEY_EMP_ID = "EmployeeId";
    public static final String KEY_EMP_TYPE_ID = "EmployeeTypeId";
    public static final String KEY_EMP_TYPE_NAME = "EmployeeTypeName";
    public static final String KEY_EMP_TYPE_ROLES = "EmployeeTypeRoles";
    public static final String KEY_STATION_ID = "StationId";
    public static final String KEY_USERNAME = "UserName";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_FIRSTNAME = "FirstName";
    public static final String KEY_LASTNAME = "LastName";
    public static final String KEY_FATHERNAME = "FatherName";
    public static final String KEY_DESIGNATION = "Designation";
    public static final String KEY_NICNUMBER = "NICNumber";
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_CITY = "City";
    public static final String KEY_STATE = "State";
    public static final String KEY_ZIPCODE = "ZipCode";
    public static final String KEY_COUNTRY = "Country";
    public static final String KEY_PHONENO = "PhoneNumber";
    public static final String KEY_MOBILENO = "MobileNumber";
    public static final String KEY_EMAILADD = "EmailAddress";
    public static final String KEY_EMP_ADD_DATETIME = "EmployeeAddedDateTime";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_NOTES = "Notes";
    public static final String KEY_EMP_ROLES = "EmployeeRoles";
    public static final String KEY_EMP_CUR_LOG_DATETIME = "EmployeeCurrentLoginDateTime";
    public static final String KEY_EMP_LAST_LOG_DATETIME = "EmployeeLastLoginDateTime";
    public static final String KEY_PWD_CHANGE_DATE = "PwdChangeDate";
    public static final String KEY_IS_PWD_CHANGE = "IsPasswordChange";
    public static final String KEY_EMPLOYEE_STATION_ID = "EmployeeStationId";

    public static final String KEY_REGION_NAME = "RegionName";
    public static final String KEY_REGION_ID = "RegionId";
    public static final String KEY_EMP_REGION_ID = "EmployeeRegionId";

    public static String createOrganiztionEmployeesTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            "  " + KEY_EMP_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + KEY_EMP_TYPE_ID + " int(11) DEFAULT '1',\n" +
            "  " + KEY_STATION_ID + " int(11) DEFAULT '0',\n" +
            "  " + KEY_USERNAME + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_PASSWORD + " varchar(100) DEFAULT NULL,\n" +
            "  " + KEY_FIRSTNAME + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_LASTNAME + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_FATHERNAME + " varchar(30) DEFAULT NULL,\n" +
            "  " + KEY_DESIGNATION + " varchar(30) DEFAULT NULL,\n" +
            "  " + KEY_NICNUMBER + " varchar(15) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS + " varchar(200) DEFAULT NULL,\n" +
            "  " + KEY_CITY + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_STATE + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_ZIPCODE + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_COUNTRY + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_PHONENO + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_MOBILENO + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_EMAILADD + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_EMP_ADD_DATETIME + " datetime DEFAULT NULL,\n" +
            "  " + KEY_STATUS + " tinyint(3) DEFAULT '1',\n" +
            "  " + KEY_NOTES + " text,\n" +
            "  " + KEY_EMP_ROLES + " text,\n" +
            "  " + KEY_EMP_CUR_LOG_DATETIME + " datetime DEFAULT NULL,\n" +
            "  " + KEY_EMP_LAST_LOG_DATETIME + " datetime DEFAULT NULL,\n" +
            "  " + KEY_PWD_CHANGE_DATE + " datetime DEFAULT NULL,\n" +
            "  " + KEY_IS_PWD_CHANGE + " tinyint(3) DEFAULT '0'\n" +
            ") \n";

    public static String createOrganiztionEmployeesStationsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " (\n" +
            "  " + KEY_EMPLOYEE_STATION_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + KEY_EMP_ID + " int(11) DEFAULT NULL,\n" +
            "  " + KEY_STATION_ID + " int(11) DEFAULT NULL\n" +
            ")";

    public static String createOrganiztionEmployeesRegionsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + " (\n" +
            "  " + KEY_EMP_REGION_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + KEY_STATION_ID + " int(11) DEFAULT '0',\n" +
            "  " + KEY_EMP_ID + " int(11) DEFAULT NULL,\n" +
            "  " + KEY_REGION_NAME+ " varchar(30) DEFAULT NULL\n" +
            ")";

    public static String createOrganiztionEmployeesTypesTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME4 + " (\n" +
            "    " + KEY_EMP_TYPE_ID + " integer primary key AUTOINCREMENT,\n" +
            "    " + KEY_EMP_TYPE_NAME + " varchar(30) DEFAULT NULL,\n" +
            "    "+ KEY_EMP_TYPE_ROLES  +" text\n" +
            "    )";


}
