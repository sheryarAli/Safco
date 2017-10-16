package customclasses;

import java.util.ArrayList;

/**
 * Created by shery on 12/6/2016.
 */

public class GroupSyncUpData {



    private Group group;
    private ArrayList<CustomerSyncUpData> customerSyncUpDatas = new ArrayList<>();

    private ArrayList<GroupMember> groupMembers = new ArrayList<>();




    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public ArrayList<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public ArrayList<CustomerSyncUpData> getCustomerSyncUpDatas() {
        return customerSyncUpDatas;
    }

    public void setCustomerSyncUpDatas(ArrayList<CustomerSyncUpData> customerSyncUpDatas) {
        this.customerSyncUpDatas = customerSyncUpDatas;
    }

}
