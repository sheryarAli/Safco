package databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import customclasses.Asset;
import customclasses.CommonConst;
import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.Document;
import customclasses.Employees;
import customclasses.FamilyMember;
import customclasses.Group;
import customclasses.GroupMember;
import customclasses.Guaranteer;
import customclasses.Loan;
import customclasses.MicrofinanceCustomers;
import customclasses.OrganizationEmployees;
import customclasses.OrganizationRegion;
import customclasses.OrganizationStaions;
import customclasses.Questionnaire;
import customclasses.QuestionnaireAnswer;
import customclasses.Region;

import customclasses.Stations;

/**
 * Created by shery on 11/16/2016.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final String TAG = DBHandler.class.getSimpleName();
    public static final String DATABASE_NAME = "ssf_mms_db";
    public static final int DB_VERSION = 17;

    public static final String KEY_NOTES = "notes";

    private SQLiteDatabase wDB, rDB;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);

    }

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(OrganizationEmployees.createOrganiztionEmployeesTable);
        sqLiteDatabase.execSQL(OrganizationRegion.createOrganiztionRegionTable);
        sqLiteDatabase.execSQL(OrganizationRegion.createOrganiztionRegionTypes);
        sqLiteDatabase.execSQL(OrganizationStaions.createOrganizationStations);
        sqLiteDatabase.execSQL(OrganizationStaions.createOrganizationStationsTypes);
        sqLiteDatabase.execSQL(MicrofinanceCustomers.createMicrofinanceCustomers);
        sqLiteDatabase.execSQL(OrganizationEmployees.createOrganiztionEmployeesRegionsTable);
        sqLiteDatabase.execSQL(OrganizationEmployees.createOrganiztionEmployeesStationsTable);
        sqLiteDatabase.execSQL(OrganizationEmployees.createOrganiztionEmployeesTypesTable);
        sqLiteDatabase.execSQL(MicrofinanceCustomers.createMicrofinanceCustomersAsset);
        sqLiteDatabase.execSQL(MicrofinanceCustomers.createMicrofinanceCustomersGuaranteers);
        sqLiteDatabase.execSQL(MicrofinanceCustomers.createMicrofinanceCustomersFMember);
        sqLiteDatabase.execSQL(Loan.createLoanTable);
        sqLiteDatabase.execSQL(Group.createGroupTable);
        sqLiteDatabase.execSQL(Document.createDocumentTable);
        sqLiteDatabase.execSQL(GroupMember.createGroupMemberTable);
        sqLiteDatabase.execSQL(Questionnaire.createQuestionnaireTable);
        sqLiteDatabase.execSQL(QuestionnaireAnswer.createQuestionnaireAnswerTable);



        /*

        if (!checkIfColumnExits(sqLiteDatabase, MicrofinanceCustomers.TABLE_NAME3, MicrofinanceCustomers.KEY_NOTES)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + MicrofinanceCustomers.TABLE_NAME3 + " ADD COLUMN " + MicrofinanceCustomers.KEY_NOTES + " TEXT");
        }
        if (!checkIfColumnExits(sqLiteDatabase, Loan.LOAN_TABLE, MicrofinanceCustomers.KEY_NOTES)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + Loan.LOAN_TABLE + " ADD COLUMN " + MicrofinanceCustomers.KEY_NOTES + " TEXT");
        }*/


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.e(TAG, "onUpgrade: ");

        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, MicrofinanceCustomers.TABLE_NAME, MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + MicrofinanceCustomers.TABLE_NAME + " ADD COLUMN " + MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC + " text");
        }
        if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, Document.DOCUMENT_TABLE, Document.KEY_UNI_CODE)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + Document.DOCUMENT_TABLE + " ADD COLUMN " + Document.KEY_UNI_CODE + " text");
        }
        /*if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, MicrofinanceCustomers.TABLE_NAME, MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + MicrofinanceCustomers.TABLE_NAME + " ADD COLUMN " + MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC + " text");
        }*/
        /*if (!checkIfColumnExits(sqLiteDatabase, MicrofinanceCustomers.TABLE_NAME, MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + MicrofinanceCustomers.TABLE_NAME + " ADD COLUMN " + MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG + " BLOB");
        }*/

        /*if (!CommonMethod.checkIfColumnExits(sqLiteDatabase, Document.DOCUMENT_TABLE, Document.KEY_UNI_CODE)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + Document.DOCUMENT_TABLE + " ADD COLUMN " + Document.KEY_UNI_CODE + " text");
        }*/

        /*if (!checkIfColumnExits(sqLiteDatabase, MicrofinanceCustomers.TABLE_NAME, MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + MicrofinanceCustomers.TABLE_NAME + " ADD COLUMN " + MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP + " text DEFAULT NULL");
        }*/

        /*sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrganizationEmployees.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrganizationEmployees.TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrganizationEmployees.TABLE_NAME3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrganizationEmployees.TABLE_NAME4);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrganizationRegion.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrganizationRegion.TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrganizationStaions.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrganizationStaions.TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MicrofinanceCustomers.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MicrofinanceCustomers.TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MicrofinanceCustomers.TABLE_NAME3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MicrofinanceCustomers.TABLE_NAME4);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Loan.LOAN_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Group.GROUP_TABLE);*/


        /*Log.e(TAG, "onUpgrade Table Altered");
        if (!checkIfColumnExits(sqLiteDatabase, MicrofinanceCustomers.TABLE_NAME, MicrofinanceCustomers.KEY_DATA_SYNCUP)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + MicrofinanceCustomers.TABLE_NAME + " ADD COLUMN " + MicrofinanceCustomers.KEY_DATA_SYNCUP + " smallint(6) DEFAULT '0'");
        }*/

