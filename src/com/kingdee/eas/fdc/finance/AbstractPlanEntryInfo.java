package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanEntryInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款计划分录 's 头 property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.PayPlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object:付款计划分录's 工程项目Idproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object:付款计划分录's 费用类型property 
     */
    public String getExpenseTypeId()
    {
        return getString("expenseTypeId");
    }
    public void setExpenseTypeId(String item)
    {
        setString("expenseTypeId", item);
    }
    /**
     * Object:付款计划分录's 下月计划金额property 
     */
    public java.math.BigDecimal getPlanAmount1()
    {
        return getBigDecimal("planAmount1");
    }
    public void setPlanAmount1(java.math.BigDecimal item)
    {
        setBigDecimal("planAmount1", item);
    }
    /**
     * Object:付款计划分录's 下2月计划金额property 
     */
    public java.math.BigDecimal getPlanAmount2()
    {
        return getBigDecimal("planAmount2");
    }
    public void setPlanAmount2(java.math.BigDecimal item)
    {
        setBigDecimal("planAmount2", item);
    }
    /**
     * Object:付款计划分录's 下3月计划金额property 
     */
    public java.math.BigDecimal getPlanAmount3()
    {
        return getBigDecimal("planAmount3");
    }
    public void setPlanAmount3(java.math.BigDecimal item)
    {
        setBigDecimal("planAmount3", item);
    }
    /**
     * Object: 付款计划分录 's 付款申请单 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getPayRequest()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("payRequest");
    }
    public void setPayRequest(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("payRequest", item);
    }
    /**
     * Object:付款计划分录's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AC337651");
    }
}