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
public class FDCSysFieldEnum extends StringEnum
{
    public static final String IDIO_ROOMAREA_VALUE = "idio_roomArea";
    public static final String IDIO_DEALSUMAMOUNT_VALUE = "idio_dealSumAmount";
    public static final String IDIO_DEALPRICE_VALUE = "idio_dealPrice";
    public static final String IDIO_CURRYTYPE_VALUE = "idio_curryType";
    public static final String IDIO_PURCHASECURRENCY_VALUE = "idio_purchaseCurrency";
    public static final String IDIO_CUSNAME_1_VALUE = "idio_cusName_1";
    public static final String IDIO_PHONE_VALUE = "idio_phone";
    public static final String IDIO_PAPERNO_VALUE = "idio_paperNO";
    public static final String CONT_BUILDINGAREA_VALUE = "cont_buildingArea";
    public static final String CONT_DEALPRICE_VALUE = "cont_dealPrice";
    public static final String CONT_DEALSUMAMOUNT_VALUE = "cont_DealSumAmount";
    public static final String CONT_FIRSTPAYAMT_VALUE = "cont_firstPayAmt";
    public static final String CONT_CUSNAME_VALUE = "cont_CusName";
    public static final String CONT_AFFIRMCUSNAME_VALUE = "cont_affirmCusName";
    public static final String CONT_PAPERNO_VALUE = "cont_paperNO";
    public static final String CONT_AFFIRMPAPERNO_VALUE = "cont_affirmPaperNO";
    public static final String CONT_PAPERTYPE_VALUE = "cont_paperType";
    public static final String CONT_LIVINGAREA_VALUE = "cont_livingArea";
    public static final String CONT_LOT_VALUE = "cont_lot";
    public static final String CONT_RPR_VALUE = "cont_rpr";
    public static final String CONT_PHOTO_VALUE = "cont_photo";
    public static final String CONT_POSTALCODE_VALUE = "cont_postalcode";
    public static final String CONT_LINKADRESS_VALUE = "cont_linkAdress";
    public static final String CONT_CUSNAME2_VALUE = "cont_cusName2";

    public static final FDCSysFieldEnum idio_roomArea = new FDCSysFieldEnum("idio_roomArea", IDIO_ROOMAREA_VALUE);
    public static final FDCSysFieldEnum idio_dealSumAmount = new FDCSysFieldEnum("idio_dealSumAmount", IDIO_DEALSUMAMOUNT_VALUE);
    public static final FDCSysFieldEnum idio_dealPrice = new FDCSysFieldEnum("idio_dealPrice", IDIO_DEALPRICE_VALUE);
    public static final FDCSysFieldEnum idio_curryType = new FDCSysFieldEnum("idio_curryType", IDIO_CURRYTYPE_VALUE);
    public static final FDCSysFieldEnum idio_purchaseCurrency = new FDCSysFieldEnum("idio_purchaseCurrency", IDIO_PURCHASECURRENCY_VALUE);
    public static final FDCSysFieldEnum idio_cusName_1 = new FDCSysFieldEnum("idio_cusName_1", IDIO_CUSNAME_1_VALUE);
    public static final FDCSysFieldEnum idio_phone = new FDCSysFieldEnum("idio_phone", IDIO_PHONE_VALUE);
    public static final FDCSysFieldEnum idio_paperNO = new FDCSysFieldEnum("idio_paperNO", IDIO_PAPERNO_VALUE);
    public static final FDCSysFieldEnum cont_buildingArea = new FDCSysFieldEnum("cont_buildingArea", CONT_BUILDINGAREA_VALUE);
    public static final FDCSysFieldEnum cont_dealPrice = new FDCSysFieldEnum("cont_dealPrice", CONT_DEALPRICE_VALUE);
    public static final FDCSysFieldEnum cont_DealSumAmount = new FDCSysFieldEnum("cont_DealSumAmount", CONT_DEALSUMAMOUNT_VALUE);
    public static final FDCSysFieldEnum cont_firstPayAmt = new FDCSysFieldEnum("cont_firstPayAmt", CONT_FIRSTPAYAMT_VALUE);
    public static final FDCSysFieldEnum cont_CusName = new FDCSysFieldEnum("cont_CusName", CONT_CUSNAME_VALUE);
    public static final FDCSysFieldEnum cont_affirmCusName = new FDCSysFieldEnum("cont_affirmCusName", CONT_AFFIRMCUSNAME_VALUE);
    public static final FDCSysFieldEnum cont_paperNO = new FDCSysFieldEnum("cont_paperNO", CONT_PAPERNO_VALUE);
    public static final FDCSysFieldEnum cont_affirmPaperNO = new FDCSysFieldEnum("cont_affirmPaperNO", CONT_AFFIRMPAPERNO_VALUE);
    public static final FDCSysFieldEnum cont_paperType = new FDCSysFieldEnum("cont_paperType", CONT_PAPERTYPE_VALUE);
    public static final FDCSysFieldEnum cont_livingArea = new FDCSysFieldEnum("cont_livingArea", CONT_LIVINGAREA_VALUE);
    public static final FDCSysFieldEnum cont_lot = new FDCSysFieldEnum("cont_lot", CONT_LOT_VALUE);
    public static final FDCSysFieldEnum cont_rpr = new FDCSysFieldEnum("cont_rpr", CONT_RPR_VALUE);
    public static final FDCSysFieldEnum cont_photo = new FDCSysFieldEnum("cont_photo", CONT_PHOTO_VALUE);
    public static final FDCSysFieldEnum cont_postalcode = new FDCSysFieldEnum("cont_postalcode", CONT_POSTALCODE_VALUE);
    public static final FDCSysFieldEnum cont_linkAdress = new FDCSysFieldEnum("cont_linkAdress", CONT_LINKADRESS_VALUE);
    public static final FDCSysFieldEnum cont_cusName2 = new FDCSysFieldEnum("cont_cusName2", CONT_CUSNAME2_VALUE);

    /**
     * construct function
     * @param String fDCSysFieldEnum
     */
    private FDCSysFieldEnum(String name, String fDCSysFieldEnum)
    {
        super(name, fDCSysFieldEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FDCSysFieldEnum getEnum(String fDCSysFieldEnum)
    {
        return (FDCSysFieldEnum)getEnum(FDCSysFieldEnum.class, fDCSysFieldEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FDCSysFieldEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FDCSysFieldEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FDCSysFieldEnum.class);
    }
}