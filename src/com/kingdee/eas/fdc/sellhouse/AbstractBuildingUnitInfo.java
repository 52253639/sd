package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingUnitInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildingUnitInfo()
    {
        this("id");
    }
    protected AbstractBuildingUnitInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 楼栋单元 's 楼栋 property 
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
     * Object:楼栋单元's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:楼栋单元's 是否有单元property 
     */
    public boolean isHaveUnit()
    {
        return getBoolean("haveUnit");
    }
    public void setHaveUnit(boolean item)
    {
        setBoolean("haveUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FDBCDCB3");
    }
}