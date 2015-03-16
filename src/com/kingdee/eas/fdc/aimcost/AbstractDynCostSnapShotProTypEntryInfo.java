package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostSnapShotProTypEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDynCostSnapShotProTypEntryInfo()
    {
        this("id");
    }
    protected AbstractDynCostSnapShotProTypEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 动态成本快照产品类型分录 's 动态成本快照 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 产品类型Idproperty 
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
     * Object:动态成本快照产品类型分录's 目标可售单方property 
     */
    public java.math.BigDecimal getAimSaleUnitAmt()
    {
        return getBigDecimal("aimSaleUnitAmt");
    }
    public void setAimSaleUnitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimSaleUnitAmt", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 目标property 
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
     * Object:动态成本快照产品类型分录's 已发生property 
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
     * Object:动态成本快照产品类型分录's 未发生property 
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
     * Object:动态成本快照产品类型分录's 动态可售单方property 
     */
    public java.math.BigDecimal getDynSaleUnitAmt()
    {
        return getBigDecimal("dynSaleUnitAmt");
    }
    public void setDynSaleUnitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynSaleUnitAmt", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 动态property 
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
     * Object:动态成本快照产品类型分录's 已实现成本property 
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
     * Object:动态成本快照产品类型分录's 目标指标分摊目标成本property 
     */
    public java.math.BigDecimal getAimAimCostAmt()
    {
        return getBigDecimal("aimAimCostAmt");
    }
    public void setAimAimCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimAimCostAmt", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 目标指标分摊可售单方property 
     */
    public java.math.BigDecimal getAimAimSaleUnitAmt()
    {
        return getBigDecimal("aimAimSaleUnitAmt");
    }
    public void setAimAimSaleUnitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimAimSaleUnitAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC1B8CC1");
    }
}