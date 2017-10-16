package fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.util.ArrayList;

import adapters.GenericAdapter;
import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.GroupMember;
import databases.DBHandler;

/**
 * Created by shery on 12/19/2016.
 */

public class DialogGroupMemberFragment extends DialogFragment implements View.OnClickListener {

    private static final String TAG = DialogGroupMemberFragment.class.getSimpleName();
    private RecyclerView customerRecView;
    private GenericAdapter<Customer> adapter;
    private ArrayList<GroupMember> dataList;
    private ArrayList<Customer> dataList2;
    private Button addBtn;
    private EditText searchNicEdt;
    private DBHandler dbHandler;

    private void initViews(View view) {
        customerRecView = (RecyclerView) view.findViewById(R.id.customerRecView);
        addBtn = (Button) view.findViewById(R.id.addBtn);
        searchNicEdt = (EditText) view.findViewById(R.id.searchNicEdt);
        customerRecView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_group_member, container, false);
        initViews(view);
        dataList = ((CustomerGroupFormFragment) getParentFragment()).groupMembers;
        dataList2 = ((CustomerGroupFormFragment) getParentFragment()).customers;
        dbHandler = new DBHandler(getContext());
        addBtn.setOnClickListener(this);
        searchNicEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (validateUI()) {
                        dataList2.clear();
                        dataList2.addAll(dbHandler.getCustomerHavingLoan(v.getText().toString()));
                        adapter.notifyDataSetChanged();

                    }
                    Toast.makeText(getContext(), v.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataList2 = dbHandler.getRecentCustomerDataHavingLoan();
        adapter = new GenericAdapter<Customer>(getContext(), dataList2) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_item_dialog, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final Customer val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.text1.setText(val.getFirstName() + " " + val.getLastName());
                itemViewHolder.text2.setText(val.getNICNumber());
                itemViewHolder.checkbox.setOnCheckedChangeListener(null);
                itemViewHolder.checkbox.setChecked(val.isActivated());
                itemViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        GroupMember groupMember = new GroupMember();
                        groupMember.setCustomerId(val.getCustomerId());
                        groupMember.setFullname(val.getFirstName() + " " + val.getLastName());
                        groupMember.setNicNumber(val.getNICNumber());
                        groupMember.setBusinessAddress(dbHandler.getCustomerBuisnessAddress(val.getCustomerId()));
                        groupMember.setMobileNumber(val.getMobileNumber());
                        groupMember.setAddress(val.getRegionName());


                        if (b && !contains(dataList, val.getNICNumber())) {

                            dataList.add(groupMember);

                        } else if ((!b && contains(dataList, val.getNICNumber()))){
                            removeMemberWithNic(groupMember.getNicNumber());
                        }
                        val.setActivated(b);
                    }
                });

            }

            @Override
            public void onItemClick(View view) {

            }
        };
        customerRecView.setAdapter(adapter);
    }

    private boolean validateUI() {
        if(CommonMethod.checkEmpty(searchNicEdt)){
            searchNicEdt.setError("Please Fill Customer NIC Field");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == addBtn) {
            ((CustomerGroupFormFragment) getParentFragment()).adapter.notifyDataSetChanged();
            getDialog().dismiss();

        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        CheckBox checkbox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);


        }
    }

    private boolean contains(ArrayList<GroupMember> groupMembers, String nic) {
        for (GroupMember groupMember : groupMembers) {
            if (groupMember.getNicNumber().equalsIgnoreCase(nic)) {
                return true;
            }
        }
        return false;
    }

    private void removeMemberWithNic(String nic) {
        for (GroupMember groupMember : dataList) {
            if (groupMember.getNicNumber().trim().equalsIgnoreCase(nic.trim())) {
                dataList.remove(groupMember);
                return;
            }
        }

    }

}
