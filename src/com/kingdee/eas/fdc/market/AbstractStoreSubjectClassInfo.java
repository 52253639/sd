package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStoreSubjectClassInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractStoreSubjectClassInfo()
    {
        this("id");
    }
    protected AbstractStoreSubjectClassInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 题库题目类别 's 父节点 property 
     */
    public com.kingdee.eas.fdc.market.StoreSubjectClassInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.StoreSubjectClassInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.StoreSubjectClassInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3844997C");
    }
}