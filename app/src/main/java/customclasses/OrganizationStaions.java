package customclasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shery on 11/17/2016.
 */

public class OrganizationStaions {
    public static final String TABLE_NAME = "organization_stations";
    public static final String KEY_STATION_ID = "StationId";
    public static final String KEY_STATION_TYPE_ID = "StationTypeId";
    public static final String KEY_STATION_PARENT_ID = "StationParentId";
    public static final String KEY_REGION_ID = "RegionId";
    public static final String KEY_STATION_CODE = "StationCode";
    public static final String KEY_STATION_NAME = "StationName";
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_CITY = "City";
    public static final String KEY_STATE = "State";
    public static final String KEY_ZIPCODE = "ZipCode";
    public static final String KEY_COUNTRY = "Country";
    public static final String KEY_PHONENO = "PhoneNumber";
    public static final String KEY_EMAIL_ADD = "EmailAddress";
    public static final String KEY_NOTES = "Notes";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_STATION_ADD_DATETIME = "StationAddedDateTime";

    public static final String TABLE_NAME2 = "organization_stationtypes";
    public static final String KEY_STATION_TYPE_NAME = "StationTypeName";

    @SerializedName("StationId")
    private int stationId;
    private int stationTypeId;
    private int stationParentId;
    private int regionId;
    private int status;
    private String stationCode;
    private String stationName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phoneNo;
    private String emailAdd;
    private String notes;
    private String stationAddDT;

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getStationTypeId() {
        return stationTypeId;
    }

    public void setStationTypeId(int stationTypeId) {
        this.stationTypeId = stationTypeId;
    }

    public int getStationParentId() {
        return stationParentId;
    }

    public void setStationParentId(int stationParentId) {
        this.stationParentId = stationParentId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStationAddDT() {
        return stationAddDT;
    }

    public void setStationAddDT(String stationAddDT) {
        this.stationAddDT = stationAddDT;
    }

    public static String getCreateOrganizationStations() {
        return createOrganizationStations;
    }

    public static void setCreateOrganizationStations(String createOrganizationStations) {
        OrganizationStaions.createOrganizationStations = createOrganizationStations;
    }

    public static String getCreateOrganizationStationsTypes() {
        return createOrganizationStationsTypes;
    }

    public static void setCreateOrganizationStationsTypes(String createOrganizationStationsTypes) {
        OrganizationStaions.createOrganizationStationsTypes = createOrganizationStationsTypes;
    }

    //  table organization_stations
    public static String createOrganizationStations = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            "  " + KEY_STATION_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + KEY_STATION_TYPE_ID + " int(11) DEFAULT NULL,\n" +
            "  " + KEY_STATION_PARENT_ID + " int(11) DEFAULT '0',\n" +
            "  " + KEY_REGION_ID + " int(11) DEFAULT '1',\n" +
            "  " + KEY_STATION_CODE + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_STATION_NAME + " varchar(40) DEFAULT NULL,\n" +
            "  " + KEY_ADDRESS + " varchar(200) DEFAULT NULL,\n" +
            "  " + KEY_CITY + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_STATE + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_ZIPCODE + " varchar(10) DEFAULT NULL,\n" +
            "  " + KEY_COUNTRY + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_PHONENO + " varchar(20) DEFAULT NULL,\n" +
            "  " + KEY_EMAIL_ADD + " varchar(60) DEFAULT NULL,\n" +
            "  " + KEY_NOTES + " text,\n" +
            "  " + KEY_STATUS + " tinyint(3) DEFAULT '1',\n" +
            "  " + KEY_STATION_ADD_DATETIME + " datetime DEFAULT NULL\n" +
            ")";


    //  table organization_stationstypes
    public static String createOrganizationStationsTypes = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " (\n" +
            "  " + KEY_STATION_TYPE_ID + " integer primary key AUTOINCREMENT,\n" +
            "  " + KEY_STATION_TYPE_NAME + " char(20) DEFAULT NULL\n" +
            ")";


}
