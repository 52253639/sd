/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AppraisePhaseEnum extends StringEnum
{
    public static final String QUAL_VALUE = "1";
    public static final String EVAL_VALUE = "2";
    public static final String DEADLINE_VALUE = "3";

    public static final AppraisePhaseEnum qual = new AppraisePhaseEnum("qual", QUAL_VALUE);
    public static final AppraisePhaseEnum eval = new AppraisePhaseEnum("eval", EVAL_VALUE);
    public static final AppraisePhaseEnum deadline = new AppraisePhaseEnum("deadline", DEADLINE_VALUE);

    /**
     * construct function
     * @param String appraisePhaseEnum
     */
    private AppraisePhaseEnum(String name, String appraisePhaseEnum)
    {
        super(name, appraisePhaseEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AppraisePhaseEnum getEnum(String appraisePhaseEnum)
    {
        return (AppraisePhaseEnum)getEnum(AppraisePhaseEnum.class, appraisePhaseEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AppraisePhaseEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AppraisePhaseEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AppraisePhaseEnum.class);
    }
}