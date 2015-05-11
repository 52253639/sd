package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasureCostCompareInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMeasureCostCompareInfo()
    {
        this("id");
    }
    protected AbstractMeasureCostCompareInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 调整原因 's 头 property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureCostInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureCostInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.MeasureCostInfo item)
    {
        put("head", item);
    }
    /**
     * Object:调整原因's 调整成本科目property 
     */
    public String getCostAccount()
    {
        return getString("costAccount");
    }
    public void setCostAccount(String item)
    {
        setString("costAccount", item);
    }
    /**
     * Object:调整原因's 调整内容property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:调整原因's 调整原因property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:调整原因's 产品类型property 
     */
    public String getProductType()
    {
        return getString("productType");
    }
    public void setProductType(String item)
    {
        setString("productType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0FF211B1");
    }
}