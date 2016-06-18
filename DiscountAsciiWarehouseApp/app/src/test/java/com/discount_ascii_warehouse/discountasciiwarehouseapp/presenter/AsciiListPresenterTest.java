package com.discount_ascii_warehouse.discountasciiwarehouseapp.presenter;

import com.discount_ascii_warehouse.app.asciilist.AsciiListContract;
import com.discount_ascii_warehouse.app.asciilist.AsciiListPresenter;
import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.data.ascii.AsciiService;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequest;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequestRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.exceptions.MockitoLimitations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

/**
 * Created by mariobraga on 6/13/16.
 */
public class AsciiListPresenterTest {


    @Mock
    private AsciiRequestRepository asciiRequestRepository;

    @Mock
    private AsciiService asciiService;

    @Mock
    private AsciiListContract.View asciiListView;

    @Captor
    private ArgumentCaptor<AsciiService.GetAsciiServiceCallBack> asciiCallBackArgumentCaptor;

    private AsciiListPresenter asciiListPresenter;


    @Before
    public void setupAsciiListPresenter() {

        MockitoAnnotations.initMocks(this);

        asciiListPresenter = new AsciiListPresenter();
        asciiListPresenter.setAsciiListView(asciiListView);
        asciiListPresenter.setAsciiRepository(asciiRequestRepository);
        asciiListPresenter.setAsciiService(asciiService);
    }

    @Test
    public void onClickSearchAscii()
    {
        int limit = 10;
        int skip = 0;
        String query = "";
        boolean onlyInStock = false;
        String response = getFakeNDJSONReponse();

        AsciiRequest asciiRequest = getAsciiRequest(limit,skip,query, onlyInStock, response);
        List<Ascii> asciiList = getFakeAsciiList();

       asciiListPresenter.onClickSearchAscii(asciiRequest);

            verify(asciiListView).hideLoading();
            verify(asciiRequestRepository).getCachedRequestInLastHour(asciiRequest);
            verify(asciiService).getAsciiList(Mockito.eq(asciiRequest), asciiCallBackArgumentCaptor.capture());

                asciiCallBackArgumentCaptor.getValue().onAsciiLoaded(asciiList, response);

            verify(asciiRequestRepository).cacheRequest(Mockito.eq(asciiRequest));
            verify(asciiListView).hideLoading();
            verify(asciiListView).showSearchedAsciiList(Mockito.eq(asciiList));


        //get cached Request
        asciiListPresenter.onClickSearchAscii(asciiRequest);

            verify(asciiListView,times(2)).showLoading();
            verify(asciiRequestRepository, times(2)).getCachedRequestInLastHour(asciiRequest);
            verify(asciiListView).hideLoading();
            verify(asciiListView).showSearchedAsciiList(Mockito.eq(asciiList));

        asciiRequest.setQuery("list should return empty");

        asciiListPresenter.onClickSearchAscii(asciiRequest);

            verify(asciiListView, times(3)).showLoading();
            verify(asciiRequestRepository, times(3)).getCachedRequestInLastHour(asciiRequest);
            verify(asciiService, times(3)).getAsciiList(Mockito.eq(asciiRequest), asciiCallBackArgumentCaptor.capture());

                asciiList.clear();
                asciiCallBackArgumentCaptor.getValue().onAsciiLoaded(asciiList, "");

            verify(asciiRequestRepository, times(2)).cacheRequest(Mockito.eq(asciiRequest));
            verify(asciiListView,Mockito.times(2)).hideLoading();
            verify(asciiListView).showEmptyListMessage();

        asciiRequest.setQuery("should return error message");

        asciiListPresenter.onClickSearchAscii(asciiRequest);

            verify(asciiListView, Mockito.times(4)).showLoading();
            verify(asciiRequestRepository, times(4)).getCachedRequestInLastHour(asciiRequest);
            verify(asciiService, Mockito.times(4)).getAsciiList(Mockito.eq(asciiRequest),asciiCallBackArgumentCaptor.capture());

                asciiCallBackArgumentCaptor.getValue().onError("error");

            verify(asciiListView,Mockito.times(3)).hideLoading();
            verify(asciiListView).showErrorMsg(Mockito.eq("error"));

    }

