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
public class InviteFormEnum extends StringEnum
{
    public static final String INVITETYPE_VALUE = "1INVITETYPE";
    public static final String TENDERDISCUSSION_VALUE = "2TENDERDISCUSSION";
    public static final String DIRECTCONSIGNMENT_VALUE = "3DIRECTCONSIGNMENT";

    public static final InviteFormEnum INVITETYPE = new InviteFormEnum("INVITETYPE", INVITETYPE_VALUE);
    public static final InviteFormEnum TENDERDISCUSSION = new InviteFormEnum("TENDERDISCUSSION", TENDERDISCUSSION_VALUE);
    public static final InviteFormEnum DIRECTCONSIGNMENT = new InviteFormEnum("DIRECTCONSIGNMENT", DIRECTCONSIGNMENT_VALUE);

    /**
     * construct function
     * @param String inviteFormEnum
     */
    private InviteFormEnum(String name, String inviteFormEnum)
    {
        super(name, inviteFormEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InviteFormEnum getEnum(String inviteFormEnum)
    {
        return (InviteFormEnum)getEnum(InviteFormEnum.class, inviteFormEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InviteFormEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InviteFormEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InviteFormEnum.class);
    }
}