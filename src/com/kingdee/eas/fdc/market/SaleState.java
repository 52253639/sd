/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class SaleState extends StringEnum
{
    public static final String PRESALE_VALUE = "0";
    public static final String SELLING_VALUE = "1";
    public static final String SOLD_VALUE = "2";

    public static final SaleState preSale = new SaleState("preSale", PRESALE_VALUE);
    public static final SaleState selling = new SaleState("selling", SELLING_VALUE);
    public static final SaleState sold = new SaleState("sold", SOLD_VALUE);

    /**
     * construct function
     * @param String saleState
     */
    private SaleState(String name, String saleState)
    {
        super(name, saleState);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SaleState getEnum(String saleState)
    {
        return (SaleState)getEnum(SaleState.class, saleState);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SaleState.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SaleState.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SaleState.class);
    }
}