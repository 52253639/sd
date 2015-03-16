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
public class CommerceChangeNewStatusEnum extends StringEnum
{
    public static final String ACTIVE_VALUE = "active";
    public static final String TRANSACTION_VALUE = "transaction";
    public static final String CLOSE_VALUE = "close";
    public static final String END_VALUE = "end";

    public static final CommerceChangeNewStatusEnum ACTIVE = new CommerceChangeNewStatusEnum("ACTIVE", ACTIVE_VALUE);
    public static final CommerceChangeNewStatusEnum TRANSACTION = new CommerceChangeNewStatusEnum("TRANSACTION", TRANSACTION_VALUE);
    public static final CommerceChangeNewStatusEnum CLOSE = new CommerceChangeNewStatusEnum("CLOSE", CLOSE_VALUE);
    public static final CommerceChangeNewStatusEnum END = new CommerceChangeNewStatusEnum("END", END_VALUE);

    /**
     * construct function
     * @param String commerceChangeNewStatusEnum
     */
    private CommerceChangeNewStatusEnum(String name, String commerceChangeNewStatusEnum)
    {
        super(name, commerceChangeNewStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CommerceChangeNewStatusEnum getEnum(String commerceChangeNewStatusEnum)
    {
        return (CommerceChangeNewStatusEnum)getEnum(CommerceChangeNewStatusEnum.class, commerceChangeNewStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CommerceChangeNewStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CommerceChangeNewStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CommerceChangeNewStatusEnum.class);
    }
}