package customclasses;

/**
 * Created by shery on 11/16/2016.
 */

public class OrganizationRegion {
    public static final String TABLE_NAME = "organization_regions";


    public static final String KEY_REGION_ID = "RegionId";

    public static final String KEY_REGION_TYPE_ID = "RegionTypeId";
    public static final String KEY_REGION_PARENT_ID = "RegionParentId";
    public static final String KEY_REGION_NAME = "RegionName";
    public static final String KEY_REGION_CODE = "RegionCode";
    public static final String KEY_REGION_DESC = "RegionDescription";
    public static final String KEY_REGION_DETAILS = "RegionDetails";
    public static final String KEY_REGION_DETAILS_STATUS = "RegionDetailsStatus";
    public static final String KEY_REGION_DETAILS_COMMENTS = "RegionDetailsComments";
    public static final String KEY_NOTES = "Notes";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_REGION_ADD_DATE_ADD_TIME = "RegionAddedDateTime";

    public static final String TABLE_NAME2 = "organization_regiontypes";
    public static final String KEY_REGION_TYPE_NAME = "RegionTypeName";



    //  table organization_regions
    public static String createOrganiztionRegionTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            "  " + KEY_REGION_ID + " integer PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + KEY_REGION_TYPE_ID + " int(11) DEFAULT NULL,\n" +
            "  " + KEY_REGION_PARENT_ID + " int(11) DEFAULT '0',\n" +
            "  " + KEY_REGION_NAME + " varchar(200) DEFAULT NULL,\n" +
            "  " + KEY_REGION_CODE + " varchar(200) DEFAULT NULL,\n" +
            "  " + KEY_REGION_DESC + " text,\n" +
            "  " + KEY_REGION_DETAILS + " text,\n" +
            "  " + KEY_REGION_DETAILS_STATUS + " tinyint(3) DEFAULT '0',\n" +
            "  " + KEY_REGION_DETAILS_COMMENTS + " text,\n" +
            "  " + KEY_NOTES + " text,\n" +
            "  " + KEY_STATUS + " tinyint(3) DEFAULT '1',\n" +
            "  " + KEY_REGION_ADD_DATE_ADD_TIME + " datetime DEFAULT NULL\n" +
            ")";

    //  table organization_regionstypes

    public static String createOrganiztionRegionTypes = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME2+" (\n" +
            "  "+KEY_REGION_TYPE_ID+" integer primary key AUTOINCREMENT,\n" +
            "  "+KEY_REGION_TYPE_NAME+" char(30) DEFAULT NULL,\n" +
            "  "+KEY_REGION_PARENT_ID+" int(11) DEFAULT '0'\n" +
            ")";

}
