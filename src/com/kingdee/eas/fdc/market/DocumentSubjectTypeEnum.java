/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class DocumentSubjectTypeEnum extends IntEnum
{
    public static final int SUBJECT_TYPE_SINGLE_VALUE = 1;
    public static final int SUBJECT_TYPE_MULTIPLE_VALUE = 2;
    public static final int SUBJECT_TYPE_FILL_VALUE = 3;
    public static final int SUBJECT_TYPE_DESCRIPTION_VALUE = 4;

    public static final DocumentSubjectTypeEnum SUBJECT_TYPE_SINGLE = new DocumentSubjectTypeEnum("SUBJECT_TYPE_SINGLE", SUBJECT_TYPE_SINGLE_VALUE);
    public static final DocumentSubjectTypeEnum SUBJECT_TYPE_MULTIPLE = new DocumentSubjectTypeEnum("SUBJECT_TYPE_MULTIPLE", SUBJECT_TYPE_MULTIPLE_VALUE);
    public static final DocumentSubjectTypeEnum SUBJECT_TYPE_FILL = new DocumentSubjectTypeEnum("SUBJECT_TYPE_FILL", SUBJECT_TYPE_FILL_VALUE);
    public static final DocumentSubjectTypeEnum SUBJECT_TYPE_DESCRIPTION = new DocumentSubjectTypeEnum("SUBJECT_TYPE_DESCRIPTION", SUBJECT_TYPE_DESCRIPTION_VALUE);

    /**
     * construct function
     * @param integer documentSubjectTypeEnum
     */
    private DocumentSubjectTypeEnum(String name, int documentSubjectTypeEnum)
    {
        super(name, documentSubjectTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DocumentSubjectTypeEnum getEnum(String documentSubjectTypeEnum)
    {
        return (DocumentSubjectTypeEnum)getEnum(DocumentSubjectTypeEnum.class, documentSubjectTypeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static DocumentSubjectTypeEnum getEnum(int documentSubjectTypeEnum)
    {
        return (DocumentSubjectTypeEnum)getEnum(DocumentSubjectTypeEnum.class, documentSubjectTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DocumentSubjectTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DocumentSubjectTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DocumentSubjectTypeEnum.class);
    }
}