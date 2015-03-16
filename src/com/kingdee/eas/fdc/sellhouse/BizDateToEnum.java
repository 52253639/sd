/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class BizDateToEnum extends StringEnum
{
    public static final String OLDBILLBIZDATE_VALUE = "OldBillBizDate";
    public static final String NEWBILLBIZDATE_VALUE = "NewBillBizDate";

    public static final BizDateToEnum OldBillBizDate = new BizDateToEnum("OldBillBizDate", OLDBILLBIZDATE_VALUE);
    public static final BizDateToEnum NewBillBizDate = new BizDateToEnum("NewBillBizDate", NEWBILLBIZDATE_VALUE);

    /**
     * construct function
     * @param String bizDateToEnum
     */
    private BizDateToEnum(String name, String bizDateToEnum)
    {
        super(name, bizDateToEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BizDateToEnum getEnum(String bizDateToEnum)
    {
        return (BizDateToEnum)getEnum(BizDateToEnum.class, bizDateToEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BizDateToEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BizDateToEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BizDateToEnum.class);
    }
}