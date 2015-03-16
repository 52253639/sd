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
public class QuotingPriceStatusEnum extends StringEnum
{
    public static final String NOIMPORTPRICE_VALUE = "0";
    public static final String IMPORTEDPRICE_VALUE = "1";
    public static final String EVALUATED_VALUE = "2";
    public static final String IMPORTBASE_VALUE = "3";
    public static final String IMPORTTODB_VALUE = "4";

    public static final QuotingPriceStatusEnum NoImportPrice = new QuotingPriceStatusEnum("NoImportPrice", NOIMPORTPRICE_VALUE);
    public static final QuotingPriceStatusEnum ImportedPrice = new QuotingPriceStatusEnum("ImportedPrice", IMPORTEDPRICE_VALUE);
    public static final QuotingPriceStatusEnum Evaluated = new QuotingPriceStatusEnum("Evaluated", EVALUATED_VALUE);
    public static final QuotingPriceStatusEnum ImportBase = new QuotingPriceStatusEnum("ImportBase", IMPORTBASE_VALUE);
    public static final QuotingPriceStatusEnum ImportToDB = new QuotingPriceStatusEnum("ImportToDB", IMPORTTODB_VALUE);

    /**
     * construct function
     * @param String quotingPriceStatusEnum
     */
    private QuotingPriceStatusEnum(String name, String quotingPriceStatusEnum)
    {
        super(name, quotingPriceStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuotingPriceStatusEnum getEnum(String quotingPriceStatusEnum)
    {
        return (QuotingPriceStatusEnum)getEnum(QuotingPriceStatusEnum.class, quotingPriceStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuotingPriceStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuotingPriceStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuotingPriceStatusEnum.class);
    }
}