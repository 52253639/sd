package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyReportInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomPropertyReportInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyReportInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房屋产权信息 's 头 property 
     */
    public com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo item)
    {
        put("head", item);
    }
    /**
     * Object:房屋产权信息's 房源名称property 
     */
    public String getRoomResName()
    {
        return getString("roomResName");
    }
    public void setRoomResName(String item)
    {
        setString("roomResName", item);
    }
    /**
     * Object:房屋产权信息's 房源性质property 
     */
    public String getRoomResProperty()
    {
        return getString("roomResProperty");
    }
    public void setRoomResProperty(String item)
    {
        setString("roomResProperty", item);
    }
    /**
     * Object:房屋产权信息's 地址property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:房屋产权信息's 面积property 
     */
    public String getArea()
    {
        return getString("area");
    }
    public void setArea(String item)
    {
        setString("area", item);
    }
    /**
     * Object:房屋产权信息's 产权人property 
     */
    public String getPropertyOwner()
    {
        return getString("propertyOwner");
    }
    public void setPropertyOwner(String item)
    {
        setString("propertyOwner", item);
    }
    /**
     * Object:房屋产权信息's 产权性质property 
     */
    public String getProperty()
    {
        return getString("property");
    }
    public void setProperty(String item)
    {
        setString("property", item);
    }
    /**
     * Object:房屋产权信息's 变动原因property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    /**
     * Object:房屋产权信息's 产权编号property 
     */
    public String getPropertyNumber()
    {
        return getString("propertyNumber");
    }
    public void setPropertyNumber(String item)
    {
        setString("propertyNumber", item);
    }
    /**
     * Object:房屋产权信息's 出征时间property 
     */
    public java.sql.Timestamp getCertifiedTime()
    {
        return getTimestamp("certifiedTime");
    }
    public void setCertifiedTime(java.sql.Timestamp item)
    {
        setTimestamp("certifiedTime", item);
    }
    /**
     * Object:房屋产权信息's 情况说明property 
     */
    public String getInformationnote()
    {
        return getString("informationnote");
    }
    public void setInformationnote(String item)
    {
        setString("informationnote", item);
    }
    /**
     * Object:房屋产权信息's 字段1property 
     */
    public String getRemark1()
    {
        return getString("remark1");
    }
    public void setRemark1(String item)
    {
        setString("remark1", item);
    }
    /**
     * Object:房屋产权信息's 字段2property 
     */
    public String getRemark2()
    {
        return getString("remark2");
    }
    public void setRemark2(String item)
    {
        setString("remark2", item);
    }
    /**
     * Object:房屋产权信息's 字段3property 
     */
    public String getRemark3()
    {
        return getString("remark3");
    }
    public void setRemark3(String item)
    {
        setString("remark3", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("80C3D68D");
    }
}