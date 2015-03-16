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
public class InteractionTypeEnum extends StringEnum
{
    public static final String TELEPHONE_VALUE = "TELEPHONE";
    public static final String TOTELEPHONE_VALUE = "TOTELEPHONE";
    public static final String INTERVIEW_VALUE = "INTERVIEW";
    public static final String EDIT_VALUE = "EDIT";
    public static final String TRAVEL_VALUE = "TRAVEL";

    public static final InteractionTypeEnum TELEPHONE = new InteractionTypeEnum("TELEPHONE", TELEPHONE_VALUE);
    public static final InteractionTypeEnum TOTELEPHONE = new InteractionTypeEnum("TOTELEPHONE", TOTELEPHONE_VALUE);
    public static final InteractionTypeEnum INTERVIEW = new InteractionTypeEnum("INTERVIEW", INTERVIEW_VALUE);
    public static final InteractionTypeEnum EDIT = new InteractionTypeEnum("EDIT", EDIT_VALUE);
    public static final InteractionTypeEnum TRAVEL = new InteractionTypeEnum("TRAVEL", TRAVEL_VALUE);

    /**
     * construct function
     * @param String interactionTypeEnum
     */
    private InteractionTypeEnum(String name, String interactionTypeEnum)
    {
        super(name, interactionTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InteractionTypeEnum getEnum(String interactionTypeEnum)
    {
        return (InteractionTypeEnum)getEnum(InteractionTypeEnum.class, interactionTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InteractionTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InteractionTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InteractionTypeEnum.class);
    }
}