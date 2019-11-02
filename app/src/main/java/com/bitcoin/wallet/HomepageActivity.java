package com.bitcoin.wallet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bitcoin.wallet.API.ApiWalletPage;
import com.bitcoin.wallet.APIDTO.WalletPageResponse;
import com.bitcoin.wallet.Adapters.MainPagerAdapter;
import com.bitcoin.wallet.ApiService.RetrofitSingleton;
import com.bitcoin.wallet.Fragments.ExchangeFragment;
import com.bitcoin.wallet.Fragments.MyWalletFragment;
import com.bitcoin.wallet.Fragments.TransactionFragment;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomepageActivity extends AppCompatActivity {
    private static final String TAG = "HomepageActivity";
    private TabLayout tabLayout;
    private SharedManager sharedManager;
    private TextView toolbarTitle, tvTokenBalance, tvUserBalance, tvUserEmail;
    private ViewPager viewPager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private boolean isStateChanged = false;
    private String toolbarUserTokenPrice;
    private ImageView imageLogout;
    private View loadingLayout;
    private int viewPagerIndex;

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        init();
        getWalletPageRequest(sharedManager.getData(Constants.KEY_TOKEN));
        imageLogout.setOnClickListener(view -> sharedManager.logoutUser());

        setTabColor(); // setting Tab Layout color on collapsed or expanded state

        Toolbar myToolbar = findViewById(R.id.custom_toolbar_simple);
        setSupportActionBar(myToolbar);

        swipeRefreshLayout.setColorSchemeResources(R.color.red_btn_register_clicked); // set loader color
        swipeRefreshLayout.setOnRefreshListener(this::onPullToRefresh);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPagerIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTabColor() {


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {

                swipeRefreshLayout.setEnabled(verticalOffset == 0);
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    toolbarTitle.setText("Your balance: " + toolbarUserTokenPrice);
                    if (isStateChanged) {
                        YoYo.with(Techniques.FadeInDown)
                                .duration(700)
                                .playOn(toolbarTitle);
                        isStateChanged = false;
                    }

                } else {
                    tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));


                    if (!isStateChanged) {
                        YoYo.with(Techniques.FadeInUp)
                                .duration(800)
                                .playOn(toolbarTitle);
                        toolbarTitle.setText(getString(R.string.app_name));
                        isStateChanged = true;
                    }


                }

            }
        });
    }

    public void toggleRefreshing(boolean enabled) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enabled);
        }
    }

    private void onPullToRefresh() {
        if (Constants.refreshAble) {
            initPagerAdapter();
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void initPagerAdapter() {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), getFragmentList(), getFragmentNames());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageScrollStateChanged(int state) {
                toggleRefreshing(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(viewPagerIndex,true);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void init() {
        toolbarTitle = findViewById(R.id.toolbar_textview);
        imageLogout = findViewById(R.id.menu_btn_exit);
        appBarLayout = findViewById(R.id.appbar_layout_activity);
        viewPager = findViewById(R.id.activity_homepage_viewpager);
        swipeRefreshLayout = findViewById(R.id.activity_homepage_pullToRefresh);
        tabLayout = findViewById(R.id.tabLayout);
        tvTokenBalance = findViewById(R.id.activity_wallet_text_view_token_balance);
        tvUserBalance = findViewById(R.id.activity_wallet_text_view_user_balance);
        tvUserEmail = findViewById(R.id.activity_wallet_text_view_email_address);
        loadingLayout = findViewById(R.id.layout_loading_frame_container);
        sharedManager = new SharedManager(this);
    }

    private void getWalletPageRequest(String token) {
        CustomProgressBar.showProgress(loadingLayout);
        ApiWalletPage api = RetrofitSingleton.initRetrofit().create(ApiWalletPage.class);
        Call<WalletPageResponse> call = api.walletPageRequest(token);
        call.enqueue(new Callback<WalletPageResponse>() {
            @Override
            public void onResponse(Call<WalletPageResponse> call, Response<WalletPageResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    parseDataToUi(response.body());
                    CustomProgressBar.dismissProgress(loadingLayout);
                    initPagerAdapter();
                    Constants.refreshAble = true;
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                    Constants.refreshAble = true;
                    CustomProgressBar.dismissProgress(loadingLayout);
                }

            }

            @Override
            public void onFailure(Call<WalletPageResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: wallet page req" + t.getMessage());
                CustomProgressBar.dismissProgress(loadingLayout);
                Constants.refreshAble = true;
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);

                }
            }
        });

    }

    private void parseDataToUi(WalletPageResponse model) {
        if (sharedManager.getData(Constants.KEY_WALLET_ADDRESS).isEmpty()){
            sharedManager.setData(model.getPrivatekey(),Constants.KEY_PRIVATE_KEY);
            sharedManager.setData(model.getAddresses(),Constants.KEY_WALLET_ADDRESS);
        }
        tvUserEmail.setText(sharedManager.getData(Constants.KEY_EMAIL));
        if (model.getBalance() == null) {
            tvUserBalance.setText("0");
            toolbarUserTokenPrice = "0";
        } else {
            tvUserBalance.setText(model.getBalance());
            toolbarUserTokenPrice = model.getBalance();
        }

        if (model.getPrice() == null) tvTokenBalance.setText("0");
        else tvTokenBalance.setText(model.getPrice());
    }

    /* Sets up view pager and tab layout */
    private List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyWalletFragment());
        fragmentList.add(new TransactionFragment());
        fragmentList.add(new ExchangeFragment());
        return fragmentList;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    /* Generates tabs names that would be used in tab bar */
    private List<String> getFragmentNames() {
        List<String> tabsName = new ArrayList<>();
        tabsName.add(getString(R.string.general_wallet_fragment_name));
        tabsName.add(getString(R.string.general_transaction_fragment_name));
        tabsName.add(getString(R.string.general_exchange_fragment_name));
        return tabsName;
    }


}


