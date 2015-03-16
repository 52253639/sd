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
public class InvitePurchaseAuthorization extends StringEnum
{
    public static final String SECTIONINNER_VALUE = "4SECTIONINNER";
    public static final String SECTIONOUTERDIVIINNER_VALUE = "5SECTIONOUTERDIVIINNER";
    public static final String DIVISIONOUTERBUSDIVINNER_VALUE = "6DIVISIONOUTERBUSDIVINNER";
    public static final String OUTTER_VALUE = "2OUTTER";
    public static final String COMINNER_VALUE = "3COMINNER";
    public static final String PROJECTRESPBUSDIVINNER_VALUE = "7PROJECTRESPBUSDIVINNER";
    public static final String INNER_VALUE = "1INNER";
    public static final String INNERCOMPANY_VALUE = "8INNERCOMPANY";
    public static final String OUTERCOMPANY_VALUE = "9OUTERCOMPANY";

    public static final InvitePurchaseAuthorization SECTIONINNER = new InvitePurchaseAuthorization("SECTIONINNER", SECTIONINNER_VALUE);
    public static final InvitePurchaseAuthorization SECTIONOUTERDIVIINNER = new InvitePurchaseAuthorization("SECTIONOUTERDIVIINNER", SECTIONOUTERDIVIINNER_VALUE);
    public static final InvitePurchaseAuthorization DIVISIONOUTERBUSDIVINNER = new InvitePurchaseAuthorization("DIVISIONOUTERBUSDIVINNER", DIVISIONOUTERBUSDIVINNER_VALUE);
    public static final InvitePurchaseAuthorization OUTTER = new InvitePurchaseAuthorization("OUTTER", OUTTER_VALUE);
    public static final InvitePurchaseAuthorization COMINNER = new InvitePurchaseAuthorization("COMINNER", COMINNER_VALUE);
    public static final InvitePurchaseAuthorization PROJECTRESPBUSDIVINNER = new InvitePurchaseAuthorization("PROJECTRESPBUSDIVINNER", PROJECTRESPBUSDIVINNER_VALUE);
    public static final InvitePurchaseAuthorization INNER = new InvitePurchaseAuthorization("INNER", INNER_VALUE);
    public static final InvitePurchaseAuthorization INNERCOMPANY = new InvitePurchaseAuthorization("INNERCOMPANY", INNERCOMPANY_VALUE);
    public static final InvitePurchaseAuthorization OUTERCOMPANY = new InvitePurchaseAuthorization("OUTERCOMPANY", OUTERCOMPANY_VALUE);

    /**
     * construct function
     * @param String invitePurchaseAuthorization
     */
    private InvitePurchaseAuthorization(String name, String invitePurchaseAuthorization)
    {
        super(name, invitePurchaseAuthorization);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InvitePurchaseAuthorization getEnum(String invitePurchaseAuthorization)
    {
        return (InvitePurchaseAuthorization)getEnum(InvitePurchaseAuthorization.class, invitePurchaseAuthorization);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InvitePurchaseAuthorization.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InvitePurchaseAuthorization.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InvitePurchaseAuthorization.class);
    }
}