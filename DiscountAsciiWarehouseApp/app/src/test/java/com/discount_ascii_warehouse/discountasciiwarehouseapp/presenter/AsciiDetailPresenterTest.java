package com.discount_ascii_warehouse.discountasciiwarehouseapp.presenter;

import com.discount_ascii_warehouse.app.asciidetail.AsciiDetailContract;
import com.discount_ascii_warehouse.app.asciidetail.AsciiDetailPresenter;
import com.discount_ascii_warehouse.app.asciilist.AsciiListContract;
import com.discount_ascii_warehouse.app.asciilist.AsciiListPresenter;
import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.data.ascii.AsciiService;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequestRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

/**
 * Created by mariobraga on 6/17/16.
 */
public class AsciiDetailPresenterTest {


    @Mock
    private AsciiDetailContract.View asciiDetailView;


    private AsciiDetailPresenter asciiDetailPresenter;


    @Before
    public void setupAsciiListPresenter() {

        MockitoAnnotations.initMocks(this);

        asciiDetailPresenter = new AsciiDetailPresenter();
        asciiDetailPresenter.setAsciiDetailView(asciiDetailView);

    }


    @Test
    public void onClickOk() {

        asciiDetailPresenter.onClickOk();
        verify(asciiDetailView).showAsciiListActivity();
    }

    @Test
    public void onClickBtnBuy() {

        asciiDetailPresenter.onClickBtnBuy();
        verify(asciiDetailView).showConfirmBuyMsg();
    }

    @Test
    public void onClickYesBuy() {

        Ascii ascii = new Ascii();
        ascii.setType("face");
        ascii.setSize(20);
        ascii.setPrice(20);
        ascii.setStock(5);
        ascii.getTags().add("unit");
        ascii.getTags().add("test");

        asciiDetailPresenter.onClickYesBuy(ascii);
        verify(asciiDetailView).showBuySuccessMsg();
    }

    @Test
    public void onClickBuyNo() {

        asciiDetailPresenter.onClickBuyNo();
        verify(asciiDetailView).showBuyFailureMsg();
    }

    @Test
    public void onClickBack()
    {
        asciiDetailPresenter.onClickBack();
        verify(asciiDetailView).showAsciiListActivity();
    }

}
