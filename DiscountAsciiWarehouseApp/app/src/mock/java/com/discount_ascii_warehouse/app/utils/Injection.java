package com.discount_ascii_warehouse.app.utils;


import com.discount_ascii_warehouse.app.data.ascii.AsciiService;
import com.discount_ascii_warehouse.app.data.ascii.FakeAsciiServiceImpl;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequestRepository;
import com.discount_ascii_warehouse.app.data.asciirequest.FakeAsciiRequestRepositoryImpl;

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
