/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class DocumentOptionHorizonLayoutEnum extends IntEnum
{
    public static final int HORIZONTAL_ALIGN_CENTER_VALUE = 1;
    public static final int HORIZONTAL_ALIGN_LEFT_VALUE = 2;

    public static final DocumentOptionHorizonLayoutEnum HORIZONTAL_ALIGN_CENTER = new DocumentOptionHorizonLayoutEnum("HORIZONTAL_ALIGN_CENTER", HORIZONTAL_ALIGN_CENTER_VALUE);
    public static final DocumentOptionHorizonLayoutEnum HORIZONTAL_ALIGN_LEFT = new DocumentOptionHorizonLayoutEnum("HORIZONTAL_ALIGN_LEFT", HORIZONTAL_ALIGN_LEFT_VALUE);

    /**
     * construct function
     * @param integer documentOptionHorizonLayoutEnum
     */
    private DocumentOptionHorizonLayoutEnum(String name, int documentOptionHorizonLayoutEnum)
    {
        super(name, documentOptionHorizonLayoutEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DocumentOptionHorizonLayoutEnum getEnum(String documentOptionHorizonLayoutEnum)
    {
        return (DocumentOptionHorizonLayoutEnum)getEnum(DocumentOptionHorizonLayoutEnum.class, documentOptionHorizonLayoutEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static DocumentOptionHorizonLayoutEnum getEnum(int documentOptionHorizonLayoutEnum)
    {
        return (DocumentOptionHorizonLayoutEnum)getEnum(DocumentOptionHorizonLayoutEnum.class, documentOptionHorizonLayoutEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DocumentOptionHorizonLayoutEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DocumentOptionHorizonLayoutEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DocumentOptionHorizonLayoutEnum.class);
    }
}