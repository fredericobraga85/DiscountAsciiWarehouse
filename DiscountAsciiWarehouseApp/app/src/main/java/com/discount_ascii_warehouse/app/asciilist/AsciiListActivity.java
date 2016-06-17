package com.discount_ascii_warehouse.app.asciilist;

import android.app.SearchManager;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.discount_ascii_warehouse.app.data.ascii.AsciiService;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequestRepository;
import com.discount_ascii_warehouse.app.utils.Injection;
import com.discount_ascii_warehouse.discountasciiwarehouseapp.R;


public class AsciiListActivity extends AppCompatActivity {

    private AsciiListFragment asciiListFragment;
    private AsciiListPresenter asciiListPresenter;
    private AsciiRequestRepository asciiRequestRepository;
    private AsciiService asciiService;

    private static final String ASCII_FRAG_TAG = "asciiFragTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascii_list);


        initMVP();

    }


    private void initMVP() {

        asciiListPresenter     = new AsciiListPresenter();
        asciiRequestRepository = Injection.getAsciiRequestRepository();
        asciiService           = Injection.getAsciiService();

        FragmentManager fragmentManager = getSupportFragmentManager();
        asciiListFragment = (AsciiListFragment) fragmentManager.findFragmentByTag(ASCII_FRAG_TAG);

        if(asciiListFragment == null)
        {
            asciiListFragment = asciiListFragment.newInstance(asciiListPresenter);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.contentFrame, asciiListFragment,ASCII_FRAG_TAG);
            transaction.commit();
        }

        asciiListPresenter.setAsciiListView(asciiListFragment);
        asciiListPresenter.setAsciiRepository(asciiRequestRepository);
        asciiListPresenter.setAsciiService(asciiService);

    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            asciiListFragment.search(query);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
