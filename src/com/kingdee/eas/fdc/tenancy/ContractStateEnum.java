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
public class ContractStateEnum extends StringEnum
{
    public static final String SAVED_VALUE = "SAVED";
    public static final String SUBMITTED_VALUE = "SUBMITTED";
    public static final String AUDITED_VALUE = "Audited";
    public static final String INUSING_VALUE = "InUsing";
    public static final String STOPPED_VALUE = "Stopped";

    public static final ContractStateEnum SAVED = new ContractStateEnum("SAVED", SAVED_VALUE);
    public static final ContractStateEnum SUBMITTED = new ContractStateEnum("SUBMITTED", SUBMITTED_VALUE);
    public static final ContractStateEnum Audited = new ContractStateEnum("Audited", AUDITED_VALUE);
    public static final ContractStateEnum InUsing = new ContractStateEnum("InUsing", INUSING_VALUE);
    public static final ContractStateEnum Stopped = new ContractStateEnum("Stopped", STOPPED_VALUE);

    /**
     * construct function
     * @param String contractStateEnum
     */
    private ContractStateEnum(String name, String contractStateEnum)
    {
        super(name, contractStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractStateEnum getEnum(String contractStateEnum)
    {
        return (ContractStateEnum)getEnum(ContractStateEnum.class, contractStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractStateEnum.class);
    }
}