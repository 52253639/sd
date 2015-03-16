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
public class CusRepeatRuleEnum extends StringEnum
{
    public static final String INFO_VALUE = "INFO";
    public static final String FORBITTEN_VALUE = "FORBITTEN";
    public static final String NOTHING_VALUE = "NOTHING";

    public static final CusRepeatRuleEnum INFO = new CusRepeatRuleEnum("INFO", INFO_VALUE);
    public static final CusRepeatRuleEnum FORBITTEN = new CusRepeatRuleEnum("FORBITTEN", FORBITTEN_VALUE);
    public static final CusRepeatRuleEnum NOTHING = new CusRepeatRuleEnum("NOTHING", NOTHING_VALUE);

    /**
     * construct function
     * @param String cusRepeatRuleEnum
     */
    private CusRepeatRuleEnum(String name, String cusRepeatRuleEnum)
    {
        super(name, cusRepeatRuleEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CusRepeatRuleEnum getEnum(String cusRepeatRuleEnum)
    {
        return (CusRepeatRuleEnum)getEnum(CusRepeatRuleEnum.class, cusRepeatRuleEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CusRepeatRuleEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CusRepeatRuleEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CusRepeatRuleEnum.class);
    }
}