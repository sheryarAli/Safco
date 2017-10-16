package customclasses;

import java.io.Serializable;

/**
 * Created by shery on 11/25/2016.
 */

public class Asset implements Serializable{
    private int CustomerAssetId, _CustomerAssetNameId, CustomerId;
    private String AssetName, AssetQuantity, AssetValue, AssetOwner, Notes, AssetAddedDateTime;

    public int getCustomerAssetId() {
        return CustomerAssetId;
    }

    public void setCustomerAssetId(int customerAssetId) {
        CustomerAssetId = customerAssetId;
    }

    public int get_CustomerAssetNameId() {
        return _CustomerAssetNameId;
    }

    public void set_CustomerAssetNameId(int _CustomerAssetNameId) {
        this._CustomerAssetNameId = _CustomerAssetNameId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getAssetName() {
        return AssetName;
    }

    public void setAssetName(String assetName) {
        AssetName = assetName;
    }

    public String getAssetQuantity() {
        return AssetQuantity;
    }

    public void setAssetQuantity(String assetQuantity) {
        AssetQuantity = assetQuantity;
    }

    public String getAssetValue() {
        return AssetValue;
    }

    public void setAssetValue(String assetValue) {
        AssetValue = assetValue;
    }

    public String getAssetOwner() {
        return AssetOwner;
    }

    public void setAssetOwner(String assetOwner) {
        AssetOwner = assetOwner;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getAssetAddedDateTime() {
        return AssetAddedDateTime;
    }

    public void setAssetAddedDateTime(String assetAddedDateTime) {
        AssetAddedDateTime = assetAddedDateTime;
    }
}
