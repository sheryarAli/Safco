package activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import adapters.GenericAdapter;
import customclasses.Asset;
import customclasses.CommonMethod;
import customclasses.CustomerSyncUpData;
import customclasses.Document;
import customclasses.EndlessRecyclerViewScrollListener;
import customclasses.FamilyMember;
import customclasses.Group;
import customclasses.GroupMember;
import customclasses.Guaranteer;
import customclasses.Loan;
import customclasses.LoanSyncUpData;
import customclasses.GroupSyncUpData;
import customclasses.PhpConnectivity;
import customclasses.QuestionnaireAnswer;
import cz.msebera.android.httpclient.Header;
import databases.DBHandler;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static customclasses.CommonConst.RESPONSE_SUCCESS;

/**
 * Created by shery on 11/29/2016.
 */

public class SyncUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SyncUpActivity.class.getSimpleName();

    private EditText searchFromDateEdt, searchToDateEdt;
    private TextView availabilityText;
    private Button searchBtn, cancelBtn;
    private RecyclerView mRecView;
    private GenericAdapter<Group> recAdapter;
    private ArrayList<Group> groupList = new ArrayList<>();
    private DBHandler dbHandler;
    private Toast toast;
    private int searchQueryType = 0;
    private ProgressDialog progressDialog;
    private GroupSyncUpData groupSyncUpData;
    private LinearLayoutManager linearLayoutManager;

    private int imgTotalCount = 0;
    private int imageCount, documentCount;
    private String oldNic = "";


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_up);
        searchFromDateEdt = (EditText) findViewById(R.id.searchFromDateEdt);
        searchToDateEdt = (EditText) findViewById(R.id.searchToDateEdt);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        availabilityText = (TextView) findViewById(R.id.availabilityText);
        mRecView = (RecyclerView) findViewById(R.id.syncUpList);
        linearLayoutManager = new LinearLayoutManager(this);

        progressDialog = new ProgressDialog(SyncUpActivity.this);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/fontawesome-webfont.ttf");
        cancelBtn.setTypeface(font);
        searchBtn.setTypeface(font);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Group Sync Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHandler = new DBHandler(this);

        searchFromDateEdt.setOnClickListener(this);
        searchToDateEdt.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);

        mRecView.setLayoutManager(linearLayoutManager);


        recAdapter = new GenericAdapter<Group>(this, groupList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(SyncUpActivity.this).inflate(R.layout.row_item_sync_up, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Group val, final int position) {
                final Group group = val;
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);
                itemViewHolder.text.setText("Group Name: " + group.getGroup_Name());
                itemViewHolder.syncUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (CommonMethod.isNetworkAvailable(SyncUpActivity.this)) {
                            new SyncUpTask(((Button) view), position).execute();
                        }

                    }
                });

            }

            @Override
            public void onItemClick(View view) {

            }
        };

        mRecView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.e(TAG, "onLoadMore: " + page);
                loadMore(searchQueryType);

            }
        });

        mRecView.setAdapter(recAdapter);
        loadMore(searchQueryType);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadMore(int i) {
        Log.e(TAG, "loadMore: searchQueryType: " + i);
        int lastIndexId = groupList.size() == 0 ? Integer.MAX_VALUE : groupList.get(groupList.size() - 1).getGroupId();
        switch (i) {
            case 0:
                groupList.addAll(dbHandler.getRecentSyncGroupData(lastIndexId));
                break;
            case 1:
                groupList.addAll(dbHandler.getSyncGroupDataByDate(searchFromDateEdt.getText().toString(), searchToDateEdt.getText().toString(), lastIndexId));
                break;
        }
        mRecView.post(new Runnable() {
            @Override
            public void run() {
                if (groupList.size() == 0) {
                    availabilityText.setVisibility(View.VISIBLE);
                    mRecView.setVisibility(View.GONE);
                } else {
                    availabilityText.setVisibility(View.GONE);
                    mRecView.setVisibility(View.VISIBLE);

                }
                recAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == searchFromDateEdt) {
            CommonMethod.pickDate(this, searchFromDateEdt);
        } else if (view == searchToDateEdt) {
            CommonMethod.pickDate(this, searchToDateEdt);
        } else if (view == cancelBtn) {
            searchFromDateEdt.setText("");
            searchToDateEdt.setText("");
            groupList.clear();
            searchQueryType = 0;
            loadMore(searchQueryType);

        } else if (view == searchBtn) {
            if (validateUI()) {
                groupList.clear();
                searchQueryType = 1;
                loadMore(searchQueryType);
            }
        }
    }

    private boolean validateUI() {
        if (CommonMethod.checkEmpty(searchFromDateEdt)) {
            searchFromDateEdt.setError("Please Select Any Date");
            return false;
        }
        if (CommonMethod.checkEmpty(searchToDateEdt)) {
            searchToDateEdt.setError("Please Select Any Date");
            return false;
        }
        return true;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView text;
        private Button syncUpBtn;


        public ItemViewHolder(View itemView) {
            super(itemView);
            Typeface font = Typeface.createFromAsset(getResources().getAssets(),
                    "fonts/fontawesome-webfont.ttf");
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            text = (TextView) itemView.findViewById(R.id.text);
            syncUpBtn = (Button) itemView.findViewById(R.id.syncUpBtn);
            syncUpBtn.setTypeface(font);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private class SyncUpTask extends AsyncTask<Group, Void, String> {
        private Button syncUpBtn;
        private int position;
        private ArrayList<Document> documentList1 = new ArrayList<>();


        public SyncUpTask(Button syncUpBtn, int position) {
            this.syncUpBtn = syncUpBtn;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            groupSyncUpData = new GroupSyncUpData();
            progressDialog.setTitle("Sync Up");
            progressDialog.setMessage("Preparing Data For Sync Up ..");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(Group... params) {
            groupSyncUpData.setGroup(recAdapter.getItem(position));
            ArrayList<GroupMember> groupMembers = dbHandler.getGroupMember(groupSyncUpData.getGroup().getGroupId());

//            TODO Uncomment Group Restriction Code
            if (groupMembers.size() < 3 || groupMembers.size() > 7)
                return "Group Members Cannot be less than 3 & greater than 7";

            groupSyncUpData.setGroupMembers(groupMembers);

            ArrayList<CustomerSyncUpData> customerSyncUpDatas = new ArrayList<CustomerSyncUpData>();
            for (GroupMember groupMember : groupSyncUpData.getGroupMembers()) {
                CustomerSyncUpData customerSyncUpData = new CustomerSyncUpData();
                customerSyncUpData.setCustomer(dbHandler.getCustomerForSyncUp(groupMember.getCustomerId()));
                ArrayList<Asset> assets = dbHandler.getAssetData(groupMember.getCustomerId());

                if (assets.size() == 0)
                    return groupMember.getFullname() + " Assets Not Inserted Yet";
                customerSyncUpData.setAssets(assets);
                ArrayList<FamilyMember> familyMembers = dbHandler.getFamilyMemberData(groupMember.getCustomerId());
                /*if (familyMembers.size() == 0)
                    return groupMember.getFullname() + " Family Members Not Inserted Yet";*/
                customerSyncUpData.setFamilyMembers(familyMembers);
                ArrayList<Guaranteer> guaranteers = dbHandler.getGuaranteerData(groupMember.getCustomerId());
                if (guaranteers.size() == 0)
                    return groupMember.getFullname() + " Guarantors Not Inserted Yet";
                customerSyncUpData.setGuaranteers(guaranteers);

                ArrayList<Loan> loans = dbHandler.getLoanData(groupMember.getCustomerId());
                ArrayList<LoanSyncUpData> loanSyncUpDatas = new ArrayList<LoanSyncUpData>();

                if (loans.size() == 0)
                    return groupMember.getFullname() + " Loan Form Not Filled Yet";

                for (Loan loan : loans) {
                    LoanSyncUpData loanSyncUpData = new LoanSyncUpData();
                    loanSyncUpData.setLoan(loan);
                    ArrayList<Document> documentList2 = dbHandler.getDocumentData(SyncUpActivity.this, groupMember.getNicNumber(), loan.getLoanId());

                    if (documentList2.size() == 0)
                        return groupMember.getFullname() + " Documents Not Inserted Yet";


                    documentList1.addAll(documentList2);

                    QuestionnaireAnswer questionnaireAnswer = dbHandler.getQuestionnaireAnswerData(loan.getLoanId(), loan.getCustomerId());
                    if (questionnaireAnswer == null)
                        return groupMember.getFullname() + " Poverty Score Card Form Not Filled Yet";
                    loanSyncUpData.setQuestionnaireAnswer(questionnaireAnswer);
                    loanSyncUpDatas.add(loanSyncUpData);
                }

                customerSyncUpData.setLoanSyncUpDatas(loanSyncUpDatas);
                customerSyncUpDatas.add(customerSyncUpData);
            }
            groupSyncUpData.setCustomerSyncUpDatas(customerSyncUpDatas);

            return "success";


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                imgTotalCount = documentList1.size();
                imageCount = 1;
                uploadDocument(documentList1);
            } else {
                CommonMethod.alert(SyncUpActivity.this, "Sync Up Error", s);
            }
//            uploadGroupData();
            /*Log.e(TAG, "response: " + s);
            if (s != null) {


                if (s.equalsIgnoreCase("Success")) {
                    dbHandler.updateGroupSync(recAdapter.getItem(position).getGroupId(), 1);
                    recAdapter.getItemList().remove(position);
                    recAdapter.notifyItemRemoved(position);
                    recAdapter.notifyItemRangeChanged(position, recAdapter.getItemList().size());
                    toast.setText("Sync Up Successful");
                    toast.show();

                } else if (s.equalsIgnoreCase("test")) {
                    toast.setText("Test Sync Up Successfull");
                    toast.show();
                } else {
                    CommonMethod.alert(SyncUpActivity.this, "Unable To Sync Up", s);

                }
            } else {
                toast.setText("Sync Up Fail....");
                toast.show();
            }*/

        }
    }

    public void uploadGroupData() {
//        Log.e(TAG, "group_data: "+ new Gson().toJson(groupSyncUpData));
        CommonMethod.writeToFile(new Gson().toJson(groupSyncUpData));
        RequestParams params = new RequestParams();
        params.put("group_data", new Gson().toJson(groupSyncUpData));
//TODO Test URL For Debugging
//        https://mms.safcosupport.org/test/employees/tabData/syncdown.php
        PhpConnectivity.getClient().post("https://mms.safcosupport.org/test/employees/tabData/syncdown.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progressDialog.setTitle("Group Sync Up");
                progressDialog.setMessage("Group  Data Uploading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                if (response.trim().equalsIgnoreCase(RESPONSE_SUCCESS)) {
                    progressDialog.dismiss();
                    CommonMethod.alert(SyncUpActivity.this
                            , "Group Sync Up:", "Success");
                    dbHandler.updateGroupSync(groupSyncUpData.getGroup().getGroupId(), 1);
                    recAdapter.getItemList().remove(groupSyncUpData.getGroup());
                    recAdapter.notifyDataSetChanged();
                    groupSyncUpData = null;
                } else {
                    Log.e(TAG, "onFailure: " + response);
                    progressDialog.dismiss();
                    CommonMethod.alert(SyncUpActivity.this
                            , "Group Sync Up Failure:", "Response: " + response);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody == null) {
                    CommonMethod.alert(SyncUpActivity.this
                            , "Connectivity Error:", "Try Again!");
                    progressDialog.dismiss();
                    return;
                }
                String response = new String(responseBody);
                Log.e(TAG, "onFailure: " + response);
                progressDialog.dismiss();
                CommonMethod.alert(SyncUpActivity.this
                        , "Group Sync Up Failure:", "Response: " + response);

            }
        });
    }


    public void uploadDocument(final ArrayList<Document> documents) {

        if (documents.size() == 0) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            imageCount = 1;
            uploadGroupData();
            return;
        }
        CommonMethod.writeToFile(new Gson().toJson(documents.get(0)));
//        Log.d(TAG, "uploadDocument: For Debuging");
        Document document = documents.get(0);

        if (!oldNic.equalsIgnoreCase(document.getNicNumber())) {
            oldNic = document.getNicNumber();
            documentCount = 1;
        }
        RequestParams params = new RequestParams();
        params.put("document_count", documentCount);
        params.put("document_data", new Gson().toJson(document));

        /*params.put(Document.KEY_IMAGE_NAME, document.getImageName());
        params.put(Document.KEY_IMAGE, document.getImage());
        params.put(Document.KEY_LOAN_ID, document.getLoanId());
        params.put(Document.KEY_DOC_ADDED_DATETIME, document.getDocumentAddedDataTime());*/
        PhpConnectivity.getClient().post("https://mms.safcosupport.org/employees/tabData/ImageSyncDown.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                if (progressDialog.isShowing()) {
                    progressDialog.setMessage("Uploading Document " + imageCount + " of " + imgTotalCount);
                } else {
                    progressDialog.setTitle("Documents Sync Up");
                    progressDialog.setMessage("Uploading Document " + imageCount + " of " + imgTotalCount);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                if (response.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                    Log.e(TAG, "Document Sync Up Response: " + response);
                    imageCount++;
                    documentCount++;
                    documents.remove(0);
                    uploadDocument(documents);
                } else {
                    Log.e(TAG, "Sync Up Failure: " + response);
                    progressDialog.dismiss();
                    CommonMethod.alert(SyncUpActivity.this
                            , "Document Sync Up Failure:", "Response: " + response);

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody == null) {
                    CommonMethod.alert(SyncUpActivity.this
                            , "Connectivity Error:", "Try Again!");
                    progressDialog.dismiss();
                    return;
                }
                String response = new String(responseBody);
                Log.e(TAG, "onFailure: " + response);
                CommonMethod.alert(SyncUpActivity.this
                        , "Document Sync Up Failure:", "Response: " + response);
                progressDialog.dismiss();
            }


        });

    }

}
