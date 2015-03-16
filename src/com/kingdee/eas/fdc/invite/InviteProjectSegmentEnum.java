/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class InviteProjectSegmentEnum extends IntEnum
{
    public static final int S0_VALUE = 0;
    public static final int S1_VALUE = 1;
    public static final int S2_VALUE = 2;
    public static final int S3_VALUE = 3;
    public static final int S4_VALUE = 4;
    public static final int S5_VALUE = 5;
    public static final int S6_VALUE = 6;
    public static final int S7_VALUE = 7;
    public static final int S8_VALUE = 8;
    public static final int S9_VALUE = 9;
    public static final int S10_VALUE = 10;

    public static final InviteProjectSegmentEnum S0 = new InviteProjectSegmentEnum("S0", S0_VALUE);
    public static final InviteProjectSegmentEnum S1 = new InviteProjectSegmentEnum("S1", S1_VALUE);
    public static final InviteProjectSegmentEnum S2 = new InviteProjectSegmentEnum("S2", S2_VALUE);
    public static final InviteProjectSegmentEnum S3 = new InviteProjectSegmentEnum("S3", S3_VALUE);
    public static final InviteProjectSegmentEnum S4 = new InviteProjectSegmentEnum("S4", S4_VALUE);
    public static final InviteProjectSegmentEnum S5 = new InviteProjectSegmentEnum("S5", S5_VALUE);
    public static final InviteProjectSegmentEnum S6 = new InviteProjectSegmentEnum("S6", S6_VALUE);
    public static final InviteProjectSegmentEnum S7 = new InviteProjectSegmentEnum("S7", S7_VALUE);
    public static final InviteProjectSegmentEnum S8 = new InviteProjectSegmentEnum("S8", S8_VALUE);
    public static final InviteProjectSegmentEnum S9 = new InviteProjectSegmentEnum("S9", S9_VALUE);
    public static final InviteProjectSegmentEnum S10 = new InviteProjectSegmentEnum("S10", S10_VALUE);

    /**
     * construct function
     * @param integer inviteProjectSegmentEnum
     */
    private InviteProjectSegmentEnum(String name, int inviteProjectSegmentEnum)
    {
        super(name, inviteProjectSegmentEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InviteProjectSegmentEnum getEnum(String inviteProjectSegmentEnum)
    {
        return (InviteProjectSegmentEnum)getEnum(InviteProjectSegmentEnum.class, inviteProjectSegmentEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static InviteProjectSegmentEnum getEnum(int inviteProjectSegmentEnum)
    {
        return (InviteProjectSegmentEnum)getEnum(InviteProjectSegmentEnum.class, inviteProjectSegmentEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InviteProjectSegmentEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InviteProjectSegmentEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InviteProjectSegmentEnum.class);
    }
}