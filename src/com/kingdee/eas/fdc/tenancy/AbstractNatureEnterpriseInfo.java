package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNatureEnterpriseInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractNatureEnterpriseInfo()
    {
        this("id");
    }
    protected AbstractNatureEnterpriseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 企业性质 's 父节点 property 
     */
    public com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4516711");
    }
}