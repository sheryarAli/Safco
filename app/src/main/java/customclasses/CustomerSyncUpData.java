package customclasses;

import java.util.ArrayList;

/**
 * Created by shery on 12/30/2016.
 */

public class CustomerSyncUpData {
    private Customer customer;
    private ArrayList<Asset> assets;
    private ArrayList<Guaranteer> guaranteers;
    private ArrayList<FamilyMember> familyMembers;

    private ArrayList<LoanSyncUpData> loanSyncUpDatas;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public void setAssets(ArrayList<Asset> assets) {
        this.assets = assets;
    }

    public ArrayList<Guaranteer> getGuaranteers() {
        return guaranteers;
    }

    public void setGuaranteers(ArrayList<Guaranteer> guaranteers) {
        this.guaranteers = guaranteers;
    }

    public ArrayList<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(ArrayList<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public ArrayList<LoanSyncUpData> getLoanSyncUpDatas() {
        return loanSyncUpDatas;
    }

    public void setLoanSyncUpDatas(ArrayList<LoanSyncUpData> loanSyncUpDatas) {
        this.loanSyncUpDatas = loanSyncUpDatas;
    }
}
