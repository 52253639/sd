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
public class AfterServiceStateEnum extends StringEnum
{
    public static final String NODEA_VALUE = "NODEA";
    public static final String DEALING_VALUE = "DEALING";
    public static final String DEALFINISH_VALUE = "DEALFINISH";
    public static final String BANKFUND_VALUE = "BANKFUND";
    public static final String SUSPENDBYHAND_VALUE = "SUSPENDBYHAND";
    public static final String CHANGROOM_VALUE = "CHANGROOM";
    public static final String QUITROOM_VALUE = "QUITROOM";

    public static final AfterServiceStateEnum NODEA = new AfterServiceStateEnum("NODEA", NODEA_VALUE);
    public static final AfterServiceStateEnum DEALING = new AfterServiceStateEnum("DEALING", DEALING_VALUE);
    public static final AfterServiceStateEnum DEALFINISH = new AfterServiceStateEnum("DEALFINISH", DEALFINISH_VALUE);
    public static final AfterServiceStateEnum BANKFUND = new AfterServiceStateEnum("BANKFUND", BANKFUND_VALUE);
    public static final AfterServiceStateEnum SUSPENDBYHAND = new AfterServiceStateEnum("SUSPENDBYHAND", SUSPENDBYHAND_VALUE);
    public static final AfterServiceStateEnum CHANGROOM = new AfterServiceStateEnum("CHANGROOM", CHANGROOM_VALUE);
    public static final AfterServiceStateEnum QUITROOM = new AfterServiceStateEnum("QUITROOM", QUITROOM_VALUE);

    /**
     * construct function
     * @param String afterServiceStateEnum
     */
    private AfterServiceStateEnum(String name, String afterServiceStateEnum)
    {
        super(name, afterServiceStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AfterServiceStateEnum getEnum(String afterServiceStateEnum)
    {
        return (AfterServiceStateEnum)getEnum(AfterServiceStateEnum.class, afterServiceStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AfterServiceStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AfterServiceStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AfterServiceStateEnum.class);
    }
}