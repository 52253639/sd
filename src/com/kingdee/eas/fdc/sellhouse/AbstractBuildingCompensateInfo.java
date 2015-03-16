package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingCompensateInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractBuildingCompensateInfo()
    {
        this("id");
    }
    protected AbstractBuildingCompensateInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Â¥¶°²¹²î 's Â¥¶° property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7E46DD26");
    }
}