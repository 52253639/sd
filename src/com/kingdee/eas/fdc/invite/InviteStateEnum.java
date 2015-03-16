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
public class InviteStateEnum extends StringEnum
{
    public static final String SAVED_VALUE = "1SAVED";
    public static final String SUBMITTED_VALUE = "2SUBMITTED";
    public static final String AUDITTING_VALUE = "3AUDITTING";
    public static final String AUDITTED_VALUE = "4AUDITTED";
    public static final String STARTED_VALUE = "5STARTED";
    public static final String EXECUTE_VALUE = "6EXECUTE";
    public static final String CLOSED_VALUE = "7CLOSED";

    public static final InviteStateEnum SAVED = new InviteStateEnum("SAVED", SAVED_VALUE);
    public static final InviteStateEnum SUBMITTED = new InviteStateEnum("SUBMITTED", SUBMITTED_VALUE);
    public static final InviteStateEnum AUDITTING = new InviteStateEnum("AUDITTING", AUDITTING_VALUE);
    public static final InviteStateEnum AUDITTED = new InviteStateEnum("AUDITTED", AUDITTED_VALUE);
    public static final InviteStateEnum STARTED = new InviteStateEnum("STARTED", STARTED_VALUE);
    public static final InviteStateEnum EXECUTE = new InviteStateEnum("EXECUTE", EXECUTE_VALUE);
    public static final InviteStateEnum CLOSED = new InviteStateEnum("CLOSED", CLOSED_VALUE);

    /**
     * construct function
     * @param String inviteStateEnum
     */
    private InviteStateEnum(String name, String inviteStateEnum)
    {
        super(name, inviteStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InviteStateEnum getEnum(String inviteStateEnum)
    {
        return (InviteStateEnum)getEnum(InviteStateEnum.class, inviteStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InviteStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InviteStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InviteStateEnum.class);
    }
}