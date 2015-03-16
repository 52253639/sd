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
public class CommercialStageEnum extends StringEnum
{
    public static final String NOTCOLLECTED_VALUE = "NotCollected";
    public static final String CHANGE_VALUE = "Change";
    public static final String AUDIT_VALUE = "Audit";
    public static final String LOANS_VALUE = "Loans";
    public static final String WAITING_VALUE = "Waiting";
    public static final String DJY_VALUE = "DJY";
    public static final String NOCONTRACT_VALUE = "NOCONTRACT";
    public static final String QUIDT_VALUE = "QUIDT";

    public static final CommercialStageEnum NotCollected = new CommercialStageEnum("NotCollected", NOTCOLLECTED_VALUE);
    public static final CommercialStageEnum Change = new CommercialStageEnum("Change", CHANGE_VALUE);
    public static final CommercialStageEnum Audit = new CommercialStageEnum("Audit", AUDIT_VALUE);
    public static final CommercialStageEnum Loans = new CommercialStageEnum("Loans", LOANS_VALUE);
    public static final CommercialStageEnum Waiting = new CommercialStageEnum("Waiting", WAITING_VALUE);
    public static final CommercialStageEnum DJY = new CommercialStageEnum("DJY", DJY_VALUE);
    public static final CommercialStageEnum NOCONTRACT = new CommercialStageEnum("NOCONTRACT", NOCONTRACT_VALUE);
    public static final CommercialStageEnum QUIDT = new CommercialStageEnum("QUIDT", QUIDT_VALUE);

    /**
     * construct function
     * @param String commercialStageEnum
     */
    private CommercialStageEnum(String name, String commercialStageEnum)
    {
        super(name, commercialStageEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CommercialStageEnum getEnum(String commercialStageEnum)
    {
        return (CommercialStageEnum)getEnum(CommercialStageEnum.class, commercialStageEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CommercialStageEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CommercialStageEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CommercialStageEnum.class);
    }
}