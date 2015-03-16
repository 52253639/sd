package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomAssistantInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractRoomAssistantInfo()
    {
        this("id");
    }
    protected AbstractRoomAssistantInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房间辅助资料 's 房间辅助类型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo getType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo)get("type");
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo item)
    {
        put("type", item);
    }
    /**
     * Object:房间辅助资料's 是否默认property 
     */
    public boolean isIsDefault()
    {
        return getBoolean("isDefault");
    }
    public void setIsDefault(boolean item)
    {
        setBoolean("isDefault", item);
    }
    /**
     * Object: 房间辅助资料 's 销售项目 property 
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
     * Object: 房间辅助资料 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC8DAD48");
    }
}