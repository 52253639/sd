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
public class ChooseRoomStateEnum extends StringEnum
{
    public static final String SELLED_VALUE = "1SELLED";
    public static final String AFFIRM_VALUE = "2AFFIRM";
    public static final String UNCONFIRMED_VALUE = "3UNCONFIRMED";
    public static final String TRANSPREPUR_VALUE = "4TRANSPREPUR";
    public static final String TRANSPURCHASE_VALUE = "5TRANSPURCHASE";
    public static final String TRANSSIGN_VALUE = "6TRANSSIGN";
    public static final String CANCELCHOOSE_VALUE = "7CANCELCHOOSE";

    public static final ChooseRoomStateEnum Selled = new ChooseRoomStateEnum("Selled", SELLED_VALUE);
    public static final ChooseRoomStateEnum Affirm = new ChooseRoomStateEnum("Affirm", AFFIRM_VALUE);
    public static final ChooseRoomStateEnum UnConfirmed = new ChooseRoomStateEnum("UnConfirmed", UNCONFIRMED_VALUE);
    public static final ChooseRoomStateEnum TransPrePur = new ChooseRoomStateEnum("TransPrePur", TRANSPREPUR_VALUE);
    public static final ChooseRoomStateEnum TransPurchase = new ChooseRoomStateEnum("TransPurchase", TRANSPURCHASE_VALUE);
    public static final ChooseRoomStateEnum TransSign = new ChooseRoomStateEnum("TransSign", TRANSSIGN_VALUE);
    public static final ChooseRoomStateEnum CancelChoose = new ChooseRoomStateEnum("CancelChoose", CANCELCHOOSE_VALUE);

    /**
     * construct function
     * @param String chooseRoomStateEnum
     */
    private ChooseRoomStateEnum(String name, String chooseRoomStateEnum)
    {
        super(name, chooseRoomStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChooseRoomStateEnum getEnum(String chooseRoomStateEnum)
    {
        return (ChooseRoomStateEnum)getEnum(ChooseRoomStateEnum.class, chooseRoomStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChooseRoomStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChooseRoomStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChooseRoomStateEnum.class);
    }
}