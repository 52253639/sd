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
public class EnterprisePropertyEnum extends StringEnum
{
    public static final String PERSONALASSET_VALUE = "PersonalAsset";
    public static final String JOINHANDSENTERPRICE_VALUE = "JoinHandsEnterprice";
    public static final String LIMITEDLIABILITY_VALUE = "LimitedLiability";
    public static final String LIMITLESSLIABILITY_VALUE = "LimitlessLiability";
    public static final String STOCKCOMPANY_VALUE = "StockCompany";
    public static final String SINGLEASSET_VALUE = "SingleAsset";

    public static final EnterprisePropertyEnum PersonalAsset = new EnterprisePropertyEnum("PersonalAsset", PERSONALASSET_VALUE);
    public static final EnterprisePropertyEnum JoinHandsEnterprice = new EnterprisePropertyEnum("JoinHandsEnterprice", JOINHANDSENTERPRICE_VALUE);
    public static final EnterprisePropertyEnum LimitedLiability = new EnterprisePropertyEnum("LimitedLiability", LIMITEDLIABILITY_VALUE);
    public static final EnterprisePropertyEnum LimitlessLiability = new EnterprisePropertyEnum("LimitlessLiability", LIMITLESSLIABILITY_VALUE);
    public static final EnterprisePropertyEnum StockCompany = new EnterprisePropertyEnum("StockCompany", STOCKCOMPANY_VALUE);
    public static final EnterprisePropertyEnum SingleAsset = new EnterprisePropertyEnum("SingleAsset", SINGLEASSET_VALUE);

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