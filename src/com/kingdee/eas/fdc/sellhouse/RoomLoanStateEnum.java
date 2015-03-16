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
public class RoomLoanStateEnum extends StringEnum
{
    public static final String NOTLOANED_VALUE = "1NOTLOANED";
    public static final String LOANED_VALUE = "2LOANED";

    public static final RoomLoanStateEnum NOTLOANED = new RoomLoanStateEnum("NOTLOANED", NOTLOANED_VALUE);
    public static final RoomLoanStateEnum LOANED = new RoomLoanStateEnum("LOANED", LOANED_VALUE);

    /**
     * construct function
     * @param String roomLoanStateEnum
     */
    private RoomLoanStateEnum(String name, String roomLoanStateEnum)
    {
        super(name, roomLoanStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomLoanStateEnum getEnum(String roomLoanStateEnum)
    {
        return (RoomLoanStateEnum)getEnum(RoomLoanStateEnum.class, roomLoanStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomLoanStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomLoanStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomLoanStateEnum.class);
    }
}