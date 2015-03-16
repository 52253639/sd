package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCostLogProductEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCCostLogProductEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCCostLogProductEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:日志表产品类型分录's FDCCost头property 
     */
    public com.kingdee.bos.util.BOSUuid getParentID()
    {
        return getBOSUuid("parentID");
    }
    public void setParentID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("parentID", item);
    }
    /**
     * Object:日志表产品类型分录's 产品类型Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProductTypeId()
    {
        return getBOSUuid("productTypeId");
    }
    public void setProductTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("productTypeId", item);
    }
    /**
     * Object:日志表产品类型分录's 目标可售单方property 
     */
    public java.math.BigDecimal getAimSellAmt()
    {
        return getBigDecimal("aimSellAmt");
    }
    public void setAimSellAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimSellAmt", item);
    }
    /**
     * Object:日志表产品类型分录's 目标property 
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
     * Object:日志表产品类型分录's 已发生property 
     */
    public java.math.BigDecimal getHasHappenAmt()
    {
        return getBigDecimal("hasHappenAmt");
    }
    public void setHasHappenAmt(java.math.BigDecimal item)
    {
        setBigDecimal("hasHappenAmt", item);
    }
    /**
     * Object:日志表产品类型分录's 未发生property 
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
     * Object:日志表产品类型分录's 动态可售单方property 
     */
    public java.math.BigDecimal getDynSellAmt()
    {
        return getBigDecimal("dynSellAmt");
    }
    public void setDynSellAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynSellAmt", item);
    }
    /**
     * Object:日志表产品类型分录's 动态property 
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
     * Object:日志表产品类型分录's 已实现成本property 
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
     * Object:日志表产品类型分录's 已付款金额property 
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
     * Object:日志表产品类型分录's 已发生直接金额property 
     */
    public java.math.BigDecimal getDirectHappendAmt()
    {
        return getBigDecimal("directHappendAmt");
    }
    public void setDirectHappendAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directHappendAmt", item);
    }
    /**
     * Object:日志表产品类型分录's 已实现直接金额property 
     */
    public java.math.BigDecimal getDirectCostPayedAmt()
    {
        return getBigDecimal("directCostPayedAmt");
    }
    public void setDirectCostPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directCostPayedAmt", item);
    }
    /**
     * Object:日志表产品类型分录's 已付款直接金额property 
     */
    public java.math.BigDecimal getDirectHasPayedAmt()
    {
        return getBigDecimal("directHasPayedAmt");
    }
    public void setDirectHasPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directHasPayedAmt", item);
    }
    /**
     * Object:日志表产品类型分录's 目标成本直接金额property 
     */
    public java.math.BigDecimal getDirectAimCost()
    {
        return getBigDecimal("directAimCost");
    }
    public void setDirectAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("directAimCost", item);
    }
    /**
     * Object:日志表产品类型分录's 动态成本直接金额property 
     */
    public java.math.BigDecimal getDirectDynCost()
    {
        return getBigDecimal("directDynCost");
    }
    public void setDirectDynCost(java.math.BigDecimal item)
    {
        setBigDecimal("directDynCost", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("22FA210C");
    }
}