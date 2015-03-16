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
public class AppraiseTemplateStateEnum extends StringEnum
{
    public static final String SAVE_VALUE = "1SAVE";
    public static final String SUBMIT_VALUE = "2SUBMIT";
    public static final String AUDITED_VALUE = "3AUDITED";

    public static final AppraiseTemplateStateEnum SAVE = new AppraiseTemplateStateEnum("SAVE", SAVE_VALUE);
    public static final AppraiseTemplateStateEnum SUBMIT = new AppraiseTemplateStateEnum("SUBMIT", SUBMIT_VALUE);
    public static final AppraiseTemplateStateEnum AUDITED = new AppraiseTemplateStateEnum("AUDITED", AUDITED_VALUE);

    /**
     * construct function
     * @param String appraiseTemplateStateEnum
     */
    private AppraiseTemplateStateEnum(String name, String appraiseTemplateStateEnum)
    {
        super(name, appraiseTemplateStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AppraiseTemplateStateEnum getEnum(String appraiseTemplateStateEnum)
    {
        return (AppraiseTemplateStateEnum)getEnum(AppraiseTemplateStateEnum.class, appraiseTemplateStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AppraiseTemplateStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AppraiseTemplateStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AppraiseTemplateStateEnum.class);
    }
}