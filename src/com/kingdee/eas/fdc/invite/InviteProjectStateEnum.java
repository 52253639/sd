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
public class InviteProjectStateEnum extends StringEnum
{
    public static final String SAVED_VALUE = "1SAVED";
    public static final String SUBMITED_VALUE = "2SUBMITED";
    public static final String AUDITTING_VALUE = "3AUDITTING";
    public static final String AUDITTED_VALUE = "4AUDITTED";
    public static final String FILEMAKING_VALUE = "5FILEMAKING";
    public static final String ANSWERING_VALUE = "6ANSWERING";
    public static final String APPRAISING_VALUE = "7APPRAISING";
    public static final String FIXED_VALUE = "8FIXED";
    public static final String SIGNEDCONTRACT_VALUE = "9SIGNEDCONTRACT";

    public static final InviteProjectStateEnum SAVED = new InviteProjectStateEnum("SAVED", SAVED_VALUE);
    public static final InviteProjectStateEnum SUBMITED = new InviteProjectStateEnum("SUBMITED", SUBMITED_VALUE);
    public static final InviteProjectStateEnum AUDITTING = new InviteProjectStateEnum("AUDITTING", AUDITTING_VALUE);
    public static final InviteProjectStateEnum AUDITTED = new InviteProjectStateEnum("AUDITTED", AUDITTED_VALUE);
    public static final InviteProjectStateEnum FILEMAKING = new InviteProjectStateEnum("FILEMAKING", FILEMAKING_VALUE);
    public static final InviteProjectStateEnum ANSWERING = new InviteProjectStateEnum("ANSWERING", ANSWERING_VALUE);
    public static final InviteProjectStateEnum APPRAISING = new InviteProjectStateEnum("APPRAISING", APPRAISING_VALUE);
    public static final InviteProjectStateEnum FIXED = new InviteProjectStateEnum("FIXED", FIXED_VALUE);
    public static final InviteProjectStateEnum SIGNEDCONTRACT = new InviteProjectStateEnum("SIGNEDCONTRACT", SIGNEDCONTRACT_VALUE);

    /**
     * construct function
     * @param String inviteProjectStateEnum
     */
    private InviteProjectStateEnum(String name, String inviteProjectStateEnum)
    {
        super(name, inviteProjectStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InviteProjectStateEnum getEnum(String inviteProjectStateEnum)
    {
        return (InviteProjectStateEnum)getEnum(InviteProjectStateEnum.class, inviteProjectStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InviteProjectStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InviteProjectStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InviteProjectStateEnum.class);
    }
}