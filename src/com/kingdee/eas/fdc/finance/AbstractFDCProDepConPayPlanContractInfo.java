package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepConPayPlanContractInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepConPayPlanContractInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepConPayPlanContractInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryCollection());
    }
    /**
     * Object: 已签定合同分录 's 父结点 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 已签定合同分录 's 部门编制 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("department");
    }
    public void setDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("department", item);
    }
    /**
     * Object: 已签定合同分录 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object:已签定合同分录's 是否打回property 
     */
    public boolean isIsBack()
    {
        return getBoolean("isBack");
    }
    public void setIsBack(boolean item)
    {
        setBoolean("isBack", item);
    }
    /**
     * Object:已签定合同分录's 合同名称property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:已签定合同分录's 合同最新造价property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object:已签定合同分录's 截止上月累计应付property 
     */
    public java.math.BigDecimal getLastMonthPayable()
    {
        return getBigDecimal("lastMonthPayable");
    }
    public void setLastMonthPayable(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthPayable", item);
    }
    /**
     * Object:已签定合同分录's 截止上月累计实付property 
     */
    public java.math.BigDecimal getLastMonthPay()
    {
        return getBigDecimal("lastMonthPay");
    }
    public void setLastMonthPay(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthPay", item);
    }
    /**
     * Object:已签定合同分录's 截止上月累计未付property 
     */
    public java.math.BigDecimal getLastMonthNopay()
    {
        return getBigDecimal("lastMonthNopay");
    }
    public void setLastMonthNopay(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthNopay", item);
    }
    /**
     * Object:已签定合同分录's 截止上月累计在途property 
     */
    public java.math.BigDecimal getLastMonthEnRoute()
    {
        return getBigDecimal("lastMonthEnRoute");
    }
    public void setLastMonthEnRoute(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthEnRoute", item);
    }
    /**
     * Object: 已签定合同分录 's 合同分录月度支付 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryCollection)get("entrys");
    }
    /**
     * Object: 已签定合同分录 's 合同滚动计划对应分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo getDepPlan()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo)get("depPlan");
    }
    public void setDepPlan(com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo item)
    {
        put("depPlan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EC1F9E19");
    }
}