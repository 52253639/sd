package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommissionApplyInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCommissionApplyInfo()
    {
        this("id");
    }
    protected AbstractCommissionApplyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 佣金申请 's 意向客户 property 
     */
    public com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo getIntentionCustomer()
    {
        return (com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo)get("intentionCustomer");
    }
    public void setIntentionCustomer(com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo item)
    {
        put("intentionCustomer", item);
    }
    /**
     * Object: 佣金申请 's 成交项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 佣金申请 's 成交房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:佣金申请's 成交总价property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:佣金申请's 佣金金额property 
     */
    public java.math.BigDecimal getCommissionAmount()
    {
        return getBigDecimal("commissionAmount");
    }
    public void setCommissionAmount(java.math.BigDecimal item)
    {
        setBigDecimal("commissionAmount", item);
    }
    /**
     * Object:佣金申请's 佣金比例property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:佣金申请's 已支付property 
     */
    public boolean isIsPayed()
    {
        return getBoolean("isPayed");
    }
    public void setIsPayed(boolean item)
    {
        setBoolean("isPayed", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("616C187A");
    }
}