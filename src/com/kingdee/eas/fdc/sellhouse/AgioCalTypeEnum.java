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
public class AgioCalTypeEnum extends StringEnum
{
    public static final String JIANDIAN_VALUE = "JianDian";
    public static final String DAZHE_VALUE = "Dazhe";
    public static final String ZONGJIA_VALUE = "ZongJia";
    public static final String DANJIA_VALUE = "DanJia";

    public static final AgioCalTypeEnum JianDian = new AgioCalTypeEnum("JianDian", JIANDIAN_VALUE);
    public static final AgioCalTypeEnum Dazhe = new AgioCalTypeEnum("Dazhe", DAZHE_VALUE);
    public static final AgioCalTypeEnum ZongJia = new AgioCalTypeEnum("ZongJia", ZONGJIA_VALUE);
    public static final AgioCalTypeEnum DanJia = new AgioCalTypeEnum("DanJia", DANJIA_VALUE);

    /**
     * construct function
     * @param String agioCalTypeEnum
     */
    private AgioCalTypeEnum(String name, String agioCalTypeEnum)
    {
        super(name, agioCalTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AgioCalTypeEnum getEnum(String agioCalTypeEnum)
    {
        return (AgioCalTypeEnum)getEnum(AgioCalTypeEnum.class, agioCalTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AgioCalTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AgioCalTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AgioCalTypeEnum.class);
    }
}