package com.discount_ascii_warehouse.app.utils;


import com.discount_ascii_warehouse.app.data.ascii.FakeAsciiServiceImpl;

/**
 * Created by mariobraga on 6/13/16.
 */
public class Injection {

    public static AsciiService getAsciiService()
    {
        return new FakeAsciiServiceImpl();
    }

    public static AsciiRequestRepository getAsciiRequestRepository()
    {
        return new FakeAsciiRequestRepositoryImpl();
    }
}
