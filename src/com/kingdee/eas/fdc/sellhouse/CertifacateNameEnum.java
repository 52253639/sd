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
public class CertifacateNameEnum extends StringEnum
{
    public static final String IDCARD_VALUE = "1IDCard";
    public static final String PASSPORT_VALUE = "2Passport";
    public static final String PRC_VALUE = "3PRC";
    public static final String OTHERS_VALUE = "4Others";
    public static final String LICENSE_VALUE = "5";
    public static final String HISTOLYSIS_VALUE = "6";
    public static final String REVENUER_VALUE = "7";
    public static final String CORPORATIVE_VALUE = "8";
    public static final String MILITARYID_VALUE = "9MilitaryID";

    public static final CertifacateNameEnum IDCard = new CertifacateNameEnum("IDCard", IDCARD_VALUE);
    public static final CertifacateNameEnum Passport = new CertifacateNameEnum("Passport", PASSPORT_VALUE);
    public static final CertifacateNameEnum PRC = new CertifacateNameEnum("PRC", PRC_VALUE);
    public static final CertifacateNameEnum Others = new CertifacateNameEnum("Others", OTHERS_VALUE);
    public static final CertifacateNameEnum License = new CertifacateNameEnum("License", LICENSE_VALUE);
    public static final CertifacateNameEnum Histolysis = new CertifacateNameEnum("Histolysis", HISTOLYSIS_VALUE);
    public static final CertifacateNameEnum Revenuer = new CertifacateNameEnum("Revenuer", REVENUER_VALUE);
    public static final CertifacateNameEnum Corporative = new CertifacateNameEnum("Corporative", CORPORATIVE_VALUE);
    public static final CertifacateNameEnum MilitaryID = new CertifacateNameEnum("MilitaryID", MILITARYID_VALUE);

    /**
     * construct function
     * @param String certifacateNameEnum
     */
    private CertifacateNameEnum(String name, String certifacateNameEnum)
    {
        super(name, certifacateNameEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CertifacateNameEnum getEnum(String certifacateNameEnum)
    {
        return (CertifacateNameEnum)getEnum(CertifacateNameEnum.class, certifacateNameEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CertifacateNameEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CertifacateNameEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CertifacateNameEnum.class);
    }
}