package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractBuildingInfo()
    {
        this("id");
    }
    protected AbstractBuildingInfo(String pkField)
    {
        super(pkField);
        put("roomDes", new com.kingdee.eas.fdc.sellhouse.RoomDesCollection());
        put("banBasedataEntryList", new com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListCollection());
        put("areaEntry", new com.kingdee.eas.fdc.sellhouse.BuildingAreaEntryCollection());
        put("units", new com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection());
        put("roomModels", new com.kingdee.eas.fdc.sellhouse.RoomModelCollection());
        put("floorEntry", new com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection());
    }
    /**
     * Object: 楼栋 's 销售项目 property 
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
     * Object: 楼栋 's 房间描述 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomDesCollection getRoomDes()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomDesCollection)get("roomDes");
    }
    /**
     * Object: 楼栋 's 户型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelCollection getRoomModels()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelCollection)get("roomModels");
    }
    /**
     * Object: 楼栋 's 分区 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SubareaInfo getSubarea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SubareaInfo)get("subarea");
    }
    public void setSubarea(com.kingdee.eas.fdc.sellhouse.SubareaInfo item)
    {
        put("subarea", item);
    }
    /**
     * Object:楼栋's 单元个数property 
     */
    public int getUnitCount()
    {
        return getInt("unitCount");
    }
    public void setUnitCount(int item)
    {
        setInt("unitCount", item);
    }
    /**
     * Object: 楼栋 's 单元分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection getUnits()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection)get("units");
    }
    /**
     * Object:楼栋's 编码方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.CodingTypeEnum getCodingType()
    {
        return com.kingdee.eas.fdc.sellhouse.CodingTypeEnum.getEnum(getString("codingType"));
    }
    public void setCodingType(com.kingdee.eas.fdc.sellhouse.CodingTypeEnum item)
    {
		if (item != null) {
        setString("codingType", item.getValue());
		}
    }
    /**
     * Object:楼栋's 楼高property 
     */
    public java.math.BigDecimal getBuildingHeight()
    {
        return getBigDecimal("buildingHeight");
    }
    public void setBuildingHeight(java.math.BigDecimal item)
    {
        setBigDecimal("buildingHeight", item);
    }
    /**
     * Object: 楼栋 's 建筑属性 property 
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
     * Object:楼栋's 入住日期property 
     */
    public java.util.Date getJoinInDate()
    {
        return getDate("joinInDate");
    }
    public void setJoinInDate(java.util.Date item)
    {
        setDate("joinInDate", item);
    }
    /**
     * Object:楼栋's 竣工日期property 
     */
    public java.util.Date getCompleteDate()
    {
        return getDate("completeDate");
    }
    public void setCompleteDate(java.util.Date item)
    {
        setDate("completeDate", item);
    }
    /**
     * Object:楼栋's 地上楼层数property 
     */
    public int getFloorCount()
    {
        return getInt("floorCount");
    }
    public void setFloorCount(int item)
    {
        setInt("floorCount", item);
    }
    /**
     * Object:楼栋's 占地面积property 
     */
    public java.math.BigDecimal getBuildingTerraArea()
    {
        return getBigDecimal("buildingTerraArea");
    }
    public void setBuildingTerraArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildingTerraArea", item);
    }
    /**
     * Object: 楼栋 's 施工单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConstructPart()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("constructPart");
    }
    public void setConstructPart(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("constructPart", item);
    }
    /**
     * Object: 楼栋 's 建筑结构 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo getBuildingStructure()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo)get("buildingStructure");
    }
    public void setBuildingStructure(com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo item)
    {
        put("buildingStructure", item);
    }
    /**
     * Object:楼栋's 空调类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ConditionTypeEnum getConditionType()
    {
        return com.kingdee.eas.fdc.sellhouse.ConditionTypeEnum.getEnum(getString("conditionType"));
    }
    public void setConditionType(com.kingdee.eas.fdc.sellhouse.ConditionTypeEnum item)
    {
		if (item != null) {
        setString("conditionType", item.getValue());
		}
    }
    /**
     * Object:楼栋's 空调标准说明property 
     */
    public String getConditionStandard()
    {
        return getString("conditionStandard");
    }
    public void setConditionStandard(String item)
    {
        setString("conditionStandard", item);
    }
    /**
     * Object:楼栋's 网络类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.NetWorkTypeEnum getNetWorkType()
    {
        return com.kingdee.eas.fdc.sellhouse.NetWorkTypeEnum.getEnum(getString("netWorkType"));
    }
    public void setNetWorkType(com.kingdee.eas.fdc.sellhouse.NetWorkTypeEnum item)
    {
		if (item != null) {
        setString("netWorkType", item.getValue());
		}
    }
    /**
     * Object:楼栋's 网络情况说明property 
     */
    public String getNetWorkCircs()
    {
        return getString("netWorkCircs");
    }
    public void setNetWorkCircs(String item)
    {
        setString("netWorkCircs", item);
    }
    /**
     * Object:楼栋's 通讯情况说明property 
     */
    public String getCommunication()
    {
        return getString("communication");
    }
    public void setCommunication(String item)
    {
        setString("communication", item);
    }
    /**
     * Object:楼栋's 电力设施说明property 
     */
    public String getEstablishment()
    {
        return getString("establishment");
    }
    public void setEstablishment(String item)
    {
        setString("establishment", item);
    }
    /**
     * Object:楼栋's 消防设施说明property 
     */
    public String getFireControl()
    {
        return getString("fireControl");
    }
    public void setFireControl(String item)
    {
        setString("fireControl", item);
    }
    /**
     * Object:楼栋's 天花说明property 
     */
    public String getCeiling()
    {
        return getString("ceiling");
    }
    public void setCeiling(String item)
    {
        setString("ceiling", item);
    }
    /**
     * Object:楼栋's 地面说明property 
     */
    public String getFloorExplain()
    {
        return getString("floorExplain");
    }
    public void setFloorExplain(String item)
    {
        setString("floorExplain", item);
    }
    /**
     * Object:楼栋's 客用电梯数量property 
     */
    public int getCustomerLiftCount()
    {
        return getInt("customerLiftCount");
    }
    public void setCustomerLiftCount(int item)
    {
        setInt("customerLiftCount", item);
    }
    /**
     * Object:楼栋's 货用电梯数量property 
     */
    public int getCargoLiftCount()
    {
        return getInt("cargoLiftCount");
    }
    public void setCargoLiftCount(int item)
    {
        setInt("cargoLiftCount", item);
    }
    /**
     * Object:楼栋's 电梯详细情况说明property 
     */
    public String getLiftExplain()
    {
        return getString("liftExplain");
    }
    public void setLiftExplain(String item)
    {
        setString("liftExplain", item);
    }
    /**
     * Object:楼栋's 承重说明property 
     */
    public String getSupportWeight()
    {
        return getString("supportWeight");
    }
    public void setSupportWeight(String item)
    {
        setString("supportWeight", item);
    }
    /**
     * Object: 楼栋 's 产品类型 property 
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
     * Object:楼栋's 行政编号property 
     */
    public String getAdministrativeNumber()
    {
        return getString("administrativeNumber");
    }
    public void setAdministrativeNumber(String item)
    {
        setString("administrativeNumber", item);
    }
    /**
     * Object: 楼栋 's 楼层分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection getFloorEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection)get("floorEntry");
    }
    /**
     * Object:楼栋's 地下楼层数property 
     */
    public int getSubFloorCount()
    {
        return getInt("subFloorCount");
    }
    public void setSubFloorCount(int item)
    {
        setInt("subFloorCount", item);
    }
    /**
     * Object:楼栋's 短名称property 
     */
    public String getDisplayName()
    {
        return getString("displayName");
    }
    public void setDisplayName(String item)
    {
        setString("displayName", item);
    }
    /**
     * Object:楼栋's 开工日期property 
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
     * Object:楼栋's 建筑面积property 
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
     * Object:楼栋's 使用率property 
     */
    public java.math.BigDecimal getUseRate()
    {
        return getBigDecimal("useRate");
    }
    public void setUseRate(java.math.BigDecimal item)
    {
        setBigDecimal("useRate", item);
    }
    /**
     * Object:楼栋's 是否已取得销售许可证property 
     */
    public boolean isIsGetCertificated()
    {
        return getBoolean("isGetCertificated");
    }
    public void setIsGetCertificated(boolean item)
    {
        setBoolean("isGetCertificated", item);
    }
    /**
     * Object:楼栋's 销售许可property 
     */
    public String getSellCertification()
    {
        return getString("sellCertification");
    }
    public void setSellCertification(String item)
    {
        setString("sellCertification", item);
    }
    /**
     * Object:楼栋's 层高property 
     */
    public java.math.BigDecimal getFloorHeight()
    {
        return getBigDecimal("floorHeight");
    }
    public void setFloorHeight(java.math.BigDecimal item)
    {
        setBigDecimal("floorHeight", item);
    }
    /**
     * Object:楼栋's 取得预证日期property 
     */
    public java.util.Date getSellCertifiDate()
    {
        return getDate("sellCertifiDate");
    }
    public void setSellCertifiDate(java.util.Date item)
    {
        setDate("sellCertifiDate", item);
    }
    /**
     * Object:楼栋's 结构封顶property 
     */
    public java.util.Date getStructureDate()
    {
        return getDate("structureDate");
    }
    public void setStructureDate(java.util.Date item)
    {
        setDate("structureDate", item);
    }
    /**
     * Object: 楼栋 's 主数据楼栋 property 
     */
    public com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo getBanBasedataEntry()
    {
        return (com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo)get("banBasedataEntry");
    }
    public void setBanBasedataEntry(com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo item)
    {
        put("banBasedataEntry", item);
    }
    /**
     * Object: 楼栋 's 主数据楼栋分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListCollection getBanBasedataEntryList()
    {
        return (com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListCollection)get("banBasedataEntryList");
    }
    /**
     * Object: 楼栋 's 楼层面积分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingAreaEntryCollection getAreaEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingAreaEntryCollection)get("areaEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("159C6E8F");
    }
}