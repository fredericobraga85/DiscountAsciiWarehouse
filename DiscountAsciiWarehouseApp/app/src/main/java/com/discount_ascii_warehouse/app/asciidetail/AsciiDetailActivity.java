package com.discount_ascii_warehouse.app.asciidetail;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.discount_ascii_warehouse.app.utils.Injection;
import com.discount_ascii_warehouse.discountasciiwarehouseapp.R;

public class AsciiDetailActivity extends AppCompatActivity {

    private AsciiDetailFragment asciiDetailFragment;
    private AsciiDetailPresenter asciiDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascii_detail);

        configActionBar();

        initMVP();

    }

    private void configActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private void initMVP() {

        asciiDetailPresenter = new AsciiDetailPresenter();
        asciiDetailFragment = AsciiDetailFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, asciiDetailFragment);
        transaction.commit();

        asciiDetailFragment.setAsciiDetailPresenter(asciiDetailPresenter);
        asciiDetailPresenter.setAsciiDetailView(asciiDetailFragment);

    }


}
