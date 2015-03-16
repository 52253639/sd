package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanGatherEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanGatherEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanGatherEntryInfo(String pkField)
    {
        super(pkField);
        put("dateEntry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryCollection());
    }
    /**
     * Object: 项目月度付款计划汇总分录 's 项目月度付款计划汇总 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 项目月度付款计划汇总分录 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: 项目月度付款计划汇总分录 's 合规规划 property 
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
     * Object: 项目月度付款计划汇总分录 's 明细分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryCollection getDateEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryCollection)get("dateEntry");
    }
    /**
     * Object: 项目月度付款计划汇总分录 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object: 项目月度付款计划汇总分录 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object:项目月度付款计划汇总分录's 源单据Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSrcId()
    {
        return getBOSUuid("srcId");
    }
    public void setSrcId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("srcId", item);
    }
    /**
     * Object:项目月度付款计划汇总分录's 事项property 
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
     * Object:项目月度付款计划汇总分录's 金额property 
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
        return new BOSObjectType("0CA7FFF8");
    }
}