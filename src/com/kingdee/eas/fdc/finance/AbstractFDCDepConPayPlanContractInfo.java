package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanContractInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanContractInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanContractInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection());
    }
    /**
     * Object: 已签定合同分录 's 父结点 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 已签定合同分录 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 已签定合同分录 's 合同编码  property 
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
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection)get("entrys");
    }
    /**
     * Object:已签定合同分录's 是否被打回property 
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
     * Object:已签定合同分录's 打回后是否修改过property 
     */
    public boolean isIsEdit()
    {
        return getBoolean("isEdit");
    }
    public void setIsEdit(boolean item)
    {
        setBoolean("isEdit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4FE59E5A");
    }
}