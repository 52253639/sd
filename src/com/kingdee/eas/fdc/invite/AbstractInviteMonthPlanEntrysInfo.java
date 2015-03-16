package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteMonthPlanEntrysInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteMonthPlanEntrysInfo()
    {
        this("id");
    }
    protected AbstractInviteMonthPlanEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 月度采购招标计划分录 's 月度采购招标计划 property 
     */
    public com.kingdee.eas.fdc.invite.InviteMonthPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteMonthPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteMonthPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:月度采购招标计划分录's 工作内容property 
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
     * Object:月度采购招标计划分录's 规划金额property 
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
     * Object:月度采购招标计划分录's 框架合约编码property 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    /**
     * Object:月度采购招标计划分录's 框架合约名称property 
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
     * Object:月度采购招标计划分录's 成本构成property 
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
     * Object:月度采购招标计划分录's 级次property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:月度采购招标计划分录's 招标文件审批日期property 
     */
    public java.util.Date getDocumentsAuditDate()
    {
        return getDate("documentsAuditDate");
    }
    public void setDocumentsAuditDate(java.util.Date item)
    {
        setDate("documentsAuditDate", item);
    }
    /**
     * Object:月度采购招标计划分录's 中标审批日期property 
     */
    public java.util.Date getResultAuditDate()
    {
        return getDate("resultAuditDate");
    }
    public void setResultAuditDate(java.util.Date item)
    {
        setDate("resultAuditDate", item);
    }
    /**
     * Object:月度采购招标计划分录's 合同审批日期property 
     */
    public java.util.Date getContractAuditDate()
    {
        return getDate("contractAuditDate");
    }
    public void setContractAuditDate(java.util.Date item)
    {
        setDate("contractAuditDate", item);
    }
    /**
     * Object:月度采购招标计划分录's 预计进场日期property 
     */
    public java.util.Date getEnterAuditDate()
    {
        return getDate("enterAuditDate");
    }
    public void setEnterAuditDate(java.util.Date item)
    {
        setDate("enterAuditDate", item);
    }
    /**
     * Object:月度采购招标计划分录's 框架合约IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProgrammingContractID()
    {
        return getBOSUuid("programmingContractID");
    }
    public void setProgrammingContractID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("programmingContractID", item);
    }
    /**
     * Object:月度采购招标计划分录's 图纸及样品property 
     */
    public java.util.Date getPaperDate()
    {
        return getDate("paperDate");
    }
    public void setPaperDate(java.util.Date item)
    {
        setDate("paperDate", item);
    }
    /**
     * Object: 月度采购招标计划分录 's 制度要求的采购方式 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormInfo getRequiredInviteForm()
    {
        return (com.kingdee.eas.fdc.invite.InviteFormInfo)get("requiredInviteForm");
    }
    public void setRequiredInviteForm(com.kingdee.eas.fdc.invite.InviteFormInfo item)
    {
        put("requiredInviteForm", item);
    }
    /**
     * Object:月度采购招标计划分录's 目标成本单方指标property 
     */
    public java.math.BigDecimal getIndicator()
    {
        return getBigDecimal("indicator");
    }
    public void setIndicator(java.math.BigDecimal item)
    {
        setBigDecimal("indicator", item);
    }
    /**
     * Object:月度采购招标计划分录's 变更原因property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7CBDAA7D");
    }
}