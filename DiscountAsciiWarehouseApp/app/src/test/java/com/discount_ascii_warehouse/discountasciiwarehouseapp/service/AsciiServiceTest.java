package com.discount_ascii_warehouse.discountasciiwarehouseapp.service;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.data.ascii.AsciiService;
import com.discount_ascii_warehouse.app.data.ascii.AsciiServiceRetrofit;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by mariobraga on 6/13/16.
 */
public class AsciiServiceTest {


    AsciiService asciiService;
    private Ascii asciiFirstTest;

    @Before
    public void setupAsciiService() {

        MockitoAnnotations.initMocks(this);

        asciiService = new AsciiServiceRetrofit();

    }


    @Test
    public void getAsciiListWithNullParams() {

        try {


            final CountDownLatch signal = new CountDownLatch(1);
            asciiService.getAsciiList(new AsciiRequest(), new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList, String response) {

                    Assert.assertTrue(asciiList != null);
                    Assert.assertTrue(asciiList.size() > 0);

                    for(Ascii ascii : asciiList)
                    {
                        Assert.assertTrue(ascii.getType() != null);
                        Assert.assertTrue(ascii.getId() != null);
                        Assert.assertTrue(ascii.getSize() != 0);
                        Assert.assertTrue(ascii.getPrice() != 0);
                        Assert.assertTrue(ascii.getFace() != null);
                        Assert.assertTrue(ascii.getStock() >= 0);
                        Assert.assertTrue(ascii.getTags() != null);
                    }

                    signal.countDown();
                }

                @Override
                public void onError(String msg) {

                    Assert.fail();
                    signal.countDown();
                }
            });

