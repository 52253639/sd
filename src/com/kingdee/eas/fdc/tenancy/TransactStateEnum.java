/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TransactStateEnum extends StringEnum
{
    public static final String SUBMITLIVINGBOOK_VALUE = "SubmitLivingBook";
    public static final String SUBMITOUTBOOK_VALUE = "SubmitOutBook";
    public static final String TRANSACTOVER_VALUE = "TransactOver";

    public static final TransactStateEnum SubmitLivingBook = new TransactStateEnum("SubmitLivingBook", SUBMITLIVINGBOOK_VALUE);
    public static final TransactStateEnum SubmitOutBook = new TransactStateEnum("SubmitOutBook", SUBMITOUTBOOK_VALUE);
    public static final TransactStateEnum TransactOver = new TransactStateEnum("TransactOver", TRANSACTOVER_VALUE);

    /**
     * construct function
     * @param String transactStateEnum
     */
    private TransactStateEnum(String name, String transactStateEnum)
    {
        super(name, transactStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TransactStateEnum getEnum(String transactStateEnum)
    {
        return (TransactStateEnum)getEnum(TransactStateEnum.class, transactStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TransactStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TransactStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TransactStateEnum.class);
    }
}