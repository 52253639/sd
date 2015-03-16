package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommerceChanceInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCommerceChanceInfo()
    {
        this("id");
    }
    protected AbstractCommerceChanceInfo(String pkField)
    {
        super(pkField);
        put("hopedRooms", new com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryCollection());
        put("changeRoom", new com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomCollection());
        put("shareSaleMan", new com.kingdee.eas.fdc.sellhouse.ShareSaleManCollection());
        put("questionPaperAnser", new com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection());
    }
    /**
     * Object:商机's 所属系统property 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getSysType()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("sysType"));
    }
    public void setSysType(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("sysType", item.getValue());
		}
    }
    /**
     * Object: 商机 's 房地产客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    /**
     * Object: 商机 's 租售项目 property 
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
     * Object:商机's 联系电话property 
     */
    public String getPhoneNumber()
    {
        return getString("phoneNumber");
    }
    public void setPhoneNumber(String item)
    {
        setString("phoneNumber", item);
    }
    /**
     * Object: 商机 's 级别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo getCommerceLevel()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo)get("commerceLevel");
    }
    public void setCommerceLevel(com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo item)
    {
        put("commerceLevel", item);
    }
    /**
     * Object:商机's 商机状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum getCommerceStatus()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum.getEnum(getString("commerceStatus"));
    }
    public void setCommerceStatus(com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum item)
    {
		if (item != null) {
        setString("commerceStatus", item.getValue());
		}
    }
    /**
     * Object: 商机 's 意向建筑性质 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getHopedBuildingProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("hopedBuildingProperty");
    }
    public void setHopedBuildingProperty(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("hopedBuildingProperty", item);
    }
    /**
     * Object: 商机 's 意向产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getHopedProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("hopedProductType");
    }
    public void setHopedProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("hopedProductType", item);
    }
    /**
     * Object: 商机 's 意向朝向 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo getHopedDirection()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo)get("hopedDirection");
    }
    public void setHopedDirection(com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo item)
    {
        put("hopedDirection", item);
    }
    /**
     * Object: 商机 's 面积需求 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo getHopedAreaRequirement()
    {
        return (com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo)get("hopedAreaRequirement");
    }
    public void setHopedAreaRequirement(com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo item)
    {
        put("hopedAreaRequirement", item);
    }
    /**
     * Object: 商机 's 户型需求 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo getHopedRoomModelType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo)get("hopedRoomModelType");
    }
    public void setHopedRoomModelType(com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo item)
    {
        put("hopedRoomModelType", item);
    }
    /**
     * Object: 商机 's 景观需求 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SightRequirementInfo getHopedSightRequirement()
    {
        return (com.kingdee.eas.fdc.sellhouse.SightRequirementInfo)get("hopedSightRequirement");
    }
    public void setHopedSightRequirement(com.kingdee.eas.fdc.sellhouse.SightRequirementInfo item)
    {
        put("hopedSightRequirement", item);
    }
    /**
     * Object: 商机 's 意向单价 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo getHopedUnitPrice()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo)get("hopedUnitPrice");
    }
    public void setHopedUnitPrice(com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo item)
    {
        put("hopedUnitPrice", item);
    }
    /**
     * Object: 商机 's 意向总价 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo getHopedTotalPrices()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo)get("hopedTotalPrices");
    }
    public void setHopedTotalPrices(com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo item)
    {
        put("hopedTotalPrices", item);
    }
    /**
     * Object: 商机 's 意向楼层 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedFloorInfo getHopedFloor()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedFloorInfo)get("hopedFloor");
    }
    public void setHopedFloor(com.kingdee.eas.fdc.sellhouse.HopedFloorInfo item)
    {
        put("hopedFloor", item);
    }
    /**
     * Object: 商机 's 商机原因 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo getBuyHouseReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo)get("buyHouseReason");
    }
    public void setBuyHouseReason(com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo item)
    {
        put("buyHouseReason", item);
    }
    /**
     * Object: 商机 's 意向强度 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopePitchInfo getHopedPitch()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopePitchInfo)get("hopedPitch");
    }
    public void setHopedPitch(com.kingdee.eas.fdc.sellhouse.HopePitchInfo item)
    {
        put("hopedPitch", item);
    }
    /**
     * Object: 商机 's 首付比例 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo getFirstPayProportion()
    {
        return (com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo)get("firstPayProportion");
    }
    public void setFirstPayProportion(com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo item)
    {
        put("firstPayProportion", item);
    }
    /**
     * Object:商机's 预计日期property 
     */
    public java.util.Date getIntendingDate()
    {
        return getDate("intendingDate");
    }
    public void setIntendingDate(java.util.Date item)
    {
        setDate("intendingDate", item);
    }
    /**
     * Object:商机's 预计成交额property 
     */
    public java.math.BigDecimal getIntendingMoney()
    {
        return getBigDecimal("intendingMoney");
    }
    public void setIntendingMoney(java.math.BigDecimal item)
    {
        setBigDecimal("intendingMoney", item);
    }
    /**
     * Object: 商机 's 营销顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSaleMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("saleMan");
    }
    public void setSaleMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("saleMan", item);
    }
    /**
     * Object:商机's 成交条件property 
     */
    public String getBargainOnCondition()
    {
        return getString("bargainOnCondition");
    }
    public void setBargainOnCondition(String item)
    {
        setString("bargainOnCondition", item);
    }
    /**
     * Object:商机's 终止原因property 
     */
    public String getTerminateReason()
    {
        return getString("terminateReason");
    }
    public void setTerminateReason(String item)
    {
        setString("terminateReason", item);
    }
    /**
     * Object: 商机 's 意向房源列表 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryCollection getHopedRooms()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryCollection)get("hopedRooms");
    }
    /**
     * Object:商机's 商机日期property 
     */
    public java.util.Date getCommerceDate()
    {
        return getDate("commerceDate");
    }
    public void setCommerceDate(java.util.Date item)
    {
        setDate("commerceDate", item);
    }
    /**
     * Object:商机's 置业目的property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum getCommerceIntention()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum.getEnum(getString("commerceIntention"));
    }
    public void setCommerceIntention(com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum item)
    {
		if (item != null) {
        setString("commerceIntention", item.getValue());
		}
    }
    /**
     * Object: 商机 's 产品描述 property 
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
     * Object: 商机 's 房屋形式 property 
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
     * Object:商机's 可付首期额property 
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
     * Object: 商机 's 问卷回答 property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection getQuestionPaperAnser()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection)get("questionPaperAnser");
    }
    /**
     * Object: 商机 's 客户信息 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: 商机 's 新商机级别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getNewLevel()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("newLevel");
    }
    public void setNewLevel(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("newLevel", item);
    }
    /**
     * Object: 商机 's 商机阶段 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getCommerceChanceStage()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("commerceChanceStage");
    }
    public void setCommerceChanceStage(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("commerceChanceStage", item);
    }
    /**
     * Object: 商机 's 置业目的 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getPurpose()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("purpose");
    }
    public void setPurpose(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("purpose", item);
    }
    /**
     * Object: 商机 's 商机产生的原因 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getCommerceChangeReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("commerceChangeReason");
    }
    public void setCommerceChangeReason(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("commerceChangeReason", item);
    }
    /**
     * Object:商机's 商机有效期property 
     */
    public java.util.Date getEffectiveDate()
    {
        return getDate("effectiveDate");
    }
    public void setEffectiveDate(java.util.Date item)
    {
        setDate("effectiveDate", item);
    }
    /**
     * Object:商机's 估算价值property 
     */
    public java.math.BigDecimal getWorth()
    {
        return getBigDecimal("worth");
    }
    public void setWorth(java.math.BigDecimal item)
    {
        setBigDecimal("worth", item);
    }
    /**
     * Object:商机's 成交概率property 
     */
    public java.math.BigDecimal getProbability()
    {
        return getBigDecimal("probability");
    }
    public void setProbability(java.math.BigDecimal item)
    {
        setBigDecimal("probability", item);
    }
    /**
     * Object:商机's 商机状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum getStatus()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum.getEnum(getString("status"));
    }
    public void setStatus(com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum item)
    {
		if (item != null) {
        setString("status", item.getValue());
		}
    }
    /**
     * Object:商机's 终止原因(废弃)property 
     */
    public String getStopReason()
    {
        return getString("stopReason");
    }
    public void setStopReason(String item)
    {
        setString("stopReason", item);
    }
    /**
     * Object:商机's 商机来源property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum getCommerceSrc()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum.getEnum(getString("commerceSrc"));
    }
    public void setCommerceSrc(com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum item)
    {
		if (item != null) {
        setString("commerceSrc", item.getValue());
		}
    }
    /**
     * Object: 商机 's 建筑性质 property 
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
     * Object: 商机 's 产品类型 property 
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
     * Object: 商机 's 景观 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getSign()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("sign");
    }
    public void setSign(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("sign", item);
    }
    /**
     * Object: 商机 's 户型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getRoomType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("roomType");
    }
    public void setRoomType(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("roomType", item);
    }
    /**
     * Object: 商机 's 面积范围 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getAreaScope()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("areaScope");
    }
    public void setAreaScope(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("areaScope", item);
    }
    /**
     * Object: 商机 's 楼层区间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getFloorScope()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("floorScope");
    }
    public void setFloorScope(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("floorScope", item);
    }
    /**
     * Object: 商机 's 总价范围 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getTotal()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("total");
    }
    public void setTotal(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("total", item);
    }
    /**
     * Object: 商机 's 单价区间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getPriceScope()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("priceScope");
    }
    public void setPriceScope(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("priceScope", item);
    }
    /**
     * Object: 商机 's 意向房源 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomCollection getChangeRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomCollection)get("changeRoom");
    }
    /**
     * Object: 商机 's 商机跟进 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo getTrack()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo)get("track");
    }
    public void setTrack(com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo item)
    {
        put("track", item);
    }
    /**
     * Object:商机's 原因说明property 
     */
    public String getReasonDesc()
    {
        return getString("reasonDesc");
    }
    public void setReasonDesc(String item)
    {
        setString("reasonDesc", item);
    }
    /**
     * Object: 商机 's 共享销售顾问列表 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareSaleManCollection getShareSaleMan()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareSaleManCollection)get("shareSaleMan");
    }
    /**
     * Object: 商机 's 朝向 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo getOrientations()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo)get("orientations");
    }
    public void setOrientations(com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo item)
    {
        put("orientations", item);
    }
    /**
     * Object: 商机 's 线索客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getCluesCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("cluesCustomer");
    }
    public void setCluesCustomer(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("cluesCustomer", item);
    }
    /**
     * Object:商机's 是否转认购property 
     */
    public boolean isIsToPur()
    {
        return getBoolean("isToPur");
    }
    public void setIsToPur(boolean item)
    {
        setBoolean("isToPur", item);
    }
    /**
     * Object:商机's 是否转预订property 
     */
    public boolean isIsToPre()
    {
        return getBoolean("isToPre");
    }
    public void setIsToPre(boolean item)
    {
        setBoolean("isToPre", item);
    }
    /**
     * Object:商机's 是否转签约property 
     */
    public boolean isIsToSign()
    {
        return getBoolean("isToSign");
    }
    public void setIsToSign(boolean item)
    {
        setBoolean("isToSign", item);
    }
    /**
     * Object:商机's 是否关联商机客户变更property 
     */
    public boolean isIsChange()
    {
        return getBoolean("isChange");
    }
    public void setIsChange(boolean item)
    {
        setBoolean("isChange", item);
    }
    /**
     * Object: 商机 's 终止原因 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getLinkStopReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("linkStopReason");
    }
    public void setLinkStopReason(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("linkStopReason", item);
    }
    /**
     * Object: 商机 's 工作区域 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo getWorkArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo)get("workArea");
    }
    public void setWorkArea(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo item)
    {
        put("workArea", item);
    }
    /**
     * Object: 商机 's 居住区域 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo getStayArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo)get("stayArea");
    }
    public void setStayArea(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo item)
    {
        put("stayArea", item);
    }
    /**
     * Object: 商机 's 意向户型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getIntentionType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("intentionType");
    }
    public void setIntentionType(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("intentionType", item);
    }
    /**
     * Object: 商机 's 媒体渠道 property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeInfo getClassify()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeInfo)get("classify");
    }
    public void setClassify(com.kingdee.eas.fdc.market.ChannelTypeInfo item)
    {
        put("classify", item);
    }
    /**
     * Object:商机's 最新跟进日期property 
     */
    public java.util.Date getTrackDate()
    {
        return getDate("trackDate");
    }
    public void setTrackDate(java.util.Date item)
    {
        setDate("trackDate", item);
    }
    /**
     * Object:商机's 最新跟进内容property 
     */
    public String getTrackContent()
    {
        return getString("trackContent");
    }
    public void setTrackContent(String item)
    {
        setString("trackContent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AB97E58A");
    }
}