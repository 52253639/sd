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
public class QuestionTypeEnum extends StringEnum
{
    public static final String PROMPT_VALUE = "1prompt";
    public static final String ESTOP_VALUE = "2estop";
    public static final String NOPROMPT_VALUE = "3noPrompt";

    public static final QuestionTypeEnum prompt = new QuestionTypeEnum("prompt", PROMPT_VALUE);
    public static final QuestionTypeEnum estop = new QuestionTypeEnum("estop", ESTOP_VALUE);
    public static final QuestionTypeEnum noPrompt = new QuestionTypeEnum("noPrompt", NOPROMPT_VALUE);

    /**
     * construct function
     * @param String questionTypeEnum
     */
    private QuestionTypeEnum(String name, String questionTypeEnum)
    {
        super(name, questionTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuestionTypeEnum getEnum(String questionTypeEnum)
    {
        return (QuestionTypeEnum)getEnum(QuestionTypeEnum.class, questionTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuestionTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuestionTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuestionTypeEnum.class);
    }
}