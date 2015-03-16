package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShowDeductOfPartABillInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractShowDeductOfPartABillInfo()
    {
        this("id");
    }
    protected AbstractShowDeductOfPartABillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:甲供材扣款单多次扣款过程明细's 扣款总金额property 
     */
    public java.math.BigDecimal getDeductAmount()
    {
        return getBigDecimal("deductAmount");
    }
    public void setDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmount", item);
    }
    /**
     * Object:甲供材扣款单多次扣款过程明细's 本次扣款金额property 
     */
    public java.math.BigDecimal getThisTimeDeductAmount()
    {
        return getBigDecimal("thisTimeDeductAmount");
    }
    public void setThisTimeDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("thisTimeDeductAmount", item);
    }
    /**
     * Object:甲供材扣款单多次扣款过程明细's 待扣款余额property 
     */
    public java.math.BigDecimal getStayDeductAmount()
    {
        return getBigDecimal("stayDeductAmount");
    }
    public void setStayDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("stayDeductAmount", item);
    }
    /**
     * Object:甲供材扣款单多次扣款过程明细's 付款申请单IDproperty 
     */
    public String getPayReqID()
    {
        return getString("payReqID");
    }
    public void setPayReqID(String item)
    {
        setString("payReqID", item);
    }
    /**
     * Object:甲供材扣款单多次扣款过程明细's 扣款单分录IDproperty 
     */
    public String getDeductEntryID()
    {
        return getString("deductEntryID");
    }
    public void setDeductEntryID(String item)
    {
        setString("deductEntryID", item);
    }
    /**
     * Object:甲供材扣款单多次扣款过程明细's 是否已被关联property 
     */
    public boolean isIsRelated()
    {
        return getBoolean("isRelated");
    }
    public void setIsRelated(boolean item)
    {
        setBoolean("isRelated", item);
    }
    /**
     * Object:甲供材扣款单多次扣款过程明细's 已扣款金额property 
     */
    public java.math.BigDecimal getHasDeductAmount()
    {
        return getBigDecimal("hasDeductAmount");
    }
    public void setHasDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasDeductAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("33470BCF");
    }
}