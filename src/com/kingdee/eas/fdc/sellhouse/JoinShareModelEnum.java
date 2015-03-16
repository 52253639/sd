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
public class JoinShareModelEnum extends StringEnum
{
    public static final String SHAREPPMUSER_VALUE = "SharePPMUser";
    public static final String SHAREPPMMARKET_VALUE = "SharePPMMarket";
    public static final String SHAREPPMORGUNIT_VALUE = "SharePPMOrgUnit";

    public static final JoinShareModelEnum SharePPMUser = new JoinShareModelEnum("SharePPMUser", SHAREPPMUSER_VALUE);
    public static final JoinShareModelEnum SharePPMMarket = new JoinShareModelEnum("SharePPMMarket", SHAREPPMMARKET_VALUE);
    public static final JoinShareModelEnum SharePPMOrgUnit = new JoinShareModelEnum("SharePPMOrgUnit", SHAREPPMORGUNIT_VALUE);

    /**
     * construct function
     * @param String joinShareModelEnum
     */
    private JoinShareModelEnum(String name, String joinShareModelEnum)
    {
        super(name, joinShareModelEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static JoinShareModelEnum getEnum(String joinShareModelEnum)
    {
        return (JoinShareModelEnum)getEnum(JoinShareModelEnum.class, joinShareModelEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(JoinShareModelEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(JoinShareModelEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(JoinShareModelEnum.class);
    }
}