package customclasses;

/**
 * Created by shery on 11/23/2016.
 */

public class Region {
    private int RegionId,RegionTypeId,RegionParentId, Status, RegionDetailsStatus;
    private String RegionName,RegionCode,RegionDescription, RegionDetails, RegionDetailsComments, Notes, RegionAddedDateTime;


    public int getRegionId() {
        return RegionId;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }

    public int getRegionTypeId() {
        return RegionTypeId;
    }

    public void setRegionTypeId(int regionTypeId) {
        RegionTypeId = regionTypeId;
    }

    public int getRegionParentId() {
        return RegionParentId;
    }

    public void setRegionParentId(int regionParentId) {
        RegionParentId = regionParentId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }

    public String getRegionCode() {
        return RegionCode;
    }

    public void setRegionCode(String regionCode) {
        RegionCode = regionCode;
    }

    public String getRegionDescription() {
        return RegionDescription;
    }

    public void setRegionDescription(String regionDescription) {
        RegionDescription = regionDescription;
    }

    public String getRegionDetails() {
        return RegionDetails;
    }

    public void setRegionDetails(String regionDetails) {
        RegionDetails = regionDetails;
    }

    public int getRegionDetailsStatus() {
        return RegionDetailsStatus;
    }

    public void setRegionDetailsStatus(int regionDetailsStatus) {
        RegionDetailsStatus = regionDetailsStatus;
    }

    public String getRegionDetailsComments() {
        return RegionDetailsComments;
    }

    public void setRegionDetailsComments(String regionDetailsComments) {
        RegionDetailsComments = regionDetailsComments;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getRegionAddedDateTime() {
        return RegionAddedDateTime;
    }

    public void setRegionAddedDateTime(String regionAddedDateTime) {
        RegionAddedDateTime = regionAddedDateTime;
    }
}
