package databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.Document;
import customclasses.MicrofinanceCustomers;
import customclasses.OrganizationEmployees;
import customclasses.OrganizationStaions;
import customclasses.Questionnaire;
import customclasses.QuestionnaireAnswer;
import customclasses.Repayment;
import customclasses.RepaymentLog;

import static customclasses.Repayment.*;
import static customclasses.RepaymentLog.REPAYMENT_LOG_TABLE;
import static customclasses.RepaymentLog.createRepaymentLogTable;


/**
 * Created by shery on 8/12/2017.
 */

public class DbHandler2 extends SQLiteOpenHelper {
    private static final String TAG = DBHandler.class.getSimpleName();
    public static final String DATABASE_NAME = "ssf_mms_db2";
    public static final int DB_VERSION = 4;

    public DbHandler2(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    //TODO onCreate
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createRepaymentTable);
        sqLiteDatabase.execSQL(createRepaymentLogTable);
    }

    //TODO onUpgrade

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        //TODO For Debuging Only
        onCreate(sqLiteDatabase);
        /*
        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_DATA_SYNCUP)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_DATA_SYNCUP + " smallint(6) DEFAULT '0'");
        }

        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, OrganizationStaions.KEY_STATION_ID)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + OrganizationStaions.KEY_STATION_ID + " integer");
        }
        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_NIC_NUMBER)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_NIC_NUMBER + " text");
        }
        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_LAST_REP_AMOUNT)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_LAST_REP_AMOUNT + " text");
        }

        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_LAST_REP_DT)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_LAST_REP_DT + " text");
        }

        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_CURRENT_REP_AMOUNT)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_CURRENT_REP_AMOUNT + " text");
        }

        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_CURRENT_REP_DT)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_CURRENT_REP_DT + " text");
        }

        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_REP_AMOUNT)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_REP_AMOUNT + " text");
        }
        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_REP_DT)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_REP_DT + " text");
        }
        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_PENALTY)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_PENALTY + " text");
        }
        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, REPAYMENT_TABLE, KEY_PROCESSING_FEES)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + REPAYMENT_TABLE + " ADD COLUMN " + KEY_PROCESSING_FEES + " text");
        }*/
    }

    //TODO updateRepaymentAmountInLog
    public boolean updateRepaymentAmountInLog(int id, String repaymentAmount) {
        SQLiteDatabase dbW = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REP_AMOUNT, repaymentAmount);
        int res = dbW.update(REPAYMENT_LOG_TABLE, values,
                KEY_ID + " = ? ",
                new String[]{id + ""});
        dbW.close();
        Log.d(TAG, "updateRepaymentAmountInLog: res: " + res);
        return res != 0;
    }

    //TODO updateRepaymentData
    public long updateRepaymentData(Repayment repayment) {
        SQLiteDatabase dbW = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String repaymentAmount = repayment.getRepaymentAmount();
        if (repaymentAmount == null || repaymentAmount.equalsIgnoreCase(""))
            repaymentAmount = "0";
        values.put(KEY_REP_AMOUNT, repaymentAmount);
        values.put(KEY_PENALTY, repayment.getPenalty() == null ? "" : repayment.getPenalty());
        values.put(KEY_PROCESSING_FEES, repayment.getProccessingFees() == null ? "" : repayment.getProccessingFees());
        values.put(KEY_REP_DT, repayment.getRepaymentDateTime() == null ? "" : repayment.getRepaymentDateTime());

        int res = dbW.update(REPAYMENT_TABLE, values,
                KEY_LOAN_ID + " = ? ",
                new String[]{repayment.getLoanId() + ""});
        dbW.close();
        return res;
    }

    //TODO updateRepaymentDataSyncStatus
    public void updateRepaymentDataSyncStatus(ArrayList<Repayment> repayments) {
        SQLiteDatabase dbW = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        /*values.put(KEY_REP_AMOUNT, repayment.getRepaymentAmount());
        values.put(KEY_PENALTY, repayment.getPenalty());
        values.put(KEY_PROCESSING_FEES, repayment.getProccessingFees());*/
        for (Repayment repayment : repayments) {
            values.put(KEY_DATA_SYNCUP, 1);

            int res = dbW.update(REPAYMENT_TABLE, values,
                    KEY_LOAN_ID + " = ? ",
                    new String[]{repayment.getLoanId() + ""});
        }
        dbW.close();
    }

    //TODO getTotalRepaymentPaid
    public double getTotalRepaymentPaid(int loanId) {
        double totalRepaymentPaid = 0;
        SQLiteDatabase dbR = this.getReadableDatabase();
        Cursor cursor = dbR.rawQuery("SELECT RepaymentAmount FROM " + REPAYMENT_LOG_TABLE + " WHERE loanId = " + loanId, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    totalRepaymentPaid += Double.parseDouble(cursor.getString(0));
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }
        return totalRepaymentPaid;
    }

    //TODO getSyncedRepaymentLogDataByLoanId
    public ArrayList<RepaymentLog> getSyncedRepaymentLogDataByLoanId(int loanId) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<RepaymentLog> repaymentLogs = new ArrayList<RepaymentLog>();
        Cursor cursor = dbR.rawQuery("SELECT id, loanId, customerGroupId, NICNumber, customerName, RepaymentAmount, RepaymentDateTime, Penalty, ProccessingFees, StationId FROM "
                + REPAYMENT_LOG_TABLE + " WHERE (loanId = " + loanId + ") AND (IsSyncedUp = 1)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RepaymentLog repaymentLog = getRepaymentLogFromCursor(cursor);
                    repaymentLogs.add(repaymentLog);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }
        return repaymentLogs;
    }


    //TODO getRepaymentLogDataByLoanId
    public ArrayList<RepaymentLog> getRepaymentLogDataByLoanId(int loanId) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<RepaymentLog> repaymentLogs = new ArrayList<RepaymentLog>();
        Cursor cursor = dbR.rawQuery("SELECT id, loanId, customerGroupId, NICNumber, customerName, RepaymentAmount, RepaymentDateTime, Penalty, ProccessingFees, StationId FROM "
                + REPAYMENT_LOG_TABLE + " WHERE (loanId = " + loanId + ") AND (IsSyncedUp = 0)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RepaymentLog repaymentLog = getRepaymentLogFromCursor(cursor);
                    repaymentLogs.add(repaymentLog);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }
        return repaymentLogs;
    }

    //TODO getRepaymentLogDataByNic
    public ArrayList<RepaymentLog> getRepaymentLogDataByNic(String nic) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<RepaymentLog> repaymentLogs = new ArrayList<RepaymentLog>();
        Cursor cursor = dbR.rawQuery("SELECT id, loanId, customerGroupId, NICNumber, customerName, RepaymentAmount, RepaymentDateTime, Penalty, ProccessingFees, StationId FROM "
                + REPAYMENT_LOG_TABLE + " WHERE (NICNumber = '" + nic + "') AND (IsSyncedUp = 0)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RepaymentLog repaymentLog = getRepaymentLogFromCursor(cursor);
                    repaymentLogs.add(repaymentLog);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }
        return repaymentLogs;
    }

    //TODO getSyncedRepaymentLogDataByNic
    public ArrayList<RepaymentLog> getSyncedRepaymentLogDataByNic(String nic) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<RepaymentLog> repaymentLogs = new ArrayList<RepaymentLog>();
        Cursor cursor = dbR.rawQuery("SELECT id, loanId, customerGroupId, NICNumber, customerName, RepaymentAmount, RepaymentDateTime, Penalty, ProccessingFees, StationId FROM "
                + REPAYMENT_LOG_TABLE + " WHERE (NICNumber = '" + nic + "') AND (IsSyncedUp = 1)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RepaymentLog repaymentLog = getRepaymentLogFromCursor(cursor);
                    repaymentLogs.add(repaymentLog);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }
        return repaymentLogs;
    }

    //TODO getRepaymentLogDataByDate
    public ArrayList<RepaymentLog> getRepaymentLogDataByDate(String date) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<RepaymentLog> repaymentLogs = new ArrayList<RepaymentLog>();
        Cursor cursor = dbR.rawQuery("SELECT id, loanId, customerGroupId, RepaymentAmount, NICNumber, customerName, RepaymentDateTime, Penalty, ProccessingFees, StationId FROM "
                + REPAYMENT_LOG_TABLE + " WHERE (RepaymentDateTime LIKE '" + date + "%') AND (IsSyncedUp = 0)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RepaymentLog repaymentLog = getRepaymentLogFromCursor(cursor);
                    repaymentLogs.add(repaymentLog);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }
        return repaymentLogs;
    }

    //TODO getSyncedRepaymentLogDataByDate
    public ArrayList<RepaymentLog> getSyncedRepaymentLogDataByDate(String date) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<RepaymentLog> repaymentLogs = new ArrayList<RepaymentLog>();
        Cursor cursor = dbR.rawQuery("SELECT id, loanId, customerGroupId, RepaymentAmount, NICNumber, customerName, RepaymentDateTime, Penalty, ProccessingFees, StationId FROM "
                + REPAYMENT_LOG_TABLE + " WHERE (RepaymentDateTime LIKE '" + date + "%') AND (IsSyncedUp = 1)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RepaymentLog repaymentLog = getRepaymentLogFromCursor(cursor);
                    repaymentLogs.add(repaymentLog);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }
        return repaymentLogs;
    }

    //TODO getRepaymentLogFromCursor
    public RepaymentLog getRepaymentLogFromCursor(Cursor cursor) {
        RepaymentLog repaymentLog = new RepaymentLog();
        repaymentLog.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        repaymentLog.setLoanId(cursor.getInt(cursor.getColumnIndex(KEY_LOAN_ID)));
        repaymentLog.setCustomerGroupId(cursor.getString(cursor.getColumnIndex(KEY_CUSTOMER_GROUP_ID)));
        repaymentLog.setNICNumber(cursor.getString(cursor.getColumnIndex(KEY_NIC_NUMBER)));
        repaymentLog.setCustomerName(cursor.getString(cursor.getColumnIndex(KEY_CUSTOMER_NAME)));
        repaymentLog.setRepaymentAmount(cursor.getString(cursor.getColumnIndex(KEY_REP_AMOUNT)));
        repaymentLog.setPenalty(cursor.getString(cursor.getColumnIndex(KEY_PENALTY)));
        repaymentLog.setProccessingFees(cursor.getString(cursor.getColumnIndex(KEY_PROCESSING_FEES)));
        repaymentLog.setRepaymentDateTime(cursor.getString(cursor.getColumnIndex(KEY_REP_DT)));
        repaymentLog.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
        return repaymentLog;
    }

    //TODO getRepaymentLogData
    public ArrayList<RepaymentLog> getRepaymentLogData() {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<RepaymentLog> repaymentLogs = new ArrayList<RepaymentLog>();
        Cursor cursor = dbR.rawQuery("SELECT id, loanId, customerGroupId, RepaymentAmount, NICNumber, customerName, RepaymentDateTime, Penalty, ProccessingFees, StationId FROM "
                + REPAYMENT_LOG_TABLE + " WHERE IsSyncedUp = 0", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RepaymentLog repaymentLog = getRepaymentLogFromCursor(cursor);
                    repaymentLogs.add(repaymentLog);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }

        return repaymentLogs;
    }

    //TODO updateRepaymentLogDataSyncStatus
    public void updateRepaymentLogDataSyncStatus(ArrayList<RepaymentLog> repaymentLogs) {
        SQLiteDatabase dbW = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (RepaymentLog repaymentLog : repaymentLogs) {
            values.put(KEY_DATA_SYNCUP, 1);
            int res = dbW.update(REPAYMENT_LOG_TABLE, values,
                    KEY_ID + " = ? ",
                    new String[]{repaymentLog.getId() + ""});
        }
        dbW.close();
    }

    //TODO deleteRepaymentLogData
    public void deleteRepaymentLogData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + REPAYMENT_LOG_TABLE + " WHERE " + KEY_ID + " = " + id + "");
        db.close();
    }

    //TODO insertRepaymentLogData
    public long insertRepaymentLogData(RepaymentLog repaymentLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOAN_ID, repaymentLog.getLoanId());
        values.put(KEY_CUSTOMER_GROUP_ID, repaymentLog.getCustomerGroupId());
        values.put(KEY_CUSTOMER_NAME, repaymentLog.getCustomerName());
        values.put(KEY_NIC_NUMBER, repaymentLog.getNICNumber());
        values.put(KEY_REP_AMOUNT, repaymentLog.getRepaymentAmount());
        values.put(KEY_PENALTY, repaymentLog.getPenalty());
        values.put(KEY_PROCESSING_FEES, repaymentLog.getProccessingFees());
        values.put(KEY_REP_DT, repaymentLog.getRepaymentDateTime());
        values.put(OrganizationStaions.KEY_STATION_ID, repaymentLog.getStationId());
        long res = db.insert(REPAYMENT_LOG_TABLE, null, values);
        db.close();
        return res;
    }

    //TODO insertRepaymentData
    public long insertRepaymentData(Repayment repayment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOAN_ID, repayment.getLoanId());
        values.put(KEY_EMPLOYEE_ID, repayment.getEmployeeId());
        values.put(OrganizationStaions.KEY_STATION_ID, repayment.getStationId());
        values.put(KEY_CUSTOMER_NAME, repayment.getCustomerName());
        values.put(KEY_NIC_NUMBER, repayment.getNICNumber());
        values.put(KEY_STATION_NAME, repayment.getStationName());
        values.put(KEY_REP_START_DATE, repayment.getRepaymentStartDate());
        values.put(KEY_CUSTOMER_GROUP_ID, repayment.getCustomerGroupId());
        values.put(KEY_CHEQ_NUMBER, repayment.getChequeNumber());
        values.put(KEY_APP_AMOUNT, repayment.getApproveAmount());
        values.put(KEY_FP_IMAGE_TEMP, repayment.getfPImageTemp());
        values.put(KEY_EMERGENCY_FUND, repayment.getEmergencyFund());
        values.put(KEY_OTHER_FEES, repayment.getOtherFees());
        values.put(KEY_REG_FEES, repayment.getRegistrtaionFees());
        values.put(KEY_SERVICE_CHARGES_RATE, repayment.getServiceChargesRate());
        values.put(KEY_TOTAL_PAID, repayment.getTotalpaid());
        values.put(KEY_TOTAL_LOAN_AMOUNT, repayment.getTotalLoanAmount());
        values.put(KEY_LAST_REP_AMOUNT, repayment.getLastRepaymentAmount());
        values.put(KEY_LAST_REP_DT, repayment.getLastRepaymentDateTime());
        values.put(KEY_CURRENT_REP_AMOUNT, repayment.getCurrentRepaymentAmount());
        values.put(KEY_CURRENT_REP_DT, repayment.getCurrentRepaymentDateTime());
        long res = db.insert(REPAYMENT_TABLE, null, values);
        db.close();
        return res;
    }

    //TODO getRepaymentDataForSyncUp
    public ArrayList<Repayment> getRepaymentDataForSyncUp() {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<Repayment> repayments = new ArrayList<Repayment>();

        Cursor cursor = dbR.rawQuery("SELECT loanId, RepaymentAmount, Penalty, ProccessingFees, StationId, RepaymentDateTime FROM " + REPAYMENT_TABLE + " WHERE (" + KEY_REP_AMOUNT + " <> '' AND " + KEY_REP_AMOUNT + " <> '0' ) AND ( " + KEY_DATA_SYNCUP + " = 0)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Repayment repayment = new Repayment();
                    repayment.setLoanId(cursor.getInt(cursor.getColumnIndex(KEY_LOAN_ID)));
                    repayment.setRepaymentAmount(cursor.getString(cursor.getColumnIndex(KEY_REP_AMOUNT)));
                    repayment.setPenalty(cursor.getString(cursor.getColumnIndex(KEY_PENALTY)));
                    repayment.setProccessingFees(cursor.getString(cursor.getColumnIndex(KEY_PROCESSING_FEES)));
                    repayment.setRepaymentDateTime(cursor.getString(cursor.getColumnIndex(KEY_REP_DT)));
                    repayment.setStationId(cursor.getInt(cursor.getColumnIndex("StationId")));
                    repayments.add(repayment);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            dbR.close();
        }

        return repayments;
    }

    //TODO getSyncedRepaymentDataByGroupId
    public ArrayList<Repayment> getSyncedRepaymentDataByGroupId(SQLiteDatabase dbR, int customerGroupId) {
        ArrayList<Repayment> repayments = new ArrayList<Repayment>();
        Cursor cursor = dbR.rawQuery("SELECT loanId, customerName, NICNumber, LastRepaymentDateTime, totalLoanAmount, Totalpaid, CurrentRepaymentAmount, customerGroupId, RepaymentDateTime, RepaymentAmount, Penalty, ProccessingFees FROM " + REPAYMENT_TABLE +
                " WHERE (" + KEY_CUSTOMER_GROUP_ID + " = " + customerGroupId + ") AND (" + KEY_DATA_SYNCUP + " = 1)", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Repayment repayment = getRepaymentFromCursor(cursor);
                repayments.add(repayment);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return repayments;
    }

    //TODO getRepaymentDataByGroupId
    public ArrayList<Repayment> getRepaymentDataByGroupId(SQLiteDatabase dbR, int customerGroupId) {
        ArrayList<Repayment> repayments = new ArrayList<Repayment>();
        Cursor cursor = dbR.rawQuery("SELECT loanId, customerName, NICNumber, LastRepaymentDateTime, totalLoanAmount, Totalpaid, CurrentRepaymentAmount, customerGroupId, RepaymentDateTime, RepaymentAmount, Penalty, ProccessingFees, StationId FROM " + REPAYMENT_TABLE +
                " WHERE (" + KEY_CUSTOMER_GROUP_ID + " = " + customerGroupId + ") AND (" + KEY_DATA_SYNCUP + " = 0)", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Repayment repayment = getRepaymentFromCursor(cursor);
                repayments.add(repayment);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return repayments;
    }


    //TODO memberExistsInRepayments
    public boolean memberExistsInRepayments(ArrayList<Repayment> repayments, int loanId, String nic) {
        for (Repayment repayment : repayments) {
            if (loanId == repayment.getLoanId() || nic.equalsIgnoreCase(repayment.getNICNumber()))
                return true;
        }
        return false;
    }

    //TODO getRepaymentFromCursor
    private Repayment getRepaymentFromCursor(Cursor cursor) {
        Repayment repayment = new Repayment();
        repayment.setLoanId(cursor.getInt(cursor.getColumnIndex(KEY_LOAN_ID)));
        repayment.setCustomerName(cursor.getString(cursor.getColumnIndex(KEY_CUSTOMER_NAME)));
        repayment.setNICNumber(cursor.getString(cursor.getColumnIndex(KEY_NIC_NUMBER)));
        repayment.setLastRepaymentDateTime(cursor.getString(cursor.getColumnIndex(KEY_LAST_REP_DT)));
        repayment.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATION_ID)));

        String repaymentDate = cursor.getString(cursor.getColumnIndex(KEY_REP_DT));
        if (repaymentDate != null && !repaymentDate.isEmpty()) {
            repayment.setRepaymentDateTime(repaymentDate);
        } else {
            repayment.setRepaymentDateTime("");
        }
        String repaymentAmount = cursor.getString(cursor.getColumnIndex(KEY_REP_AMOUNT));
        if (repaymentAmount != null && !repaymentAmount.isEmpty()) {
            repayment.setRepaymentAmount(repaymentAmount);
        } else {
            repayment.setRepaymentAmount("0");
        }
        String penalty = cursor.getString(cursor.getColumnIndex(KEY_PENALTY));
        if (penalty != null && !penalty.isEmpty()) {
            repayment.setPenalty(penalty);
        } else {
            repayment.setRepaymentAmount("0");
        }
        String proccessingFees = cursor.getString(cursor.getColumnIndex(KEY_PROCESSING_FEES));
        if (proccessingFees != null && !proccessingFees.isEmpty()) {
            repayment.setProccessingFees(proccessingFees);

        } else {
            repayment.setRepaymentAmount("0");
        }
        String totalLoanAmountStr = cursor.getString(cursor.getColumnIndex(KEY_TOTAL_LOAN_AMOUNT));
        if (totalLoanAmountStr == null || totalLoanAmountStr.isEmpty()) {
            repayment.setTotalLoanAmount("0");
        } else {
            repayment.setTotalLoanAmount(cursor.getString(cursor.getColumnIndex(KEY_TOTAL_LOAN_AMOUNT)));
        }

        String totalPaidStr = cursor.getString(cursor.getColumnIndex(KEY_TOTAL_PAID));
        if (totalPaidStr == null || totalPaidStr.isEmpty()) {
            repayment.setTotalpaid("0");
        } else {
            repayment.setTotalpaid(cursor.getString(cursor.getColumnIndex(KEY_TOTAL_PAID)));
        }


        repayment.setCurrentRepaymentAmount(cursor.getString(cursor.getColumnIndex(KEY_CURRENT_REP_AMOUNT)));
        repayment.setCustomerGroupId(cursor.getString(cursor.getColumnIndex(KEY_CUSTOMER_GROUP_ID)));
        return repayment;
    }

    //TODO getSyncedRepaymentDataByLoanId
    public ArrayList<Repayment> getSyncedRepaymentDataByLoanId(int loanId) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<Repayment> repayments = new ArrayList<Repayment>();
        Cursor cursor = dbR.rawQuery("SELECT loanId, customerName, NICNumber, LastRepaymentDateTime, totalLoanAmount, Totalpaid, CurrentRepaymentAmount, customerGroupId, RepaymentDateTime, RepaymentAmount, Penalty, ProccessingFees FROM " + REPAYMENT_TABLE +
                " WHERE (" + KEY_LOAN_ID + " = " + loanId + ") AND (" + KEY_DATA_SYNCUP + " = 1)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Repayment repayment = getRepaymentFromCursor(cursor);
                    repayments.add(repayment);
                } while (cursor.moveToNext());
                cursor.close();

                if (repayments.get(0).getCustomerGroupId() != null && !repayments.get(0).getCustomerGroupId().equalsIgnoreCase("")) {
                    for (Repayment repayment : getSyncedRepaymentDataByGroupId(dbR, Integer.parseInt(repayments.get(0).getCustomerGroupId()))) {
                        if (!memberExistsInRepayments(repayments, repayment.getLoanId(), repayment.getNICNumber())) {
                            repayments.add(repayment);
                        }
                    }
                }
            }
        } finally {

            dbR.close();
        }
        return repayments;
    }

    //TODO getRepaymentDataByLoanId
    public ArrayList<Repayment> getRepaymentDataByLoanId(int loanId) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<Repayment> repayments = new ArrayList<Repayment>();
        Cursor cursor = dbR.rawQuery("SELECT loanId, customerName, NICNumber, LastRepaymentDateTime, totalLoanAmount, Totalpaid, CurrentRepaymentAmount, customerGroupId, RepaymentDateTime, RepaymentAmount, Penalty, ProccessingFees, StationId FROM " + REPAYMENT_TABLE +
                " WHERE (" + KEY_LOAN_ID + " = " + loanId + ") AND (" + KEY_DATA_SYNCUP + " = 0)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Repayment repayment = getRepaymentFromCursor(cursor);
                    repayments.add(repayment);
                } while (cursor.moveToNext());
                cursor.close();

                if (repayments.get(0).getCustomerGroupId() != null && !repayments.get(0).getCustomerGroupId().equalsIgnoreCase("")) {
                    for (Repayment repayment : getRepaymentDataByGroupId(dbR, Integer.parseInt(repayments.get(0).getCustomerGroupId()))) {
                        if (!memberExistsInRepayments(repayments, repayment.getLoanId(), repayment.getNICNumber())) {
                            repayments.add(repayment);
                        }
                    }
                }
            }
        } finally {

            dbR.close();
        }
        return repayments;
    }

    //TODO getSyncedRepaymentDataByNic
    public ArrayList<Repayment> getSyncedRepaymentDataByNic(String nic) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<Repayment> repayments = new ArrayList<Repayment>();
        Cursor cursor = dbR.rawQuery("SELECT loanId, customerName, NICNumber, LastRepaymentDateTime, totalLoanAmount, Totalpaid, CurrentRepaymentAmount, customerGroupId, RepaymentDateTime, RepaymentAmount, Penalty, ProccessingFees FROM " + REPAYMENT_TABLE +
                " WHERE (" + KEY_NIC_NUMBER + " = '" + nic + "') AND (" + KEY_DATA_SYNCUP + " = 1)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Repayment repayment = getRepaymentFromCursor(cursor);
                    repayments.add(repayment);
                } while (cursor.moveToNext());
                cursor.close();

                if (repayments.get(0).getCustomerGroupId() != null && !repayments.get(0).getCustomerGroupId().equalsIgnoreCase("")) {
                    for (Repayment repayment : getSyncedRepaymentDataByGroupId(dbR, Integer.parseInt(repayments.get(0).getCustomerGroupId()))) {
                        if (!memberExistsInRepayments(repayments, repayment.getLoanId(), repayment.getNICNumber())) {
                            repayments.add(repayment);
                        }
                    }
                }
            }
        } finally {

            dbR.close();
        }
        return repayments;
    }

    //TODO getRepaymentDataByNic
    public ArrayList<Repayment> getRepaymentDataByNic(String nic) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        ArrayList<Repayment> repayments = new ArrayList<Repayment>();
        Cursor cursor = dbR.rawQuery("SELECT loanId, customerName, NICNumber, LastRepaymentDateTime, totalLoanAmount, Totalpaid, CurrentRepaymentAmount, customerGroupId, RepaymentDateTime, RepaymentAmount, Penalty, ProccessingFees, StationId FROM " + REPAYMENT_TABLE +
                " WHERE (" + KEY_NIC_NUMBER + " = '" + nic + "') AND (" + KEY_DATA_SYNCUP + " = 0)", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Repayment repayment = getRepaymentFromCursor(cursor);
                    repayments.add(repayment);
                } while (cursor.moveToNext());
                cursor.close();

                if (repayments.get(0).getCustomerGroupId() != null && !repayments.get(0).getCustomerGroupId().equalsIgnoreCase("")) {
                    for (Repayment repayment : getRepaymentDataByGroupId(dbR, Integer.parseInt(repayments.get(0).getCustomerGroupId()))) {
                        if (!memberExistsInRepayments(repayments, repayment.getLoanId(), repayment.getNICNumber())) {
                            repayments.add(repayment);
                        }
                    }
                }
            }
        } finally {

            dbR.close();
        }
        return repayments;
    }

    //TODO getFPWithLoanId
    public Map<String, String> getFPWithLoanId(int previousLoanId) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        Map<String, String> loanIdWithFPMap = new HashMap<>();
        Cursor cursor = dbR.rawQuery("select loanId, fPImageTemp from Repayment where (loanId > " + previousLoanId + ") AND (fPImageTemp <> '') ORDER BY loanId LIMIT 1 ", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                loanIdWithFPMap.put("loanId", cursor.getString(cursor.getColumnIndex("loanId")));
                loanIdWithFPMap.put("fPImageTemp", cursor.getString(cursor.getColumnIndex("fPImageTemp")));
                cursor.close();
            }

        } finally {
            dbR.close();
        }

        return loanIdWithFPMap;
    }

    //TODO clearDB
    public void clearDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + REPAYMENT_TABLE);
        db.close();
    }
}
