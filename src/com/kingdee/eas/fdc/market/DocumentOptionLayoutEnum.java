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
public class DocumentOptionLayoutEnum extends IntEnum
{
    public static final int ALIGN_TYPE_FLOW_VALUE = 1;
    public static final int ALIGN_TYPE_LINE_VALUE = 2;

    public static final DocumentOptionLayoutEnum ALIGN_TYPE_FLOW = new DocumentOptionLayoutEnum("ALIGN_TYPE_FLOW", ALIGN_TYPE_FLOW_VALUE);
    public static final DocumentOptionLayoutEnum ALIGN_TYPE_LINE = new DocumentOptionLayoutEnum("ALIGN_TYPE_LINE", ALIGN_TYPE_LINE_VALUE);

    /**
     * construct function
     * @param integer documentOptionLayoutEnum
     */
    private DocumentOptionLayoutEnum(String name, int documentOptionLayoutEnum)
    {
        super(name, documentOptionLayoutEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DocumentOptionLayoutEnum getEnum(String documentOptionLayoutEnum)
    {
        return (DocumentOptionLayoutEnum)getEnum(DocumentOptionLayoutEnum.class, documentOptionLayoutEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static DocumentOptionLayoutEnum getEnum(int documentOptionLayoutEnum)
    {
        return (DocumentOptionLayoutEnum)getEnum(DocumentOptionLayoutEnum.class, documentOptionLayoutEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DocumentOptionLayoutEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DocumentOptionLayoutEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DocumentOptionLayoutEnum.class);
    }
}