package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanProEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanProEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanProEntryInfo(String pkField)
    {
        super(pkField);
        put("dateEntry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryCollection());
    }
    /**
     * Object: 待签合同&&无合同费用付款计划分录 's 合规规划 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object: 待签合同&&无合同费用付款计划分录 's 待签合同&&无合同费用付款计划 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 待签合同&&无合同费用付款计划分录 's 明细分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryCollection getDateEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryCollection)get("dateEntry");
    }
    /**
     * Object:待签合同&&无合同费用付款计划分录's 事项property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:待签合同&&无合同费用付款计划分录's 金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2C8EE8A4");
    }
}