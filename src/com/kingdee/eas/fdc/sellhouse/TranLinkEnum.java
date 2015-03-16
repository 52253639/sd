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
public class TranLinkEnum extends StringEnum
{
    public static final String BAYNUMBER_VALUE = "BayNumber";
    public static final String PREDETERMINE_VALUE = "Predetermine";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";

    public static final TranLinkEnum BAYNUMBER = new TranLinkEnum("BAYNUMBER", BAYNUMBER_VALUE);
    public static final TranLinkEnum PREDETERMINE = new TranLinkEnum("PREDETERMINE", PREDETERMINE_VALUE);
    public static final TranLinkEnum PURCHASE = new TranLinkEnum("PURCHASE", PURCHASE_VALUE);
    public static final TranLinkEnum SIGN = new TranLinkEnum("SIGN", SIGN_VALUE);

    /**
     * construct function
     * @param String tranLinkEnum
     */
    private TranLinkEnum(String name, String tranLinkEnum)
    {
        super(name, tranLinkEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TranLinkEnum getEnum(String tranLinkEnum)
    {
        return (TranLinkEnum)getEnum(TranLinkEnum.class, tranLinkEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TranLinkEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TranLinkEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TranLinkEnum.class);
    }
}