package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyRoomEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTenancyRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyRoomEntryInfo(String pkField)
    {
        super(pkField);
        put("equipments", new com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection());
        put("tenRoomCharges", new com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection());
        put("roomPayList", new com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection());
        put("dealAmounts", new com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection());
    }
    /**
     * Object: 认租单房间分录 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancy()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancy");
    }
    public void setTenancy(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancy", item);
    }
    /**
     * Object: 认租单房间分录 's 付款计划 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection getRoomPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection)get("roomPayList");
    }
    /**
     * Object: 认租单房间分录 's 房间 property 
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
     * Object:认租单房间分录's 房间长编码property 
     */
    public String getRoomLongNum()
    {
        return getString("roomLongNum");
    }
    public void setRoomLongNum(String item)
    {
        setString("roomLongNum", item);
    }
    /**
     * Object:认租单房间分录's 成交租金类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getDealRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("dealRentType"));
    }
    public void setDealRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("dealRentType", item.getValue());
		}
    }
    /**
     * Object:认租单房间分录's 房间成交租价property 
     */
    public java.math.BigDecimal getDealRoomRent()
    {
        return getBigDecimal("dealRoomRent");
    }
    public void setDealRoomRent(java.math.BigDecimal item)
    {
        setBigDecimal("dealRoomRent", item);
    }
    /**
     * Object:认租单房间分录's 成交房租单价property 
     */
    public java.math.BigDecimal getDealRoomRentPrice()
    {
        return getBigDecimal("dealRoomRentPrice");
    }
    public void setDealRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dealRoomRentPrice", item);
    }
    /**
     * Object:认租单房间分录's 原租金类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getStandardRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("standardRentType"));
    }
    public void setStandardRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("standardRentType", item.getValue());
		}
    }
    /**
     * Object:认租单房间分录's 标准房间租价property 
     */
    public java.math.BigDecimal getStandardRoomRent()
    {
        return getBigDecimal("standardRoomRent");
    }
    public void setStandardRoomRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardRoomRent", item);
    }
    /**
     * Object:认租单房间分录's 标准房租单价property 
     */
    public java.math.BigDecimal getStandardRoomRentPrice()
    {
        return getBigDecimal("standardRoomRentPrice");
    }
    public void setStandardRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("standardRoomRentPrice", item);
    }
    /**
     * Object:认租单房间分录's 起始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:认租单房间分录's 结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:认租单房间分录's 实际交房日期property 
     */
    public java.util.Date getActDeliveryRoomDate()
    {
        return getDate("actDeliveryRoomDate");
    }
    public void setActDeliveryRoomDate(java.util.Date item)
    {
        setDate("actDeliveryRoomDate", item);
    }
    /**
     * Object:认租单房间分录's 到期标志property 
     */
    public com.kingdee.eas.fdc.tenancy.FlagAtTermEnum getFlagAtTerm()
    {
        return com.kingdee.eas.fdc.tenancy.FlagAtTermEnum.getEnum(getString("flagAtTerm"));
    }
    public void setFlagAtTerm(com.kingdee.eas.fdc.tenancy.FlagAtTermEnum item)
    {
		if (item != null) {
        setString("flagAtTerm", item.getValue());
		}
    }
    /**
     * Object:认租单房间分录's 计租面积（原建筑面积）property 
     */
    public java.math.BigDecimal getBuildingArea()
    {
        return getBigDecimal("buildingArea");
    }
    public void setBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildingArea", item);
    }
    /**
     * Object:认租单房间分录's 实际退租日期property 
     */
    public java.util.Date getActQuitTenDate()
    {
        return getDate("actQuitTenDate");
    }
    public void setActQuitTenDate(java.util.Date item)
    {
        setDate("actQuitTenDate", item);
    }
    /**
     * Object:认租单房间分录's 房间分录状态property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenRoomState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenRoomState"));
    }
    public void setTenRoomState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenRoomState", item.getValue());
		}
    }
    /**
     * Object:认租单房间分录's 房间剩余押金property 
     */
    public java.math.BigDecimal getRemainDepositAmount()
    {
        return getBigDecimal("remainDepositAmount");
    }
    public void setRemainDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("remainDepositAmount", item);
    }
    /**
     * Object:认租单房间分录's 房间总租金property 
     */
    public java.math.BigDecimal getRoomTotalRent()
    {
        return getBigDecimal("roomTotalRent");
    }
    public void setRoomTotalRent(java.math.BigDecimal item)
    {
        setBigDecimal("roomTotalRent", item);
    }
    /**
     * Object: 认租单房间分录 's 配套设备 property 
     */
    public com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection getEquipments()
    {
        return (com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection)get("equipments");
    }
    /**
     * Object: 认租单房间分录 's 收费项目 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection getTenRoomCharges()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection)get("tenRoomCharges");
    }
    /**
     * Object:认租单房间分录's 押金金额(已作废)property 
     */
    public java.math.BigDecimal getDepositAmount()
    {
        return getBigDecimal("depositAmount");
    }
    public void setDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("depositAmount", item);
    }
    /**
     * Object:认租单房间分录's 首期租金property 
     */
    public java.math.BigDecimal getFirstPayAmount()
    {
        return getBigDecimal("firstPayAmount");
    }
    public void setFirstPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("firstPayAmount", item);
    }
    /**
     * Object: 认租单房间分录 's 成交价分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection getDealAmounts()
    {
        return (com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection)get("dealAmounts");
    }
    /**
     * Object:认租单房间分录's 租金计算方式property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModeEnum getTenancyModel()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyModeEnum.getEnum(getString("tenancyModel"));
    }
    public void setTenancyModel(com.kingdee.eas.fdc.tenancy.TenancyModeEnum item)
    {
		if (item != null) {
        setString("tenancyModel", item.getValue());
		}
    }
    /**
     * Object:认租单房间分录's 套内面积property 
     */
    public java.math.BigDecimal getRoomArea()
    {
        return getBigDecimal("roomArea");
    }
    public void setRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("roomArea", item);
    }
    /**
     * Object:认租单房间分录's 装修标准property 
     */
    public String getFitmentStandard()
    {
        return getString("fitmentStandard");
    }
    public void setFitmentStandard(String item)
    {
        setString("fitmentStandard", item);
    }
    /**
     * Object: 认租单房间分录 's 朝向 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo getDirection()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo)get("direction");
    }
    public void setDirection(com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo item)
    {
        put("direction", item);
    }
    /**
     * Object: 认租单房间分录 's 景观 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SightRequirementInfo getSight()
    {
        return (com.kingdee.eas.fdc.sellhouse.SightRequirementInfo)get("sight");
    }
    public void setSight(com.kingdee.eas.fdc.sellhouse.SightRequirementInfo item)
    {
        put("sight", item);
    }
    /**
     * Object: 认租单房间分录 's 户型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getRoomModel()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("roomModel");
    }
    public void setRoomModel(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("roomModel", item);
    }
    /**
     * Object: 认租单房间分录 's 房屋形式 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomFormInfo getRoomForm()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomFormInfo)get("roomForm");
    }
    public void setRoomForm(com.kingdee.eas.fdc.sellhouse.RoomFormInfo item)
    {
        put("roomForm", item);
    }
    /**
     * Object: 认租单房间分录 's 建筑性质 property 
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
     * Object: 认租单房间分录 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object: 认租单房间分录 's 产品描述 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ProductDetialInfo getProductDetail()
    {
        return (com.kingdee.eas.fdc.sellhouse.ProductDetialInfo)get("productDetail");
    }
    public void setProductDetail(com.kingdee.eas.fdc.sellhouse.ProductDetialInfo item)
    {
        put("productDetail", item);
    }
    /**
     * Object: 认租单房间分录 's 房间用途 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomUsageInfo getRoomUsage()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomUsageInfo)get("roomUsage");
    }
    public void setRoomUsage(com.kingdee.eas.fdc.sellhouse.RoomUsageInfo item)
    {
        put("roomUsage", item);
    }
    /**
     * Object:认租单房间分录's 交接状态property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleStateEnum getHandleState()
    {
        return com.kingdee.eas.fdc.tenancy.HandleStateEnum.getEnum(getString("handleState"));
    }
    public void setHandleState(com.kingdee.eas.fdc.tenancy.HandleStateEnum item)
    {
		if (item != null) {
        setString("handleState", item.getValue());
		}
    }
    /**
     * Object:认租单房间分录's 描述property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:认租单房间分录's 标准日单价property 
     */
    public java.math.BigDecimal getDayPrice()
    {
        return getBigDecimal("dayPrice");
    }
    public void setDayPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dayPrice", item);
    }
    /**
     * Object:认租单房间分录's 实际日单价（元/日/面积）property 
     */
    public java.math.BigDecimal getActDayprice()
    {
        return getBigDecimal("actDayprice");
    }
    public void setActDayprice(java.math.BigDecimal item)
    {
        setBigDecimal("actDayprice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B54A7CE0");
    }
}