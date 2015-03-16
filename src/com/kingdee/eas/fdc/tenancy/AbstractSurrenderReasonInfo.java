package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSurrenderReasonInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSurrenderReasonInfo()
    {
        this("id");
    }
    protected AbstractSurrenderReasonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 退租原因 's 关联项目工程 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("21B32501");
    }
}