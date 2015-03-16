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
public class InvitePurchaseModeEnum extends StringEnum
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1327280782981598138L;
	public static final String SINGLE_VALUE = "1SINGLE";
    public static final String GROUP_VALUE = "2GROUP";
    public static final String STRATEGY_VALUE = "3STRATEGY";

    public static final InvitePurchaseModeEnum SINGLE = new InvitePurchaseModeEnum("SINGLE", SINGLE_VALUE);
    public static final InvitePurchaseModeEnum GROUP = new InvitePurchaseModeEnum("GROUP", GROUP_VALUE);
    public static final InvitePurchaseModeEnum STRATEGY = new InvitePurchaseModeEnum("STRATEGY", STRATEGY_VALUE);

    /**
     * construct function
     * @param String invitePurchaseModeEnum
     */
    private InvitePurchaseModeEnum(String name, String invitePurchaseModeEnum)
    {
        super(name, invitePurchaseModeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InvitePurchaseModeEnum getEnum(String invitePurchaseModeEnum)
    {
        return (InvitePurchaseModeEnum)getEnum(InvitePurchaseModeEnum.class, invitePurchaseModeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InvitePurchaseModeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InvitePurchaseModeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InvitePurchaseModeEnum.class);
    }
}