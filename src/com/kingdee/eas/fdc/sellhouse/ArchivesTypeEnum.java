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
public class ArchivesTypeEnum extends StringEnum
{
    public static final String CUSTOMERARCH_VALUE = "CustomerArch";
    public static final String ROOMARCH_VALUE = "RoomArch";
    public static final String COMMERARCH_VALUE = "commerArch";

    public static final ArchivesTypeEnum CustomerArch = new ArchivesTypeEnum("CustomerArch", CUSTOMERARCH_VALUE);
    public static final ArchivesTypeEnum RoomArch = new ArchivesTypeEnum("RoomArch", ROOMARCH_VALUE);
    public static final ArchivesTypeEnum commerArch = new ArchivesTypeEnum("commerArch", COMMERARCH_VALUE);

    /**
     * construct function
     * @param String archivesTypeEnum
     */
    private ArchivesTypeEnum(String name, String archivesTypeEnum)
    {
        super(name, archivesTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ArchivesTypeEnum getEnum(String archivesTypeEnum)
    {
        return (ArchivesTypeEnum)getEnum(ArchivesTypeEnum.class, archivesTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ArchivesTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ArchivesTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ArchivesTypeEnum.class);
    }
}