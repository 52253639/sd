package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShareSellProjectInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractShareSellProjectInfo()
    {
        this("id");
    }
    protected AbstractShareSellProjectInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:项目共享's 共享日期property 
     */
    public java.util.Date getShareDate()
    {
        return getDate("shareDate");
    }
    public void setShareDate(java.util.Date item)
    {
        setDate("shareDate", item);
    }
    /**
     * Object: 项目共享 's 共享项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 项目共享 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getShareProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("shareProject");
    }
    public void setShareProject(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("shareProject", item);
    }
    /**
     * Object: 项目共享 's 共享操作人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getOperationPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("operationPerson");
    }
    public void setOperationPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("operationPerson", item);
    }
    /**
     * Object: 项目共享 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("94CAEAE3");
    }
}