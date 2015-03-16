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
public class RevListTypeEnum extends StringEnum
{
    public static final String PURCHASEREV_VALUE = "purchaseRev";
    public static final String PURELSEREV_VALUE = "purElseRev";
    public static final String SINCERITYPUR_VALUE = "sincerityPur";
    public static final String TENROOMREV_VALUE = "tenRoomRev";
    public static final String TENOTHERREV_VALUE = "tenOtherRev";
    public static final String SINCEROBLIGATE_VALUE = "sincerobligate";
    public static final String FDCCUSTOMERREV_VALUE = "fdcCustomerRev";
    public static final String AREACOMPENSATE_VALUE = "areaCompensate";
    public static final String GENERALAR_VALUE = "generalar";
    public static final String TEMPORARY_VALUE = "temporary";
    public static final String MEASUREAR_VALUE = "measureAR";
    public static final String DEPOSITWITHDRAW_VALUE = "depositWithdraw";
    public static final String PROEAR_VALUE = "proear";
    public static final String DEPOSITREV_VALUE = "depositRev";
    public static final String DEDITAR_VALUE = "deditAR";
    public static final String ADVANCEREV_VALUE = "advancerev";

    public static final RevListTypeEnum purchaseRev = new RevListTypeEnum("purchaseRev", PURCHASEREV_VALUE);
    public static final RevListTypeEnum purElseRev = new RevListTypeEnum("purElseRev", PURELSEREV_VALUE);
    public static final RevListTypeEnum sincerityPur = new RevListTypeEnum("sincerityPur", SINCERITYPUR_VALUE);
    public static final RevListTypeEnum tenRoomRev = new RevListTypeEnum("tenRoomRev", TENROOMREV_VALUE);
    public static final RevListTypeEnum tenOtherRev = new RevListTypeEnum("tenOtherRev", TENOTHERREV_VALUE);
    public static final RevListTypeEnum sincerobligate = new RevListTypeEnum("sincerobligate", SINCEROBLIGATE_VALUE);
    public static final RevListTypeEnum fdcCustomerRev = new RevListTypeEnum("fdcCustomerRev", FDCCUSTOMERREV_VALUE);
    public static final RevListTypeEnum areaCompensate = new RevListTypeEnum("areaCompensate", AREACOMPENSATE_VALUE);
    public static final RevListTypeEnum generalar = new RevListTypeEnum("generalar", GENERALAR_VALUE);
    public static final RevListTypeEnum temporary = new RevListTypeEnum("temporary", TEMPORARY_VALUE);
    public static final RevListTypeEnum measureAR = new RevListTypeEnum("measureAR", MEASUREAR_VALUE);
    public static final RevListTypeEnum depositWithdraw = new RevListTypeEnum("depositWithdraw", DEPOSITWITHDRAW_VALUE);
    public static final RevListTypeEnum proear = new RevListTypeEnum("proear", PROEAR_VALUE);
    public static final RevListTypeEnum depositRev = new RevListTypeEnum("depositRev", DEPOSITREV_VALUE);
    public static final RevListTypeEnum deditAR = new RevListTypeEnum("deditAR", DEDITAR_VALUE);
    public static final RevListTypeEnum advancerev = new RevListTypeEnum("advancerev", ADVANCEREV_VALUE);

    /**
     * construct function
     * @param String revListTypeEnum
     */
    private RevListTypeEnum(String name, String revListTypeEnum)
    {
        super(name, revListTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RevListTypeEnum getEnum(String revListTypeEnum)
    {
        return (RevListTypeEnum)getEnum(RevListTypeEnum.class, revListTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RevListTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RevListTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RevListTypeEnum.class);
    }
}