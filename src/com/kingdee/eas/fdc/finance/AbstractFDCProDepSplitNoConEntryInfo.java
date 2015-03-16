package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitNoConEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepSplitNoConEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitNoConEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: null 's  property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitNoConInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitNoConInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCProDepSplitNoConInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: null 's 拆分明细 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryInfo getPlanDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryInfo)get("planDetails");
    }
    public void setPlanDetails(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryInfo item)
    {
        put("planDetails", item);
    }
    /**
     * Object:null's 拆分金额property 
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
     * Object:null's 月份property 
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
        return new BOSObjectType("42533AD3");
    }
}