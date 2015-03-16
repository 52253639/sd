package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingContractEconomyInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractProgrammingContractEconomyInfo()
    {
        this("id");
    }
    protected AbstractProgrammingContractEconomyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 框架合约经济条款 's 框架合约 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: 框架合约经济条款 's 付款类型 property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getPaymentType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("paymentType");
    }
    public void setPaymentType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("paymentType", item);
    }
    /**
     * Object:框架合约经济条款's 付款比例property 
     */
    public java.math.BigDecimal getScale()
    {
        return getBigDecimal("scale");
    }
    public void setScale(java.math.BigDecimal item)
    {
        setBigDecimal("scale", item);
    }
    /**
     * Object:框架合约经济条款's 付款金额property 
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
     * Object:框架合约经济条款's 付款条件property 
     */
    public String getCondition()
    {
        return getString("condition");
    }
    public void setCondition(String item)
    {
        setString("condition", item);
    }
    /**
     * Object:框架合约经济条款's 付款日期property 
     */
    public java.sql.Timestamp getPaymentDate()
    {
        return getTimestamp("paymentDate");
    }
    public void setPaymentDate(java.sql.Timestamp item)
    {
        setTimestamp("paymentDate", item);
    }
    /**
     * Object:框架合约经济条款's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("144467E3");
    }
}