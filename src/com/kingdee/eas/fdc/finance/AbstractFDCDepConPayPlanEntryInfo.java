package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanEntryInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection());
    }
    /**
     * Object:部门合同付款计划分录's 合同IDproperty 
     */
    public String getContractBillId()
    {
        return getString("contractBillId");
    }
    public void setContractBillId(String item)
    {
        setString("contractBillId", item);
    }
    /**
     * Object:部门合同付款计划分录's 合同编码property 
     */
    public String getContractNumber()
    {
        return getString("contractNumber");
    }
    public void setContractNumber(String item)
    {
        setString("contractNumber", item);
    }
    /**
     * Object:部门合同付款计划分录's 合同名称property 
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
     * Object:部门合同付款计划分录's 合同金额property 
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
     * Object:部门合同付款计划分录's 合同最新造价property 
     */
    public java.math.BigDecimal getContractLastestPrice()
    {
        return getBigDecimal("contractLastestPrice");
    }
    public void setContractLastestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractLastestPrice", item);
    }
    /**
     * Object: 部门合同付款计划分录 's 头 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 部门合同付款计划分录 's 条目 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection)get("items");
    }
    /**
     * Object:部门合同付款计划分录's 是否是待签订合同property 
     */
    public boolean isIsUnsettledCon()
    {
        return getBoolean("isUnsettledCon");
    }
    public void setIsUnsettledCon(boolean item)
    {
        setBoolean("isUnsettledCon", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5EB6C02A");
    }
}