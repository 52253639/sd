package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractCostEntryInfo()
    {
        this("id");
    }
    protected AbstractCostEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 目标成本条目 's 父级成本科目 property 
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
     * Object:目标成本条目's 条目名称property 
     */
    public String getEntryName()
    {
        return getString("entryName");
    }
    public void setEntryName(String item)
    {
        setString("entryName", item);
    }
    /**
     * Object:目标成本条目's 工作量property 
     */
    public java.math.BigDecimal getWorkload()
    {
        return getBigDecimal("workload");
    }
    public void setWorkload(java.math.BigDecimal item)
    {
        setBigDecimal("workload", item);
    }
    /**
     * Object:目标成本条目's 单价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:目标成本条目's 单位property 
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
     * Object:目标成本条目's 目标成本property 
     */
    public java.math.BigDecimal getCostAmount()
    {
        return getBigDecimal("costAmount");
    }
    public void setCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("costAmount", item);
    }
    /**
     * Object: 目标成本条目 's 所属产品 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object: 目标成本条目 's 头 property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.AimCostInfo item)
    {
        put("head", item);
    }
    /**
     * Object:目标成本条目's 是否进成本数据库property 
     */
    public boolean isIsEnterDB()
    {
        return getBoolean("isEnterDB");
    }
    public void setIsEnterDB(boolean item)
    {
        setBoolean("isEnterDB", item);
    }
    /**
     * Object:目标成本条目's 测算分录property 
     */
    public String getMeasureEntryID()
    {
        return getString("measureEntryID");
    }
    public void setMeasureEntryID(String item)
    {
        setString("measureEntryID", item);
    }
    /**
     * Object:目标成本条目's 合约规划property 
     */
    public String getProgram()
    {
        return getString("program");
    }
    public void setProgram(String item)
    {
        setString("program", item);
    }
    /**
     * Object:目标成本条目's 备注property 
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
     * Object:目标成本条目's 变化原因property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E8E1892E");
    }
}