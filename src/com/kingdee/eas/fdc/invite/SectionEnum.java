/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class SectionEnum extends IntEnum
{
    public static final int SECTION1_VALUE = 1;
    public static final int SECTION2_VALUE = 2;
    public static final int SECTION3_VALUE = 3;
    public static final int SECTION4_VALUE = 4;
    public static final int SECTION5_VALUE = 5;
    public static final int SECTION6_VALUE = 6;
    public static final int SECTION7_VALUE = 7;
    public static final int SECTION8_VALUE = 8;
    public static final int SECTION9_VALUE = 9;
    public static final int SECTION10_VALUE = 10;

    public static final SectionEnum SECTION1 = new SectionEnum("SECTION1", SECTION1_VALUE);
    public static final SectionEnum SECTION2 = new SectionEnum("SECTION2", SECTION2_VALUE);
    public static final SectionEnum SECTION3 = new SectionEnum("SECTION3", SECTION3_VALUE);
    public static final SectionEnum SECTION4 = new SectionEnum("SECTION4", SECTION4_VALUE);
    public static final SectionEnum SECTION5 = new SectionEnum("SECTION5", SECTION5_VALUE);
    public static final SectionEnum SECTION6 = new SectionEnum("SECTION6", SECTION6_VALUE);
    public static final SectionEnum SECTION7 = new SectionEnum("SECTION7", SECTION7_VALUE);
    public static final SectionEnum SECTION8 = new SectionEnum("SECTION8", SECTION8_VALUE);
    public static final SectionEnum SECTION9 = new SectionEnum("SECTION9", SECTION9_VALUE);
    public static final SectionEnum SECTION10 = new SectionEnum("SECTION10", SECTION10_VALUE);

    /**
     * construct function
     * @param integer sectionEnum
     */
    private SectionEnum(String name, int sectionEnum)
    {
        super(name, sectionEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SectionEnum getEnum(String sectionEnum)
    {
        return (SectionEnum)getEnum(SectionEnum.class, sectionEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static SectionEnum getEnum(int sectionEnum)
    {
        return (SectionEnum)getEnum(SectionEnum.class, sectionEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SectionEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SectionEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SectionEnum.class);
    }
}