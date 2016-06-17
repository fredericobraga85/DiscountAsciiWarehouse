package com.discount_ascii_warehouse.app.asciilist;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequest;
import com.discount_ascii_warehouse.app.utils.AppProperties;
import com.discount_ascii_warehouse.app.utils.ScreenUtils;
import com.discount_ascii_warehouse.app.utils.AsciiGridCalculator;
import com.discount_ascii_warehouse.discountasciiwarehouseapp.R;
import com.discount_ascii_warehouse.app.asciidetail.AsciiDetailActivity;
import com.discount_ascii_warehouse.app.data.ascii.Ascii;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class AsciiListFragment extends Fragment implements AsciiListContract.View {

    FrameLayout flProgressBar;
    RecyclerView recyclerView;
    TextView tvMsg;


    private Integer limit = 0;
    private Integer skip = 0;
    private String query = "";
    private boolean isOnlyInStock = false;
    private AsciiListContract.UserActionsListener asciiListPresenter;
    private Menu menu;



    public AsciiListFragment() {
        // Required empty public constructor
    }

    public static AsciiListFragment newInstance(AsciiListContract.UserActionsListener asciiListPresenter) {
        AsciiListFragment fragment = new AsciiListFragment();
        fragment.asciiListPresenter = asciiListPresenter;

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ascii_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        flProgressBar = (FrameLayout) view.findViewById(R.id.flProgressBar);
        tvMsg = (TextView) view.findViewById(R.id.tvMsg);
        recyclerView.addOnScrollListener(onRecylerViewScrolled());



        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calculateLimit();
        skip = 0;

        asciiListPresenter.onClickSearchAscii(getAsciiRequest(limit, skip, query, isOnlyInStock));

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        this.menu = menu;
        inflater.inflate(R.menu.menu_ascii_list, menu);
        configSearchBar(menu);
        configOnlyStockIcon();

    }

    private void configOnlyStockIcon() {

        if(isOnlyInStock)
        {
            showOnlyStockIconActivated();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.onlyInStock)
        {
            skip = 0;
            isOnlyInStock = !isOnlyInStock;
            asciiListPresenter.onClickOnlyInStock(getAsciiRequest(limit,skip,query,isOnlyInStock));
        }

        return true;
    }

    private void configSearchBar(Menu menu) {

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                search("");
                return true;

            }
        });

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getActivity().getApplicationContext(), AsciiListActivity.class)));

        if(!"".equals(query)) {
            searchItem.expandActionView();
            searchView.setQuery(query, false);
        }



    }

    private RecyclerView.OnScrollListener onRecylerViewScrolled() {

        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View view = (View) recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                int diff = (view.getBottom() - (recyclerView.getHeight() + recyclerView.getScrollY()));


                if (diff == 0) {

                    skip = limit + skip;

                    asciiListPresenter.onRefreshAsciiList(getAsciiRequest(limit, skip, query, isOnlyInStock));
                }
            }
        };
    }





    public AsciiListContract.UserActionsListener getAsciiListPresenter() {
        return asciiListPresenter;
    }

    public void setAsciiListPresenter(AsciiListContract.UserActionsListener asciiListPresenter) {
        this.asciiListPresenter = asciiListPresenter;
    }



    private void calculateLimit() {

        limit = AsciiGridCalculator.calculateLimit(getActivity());
    }



    public void search(String query) {
        this.query = query;
        this.skip = 0;

        asciiListPresenter.onClickSearchAscii(getAsciiRequest(limit, skip , query, isOnlyInStock));
    }



    // VIEW

    @Override
    public void showLoading() {

        flProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvMsg.setVisibility(View.GONE);

    }

    @Override
    public void hideLoading() {

        recyclerView.setVisibility(View.VISIBLE);
        flProgressBar.setVisibility(View.GONE);
    }



    private void configRecyclerView( List<Ascii> asciiList)
    {
        final AsciiListAdapter adapter = new AsciiListAdapter(getActivity(), asciiList, new AsciiListAdapter.AsciiAdapterListener() {

            @Override
            public void onClickAscii(Ascii ascii) {
                asciiListPresenter.onClickAscii(ascii);
            }
        });

        recyclerView.setAdapter(adapter);

        if(recyclerView.getLayoutManager() == null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 100);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position < adapter.getAsciiList().size()) {
                        return AsciiGridCalculator.calculateCardSpan(getActivity(), adapter.getAsciiList(), position);
                    } else {
                        return 100;
                    }
                }
            });

            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    @Override
    public void showSearchedAsciiList(final List<Ascii> asciiList) {

        if(recyclerView.getAdapter() == null) {
            configRecyclerView(asciiList);
        }
        else
        {
            ((AsciiListAdapter)recyclerView.getAdapter()).swap(asciiList);
        }

    }

    @Override
    public void showRefreshedAsciiList(final List<Ascii> asciiList) {

        ((AsciiListAdapter)recyclerView.getAdapter()).refresh(asciiList);

    }


    @Override
    public void showEmptyListMessage() {

        recyclerView.setVisibility(View.GONE);
        tvMsg.setText(R.string.no_results_found);
        tvMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg(String msg) {

        recyclerView.setVisibility(View.GONE);
        tvMsg.setText(msg);
        tvMsg.setVisibility(View.VISIBLE);

    }


    @Override
    public void showOnlyStockIconActivated() {

        MenuItem item = menu.findItem(R.id.onlyInStock);
        item.setIcon(android.R.drawable.checkbox_on_background);

    }

    @Override
    public void showOnlyStockIconDeactivated() {

        MenuItem item = menu.findItem(R.id.onlyInStock);
        item.setIcon(android.R.drawable.checkbox_off_background);

    }

    @Override
    public void showAsciiDetailActivity(Ascii ascii) {

        Intent intent = new Intent(getContext(), AsciiDetailActivity.class);
        intent.putExtra(AppProperties.ASCII_KEY, ascii);
        startActivity(intent);

    }

    @Override
    public void showLoadingOnScroll() {

        ((AsciiListAdapter)recyclerView.getAdapter()).showLoading();
    }


    @Override
    public void hideLoadingOnScroll() {

        ((AsciiListAdapter)recyclerView.getAdapter()).removeLoading();

    }

    @Override
    public void showErrorLoadingScroll() {

        Toast.makeText(getActivity() , getString(R.string.error_on_loading), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private AsciiRequest getAsciiRequest(int limit, int skip, String query , boolean isOnlyInStock)
    {
        AsciiRequest asciiRequest = new AsciiRequest();
        asciiRequest.setTimestamp(new Date().getTime());
        asciiRequest.setLimit(limit);
        asciiRequest.setSkip(skip);
        asciiRequest.setQuery(query);
        asciiRequest.setOnlyInStock(isOnlyInStock);

        return asciiRequest;
    }


}
