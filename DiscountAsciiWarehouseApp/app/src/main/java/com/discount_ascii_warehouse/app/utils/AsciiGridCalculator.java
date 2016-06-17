package com.discount_ascii_warehouse.app.utils;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.discountasciiwarehouseapp.R;

import java.util.List;

/**
 * Created by mariobraga on 6/14/16.
 */
public class AsciiGridCalculator {

    public static int calculateCardSpan(Context context, List<Ascii> asciiList, int position) {

        int numberColumns           = getNumberColumns(context);
        int totalSizeOfAsciiInRow   = getTotalSizeOfAsciiInRow(asciiList, position, numberColumns);
        int span                    = getSpan(asciiList, position, numberColumns, totalSizeOfAsciiInRow);

        return span;

    }

    private static int getSpan(List<Ascii> asciiList, int position, int numberColumns, int totalSizeOfAsciiInRow) {

        if(isLastPositionOfColumn(position, numberColumns))
        {
            return calculateSpanForLastColumn(asciiList, position, numberColumns, totalSizeOfAsciiInRow);
        }
        else
        {
            return calculateSpan(asciiList, position, totalSizeOfAsciiInRow);
        }

    }

    private static int calculateSpan(List<Ascii> asciiList, int position, int totalSizeOfAsciiInRow) {

        Ascii ascii = asciiList.get(position);
        int asciiNumberSize = ascii.getFace().length() * ascii.getSize();
        double porcentOfCardWidthOccupation = (asciiNumberSize * 1.0) / totalSizeOfAsciiInRow * 10000;
        int porcentSpan =   ((int) porcentOfCardWidthOccupation) / 100;
        return porcentSpan;
    }

    private static int calculateSpanForLastColumn(List<Ascii> asciiList, int position, int numberColumns, int totalSizeOfAsciiInRow) {
        int porcentSpanOccupied = 0;

        for(int i = 1 ; i < numberColumns ; i++) {

            if(asciiList.size() > (position - i)) {
                Ascii ascii = asciiList.get(position - i);
                int asciiNumberSize = ascii.getFace().length() * ascii.getSize();
                double porcentOfCardWidthOccupation = (asciiNumberSize * 1.0) / totalSizeOfAsciiInRow * 10000;
                int porcentSpanRounded =   ((int) porcentOfCardWidthOccupation) / 100;
                porcentSpanOccupied += porcentSpanRounded;
            }
        }

        int porcentSpan =   100 - porcentSpanOccupied;

        while ( (100 - porcentSpanOccupied - porcentSpan != 0))
        {
            porcentSpan += 1;
        }

        return  porcentSpan;
    }

    private static int getNumberColumns(Context context) {
        int numberColumns = 0;
        int screenWidth = ScreenUtils.getScreenWidth(context);

        if(screenWidth > 800)
        {
            numberColumns = 3;
        }
        else
        {
            numberColumns = 2;
        }
        return numberColumns;
    }


    private static int getTotalSizeOfAsciiInRow(List<Ascii> asciiList, int position, int numberColumns) {

        int rowPositionOfCardView    = position / numberColumns ;
        int firstCardPositionInRow   = rowPositionOfCardView * numberColumns;

        int totalSizeOfAsciiInRow = 0;

        for(int i = firstCardPositionInRow ; i < firstCardPositionInRow + numberColumns ; i++ )
        {
            if(asciiList.size() > i) {
                totalSizeOfAsciiInRow += asciiList.get(i).getFace().length() * asciiList.get(i).getSize();
            }
        }
        return totalSizeOfAsciiInRow;
    }


    public static boolean isLastPositionOfColumn(int position, int numberColumns) {

        if(position > 0 && (position + 1) % numberColumns == 0)
        {
            return true;
        }

        return false;
    }

    public static int calculateLimit(Context context)
    {
        int screenHeightPx         = ScreenUtils.getScreenHeight(context) - getActionBarHeight(context);
        float cardViewHeightPx     = context.getResources().getDimension(R.dimen.ascii_cardview_height);
        int numberOfColumns        = getNumberColumns(context);

        double limit = (screenHeightPx / cardViewHeightPx) * numberOfColumns ;

        if(limit % numberOfColumns > (numberOfColumns * 1.0) / 2)
        {
            limit = limit - (limit % numberOfColumns) + numberOfColumns;
        }
        else
        {
            limit = limit - (limit % numberOfColumns);
        }


        return (int) limit;
    }

    private static int getActionBarHeight(Context context)
    {
        int actionBarHeight = 0;

        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }

        return actionBarHeight;
    }


}
