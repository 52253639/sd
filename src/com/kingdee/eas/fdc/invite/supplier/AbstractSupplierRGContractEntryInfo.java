package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierRGContractEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierRGContractEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierRGContractEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:供应商评审汇总合同分录's 有无合同property 
     */
    public boolean isIsHasContract()
    {
        return getBoolean("isHasContract");
    }
    public void setIsHasContract(boolean item)
    {
        setBoolean("isHasContract", item);
    }
    /**
     * Object:供应商评审汇总合同分录's 合同名称property 
     */
    public String getContract()
    {
        return getString("contract");
    }
    public void setContract(String item)
    {
        setString("contract", item);
    }
    /**
     * Object: 供应商评审汇总合同分录 's 合同名称 property 
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
     * Object: 供应商评审汇总合同分录 's 供应商评审汇总 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object:供应商评审汇总合同分录's 合同签订金额property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:供应商评审汇总合同分录's 项目经理property 
     */
    public String getManager()
    {
        return getString("manager");
    }
    public void setManager(String item)
    {
        setString("manager", item);
    }
    /**
     * Object:供应商评审汇总合同分录's 项目经理联系电话property 
     */
    public String getManagerPhone()
    {
        return getString("managerPhone");
    }
    public void setManagerPhone(String item)
    {
        setString("managerPhone", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2A04A1C7");
    }
}