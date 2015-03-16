/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class EnterprisePropertyEnum extends StringEnum
{
    public static final String PERSONALASSET_VALUE = "PERSONALASSET";
    public static final String JOINHANDSENTERPRICE_VALUE = "JOINHANDSENTERPRICE";
    public static final String LIMITEDLIABILITY_VALUE = "LIMITEDLIABILITY";
    public static final String LIMITLESSLIABILITY_VALUE = "LIMITLESSLIABILITY";
    public static final String STOCKAOMPANY_VALUE = "STOCKCOMPANY";
    public static final String SINGLEASSET_VALUE = "SINGLEASSET";

    public static final EnterprisePropertyEnum PERSONALASSET = new EnterprisePropertyEnum("PERSONALASSET", PERSONALASSET_VALUE);
    public static final EnterprisePropertyEnum JOINHANDSENTERPRICE = new EnterprisePropertyEnum("JOINHANDSENTERPRICE", JOINHANDSENTERPRICE_VALUE);
    public static final EnterprisePropertyEnum LIMITEDLIABILITY = new EnterprisePropertyEnum("LIMITEDLIABILITY", LIMITEDLIABILITY_VALUE);
    public static final EnterprisePropertyEnum LIMITLESSLIABILITY = new EnterprisePropertyEnum("LIMITLESSLIABILITY", LIMITLESSLIABILITY_VALUE);
    public static final EnterprisePropertyEnum STOCKAOMPANY = new EnterprisePropertyEnum("STOCKAOMPANY", STOCKAOMPANY_VALUE);
    public static final EnterprisePropertyEnum SINGLEASSET = new EnterprisePropertyEnum("SINGLEASSET", SINGLEASSET_VALUE);

    /**
     * construct function
     * @param String enterprisePropertyEnum
     */
    private EnterprisePropertyEnum(String name, String enterprisePropertyEnum)
    {
        super(name, enterprisePropertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static EnterprisePropertyEnum getEnum(String enterprisePropertyEnum)
    {
        return (EnterprisePropertyEnum)getEnum(EnterprisePropertyEnum.class, enterprisePropertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(EnterprisePropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(EnterprisePropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(EnterprisePropertyEnum.class);
    }
}