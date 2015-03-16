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
public class QuestionStyle extends StringEnum
{
    public static final String ASKQUESTION_VALUE = "1";
    public static final String JUDGEQUESTION_VALUE = "3";
    public static final String FILLBLANKQUESTION_VALUE = "4";
    public static final String SINGLESELECTQUESTION_VALUE = "2";
    public static final String MUTISELECTQUESTION_VALUE = "5";

    public static final QuestionStyle ASKQUESTION = new QuestionStyle("ASKQUESTION", ASKQUESTION_VALUE);
    public static final QuestionStyle JUDGEQUESTION = new QuestionStyle("JUDGEQUESTION", JUDGEQUESTION_VALUE);
    public static final QuestionStyle FILLBlankQUESTION = new QuestionStyle("FILLBlankQUESTION", FILLBLANKQUESTION_VALUE);
    public static final QuestionStyle SINGlESELECTQUESTION = new QuestionStyle("SINGlESELECTQUESTION", SINGLESELECTQUESTION_VALUE);
    public static final QuestionStyle MUTISELECTQUESTION = new QuestionStyle("MUTISELECTQUESTION", MUTISELECTQUESTION_VALUE);

    /**
     * construct function
     * @param String questionStyle
     */
    private QuestionStyle(String name, String questionStyle)
    {
        super(name, questionStyle);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuestionStyle getEnum(String questionStyle)
    {
        return (QuestionStyle)getEnum(QuestionStyle.class, questionStyle);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuestionStyle.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuestionStyle.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuestionStyle.class);
    }
}