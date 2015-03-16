package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectYearPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectYearPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectYearPlanEntryInfo(String pkField)
    {
        super(pkField);
        put("dateEntry", new com.kingdee.eas.fdc.finance.ProjectYearPlanDateEntryCollection());
    }
    /**
     * Object: 项目年度付款规划分录 's 项目年度付款计划 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.ProjectYearPlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 项目年度付款规划分录 's 合同 property 
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
     * Object: 项目年度付款规划分录 's 合规规划 property 
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
     * Object: 项目年度付款规划分录 's 明细分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanDateEntryCollection getDateEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanDateEntryCollection)get("dateEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("24688802");
    }
}