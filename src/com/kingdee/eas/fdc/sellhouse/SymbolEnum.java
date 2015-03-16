/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class SymbolEnum extends IntEnum
{
    public static final int ADD_VALUE = 0;
    public static final int MINUS_VALUE = 1;
    public static final int MULTIPLY_VALUE = 2;

    public static final SymbolEnum ADD = new SymbolEnum("ADD", ADD_VALUE);
    public static final SymbolEnum MINUS = new SymbolEnum("MINUS", MINUS_VALUE);
    public static final SymbolEnum MULTIPLY = new SymbolEnum("MULTIPLY", MULTIPLY_VALUE);

    /**
     * construct function
     * @param integer symbolEnum
     */
    private SymbolEnum(String name, int symbolEnum)
    {
        super(name, symbolEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SymbolEnum getEnum(String symbolEnum)
    {
        return (SymbolEnum)getEnum(SymbolEnum.class, symbolEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static SymbolEnum getEnum(int symbolEnum)
    {
        return (SymbolEnum)getEnum(SymbolEnum.class, symbolEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SymbolEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SymbolEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SymbolEnum.class);
    }
}