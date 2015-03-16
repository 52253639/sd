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
public class OwnerShipStateEnum extends StringEnum
{
    public static final String REPLACEASSIGNGLEBE_VALUE = "1ReplaceAssignGlebe";
    public static final String ALREADYLANDCERTIFICATE_VALUE = "2AlreadyLandCertificate";
    public static final String ALREADYPRESELL_VALUE = "3AlreadyPresell";
    public static final String ALAREADYPRESELLBACK_VALUE = "4AlareadyPresellBack";
    public static final String ALREADYINITREGISTER_VALUE = "5AlreadyInitRegister";
    public static final String ALREADYHOUSECERTIFICATE_VALUE = "6AlreadyHouseCertificate";

    public static final OwnerShipStateEnum ReplaceAssignGlebe = new OwnerShipStateEnum("ReplaceAssignGlebe", REPLACEASSIGNGLEBE_VALUE);
    public static final OwnerShipStateEnum AlreadyLandCertificate = new OwnerShipStateEnum("AlreadyLandCertificate", ALREADYLANDCERTIFICATE_VALUE);
    public static final OwnerShipStateEnum AlreadyPresell = new OwnerShipStateEnum("AlreadyPresell", ALREADYPRESELL_VALUE);
    public static final OwnerShipStateEnum AlareadyPresellBack = new OwnerShipStateEnum("AlareadyPresellBack", ALAREADYPRESELLBACK_VALUE);
    public static final OwnerShipStateEnum AlreadyInitRegister = new OwnerShipStateEnum("AlreadyInitRegister", ALREADYINITREGISTER_VALUE);
    public static final OwnerShipStateEnum AlreadyHouseCertificate = new OwnerShipStateEnum("AlreadyHouseCertificate", ALREADYHOUSECERTIFICATE_VALUE);

    /**
     * construct function
     * @param String ownerShipStateEnum
     */
    private OwnerShipStateEnum(String name, String ownerShipStateEnum)
    {
        super(name, ownerShipStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OwnerShipStateEnum getEnum(String ownerShipStateEnum)
    {
        return (OwnerShipStateEnum)getEnum(OwnerShipStateEnum.class, ownerShipStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OwnerShipStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OwnerShipStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OwnerShipStateEnum.class);
    }
}