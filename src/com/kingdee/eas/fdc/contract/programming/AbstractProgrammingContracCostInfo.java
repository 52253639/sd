package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingContracCostInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractProgrammingContracCostInfo()
    {
        this("id");
    }
    protected AbstractProgrammingContracCostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 框架合约成本构成 's 框架合约 property 
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
     * Object: 框架合约成本构成 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:框架合约成本构成's 目标成本property 
     */
    public java.math.BigDecimal getGoalCost()
    {
        return getBigDecimal("goalCost");
    }
    public void setGoalCost(java.math.BigDecimal item)
    {
        setBigDecimal("goalCost", item);
    }
    /**
     * Object:框架合约成本构成's 已分配property 
     */
    public java.math.BigDecimal getAssigned()
    {
        return getBigDecimal("assigned");
    }
    public void setAssigned(java.math.BigDecimal item)
    {
        setBigDecimal("assigned", item);
    }
    /**
     * Object:框架合约成本构成's 待分配property 
     */
    public java.math.BigDecimal getAssigning()
    {
        return getBigDecimal("assigning");
    }
    public void setAssigning(java.math.BigDecimal item)
    {
        setBigDecimal("assigning", item);
    }
    /**
     * Object:框架合约成本构成's 本合约分配property 
     */
    public java.math.BigDecimal getContractAssign()
    {
        return getBigDecimal("contractAssign");
    }
    public void setContractAssign(java.math.BigDecimal item)
    {
        setBigDecimal("contractAssign", item);
    }
    /**
     * Object:框架合约成本构成's 备注property 
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
        return new BOSObjectType("9E6FDD26");
    }
}