            signal.await();

        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        }


    }

    @Test
    public void getAsciiListWithLimit() {

        try {

            final int limit = 100;

            final CountDownLatch signal = new CountDownLatch(1);
            asciiService.getAsciiList(getAsciiRequest(limit, 0, null, false), new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList,String response) {

                    Assert.assertTrue(asciiList != null);
                    Assert.assertTrue(asciiList.size() == limit);

                    for(Ascii ascii : asciiList)
                    {
                        Assert.assertTrue(ascii.getType() != null);
                        Assert.assertTrue(ascii.getId() != null);
                        Assert.assertTrue(ascii.getSize() != 0);
                        Assert.assertTrue(ascii.getPrice() != 0);
                        Assert.assertTrue(ascii.getFace() != null);
                        Assert.assertTrue(ascii.getStock() >= 0);
                        Assert.assertTrue(ascii.getTags() != null);
                    }

                    signal.countDown();
                }

                @Override
                public void onError(String msg) {

                    Assert.fail();
                    signal.countDown();
                }
            });

            signal.await();

        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        }


    }


    @Test
    public void getAsciiListWithSkip() {


        try {

            final CountDownLatch signal = new CountDownLatch(1);
            asciiService.getAsciiList(getAsciiRequest(3, 0, null, false), new AsciiService.GetAsciiServiceCallBack() {

                @Override
                public void onAsciiLoaded(List<Ascii> asciiList,String response) {

                    Assert.assertTrue(asciiList != null);
                    Assert.assertTrue(asciiList.size() == 3);

                    asciiFirstTest = asciiList.get(2);

                    for(Ascii ascii : asciiList)
                    {
                        Assert.assertTrue(ascii.getType() != null);
                        Assert.assertTrue(ascii.getId() != null);
                        Assert.assertTrue(ascii.getSize() != 0);
                        Assert.assertTrue(ascii.getPrice() != 0);
                        Assert.assertTrue(ascii.getFace() != null);
                        Assert.assertTrue(ascii.getStock() >= 0);
                        Assert.assertTrue(ascii.getTags() != null);


                    }

                    signal.countDown();
                }

                @Override
                public void onError(String msg) {

                    Assert.fail();
                    signal.countDown();
                }
            });

            signal.await();

            final CountDownLatch signal2 = new CountDownLatch(1);

            asciiService.getAsciiList(getAsciiRequest(4, 2, null, false), new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList,String response) {

                    Assert.assertTrue(asciiList != null);
                    Assert.assertTrue(asciiList.size() == 4);

                    for(int i = 0 ; i < asciiList.size(); i++)
                    {
                        Ascii ascii = asciiList.get(i);

                        if(i == 0)
                        {
                            Assert.assertTrue(ascii.getId().equals(asciiFirstTest.getId()));

                        }

                        Assert.assertTrue(ascii.getType() != null);
                        Assert.assertTrue(ascii.getId() != null);
                        Assert.assertTrue(ascii.getSize() != 0);
                        Assert.assertTrue(ascii.getPrice() != 0);
                        Assert.assertTrue(ascii.getFace() != null);
                        Assert.assertTrue(ascii.getStock() >= 0);
                        Assert.assertTrue(ascii.getTags() != null);
                    }

                    signal2.countDown();
                }

                @Override
                public void onError(String msg) {

                    Assert.fail();
                    signal2.countDown();
                }
            });

            signal2.await();


        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        }


    }


    @Test
    public void getAsciiListWithTags() {

        try {

            final String tag1 = "flat";
            final String tag2 = "bored";
            final String tag3 = "consectetur";
            String query = tag1 + " " + tag2 + " " + tag3;


            final CountDownLatch signal = new CountDownLatch(1);
            asciiService.getAsciiList(getAsciiRequest(100, 0, query , true), new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList,String response) {

                    Assert.assertTrue(asciiList != null);
                    Assert.assertTrue(asciiList.size() > 0);

                    for(Ascii ascii : asciiList)
                    {
                        Assert.assertTrue(ascii.getType() != null);
                        Assert.assertTrue(ascii.getId() != null);
                        Assert.assertTrue(ascii.getSize() != 0);
                        Assert.assertTrue(ascii.getPrice() != 0);
                        Assert.assertTrue(ascii.getFace() != null);
                        Assert.assertTrue(ascii.getStock() > 0);
                        Assert.assertTrue(ascii.getTags() != null);
                        Assert.assertTrue(ascii.getTags().contains(tag1) || ascii.getTags().contains(tag2) || ascii.getTags().contains(tag3));

                    }

                    signal.countDown();
                }

                @Override
                public void onError(String msg) {

                    Assert.fail();
                    signal.countDown();
                }
            });

            signal.await();

        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        }


    }


    @Test
    public void getAsciiListWithWrongTags() {

        try {

            final String tag1 = "not going to find nothing";


            final CountDownLatch signal = new CountDownLatch(1);
            asciiService.getAsciiList(getAsciiRequest(100, 0, tag1 , true), new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList,String response) {

                    Assert.assertTrue(asciiList != null);
                    Assert.assertTrue(asciiList.size() == 0);

                    signal.countDown();
                }

                @Override
                public void onError(String msg) {

                    Assert.fail();
                    signal.countDown();
                }
            });

            signal.await();

        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        }


    }

    @Test
    public void getAsciiListWithNotOnlyInStock() {

        try {

            final CountDownLatch signal = new CountDownLatch(1);
            asciiService.getAsciiList(getAsciiRequest(100, 0, null, false), new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList,String response) {

                    Assert.assertTrue(asciiList != null);
                    Assert.assertTrue(asciiList.size() > 0);

                    for(Ascii ascii : asciiList)
                    {
                        Assert.assertTrue(ascii.getType() != null);
                        Assert.assertTrue(ascii.getId() != null);
                        Assert.assertTrue(ascii.getSize() != 0);
                        Assert.assertTrue(ascii.getPrice() != 0);
                        Assert.assertTrue(ascii.getFace() != null);
                        Assert.assertTrue(ascii.getStock() >= 0);
                        Assert.assertTrue(ascii.getTags() != null);
                    }

                    signal.countDown();
                }

                @Override
                public void onError(String msg) {

                    Assert.fail();
                    signal.countDown();
                }
            });

            signal.await();

        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        }


    }


    @Test
    public void getAsciiListWithOnlyInStock() {

        try {

            final CountDownLatch signal = new CountDownLatch(1);
            asciiService.getAsciiList(getAsciiRequest(100, 0, null, true), new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList, String response) {

                    Assert.assertTrue(asciiList != null);
                    Assert.assertTrue(asciiList.size() > 0);

                    for(Ascii ascii : asciiList)
                    {
                        Assert.assertTrue(ascii.getType() != null);
                        Assert.assertTrue(ascii.getId() != null);
                        Assert.assertTrue(ascii.getSize() != 0);
                        Assert.assertTrue(ascii.getPrice() != 0);
                        Assert.assertTrue(ascii.getFace() != null);
                        Assert.assertTrue(ascii.getStock() > 0);
                        Assert.assertTrue(ascii.getTags() != null);
                    }

                    signal.countDown();
                }

                @Override
                public void onError(String msg) {

                    Assert.fail();
                    signal.countDown();
                }
            });

            signal.await();

        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        }


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
