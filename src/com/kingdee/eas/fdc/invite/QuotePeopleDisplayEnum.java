/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class QuotePeopleDisplayEnum extends StringEnum
{
    public static final String BIDDERFULLNAME_VALUE = "1";
    public static final String BIDDERNUMBER_VALUE = "2";

    public static final QuotePeopleDisplayEnum BidderFullName = new QuotePeopleDisplayEnum("BidderFullName", BIDDERFULLNAME_VALUE);
    public static final QuotePeopleDisplayEnum BidderNumber = new QuotePeopleDisplayEnum("BidderNumber", BIDDERNUMBER_VALUE);

    /**
     * construct function
     * @param String quotePeopleDisplayEnum
     */
    private QuotePeopleDisplayEnum(String name, String quotePeopleDisplayEnum)
    {
        super(name, quotePeopleDisplayEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuotePeopleDisplayEnum getEnum(String quotePeopleDisplayEnum)
    {
        return (QuotePeopleDisplayEnum)getEnum(QuotePeopleDisplayEnum.class, quotePeopleDisplayEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuotePeopleDisplayEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuotePeopleDisplayEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuotePeopleDisplayEnum.class);
    }
}