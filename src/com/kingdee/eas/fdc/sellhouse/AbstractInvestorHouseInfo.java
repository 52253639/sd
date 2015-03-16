package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvestorHouseInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractInvestorHouseInfo()
    {
        this("id");
    }
    protected AbstractInvestorHouseInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.InvestorHouseLinkmanEntrysCollection());
        put("trackRecords", new com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordCollection());
    }
    /**
     * Object:投资房源's 原注册名property 
     */
    public String getOldEnrollName()
    {
        return getString("oldEnrollName");
    }
    public void setOldEnrollName(String item)
    {
        setString("oldEnrollName", item);
    }
    /**
     * Object:投资房源's 楼盘位置property 
     */
    public String getOrderLocation()
    {
        return getString("orderLocation");
    }
    public void setOrderLocation(String item)
    {
        setString("orderLocation", item);
    }
    /**
     * Object:投资房源's 盘源分类property 
     */
    public com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum getOrderSourceClass()
    {
        return com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum.getEnum(getString("orderSourceClass"));
    }
    public void setOrderSourceClass(com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum item)
    {
		if (item != null) {
        setString("orderSourceClass", item.getValue());
		}
    }
    /**
     * Object:投资房源's 指定楼栋property 
     */
    public String getAssignBuilding()
    {
        return getString("assignBuilding");
    }
    public void setAssignBuilding(String item)
    {
        setString("assignBuilding", item);
    }
    /**
     * Object:投资房源's 指定房间property 
     */
    public String getAssignRoom()
    {
        return getString("assignRoom");
    }
    public void setAssignRoom(String item)
    {
        setString("assignRoom", item);
    }
    /**
     * Object:投资房源's 面积说明property 
     */
    public String getAreaDescription()
    {
        return getString("areaDescription");
    }
    public void setAreaDescription(String item)
    {
        setString("areaDescription", item);
    }
    /**
     * Object:投资房源's 价格说明property 
     */
    public String getPriceDescription()
    {
        return getString("priceDescription");
    }
    public void setPriceDescription(String item)
    {
        setString("priceDescription", item);
    }
    /**
     * Object:投资房源's 欠费情况property 
     */
    public String getChargeComplexion()
    {
        return getString("chargeComplexion");
    }
    public void setChargeComplexion(String item)
    {
        setString("chargeComplexion", item);
    }
    /**
     * Object:投资房源's 产权状况property 
     */
    public com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum getOwnerShipState()
    {
        return com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum.getEnum(getString("OwnerShipState"));
    }
    public void setOwnerShipState(com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum item)
    {
		if (item != null) {
        setString("OwnerShipState", item.getValue());
		}
    }
    /**
     * Object:投资房源's 产权现状property 
     */
    public com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum getOwnerShipNew()
    {
        return com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum.getEnum(getString("ownerShipNew"));
    }
    public void setOwnerShipNew(com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum item)
    {
		if (item != null) {
        setString("ownerShipNew", item.getValue());
		}
    }
    /**
     * Object:投资房源's 业务日期property 
     */
    public java.util.Date getBizDate()
    {
        return getDate("bizDate");
    }
    public void setBizDate(java.util.Date item)
    {
        setDate("bizDate", item);
    }
    /**
     * Object: 投资房源 's 楼盘区域 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo getHabitationArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo)get("habitationArea");
    }
    public void setHabitationArea(com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo item)
    {
        put("habitationArea", item);
    }
    /**
     * Object: 投资房源 's 营销顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSalesman()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("salesman");
    }
    public void setSalesman(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("salesman", item);
    }
    /**
     * Object: 投资房源 's 建筑性质 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getBuildingProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("buildingProperty");
    }
    public void setBuildingProperty(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("buildingProperty", item);
    }
    /**
     * Object: 投资房源 's 登记人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getBooker()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("booker");
    }
    public void setBooker(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("booker", item);
    }
    /**
     * Object:投资房源's 物业现状property 
     */
    public String getWuyeActuality()
    {
        return getString("wuyeActuality");
    }
    public void setWuyeActuality(String item)
    {
        setString("wuyeActuality", item);
    }
    /**
     * Object: 投资房源 's 信息来源 property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceInfo getInformationSource()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceInfo)get("informationSource");
    }
    public void setInformationSource(com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceInfo item)
    {
        put("informationSource", item);
    }
    /**
     * Object: 投资房源 's 联系人分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseLinkmanEntrysCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseLinkmanEntrysCollection)get("entrys");
    }
    /**
     * Object: 投资房源 's 跟进记录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordCollection getTrackRecords()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordCollection)get("trackRecords");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("58D7C66D");
    }
}