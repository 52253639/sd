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
public class PurchaseTypeEnum extends StringEnum
{
    public static final String MATERIAL_VALUE = "0MATERIAL";
    public static final String PROJECT_VALUE = "1PROJECT";
    public static final String SERVICE_VALUE = "2SERVICE";

    public static final PurchaseTypeEnum material = new PurchaseTypeEnum("material", MATERIAL_VALUE);
    public static final PurchaseTypeEnum project = new PurchaseTypeEnum("project", PROJECT_VALUE);
    public static final PurchaseTypeEnum service = new PurchaseTypeEnum("service", SERVICE_VALUE);

    /**
     * construct function
     * @param String purchaseTypeEnum
     */
    private PurchaseTypeEnum(String name, String purchaseTypeEnum)
    {
        super(name, purchaseTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PurchaseTypeEnum getEnum(String purchaseTypeEnum)
    {
        return (PurchaseTypeEnum)getEnum(PurchaseTypeEnum.class, purchaseTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PurchaseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PurchaseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PurchaseTypeEnum.class);
    }
}