    @Test
    public void onClickOnlyInStock()
    {
        int limit = 10;
        int skip = 0;
        String query = "";
        boolean onlyInStock = true;
        String response = getFakeNDJSONReponse();

        AsciiRequest asciiRequest = getAsciiRequest(limit,skip,query, onlyInStock,response);
        List<Ascii> asciiList = getFakeAsciiList();

        asciiListPresenter.onClickOnlyInStock(asciiRequest);

            verify(asciiListView).showLoading();
            verify(asciiListView).showOnlyStockIconActivated();
            verify(asciiRequestRepository).getCachedRequestInLastHour(eq(asciiRequest));
            verify(asciiService).getAsciiList(Mockito.eq(asciiRequest), asciiCallBackArgumentCaptor.capture());

                asciiCallBackArgumentCaptor.getValue().onAsciiLoaded(asciiList, "");

            verify(asciiRequestRepository).cacheRequest(eq(asciiRequest));
            verify(asciiListView).hideLoading();
            verify(asciiListView).showSearchedAsciiList(Mockito.eq(asciiList));

        asciiRequest.setOnlyInStock(false);

        asciiListPresenter.onClickOnlyInStock(asciiRequest);

            verify(asciiListView,times(2)).showLoading();
            verify(asciiListView).showOnlyStockIconDeactivated();
            verify(asciiRequestRepository, times(2)).getCachedRequestInLastHour(eq(asciiRequest));
            verify(asciiService, Mockito.times(2)).getAsciiList(Mockito.eq(asciiRequest), asciiCallBackArgumentCaptor.capture());

                asciiCallBackArgumentCaptor.getValue().onAsciiLoaded(asciiList, "");

            verify(asciiRequestRepository, times(2)).cacheRequest(eq(asciiRequest));
            verify(asciiListView,times(2)).hideLoading();
            verify(asciiListView, times(2)).showSearchedAsciiList(Mockito.eq(asciiList));

        asciiRequest.setOnlyInStock(true);

        asciiListPresenter.onClickOnlyInStock(asciiRequest);

            verify(asciiListView,times(3)).showLoading();
            verify(asciiListView, times(2)).showOnlyStockIconActivated();
            verify(asciiRequestRepository, times(3)).getCachedRequestInLastHour(eq(asciiRequest));
            verify(asciiListView,times(2)).hideLoading();
            verify(asciiListView, times(2)).showSearchedAsciiList(Mockito.eq(asciiList));


    }


    @Test
    public void onClickAscii()
    {
        Ascii ascii = getAscii();

        asciiListPresenter.onClickAscii(ascii);

        verify(asciiListView).showAsciiDetailActivity(ascii);

    }

    @Test
    public void onRefreshAsciiList()
    {

        int limit = 10;
        int skip = 0;
        String query = "";
        boolean onlyInStock = false;
        String response = getFakeNDJSONReponse();

        AsciiRequest asciiRequest = getAsciiRequest(limit,skip,query, onlyInStock,response);
        List<Ascii> asciiList = getFakeAsciiList();

        asciiListPresenter.onRefreshAsciiList(asciiRequest);

            verify(asciiRequestRepository).getCachedRequestInLastHour(asciiRequest);
            verify(asciiService).getAsciiList(Mockito.eq(asciiRequest), asciiCallBackArgumentCaptor.capture());

                asciiCallBackArgumentCaptor.getValue().onAsciiLoaded(asciiList, "");

            verify(asciiRequestRepository).cacheRequest(asciiRequest);
            verify(asciiListView).showRefreshedAsciiList(asciiList);

        asciiListPresenter.onRefreshAsciiList(asciiRequest);

            verify(asciiRequestRepository, times(2)).getCachedRequestInLastHour(asciiRequest);
            verify(asciiListView).showRefreshedAsciiList(asciiList);

        asciiRequest.setQuery("list shoudl return empty");

        asciiListPresenter.onRefreshAsciiList(asciiRequest);

            verify(asciiRequestRepository, times(3)).getCachedRequestInLastHour(asciiRequest);
            verify(asciiService, Mockito.times(3)).getAsciiList(Mockito.eq(asciiRequest), asciiCallBackArgumentCaptor.capture());

                asciiList.clear();
                asciiCallBackArgumentCaptor.getValue().onAsciiLoaded(asciiList, "");

            verify(asciiRequestRepository, times(2)).cacheRequest(asciiRequest);
            verify(asciiListView, times(2)).hideLoadingOnScroll();

        asciiRequest.setQuery("should show error");

        asciiListPresenter.onRefreshAsciiList(asciiRequest);

            verify(asciiService, Mockito.times(4)).getAsciiList(Mockito.eq(asciiRequest), asciiCallBackArgumentCaptor.capture());
            verify(asciiRequestRepository, times(4)).getCachedRequestInLastHour(asciiRequest);

                asciiCallBackArgumentCaptor.getValue().onError("error");

            verify(asciiListView, times(3)).hideLoadingOnScroll();
            verify(asciiListView).showErrorLoadingScroll();


    }


