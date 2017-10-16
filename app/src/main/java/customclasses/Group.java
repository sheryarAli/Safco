package customclasses;

import java.io.Serializable;

/**
 * Created by shery on 12/9/2016.
 */

public class Group implements Serializable {
    public static final String GROUP_TABLE = "Groups";
    public static final String KEY_GROUP_ID = "Group_Id";
    public static final String KEY_GROUP_NAME = "Group_Name";
    public static final String KEY_GROUP_NOTE = "Group_Note";
    public static final String KEY_GROUP_CHECK_LIST_DATE = "Group_Check_List_Date";
    public static final String KEY_GROUP_STATUS = "Group_Status";
    public static final String KEY_GROUP_ADD_BY = "Group_Added_By";
    public static final String KEY_GROUP_APP_BY = "Group_Approved_By";
    public static final String KEY_GROUP_VER_BY = "Group_Verified_By";
    public static final String KEY_GROUP_ADD_BY_DES = "Group_Added_By_Designation";
    public static final String KEY_GROUP_APP_BY_DES = "Group_Approved_By_Designation";
    public static final String KEY_GROUP_VER_BY_DES = "Group_Verified_By_Designation";
    public static final String KEY_GROUP_ADD_BY_COM = "Group_Added_By_Comment";
    public static final String KEY_GROUP_APP_BY_COM = "Group_Approved_By_Comment";
    public static final String KEY_GROUP_VER_BY_COM = "Group_Verified_By_Comment";

    private int groupId, syncUpStatus;
    private String Group_Name, Group_Check_List_Date, Group_Status, Group_Note,
            Group_Added_By, Group_Approved_By, Group_Verified_By,
            Group_Added_By_Designation, Group_Approved_By_Designation, Group_Verified_By_Designation,
            Group_Added_By_Comment, Group_Approved_By_Comment, Group_Verified_By_Comment;

    public int getSyncUpStatus() {
        return syncUpStatus;
    }

    public void setSyncUpStatus(int syncUpStatus) {
        this.syncUpStatus = syncUpStatus;
    }

    public String getGroup_Note() {
        return Group_Note;
    }

    public void setGroup_Note(String group_Note) {
        Group_Note = group_Note;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroup_Name() {
        return Group_Name;
    }

    public void setGroup_Name(String group_Name) {
        Group_Name = group_Name;
    }

    public String getGroup_Check_List_Date() {
        return Group_Check_List_Date;
    }

    public void setGroup_Check_List_Date(String group_Check_List_Date) {
        Group_Check_List_Date = group_Check_List_Date;
    }

    public String getGroup_Status() {
        return Group_Status;
    }

    public void setGroup_Status(String group_Status) {
        Group_Status = group_Status;
    }

    public String getGroup_Added_By() {
        return Group_Added_By;
    }

    public void setGroup_Added_By(String group_Added_By) {
        Group_Added_By = group_Added_By;
    }

    public String getGroup_Approved_By() {
        return Group_Approved_By;
    }

    public void setGroup_Approved_By(String group_Approved_By) {
        Group_Approved_By = group_Approved_By;
    }

    public String getGroup_Verified_By() {
        return Group_Verified_By;
    }

    public void setGroup_Verified_By(String group_Verified_By) {
        Group_Verified_By = group_Verified_By;
    }

    public String getGroup_Added_By_Designation() {
        return Group_Added_By_Designation;
    }

    public void setGroup_Added_By_Designation(String group_Added_By_Designation) {
        Group_Added_By_Designation = group_Added_By_Designation;
    }

    public String getGroup_Approved_By_Designation() {
        return Group_Approved_By_Designation;
    }

    public void setGroup_Approved_By_Designation(String group_Approved_By_Designation) {
        Group_Approved_By_Designation = group_Approved_By_Designation;
    }

    public String getGroup_Verified_By_Designation() {
        return Group_Verified_By_Designation;
    }

    public void setGroup_Verified_By_Designation(String group_Verified_By_Designation) {
        Group_Verified_By_Designation = group_Verified_By_Designation;
    }

    public String getGroup_Added_By_Comment() {
        return Group_Added_By_Comment;
    }

    public void setGroup_Added_By_Comment(String group_Added_By_Comment) {
        Group_Added_By_Comment = group_Added_By_Comment;
    }

    public String getGroup_Approved_By_Comment() {
        return Group_Approved_By_Comment;
    }

    public void setGroup_Approved_By_Comment(String group_Approved_By_Comment) {
        Group_Approved_By_Comment = group_Approved_By_Comment;
    }

    public String getGroup_Verified_By_Comment() {
        return Group_Verified_By_Comment;
    }

    public void setGroup_Verified_By_Comment(String group_Verified_By_Comment) {
        Group_Verified_By_Comment = group_Verified_By_Comment;
    }


    public static String createGroupTable = "CREATE TABLE IF NOT EXISTS " + GROUP_TABLE + " (" +
            " " + KEY_GROUP_ID + " integer primary key AUTOINCREMENT," +
            " " + KEY_GROUP_NAME + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_CHECK_LIST_DATE + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_STATUS + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_NOTE + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_ADD_BY + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_ADD_BY_DES + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_ADD_BY_COM + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_APP_BY + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_APP_BY_DES + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_APP_BY_COM + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_VER_BY + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_VER_BY_DES + " varchar(60) DEFAULT NULL," +
            " " + KEY_GROUP_VER_BY_COM + " varchar(60) DEFAULT NULL," +
            " " + CommonConst.KEY_CUR_ADD_DATE + " varchar(60) DEFAULT NULL," +
            " " + MicrofinanceCustomers.KEY_DATA_SYNCUP + " int(2) DEFAULT '0'" +
            ") ";

}
