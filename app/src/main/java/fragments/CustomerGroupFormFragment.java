package fragments;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import adapters.GenericAdapter;
import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.Group;
import customclasses.GroupMember;
import customclasses.Guaranteer;
import databases.DBHandler;

/**
 * Created by shery on 12/3/2016.
 */

public class CustomerGroupFormFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CustomerGroupFormFragment.class.getSimpleName();
    private EditText groupNameEdt, noteEdt, addByEdt,
            addByComEdt, verifyByEdt, verifyByComEdt;
    private Spinner groupStatusSpin, addByDesSpin, verifyByDesSpin;
    private Button saveBtn, addBtn;
    private CheckBox status;
    private RecyclerView gMemberRecView;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat dateFormater;
    private DBHandler dbHandler;
    private Date curDate;
    private String timeStamp;
    private Toast toast;

    public ArrayList<GroupMember> groupMembers, deleteList;
    public ArrayList<Customer> customers;
    public GenericAdapter<GroupMember> adapter;
    private FragmentManager fragmentManager;
    private Group group;


    private void initViews(View view) {
        groupNameEdt = (EditText) view.findViewById(R.id.groupNameEdt);

        noteEdt = (EditText) view.findViewById(R.id.noteEdt);
        addByEdt = (EditText) view.findViewById(R.id.addByEdt);
        addByComEdt = (EditText) view.findViewById(R.id.addByComEdt);
        verifyByEdt = (EditText) view.findViewById(R.id.verifyByEdt);
        verifyByComEdt = (EditText) view.findViewById(R.id.verifyByComEdt);


        addBtn = (Button) view.findViewById(R.id.addBtn);

        groupStatusSpin = (Spinner) view.findViewById(R.id.groupStatusSpin);
        addByDesSpin = (Spinner) view.findViewById(R.id.addByDesSpin);
        verifyByDesSpin = (Spinner) view.findViewById(R.id.verifyByDesSpin);

        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        status = (CheckBox) view.findViewById(R.id.status);
        gMemberRecView = (RecyclerView) view.findViewById(R.id.gMemberRecView);

        gMemberRecView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    private void checkViewType() {
        group = (Group) getActivity().getIntent().getSerializableExtra("group");
        if (group != null) {
            saveBtn.setText("Update");
            groupNameEdt.setText(group.getGroup_Name());

            noteEdt.setText(group.getGroup_Note());
            addByEdt.setText(group.getGroup_Added_By());
            addByComEdt.setText(group.getGroup_Added_By_Comment());
            CommonMethod.setSpinnerSelectedItem(addByDesSpin, group.getGroup_Added_By_Designation());


            Log.e(TAG, "Group Approved: " + group.getGroup_Approved_By_Designation());


            verifyByEdt.setText(group.getGroup_Verified_By());

            verifyByComEdt.setText(group.getGroup_Verified_By_Comment());
            Log.e(TAG, "Group Id: " + group.getGroupId());
            status.setChecked(group.getSyncUpStatus() == 1);
            CommonMethod.setSpinnerSelectedItem(verifyByDesSpin, group.getGroup_Verified_By_Designation());
            groupMembers = dbHandler.getGroupMember(group.getGroupId());
            for (GroupMember groupMember : groupMembers) {
                Log.e(TAG, "groupMember Full Name: " + groupMember.getFullname());

            }
            Log.e(TAG, "groupMembers Size: " + groupMembers.size());


        } else {
            status.setVisibility(View.GONE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_group_form, container, false);
        fragmentManager = getChildFragmentManager();
        dbHandler = new DBHandler(getContext());
        groupMembers = new ArrayList<GroupMember>();
        deleteList = new ArrayList<GroupMember>();

        initViews(view);

        customers = new ArrayList<Customer>();
        checkViewType();

        addBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        return view;
    }

    private boolean validateUI() {
        if (
                groupNameEdt.getText().toString().equalsIgnoreCase("")
                        || addByEdt.getText().toString().equalsIgnoreCase("")
                        || addByComEdt.getText().toString().equalsIgnoreCase("")
                        || groupMembers.size() == 0

                ) {

            toast.setText("Please fill all required fields");
            toast.show();
            return false;
        } else if (!checkGroupLeader()) {
            toast.setText("Group Leader Not Set...");
            toast.show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");


        adapter = new GenericAdapter<GroupMember>(getContext(), groupMembers) {

            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_item_group_member, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;

            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final GroupMember val, final int position) {

                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.text1.setText(val.getFullname());
                itemViewHolder.checkBox.setTag(position);
                itemViewHolder.checkBox.setOnCheckedChangeListener(null);
                Log.e(TAG, "on Bind Data, isLeader: " + val.isGroupLeader());
                /*itemViewHolder.checkBox.setChecked(val.isGroupLeader());*/
                if (val.isGroupLeader()) {
                    selectedPosition = position;
                }
                /*if (getIte)*/
                if (position == selectedPosition) {
                    itemViewHolder.checkBox.setChecked(true);
                } else {
                    itemViewHolder.checkBox.setChecked(false);
                }


                itemViewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (saveBtn.getText().toString().equalsIgnoreCase("update")) {

                            deleteList.add(getItem(position));
                        }
                        if (getItem(position).isGroupLeader()) {
                            selectedPosition = -1;
                        }
                        getItemList().remove(position);
                        adapter.notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemList().size());
                    }
                });

                itemViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Log.e(TAG, val.getFullname() + " leader: " + b);
                        if (selectedPosition != -1)
                            getItem(selectedPosition).setGroupLeader(false);
                        if (b) {
                            selectedPosition = position;
                            itemViewHolder.checkBox.setChecked(true);
                            getItem(selectedPosition).setGroupLeader(true);
                            notifyDataSetChanged();


                        } else {
                            itemViewHolder.checkBox.setChecked(false);
                        }
                    }
                });
            }

            @Override
            public void onItemClick(View view) {

            }
        };
        gMemberRecView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        if (view == saveBtn) {
            if (validateUI()) {
                if (saveBtn.getText().toString().equalsIgnoreCase("update")) {

                    group.setGroup_Name(groupNameEdt.getText().toString());
                    group.setGroup_Status(groupStatusSpin.getSelectedItem() + "");
                    group.setGroup_Note(noteEdt.getText().toString());
                    group.setGroup_Added_By(addByEdt.getText().toString());
                    group.setGroup_Added_By_Comment(addByComEdt.getText().toString());
                    group.setGroup_Added_By_Designation(addByDesSpin.getSelectedItem() + "");


                    group.setGroup_Verified_By(verifyByEdt.getText().toString());
                    group.setGroup_Verified_By_Comment(verifyByComEdt.getText().toString());
                    group.setGroup_Verified_By_Designation(verifyByDesSpin.getSelectedItem() + "");
                    group.setSyncUpStatus((status.isChecked() ? 1 : 0));
                    int res = dbHandler.updateGroup(group);
                    if (res != 0) {

                        dbHandler.deleteGroupMembers(deleteList);
                        dbHandler.insertGroupMember(groupMembers, group.getGroupId());
                        addGuaranteer();
                        toast.setText("Group Successfully Updated..");
                        toast.show();
                        getActivity().finish();
                    }

                } else if (saveBtn.getText().toString().equalsIgnoreCase("save")) {
                    Group group = new Group();
                    group.setGroup_Name(groupNameEdt.getText().toString());
                    group.setGroup_Status(groupStatusSpin.getSelectedItem() + "");
                    group.setGroup_Note(noteEdt.getText().toString());
                    group.setGroup_Added_By(addByEdt.getText().toString());
                    group.setGroup_Added_By_Comment(addByComEdt.getText().toString());
                    group.setGroup_Added_By_Designation(addByDesSpin.getSelectedItem() + "");


                    group.setGroup_Verified_By(verifyByEdt.getText().toString());
                    group.setGroup_Verified_By_Comment(verifyByComEdt.getText().toString());
                    group.setGroup_Verified_By_Designation(verifyByDesSpin.getSelectedItem() + "");
                    int res = Integer.valueOf(dbHandler.insertGroup(group) + "");

                    if (res != -1) {

                        dbHandler.insertGroupMember(groupMembers, res);
                        addGuaranteer();
                        toast.setText("Group Successfully Added..");
                        toast.show();
                        getActivity().finish();

                    }
                }

            }


        } else if (view == addBtn) {
            DialogGroupMemberFragment fragment = new DialogGroupMemberFragment();
            fragment.show(fragmentManager, "CustomerGroupFormFragment");
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        Button delBtn;
        CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            checkBox = (CheckBox) itemView.findViewById(R.id.leaderChk);
            delBtn = (Button) itemView.findViewById(R.id.delBtn);
            Typeface font = Typeface.createFromAsset(getResources().getAssets(),
                    "fonts/fontawesome-webfont.ttf");
            delBtn.setTypeface(font);


        }
    }

    private void addGuaranteer() {
        for (GroupMember groupMember : adapter.getItemList()) {
            for (GroupMember groupMember1 : adapter.getItemList()) {
                if (groupMember.getCustomerId() != groupMember1.getCustomerId()) {
                    Guaranteer guaranteer = new Guaranteer();
                    guaranteer.setCustomerId(groupMember.getCustomerId());
                    guaranteer.setFullName(groupMember1.getFullname());
                    guaranteer.setNICNumber(groupMember1.getNicNumber());
                    guaranteer.setBusinessAddress(groupMember1.getBusinessAddress());
                    guaranteer.setContactNumber(groupMember.getMobileNumber());
                    guaranteer.setAddress(groupMember.getAddress());
                    guaranteer.setStatus(1);
                    dbHandler.insertGuaranteerData(guaranteer);
                }
            }

        }
    }


    private boolean checkGroupLeader() {
        for (GroupMember groupMember : adapter.getItemList()) {
            Log.e(TAG, groupMember.getFullname() + " isLeader: " + groupMember.isGroupLeader());
            if (groupMember.isGroupLeader()) {
                return true;
            }
        }
        return false;
    }


}
