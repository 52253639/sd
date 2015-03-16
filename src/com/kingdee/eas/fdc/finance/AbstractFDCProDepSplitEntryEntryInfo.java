package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitEntryEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCProDepSplitEntryEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitEntryEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 滚动付款计划拆分明细 's 拆分分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCProDepSplitEntryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 滚动付款计划拆分明细 's 拆分明细 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo getDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo)get("details");
    }
    public void setDetails(com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo item)
    {
        put("details", item);
    }
    /**
     * Object:滚动付款计划拆分明细's 拆分完工工程量property 
     */
    public java.math.BigDecimal getSptWorkload()
    {
        return getBigDecimal("sptWorkload");
    }
    public void setSptWorkload(java.math.BigDecimal item)
    {
        setBigDecimal("sptWorkload", item);
    }
    /**
     * Object:滚动付款计划拆分明细's 拆分计划支付property 
     */
    public java.math.BigDecimal getSptPay()
    {
        return getBigDecimal("sptPay");
    }
    public void setSptPay(java.math.BigDecimal item)
    {
        setBigDecimal("sptPay", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AB0B6902");
    }
}