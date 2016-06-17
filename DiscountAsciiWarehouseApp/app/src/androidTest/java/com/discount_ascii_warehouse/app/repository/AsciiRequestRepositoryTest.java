package com.discount_ascii_warehouse.app.repository;

import android.support.test.runner.AndroidJUnit4;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequest;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequestRepository;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequestRepositorySugar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import retrofit2.http.GET;

/**
 * Created by mariobraga on 6/15/16.
 */
@RunWith(AndroidJUnit4.class)
public class AsciiRequestRepositoryTest {


    private AsciiRequestRepository asciiRequestRepository;


    @Before
    public void setupAsciiListPresenter() {

        asciiRequestRepository = new AsciiRequestRepositorySugar();
        asciiRequestRepository.deleteAllCachedRequest();
    }


    @Test
    public void cacheRequest()
    {
        // SAVE

        long timestamp = new Date().getTime();
        int limit = 10;
        int skip = 0;
        String query = "";
        boolean onlyInStock = false;

        String response = getFakeNDJSONReponse();
        AsciiRequest asciiRequest = getAsciiRequest(timestamp , limit, skip, query, onlyInStock, response);

        long id = asciiRequestRepository.cacheRequest(asciiRequest);

        Assert.assertTrue( "ID is " + id, id == 1);

        //GET

       AsciiRequest asciiCachedRequest  = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest);

        Assert.assertTrue(asciiCachedRequest != null);


        Assert.assertTrue(asciiCachedRequest.getLimit() == limit);
        Assert.assertTrue(asciiCachedRequest.getSkip() == skip);
        Assert.assertTrue(asciiCachedRequest.getQuery().equals(query));
        Assert.assertTrue(asciiCachedRequest.isOnlyInStock() == onlyInStock);


        // ADD

        AsciiRequest asciiRequest2 = getAsciiRequest(timestamp , limit, skip, query, onlyInStock, response);

        long id2 = asciiRequestRepository.cacheRequest(asciiRequest2);

        Assert.assertTrue( "ID is " + id, id2 == 2);



        //DELETE


        asciiRequestRepository.deleteCachedRequest(id);

        AsciiRequest asciiCachedRequest1  = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest);

        Assert.assertTrue(asciiCachedRequest1 != null);

        asciiRequestRepository.deleteCachedRequest(id2);

        AsciiRequest asciiCachedRequest2  = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest2);

        Assert.assertTrue(asciiCachedRequest2 == null);



    }

    @Test
    public void deleteCachedRequest()
    {
        long timestamp = new Date().getTime();
        int limit = 10;
        int skip = 0;
        String query = "";
        boolean onlyInStock = false;

        String response = getFakeNDJSONReponse();
        AsciiRequest ar = getAsciiRequest(timestamp , limit, skip, query, onlyInStock, response);

        long id = asciiRequestRepository.cacheRequest(ar);

        AsciiRequest asciiRequestLastHour = asciiRequestRepository.getCachedRequestInLastHour(ar);

        Assert.assertTrue(asciiRequestLastHour != null);

        asciiRequestRepository.deleteCachedRequest(id);

        AsciiRequest asciiRequestEmpty  = asciiRequestRepository.getCachedRequestInLastHour(ar);

        Assert.assertTrue(asciiRequestEmpty == null);
    }


    @Test
    public void deleteAllCachedRequest()
    {
        // SAVE

        long timestamp = 1000;
        int limit = 10;
        int skip = 0;
        String query = "";
        boolean onlyInStock = false;

        String response = getFakeNDJSONReponse();
        AsciiRequest asciiRequest = getAsciiRequest(timestamp , limit, skip, query, onlyInStock, response);

        long id = asciiRequestRepository.cacheRequest(asciiRequest);


        long timestamp2 = new Date().getTime();
        int limit2 = 10;
        int skip2 = 0;
        String query2 = "";
        boolean onlyInStock2 = false;

        String response2 = getFakeNDJSONReponse();
        AsciiRequest asciiRequest2 = getAsciiRequest(timestamp2 , limit2, skip2, query2, onlyInStock2, response2);

        long id2 = asciiRequestRepository.cacheRequest(asciiRequest2);


        asciiRequestRepository.deleteAllCachedRequest();

        AsciiRequest asciiCachedRequestNull  = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest);

        Assert.assertTrue(asciiCachedRequestNull == null);


    }

    @Test
    public void deleteAllRequestsOlderThanOneHour()
    {
        // SAVE

        long timestamp = 1000;
        int limit = 10;
        int skip = 0;
        String query = "";
        boolean onlyInStock = false;

        String response = getFakeNDJSONReponse();
        AsciiRequest asciiRequest = getAsciiRequest(timestamp , limit, skip, query, onlyInStock, response);

        long id = asciiRequestRepository.cacheRequest(asciiRequest);

        AsciiRequest asciiCachedRequest  = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest);

        Assert.assertTrue(asciiCachedRequest == null);

        asciiRequestRepository.deleteAllRequestsOlderThanOneHour();

        AsciiRequest asciiCachedRequestNull  = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest);

        Assert.assertTrue(asciiCachedRequestNull == null);



        long timestamp2 = new Date().getTime();
        int limit2 = 10;
        int skip2 = 0;
        String query2 = "";
        boolean onlyInStock2 = false;

        String response2 = getFakeNDJSONReponse();
        AsciiRequest asciiRequest2 = getAsciiRequest(timestamp2 , limit2, skip2, query2, onlyInStock2, response2);

        long id2 = asciiRequestRepository.cacheRequest(asciiRequest2);


        AsciiRequest asciiCachedRequest2  = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest2);

        Assert.assertTrue(asciiCachedRequest2 != null);

        asciiRequestRepository.deleteAllRequestsOlderThanOneHour();

        AsciiRequest asciiCachedRequestNotNull  = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest2);

        Assert.assertTrue(asciiCachedRequestNotNull != null);

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

    public AsciiRequest getAsciiRequest(long timestamp , int limit, int skip , String query , boolean onlyInStock , String response)
    {
        AsciiRequest asciiRequest = new AsciiRequest();
        asciiRequest.setTimestamp(timestamp);
        asciiRequest.setLimit(limit);
        asciiRequest.setSkip(skip);
        asciiRequest.setQuery(query);
        asciiRequest.setOnlyInStock(onlyInStock);
        asciiRequest.setResponse(response);

        return asciiRequest;

    }
}
