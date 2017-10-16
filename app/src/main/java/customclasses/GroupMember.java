package customclasses;

/**
 * Created by shery on 12/20/2016.
 */

public class GroupMember {
    public static final String GROUP_MEMBER_TABLE = "group_member";
    public static final String KEY_GROUP_MEMBER_ID = "Group_Member_Id";
    public static final String KEY_GROUP_LEADER = "IsLeader";
    private int CustomerGroupMemberId, CustomerGroupId, CustomerId;
    private String Fullname, NicNumber, BusinessAddress, mobileNumber, Address;
    private boolean IsGroupLeader;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBusinessAddress() {
        return BusinessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        BusinessAddress = businessAddress;
    }

    public boolean isGroupLeader() {
        return IsGroupLeader;
    }

    public void setGroupLeader(boolean groupLeader) {
        IsGroupLeader = groupLeader;
    }

    public int getCustomerGroupMemberId() {
        return CustomerGroupMemberId;
    }

    public void setCustomerGroupMemberId(int customerGroupMemberId) {
        CustomerGroupMemberId = customerGroupMemberId;
    }

    public int getCustomerGroupId() {
        return CustomerGroupId;
    }

    public void setCustomerGroupId(int customerGroupId) {
        CustomerGroupId = customerGroupId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        this.Fullname = fullname;
    }

    public String getNicNumber() {
        return NicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.NicNumber = nicNumber;
    }

    public static String createGroupMemberTable = "CREATE TABLE IF NOT EXISTS " + GROUP_MEMBER_TABLE + " ("
            + KEY_GROUP_MEMBER_ID + " integer primary key autoincrement, "
            + Group.KEY_GROUP_ID + " int(11), "
            + MicrofinanceCustomers.KEY_CUSTOMER_ID + " int(11), "
            + MicrofinanceCustomers.KEY_FULL_NAME + " text, "
            + KEY_GROUP_LEADER + " int(1) default '0', "
            + MicrofinanceCustomers.KEY_NIC_NUMBER + " text "
            + ")";
}
