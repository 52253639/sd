package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitHasConEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepSplitHasConEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitHasConEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 滚动付款计划拆分明细 's 拆分分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitHasConInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitHasConInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCProDepSplitHasConInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 滚动付款计划拆分明细 's 拆分明细 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo getPlanDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo)get("planDetails");
    }
    public void setPlanDetails(com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo item)
    {
        put("planDetails", item);
    }
    /**
     * Object:滚动付款计划拆分明细's 拆分金额property 
     */
    public java.math.BigDecimal getSptPay()
    {
        return getBigDecimal("sptPay");
    }
    public void setSptPay(java.math.BigDecimal item)
    {
        setBigDecimal("sptPay", item);
    }
    /**
     * Object:滚动付款计划拆分明细's 月份property 
     */
    public java.util.Date getMonth()
    {
        return getDate("month");
    }
    public void setMonth(java.util.Date item)
    {
        setDate("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("996D4F28");
    }
}