package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingContractInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProgrammingContractInfo()
    {
        this("id");
    }
    protected AbstractProgrammingContractInfo(String pkField)
    {
        super(pkField);
        put("costEntries", new com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection());
        put("economyEntries", new com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection());
    }
    /**
     * Object: 合约规划 's 项目合约规划 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingInfo item)
    {
        put("programming", item);
    }
    /**
     * Object: 合约规划 's 上级规划合约 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:合约规划's 规划金额property 
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
     * Object:合约规划's 控制金额property 
     */
    public java.math.BigDecimal getControlAmount()
    {
        return getBigDecimal("controlAmount");
    }
    public void setControlAmount(java.math.BigDecimal item)
    {
        setBigDecimal("controlAmount", item);
    }
    /**
     * Object:合约规划's 规划余额property 
     */
    public java.math.BigDecimal getBalance()
    {
        return getBigDecimal("balance");
    }
    public void setBalance(java.math.BigDecimal item)
    {
        setBigDecimal("balance", item);
    }
    /**
     * Object:合约规划's 控制余额property 
     */
    public java.math.BigDecimal getControlBalance()
    {
        return getBigDecimal("controlBalance");
    }
    public void setControlBalance(java.math.BigDecimal item)
    {
        setBigDecimal("controlBalance", item);
    }
    /**
     * Object:合约规划's 签约金额property 
     */
    public java.math.BigDecimal getSignUpAmount()
    {
        return getBigDecimal("signUpAmount");
    }
    public void setSignUpAmount(java.math.BigDecimal item)
    {
        setBigDecimal("signUpAmount", item);
    }
    /**
     * Object:合约规划's 变更金额property 
     */
    public java.math.BigDecimal getChangeAmount()
    {
        return getBigDecimal("changeAmount");
    }
    public void setChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmount", item);
    }
    /**
     * Object:合约规划's 结算金额property 
     */
    public java.math.BigDecimal getSettleAmount()
    {
        return getBigDecimal("settleAmount");
    }
    public void setSettleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmount", item);
    }
    /**
     * Object:合约规划's 是否被引用property 
     */
    public boolean isIsCiting()
    {
        return getBoolean("isCiting");
    }
    public void setIsCiting(boolean item)
    {
        setBoolean("isCiting", item);
    }
    /**
     * Object:合约规划's 引用版本property 
     */
    public int getCiteVersion()
    {
        return getInt("citeVersion");
    }
    public void setCiteVersion(int item)
    {
        setInt("citeVersion", item);
    }
    /**
     * Object:合约规划's 规划合约源IDproperty 
     */
    public String getSrcId()
    {
        return getString("srcId");
    }
    public void setSrcId(String item)
    {
        setString("srcId", item);
    }
    /**
     * Object:合约规划's 附件property 
     */
    public String getAttachment()
    {
        return getString("attachment");
    }
    public void setAttachment(String item)
    {
        setString("attachment", item);
    }
    /**
     * Object: 合约规划 's 成本构成 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection getCostEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection)get("costEntries");
    }
    /**
     * Object: 合约规划 's 经济条款 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection getEconomyEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection)get("economyEntries");
    }
    /**
     * Object:合约规划's 排序列property 
     */
    public int getSortNumber()
    {
        return getInt("sortNumber");
    }
    public void setSortNumber(int item)
    {
        setInt("sortNumber", item);
    }
    /**
     * Object:合约规划's 工作内容property 
     */
    public String getWorkContent()
    {
        return getString("workContent");
    }
    public void setWorkContent(String item)
    {
        setString("workContent", item);
    }
    /**
     * Object:合约规划's 甲供及甲指材设property 
     */
    public String getSupMaterial()
    {
        return getString("supMaterial");
    }
    public void setSupMaterial(String item)
    {
        setString("supMaterial", item);
    }
    /**
     * Object:合约规划's 招标方式property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormEnum getInviteWay()
    {
        return com.kingdee.eas.fdc.invite.InviteFormEnum.getEnum(getString("inviteWay"));
    }
    public void setInviteWay(com.kingdee.eas.fdc.invite.InviteFormEnum item)
    {
		if (item != null) {
        setString("inviteWay", item.getValue());
		}
    }
    /**
     * Object: 合约规划 's 招标组织 property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getInviteOrg()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("inviteOrg");
    }
    public void setInviteOrg(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("inviteOrg", item);
    }
    /**
     * Object:合约规划's 建筑单方property 
     */
    public java.math.BigDecimal getBuildPerSquare()
    {
        return getBigDecimal("buildPerSquare");
    }
    public void setBuildPerSquare(java.math.BigDecimal item)
    {
        setBigDecimal("buildPerSquare", item);
    }
    /**
     * Object:合约规划's 可售单方property 
     */
    public java.math.BigDecimal getSoldPerSquare()
    {
        return getBigDecimal("soldPerSquare");
    }
    public void setSoldPerSquare(java.math.BigDecimal item)
    {
        setBigDecimal("soldPerSquare", item);
    }
    /**
     * Object:合约规划's 成本构成名称组合property 
     */
    public String getCostAccountNames()
    {
        return getString("costAccountNames");
    }
    public void setCostAccountNames(String item)
    {
        setString("costAccountNames", item);
    }
    /**
     * Object:合约规划's 预估金额property 
     */
    public java.math.BigDecimal getEstimateAmount()
    {
        return getBigDecimal("estimateAmount");
    }
    public void setEstimateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("estimateAmount", item);
    }
    /**
     * Object:合约规划's 无文本合同金额property 
     */
    public java.math.BigDecimal getWithOutTextAmount()
    {
        return getBigDecimal("withOutTextAmount");
    }
    public void setWithOutTextAmount(java.math.BigDecimal item)
    {
        setBigDecimal("withOutTextAmount", item);
    }
    /**
     * Object:合约规划's 未签合同金额property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:合约规划's 无文本合同编码property 
     */
    public String getWithOutTextNumber()
    {
        return getString("withOutTextNumber");
    }
    public void setWithOutTextNumber(String item)
    {
        setString("withOutTextNumber", item);
    }
    /**
     * Object:合约规划's 无文本合同名称property 
     */
    public String getWithOutTextName()
    {
        return getString("withOutTextName");
    }
    public void setWithOutTextName(String item)
    {
        setString("withOutTextName", item);
    }
    /**
     * Object:合约规划's 合同与无文本合同IDproperty 
     */
    public String getBillId()
    {
        return getString("billId");
    }
    public void setBillId(String item)
    {
        setString("billId", item);
    }
    /**
     * Object: 合约规划 's 合同类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object:合约规划's 补充合同反审批预估金额快照property 
     */
    public java.math.BigDecimal getUnAuditContractEA()
    {
        return getBigDecimal("unAuditContractEA");
    }
    public void setUnAuditContractEA(java.math.BigDecimal item)
    {
        setBigDecimal("unAuditContractEA", item);
    }
    /**
     * Object:合约规划's 合同结算反审批预估金额快照property 
     */
    public java.math.BigDecimal getUnAuditContractSettleEA()
    {
        return getBigDecimal("unAuditContractSettleEA");
    }
    public void setUnAuditContractSettleEA(java.math.BigDecimal item)
    {
        setBigDecimal("unAuditContractSettleEA", item);
    }
    /**
     * Object:合约规划's 关联采购property 
     */
    public boolean isIsInvite()
    {
        return getBoolean("isInvite");
    }
    public void setIsInvite(boolean item)
    {
        setBoolean("isInvite", item);
    }
    /**
     * Object:合约规划's 是否被无文本引用property 
     */
    public boolean isIsWTCiting()
    {
        return getBoolean("isWTCiting");
    }
    public void setIsWTCiting(boolean item)
    {
        setBoolean("isWTCiting", item);
    }
    /**
     * Object:合约规划's 调整颜色property 
     */
    public String getCompare()
    {
        return getString("compare");
    }
    public void setCompare(String item)
    {
        setString("compare", item);
    }
    /**
     * Object:合约规划's 建面单方property 
     */
    public java.math.BigDecimal getBuildPrice()
    {
        return getBigDecimal("buildPrice");
    }
    public void setBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildPrice", item);
    }
    /**
     * Object:合约规划's 录入综合单价property 
     */
    public boolean isIsInput()
    {
        return getBoolean("isInput");
    }
    public void setIsInput(boolean item)
    {
        setBoolean("isInput", item);
    }
    /**
     * Object:合约规划's 实体工程量property 
     */
    public java.math.BigDecimal getQuantities()
    {
        return getBigDecimal("quantities");
    }
    public void setQuantities(java.math.BigDecimal item)
    {
        setBigDecimal("quantities", item);
    }
    /**
     * Object:合约规划's 单位property 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:合约规划's 综合单价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ECE079DB");
    }
}