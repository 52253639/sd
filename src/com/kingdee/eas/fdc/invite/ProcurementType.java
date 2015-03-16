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
public class ProcurementType extends StringEnum
{
    public static final String ONE_VALUE = "1ONE";
    public static final String TWO_VALUE = "2TWO";
    public static final String THREE_VALUE = "3THREE";

    public static final ProcurementType ONE = new ProcurementType("ONE", ONE_VALUE);
    public static final ProcurementType TWO = new ProcurementType("TWO", TWO_VALUE);
    public static final ProcurementType THREE = new ProcurementType("THREE", THREE_VALUE);

    /**
     * construct function
     * @param String procurementType
     */
    private ProcurementType(String name, String procurementType)
    {
        super(name, procurementType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ProcurementType getEnum(String procurementType)
    {
        return (ProcurementType)getEnum(ProcurementType.class, procurementType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ProcurementType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ProcurementType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ProcurementType.class);
    }
}