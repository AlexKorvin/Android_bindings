package com.example.aleksandrmykhailenko.androidbindings.ui.split;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.aleksandrmykhailenko.androidbindings.R;
import com.example.aleksandrmykhailenko.androidbindings.databinding.ActivitySplitTunnelingBinding;

public class SplitTunnelingActivity extends AppCompatActivity {

    private static final String TAG = SplitTunnelingActivity.class.getSimpleName();

    private SplitTunnelingViewModel viewModel;
    private ActivitySplitTunnelingBinding binding;
    private SplitTunnelingRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return false;
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.split_tunneling_title);
    }

    private void init() {
        adapter = new SplitTunnelingRecyclerViewAdapter();
        viewModel = new SplitTunnelingViewModel();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_split_tunneling);
        binding.contentLayout.setViewmodel(viewModel);
        binding.contentLayout.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.contentLayout.recyclerView.setAdapter(adapter);

        getAllApplications();
    }

    private void getAllApplications() {
        viewModel.getApplicationsList(getPackageManager());
    }

}