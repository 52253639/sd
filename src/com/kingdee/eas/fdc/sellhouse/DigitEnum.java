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
public class DigitEnum extends StringEnum
{
    public static final String TENTHDIGIT_VALUE = "TenthDigit";
    public static final String ENTRYDIGIT_VALUE = "EntryDigit";
    public static final String TENDIGIT_VALUE = "TenDigit";
    public static final String HUNDREDDIGIT_VALUE = "HundredDigit";
    public static final String THOUSANDDIGIT_VALUE = "ThousandDigit";
    public static final String TENTHOUSANDDIGIT_VALUE = "TenThousandDigit";
    public static final String LAKHDIGIT_VALUE = "LakhDigit";

    public static final DigitEnum TenthDigit = new DigitEnum("TenthDigit", TENTHDIGIT_VALUE);
    public static final DigitEnum EntryDigit = new DigitEnum("EntryDigit", ENTRYDIGIT_VALUE);
    public static final DigitEnum TenDigit = new DigitEnum("TenDigit", TENDIGIT_VALUE);
    public static final DigitEnum HundredDigit = new DigitEnum("HundredDigit", HUNDREDDIGIT_VALUE);
    public static final DigitEnum ThousandDigit = new DigitEnum("ThousandDigit", THOUSANDDIGIT_VALUE);
    public static final DigitEnum TenThousandDigit = new DigitEnum("TenThousandDigit", TENTHOUSANDDIGIT_VALUE);
    public static final DigitEnum LakhDigit = new DigitEnum("LakhDigit", LAKHDIGIT_VALUE);

    /**
     * construct function
     * @param String digitEnum
     */
    private DigitEnum(String name, String digitEnum)
    {
        super(name, digitEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DigitEnum getEnum(String digitEnum)
    {
        return (DigitEnum)getEnum(DigitEnum.class, digitEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DigitEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DigitEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DigitEnum.class);
    }
}