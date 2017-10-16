package customclasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by shery on 11/23/2016.
 */

public class Stations implements Serializable {

    @SerializedName("StationId")
    private int StationId;
    @SerializedName("StationTypeId")
    private int StationTypeId;
    @SerializedName("StationParentId")
    private int StationParentId;
    @SerializedName("RegionId")
    private int RegionId;
    @SerializedName("Status")
    private int Status;


    @SerializedName("StationName")
    private String StationName;
    @SerializedName("StationCode")
    private String StationCode;
    @SerializedName("Address")
    private String Address;
    @SerializedName("City")
    private String City;
    @SerializedName("State")
    private String State;
    @SerializedName("ZipCode")
    private String ZipCode;
    @SerializedName("Country")
    private String Country;
    @SerializedName("PhoneNumber")
    private String PhoneNumber;
    @SerializedName("EmailAddress")
    private String EmailAddress;
    @SerializedName("Notes")
    private String Notes;
    @SerializedName("StationAddedDateTime")
    private String StationAddedDateTime;

    public int getStationId() {
        return StationId;
    }

    public void setStationId(int stationId) {
        StationId = stationId;
    }

    public int getStationTypeId() {
        return StationTypeId;
    }

    public void setStationTypeId(int stationTypeId) {
        StationTypeId = stationTypeId;
    }

    public int getStationParentId() {
        return StationParentId;
    }

    public void setStationParentId(int stationParentId) {
        StationParentId = stationParentId;
    }

    public int getRegionId() {
        return RegionId;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String stationCode) {
        StationCode = stationCode;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getStationAddedDateTime() {
        return StationAddedDateTime;
    }

    public void setStationAddedDateTime(String stationAddedDateTime) {
        StationAddedDateTime = stationAddedDateTime;
    }
}
