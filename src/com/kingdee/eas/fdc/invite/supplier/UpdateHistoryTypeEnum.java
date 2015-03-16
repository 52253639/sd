/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class UpdateHistoryTypeEnum extends StringEnum
{
    public static final String ENTERHIS_VALUE = "FEnterHis";
    public static final String CONSTRACTHIS_VALUE = "FConstractHis";
    public static final String APPRISEHIS_VALUE = "FAppraiseHis";
    public static final String CHANGHIS_VALUE = "FChangeHis";

    public static final UpdateHistoryTypeEnum ENTERHIS = new UpdateHistoryTypeEnum("ENTERHIS", ENTERHIS_VALUE);
    public static final UpdateHistoryTypeEnum CONSTRACTHIS = new UpdateHistoryTypeEnum("CONSTRACTHIS", CONSTRACTHIS_VALUE);
    public static final UpdateHistoryTypeEnum APPRISEHIS = new UpdateHistoryTypeEnum("APPRISEHIS", APPRISEHIS_VALUE);
    public static final UpdateHistoryTypeEnum CHANGHIS = new UpdateHistoryTypeEnum("CHANGHIS", CHANGHIS_VALUE);

    /**
     * construct function
     * @param String updateHistoryTypeEnum
     */
    private UpdateHistoryTypeEnum(String name, String updateHistoryTypeEnum)
    {
        super(name, updateHistoryTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static UpdateHistoryTypeEnum getEnum(String updateHistoryTypeEnum)
    {
        return (UpdateHistoryTypeEnum)getEnum(UpdateHistoryTypeEnum.class, updateHistoryTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(UpdateHistoryTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(UpdateHistoryTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(UpdateHistoryTypeEnum.class);
    }
}