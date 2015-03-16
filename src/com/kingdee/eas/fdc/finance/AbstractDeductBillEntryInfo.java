package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeductBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractDeductBillEntryInfo()
    {
        this("id");
    }
    protected AbstractDeductBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.finance.DeductBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.DeductBillInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.DeductBillInfo item)
    {
        put("Parent", item);
    }
    /**
     * Object:分录's 合同ID（包括合同和无文本合同）property 
     */
    public String getContractId()
    {
        return getString("contractId");
    }
    public void setContractId(String item)
    {
        setString("contractId", item);
    }
    /**
     * Object: 分录 's 扣款单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getDeductUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("deductUnit");
    }
    public void setDeductUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("deductUnit", item);
    }
    /**
     * Object: 分录 's 扣款类型 property 
     */
    public com.kingdee.eas.fdc.basedata.DeductTypeInfo getDeductType()
    {
        return (com.kingdee.eas.fdc.basedata.DeductTypeInfo)get("deductType");
    }
    public void setDeductType(com.kingdee.eas.fdc.basedata.DeductTypeInfo item)
    {
        put("deductType", item);
    }
    /**
     * Object:分录's 扣款事项property 
     */
    public String getDeductItem()
    {
        return getString("deductItem");
    }
    public void setDeductItem(String item)
    {
        setString("deductItem", item);
    }
    /**
     * Object:分录's 扣款金额property 
     */
    public java.math.BigDecimal getDeductAmt()
    {
        return getBigDecimal("deductAmt");
    }
    public void setDeductAmt(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmt", item);
    }
    /**
     * Object:分录's 扣款日期property 
     */
    public java.util.Date getDeductDate()
    {
        return getDate("deductDate");
    }
    public void setDeductDate(java.util.Date item)
    {
        setDate("deductDate", item);
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
     * Object:分录's 是否已申请property 
     */
    public boolean isHasApplied()
    {
        return getBoolean("hasApplied");
    }
    public void setHasApplied(boolean item)
    {
        setBoolean("hasApplied", item);
    }
    /**
     * Object:分录's 分录编码（Webservice中才用到）property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:分录's 扣款原币金额property 
     */
    public java.math.BigDecimal getDeductOriginalAmt()
    {
        return getBigDecimal("deductOriginalAmt");
    }
    public void setDeductOriginalAmt(java.math.BigDecimal item)
    {
        setBigDecimal("deductOriginalAmt", item);
    }
    /**
     * Object:分录's 汇率property 
     */
    public java.math.BigDecimal getExRate()
    {
        return getBigDecimal("exRate");
    }
    public void setExRate(java.math.BigDecimal item)
    {
        setBigDecimal("exRate", item);
    }
    /**
     * Object: 分录 's 科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2081EA39");
    }
}