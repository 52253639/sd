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
public class QuestPaperBizSceneEnum extends StringEnum
{
    public static final String OTHER_VALUE = "Other";
    public static final String CLUE_VALUE = "Clue";
    public static final String CUSTOMER_VALUE = "Customer";
    public static final String ENTERTAIN_VALUE = "EnterTain";
    public static final String TRACERECORD_VALUE = "TraceRecord";
    public static final String COMMERCE_VALUE = "Commerce";
    public static final String PREPUR_VALUE = "PrePur";
    public static final String PURCHASE_VALUE = "Purchase";
    public static final String SIGN_VALUE = "Sign";

    public static final QuestPaperBizSceneEnum Other = new QuestPaperBizSceneEnum("Other", OTHER_VALUE);
    public static final QuestPaperBizSceneEnum Clue = new QuestPaperBizSceneEnum("Clue", CLUE_VALUE);
    public static final QuestPaperBizSceneEnum Customer = new QuestPaperBizSceneEnum("Customer", CUSTOMER_VALUE);
    public static final QuestPaperBizSceneEnum EnterTain = new QuestPaperBizSceneEnum("EnterTain", ENTERTAIN_VALUE);
    public static final QuestPaperBizSceneEnum TraceRecord = new QuestPaperBizSceneEnum("TraceRecord", TRACERECORD_VALUE);
    public static final QuestPaperBizSceneEnum Commerce = new QuestPaperBizSceneEnum("Commerce", COMMERCE_VALUE);
    public static final QuestPaperBizSceneEnum PrePur = new QuestPaperBizSceneEnum("PrePur", PREPUR_VALUE);
    public static final QuestPaperBizSceneEnum Purchase = new QuestPaperBizSceneEnum("Purchase", PURCHASE_VALUE);
    public static final QuestPaperBizSceneEnum Sign = new QuestPaperBizSceneEnum("Sign", SIGN_VALUE);

    /**
     * construct function
     * @param String questPaperBizSceneEnum
     */
    private QuestPaperBizSceneEnum(String name, String questPaperBizSceneEnum)
    {
        super(name, questPaperBizSceneEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuestPaperBizSceneEnum getEnum(String questPaperBizSceneEnum)
    {
        return (QuestPaperBizSceneEnum)getEnum(QuestPaperBizSceneEnum.class, questPaperBizSceneEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuestPaperBizSceneEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuestPaperBizSceneEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuestPaperBizSceneEnum.class);
    }
}