//        onCreate(sqLiteDatabase);

    }

    /*public boolean checkIfColumnExits(SQLiteDatabase db, String tableName, String columnName) {
        Cursor res = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
        int val = res.getColumnIndex(columnName);
        res.close();
        return val != -1;
    }*/

    /*public int getRecentId(String tableName, String keyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + keyId + " FROM " + tableName + " WHERE CustomerId = (SELECT MAX(" + keyId + ")  FROM " + tableName + ");", null);
        int id = 0;
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                id = cursor.getInt(cursor.getColumnIndex(Loan.KEY_LOAN_ID));
            }
        } finally {
            cursor.close();
            db.close();
        }


        return id;
    }*/

    public void deleteQuegetstionnaireAnswerData(int loanId, int customerId) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + QuestionnaireAnswer.QUESTIONNARE_ANSWER_TABLE + " WHERE " + MicrofinanceCustomers.KEY_LOAN_ID + " = " + loanId +
                " AND " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId);
        db.close();

    }

    public QuestionnaireAnswer getQuestionnaireAnswerData(int loanId, int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionnaireAnswer.QUESTIONNARE_ANSWER_TABLE
                + " where " + MicrofinanceCustomers.KEY_LOAN_ID + " = " + loanId + " and "
                + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId, null);
        QuestionnaireAnswer questionnaireAnswer = null;
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                questionnaireAnswer = new QuestionnaireAnswer();
                questionnaireAnswer.setQuestionnaireFormId(cursor.getInt(cursor.getColumnIndex(QuestionnaireAnswer.KEY_QUESTIONNARE_FORM_ID)));
                questionnaireAnswer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                questionnaireAnswer.setLoan_Id(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_LOAN_ID)));
                questionnaireAnswer.setAnswer1(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER1)));
                questionnaireAnswer.setAnswer2(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER2)));
                questionnaireAnswer.setAnswer3(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER3)));
                questionnaireAnswer.setAnswer4(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER4)));
                questionnaireAnswer.setAnswer5(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER5)));
                questionnaireAnswer.setAnswer6(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER6)));
                questionnaireAnswer.setAnswer7(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER7)));
                questionnaireAnswer.setAnswer8(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER8)));
                questionnaireAnswer.setAnswer9(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER9)));
                questionnaireAnswer.setAnswer10(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER10)));
                questionnaireAnswer.setAnswer11(cursor.getString(cursor.getColumnIndex(QuestionnaireAnswer.KEY_ANSWER11)));
                questionnaireAnswer.setAnswer12(cursor.getString(cursor.getColumnIndex(QuestionnaireAnswer.KEY_ANSWER12)));
                questionnaireAnswer.setAnswer13(cursor.getString(cursor.getColumnIndex(QuestionnaireAnswer.KEY_ANSWER13)));


            }
        } finally {
            cursor.close();
            db.close();
        }
        return questionnaireAnswer;
    }


    public int updateQuestionnaireAnswer(QuestionnaireAnswer questionnaireAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, questionnaireAnswer.getCustomerId());
        values.put(MicrofinanceCustomers.KEY_LOAN_ID, questionnaireAnswer.getLoan_Id());
        values.put(Questionnaire.KEY_ANSWER1, questionnaireAnswer.getAnswer1());
        values.put(Questionnaire.KEY_ANSWER2, questionnaireAnswer.getAnswer2());
        values.put(Questionnaire.KEY_ANSWER3, questionnaireAnswer.getAnswer3());
        values.put(Questionnaire.KEY_ANSWER4, questionnaireAnswer.getAnswer4());
        values.put(Questionnaire.KEY_ANSWER5, questionnaireAnswer.getAnswer5());
        values.put(Questionnaire.KEY_ANSWER6, questionnaireAnswer.getAnswer6());
        values.put(Questionnaire.KEY_ANSWER7, questionnaireAnswer.getAnswer7());
        values.put(Questionnaire.KEY_ANSWER8, questionnaireAnswer.getAnswer8());
        values.put(Questionnaire.KEY_ANSWER9, questionnaireAnswer.getAnswer9());
        values.put(Questionnaire.KEY_ANSWER10, questionnaireAnswer.getAnswer10());
        values.put(QuestionnaireAnswer.KEY_ANSWER11, questionnaireAnswer.getAnswer11());
        values.put(QuestionnaireAnswer.KEY_ANSWER12, questionnaireAnswer.getAnswer12());
        values.put(QuestionnaireAnswer.KEY_ANSWER13, questionnaireAnswer.getAnswer13());

        int res = db.update(QuestionnaireAnswer.QUESTIONNARE_ANSWER_TABLE, values,
                QuestionnaireAnswer.KEY_QUESTIONNARE_FORM_ID + " = ? ",
                new String[]{questionnaireAnswer.getQuestionnaireFormId() + ""});
        db.close();
        return res;
    }

    public long insertQuestionnareAnswer(QuestionnaireAnswer questionnaireAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, questionnaireAnswer.getCustomerId());
        values.put(MicrofinanceCustomers.KEY_LOAN_ID, questionnaireAnswer.getLoan_Id());
        values.put(Questionnaire.KEY_ANSWER1, questionnaireAnswer.getAnswer1());
        values.put(Questionnaire.KEY_ANSWER2, questionnaireAnswer.getAnswer2());
        values.put(Questionnaire.KEY_ANSWER3, questionnaireAnswer.getAnswer3());
        values.put(Questionnaire.KEY_ANSWER4, questionnaireAnswer.getAnswer4());
        values.put(Questionnaire.KEY_ANSWER5, questionnaireAnswer.getAnswer5());
        values.put(Questionnaire.KEY_ANSWER6, questionnaireAnswer.getAnswer6());
        values.put(Questionnaire.KEY_ANSWER7, questionnaireAnswer.getAnswer7());
        values.put(Questionnaire.KEY_ANSWER8, questionnaireAnswer.getAnswer8());
        values.put(Questionnaire.KEY_ANSWER9, questionnaireAnswer.getAnswer9());
        values.put(Questionnaire.KEY_ANSWER10, questionnaireAnswer.getAnswer10());
        values.put(QuestionnaireAnswer.KEY_ANSWER11, questionnaireAnswer.getAnswer11());
        values.put(QuestionnaireAnswer.KEY_ANSWER12, questionnaireAnswer.getAnswer12());
        values.put(QuestionnaireAnswer.KEY_ANSWER13, questionnaireAnswer.getAnswer13());
        values.put(MicrofinanceCustomers.KEY_NIC_NUMBER, questionnaireAnswer.getNICNumber());
        long res = db.insert(QuestionnaireAnswer.QUESTIONNARE_ANSWER_TABLE, null, values);
        db.close();
        return res;
    }

    public boolean questionnaireExists(int quesId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + Questionnaire.QUESTIONNAIRE_TABLE + " where " + Questionnaire.KEY_QUESTIONNAIRE_ID + " = " + quesId + "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0)
            return true;
        return false;

    }


    public void insertQuestionnaire(ArrayList<Questionnaire> questionnaires) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Questionnaire questionnaire : questionnaires) {
            if (!questionnaireExists(questionnaire.getQuestionnaireId())) {
                values.put(Questionnaire.KEY_QUESTIONNAIRE_ID, questionnaire.getQuestionnaireId());
                values.put(Questionnaire.KEY_QUESTION, questionnaire.getQuestion());
                values.put(Questionnaire.KEY_ANSWER_TYPE, questionnaire.getAnswerType());
                values.put(Questionnaire.KEY_ANSWER1, questionnaire.getAnswer1());
                values.put(Questionnaire.KEY_ANSWER2, questionnaire.getAnswer2());
                values.put(Questionnaire.KEY_ANSWER3, questionnaire.getAnswer3());
                values.put(Questionnaire.KEY_ANSWER4, questionnaire.getAnswer4());
                values.put(Questionnaire.KEY_ANSWER5, questionnaire.getAnswer5());
                values.put(Questionnaire.KEY_ANSWER6, questionnaire.getAnswer6());
                values.put(Questionnaire.KEY_ANSWER7, questionnaire.getAnswer7());
                values.put(Questionnaire.KEY_ANSWER8, questionnaire.getAnswer8());
                values.put(Questionnaire.KEY_ANSWER9, questionnaire.getAnswer9());
                values.put(Questionnaire.KEY_ANSWER10, questionnaire.getAnswer10());
                long res = db.insert(Questionnaire.QUESTIONNAIRE_TABLE, null, values);
            }
        }
        db.close();
    }


    public ArrayList<Questionnaire> getQuestionnaireData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Questionnaire> questDataList = new ArrayList<Questionnaire>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Questionnaire.QUESTIONNAIRE_TABLE, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Questionnaire questionnaire = new Questionnaire();
                    questionnaire.setQuestionnaireId(cursor.getInt(cursor.getColumnIndex(Questionnaire.KEY_QUESTIONNAIRE_ID)));
                    questionnaire.setQuestion(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_QUESTION)));
                    questionnaire.setAnswerType(cursor.getInt(cursor.getColumnIndex(Questionnaire.KEY_ANSWER_TYPE)));
                    questionnaire.setAnswer1(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER1)));
                    questionnaire.setAnswer2(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER2)));
                    questionnaire.setAnswer3(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER3)));
                    questionnaire.setAnswer4(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER4)));
                    questionnaire.setAnswer5(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER5)));
                    questionnaire.setAnswer6(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER6)));
                    questionnaire.setAnswer7(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER7)));
                    questionnaire.setAnswer8(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER8)));
                    questionnaire.setAnswer9(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER9)));
                    questionnaire.setAnswer10(cursor.getString(cursor.getColumnIndex(Questionnaire.KEY_ANSWER10)));
                    questionnaire.setStatus(cursor.getInt(cursor.getColumnIndex(Questionnaire.KEY_STATUS)));
                    questDataList.add(questionnaire);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }
        return questDataList;
    }

    public String getCustomerBuisnessAddress(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + Loan.KEY_BUSI_ADD + " FROM " + Loan.LOAN_TABLE + " WHERE "
                + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId, null);
        String bAdd = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                bAdd = cursor.getString(cursor.getColumnIndex(Loan.KEY_BUSI_ADD));
            }
        } finally {
            cursor.close();
            db.close();
        }
        return bAdd;
    }

    public void deleteLoanData(int customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Loan.LOAN_TABLE + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId + "");
        db.close();
    }

    public int getLoanId(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + Loan.KEY_LOAN_ID + " FROM " + Loan.LOAN_TABLE + " WHERE "
                + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId, null);
        int loanId = 0;
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                loanId = cursor.getInt(cursor.getColumnIndex(Loan.KEY_LOAN_ID));
            }
        } finally {
            cursor.close();
            db.close();
        }

        return loanId;
    }

    public ArrayList<Loan> getLoanData(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Loan> loanDataList = new ArrayList<Loan>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Loan.LOAN_TABLE + " WHERE "
                + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId, null);


        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Loan loan = new Loan();
                    loan.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    loan.setLoanId(cursor.getInt(cursor.getColumnIndex(Loan.KEY_LOAN_ID)));
                    loan.setLoanTypeId(cursor.getString(cursor.getColumnIndex(Loan.KEY_LOAN_TYPE)));
                    loan.setLoanStatus(cursor.getString(cursor.getColumnIndex(Loan.KEY_LOAN_STATUS)));
                    loan.setRequestedLoanAmount(cursor.getString(cursor.getColumnIndex(Loan.KEY_REQ_LOAN)));
                    loan.setLoanTerm(cursor.getString(cursor.getColumnIndex(Loan.KEY_LOAN_TERM)));
                    loan.setApprovedLoanAmount(cursor.getString(cursor.getColumnIndex(Loan.KEY_APP_LOAN)));
                    loan.setAmountRequiredForBusiness(cursor.getString(cursor.getColumnIndex(Loan.KEY_AMO_REQ_BUSI)));
                    loan.setPersonalCapitalInBusiness(cursor.getString(cursor.getColumnIndex(Loan.KEY_PER_CAP_BUSI)));
                    loan.setExpectedMonthlyIncomeFromBusiness(cursor.getString(cursor.getColumnIndex(Loan.KEY_EXP_MON_INC_BUSI)));
                    loan.setBusinessAddress(cursor.getString(cursor.getColumnIndex(Loan.KEY_BUSI_ADD)));
                    loan.setMonthlyIncome(cursor.getString(cursor.getColumnIndex(Loan.KEY_MON_INC)));
                    loan.setMonthlyExpense(cursor.getString(cursor.getColumnIndex(Loan.KEY_MON_EXP)));
                    loan.setHousehold_MonthlyExpense(cursor.getString(cursor.getColumnIndex(Loan.KEY_MON_HH_EXP)));
                    loan.setHousehold_MonthlyIncome(cursor.getString(cursor.getColumnIndex(Loan.KEY_MON_HH_INC)));
                    loan.setSavings(cursor.getString(cursor.getColumnIndex(Loan.KEY_SAVING)));
                    loan.setHousehold_Savings(cursor.getString(cursor.getColumnIndex(Loan.KEY_HH_SAVING)));
                    loan.setExistingBusiness(cursor.getString(cursor.getColumnIndex(Loan.KEY_EXI_BUSI)));
                    loan.setExperienceInBusiness(cursor.getString(cursor.getColumnIndex(Loan.KEY_BUSI_EXP)));
                    loan.setAddedDateTime(cursor.getString(cursor.getColumnIndex(CommonConst.KEY_CUR_ADD_DATE)));

                    String notes = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NOTES));
                    loan.setNotes((TextUtils.isEmpty(notes) ? "" : notes));
                    loanDataList.add(loan);
                } while (cursor.moveToNext());

            }
        } finally {
            cursor.close();
            db.close();
        }

        return loanDataList;
    }

    /*public boolean groupMemberExist(int id, String nic) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " +
                GroupMember.GROUP_MEMBER_TABLE + " WHERE "
                + MicrofinanceCustomers.KEY_NIC_NUMBER + " = '"
                + nic + "' AND " + GroupMember.KEY_GROUP_MEMBER_ID + " = " + id + "", null);


        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }


        return false;
    }*/

    public boolean groupMemberExist(String nic, int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " +
                GroupMember.GROUP_MEMBER_TABLE + " WHERE "
                + MicrofinanceCustomers.KEY_NIC_NUMBER + " = '"
                + nic + "' AND " + Group.KEY_GROUP_ID + " = " + groupId + "", null);


        if (cursor != null && cursor.getCount() > 0) {

            return true;
        }

        return false;
    }

    public ArrayList<GroupMember> getGroupMember(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<GroupMember> grpMemberDataList = new ArrayList<GroupMember>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GroupMember.GROUP_MEMBER_TABLE + " WHERE " + Group.KEY_GROUP_ID + " = " + groupId, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    GroupMember groupMember = new GroupMember();

                    groupMember.setCustomerGroupId(cursor.getInt(cursor.getColumnIndex(Group.KEY_GROUP_ID)));
                    groupMember.setCustomerGroupMemberId(cursor.getInt(cursor.getColumnIndex(GroupMember.KEY_GROUP_MEMBER_ID)));
                    groupMember.setNicNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_NUMBER)));
                    groupMember.setFullname(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FULL_NAME)));
                    groupMember.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    Log.e(TAG, (cursor.getInt(cursor.getColumnIndex(GroupMember.KEY_GROUP_LEADER)) == 1) + "");
                    groupMember.setGroupLeader((cursor.getInt(cursor.getColumnIndex(GroupMember.KEY_GROUP_LEADER)) == 1));
                    grpMemberDataList.add(groupMember);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        return grpMemberDataList;
    }

    //    TODO getRecentGroupData pagination
    public ArrayList<Group> getRecentGroupData(int lastId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Group> grpDataList = new ArrayList<Group>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Group.GROUP_TABLE + " WHERE (Group_Id < " + lastId +
                " ) ORDER BY " + Group.KEY_GROUP_ID + " DESC LIMIT 10", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Group group = new Group();

                    group.setGroupId(cursor.getInt(cursor.getColumnIndex(Group.KEY_GROUP_ID)));

                    group.setGroup_Name(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NAME)));
                    group.setGroup_Status(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_STATUS)));
                    group.setGroup_Note(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NOTE)));
                    group.setGroup_Added_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY)));
                    group.setGroup_Added_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_COM)));
                    group.setGroup_Added_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_DES)));
                    group.setGroup_Verified_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY)));
                    group.setGroup_Verified_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_COM)));
                    group.setGroup_Verified_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_DES)));
                    group.setSyncUpStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DATA_SYNCUP)));
                    grpDataList.add(group);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }


        Log.e(TAG, "Group Size: " + grpDataList.size());
        return grpDataList;

    }

    //    TODO getGroupDataByDate Pagination
    public ArrayList<Group> getGroupDataByDate(String fDate, String tDate, int lastId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Group> grpDataList = new ArrayList<Group>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Group.GROUP_TABLE + " WHERE (Group_Id < " + lastId +
                ") AND (" + CommonConst.KEY_CUR_ADD_DATE + " " +
                "BETWEEN '" + fDate + "' AND '" + tDate + "' " +
                ") ORDER BY Group_Id DESC LIMIT 10", null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Group group = new Group();

                    group.setGroupId(cursor.getInt(cursor.getColumnIndex(Group.KEY_GROUP_ID)));

                    group.setGroup_Name(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NAME)));
                    group.setGroup_Status(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_STATUS)));
                    group.setGroup_Note(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NOTE)));
                    group.setGroup_Added_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY)));
                    group.setGroup_Added_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_COM)));
                    group.setGroup_Added_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_DES)));
                    group.setGroup_Verified_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY)));
                    group.setGroup_Verified_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_COM)));
                    group.setGroup_Verified_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_DES)));
                    group.setSyncUpStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DATA_SYNCUP)));
                    grpDataList.add(group);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }


        Log.e(TAG, "Group Size: " + grpDataList.size());
        return grpDataList;
    }

    public ArrayList<Group> getGroupData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Group> grpDataList = new ArrayList<Group>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Group.GROUP_TABLE, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Group group = new Group();

                    group.setGroupId(cursor.getInt(cursor.getColumnIndex(Group.KEY_GROUP_ID)));

                    group.setGroup_Name(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NAME)));
                    group.setGroup_Status(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_STATUS)));
                    group.setGroup_Note(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NOTE)));
                    group.setGroup_Added_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY)));
                    group.setGroup_Added_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_COM)));
                    group.setGroup_Added_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_DES)));
                    group.setGroup_Verified_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY)));
                    group.setGroup_Verified_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_COM)));
                    group.setGroup_Verified_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_DES)));
                    group.setSyncUpStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DATA_SYNCUP)));
                    grpDataList.add(group);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }


        Log.e(TAG, "Group Size: " + grpDataList.size());
        return grpDataList;
    }

    //    TODO getSyncGroupDataByDate Pagination
    public ArrayList<Group> getSyncGroupDataByDate(String fDate, String tDate, int lastId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Group> grpDataList = new ArrayList<Group>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Group.GROUP_TABLE + " WHERE (Group_Id < " + lastId +
                ") AND (" + CommonConst.KEY_CUR_ADD_DATE + " " +
                "BETWEEN '" + fDate + "' AND '" + tDate + "' " +
                ") AND (" + MicrofinanceCustomers.KEY_DATA_SYNCUP + " = 0 ) ORDER BY " + Group.KEY_GROUP_ID + " DESC LIMIT 10", null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Group group = new Group();

                    group.setGroupId(cursor.getInt(cursor.getColumnIndex(Group.KEY_GROUP_ID)));

                    group.setGroup_Name(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NAME)));
                    group.setGroup_Status(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_STATUS)));
                    group.setGroup_Note(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NOTE)));
                    group.setGroup_Added_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY)));
                    group.setGroup_Added_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_COM)));
                    group.setGroup_Added_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_DES)));
                    group.setGroup_Verified_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY)));
                    group.setGroup_Verified_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_COM)));
                    group.setGroup_Verified_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_DES)));
                    grpDataList.add(group);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }


        Log.e(TAG, "Group Size: " + grpDataList.size());
        return grpDataList;
    }

    //    TODO getRecentSyncGroupData pagination
    public ArrayList<Group> getRecentSyncGroupData(int lastId) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Group> grpDataList = new ArrayList<Group>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Group.GROUP_TABLE + " WHERE (Group_Id < " + lastId +
                ") AND (" + MicrofinanceCustomers.KEY_DATA_SYNCUP + " = 0 ) ORDER BY "
                + Group.KEY_GROUP_ID + " DESC LIMIT 10", null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Group group = new Group();

                    group.setGroupId(cursor.getInt(cursor.getColumnIndex(Group.KEY_GROUP_ID)));

                    group.setGroup_Name(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NAME)));
                    group.setGroup_Status(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_STATUS)));
                    group.setGroup_Note(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_NOTE)));
                    group.setGroup_Added_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY)));
                    group.setGroup_Added_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_COM)));
                    group.setGroup_Added_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_ADD_BY_DES)));
                    group.setGroup_Verified_By(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY)));
                    group.setGroup_Verified_By_Comment(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_COM)));
                    group.setGroup_Verified_By_Designation(cursor.getString(cursor.getColumnIndex(Group.KEY_GROUP_VER_BY_DES)));
                    grpDataList.add(group);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }


        Log.e(TAG, "Group Size: " + grpDataList.size());
        return grpDataList;
    }

    public void insertGroupMember(ArrayList<GroupMember> groupMembers, int groupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (GroupMember groupMember : groupMembers) {
            Log.e(TAG, "Name: " + groupMember.getFullname() + " Leader: " + groupMember.isGroupLeader());
            if (groupMember.getCustomerGroupMemberId() == 0) {
                values.put(Group.KEY_GROUP_ID, groupId);
                values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, groupMember.getCustomerId());
                values.put(MicrofinanceCustomers.KEY_FULL_NAME, groupMember.getFullname());
                values.put(MicrofinanceCustomers.KEY_NIC_NUMBER, groupMember.getNicNumber());
                values.put(GroupMember.KEY_GROUP_LEADER, (groupMember.isGroupLeader() ? 1 : 0));
                long res = db.insert(GroupMember.GROUP_MEMBER_TABLE, null, values);
            } else if (groupMemberExist(groupMember.getNicNumber(), groupId)) {
                Log.e(TAG, "groupMemberExist Name: " + groupMember.getFullname() + " Leader: " + groupMember.isGroupLeader());
                updateGroupMember(groupMember);
            }

        }

        db.close();
    }

    public int updateGroupMember(GroupMember groupMember) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, groupMember.getCustomerId());
        values.put(MicrofinanceCustomers.KEY_FULL_NAME, groupMember.getFullname());
        values.put(MicrofinanceCustomers.KEY_NIC_NUMBER, groupMember.getNicNumber());
        values.put(GroupMember.KEY_GROUP_LEADER, (groupMember.isGroupLeader() ? 1 : 0));
        int res = db.update(GroupMember.GROUP_MEMBER_TABLE, values,
                GroupMember.KEY_GROUP_MEMBER_ID + " = ? ",
                new String[]{groupMember.getCustomerGroupMemberId() + ""});
        values.clear();

        return res;
    }

    public int updateCustomerSync(int customerId, int syncUpStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MicrofinanceCustomers.KEY_DATA_SYNCUP, syncUpStatus);
        int res = db.update(MicrofinanceCustomers.TABLE_NAME, values,
                MicrofinanceCustomers.KEY_CUSTOMER_ID + " = ? ",
                new String[]{customerId + ""});
        db.close();
        return res;
    }

    public int updateGroupSync(int groupId, int syncUpStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MicrofinanceCustomers.KEY_DATA_SYNCUP, syncUpStatus);
        int res = db.update(Group.GROUP_TABLE, values,
                Group.KEY_GROUP_ID + " = ? ",
                new String[]{groupId + ""});
        db.close();
        return res;
    }

    public int updateGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Group.KEY_GROUP_NAME, group.getGroup_Name());
        values.put(Group.KEY_GROUP_STATUS, group.getGroup_Status());
        values.put(Group.KEY_GROUP_NOTE, group.getGroup_Note());

        values.put(Group.KEY_GROUP_ADD_BY, group.getGroup_Added_By());
        values.put(Group.KEY_GROUP_ADD_BY_COM, group.getGroup_Added_By_Comment());
        values.put(Group.KEY_GROUP_ADD_BY_DES, group.getGroup_Added_By_Designation());
        values.put(Group.KEY_GROUP_VER_BY, group.getGroup_Verified_By());
        values.put(Group.KEY_GROUP_VER_BY_COM, group.getGroup_Verified_By_Comment());
        values.put(Group.KEY_GROUP_VER_BY_DES, group.getGroup_Verified_By_Designation());
        values.put(MicrofinanceCustomers.KEY_DATA_SYNCUP, group.getSyncUpStatus());

        int res = db.update(Group.GROUP_TABLE, values,
                Group.KEY_GROUP_ID + " = ? ",
                new String[]{group.getGroupId() + ""});
        db.close();
        return res;
    }


    public void deleteGroupMember(int customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + GroupMember.GROUP_MEMBER_TABLE + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId + "");
        db.close();
    }

    public void deleteGroupMembers(ArrayList<GroupMember> groupMembers) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (GroupMember groupMember : groupMembers) {
            if (groupMemberExist(groupMember.getNicNumber(), groupMember.getCustomerGroupId())) {
                db.execSQL("DELETE FROM " + GroupMember.GROUP_MEMBER_TABLE + " WHERE " + GroupMember.KEY_GROUP_MEMBER_ID + " = " + groupMember.getCustomerGroupMemberId() + "");
                db.execSQL("DELETE FROM " + MicrofinanceCustomers.TABLE_NAME3 +
                        " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + groupMember.getCustomerId() + "");
            }
        }
        db.close();

    }

    public long insertGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Group.KEY_GROUP_NAME, group.getGroup_Name());
        values.put(Group.KEY_GROUP_STATUS, group.getGroup_Status());
        values.put(Group.KEY_GROUP_NOTE, group.getGroup_Note());
        values.put(Group.KEY_GROUP_ADD_BY, group.getGroup_Added_By());
        values.put(Group.KEY_GROUP_ADD_BY_COM, group.getGroup_Added_By_Comment());
        values.put(Group.KEY_GROUP_ADD_BY_DES, group.getGroup_Added_By_Designation());
        values.put(Group.KEY_GROUP_VER_BY, group.getGroup_Verified_By());
        values.put(Group.KEY_GROUP_VER_BY_COM, group.getGroup_Verified_By_Comment());
        values.put(Group.KEY_GROUP_VER_BY_DES, group.getGroup_Verified_By_Designation());
        values.put(CommonConst.KEY_CUR_ADD_DATE, new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime()));


        long res = db.insert(Group.GROUP_TABLE, null, values);
        db.close();
        return res;
    }

    public int updateLoanData(Loan loan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e(TAG, "Loan Id: " + loan.getLoanId());
        values.put(Loan.KEY_LOAN_TYPE, loan.getLoanTypeId());
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, loan.getCustomerId());
        values.put(Loan.KEY_LOAN_STATUS, loan.getLoanStatus());
        values.put(Loan.KEY_LOAN_TERM, loan.getLoanTerm());
        values.put(Loan.KEY_AMO_REQ_BUSI, loan.getAmountRequiredForBusiness());
        values.put(Loan.KEY_APP_LOAN, loan.getApprovedLoanAmount());
        values.put(Loan.KEY_BUSI_ADD, loan.getBusinessAddress());
        values.put(Loan.KEY_EXP_MON_INC_BUSI, loan.getExpectedMonthlyIncomeFromBusiness());
        values.put(Loan.KEY_EXI_BUSI, loan.getExistingBusiness());
        values.put(Loan.KEY_HH_SAVING, loan.getHousehold_Savings());
        values.put(Loan.KEY_SAVING, loan.getSavings());
        values.put(Loan.KEY_PER_CAP_BUSI, loan.getPersonalCapitalInBusiness());
        values.put(Loan.KEY_REQ_LOAN, loan.getRequestedLoanAmount());
        values.put(Loan.KEY_MON_INC, loan.getMonthlyIncome());
        values.put(Loan.KEY_MON_EXP, loan.getMonthlyExpense());
        values.put(Loan.KEY_MON_HH_INC, loan.getHousehold_MonthlyIncome());
        values.put(Loan.KEY_MON_HH_EXP, loan.getHousehold_MonthlyExpense());
        values.put(Loan.KEY_BUSI_EXP, loan.getExperienceInBusiness());
        values.put(MicrofinanceCustomers.KEY_NOTES, loan.getNotes());
        values.put(CommonConst.KEY_CUR_ADD_DATE, loan.getAddedDateTime());

        int res = db.update(Loan.LOAN_TABLE, values,
                Loan.KEY_LOAN_ID + " = ? ",
                new String[]{loan.getLoanId() + ""});
        /*Log.e(TAG, "Loan Query: "+loan.getLoanId());*/

        db.close();
        return res;
    }

    public void deleteDocument(ArrayList<Integer> docId) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int id : docId) {
            if (id != 0 || containsDocument(id)) {
                db.execSQL("DELETE FROM " + Document.DOCUMENT_TABLE + " WHERE " + Document.KEY_DOCUMENT_ID + " = " + id);
            }
        }
        db.close();


    }

    public long insertLoan(Loan loan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Loan.KEY_LOAN_TYPE, loan.getLoanTypeId());
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, loan.getCustomerId());
        values.put(Loan.KEY_LOAN_STATUS, loan.getLoanStatus());
        values.put(Loan.KEY_LOAN_TERM, loan.getLoanTerm());
        values.put(Loan.KEY_AMO_REQ_BUSI, loan.getAmountRequiredForBusiness());
        values.put(Loan.KEY_APP_LOAN, loan.getApprovedLoanAmount());
        values.put(Loan.KEY_BUSI_ADD, loan.getBusinessAddress());
        values.put(Loan.KEY_EXP_MON_INC_BUSI, loan.getExpectedMonthlyIncomeFromBusiness());
        values.put(Loan.KEY_EXI_BUSI, loan.getExistingBusiness());
        values.put(Loan.KEY_HH_SAVING, loan.getHousehold_Savings());
        values.put(Loan.KEY_SAVING, loan.getSavings());
        values.put(Loan.KEY_PER_CAP_BUSI, loan.getPersonalCapitalInBusiness());
        values.put(Loan.KEY_REQ_LOAN, loan.getRequestedLoanAmount());
        values.put(Loan.KEY_MON_INC, loan.getMonthlyIncome());
        values.put(Loan.KEY_MON_EXP, loan.getMonthlyExpense());
        values.put(Loan.KEY_MON_HH_INC, loan.getHousehold_MonthlyIncome());
        values.put(Loan.KEY_MON_HH_EXP, loan.getHousehold_MonthlyExpense());
        values.put(Loan.KEY_BUSI_EXP, loan.getExperienceInBusiness());
        values.put(MicrofinanceCustomers.KEY_NOTES, loan.getNotes());
        values.put(MicrofinanceCustomers.KEY_NIC_NUMBER, loan.getNICNumber());
        values.put(CommonConst.KEY_CUR_ADD_DATE, loan.getAddedDateTime());

        long res = db.insert(Loan.LOAN_TABLE, null, values);
        db.close();
        return res;
    }

    public long addEmpRegion(int stationId, int empId, int regId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(OrganizationEmployees.KEY_EMP_ID, empId);
        values.put(OrganizationEmployees.KEY_STATION_ID, stationId);
        values.put(OrganizationEmployees.KEY_REGION_NAME, regId);

        long res = db.insert(OrganizationEmployees.TABLE_NAME3, null, values);
        db.close();
        return res;
    }


    public ArrayList<String> getCustomerAddedDateList(String nic) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select " + MicrofinanceCustomers.KEY_CUS_ADD_DATETIME + " from " + MicrofinanceCustomers.TABLE_NAME + " WHERE " + MicrofinanceCustomers.KEY_NIC_NUMBER + " = '" + nic + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> dates = new ArrayList<>();
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String date = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUS_ADD_DATETIME));
                    dates.add(date);
                } while (cursor.moveToNext());

            }
        } finally {
            cursor.close();
            db.close();
        }


        return dates;
    }

    public Customer getCustomerForSyncUp(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Customer customer = null;
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String data = "";
                customer = new Customer();
                customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                customer.setSyncStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DATA_SYNCUP)));
                customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                customer.setCustomerId(customerId);
                customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                customer.setNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_NUMBER)));
                customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));
                customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));

                customer.setCustomerGPSLocation(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC)));

                customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));
                customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                customer.setOldNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_OLD_NIC_NUMBER)));
                byte[] imgArr = cursor.getBlob(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG));

                if (imgArr == null) {
                    customer.setFPImageStr("");
                } else {
                    customer.setFPImageStr(CommonMethod.getEncoded64ImageStringFromBitmap(CommonMethod.byteArrayToBitmap(imgArr)));
                }

                customer.setFPImageTemp(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP)));

                customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)));

                data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER));
                customer.setFamilyNumber((TextUtils.isEmpty(data) ? "" : data));

                data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NOTES));
                customer.setNotes((TextUtils.isEmpty(data) ? "" : data));

                data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER));
                customer.setGuardianNICNumber((TextUtils.isEmpty(data) ? "" : data));

            }
        } finally {
            cursor.close();
            db.close();
        }

        return customer;
    }

    public Customer getCustomer(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Customer customer = null;
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String data = "";
                customer = new Customer();
                customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                customer.setSyncStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DATA_SYNCUP)));
                customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                customer.setCustomerId(customerId);
                customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                customer.setNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_NUMBER)));
                customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));
                customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));
                customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                customer.setOldNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_OLD_NIC_NUMBER)));

                cursor.getBlob(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG));
                customer.setFPImage(cursor.getBlob(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG)));
                customer.setFPImageTemp(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP)));

                customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)));

                data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER));
                customer.setFamilyNumber((TextUtils.isEmpty(data) ? "" : data));

                data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NOTES));
                customer.setNotes((TextUtils.isEmpty(data) ? "" : data));

                data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER));
                customer.setGuardianNICNumber((TextUtils.isEmpty(data) ? "" : data));

            }
        } finally {
            cursor.close();
            db.close();
        }

        return customer;
    }

    public ArrayList<Employees> getEmployeesData() {
        ArrayList<Employees> empDataList = new ArrayList<Employees>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + OrganizationEmployees.TABLE_NAME + "";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Employees employees = new Employees();

                    employees.setEmployeeId(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_EMP_ID)));
                    employees.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    empDataList.add(employees);
                } while (cursor.moveToNext());

            }
        } finally {
            cursor.close();
            db.close();
        }


        return empDataList;
    }

    public boolean containsDocument(int id, String name, int loanId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Document.DOCUMENT_TABLE + " WHERE " + Document.KEY_DOCUMENT_ID + " = " + id + " and" +
                " " + Document.KEY_LOAN_ID + " = " + loanId;
        Log.e(TAG, "name Query: " + query);
        Cursor cursor = db.rawQuery(query, null);
        try {

            if (cursor != null && cursor.getCount() > 0) {
                db.execSQL("UPDATE " + Document.DOCUMENT_TABLE
                        + " SET " + Document.KEY_IMAGE_NAME
                        + " = '" + name + "' WHERE " + Document.KEY_DOCUMENT_ID + " = " + id + " ");
                return true;
            }

        } finally {
            cursor.close();

        }

        return false;


    }

    public boolean containsDocument(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Document.DOCUMENT_TABLE + " WHERE " + Document.KEY_DOCUMENT_ID + " = " + id + "";
        Cursor cursor = db.rawQuery(query, null);
        try {

            if (cursor != null && cursor.getCount() > 0)
                return true;
        } finally {
            cursor.close();
            db.close();
        }
        return false;
    }

    public void deleteDocumentData(int loanId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Document.DOCUMENT_TABLE + " WHERE " + Loan.KEY_LOAN_ID + " = " + loanId + "");
        db.close();
    }

    public ArrayList<Document> getDocumentData(Context context, String nic, int loanId) {
        ArrayList<Document> docDataList = new ArrayList<Document>();
        String uniqueCode1 = "";
        SQLiteDatabase dbR = this.getReadableDatabase();
        SQLiteDatabase dbW = this.getReadableDatabase();
        String selectQuery = "select * from " + Document.DOCUMENT_TABLE + " WHERE " + Loan.KEY_LOAN_ID + " = " + loanId;
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEIStr = telephonyManager.getDeviceId();
        IMEIStr = IMEIStr.substring(IMEIStr.length() - 4, IMEIStr.length());
        uniqueCode1 = IMEIStr +
                "_" + System.currentTimeMillis();
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Document document = new Document();
                    String uniqueCode = cursor.getString(cursor.getColumnIndex(Document.KEY_UNI_CODE));

                    if (uniqueCode == null || uniqueCode.equalsIgnoreCase("")) {
                        updateUniqueCodeForDocs(dbW, document.getCustomerId(), uniqueCode1);
                        uniqueCode = uniqueCode1;
                    }
                    document.setUniqueCode(uniqueCode);

//                    document.setDocumentId(cursor.getInt(cursor.getColumnIndex(Document.KEY_DOCUMENT_ID)));
                    document.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    document.setImageName(cursor.getString(cursor.getColumnIndex(Document.KEY_IMAGE_NAME)));
                    document.setNicNumber(nic);
                    document.setImage(cursor.getString(cursor.getColumnIndex(Document.KEY_IMAGE)));
                    document.setDocumentAddedDataTime(cursor.getString(cursor.getColumnIndex(Document.KEY_DOC_ADDED_DATETIME)));
                    docDataList.add(document);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            dbR.close();
            dbW.close();
        }
        return docDataList;
    }

    public ArrayList<Document> getDocumentData(Context context, int loanId) {
        ArrayList<Document> docDataList = new ArrayList<Document>();
        SQLiteDatabase dbR = this.getReadableDatabase();
        SQLiteDatabase dbW = this.getWritableDatabase();
        String uniqueCode1 = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEIStr = telephonyManager.getDeviceId();
        IMEIStr = IMEIStr.substring(IMEIStr.length() - 4, IMEIStr.length());
        uniqueCode1 = IMEIStr +
                "_" + System.currentTimeMillis();
        String selectQuery = "select * from " + Document.DOCUMENT_TABLE + " WHERE " + Loan.KEY_LOAN_ID + " = " + loanId;
        Cursor cursor = dbR.rawQuery(selectQuery, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Document document = new Document();
                    String uniqueCode = cursor.getString(cursor.getColumnIndex(Document.KEY_UNI_CODE));
                    if (uniqueCode == null || uniqueCode.equalsIgnoreCase("")) {
                        updateUniqueCodeForDocs(dbW, document.getCustomerId(), uniqueCode1);
                        uniqueCode = uniqueCode1;
                    }
                    document.setUniqueCode(uniqueCode);
                    document.setDocumentId(cursor.getInt(cursor.getColumnIndex(Document.KEY_DOCUMENT_ID)));
                    document.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    document.setImageName(cursor.getString(cursor.getColumnIndex(Document.KEY_IMAGE_NAME)));
                    document.setImage(cursor.getString(cursor.getColumnIndex(Document.KEY_IMAGE)));
                    document.setDocumentAddedDataTime(cursor.getString(cursor.getColumnIndex(Document.KEY_DOC_ADDED_DATETIME)));
                    docDataList.add(document);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            dbR.close();
            dbW.close();
        }
        return docDataList;
    }


    public long insertDocumentData(Context context, Document document) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e(TAG, "Document Name: " + document.getImageName());
        long res = 0;
        if (!containsDocument(document.getDocumentId(), document.getImageName(), document.getLoanId())) {
            values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, document.getCustomerId());
            values.put(Document.KEY_LOAN_ID, document.getLoanId());
            values.put(Document.KEY_IMAGE, document.getImage());
            values.put(Document.KEY_IMAGE_NAME, document.getImageName());
            values.put(Document.KEY_UNI_CODE, getUniqueCodeOfDocument(db, context, document.getCustomerId()));
            values.put(Document.KEY_DOC_ADDED_DATETIME, document.getDocumentAddedDataTime());
            res = db.insert(Document.DOCUMENT_TABLE, null, values);

        }
        db.close();
        return res;
    }

    public String getUniqueCodeOfDocument(SQLiteDatabase dbW, Context context, int customerId) {
        SQLiteDatabase dbR = this.getReadableDatabase();
        String query = "select " + Document.KEY_UNI_CODE + " from " + Document.DOCUMENT_TABLE + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId;
        Cursor cursor = dbR.rawQuery(query, null);
        String uniqueCode1 = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEIStr = telephonyManager.getDeviceId();
        IMEIStr = IMEIStr.substring(IMEIStr.length() - 4, IMEIStr.length());
        uniqueCode1 = IMEIStr +
                "_" + System.currentTimeMillis();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            String uniqueCode2 = cursor.getString(cursor.getColumnIndex(Document.KEY_UNI_CODE));

            if (uniqueCode2 == null || uniqueCode2.equalsIgnoreCase("")) {
                int res = updateUniqueCodeForDocs(dbW, customerId, uniqueCode1);
                Log.e(TAG, "getUniqueCodeOfDocument: number of rows updated " + res);
                return uniqueCode1;
            }
            cursor.close();
            return uniqueCode2;

        }
        /*TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEIStr = telephonyManager .getDeviceId();
        IMEIStr = IMEIStr.substring(IMEIStr.length() - 4, IMEIStr.length());
        uniqueCode = IMEIStr +
                "_" + System.currentTimeMillis();*/
        return uniqueCode1;
    }

    public int updateUniqueCodeForDocs(SQLiteDatabase dbW, int customerId, String uniqueCode) {
        ContentValues values = new ContentValues();
        values.put(Document.KEY_UNI_CODE, uniqueCode);
        int res = dbW.update(Document.DOCUMENT_TABLE, values,
                MicrofinanceCustomers.KEY_CUSTOMER_ID + " = ? ",
                new String[]{customerId + ""});

        return res;
    }

    public boolean stationExists(int stationId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + OrganizationStaions.TABLE_NAME + " where " + OrganizationStaions.KEY_STATION_ID + " = " + stationId + "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0)
            return true;
        return false;
    }

    public void clearStationTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + OrganizationStaions.TABLE_NAME);
        db.close();
    }

    public void insertStationData(ArrayList<Stations> stationList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Stations station : stationList) {
            if (!stationExists(station.getStationId())) {
                values.put(OrganizationStaions.KEY_STATION_ID, station.getStationId());
                values.put(OrganizationStaions.KEY_STATION_NAME, station.getStationName());
                long res = db.insert(OrganizationStaions.TABLE_NAME, null, values);
            }
        }

        db.close();
    }

    public int updateAssetData(Asset asset) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, asset.getCustomerId());
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ASSET_NAME_ID, asset.get_CustomerAssetNameId());
        values.put(MicrofinanceCustomers.KEY_ASSET_VALUE, asset.getAssetValue());
        values.put(MicrofinanceCustomers.KEY_ASSET_NAME, asset.getAssetName());
        values.put(MicrofinanceCustomers.KEY_ASSET_QUANTITY, asset.getAssetQuantity());
        values.put(MicrofinanceCustomers.KEY_ASSET_OWNER, asset.getAssetOwner());
        values.put(MicrofinanceCustomers.KEY_NOTES, asset.getNotes());
        int res = db.update(MicrofinanceCustomers.TABLE_NAME2, values,
                MicrofinanceCustomers.KEY_CUSTOMER_ASSET_ID + " = ? ",
                new String[]{asset.getCustomerAssetId() + ""});

        db.close();
        return res;
    }

    public long insertAssetData(Asset asset) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, asset.getCustomerId());
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ASSET_NAME_ID, asset.get_CustomerAssetNameId());
        values.put(MicrofinanceCustomers.KEY_ASSET_VALUE, asset.getAssetValue());
        values.put(MicrofinanceCustomers.KEY_ASSET_NAME, asset.getAssetName());
        values.put(MicrofinanceCustomers.KEY_ASSET_QUANTITY, asset.getAssetQuantity());
        values.put(MicrofinanceCustomers.KEY_ASSET_OWNER, asset.getAssetOwner());
        values.put(MicrofinanceCustomers.KEY_NOTES, asset.getNotes());

        values.put(MicrofinanceCustomers.KEY_ASSET_ADD_DATETIME, new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime()));
        long res = db.insert(MicrofinanceCustomers.TABLE_NAME2, null, values);
        db.close();
        return res;

    }


    public int updateFamilyMember(FamilyMember fMember) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, fMember.getCustomerId());
        values.put(MicrofinanceCustomers.KEY_FULL_NAME, fMember.getFullName());
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_AGE, fMember.getAge());
        values.put(MicrofinanceCustomers.KEY_GENDER, fMember.getGender());
        values.put(MicrofinanceCustomers.KEY_MONTHLY_EARNING, fMember.getMonthlyEarning());
        values.put(MicrofinanceCustomers.KEY_NIC_NUMBER, fMember.getNICNumber());
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_RELATION, fMember.getRelationshipWithCustomer());
        values.put(MicrofinanceCustomers.KEY_INCOME_SOURCE, fMember.getSourceOfIncome());
        values.put(MicrofinanceCustomers.KEY_EDUCATION, fMember.getEducation());
        values.put(MicrofinanceCustomers.KEY_BUSINESS_ADDRESS, fMember.getBusinessAddress());
        int res = db.update(MicrofinanceCustomers.TABLE_NAME4, values,
                MicrofinanceCustomers.KEY_CUSTOMER_FMEMBER_ID + " = ? ",
                new String[]{fMember.getCustomerFamilyMemberId() + ""});
        db.close();
        return res;
    }

    public long insertFamilyMemberData(FamilyMember fMember) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, fMember.getCustomerId());
        values.put(MicrofinanceCustomers.KEY_FULL_NAME, fMember.getFullName());
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_AGE, fMember.getAge());
        values.put(MicrofinanceCustomers.KEY_GENDER, fMember.getGender());
        values.put(MicrofinanceCustomers.KEY_MONTHLY_EARNING, fMember.getMonthlyEarning());
        values.put(MicrofinanceCustomers.KEY_NIC_NUMBER, fMember.getNICNumber());
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_RELATION, fMember.getRelationshipWithCustomer());
        values.put(MicrofinanceCustomers.KEY_INCOME_SOURCE, fMember.getSourceOfIncome());
        values.put(MicrofinanceCustomers.KEY_EDUCATION, fMember.getEducation());
        values.put(MicrofinanceCustomers.KEY_BUSINESS_ADDRESS, fMember.getBusinessAddress());
        values.put(MicrofinanceCustomers.KEY_FMEMBER_ADD_DATETIME, new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime()));


        long res = db.insert(MicrofinanceCustomers.TABLE_NAME4, null, values);
        db.close();
        return res;

    }

    public int updateGuaranteerData(Guaranteer guaranteer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, guaranteer.getCustomerId());
        values.put(MicrofinanceCustomers.KEY_FULL_NAME, guaranteer.getFullName());
        values.put(MicrofinanceCustomers.KEY_NIC_NUMBER, guaranteer.getNICNumber());
        values.put(OrganizationEmployees.KEY_ADDRESS, guaranteer.getAddress());
        values.put(MicrofinanceCustomers.KEY_BUSINESS_ADDRESS, guaranteer.getBusinessAddress());
        values.put(MicrofinanceCustomers.KEY_CONTACT_NUMBER, guaranteer.getContactNumber());
        values.put(MicrofinanceCustomers.KEY_JOB_DESC, guaranteer.getJobDescription());
        values.put(MicrofinanceCustomers.KEY_JOB_TYPE, guaranteer.getJobType());
        values.put(MicrofinanceCustomers.KEY_LOAN_ID, guaranteer.getLoanId());
        values.put(OrganizationEmployees.KEY_STATUS, guaranteer.getStatus());
        values.put(MicrofinanceCustomers.KEY_NOTES, guaranteer.getNotes());
        int res = db.update(MicrofinanceCustomers.TABLE_NAME3, values,
                MicrofinanceCustomers.KEY_CUSTOMER_GUARANTEER_ID + " = ? ",
                new String[]{guaranteer.getCustomerGuaranteerId() + ""});
        db.close();
        return res;
    }

    public boolean guaranteerExists(int customerId, String NIC) {
        /**/
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from microfinance_customers_guaranteers where CustomerId = " + customerId + " and NICNumber = '" + NIC + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0)
            return true;
        return false;
    }

    public long insertGuaranteerData(Guaranteer guaranteer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (!guaranteerExists(guaranteer.getCustomerId(), guaranteer.getNICNumber())) {
            values.put(MicrofinanceCustomers.KEY_CUSTOMER_ID, guaranteer.getCustomerId());
            values.put(MicrofinanceCustomers.KEY_FULL_NAME, guaranteer.getFullName());
            values.put(MicrofinanceCustomers.KEY_NIC_NUMBER, guaranteer.getNICNumber());
            values.put(OrganizationEmployees.KEY_ADDRESS, guaranteer.getAddress());
            values.put(MicrofinanceCustomers.KEY_BUSINESS_ADDRESS, guaranteer.getBusinessAddress());
            values.put(MicrofinanceCustomers.KEY_CONTACT_NUMBER, guaranteer.getContactNumber());
            values.put(MicrofinanceCustomers.KEY_JOB_DESC, guaranteer.getJobDescription());
            values.put(MicrofinanceCustomers.KEY_JOB_TYPE, guaranteer.getJobType());
            values.put(MicrofinanceCustomers.KEY_LOAN_ID, guaranteer.getLoanId());
            values.put(OrganizationEmployees.KEY_STATUS, guaranteer.getStatus());
            values.put(MicrofinanceCustomers.KEY_NOTES, guaranteer.getNotes());
            values.put(MicrofinanceCustomers.KEY_GUARANTEER_ADD_DATETIME, new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime()));
            long res = db.insert(MicrofinanceCustomers.TABLE_NAME3, null, values);
            db.close();
            return res;
        }
        return -1;
    }

    public void deleteAssetDataById(int customerAssetId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MicrofinanceCustomers.TABLE_NAME2 + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ASSET_ID + " = " + customerAssetId + "");
        db.close();

    }

    public void deleteAssetData(int customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MicrofinanceCustomers.TABLE_NAME2 + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId + "");
        db.close();

    }

    public ArrayList<Asset> getAssetData(int customerId) {
        ArrayList<Asset> assetDataList = new ArrayList<Asset>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME2 +
                " where " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String data = "";
                    Asset asset = new Asset();

                    asset.setCustomerAssetId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ASSET_ID)));
                    asset.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ASSET_NAME));
                    asset.setAssetName((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ASSET_QUANTITY));
                    asset.setAssetQuantity((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ASSET_VALUE));
                    asset.setAssetValue((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ASSET_OWNER));
                    asset.setAssetOwner((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NOTES));
                    asset.setNotes((TextUtils.isEmpty(data) ? "" : data));

