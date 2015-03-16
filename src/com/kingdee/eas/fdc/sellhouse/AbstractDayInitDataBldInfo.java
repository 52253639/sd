package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayInitDataBldInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayInitDataBldInfo()
    {
        this("id");
    }
    protected AbstractDayInitDataBldInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 月结项目楼栋初始 's 项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 月结项目楼栋初始 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:月结项目楼栋初始's (已售套数)年初值property 
     */
    public int getYearRoomCount()
    {
        return getInt("yearRoomCount");
    }
    public void setYearRoomCount(int item)
    {
        setInt("yearRoomCount", item);
    }
    /**
     * Object:月结项目楼栋初始's (已售套数)累计初值property 
     */
    public int getInitRoomCount()
    {
        return getInt("initRoomCount");
    }
    public void setInitRoomCount(int item)
    {
        setInt("initRoomCount", item);
    }
    /**
     * Object:月结项目楼栋初始's (已售面积)年初值property 
     */
    public java.math.BigDecimal getYearAreaAmount()
    {
        return getBigDecimal("yearAreaAmount");
    }
    public void setYearAreaAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yearAreaAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (已售面积)累计初值property 
     */
    public java.math.BigDecimal getInitAreaAmount()
    {
        return getBigDecimal("initAreaAmount");
    }
    public void setInitAreaAmount(java.math.BigDecimal item)
    {
        setBigDecimal("initAreaAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (合同总价)年初值property 
     */
    public java.math.BigDecimal getYearContractAmount()
    {
        return getBigDecimal("yearContractAmount");
    }
    public void setYearContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yearContractAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (合同总价)累计初值property 
     */
    public java.math.BigDecimal getInitContractAmount()
    {
        return getBigDecimal("initContractAmount");
    }
    public void setInitContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("initContractAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (销售回款)年初值property 
     */
    public java.math.BigDecimal getYearRsvAmount()
    {
        return getBigDecimal("yearRsvAmount");
    }
    public void setYearRsvAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yearRsvAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (销售回款)累计初值property 
     */
    public java.math.BigDecimal getInitRsvAmount()
    {
        return getBigDecimal("initRsvAmount");
    }
    public void setInitRsvAmount(java.math.BigDecimal item)
    {
        setBigDecimal("initRsvAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (按揭回款)年初值property 
     */
    public java.math.BigDecimal getYearLoanAmount()
    {
        return getBigDecimal("yearLoanAmount");
    }
    public void setYearLoanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yearLoanAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (按揭回款)累计初值property 
     */
    public java.math.BigDecimal getInitLoanAmount()
    {
        return getBigDecimal("initLoanAmount");
    }
    public void setInitLoanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("initLoanAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (应收/退面积差额)年初值property 
     */
    public java.math.BigDecimal getYearCosateAmount()
    {
        return getBigDecimal("yearCosateAmount");
    }
    public void setYearCosateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yearCosateAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (应收/退面积差额)累计初值property 
     */
    public java.math.BigDecimal getInitCosateAmount()
    {
        return getBigDecimal("initCosateAmount");
    }
    public void setInitCosateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("initCosateAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (已收/退面积差额)年初值property 
     */
    public java.math.BigDecimal getYearCstRsvAmount()
    {
        return getBigDecimal("yearCstRsvAmount");
    }
    public void setYearCstRsvAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yearCstRsvAmount", item);
    }
    /**
     * Object:月结项目楼栋初始's (已收/退面积差额)累计初值property 
     */
    public java.math.BigDecimal getInitCstRsvAmount()
    {
        return getBigDecimal("initCstRsvAmount");
    }
    public void setInitCstRsvAmount(java.math.BigDecimal item)
    {
        setBigDecimal("initCstRsvAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("96C3347F");
    }
}