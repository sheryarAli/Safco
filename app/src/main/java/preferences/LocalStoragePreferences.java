package preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shery on 10/21/2016.
 */
public class LocalStoragePreferences {


    private Context context;
    private static final String STORAGE_NAME = "safco_microfinance";
    private static final String ISLOGGEDIN = "isloggedin";
    private static final String DOMAIN = "domain";
    private static final String TOKEN = "token";
    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String EMPLOYEEID = "empoyeeid";
    private static final String IMAGELINK = "imagelink";
    private static final String IS_SYNCED_DOWN = "isSynced";

    public LocalStoragePreferences(Context context) {
        this.context = context;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putBoolean(ISLOGGEDIN, isLoggedIn);
        sp.apply();
    }

    public boolean isSyncedDown() {

        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getBoolean(IS_SYNCED_DOWN, false);
    }

    public void setIsSyncedDown(boolean isSynced) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putBoolean(IS_SYNCED_DOWN, isSynced);
        sp.apply();
    }

    public boolean isLoggedIn() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getBoolean(ISLOGGEDIN, false);
    }

    public void setDomain(String domain) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(DOMAIN, domain.trim());
        sp.apply();
    }

    public String getDomain() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(DOMAIN, null);
    }

    public void setEmployeeId(int id) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putInt(EMPLOYEEID, id);
        sp.apply();
    }

    public int getEmployeeId() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getInt(EMPLOYEEID, 0);
    }

    public void setToken(String token) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(TOKEN, token.trim());
        sp.apply();
    }

    public String getToken() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(TOKEN, null);
    }

    public void setImageLink(String imageLink) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(IMAGELINK, imageLink.trim());
        sp.apply();
    }

    public String getImageLink() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(IMAGELINK, null);
    }

    public void setUserName(String userName) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(USERNAME, userName.trim());
        sp.apply();
    }

    public String getUserName() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(USERNAME, null);
    }

    public void setFirstName(String firstName) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(FIRSTNAME, firstName);
        sp.apply();
    }

    public String getFirstname() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(FIRSTNAME, null);
    }

    public void setLastName(String lastName) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(LASTNAME, lastName);
        sp.apply();
    }

    public String getLastname() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(LASTNAME, null);
    }

    public void clearAll() {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.clear();
        sp.commit();
    }

}
