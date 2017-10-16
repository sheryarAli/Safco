package fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.shery.safco.R;

import java.util.ArrayList;
import java.util.Locale;


import activities.AssetActivity;
import activities.CustomerFormActivity;
import activities.FamilyMemberActivity;
import activities.GuaranteerActivity;
import activities.LoanActivity;
import adapters.GenericAdapter;
import customclasses.CommonConst;
import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.EndlessRecyclerViewScrollListener;
import customclasses.MicrofinanceCustomers;
import customclasses.NicTextWatcher;
import databases.DBHandler;
import preferences.LocalStoragePreferences;

/**
 * Created by shery on 11/15/2016.
 */

public class CustomerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CustomerFragment.class.getSimpleName();


    private DBHandler dbHandler;
    private ButtonFloat addBtn;
    private Button cancelBtn;
    private Button searchBtn, aSearchBtn;
    private RecyclerView recView;

    private ArrayList<Customer> customerList;
    private GenericAdapter<Customer> recAdapter;
    private LocalStoragePreferences preferences;
    private EditText searchByFromDateEdt, searchByToDateEdt;
    private TextView availabilityText;
    private String nic, name;
    private LinearLayoutManager linearLayoutManager;


    private int searchQueryType = 0;


    CharSequence menu[] = new CharSequence[]{"View", "Loan", "Asset", "Family Member", "Guaranteer", "Location", "Delete"};

    private void showAdvanceSearchDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.advance_search_dialog, null);
        final EditText searchNicEdt = (EditText) dialogView.findViewById(R.id.searchNicEdt);
        final EditText searchNameEdt = (EditText) dialogView.findViewById(R.id.searchNameEdt);
        searchNicEdt.addTextChangedListener(NicTextWatcher.getInstance());
        searchNicEdt.setFilters(NicTextWatcher.getFilter());

        Button searchBtn = (Button) dialogView.findViewById(R.id.searchBtn);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nic = searchNicEdt.getText().toString().trim();
                name = searchNameEdt.getText().toString().trim();
                customerList.clear();
                searchQueryType = 2;
                loadMore(searchQueryType);
                alertDialog.dismiss();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer, container, false);
        dbHandler = new DBHandler(getContext());
        addBtn = (ButtonFloat) v.findViewById(R.id.addBtn);
        recView = (RecyclerView) v.findViewById(R.id.recView);
        searchByFromDateEdt = (EditText) v.findViewById(R.id.searchByFromDateEdt);
        searchByToDateEdt = (EditText) v.findViewById(R.id.searchByToDateEdt);
        availabilityText = (TextView) v.findViewById(R.id.availabilityText);
        aSearchBtn = (Button) v.findViewById(R.id.aSearchBtn);
        cancelBtn = (Button) v.findViewById(R.id.cancelBtn);
        searchBtn = (Button) v.findViewById(R.id.searchBtn);


        aSearchBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        searchByFromDateEdt.setOnClickListener(this);
        searchByToDateEdt.setOnClickListener(this);

        Typeface font = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/fontawesome-webfont.ttf");
        cancelBtn.setTypeface(font);
        searchBtn.setTypeface(font);

        cancelBtn.setOnClickListener(this);


        preferences = new LocalStoragePreferences(getContext());

        searchByFromDateEdt.setText("");
        searchByToDateEdt.setText("");
        linearLayoutManager = new LinearLayoutManager(getContext());
        recView.setLayoutManager(linearLayoutManager);
        customerList = new ArrayList<>();
        recAdapter = new GenericAdapter<Customer>(getContext(), customerList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.customer_row_item, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Customer val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);
                itemViewHolder.custId.setText("Id: " + val.getCustomerId());
                if (val.getNICType().equalsIgnoreCase("Token Number")) {
                    itemViewHolder.custCNIC.setText("Token: " + val.getTokenNumber());
                } else {
                    itemViewHolder.custCNIC.setText("CNIC: " + val.getNICNumber());
                }

                itemViewHolder.custName.setText("Name: " + val.getFirstName() + " " + val.getLastName());
            }

            @Override
            public void onItemClick(View view) {
                showMenu(getItem(Integer.valueOf(view.getTag() + "")));
            }
        };

        recView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.e(TAG, "onLoadMore: " + page);
                loadMore(searchQueryType);

            }
        });

        recView.setAdapter(recAdapter);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "Resume");
        loadMore(searchQueryType);

    }

    private void loadMore(int i) {
        Log.e(TAG, "loadMore: searchQueryType: " + i);
        int lastIndexNo = customerList.size() == 0 ? Integer.MAX_VALUE : customerList.get(customerList.size() - 1).getCustomerId();
        switch (i) {
            case 0:
                customerList.addAll(dbHandler.getRecentCustomerData(lastIndexNo));
                break;
            case 1:
                customerList.addAll(dbHandler.
                        getCustomerDataByDate(searchByFromDateEdt.getText().toString(),
                                searchByToDateEdt.getText().toString(),
                                lastIndexNo));
                break;
            case 2:
                customerList.addAll(dbHandler.
                        getCustomerDataByNameNic(name,
                                nic,
                                lastIndexNo));

                break;
        }

        recView.post(new Runnable() {
            @Override
            public void run() {

                if (customerList.size() == 0) {
                    availabilityText.setVisibility(View.VISIBLE);
                    recView.setVisibility(View.GONE);
                } else {
                    availabilityText.setVisibility(View.GONE);
                    recView.setVisibility(View.VISIBLE);

                }
                recAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "Pause");
    }

    @Override
    public void onClick(View view) {
        if (view == addBtn) {
            if (!preferences.isSyncedDown()) {
                CommonMethod.alert(getContext(), "Alert", "Please download prerequisite data to continue", "Ok");
            } else {
                getActivity().startActivity(new Intent(getActivity(), CustomerFormActivity.class));
            }

        } else if (view == searchByFromDateEdt) {
            CommonMethod.pickDate(getContext(), searchByFromDateEdt);

        } else if (view == searchByToDateEdt) {
            CommonMethod.pickDate(getContext(), searchByToDateEdt);

        } else if (view == cancelBtn) {
            searchByToDateEdt.setText("");
            searchByFromDateEdt.setText("");
            customerList.clear();
            searchQueryType = 0;
            loadMore(searchQueryType);

        } else if (view == searchBtn) {
            /*String nic = searchByNicEdt.getText().toString();
            if (!nic.equalsIgnoreCase("")) {


            }*/
            if (validateUI()) {
                searchQueryType = 1;
                customerList.clear();
                loadMore(searchQueryType);

            }
        } else if (view == aSearchBtn) {
            showAdvanceSearchDialog();
        }

    }


    private boolean validateUI() {
        if (CommonMethod.checkEmpty(searchByFromDateEdt)) {
            searchByFromDateEdt.setError("Please Select Any Date");
            return false;
        }
        if (CommonMethod.checkEmpty(searchByToDateEdt)) {
            searchByToDateEdt.setError("Please Select Any Date");
            return false;
        }
        return true;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView custId, custName, custCNIC;


        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            custId = (TextView) itemView.findViewById(R.id.custId);
            custName = (TextView) itemView.findViewById(R.id.custName);
            custCNIC = (TextView) itemView.findViewById(R.id.custCNIC);


        }
    }

    public void showDeleteAlert(final Customer item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new DeleteCustomerDataAsync().execute(item);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void showMenu(final Customer item) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Action");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                Log.e(TAG, "menu: " + which);
                switch (which) {
//                    View
                    case 0:

                        intent = new Intent(getContext(), CustomerFormActivity.class);
                        intent.putExtra(MicrofinanceCustomers.KEY_CUSTOMER_ID, item.getCustomerId());
                        Log.e(TAG, "Customer Id: " + item.getCustomerId());
                        intent.putExtra(CommonConst.VIEW_TYPE, "view");
                        startActivity(intent);
                        intent.getExtras().clear();
                        break;

//                    Loan
                    case 1:

                        intent = new Intent(getContext(), LoanActivity.class);
                        intent.putExtra(MicrofinanceCustomers.KEY_CUSTOMER_ID, item.getCustomerId());
                        intent.putExtra(MicrofinanceCustomers.KEY_NIC_NUMBER, item.getNICNumber());
                        intent.putExtra(MicrofinanceCustomers.KEY_FULL_NAME, item.getFirstName() + " " + item.getLastName());
                        startActivity(intent);
                        break;

//                    Asset
                    case 2:

                        intent = new Intent(getContext(), AssetActivity.class);
                        intent.putExtra(MicrofinanceCustomers.KEY_CUSTOMER_ID, item.getCustomerId());
                        intent.putExtra(MicrofinanceCustomers.KEY_FULL_NAME, item.getFirstName() + " " + item.getLastName());
                        startActivity(intent);
                        break;

//                    Family Member
                    case 3:

                        intent = new Intent(getContext(), FamilyMemberActivity.class);
                        intent.putExtra(MicrofinanceCustomers.KEY_CUSTOMER_ID, item.getCustomerId());
                        intent.putExtra(MicrofinanceCustomers.KEY_FULL_NAME, item.getFirstName() + " " + item.getLastName());
                        startActivity(intent);
                        break;

//                    Guaranteer
                    case 4:

                        intent = new Intent(getContext(), GuaranteerActivity.class);
                        intent.putExtra(MicrofinanceCustomers.KEY_CUSTOMER_ID, item.getCustomerId());
                        intent.putExtra(MicrofinanceCustomers.KEY_FULL_NAME, item.getFirstName() + " " + item.getLastName());
                        startActivity(intent);
                        break;

//                    Location
                    case 5:
                        Log.e(TAG, "onClick: get Customer Location....");


                        if (item.getCustomerGPSLocation().equalsIgnoreCase("")) {
                            Toast.makeText(getContext(), "Location Not Available", Toast.LENGTH_LONG).show();
                            return;
                        }
//                        if (CommonMethod.isGpsOn(getContext())) {
                        String[] latLong = item.getCustomerGPSLocation().split(",");
                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", Double.valueOf(latLong[0]), Double.valueOf(latLong[1]), item.getFirstName() + " " + item.getLastName() + "'s Location");
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
//                        }
                        break;

//                    Delete
                    case 6:
                        showDeleteAlert(item);
                        break;

                }

                Log.e(TAG, "option: " + which);

            }
        });
        builder.show();
    }

    /*private class LoadAsyncCustomerData extends AsyncTask<Integer, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            recAdapter.notifyDataSetChanged();
        }
    }*/

    private class DeleteCustomerDataAsync extends AsyncTask<Customer, Void, Void> {
        private ProgressDialog progressDialog;
        private Customer customer;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("removing customer..");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Customer... customers) {
            customer = customers[0];
            int customerId = customer.getCustomerId();
            int loanId = dbHandler.getLoanId(customerId);
            dbHandler.deleteAssetData(customerId);
            dbHandler.deleteFamilyMemberData(customerId);
            dbHandler.deleteGuaranteerData(customerId, customer.getNICNumber());
            dbHandler.deleteGroupMember(customerId);
            dbHandler.deleteDocumentData(loanId);
            dbHandler.deleteQuegetstionnaireAnswerData(loanId, customerId);
            dbHandler.deleteLoanData(customerId);
            dbHandler.deleteCustomer(customerId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customerList.remove(customer);
            recAdapter.notifyDataSetChanged();
            progressDialog.dismiss();

        }
    }
}
