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
public class SocialRelationsEnum extends StringEnum
{
    public static final String HUSBANDANDWIFE_VALUE = "HusbandAndWife";
    public static final String CHILDREN_VALUE = "Children";
    public static final String PARENT_VALUE = "Parent";
    public static final String FRIEND_VALUE = "Friend";
    public static final String RELATIVE_VALUE = "Relative";
    public static final String OTHER_VALUE = "Other";

    public static final SocialRelationsEnum HusbandAndWife = new SocialRelationsEnum("HusbandAndWife", HUSBANDANDWIFE_VALUE);
    public static final SocialRelationsEnum Children = new SocialRelationsEnum("Children", CHILDREN_VALUE);
    public static final SocialRelationsEnum Parent = new SocialRelationsEnum("Parent", PARENT_VALUE);
    public static final SocialRelationsEnum Friend = new SocialRelationsEnum("Friend", FRIEND_VALUE);
    public static final SocialRelationsEnum Relative = new SocialRelationsEnum("Relative", RELATIVE_VALUE);
    public static final SocialRelationsEnum Other = new SocialRelationsEnum("Other", OTHER_VALUE);

    /**
     * construct function
     * @param String socialRelationsEnum
     */
    private SocialRelationsEnum(String name, String socialRelationsEnum)
    {
        super(name, socialRelationsEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SocialRelationsEnum getEnum(String socialRelationsEnum)
    {
        return (SocialRelationsEnum)getEnum(SocialRelationsEnum.class, socialRelationsEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SocialRelationsEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SocialRelationsEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SocialRelationsEnum.class);
    }
}