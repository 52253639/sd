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
public class InviteProjectStepEnum extends StringEnum
{
    public static final String LOCATIONSTEP_VALUE = "1LocationStep";
    public static final String SCHEMESTEP_VALUE = "2SchemeStep";
    public static final String CONSTRUCTIONSTEP_VALUE = "3ConstructionStep";

    public static final InviteProjectStepEnum LocationStep = new InviteProjectStepEnum("LocationStep", LOCATIONSTEP_VALUE);
    public static final InviteProjectStepEnum SchemeStep = new InviteProjectStepEnum("SchemeStep", SCHEMESTEP_VALUE);
    public static final InviteProjectStepEnum ConstructionStep = new InviteProjectStepEnum("ConstructionStep", CONSTRUCTIONSTEP_VALUE);

    /**
     * construct function
     * @param String inviteProjectStepEnum
     */
    private InviteProjectStepEnum(String name, String inviteProjectStepEnum)
    {
        super(name, inviteProjectStepEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InviteProjectStepEnum getEnum(String inviteProjectStepEnum)
    {
        return (InviteProjectStepEnum)getEnum(InviteProjectStepEnum.class, inviteProjectStepEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InviteProjectStepEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InviteProjectStepEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InviteProjectStepEnum.class);
    }
}