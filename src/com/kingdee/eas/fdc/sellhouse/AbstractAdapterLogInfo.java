package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAdapterLogInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAdapterLogInfo()
    {
        this("id");
    }
    protected AbstractAdapterLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 转接历史记录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 转接历史记录 's 转前销售顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getBeforeSeller()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("beforeSeller");
    }
    public void setBeforeSeller(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("beforeSeller", item);
    }
    /**
     * Object: 转接历史记录 's 转后销售顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAfterSeller()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("afterSeller");
    }
    public void setAfterSeller(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("afterSeller", item);
    }
    /**
     * Object: 转接历史记录 's 转接操作人 property 
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
     * Object:转接历史记录's 转接日期property 
     */
    public java.util.Date getAdapterDate()
    {
        return getDate("adapterDate");
    }
    public void setAdapterDate(java.util.Date item)
    {
        setDate("adapterDate", item);
    }
    /**
     * Object:转接历史记录's 是否转接意向商机property 
     */
    public boolean isIsAdapterInter()
    {
        return getBoolean("isAdapterInter");
    }
    public void setIsAdapterInter(boolean item)
    {
        setBoolean("isAdapterInter", item);
    }
    /**
     * Object:转接历史记录's 是否转接业务办理商机property 
     */
    public boolean isIsAdapterFunction()
    {
        return getBoolean("isAdapterFunction");
    }
    public void setIsAdapterFunction(boolean item)
    {
        setBoolean("isAdapterFunction", item);
    }
    /**
     * Object:转接历史记录's 是否转接终止商机property 
     */
    public boolean isIsEndAdapter()
    {
        return getBoolean("isEndAdapter");
    }
    public void setIsEndAdapter(boolean item)
    {
        setBoolean("isEndAdapter", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("030C6290");
    }
}