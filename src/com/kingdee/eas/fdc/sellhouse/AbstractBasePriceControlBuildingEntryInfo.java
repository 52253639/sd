package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBasePriceControlBuildingEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractBasePriceControlBuildingEntryInfo()
    {
        this("id");
    }
    protected AbstractBasePriceControlBuildingEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: µ×¼Û¿ØÖÆÂ¥¶°·ÖÂ¼ 's µ×¼Û¿ØÖÆ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo item)
    {
        put("head", item);
    }
    /**
     * Object: µ×¼Û¿ØÖÆÂ¥¶°·ÖÂ¼ 's Â¥¶° property 
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
        return new BOSObjectType("4C5347DE");
    }
}