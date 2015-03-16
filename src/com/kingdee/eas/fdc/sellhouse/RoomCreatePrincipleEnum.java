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
public class RoomCreatePrincipleEnum extends StringEnum
{
    public static final String NORMALMODEL_VALUE = "1NORMALMODEL";
    public static final String UNITHORIZONTAL_VALUE = "2UNITHORIZONTAL";
    public static final String UNITVERTICAL_VALUE = "3UNITVERTICAL";
    public static final String BUILDINGHORIZONTAL_VALUE = "4BUILDINGHORIZONTAL";

    public static final RoomCreatePrincipleEnum NormalModel = new RoomCreatePrincipleEnum("NormalModel", NORMALMODEL_VALUE);
    public static final RoomCreatePrincipleEnum UnitHorizontal = new RoomCreatePrincipleEnum("UnitHorizontal", UNITHORIZONTAL_VALUE);
    public static final RoomCreatePrincipleEnum UnitVertical = new RoomCreatePrincipleEnum("UnitVertical", UNITVERTICAL_VALUE);
    public static final RoomCreatePrincipleEnum BuildingHorizontal = new RoomCreatePrincipleEnum("BuildingHorizontal", BUILDINGHORIZONTAL_VALUE);

    /**
     * construct function
     * @param String roomCreatePrincipleEnum
     */
    private RoomCreatePrincipleEnum(String name, String roomCreatePrincipleEnum)
    {
        super(name, roomCreatePrincipleEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomCreatePrincipleEnum getEnum(String roomCreatePrincipleEnum)
    {
        return (RoomCreatePrincipleEnum)getEnum(RoomCreatePrincipleEnum.class, roomCreatePrincipleEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomCreatePrincipleEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomCreatePrincipleEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomCreatePrincipleEnum.class);
    }
}