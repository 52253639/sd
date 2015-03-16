package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompeteItemInfo extends com.kingdee.eas.fdc.propertymgmt.PPMProjectDataBaseInfo implements Serializable 
{
    public AbstractCompeteItemInfo()
    {
        this("id");
    }
    protected AbstractCompeteItemInfo(String pkField)
    {
        super(pkField);
        put("SaleInfoEntry", new com.kingdee.eas.fdc.market.CompeteItemSaleInfoEntryCollection());
        put("MarketingEntry", new com.kingdee.eas.fdc.market.CompeteItemMarketingEntryCollection());
        put("UseMediaEntry", new com.kingdee.eas.fdc.market.CompeteItemUseMediaEntryCollection());
        put("PriceInfoEntry", new com.kingdee.eas.fdc.market.CompeteItemPriceInfoEntryCollection());
    }
    /**
     * Object:竞争项目's 投资商property 
     */
    public String getInvestor()
    {
        return getString("investor");
    }
    public void setInvestor(String item)
    {
        setString("investor", item);
    }
    /**
     * Object:竞争项目's 代理商property 
     */
    public String getAgent()
    {
        return getString("agent");
    }
    public void setAgent(String item)
    {
        setString("agent", item);
    }
    /**
     * Object:竞争项目's 占地面积property 
     */
    public java.math.BigDecimal getCoverArea()
    {
        return getBigDecimal("coverArea");
    }
    public void setCoverArea(java.math.BigDecimal item)
    {
        setBigDecimal("coverArea", item);
    }
    /**
     * Object:竞争项目's 建筑面积property 
     */
    public java.math.BigDecimal getArchitectArea()
    {
        return getBigDecimal("architectArea");
    }
    public void setArchitectArea(java.math.BigDecimal item)
    {
        setBigDecimal("architectArea", item);
    }
    /**
     * Object:竞争项目's 容积率property 
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
     * Object: 竞争项目 's 产品类型 property 
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
     * Object:竞争项目's 主力户型property 
     */
    public String getBruntRoomType()
    {
        return getString("bruntRoomType");
    }
    public void setBruntRoomType(String item)
    {
        setString("bruntRoomType", item);
    }
    /**
     * Object:竞争项目's 主力面积property 
     */
    public java.math.BigDecimal getBruntArea()
    {
        return getBigDecimal("bruntArea");
    }
    public void setBruntArea(java.math.BigDecimal item)
    {
        setBigDecimal("bruntArea", item);
    }
    /**
     * Object:竞争项目's 赠送面积property 
     */
    public java.math.BigDecimal getDonateArea()
    {
        return getBigDecimal("donateArea");
    }
    public void setDonateArea(java.math.BigDecimal item)
    {
        setBigDecimal("donateArea", item);
    }
    /**
     * Object:竞争项目's 停车位property 
     */
    public String getParkingLot()
    {
        return getString("parkingLot");
    }
    public void setParkingLot(String item)
    {
        setString("parkingLot", item);
    }
    /**
     * Object:竞争项目's 销售状态property 
     */
    public com.kingdee.eas.fdc.market.SaleState getSaleStute()
    {
        return com.kingdee.eas.fdc.market.SaleState.getEnum(getString("saleStute"));
    }
    public void setSaleStute(com.kingdee.eas.fdc.market.SaleState item)
    {
		if (item != null) {
        setString("saleStute", item.getValue());
		}
    }
    /**
     * Object:竞争项目's 动工时间property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:竞争项目's 竣工时间property 
     */
    public java.util.Date getFinishTime()
    {
        return getDate("finishTime");
    }
    public void setFinishTime(java.util.Date item)
    {
        setDate("finishTime", item);
    }
    /**
     * Object:竞争项目's 物业公司property 
     */
    public String getPropertyCompany()
    {
        return getString("propertyCompany");
    }
    public void setPropertyCompany(String item)
    {
        setString("propertyCompany", item);
    }
    /**
     * Object:竞争项目's 入伙时间property 
     */
    public java.util.Date getJoinTime()
    {
        return getDate("joinTime");
    }
    public void setJoinTime(java.util.Date item)
    {
        setDate("joinTime", item);
    }
    /**
     * Object:竞争项目's 使用年限property 
     */
    public java.util.Date getFromTime()
    {
        return getDate("fromTime");
    }
    public void setFromTime(java.util.Date item)
    {
        setDate("fromTime", item);
    }
    /**
     * Object:竞争项目's 到property 
     */
    public java.util.Date getToTime()
    {
        return getDate("toTime");
    }
    public void setToTime(java.util.Date item)
    {
        setDate("toTime", item);
    }
    /**
     * Object:竞争项目's 共property 
     */
    public java.math.BigDecimal getTotleYears()
    {
        return getBigDecimal("totleYears");
    }
    public void setTotleYears(java.math.BigDecimal item)
    {
        setBigDecimal("totleYears", item);
    }
    /**
     * Object:竞争项目's 管理费property 
     */
    public java.math.BigDecimal getManageCharge()
    {
        return getBigDecimal("manageCharge");
    }
    public void setManageCharge(java.math.BigDecimal item)
    {
        setBigDecimal("manageCharge", item);
    }
    /**
     * Object:竞争项目's 售楼电话property 
     */
    public String getSaleRoomTel()
    {
        return getString("saleRoomTel");
    }
    public void setSaleRoomTel(String item)
    {
        setString("saleRoomTel", item);
    }
    /**
     * Object:竞争项目's 最新均价property 
     */
    public java.math.BigDecimal getNewestAvrPrice()
    {
        return getBigDecimal("newestAvrPrice");
    }
    public void setNewestAvrPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newestAvrPrice", item);
    }
    /**
     * Object:竞争项目's 最新平均总价property 
     */
    public java.math.BigDecimal getNewestAvgAllAm()
    {
        return getBigDecimal("newestAvgAllAm");
    }
    public void setNewestAvgAllAm(java.math.BigDecimal item)
    {
        setBigDecimal("newestAvgAllAm", item);
    }
    /**
     * Object:竞争项目's 最新起价property 
     */
    public java.math.BigDecimal getNewestInitPrice()
    {
        return getBigDecimal("newestInitPrice");
    }
    public void setNewestInitPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newestInitPrice", item);
    }
    /**
     * Object:竞争项目's 最新最高价property 
     */
    public java.math.BigDecimal getNewestHighPrice()
    {
        return getBigDecimal("newestHighPrice");
    }
    public void setNewestHighPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newestHighPrice", item);
    }
    /**
     * Object:竞争项目's 地址property 
     */
    public String getAdress()
    {
        return getString("adress");
    }
    public void setAdress(String item)
    {
        setString("adress", item);
    }
    /**
     * Object:竞争项目's 小区配套设施property 
     */
    public String getVillageSupFacil()
    {
        return getString("villageSupFacil");
    }
    public void setVillageSupFacil(String item)
    {
        setString("villageSupFacil", item);
    }
    /**
     * Object:竞争项目's 周边环境及市政设施property 
     */
    public String getEnvMunicipalInf()
    {
        return getString("envMunicipalInf");
    }
    public void setEnvMunicipalInf(String item)
    {
        setString("envMunicipalInf", item);
    }
    /**
     * Object: 竞争项目 's 价格信息 property 
     */
    public com.kingdee.eas.fdc.market.CompeteItemPriceInfoEntryCollection getPriceInfoEntry()
    {
        return (com.kingdee.eas.fdc.market.CompeteItemPriceInfoEntryCollection)get("PriceInfoEntry");
    }
    /**
     * Object: 竞争项目 's 销售情况 property 
     */
    public com.kingdee.eas.fdc.market.CompeteItemSaleInfoEntryCollection getSaleInfoEntry()
    {
        return (com.kingdee.eas.fdc.market.CompeteItemSaleInfoEntryCollection)get("SaleInfoEntry");
    }
    /**
     * Object: 竞争项目 's 营销活动 property 
     */
    public com.kingdee.eas.fdc.market.CompeteItemMarketingEntryCollection getMarketingEntry()
    {
        return (com.kingdee.eas.fdc.market.CompeteItemMarketingEntryCollection)get("MarketingEntry");
    }
    /**
     * Object: 竞争项目 's 所用媒体 property 
     */
    public com.kingdee.eas.fdc.market.CompeteItemUseMediaEntryCollection getUseMediaEntry()
    {
        return (com.kingdee.eas.fdc.market.CompeteItemUseMediaEntryCollection)get("UseMediaEntry");
    }
    /**
     * Object: 竞争项目 's 开发商 property 
     */
    public com.kingdee.eas.fdc.market.DeveloperManageInfo getDeveloper()
    {
        return (com.kingdee.eas.fdc.market.DeveloperManageInfo)get("Developer");
    }
    public void setDeveloper(com.kingdee.eas.fdc.market.DeveloperManageInfo item)
    {
        put("Developer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("359F4D69");
    }
}