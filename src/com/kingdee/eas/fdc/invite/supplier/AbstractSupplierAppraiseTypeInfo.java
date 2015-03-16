package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierAppraiseTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSupplierAppraiseTypeInfo()
    {
        this("id");
    }
    protected AbstractSupplierAppraiseTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 模板类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("321B8A2F");
    }
}