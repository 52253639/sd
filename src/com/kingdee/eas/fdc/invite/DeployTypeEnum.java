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
public class DeployTypeEnum extends StringEnum
{
    public static final String SETTLEBALANCES_VALUE = "10";
    public static final String SUPPORTINGBALANCES_VALUE = "20";
    public static final String UNKNOWNFEES_VALUE = "30";
    public static final String UNSTARTPROJECT_VALUE = "40";

    public static final DeployTypeEnum SettleBalances = new DeployTypeEnum("SettleBalances", SETTLEBALANCES_VALUE);
    public static final DeployTypeEnum SupportingBalances = new DeployTypeEnum("SupportingBalances", SUPPORTINGBALANCES_VALUE);
    public static final DeployTypeEnum UnKnownFees = new DeployTypeEnum("UnKnownFees", UNKNOWNFEES_VALUE);
    public static final DeployTypeEnum UnStartProject = new DeployTypeEnum("UnStartProject", UNSTARTPROJECT_VALUE);

    /**
     * construct function
     * @param String deployTypeEnum
     */
    private DeployTypeEnum(String name, String deployTypeEnum)
    {
        super(name, deployTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DeployTypeEnum getEnum(String deployTypeEnum)
    {
        return (DeployTypeEnum)getEnum(DeployTypeEnum.class, deployTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DeployTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DeployTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DeployTypeEnum.class);
    }
}