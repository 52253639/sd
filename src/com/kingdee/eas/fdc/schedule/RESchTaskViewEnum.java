/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RESchTaskViewEnum extends StringEnum
{
    public static final String ALL_VALUE = "ALL";
    public static final String DELAY_VALUE = "DELAY";
    public static final String TODO_VALUE = "TODO";
    public static final String SHOULDCOMPLETETHISWEEK_VALUE = "SHOULDCOMPLETETHISWEEK";
    public static final String SHOULDSTARTNEXTWEEK_VALUE = "SHOULDSTARTNEXTWEEK";
    public static final String TOEVALUATION_VALUE = "TOEVALUATION";
    public static final String MILESTONE_VALUE = "MILESTONE";
    public static final String KEYTASK_VALUE = "KEYTASK";

    public static final RESchTaskViewEnum ALL = new RESchTaskViewEnum("ALL", ALL_VALUE);
    public static final RESchTaskViewEnum DELAY = new RESchTaskViewEnum("DELAY", DELAY_VALUE);
    public static final RESchTaskViewEnum TODO = new RESchTaskViewEnum("TODO", TODO_VALUE);
    public static final RESchTaskViewEnum SHOULDCOMPLETETHISWEEK = new RESchTaskViewEnum("SHOULDCOMPLETETHISWEEK", SHOULDCOMPLETETHISWEEK_VALUE);
    public static final RESchTaskViewEnum SHOULDSTARTNEXTWEEK = new RESchTaskViewEnum("SHOULDSTARTNEXTWEEK", SHOULDSTARTNEXTWEEK_VALUE);
    public static final RESchTaskViewEnum TOEVALUATION = new RESchTaskViewEnum("TOEVALUATION", TOEVALUATION_VALUE);
    public static final RESchTaskViewEnum MILESTONE = new RESchTaskViewEnum("MILESTONE", MILESTONE_VALUE);
    public static final RESchTaskViewEnum KEYTASK = new RESchTaskViewEnum("KEYTASK", KEYTASK_VALUE);

    /**
     * construct function
     * @param String rESchTaskViewEnum
     */
    private RESchTaskViewEnum(String name, String rESchTaskViewEnum)
    {
        super(name, rESchTaskViewEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RESchTaskViewEnum getEnum(String rESchTaskViewEnum)
    {
        return (RESchTaskViewEnum)getEnum(RESchTaskViewEnum.class, rESchTaskViewEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RESchTaskViewEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RESchTaskViewEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RESchTaskViewEnum.class);
    }
}