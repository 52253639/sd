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
public class SHEPayTypeBizTimeEnum extends StringEnum
{
    public static final String APPTIME_VALUE = "AppTime";
    public static final String PREREGISTER_VALUE = "PreRegister";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";
    public static final String LOAN_VALUE = "Loan";
    public static final String JOIN_VALUE = "Join";
    public static final String AREACOMPENSAGTE_VALUE = "AreaCompensagte";
    public static final String PROPERTY_VALUE = "Property";
    public static final String SIGNDATE_VALUE = "signDate";

    public static final SHEPayTypeBizTimeEnum APPTIME = new SHEPayTypeBizTimeEnum("APPTIME", APPTIME_VALUE);
    public static final SHEPayTypeBizTimeEnum PREREGISTER = new SHEPayTypeBizTimeEnum("PREREGISTER", PREREGISTER_VALUE);
    public static final SHEPayTypeBizTimeEnum PURCHASE = new SHEPayTypeBizTimeEnum("PURCHASE", PURCHASE_VALUE);
    public static final SHEPayTypeBizTimeEnum SIGN = new SHEPayTypeBizTimeEnum("SIGN", SIGN_VALUE);
    public static final SHEPayTypeBizTimeEnum LOAN = new SHEPayTypeBizTimeEnum("LOAN", LOAN_VALUE);
    public static final SHEPayTypeBizTimeEnum JOIN = new SHEPayTypeBizTimeEnum("JOIN", JOIN_VALUE);
    public static final SHEPayTypeBizTimeEnum AREACOMPENSAGTE = new SHEPayTypeBizTimeEnum("AREACOMPENSAGTE", AREACOMPENSAGTE_VALUE);
    public static final SHEPayTypeBizTimeEnum PROPERTY = new SHEPayTypeBizTimeEnum("PROPERTY", PROPERTY_VALUE);
    public static final SHEPayTypeBizTimeEnum SIGNDATE = new SHEPayTypeBizTimeEnum("SIGNDATE", SIGNDATE_VALUE);

    /**
     * construct function
     * @param String sHEPayTypeBizTimeEnum
     */
    private SHEPayTypeBizTimeEnum(String name, String sHEPayTypeBizTimeEnum)
    {
        super(name, sHEPayTypeBizTimeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SHEPayTypeBizTimeEnum getEnum(String sHEPayTypeBizTimeEnum)
    {
        return (SHEPayTypeBizTimeEnum)getEnum(SHEPayTypeBizTimeEnum.class, sHEPayTypeBizTimeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SHEPayTypeBizTimeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SHEPayTypeBizTimeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SHEPayTypeBizTimeEnum.class);
    }
}