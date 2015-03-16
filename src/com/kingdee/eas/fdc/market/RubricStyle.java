/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RubricStyle extends StringEnum
{
    public static final String SINGLESEL_VALUE = "0";
    public static final String MULTIPLESEL_VALUE = "1";
    public static final String MATRIXSINGLESEL_VALUE = "2";
    public static final String MATRIXMULTIPLESEL_VALUE = "3";
    public static final String FILL_VALUE = "4";

    public static final RubricStyle SingleSel = new RubricStyle("SingleSel", SINGLESEL_VALUE);
    public static final RubricStyle MultipleSel = new RubricStyle("MultipleSel", MULTIPLESEL_VALUE);
    public static final RubricStyle MatrixSingleSel = new RubricStyle("MatrixSingleSel", MATRIXSINGLESEL_VALUE);
    public static final RubricStyle MatrixMultipleSel = new RubricStyle("MatrixMultipleSel", MATRIXMULTIPLESEL_VALUE);
    public static final RubricStyle Fill = new RubricStyle("Fill", FILL_VALUE);

    /**
     * construct function
     * @param String rubricStyle
     */
    private RubricStyle(String name, String rubricStyle)
    {
        super(name, rubricStyle);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RubricStyle getEnum(String rubricStyle)
    {
        return (RubricStyle)getEnum(RubricStyle.class, rubricStyle);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RubricStyle.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RubricStyle.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RubricStyle.class);
    }
}