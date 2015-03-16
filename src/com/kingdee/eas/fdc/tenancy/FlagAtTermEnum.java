/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class FlagAtTermEnum extends StringEnum
{
    public static final String RELETATTERM_VALUE = "ReletAtTerm";
    public static final String QUITATTERM_VALUE = "QuitAtTerm";
    public static final String UNKNOWN_VALUE = "Unknown";
    public static final String QUITNOTATTERM_VALUE = "QuitNotAtTerm";
    public static final String REJIGGERNOTATTERM_VALUE = "RejiggerNotAtTerm";
    public static final String CHANGENAMENOTATTERM_VALUE = "ChangeNameNotAtTerm";
    public static final String PRICECHANGENOTATTERM_VALUE = "PriceChangeNotAtTerm";

    public static final FlagAtTermEnum ReletAtTerm = new FlagAtTermEnum("ReletAtTerm", RELETATTERM_VALUE);
    public static final FlagAtTermEnum QuitAtTerm = new FlagAtTermEnum("QuitAtTerm", QUITATTERM_VALUE);
    public static final FlagAtTermEnum Unknown = new FlagAtTermEnum("Unknown", UNKNOWN_VALUE);
    public static final FlagAtTermEnum QuitNotAtTerm = new FlagAtTermEnum("QuitNotAtTerm", QUITNOTATTERM_VALUE);
    public static final FlagAtTermEnum RejiggerNotAtTerm = new FlagAtTermEnum("RejiggerNotAtTerm", REJIGGERNOTATTERM_VALUE);
    public static final FlagAtTermEnum ChangeNameNotAtTerm = new FlagAtTermEnum("ChangeNameNotAtTerm", CHANGENAMENOTATTERM_VALUE);
    public static final FlagAtTermEnum PriceChangeNotAtTerm = new FlagAtTermEnum("PriceChangeNotAtTerm", PRICECHANGENOTATTERM_VALUE);

    /**
     * construct function
     * @param String flagAtTermEnum
     */
    private FlagAtTermEnum(String name, String flagAtTermEnum)
    {
        super(name, flagAtTermEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FlagAtTermEnum getEnum(String flagAtTermEnum)
    {
        return (FlagAtTermEnum)getEnum(FlagAtTermEnum.class, flagAtTermEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FlagAtTermEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FlagAtTermEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FlagAtTermEnum.class);
    }
}