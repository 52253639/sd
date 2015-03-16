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
public class CommerceSrcEnum extends StringEnum
{
    public static final String ADDBYHAND_VALUE = "addbyhand";
    public static final String CULUES_VALUE = "culues";
    public static final String TRANSACTION_VALUE = "transaction";

    public static final CommerceSrcEnum ADDBYHAND = new CommerceSrcEnum("ADDBYHAND", ADDBYHAND_VALUE);
    public static final CommerceSrcEnum CULUES = new CommerceSrcEnum("CULUES", CULUES_VALUE);
    public static final CommerceSrcEnum TRANSACTION = new CommerceSrcEnum("TRANSACTION", TRANSACTION_VALUE);

    /**
     * construct function
     * @param String commerceSrcEnum
     */
    private CommerceSrcEnum(String name, String commerceSrcEnum)
    {
        super(name, commerceSrcEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CommerceSrcEnum getEnum(String commerceSrcEnum)
    {
        return (CommerceSrcEnum)getEnum(CommerceSrcEnum.class, commerceSrcEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CommerceSrcEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CommerceSrcEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CommerceSrcEnum.class);
    }
}