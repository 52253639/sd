package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBudgetAcctEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCBudgetAcctEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCBudgetAcctEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:科目预算分录's 所属项目Idproperty 
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
     * Object: 科目预算分录 's 关联成本科目 property 
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
     * Object: 科目预算分录 's 编制人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCreator()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("creator");
    }
    public void setCreator(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("creator", item);
    }
    /**
     * Object: 科目预算分录 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDeptment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("deptment");
    }
    public void setDeptment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("deptment", item);
    }
    /**
     * Object: 科目预算分录 's 关联合同 property 
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
     * Object: 科目预算分录 's 无文本合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getConWithoutText()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("conWithoutText");
    }
    public void setConWithoutText(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("conWithoutText", item);
    }
    /**
     * Object:科目预算分录's 编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:科目预算分录's 名称property 
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
     * Object:科目预算分录's 对方单位property 
     */
    public String getPartB()
    {
        return getString("partB");
    }
    public void setPartB(String item)
    {
        setString("partB", item);
    }
    /**
     * Object:科目预算分录's 是否新增property 
     */
    public boolean isIsAdd()
    {
        return getBoolean("isAdd");
    }
    public void setIsAdd(boolean item)
    {
        setBoolean("isAdd", item);
    }
    /**
     * Object:科目预算分录's 项类型property 
     */
    public com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum getItemType()
    {
        return com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum.getEnum(getString("itemType"));
    }
    public void setItemType(com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum item)
    {
		if (item != null) {
        setString("itemType", item.getValue());
		}
    }
    /**
     * Object:科目预算分录's 目标成本property 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:科目预算分录's 动态成本property 
     */
    public java.math.BigDecimal getDynCost()
    {
        return getBigDecimal("dynCost");
    }
    public void setDynCost(java.math.BigDecimal item)
    {
        setBigDecimal("dynCost", item);
    }
    /**
     * Object:科目预算分录's 截止上年/上月末累计发生成本property 
     */
    public java.math.BigDecimal getLstCost()
    {
        return getBigDecimal("lstCost");
    }
    public void setLstCost(java.math.BigDecimal item)
    {
        setBigDecimal("lstCost", item);
    }
    /**
     * Object:科目预算分录's 合同最新造价property 
     */
    public java.math.BigDecimal getConLatestPrice()
    {
        return getBigDecimal("conLatestPrice");
    }
    public void setConLatestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("conLatestPrice", item);
    }
    /**
     * Object:科目预算分录's 拆分金额property 
     */
    public java.math.BigDecimal getSplitAmt()
    {
        return getBigDecimal("splitAmt");
    }
    public void setSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitAmt", item);
    }
    /**
     * Object:科目预算分录's 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:科目预算分录's 预算金额property 
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
     * Object:科目预算分录's 成本金额property 
     */
    public java.math.BigDecimal getCost()
    {
        return getBigDecimal("cost");
    }
    public void setCost(java.math.BigDecimal item)
    {
        setBigDecimal("cost", item);
    }
    /**
     * Object:科目预算分录's 上期累计付款property 
     */
    public java.math.BigDecimal getLstAllPay()
    {
        return getBigDecimal("lstAllPay");
    }
    public void setLstAllPay(java.math.BigDecimal item)
    {
        setBigDecimal("lstAllPay", item);
    }
    /**
     * Object:科目预算分录's 上期累计成本property 
     */
    public java.math.BigDecimal getLstAllCost()
    {
        return getBigDecimal("lstAllCost");
    }
    public void setLstAllCost(java.math.BigDecimal item)
    {
        setBigDecimal("lstAllCost", item);
    }
    /**
     * Object:科目预算分录's 已发生成本property 
     */
    public java.math.BigDecimal getHappenCost()
    {
        return getBigDecimal("happenCost");
    }
    public void setHappenCost(java.math.BigDecimal item)
    {
        setBigDecimal("happenCost", item);
    }
    /**
     * Object:科目预算分录's 已发生付款property 
     */
    public java.math.BigDecimal getHappenPay()
    {
        return getBigDecimal("happenPay");
    }
    public void setHappenPay(java.math.BigDecimal item)
    {
        setBigDecimal("happenPay", item);
    }
    /**
     * Object:科目预算分录's 合同金额property 
     */
    public java.math.BigDecimal getConAmt()
    {
        return getBigDecimal("conAmt");
    }
    public void setConAmt(java.math.BigDecimal item)
    {
        setBigDecimal("conAmt", item);
    }
    /**
     * Object:科目预算分录's 源单据property 
     */
    public String getSourceId()
    {
        return getString("sourceId");
    }
    public void setSourceId(String item)
    {
        setString("sourceId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("78623B84");
    }
}