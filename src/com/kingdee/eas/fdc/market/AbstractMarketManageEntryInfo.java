package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketManageEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMarketManageEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketManageEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.market.MarketManageInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MarketManageInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MarketManageInfo item)
    {
        put("Parent", item);
    }
    /**
     * Object:分录's 预算金额property 
     */
    public java.math.BigDecimal getButgetAmount()
    {
        return getBigDecimal("butgetAmount");
    }
    public void setButgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("butgetAmount", item);
    }
    /**
     * Object:分录's 实际金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:分录's 付款金额property 
     */
    public java.math.BigDecimal getPayment()
    {
        return getBigDecimal("payment");
    }
    public void setPayment(java.math.BigDecimal item)
    {
        setBigDecimal("payment", item);
    }
    /**
     * Object:分录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: 分录 's 费用项目 property 
     */
    public com.kingdee.eas.basedata.scm.common.ExpenseItemInfo getCostNumber()
    {
        return (com.kingdee.eas.basedata.scm.common.ExpenseItemInfo)get("costNumber");
    }
    public void setCostNumber(com.kingdee.eas.basedata.scm.common.ExpenseItemInfo item)
    {
        put("costNumber", item);
    }
    /**
     * Object:分录's 费用类别property 
     */
    public String getCostName()
    {
        return getString("costName");
    }
    public void setCostName(String item)
    {
        setString("costName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A8833E80");
    }
}