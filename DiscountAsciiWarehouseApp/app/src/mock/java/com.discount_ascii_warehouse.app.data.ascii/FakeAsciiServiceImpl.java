package com.discount_ascii_warehouse.app.data.ascii;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.data.ascii.AsciiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mariobraga on 6/13/16.
 */
public class FakeAsciiServiceImpl implements AsciiService {



    @Override
    public void getAsciiList(AsciiRequest asciiRequest, @NonNull AsciiRepository.GetAsciiCallBack callBack) throws Exception {

        List<Ascii> asciiList = getFakeAsciiList(skip, limit);
        callBack.onAsciiLoaded(asciiList);

    }

    public List<Ascii> getFakeAsciiList(@Nullable Integer skip, @Nullable  Integer limit)
    {
        List<Ascii> asciiList = new ArrayList<>();

        limit = limit == null ? 10 : limit;
        skip  = skip == null  ? 0 : skip;

        for(int i = skip ; i < (limit + skip) ; i++)
        {

            Ascii ascii = new Ascii();
            ascii.setType("type" + i );
            ascii.setId("id" + i);
            ascii.setSize((new Random().nextInt(100 - 10)) + 10);
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

}
