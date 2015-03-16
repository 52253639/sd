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
public class RoomSellStateEnum extends StringEnum
{
    public static final String INIT_VALUE = "Init";
    public static final String ONSHOW_VALUE = "Onshow";
    public static final String KEEPDOWN_VALUE = "KeepDown";
    public static final String PREPURCHASE_VALUE = "PrePurchase";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";
    public static final String SINCERPURCHASE_VALUE = "SincerPurchase";
    public static final String PRICECHANGE_VALUE = "PriceChange";
    public static final String CHANGEROOM_VALUE = "ChangeRoom";
    public static final String QUITROOM_VALUE = "QuitRoom";
    public static final String CHANGENAME_VALUE = "ChangeName";

    public static final RoomSellStateEnum Init = new RoomSellStateEnum("Init", INIT_VALUE);
    public static final RoomSellStateEnum OnShow = new RoomSellStateEnum("OnShow", ONSHOW_VALUE);
    public static final RoomSellStateEnum KeepDown = new RoomSellStateEnum("KeepDown", KEEPDOWN_VALUE);
    public static final RoomSellStateEnum PrePurchase = new RoomSellStateEnum("PrePurchase", PREPURCHASE_VALUE);
    public static final RoomSellStateEnum Purchase = new RoomSellStateEnum("Purchase", PURCHASE_VALUE);
    public static final RoomSellStateEnum Sign = new RoomSellStateEnum("Sign", SIGN_VALUE);
    public static final RoomSellStateEnum SincerPurchase = new RoomSellStateEnum("SincerPurchase", SINCERPURCHASE_VALUE);
    public static final RoomSellStateEnum priceChange = new RoomSellStateEnum("priceChange", PRICECHANGE_VALUE);
    public static final RoomSellStateEnum changeRoom = new RoomSellStateEnum("changeRoom", CHANGEROOM_VALUE);
    public static final RoomSellStateEnum quitRoom = new RoomSellStateEnum("quitRoom", QUITROOM_VALUE);
    public static final RoomSellStateEnum ChangeName = new RoomSellStateEnum("ChangeName", CHANGENAME_VALUE);

    /**
     * construct function
     * @param String roomSellStateEnum
     */
    private RoomSellStateEnum(String name, String roomSellStateEnum)
    {
        super(name, roomSellStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomSellStateEnum getEnum(String roomSellStateEnum)
    {
        return (RoomSellStateEnum)getEnum(RoomSellStateEnum.class, roomSellStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomSellStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomSellStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomSellStateEnum.class);
    }
}