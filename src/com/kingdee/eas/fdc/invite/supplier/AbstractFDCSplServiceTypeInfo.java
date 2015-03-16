package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplServiceTypeInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractFDCSplServiceTypeInfo()
    {
        this("id");
    }
    protected AbstractFDCSplServiceTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 服务类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1A4CD9ED");
    }
}