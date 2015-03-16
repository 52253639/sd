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
public class InviteFileItemTypeEnum extends StringEnum
{
    public static final String TECHCLAUSE_VALUE = "1TECHCLAUSE";
    public static final String BLUEPRINTCONTENT_VALUE = "2BLUEPRINTCONTENT";
    public static final String INVITEINSTRUCTION_VALUE = "3INVITEINSTRUCTION";
    public static final String CONTRACTCLAUSE_VALUE = "4CONTRACTCLAUSE";
    public static final String INVITELISTOFITEM_VALUE = "5INVITELISTOFITEM";

    public static final InviteFileItemTypeEnum TECHCLAUSE = new InviteFileItemTypeEnum("TECHCLAUSE", TECHCLAUSE_VALUE);
    public static final InviteFileItemTypeEnum BLUEPRINTCONTENT = new InviteFileItemTypeEnum("BLUEPRINTCONTENT", BLUEPRINTCONTENT_VALUE);
    public static final InviteFileItemTypeEnum INVITEINSTRUCTION = new InviteFileItemTypeEnum("INVITEINSTRUCTION", INVITEINSTRUCTION_VALUE);
    public static final InviteFileItemTypeEnum CONTRACTCLAUSE = new InviteFileItemTypeEnum("CONTRACTCLAUSE", CONTRACTCLAUSE_VALUE);
    public static final InviteFileItemTypeEnum INVITELISTOFITEM = new InviteFileItemTypeEnum("INVITELISTOFITEM", INVITELISTOFITEM_VALUE);

    /**
     * construct function
     * @param String inviteFileItemTypeEnum
     */
    private InviteFileItemTypeEnum(String name, String inviteFileItemTypeEnum)
    {
        super(name, inviteFileItemTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InviteFileItemTypeEnum getEnum(String inviteFileItemTypeEnum)
    {
        return (InviteFileItemTypeEnum)getEnum(InviteFileItemTypeEnum.class, inviteFileItemTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InviteFileItemTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InviteFileItemTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InviteFileItemTypeEnum.class);
    }
}