package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCostInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCCostInfo()
    {
        this("id");
    }
    protected AbstractFDCCostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:报表中间表's 工程项目IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProjectId()
    {
        return getBOSUuid("projectId");
    }
    public void setProjectId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("projectId", item);
    }
    /**
     * Object:报表中间表's 成本科目Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getCostAccountId()
    {
        return getBOSUuid("costAccountId");
    }
    public void setCostAccountId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("costAccountId", item);
    }
    /**
     * Object:报表中间表's 未结算合同金额property 
     */
    public java.math.BigDecimal getUnSettConAmt()
    {
        return getBigDecimal("unSettConAmt");
    }
    public void setUnSettConAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettConAmt", item);
    }
    /**
     * Object:报表中间表's 已结算合同金额property 
     */
    public java.math.BigDecimal getSettConAmt()
    {
        return getBigDecimal("settConAmt");
    }
    public void setSettConAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settConAmt", item);
    }
    /**
     * Object:报表中间表's 未结算签约金额合计property 
     */
    public java.math.BigDecimal getUnSettSignAmt()
    {
        return getBigDecimal("unSettSignAmt");
    }
    public void setUnSettSignAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettSignAmt", item);
    }
    /**
     * Object:报表中间表's 已结算签约金额合计property 
     */
    public java.math.BigDecimal getSettSignAmt()
    {
        return getBigDecimal("settSignAmt");
    }
    public void setSettSignAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settSignAmt", item);
    }
    /**
     * Object:报表中间表's 结算调整property 
     */
    public java.math.BigDecimal getSettAdjustAmt()
    {
        return getBigDecimal("settAdjustAmt");
    }
    public void setSettAdjustAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settAdjustAmt", item);
    }
    /**
     * Object:报表中间表's 非合同性成本property 
     */
    public java.math.BigDecimal getConWithoutTxtCostAmt()
    {
        return getBigDecimal("conWithoutTxtCostAmt");
    }
    public void setConWithoutTxtCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("conWithoutTxtCostAmt", item);
    }
    /**
     * Object:报表中间表's 目前已发生property 
     */
    public java.math.BigDecimal getHappendAmt()
    {
        return getBigDecimal("happendAmt");
    }
    public void setHappendAmt(java.math.BigDecimal item)
    {
        setBigDecimal("happendAmt", item);
    }
    /**
     * Object:报表中间表's 目前待发生property 
     */
    public java.math.BigDecimal getNotHappenAmt()
    {
        return getBigDecimal("notHappenAmt");
    }
    public void setNotHappenAmt(java.math.BigDecimal item)
    {
        setBigDecimal("notHappenAmt", item);
    }
    /**
     * Object:报表中间表's 已实现成本property 
     */
    public java.math.BigDecimal getCostPayedAmt()
    {
        return getBigDecimal("costPayedAmt");
    }
    public void setCostPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("costPayedAmt", item);
    }
    /**
     * Object:报表中间表's 已付款金额property 
     */
    public java.math.BigDecimal getHasPayedAmt()
    {
        return getBigDecimal("hasPayedAmt");
    }
    public void setHasPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("hasPayedAmt", item);
    }
    /**
     * Object:报表中间表's 动态成本property 
     */
    public java.math.BigDecimal getDynCostAmt()
    {
        return getBigDecimal("dynCostAmt");
    }
    public void setDynCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynCostAmt", item);
    }
    /**
     * Object:报表中间表's 目标成本property 
     */
    public java.math.BigDecimal getAimCostAmt()
    {
        return getBigDecimal("aimCostAmt");
    }
    public void setAimCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostAmt", item);
    }
    /**
     * Object:报表中间表's 差异property 
     */
    public java.math.BigDecimal getDiffAmt()
    {
        return getBigDecimal("diffAmt");
    }
    public void setDiffAmt(java.math.BigDecimal item)
    {
        setBigDecimal("diffAmt", item);
    }
    /**
     * Object:报表中间表's 可售单方property 
     */
    public java.math.BigDecimal getSellAmt()
    {
        return getBigDecimal("sellAmt");
    }
    public void setSellAmt(java.math.BigDecimal item)
    {
        setBigDecimal("sellAmt", item);
    }
    /**
     * Object:报表中间表's 建筑单方property 
     */
    public java.math.BigDecimal getBuildAmt()
    {
        return getBigDecimal("buildAmt");
    }
    public void setBuildAmt(java.math.BigDecimal item)
    {
        setBigDecimal("buildAmt", item);
    }
    /**
     * Object:报表中间表's 分摊类型IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getApprotionTypeId()
    {
        return getBOSUuid("approtionTypeId");
    }
    public void setApprotionTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("approtionTypeId", item);
    }
    /**
     * Object:报表中间表's 科目长编码property 
     */
    public String getAcctLongNumber()
    {
        return getString("acctLongNumber");
    }
    public void setAcctLongNumber(String item)
    {
        setString("acctLongNumber", item);
    }
    /**
     * Object:报表中间表's 项目长编码property 
     */
    public String getPrjLongNumber()
    {
        return getString("prjLongNumber");
    }
    public void setPrjLongNumber(String item)
    {
        setString("prjLongNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7799479B");
    }
}