package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPushManageHisEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPushManageHisEntryInfo()
    {
        this("id");
    }
    protected AbstractPushManageHisEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:推盘日志's 推盘日期property 
     */
    public java.util.Date getPushDate()
    {
        return getDate("pushDate");
    }
    public void setPushDate(java.util.Date item)
    {
        setDate("pushDate", item);
    }
    /**
     * Object:推盘日志's 撤盘日期property 
     */
    public java.util.Date getPullDate()
    {
        return getDate("pullDate");
    }
    public void setPullDate(java.util.Date item)
    {
        setDate("pullDate", item);
    }
    /**
     * Object: 推盘日志 's 单据头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PushManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PushManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PushManageInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 推盘日志 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:推盘日志's 状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.PushManageStateEnum getPushManageState()
    {
        return com.kingdee.eas.fdc.sellhouse.PushManageStateEnum.getEnum(getString("pushManageState"));
    }
    public void setPushManageState(com.kingdee.eas.fdc.sellhouse.PushManageStateEnum item)
    {
		if (item != null) {
        setString("pushManageState", item.getValue());
		}
    }
    /**
     * Object: 推盘日志 's 撤盘人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPuller()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("puller");
    }
    public void setPuller(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("puller", item);
    }
    /**
     * Object: 推盘日志 's 推盘人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPusher()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("pusher");
    }
    public void setPusher(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("pusher", item);
    }
    /**
     * Object: 推盘日志 's 撤盘组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:推盘日志's 开盘日期property 
     */
    public java.util.Date getOpenDate()
    {
        return getDate("openDate");
    }
    public void setOpenDate(java.util.Date item)
    {
        setDate("openDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9995485A");
    }
}