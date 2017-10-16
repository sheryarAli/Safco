package fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shery.safco.R;

import adapters.HomePagerAdapter;

/**
 * Created by shery on 11/15/2016.
 */

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.homeViewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.homeTabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont();
        return v;
    }
    private void setupViewPager(ViewPager viewPager){

        HomePagerAdapter adapter = new HomePagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CustomerFragment(), "Customer");
        adapter.addFragment(new GroupFragment(), "Group");
        /*adapter.addFragment(new LoanFragment(), "Loan");*/
        viewPager.setAdapter(adapter);

    }
    private void changeTabsFont(){
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(tf);
                }
            }
        }
    }
}