//                    asset.setNotes(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NOTES)));
                    assetDataList.add(asset);
                } while (cursor.moveToNext());

            }
        } finally {
            cursor.close();
            db.close();
        }


        return assetDataList;

    }

    public void deleteGuaranteerData(int customerGuaranteerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MicrofinanceCustomers.TABLE_NAME3 + " WHERE "
                + MicrofinanceCustomers.KEY_CUSTOMER_GUARANTEER_ID + " = " + customerGuaranteerId);
        db.close();
    }

    public void deleteGuaranteerData(int customerId, String customerNic) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MicrofinanceCustomers.TABLE_NAME3 + " WHERE "
                + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId + " OR "
                + MicrofinanceCustomers.KEY_NIC_NUMBER + " = '" + customerNic + "'");
        db.close();
    }

    public ArrayList<Guaranteer> getGuaranteerData(int customerId) {
        ArrayList<Guaranteer> guaranteerDataList = new ArrayList<Guaranteer>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME3 +
                " where " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {

                    String data = "";

                    Guaranteer guaranteer = new Guaranteer();
                    guaranteer.setCustomerGuaranteerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_GUARANTEER_ID)));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FULL_NAME));

                    guaranteer.setFullName((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_NUMBER));

                    guaranteer.setNICNumber((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_ADDRESS));

                    guaranteer.setAddress((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_JOB_TYPE));
                    guaranteer.setJobType((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_JOB_DESC));
                    guaranteer.setJobDescription((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CONTACT_NUMBER));
                    guaranteer.setContactNumber((TextUtils.isEmpty(data) ? "" : data));
                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_BUSINESS_ADDRESS));

                    guaranteer.setBusinessAddress((TextUtils.isEmpty(data) ? "" : data));

                    guaranteer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NOTES));
                    guaranteer.setNotes((TextUtils.isEmpty(data) ? "" : data));

                    guaranteer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    guaranteerDataList.add(guaranteer);
                } while (cursor.moveToNext());

            }
        } finally {
            cursor.close();
            db.close();
        }


        return guaranteerDataList;

    }

    public void deleteFamilyMemberDataById(int customerFamilyMemberId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MicrofinanceCustomers.TABLE_NAME4 + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_FMEMBER_ID + " = " + customerFamilyMemberId + "");
        db.close();

    }

    public void deleteFamilyMemberData(int customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MicrofinanceCustomers.TABLE_NAME4 + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId + "");
        db.close();

    }

    public ArrayList<FamilyMember> getFamilyMemberData(int customerId) {
        ArrayList<FamilyMember> fMemberDataList = new ArrayList<FamilyMember>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME4 +
                " where " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String data = "";
                    FamilyMember fmember = new FamilyMember();

                    fmember.setCustomerFamilyMemberId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FMEMBER_ID)));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FULL_NAME));
                    fmember.setFullName((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER));
                    fmember.setNICNumber((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_RELATION));
                    fmember.setRelationshipWithCustomer((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION));
                    fmember.setEducation((TextUtils.isEmpty(data) ? "" : data));

                    fmember.setAge(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_AGE)));
                    fmember.setMonthlyEarning(cursor.getDouble(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MONTHLY_EARNING)));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_INCOME_SOURCE));
                    fmember.setSourceOfIncome((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_BUSINESS_ADDRESS));
                    fmember.setBusinessAddress((TextUtils.isEmpty(data) ? "" : data));

                    data = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER));
                    fmember.setGender((TextUtils.isEmpty(data) ? "" : data));

                    fMemberDataList.add(fmember);
                } while (cursor.moveToNext());

            }
        } finally {
            cursor.close();
            db.close();
        }


        return fMemberDataList;

    }

    public ArrayList<Customer> getCustomerHavingLoan(String Nic) {
        ArrayList<Customer> custDataList = new ArrayList<Customer>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME + " WHERE ("
                + MicrofinanceCustomers.KEY_NIC_NUMBER + " = '" + Nic.trim()
                + "') AND ( " + MicrofinanceCustomers.KEY_DATA_SYNCUP + " = 0)";

        //    TODO getCustomerHavingLoan Inside
        Log.e(TAG, "getCustomerHavingLoan query: " + selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Customer customer = new Customer();
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    if (groupMemberExist(customer.getCustomerId())) {

                        continue;
                    }

                    customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));

                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));

                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));

                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setNotes(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NOTES)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));
                    custDataList.add(customer);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }
        ArrayList<Customer> returnList = new ArrayList<>();
        for (Customer customer : custDataList) {
            if (loanExist(customer.getCustomerId())) {
                Log.e(TAG, customer.getFirstName() + " Added");
                returnList.add(customer);

            }
        }

        return returnList;
    }

    public ArrayList<Customer> getCustomer(String Nic) {
        ArrayList<Customer> custDataList = new ArrayList<Customer>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME + " WHERE "
                + MicrofinanceCustomers.KEY_NIC_NUMBER + " = '" + Nic.trim() + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Customer customer = new Customer();
                    customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    Log.e(TAG, "isDeceased" + cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));

                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));

                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));
                    custDataList.add(customer);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }
        return custDataList;
    }

    //    TODO getCustomerNotGMemberByDate Pagination
    public ArrayList<Customer> getCustomerNotGMemberByDate(String fDate, String tDate, int lastId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Customer> custDataList = new ArrayList<Customer>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MicrofinanceCustomers.TABLE_NAME + " " +
                "WHERE (CustomerId < " + lastId +
                ") AND (" + MicrofinanceCustomers.KEY_DATA_SYNCUP + " = 0 " +
                ") AND (" + MicrofinanceCustomers.KEY_CUS_ADD_DATETIME + " " +
                "BETWEEN '" + fDate + "' AND '" + tDate + "' " +
                ")" +
                " ORDER BY " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " DESC LIMIT 10", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                Log.e(TAG, "getCustomerNotGMember CustomerList Size: " + cursor.getCount());
                cursor.moveToFirst();
                do {
                    Customer customer = new Customer();
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    if (groupMemberExist(customer.getCustomerId()))
                        continue;
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));

                    customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));

                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setSyncStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DATA_SYNCUP)));

                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));
                    customer.setCustomerGPSLocation(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC)));
                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));

                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));
                    byte[] imgArr = cursor.getBlob(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG));

                    if (imgArr == null) {
                        customer.setFPImageStr("");
                    } else {
                        customer.setFPImageStr(CommonMethod.getEncoded64ImageStringFromBitmap(CommonMethod.byteArrayToBitmap(imgArr)));
                    }

                    customer.setFPImageTemp(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP)));
                    custDataList.add(customer);
                } while (cursor.moveToNext());

            }
            Log.e(TAG, custDataList.size() + "");
        } finally {
            cursor.close();
            db.close();
        }
        return custDataList;

    }


    //    TODO getCustomerNotGMember Pagination
    public ArrayList<Customer> getCustomerNotGMember(int lastId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Customer> custDataList = new ArrayList<Customer>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MicrofinanceCustomers.TABLE_NAME + " " +
                "WHERE (CustomerId < " + lastId + ") AND (" + MicrofinanceCustomers.KEY_DATA_SYNCUP + " = 0)" +
                " ORDER BY " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " DESC LIMIT 10", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                Log.e(TAG, "getCustomerNotGMember CustomerList Size: " + cursor.getCount());
                cursor.moveToFirst();
                do {
                    Customer customer = new Customer();
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    if (groupMemberExist(customer.getCustomerId()))
                        continue;
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));

                    customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));

                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setSyncStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DATA_SYNCUP)));

                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));

                    customer.setCustomerGPSLocation(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC)));

                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));

                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));

                    byte[] imgArr = cursor.getBlob(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG));

                    if (imgArr == null) {
                        customer.setFPImageStr("");
                    } else {
                        customer.setFPImageStr(CommonMethod.getEncoded64ImageStringFromBitmap(CommonMethod.byteArrayToBitmap(imgArr)));
                    }

                    customer.setFPImageTemp(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP)));

                    custDataList.add(customer);
                } while (cursor.moveToNext());

            }
            Log.e(TAG, custDataList.size() + "");
        } finally {
            cursor.close();
            db.close();
        }
        return custDataList;

    }

    public ArrayList<Customer> getRecentCustomerDataHavingLoan() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Customer> custDataList = new ArrayList<Customer>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MicrofinanceCustomers.TABLE_NAME + " WHERE " + MicrofinanceCustomers.KEY_DATA_SYNCUP + " = 0 ORDER BY " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " DESC LIMIT 10", null);
        //    TODO getRecentCustomerDataHavingLoan Inside
        try {
            if (cursor != null && cursor.getCount() > 0) {
                Log.e(TAG, "getRecentCustomerData Size: " + cursor.getCount());
                cursor.moveToFirst();
                do {


                    Customer customer = new Customer();
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    if (groupMemberExist(customer.getCustomerId()))
                        continue;
                    customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    Log.e(TAG, "getCustomerData isDeceased" + cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    Log.e(TAG, "getCustomerData isDisabled" + cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));

                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));

                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));
                    custDataList.add(customer);
                } while (cursor.moveToNext());

            }
            Log.e(TAG, custDataList.size() + "");
        } finally {
            cursor.close();
            db.close();
        }
        ArrayList<Customer> returnList = new ArrayList<>();
        for (Customer customer : custDataList) {
            if (loanExist(customer.getCustomerId())) {
                Log.e(TAG, customer.getFirstName() + " Added");
                returnList.add(customer);

            }
        }

        return returnList;

    }

    public boolean groupMemberExist(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + GroupMember.GROUP_MEMBER_TABLE + " where " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId + "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor != null && cursor.getCount() > 0;
    }

    private boolean loanExist(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + Loan.LOAN_TABLE + " where " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId + "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor != null && cursor.getCount() > 0;

    }

    //    TODO getRecentCustomerData
    public ArrayList<Customer> getRecentCustomerData(int lastId) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Customer> custDataList = new ArrayList<Customer>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + MicrofinanceCustomers.TABLE_NAME + " Where (CustomerId < " + lastId + ") ORDER BY " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " DESC LIMIT 10", null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                Log.e(TAG, "getRecentCustomerData Size: " + cursor.getCount());
                cursor.moveToFirst();
                do {
                    Customer customer = new Customer();
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    String loc = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC));
                    customer.setCustomerGPSLocation(TextUtils.isEmpty(loc) ? "" : loc);

                    /*customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));
                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));
                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));*/
                    custDataList.add(customer);
                } while (cursor.moveToNext());

            }
            Log.e(TAG, custDataList.size() + "");
        } finally {
            cursor.close();
            db.close();
        }


        return custDataList;


    }


    public ArrayList<Customer> getCustomerDataByNameNic(String name, String nic, int lastValue) {
        ArrayList<Customer> custDataList = new ArrayList<Customer>();
        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery = "select (FirstName ||\" \"|| LastName) as FullName,* from " + MicrofinanceCustomers.TABLE_NAME +
                " WHERE (" + MicrofinanceCustomers.KEY_NIC_NUMBER + " = '" + nic + "' " +
                "OR FullName LIKE '%" + name + "%')" +
                " AND (" + MicrofinanceCustomers.KEY_CUSTOMER_ID + " < " + lastValue +
                ") ORDER BY " + MicrofinanceCustomers.KEY_CUSTOMER_ID +
                " LIMIT 10";
        Log.e(TAG, "getCustomerDataByNameNic: query: " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {

                    Customer customer = new Customer();
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    String loc = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC));
                    customer.setCustomerGPSLocation(TextUtils.isEmpty(loc) ? "" : loc);

                    /*customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));
                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));
                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));*/
                    custDataList.add(customer);
                } while (cursor.moveToNext());

            }
            Log.e(TAG, custDataList.size() + "");
        } finally {
            cursor.close();
            db.close();
        }


        return custDataList;
    }

    //    TODO getCustomerDataByDate Pagination
    public ArrayList<Customer> getCustomerDataByDate(String date1, String date2, int lastId) {
        ArrayList<Customer> custDataList = new ArrayList<Customer>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME +
                " WHERE (" + MicrofinanceCustomers.KEY_CUSTOMER_ID + " < " + lastId +
                ") AND (" + MicrofinanceCustomers.KEY_CUS_ADD_DATETIME + " " +
                "BETWEEN '" + date1 + "' AND '" + date2 + "' " +
                ") ORDER by " + MicrofinanceCustomers.KEY_CUSTOMER_ID + "" +
                " LIMIT 10";
        Log.e(TAG, "getCustomerDataByDate: query: " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Customer customer = new Customer();
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    String loc = cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC));
                    customer.setCustomerGPSLocation(TextUtils.isEmpty(loc) ? "" : loc);
                    /*customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));
                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));
                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    Log.e(TAG, "getCustomerData isDeceased" + cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    Log.e(TAG, "getCustomerData isDisabled" + cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));
                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));
                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));*/
                    custDataList.add(customer);
                } while (cursor.moveToNext());

            }
            Log.e(TAG, custDataList.size() + "");
        } finally {
            cursor.close();
            db.close();
        }


        return custDataList;
    }

    public void deleteCustomer(int customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MicrofinanceCustomers.TABLE_NAME + " WHERE " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " = " + customerId + "");
        db.close();
    }

    public ArrayList<Customer> getCustomerData() {
        ArrayList<Customer> custDataList = new ArrayList<Customer>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + MicrofinanceCustomers.TABLE_NAME + " DESC LIMIT 10";
        Cursor cursor = db.rawQuery(selectQuery, null);


        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Customer customer = new Customer();
                    customer.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    customer.setStationName(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME)));

                    customer.setMobileNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_MOBILENO)));

                    customer.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_ID)));
                    customer.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationEmployees.KEY_STATUS)));
                    customer.setFirstName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_FIRSTNAME)));
                    customer.setLastName(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_LASTNAME)));
                    customer.setNICNumber(cursor.getString(cursor.getColumnIndex(OrganizationEmployees.KEY_NICNUMBER)) + "");
                    customer.setCustomerType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_CUSTOMER_TYPE)));
                    customer.setDateOfBirth(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_DOB)));
                    customer.setEducation(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_EDUCATION)));
                    customer.setFamilyNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_FAMILY_NUMBER)));
                    customer.setGender(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GENDER)));
                    customer.setGuardianType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_TYPE)));
                    customer.setGuardian(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN)));
                    customer.setHouseStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_STATUS)));
                    customer.setHouseType(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_HOUSE_TYPE)));
                    customer.setIsDeceased(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    Log.e(TAG, "getCustomerData isDeceased" + cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DECEASED)));
                    customer.setIsDisabled(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    Log.e(TAG, "getCustomerData isDisabled" + cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_IS_DISABLED)));
                    customer.setMaritalStatus(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_MARITAL_STATUS)));
                    customer.setNICExpiryDate(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE)));
                    customer.setNICType(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_NIC_TYPE)));
                    customer.setTokenNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_TOKEN_NUMBER)) + "");
                    customer.setReligion(cursor.getInt(cursor.getColumnIndex(MicrofinanceCustomers.KEY_RELIGION)));

                    customer.setAddress_Permanent_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY)));
                    customer.setAddress_Permanent_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS)));
                    customer.setAddress_Permanent_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE)));
                    customer.setAddress_Permanent_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY)));
                    customer.setAddress_Permanent_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME)));
                    customer.setAddress_Permanent_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Permanent_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME)));
                    customer.setAddress_Permanent_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME)));
                    customer.setAddress_Present_NumberOfYears(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS)));

                    customer.setAddress_Present_Country(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY)));
                    customer.setAddress_Present_Address(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS)));
                    customer.setAddress_Present_State(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE)));
                    customer.setAddress_Present_City(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY)));
                    customer.setAddress_Present_District_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME)));
                    customer.setAddress_Present_Mohalla_Village(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE)));
                    customer.setAddress_Present_Taluka_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME)));
                    customer.setAddress_Present_UC_Name(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME)));
                    customer.setGuardianNICNumber(cursor.getString(cursor.getColumnIndex(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER)));
                    custDataList.add(customer);
                } while (cursor.moveToNext());

            }
            Log.e(TAG, custDataList.size() + "");
        } finally {
            cursor.close();
            db.close();
        }


        return custDataList;
    }

    public ArrayList<Region> getRegionData() {
        ArrayList<Region> regionDataList = new ArrayList<Region>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from " + OrganizationRegion.TABLE_NAME + "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Region region = new Region();
                    region.setRegionId(cursor.getInt(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_ID)));
                    region.setRegionTypeId(cursor.getInt(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_TYPE_ID)));
                    region.setRegionParentId(cursor.getInt(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_PARENT_ID)));
                    region.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationRegion.KEY_STATUS)));
                    region.setRegionName(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_NAME)));
                    region.setRegionCode(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_CODE)));
                    region.setRegionDescription(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_DESC)));
                    region.setRegionDescription(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_DESC)));
                    region.setRegionDetails(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_DETAILS)));
                    region.setRegionDetailsComments(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_DETAILS_COMMENTS)));
                    region.setNotes(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_NOTES)));
                    region.setRegionAddedDateTime(cursor.getString(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_ADD_DATE_ADD_TIME)));
                    region.setRegionDetailsStatus(cursor.getInt(cursor.getColumnIndex(OrganizationRegion.KEY_REGION_DETAILS_STATUS)));
                    regionDataList.add(region);
                } while (cursor.moveToNext());
            }
            Collections.reverse(regionDataList);
        } finally {
            cursor.close();
            db.close();
        }
        /*try {
            // read data from the cursor in here
        } finally {
            cursor.close();
        }*/


        return regionDataList;
    }

    public int updateCustomerData(Bundle customerData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MicrofinanceCustomers.KEY_EDUCATION, customerData.getInt(MicrofinanceCustomers.KEY_EDUCATION));
        values.put(OrganizationStaions.KEY_STATION_ID, customerData.getInt(OrganizationStaions.KEY_STATION_ID));
        values.put(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME, customerData.getString(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME));
        values.put(OrganizationEmployees.KEY_REGION_NAME, customerData.getString(OrganizationEmployees.KEY_REGION_NAME));
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG, customerData.getByteArray(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG));
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP, customerData.getString(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP));
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_TYPE, customerData.getInt(MicrofinanceCustomers.KEY_CUSTOMER_TYPE));
        values.put(OrganizationEmployees.KEY_FIRSTNAME, customerData.getString(OrganizationEmployees.KEY_FIRSTNAME));
        values.put(OrganizationEmployees.KEY_LASTNAME, customerData.getString(OrganizationEmployees.KEY_LASTNAME));
        values.put(MicrofinanceCustomers.KEY_GUARDIAN_TYPE, customerData.getInt(MicrofinanceCustomers.KEY_GUARDIAN_TYPE));
        values.put(MicrofinanceCustomers.KEY_GUARDIAN, customerData.getString(MicrofinanceCustomers.KEY_GUARDIAN));
        values.put(MicrofinanceCustomers.KEY_GENDER, "" + customerData.getChar(MicrofinanceCustomers.KEY_GENDER));
        values.put(MicrofinanceCustomers.KEY_DOB, "" + customerData.getString(MicrofinanceCustomers.KEY_DOB));
        values.put(OrganizationEmployees.KEY_NICNUMBER, "" + customerData.getString(OrganizationEmployees.KEY_NICNUMBER));
        values.put(MicrofinanceCustomers.KEY_TOKEN_NUMBER, customerData.getString(MicrofinanceCustomers.KEY_TOKEN_NUMBER) + "");
        values.put(MicrofinanceCustomers.KEY_NIC_TYPE, "" + customerData.getString(MicrofinanceCustomers.KEY_NIC_TYPE));
        values.put(MicrofinanceCustomers.KEY_FAMILY_NUMBER, "" + customerData.getString(MicrofinanceCustomers.KEY_FAMILY_NUMBER));
        values.put(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE, "" + customerData.getString(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE));
        values.put(MicrofinanceCustomers.KEY_MARITAL_STATUS, customerData.getInt(MicrofinanceCustomers.KEY_MARITAL_STATUS));
        values.put(OrganizationEmployees.KEY_STATUS, customerData.getInt(OrganizationEmployees.KEY_STATUS));
        values.put(MicrofinanceCustomers.KEY_RELIGION, customerData.getInt(MicrofinanceCustomers.KEY_RELIGION));
        values.put(MicrofinanceCustomers.KEY_EDUCATION, customerData.getInt(MicrofinanceCustomers.KEY_EDUCATION));
        values.put(MicrofinanceCustomers.KEY_HOUSE_STATUS, customerData.getInt(MicrofinanceCustomers.KEY_HOUSE_STATUS));
        values.put(MicrofinanceCustomers.KEY_HOUSE_TYPE, customerData.getInt(MicrofinanceCustomers.KEY_HOUSE_TYPE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS));
        values.put(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS, customerData.getInt(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS));
        values.put(OrganizationEmployees.KEY_MOBILENO, customerData.getString(OrganizationEmployees.KEY_MOBILENO));
        values.put(MicrofinanceCustomers.KEY_NOTES, customerData.getString(MicrofinanceCustomers.KEY_NOTES));
        values.put(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER, customerData.getString(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER));

        Log.e(TAG, "isDeceased" + customerData.getInt(MicrofinanceCustomers.KEY_IS_DECEASED));
        values.put(MicrofinanceCustomers.KEY_IS_DECEASED, customerData.getInt(MicrofinanceCustomers.KEY_IS_DECEASED));
        values.put(MicrofinanceCustomers.KEY_IS_DISABLED, customerData.getInt(MicrofinanceCustomers.KEY_IS_DISABLED));
        values.put(MicrofinanceCustomers.KEY_DATA_SYNCUP, customerData.getInt(MicrofinanceCustomers.KEY_DATA_SYNCUP));

        int res = db.update(MicrofinanceCustomers.TABLE_NAME, values,
                MicrofinanceCustomers.KEY_CUSTOMER_ID + " = ? ",
                new String[]{customerData.getInt(MicrofinanceCustomers.KEY_CUSTOMER_ID) + ""});
        db.close();
        return res;

    }

    public long insertCustomerData(Bundle customerData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME, customerData.getString(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME));
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG, customerData.getByteArray(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG));
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP, customerData.getString(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP));
        values.put(OrganizationEmployees.KEY_REGION_NAME, customerData.getString(OrganizationEmployees.KEY_REGION_NAME));
        values.put(OrganizationStaions.KEY_STATION_ID, customerData.getInt(OrganizationStaions.KEY_STATION_ID));
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_TYPE, customerData.getInt(MicrofinanceCustomers.KEY_CUSTOMER_TYPE));
        values.put(OrganizationEmployees.KEY_FIRSTNAME, customerData.getString(OrganizationEmployees.KEY_FIRSTNAME));
        values.put(OrganizationEmployees.KEY_LASTNAME, customerData.getString(OrganizationEmployees.KEY_LASTNAME));
        values.put(MicrofinanceCustomers.KEY_GUARDIAN_TYPE, customerData.getInt(MicrofinanceCustomers.KEY_GUARDIAN_TYPE));
        values.put(MicrofinanceCustomers.KEY_GUARDIAN, customerData.getString(MicrofinanceCustomers.KEY_GUARDIAN));
        values.put(MicrofinanceCustomers.KEY_GENDER, "" + customerData.getChar(MicrofinanceCustomers.KEY_GENDER));
        values.put(MicrofinanceCustomers.KEY_DOB, "" + customerData.getString(MicrofinanceCustomers.KEY_DOB));
        values.put(OrganizationEmployees.KEY_NICNUMBER, "" + customerData.getString(OrganizationEmployees.KEY_NICNUMBER));
        values.put(MicrofinanceCustomers.KEY_TOKEN_NUMBER, customerData.getString(MicrofinanceCustomers.KEY_TOKEN_NUMBER) + "");
        values.put(MicrofinanceCustomers.KEY_NIC_TYPE, "" + customerData.getString(MicrofinanceCustomers.KEY_NIC_TYPE));
        values.put(MicrofinanceCustomers.KEY_FAMILY_NUMBER, "" + customerData.getString(MicrofinanceCustomers.KEY_FAMILY_NUMBER));
        values.put(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE, "" + customerData.getString(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE));
        values.put(MicrofinanceCustomers.KEY_MARITAL_STATUS, customerData.getInt(MicrofinanceCustomers.KEY_MARITAL_STATUS));
        values.put(OrganizationEmployees.KEY_STATUS, customerData.getInt(OrganizationEmployees.KEY_STATUS));
        values.put(MicrofinanceCustomers.KEY_RELIGION, customerData.getInt(MicrofinanceCustomers.KEY_RELIGION));
        values.put(MicrofinanceCustomers.KEY_EDUCATION, customerData.getInt(MicrofinanceCustomers.KEY_EDUCATION));
        values.put(MicrofinanceCustomers.KEY_HOUSE_STATUS, customerData.getInt(MicrofinanceCustomers.KEY_HOUSE_STATUS));
        values.put(MicrofinanceCustomers.KEY_HOUSE_TYPE, customerData.getInt(MicrofinanceCustomers.KEY_HOUSE_TYPE));
        values.put(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC, customerData.getString(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE));
        values.put(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS, customerData.getString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS));
        values.put(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS, customerData.getInt(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS));
        values.put(OrganizationEmployees.KEY_MOBILENO, customerData.getString(OrganizationEmployees.KEY_MOBILENO));
        values.put(MicrofinanceCustomers.KEY_NOTES, customerData.getString(MicrofinanceCustomers.KEY_NOTES));
        values.put(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER, customerData.getString(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER));


        values.put(MicrofinanceCustomers.KEY_CUS_ADD_DATETIME, new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime()));

        Log.e(TAG, "isDeceased" + customerData.getInt(MicrofinanceCustomers.KEY_IS_DECEASED));
        values.put(MicrofinanceCustomers.KEY_IS_DECEASED, customerData.getInt(MicrofinanceCustomers.KEY_IS_DECEASED));
        values.put(MicrofinanceCustomers.KEY_IS_DISABLED, customerData.getInt(MicrofinanceCustomers.KEY_IS_DISABLED));

        values.put(MicrofinanceCustomers.KEY_DATA_SYNCUP, customerData.getInt(MicrofinanceCustomers.KEY_DATA_SYNCUP));

        long res = db.insert(MicrofinanceCustomers.TABLE_NAME, null, values);
        Log.e(TAG, "Customer Id: " + res);

        db.close();
        Log.e(TAG, "Customer Successfully Added!..");
        return res;
    }

    public ArrayList<Stations> getStationData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Stations> stationDataList = new ArrayList<Stations>();
        String selectQuery = "select * from " + OrganizationStaions.TABLE_NAME + "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Stations station = new Stations();
                    station.setStationId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ID)));
                    station.setStationName(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_NAME)));
                    /*station.setStationTypeId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_TYPE_ID)));
                    station.setStationParentId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_PARENT_ID)));
                    station.setRegionId(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_REGION_ID)));
                    station.setStatus(cursor.getInt(cursor.getColumnIndex(OrganizationStaions.KEY_STATUS)));
                    station.setStationCode(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_CODE)));
                    station.setAddress(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_ADDRESS)));
                    station.setCity(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_CITY)));
                    station.setState(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_STATE)));
                    station.setZipCode(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_ZIPCODE)));
                    station.setCountry(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_COUNTRY)));
                    station.setPhoneNumber(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_PHONENO)));
                    station.setEmailAddress(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_EMAIL_ADD)));
                    station.setNotes(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_NOTES)));
                    station.setStationAddedDateTime(cursor.getString(cursor.getColumnIndex(OrganizationStaions.KEY_STATION_ADD_DATETIME)));*/
                    stationDataList.add(station);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }


        return stationDataList;

    }


}
