package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingAreaEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildingAreaEntryInfo()
    {
        this("id");
    }
    protected AbstractBuildingAreaEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 楼层面积分录 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("head", item);
    }
    /**
     * Object:楼层面积分录's 可租面积property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ED873A16");
    }
}