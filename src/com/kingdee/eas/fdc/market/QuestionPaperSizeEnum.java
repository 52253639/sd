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
public class QuestionPaperSizeEnum extends StringEnum
{
    public static final String A0_VALUE = "841*1189";
    public static final String A1_VALUE = "594*841";
    public static final String A2_VALUE = "420*594";
    public static final String A3_VALUE = "297*420";
    public static final String A4_VALUE = "210*297";
    public static final String A5_VALUE = "148*210";
    public static final String B0_VALUE = "1000*1414";
    public static final String B1_VALUE = "707*1000";
    public static final String B2_VALUE = "500*707";
    public static final String B3_VALUE = "353*500";
    public static final String B4_VALUE = "250*354";
    public static final String B5_VALUE = "176*250";
    public static final String B6_VALUE = "125*176";

    public static final QuestionPaperSizeEnum A0 = new QuestionPaperSizeEnum("A0", A0_VALUE);
    public static final QuestionPaperSizeEnum A1 = new QuestionPaperSizeEnum("A1", A1_VALUE);
    public static final QuestionPaperSizeEnum A2 = new QuestionPaperSizeEnum("A2", A2_VALUE);
    public static final QuestionPaperSizeEnum A3 = new QuestionPaperSizeEnum("A3", A3_VALUE);
    public static final QuestionPaperSizeEnum A4 = new QuestionPaperSizeEnum("A4", A4_VALUE);
    public static final QuestionPaperSizeEnum A5 = new QuestionPaperSizeEnum("A5", A5_VALUE);
    public static final QuestionPaperSizeEnum B0 = new QuestionPaperSizeEnum("B0", B0_VALUE);
    public static final QuestionPaperSizeEnum B1 = new QuestionPaperSizeEnum("B1", B1_VALUE);
    public static final QuestionPaperSizeEnum B2 = new QuestionPaperSizeEnum("B2", B2_VALUE);
    public static final QuestionPaperSizeEnum B3 = new QuestionPaperSizeEnum("B3", B3_VALUE);
    public static final QuestionPaperSizeEnum B4 = new QuestionPaperSizeEnum("B4", B4_VALUE);
    public static final QuestionPaperSizeEnum B5 = new QuestionPaperSizeEnum("B5", B5_VALUE);
    public static final QuestionPaperSizeEnum B6 = new QuestionPaperSizeEnum("B6", B6_VALUE);

    /**
     * construct function
     * @param String questionPaperSizeEnum
     */
    private QuestionPaperSizeEnum(String name, String questionPaperSizeEnum)
    {
        super(name, questionPaperSizeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuestionPaperSizeEnum getEnum(String questionPaperSizeEnum)
    {
        return (QuestionPaperSizeEnum)getEnum(QuestionPaperSizeEnum.class, questionPaperSizeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuestionPaperSizeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuestionPaperSizeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuestionPaperSizeEnum.class);
    }
}