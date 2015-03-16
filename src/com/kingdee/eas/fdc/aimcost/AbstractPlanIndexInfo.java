package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanIndexInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPlanIndexInfo()
    {
        this("id");
    }
    protected AbstractPlanIndexInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection());
        put("customEntrys", new com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection());
    }
    /**
     * Object:规划指标's 总占地面积property 
     */
    public java.math.BigDecimal getTotalContainArea()
    {
        return getBigDecimal("totalContainArea");
    }
    public void setTotalContainArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalContainArea", item);
    }
    /**
     * Object:规划指标's 建筑用地面积property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:规划指标's 总建筑面积property 
     */
    public java.math.BigDecimal getTotalBuildArea()
    {
        return getBigDecimal("totalBuildArea");
    }
    public void setTotalBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalBuildArea", item);
    }
    /**
     * Object:规划指标's 建筑物占地面积 property 
     */
    public java.math.BigDecimal getBuildContainArea()
    {
        return getBigDecimal("buildContainArea");
    }
    public void setBuildContainArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildContainArea", item);
    }
    /**
     * Object:规划指标's 建筑密度 property 
     */
    public java.math.BigDecimal getBuildDensity()
    {
        return getBigDecimal("buildDensity");
    }
    public void setBuildDensity(java.math.BigDecimal item)
    {
        setBigDecimal("buildDensity", item);
    }
    /**
     * Object:规划指标's 绿地率property 
     */
    public java.math.BigDecimal getGreenAreaRate()
    {
        return getBigDecimal("greenAreaRate");
    }
    public void setGreenAreaRate(java.math.BigDecimal item)
    {
        setBigDecimal("greenAreaRate", item);
    }
    /**
     * Object:规划指标's 计容积率面积property 
     */
    public java.math.BigDecimal getCubageRateArea()
    {
        return getBigDecimal("cubageRateArea");
    }
    public void setCubageRateArea(java.math.BigDecimal item)
    {
        setBigDecimal("cubageRateArea", item);
    }
    /**
     * Object:规划指标's 容积率property 
     */
    public java.math.BigDecimal getCubageRate()
    {
        return getBigDecimal("cubageRate");
    }
    public void setCubageRate(java.math.BigDecimal item)
    {
        setBigDecimal("cubageRate", item);
    }
    /**
     * Object:规划指标's 道路用地合计property 
     */
    public java.math.BigDecimal getTotalRoadArea()
    {
        return getBigDecimal("totalRoadArea");
    }
    public void setTotalRoadArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalRoadArea", item);
    }
    /**
     * Object:规划指标's 沥青路面车行道 property 
     */
    public java.math.BigDecimal getPitchRoad()
    {
        return getBigDecimal("pitchRoad");
    }
    public void setPitchRoad(java.math.BigDecimal item)
    {
        setBigDecimal("pitchRoad", item);
    }
    /**
     * Object:规划指标's 砼路面车行道（停车场）property 
     */
    public java.math.BigDecimal getConcreteRoad()
    {
        return getBigDecimal("concreteRoad");
    }
    public void setConcreteRoad(java.math.BigDecimal item)
    {
        setBigDecimal("concreteRoad", item);
    }
    /**
     * Object:规划指标's 硬质铺装车行道 property 
     */
    public java.math.BigDecimal getHardRoad()
    {
        return getBigDecimal("hardRoad");
    }
    public void setHardRoad(java.math.BigDecimal item)
    {
        setBigDecimal("hardRoad", item);
    }
    /**
     * Object:规划指标's 硬质铺装广场 property 
     */
    public java.math.BigDecimal getHardSquare()
    {
        return getBigDecimal("hardSquare");
    }
    public void setHardSquare(java.math.BigDecimal item)
    {
        setBigDecimal("hardSquare", item);
    }
    /**
     * Object:规划指标's 硬质铺装人行道  property 
     */
    public java.math.BigDecimal getHardManRoad()
    {
        return getBigDecimal("hardManRoad");
    }
    public void setHardManRoad(java.math.BigDecimal item)
    {
        setBigDecimal("hardManRoad", item);
    }
    /**
     * Object:规划指标's 绿化用地合计 property 
     */
    public java.math.BigDecimal getTotalGreenArea()
    {
        return getBigDecimal("totalGreenArea");
    }
    public void setTotalGreenArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalGreenArea", item);
    }
    /**
     * Object:规划指标's 重要公共绿地 property 
     */
    public java.math.BigDecimal getImportPubGreenArea()
    {
        return getBigDecimal("importPubGreenArea");
    }
    public void setImportPubGreenArea(java.math.BigDecimal item)
    {
        setBigDecimal("importPubGreenArea", item);
    }
    /**
     * Object:规划指标's 组团宅间绿化  property 
     */
    public java.math.BigDecimal getHouseGreenArea()
    {
        return getBigDecimal("houseGreenArea");
    }
    public void setHouseGreenArea(java.math.BigDecimal item)
    {
        setBigDecimal("houseGreenArea", item);
    }
    /**
     * Object:规划指标's 底层私家花园 property 
     */
    public java.math.BigDecimal getPrivateGarden()
    {
        return getBigDecimal("privateGarden");
    }
    public void setPrivateGarden(java.math.BigDecimal item)
    {
        setBigDecimal("privateGarden", item);
    }
    /**
     * Object:规划指标's 水景面积 property 
     */
    public java.math.BigDecimal getWarterViewArea()
    {
        return getBigDecimal("warterViewArea");
    }
    public void setWarterViewArea(java.math.BigDecimal item)
    {
        setBigDecimal("warterViewArea", item);
    }
    /**
     * Object: 规划指标 's 产品指标分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection)get("entrys");
    }
    /**
     * Object:规划指标's 目标或可研成本测算property 
     */
    public String getHeadID()
    {
        return getString("headID");
    }
    public void setHeadID(String item)
    {
        setString("headID", item);
    }
    /**
     * Object: 规划指标 's 自定义分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection getCustomEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection)get("customEntrys");
    }
    /**
     * Object:规划指标's 公共配套用房property 
     */
    public java.math.BigDecimal getPublicSetHouse()
    {
        return getBigDecimal("publicSetHouse");
    }
    public void setPublicSetHouse(java.math.BigDecimal item)
    {
        setBigDecimal("publicSetHouse", item);
    }
    /**
     * Object:规划指标's 实际总建造面积property 
     */
    public java.math.BigDecimal getTotalConstructArea()
    {
        return getBigDecimal("totalConstructArea");
    }
    public void setTotalConstructArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalConstructArea", item);
    }
    /**
     * Object:规划指标's 地下室面积property 
     */
    public java.math.BigDecimal getBasementArea()
    {
        return getBigDecimal("basementArea");
    }
    public void setBasementArea(java.math.BigDecimal item)
    {
        setBigDecimal("basementArea", item);
    }
    /**
     * Object:规划指标's 精装修面积property 
     */
    public java.math.BigDecimal getDecorationArea()
    {
        return getBigDecimal("decorationArea");
    }
    public void setDecorationArea(java.math.BigDecimal item)
    {
        setBigDecimal("decorationArea", item);
    }
    /**
     * Object:规划指标's 地上建筑面积property 
     */
    public java.math.BigDecimal getGroundBuildArea()
    {
        return getBigDecimal("groundBuildArea");
    }
    public void setGroundBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("groundBuildArea", item);
    }
    /**
     * Object:规划指标's 地下建筑面积property 
     */
    public java.math.BigDecimal getUnderGroundBuildArea()
    {
        return getBigDecimal("underGroundBuildArea");
    }
    public void setUnderGroundBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("underGroundBuildArea", item);
    }
    /**
     * Object:规划指标's 商业建筑面积property 
     */
    public java.math.BigDecimal getCommBuildArea()
    {
        return getBigDecimal("commBuildArea");
    }
    public void setCommBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("commBuildArea", item);
    }
    /**
     * Object:规划指标's 建筑覆盖率property 
     */
    public java.math.BigDecimal getRateCoverbuildArea()
    {
        return getBigDecimal("rateCoverbuildArea");
    }
    public void setRateCoverbuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("rateCoverbuildArea", item);
    }
    /**
     * Object:规划指标's 小区围墙长度property 
     */
    public java.math.BigDecimal getLengthOfWall()
    {
        return getBigDecimal("lengthOfWall");
    }
    public void setLengthOfWall(java.math.BigDecimal item)
    {
        setBigDecimal("lengthOfWall", item);
    }
    /**
     * Object:规划指标's 大门数量property 
     */
    public int getDoorNumber()
    {
        return getInt("doorNumber");
    }
    public void setDoorNumber(int item)
    {
        setInt("doorNumber", item);
    }
    /**
     * Object:规划指标's 硬地率property 
     */
    public java.math.BigDecimal getRateOfHardFloor()
    {
        return getBigDecimal("rateOfHardFloor");
    }
    public void setRateOfHardFloor(java.math.BigDecimal item)
    {
        setBigDecimal("rateOfHardFloor", item);
    }
    /**
     * Object:规划指标's 小区道路面积property 
     */
    public java.math.BigDecimal getRoadArea()
    {
        return getBigDecimal("roadArea");
    }
    public void setRoadArea(java.math.BigDecimal item)
    {
        setBigDecimal("roadArea", item);
    }
    /**
     * Object:规划指标's 地上停车位个数property 
     */
    public int getGroundparkCarNum()
    {
        return getInt("groundparkCarNum");
    }
    public void setGroundparkCarNum(int item)
    {
        setInt("groundparkCarNum", item);
    }
    /**
     * Object:规划指标's 地下停车位property 
     */
    public int getUnderGroundparkCarNum()
    {
        return getInt("underGroundparkCarNum");
    }
    public void setUnderGroundparkCarNum(int item)
    {
        setInt("underGroundparkCarNum", item);
    }
    /**
     * Object:规划指标's 景观面积property 
     */
    public java.math.BigDecimal getSightSpotArea()
    {
        return getBigDecimal("sightSpotArea");
    }
    public void setSightSpotArea(java.math.BigDecimal item)
    {
        setBigDecimal("sightSpotArea", item);
    }
    /**
     * Object:规划指标's 总销售面积property 
     */
    public java.math.BigDecimal getTotalSellArea()
    {
        return getBigDecimal("totalSellArea");
    }
    public void setTotalSellArea(java.math.BigDecimal item)
    {
        setBigDecimal("totalSellArea", item);
    }
    /**
     * Object:规划指标's 用地性质property 
     */
    public String getLandUsage()
    {
        return getString("landUsage");
    }
    public void setLandUsage(String item)
    {
        setString("landUsage", item);
    }
    /**
     * Object:规划指标's 代征用地面积property 
     */
    public java.math.BigDecimal getRequisitionArea()
    {
        return getBigDecimal("requisitionArea");
    }
    public void setRequisitionArea(java.math.BigDecimal item)
    {
        setBigDecimal("requisitionArea", item);
    }
    /**
     * Object:规划指标's 住宅用地面积property 
     */
    public java.math.BigDecimal getHouseArea()
    {
        return getBigDecimal("houseArea");
    }
    public void setHouseArea(java.math.BigDecimal item)
    {
        setBigDecimal("houseArea", item);
    }
    /**
     * Object:规划指标's 其它用地面积property 
     */
    public java.math.BigDecimal getOtherArea()
    {
        return getBigDecimal("otherArea");
    }
    public void setOtherArea(java.math.BigDecimal item)
    {
        setBigDecimal("otherArea", item);
    }
    /**
     * Object:规划指标's 建筑限高property 
     */
    public java.math.BigDecimal getMaxHeight()
    {
        return getBigDecimal("maxHeight");
    }
    public void setMaxHeight(java.math.BigDecimal item)
    {
        setBigDecimal("maxHeight", item);
    }
    /**
     * Object:规划指标's 停车位配比要求property 
     */
    public String getParkingRequire()
    {
        return getString("parkingRequire");
    }
    public void setParkingRequire(String item)
    {
        setString("parkingRequire", item);
    }
    /**
     * Object:规划指标's 可售比property 
     */
    public java.math.BigDecimal getSaleRate()
    {
        return getBigDecimal("saleRate");
    }
    public void setSaleRate(java.math.BigDecimal item)
    {
        setBigDecimal("saleRate", item);
    }
    /**
     * Object:规划指标's 商业面积比property 
     */
    public java.math.BigDecimal getCommAreaRate()
    {
        return getBigDecimal("commAreaRate");
    }
    public void setCommAreaRate(java.math.BigDecimal item)
    {
        setBigDecimal("commAreaRate", item);
    }
    /**
     * Object:规划指标's 托儿所面积比property 
     */
    public java.math.BigDecimal getNurseryAreaRate()
    {
        return getBigDecimal("nurseryAreaRate");
    }
    public void setNurseryAreaRate(java.math.BigDecimal item)
    {
        setBigDecimal("nurseryAreaRate", item);
    }
    /**
     * Object:规划指标's 精装房面积比property 
     */
    public java.math.BigDecimal getClothboundRoomRate()
    {
        return getBigDecimal("clothboundRoomRate");
    }
    public void setClothboundRoomRate(java.math.BigDecimal item)
    {
        setBigDecimal("clothboundRoomRate", item);
    }
    /**
     * Object:规划指标's 其它政府要求送的面积property 
     */
    public java.math.BigDecimal getGiveArea()
    {
        return getBigDecimal("giveArea");
    }
    public void setGiveArea(java.math.BigDecimal item)
    {
        setBigDecimal("giveArea", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("768287B2");
    }
}