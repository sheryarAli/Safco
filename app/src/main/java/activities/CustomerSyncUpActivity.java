package activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import adapters.GenericAdapter;
import customclasses.Asset;
import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.CustomerSyncUpData;
import customclasses.Document;
import customclasses.EndlessRecyclerViewScrollListener;
import customclasses.FamilyMember;
import customclasses.Group;
import customclasses.GroupMember;
import customclasses.GroupSyncUpData;
import customclasses.Guaranteer;
import customclasses.Loan;
import customclasses.LoanSyncUpData;
import customclasses.PhpConnectivity;
import customclasses.QuestionnaireAnswer;
import cz.msebera.android.httpclient.Header;
import databases.DBHandler;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static customclasses.CommonConst.RESPONSE_SUCCESS;

/**
 * Created by shery on 6/10/2017.
 */

public class CustomerSyncUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CustSyncUpActivity";
    private DatePickerDialog mDatePickerDialog;
    private Button cancelBtn, searchBtn;
    private EditText searchFromDateEdt, searchToDateEdt;
    private TextView availabilityText;
    private RecyclerView mRecView;
    private GenericAdapter<Customer> recAdapter;
    private ArrayList<Customer> customerList;
    private DBHandler dbHandler;
    private Toast toast;
    private AlertDialog alertDialog;
    private SimpleDateFormat dateFormater;
    private LinearLayoutManager linearLayoutManager;
    private int searchQueryType = 0;
    private int imageCount, imgTotalCount;
    private ProgressDialog progressDialog;
    private GroupSyncUpData groupSyncUpData;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_up);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchFromDateEdt = (EditText) findViewById(R.id.searchFromDateEdt);
        searchToDateEdt = (EditText) findViewById(R.id.searchToDateEdt);
        availabilityText = (TextView) findViewById(R.id.availabilityText);

        mRecView = (RecyclerView) findViewById(R.id.syncUpList);
        linearLayoutManager = new LinearLayoutManager(this);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/fontawesome-webfont.ttf");
        cancelBtn.setTypeface(font);
        searchBtn.setTypeface(font);
        progressDialog = new ProgressDialog(CustomerSyncUpActivity.this);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        searchFromDateEdt.setOnClickListener(this);
        searchToDateEdt.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Customer Sync Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHandler = new DBHandler(this);
        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        mRecView.setLayoutManager(new LinearLayoutManager(this));
        customerList = new ArrayList<>();
        recAdapter = new GenericAdapter<Customer>(this, customerList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(CustomerSyncUpActivity.this).inflate(R.layout.row_item_sync_up, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Customer val, int position) {
                final Customer customer = val;
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);
                itemViewHolder.text.setText("Customer Name: " + customer.getFirstName() + " " + customer.getLastName());
                itemViewHolder.syncUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (CommonMethod.isNetworkAvailable(CustomerSyncUpActivity.this)) {
                            new CustomerSyncUpTask().execute(customer);
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

    @Override
    public void onClick(View view) {
        if (view == searchBtn) {
            if (validateUI()) {
                customerList.clear();
                searchQueryType = 1;
                loadMore(searchQueryType);
            }

        } else if (view == cancelBtn) {
            searchToDateEdt.setText("");
            searchFromDateEdt.setText("");
            customerList.clear();
            searchQueryType = 0;
            loadMore(searchQueryType);
        } else if (view == searchToDateEdt) {
            CommonMethod.pickDate(this, searchToDateEdt);

        } else if (view == searchFromDateEdt) {
            CommonMethod.pickDate(this, searchFromDateEdt);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMore(int i) {
        Log.e(TAG, "loadMore: searchQueryType: " + i);
        int lastIndexId = customerList.size() == 0 ? Integer.MAX_VALUE : customerList.get(customerList.size() - 1).getCustomerId();
        switch (i) {
            case 0:
                customerList.addAll(dbHandler.getCustomerNotGMember(lastIndexId));
                break;
            case 1:
                customerList.addAll(dbHandler.getCustomerNotGMemberByDate(searchFromDateEdt.getText().toString(), searchToDateEdt.getText().toString(), lastIndexId));
                break;
        }
        mRecView.post(new Runnable() {
            @Override
            public void run() {
                if (customerList.size() == 0) {
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

    private class CustomerSyncUpTask extends AsyncTask<Customer, Void, String> {
        private ProgressDialog progressDialog;
        private Customer customer;
        private ArrayList<Document> documentList1 = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CustomerSyncUpActivity.this);
            progressDialog.setTitle("Customer Sync Up");
            progressDialog.setMessage("Uploading Data..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Customer... customers) {
            customer = customers[0];
            groupSyncUpData = new GroupSyncUpData();
            Group group = new Group();

            group.setGroup_Name(customer.getFirstName() + " " + customer.getLastName());
            group.setGroup_Note("note");
            group.setGroup_Added_By("Added_By");
            group.setGroup_Added_By_Comment("Added_By_Comment");
            group.setGroup_Added_By_Designation("Added_By_Designation");
            group.setGroup_Verified_By("Verified_By");
            group.setGroup_Verified_By_Comment("Verified_By_Comment");
            group.setGroup_Verified_By_Designation("Verified_By_Designation");
            group.setGroup_Status("Processed");

            groupSyncUpData.setGroup(group);
            GroupMember groupMember = new GroupMember();
            groupMember.setFullname(customer.getFirstName() + " " + customer.getLastName());
            groupMember.setCustomerGroupId(0);
            groupMember.setGroupLeader(true);
            groupMember.setCustomerId(customer.getCustomerId());
            groupMember.setNicNumber(customer.getNICNumber());
            groupMember.setCustomerGroupMemberId(0);

            CustomerSyncUpData customerSyncUpData = new CustomerSyncUpData();
            customerSyncUpData.setCustomer(customer);
            ArrayList<Asset> assets = dbHandler.getAssetData(customer.getCustomerId());
            if (assets.size() == 0)
                return customer.getFirstName() + " " + customer.getLastName() + " Assets Not Inserted Yet";
            customerSyncUpData.setAssets(assets);
            ArrayList<FamilyMember> familyMembers = dbHandler.getFamilyMemberData(customer.getCustomerId());
            /*if (familyMembers.size() == 0)
                return customer.getFirstName() + " " + customer.getLastName() + " Family Members Not Inserted Yet";*/
            customerSyncUpData.setFamilyMembers(familyMembers);

            ArrayList<Guaranteer> guaranteers = dbHandler.getGuaranteerData(customer.getCustomerId());
            if (guaranteers.size() == 0)
                return customer.getFirstName() + " " + customer.getLastName() + " Guarantors Not Inserted Yet";
            customerSyncUpData.setGuaranteers(guaranteers);
            ArrayList<Loan> loans = dbHandler.getLoanData(customer.getCustomerId());


            if (loans.size() == 0)
                return customer.getFirstName() + " " + customer.getLastName() + " Loan Form Not Filled Yet";

            ArrayList<LoanSyncUpData> loanSyncUpDatas = new ArrayList<LoanSyncUpData>();
            for (Loan loan : loans) {
                LoanSyncUpData loanSyncUpData = new LoanSyncUpData();
                loanSyncUpData.setLoan(loan);
                ArrayList<Document> documentList2 = dbHandler.getDocumentData(CustomerSyncUpActivity.this, customer.getNICNumber(), loan.getLoanId());
                if (documentList2.size() == 0)
                    return groupMember.getFullname() + " Documents Not Inserted Yet";

                documentList1.addAll(documentList2);
                QuestionnaireAnswer questionnaireAnswer = dbHandler.getQuestionnaireAnswerData(loan.getLoanId(), loan.getCustomerId());
                if (questionnaireAnswer == null)
                    return customer.getFirstName() + " " + customer.getLastName() + " Poverty Score Card Form Not Filled Yet";
                loanSyncUpData.setQuestionnaireAnswer(questionnaireAnswer);
                loanSyncUpDatas.add(loanSyncUpData);
            }
            customerSyncUpData.setLoanSyncUpDatas(loanSyncUpDatas);
            groupSyncUpData.getGroupMembers().add(groupMember);
            groupSyncUpData.getCustomerSyncUpDatas().add(customerSyncUpData);

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
                CommonMethod.alert(CustomerSyncUpActivity.this, "Sync Up Error", s);
            }
            /*Log.e(TAG, "response: " + s);
            if (s != null) {


                if (s.equalsIgnoreCase("Success")) {
                    dbHandler.updateCustomerSync(customer.getCustomerId(), 1);
                    recAdapter.getItemList().remove(customer);
                    recAdapter.notifyDataSetChanged();
                    toast.setText("Sync Up Successful");
                    toast.show();

                } else if (s.equalsIgnoreCase("test")) {
                    toast.setText("Test Sync Up Successfull");
                    toast.show();
                } else {
                    CommonMethod.alert(CustomerSyncUpActivity.this, "Unable To Sync Up", s);
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


        PhpConnectivity.getClient().post("https://mms.safcosupport.org/employees/tabData/syncdown.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progressDialog.setTitle("Customer Sync Up");
                progressDialog.setMessage("Customer  Data Uploading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressDialog.dismiss();
                String response = new String(responseBody);
                if (response.trim().equalsIgnoreCase(RESPONSE_SUCCESS)) {
                    CommonMethod.alert(CustomerSyncUpActivity.this
                            , "Customer Sync Up:", "Success");
                    Customer customer = groupSyncUpData.getCustomerSyncUpDatas().get(0).getCustomer();
                    dbHandler.updateCustomerSync(customer.getCustomerId(), 1);
                    recAdapter.getItemList().remove(customer);
                    recAdapter.notifyDataSetChanged();
                    toast.setText("Sync Up Successful");
                    toast.show();
                    groupSyncUpData = null;
                } else {
                    Log.e(TAG, "onFailure: " + response);
                    CommonMethod.alert(CustomerSyncUpActivity.this
                            , "Customer Sync Up Failure:", "Response: " + response);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressDialog.dismiss();
                if (responseBody == null) {
                    CommonMethod.alert(CustomerSyncUpActivity.this
                            , "Connectivity Error:", "Try Again!");
                    return;
                }
                String response = new String(responseBody);
                Log.e(TAG, "onFailure: " + response);
                CommonMethod.alert(CustomerSyncUpActivity.this
                        , "Customer Sync Up Failure:", "Response: " + response);

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
        Document document = documents.get(0);


        RequestParams params = new RequestParams();
        params.put("document_count", imageCount);
        params.put("document_data", new Gson().toJson(document));

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
                    documents.remove(0);
                    uploadDocument(documents);
                } else {
                    Log.e(TAG, "Sync Up Failure: " + response);
                    progressDialog.dismiss();
                    CommonMethod.alert(CustomerSyncUpActivity.this
                            , "Document Sync Up Failure:", "Response: " + response);

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody == null) {
                    CommonMethod.alert(CustomerSyncUpActivity.this
                            , "Connectivity Error:", "Try Again!");
                    progressDialog.dismiss();
                    return;
                }
                String response = new String(responseBody);
                Log.e(TAG, "onFailure: " + response);
                CommonMethod.alert(CustomerSyncUpActivity.this
                        , "Document Sync Up Failure:", "Response: " + response);
                progressDialog.dismiss();
            }


        });

    }

}
