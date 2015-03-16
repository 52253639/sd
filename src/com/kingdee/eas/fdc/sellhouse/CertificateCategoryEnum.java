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
public class CertificateCategoryEnum extends StringEnum
{
    public static final String SYSTEM_VALUE = "0system";
    public static final String CUSTOMER_VALUE = "1customer";

    public static final CertificateCategoryEnum SYSTEM = new CertificateCategoryEnum("SYSTEM", SYSTEM_VALUE);
    public static final CertificateCategoryEnum CUSTOMER = new CertificateCategoryEnum("CUSTOMER", CUSTOMER_VALUE);

    /**
     * construct function
     * @param String certificateCategoryEnum
     */
    private CertificateCategoryEnum(String name, String certificateCategoryEnum)
    {
        super(name, certificateCategoryEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CertificateCategoryEnum getEnum(String certificateCategoryEnum)
    {
        return (CertificateCategoryEnum)getEnum(CertificateCategoryEnum.class, certificateCategoryEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CertificateCategoryEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CertificateCategoryEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CertificateCategoryEnum.class);
    }
}