    public Ascii getAscii()
    {
        int i = 0;

        Ascii ascii = new Ascii();
        ascii.setType("type" + i );
        ascii.setId("id" + i);
        ascii.setSize(i);
        ascii.setPrice(i);
        ascii.setFace("face" + i);
        ascii.setStock(i);

        ascii.getTags().add("tag1_" + i);
        ascii.getTags().add("tag2_" + i);
        ascii.getTags().add("tag3_" + i);

        return ascii;

    }

    public List<Ascii> getFakeAsciiList()
    {
        List<Ascii> asciiList = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++)
        {

            Ascii ascii = new Ascii();
            ascii.setType("type" + i );
            ascii.setId("id" + i);
            ascii.setSize(i);
            ascii.setPrice(i);
            ascii.setFace("face" + i);
            ascii.setStock(i);

            ascii.getTags().add("tag1_" + i);
            ascii.getTags().add("tag2_" + i);
            ascii.getTags().add("tag3_" + i);


            asciiList.add(ascii);

        }

        return asciiList;

    }

    private AsciiRequest getAsciiRequest(int limit, int skip, String query , boolean isOnlyInStock, String response)
    {
        AsciiRequest asciiRequest = new AsciiRequest();
        asciiRequest.setTimestamp(new Date().getTime());
        asciiRequest.setLimit(limit);
        asciiRequest.setSkip(skip);
        asciiRequest.setQuery(query);
        asciiRequest.setOnlyInStock(isOnlyInStock);
        asciiRequest.setResponse(response);

        return asciiRequest;
    }

    public String getFakeNDJSONReponse() {
        return "{\"type\":\"face\",\"id\":\"0-4itmqrfkz6e9izfr\",\"size\":24,\"price\":379,\"face\":\"( .-. )\",\"stock\":7,\"tags\":[\"flat\",\"bored\"]}\n" +
                "{\"type\":\"face\",\"id\":\"1-vw21ngfdtn50o1or\",\"size\":19,\"price\":487,\"face\":\"( .o.)\",\"stock\":0,\"tags\":[\"suprised\",\"consectetur\"]}\n" +
                "{\"type\":\"face\",\"id\":\"2-243b4x4c3o6flxr\",\"size\":34,\"price\":501,\"face\":\"( `·´ )\",\"stock\":7,\"tags\":[\"angry\",\"cross\",\"elit\"]}\n" +
                "{\"type\":\"face\",\"id\":\"3-4xmmi1l37ukjfw29\",\"size\":15,\"price\":282,\"face\":\"( ° ͜ ʖ °)\",\"stock\":2,\"tags\":[\"happy\",\"nose\"]}\n" +
                "{\"type\":\"face\",\"id\":\"4-dv5v1dfy2ljcq5mi\",\"size\":32,\"price\":50,\"face\":\"( ͡° ͜ʖ ͡°)\",\"stock\":5,\"tags\":[\"happy\",\"nose\",\"sit\",\"consectetur\",\"sit\"]}\n" +
                "{\"type\":\"face\",\"id\":\"5-v2efv45eyeklnmi\",\"size\":22,\"price\":959,\"face\":\"( ⚆ _ ⚆ )\",\"stock\":6,\"tags\":[]}\n" +
                "{\"type\":\"face\",\"id\":\"6-si4bj477ntye3ik9\",\"size\":25,\"price\":931,\"face\":\"( ︶︿︶)\",\"stock\":6,\"tags\":[]}\n" +
                "{\"type\":\"face\",\"id\":\"7-alepfznr5p7j5rk9\",\"size\":28,\"price\":90,\"face\":\"( ﾟヮﾟ)\",\"stock\":1,\"tags\":[\"amet\"]}\n" +
                "{\"type\":\"face\",\"id\":\"8-wcayxmh1zf2sm7vi\",\"size\":35,\"price\":702,\"face\":\"(\\\\/)(°,,,°)(\\\\/)\",\"stock\":5,\"tags\":[\"zoidberg\",\"amet\"]}\n" +
                "{\"type\":\"face\",\"id\":\"9-8so9m5z5fai9hpvi\",\"size\":21,\"price\":868,\"face\":\"(¬_¬)\",\"stock\":2,\"tags\":[\"consectetur\",\"sit\",\"lorem\"]}";
    }



}
