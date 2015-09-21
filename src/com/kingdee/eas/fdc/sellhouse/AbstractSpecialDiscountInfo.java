package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialDiscountInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSpecialDiscountInfo()
    {
        this("id");
    }
    protected AbstractSpecialDiscountInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryCollection());
        put("agioEntry", new com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryCollection());
    }
    /**
     * Object: 特殊折扣优惠单 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryCollection)get("entrys");
    }
    /**
     * Object:特殊折扣优惠单's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:特殊折扣优惠单's 项目名称property 
     */
    public String getItemName()
    {
        return getString("itemName");
    }
    public void setItemName(String item)
    {
        setString("itemName", item);
    }
    /**
     * Object:特殊折扣优惠单's 单据名称property 
     */
    public String getUnitPriceName()
    {
        return getString("unitPriceName");
    }
    public void setUnitPriceName(String item)
    {
        setString("unitPriceName", item);
    }
    /**
     * Object: 特殊折扣优惠单 's 客户 property 
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
     * Object:特殊折扣优惠单's 员工优惠property 
     */
    public boolean isStaffDiscount()
    {
        return getBoolean("staffDiscount");
    }
    public void setStaffDiscount(boolean item)
    {
        setBoolean("staffDiscount", item);
    }
    /**
     * Object:特殊折扣优惠单's 员工姓名property 
     */
    public String getStaffName()
    {
        return getString("staffName");
    }
    public void setStaffName(String item)
    {
        setString("staffName", item);
    }
    /**
     * Object:特殊折扣优惠单's 员工级别property 
     */
    public String getStaffRank()
    {
        return getString("staffRank");
    }
    public void setStaffRank(String item)
    {
        setString("staffRank", item);
    }
    /**
     * Object:特殊折扣优惠单's 与员工关系property 
     */
    public String getStaffRelations()
    {
        return getString("staffRelations");
    }
    public void setStaffRelations(String item)
    {
        setString("staffRelations", item);
    }
    /**
     * Object:特殊折扣优惠单's 建筑面积property 
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
     * Object:特殊折扣优惠单's 建筑单价property 
     */
    public java.math.BigDecimal getBuildingPrice()
    {
        return getBigDecimal("buildingPrice");
    }
    public void setBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPrice", item);
    }
    /**
     * Object:特殊折扣优惠单's 套内面积property 
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
     * Object:特殊折扣优惠单's 套内单价property 
     */
    public java.math.BigDecimal getRoomPrice()
    {
        return getBigDecimal("roomPrice");
    }
    public void setRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomPrice", item);
    }
    /**
     * Object:特殊折扣优惠单's 标准总价property 
     */
    public java.math.BigDecimal getStandardTotalAmount()
    {
        return getBigDecimal("standardTotalAmount");
    }
    public void setStandardTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalAmount", item);
    }
    /**
     * Object:特殊折扣优惠单's 优惠金额property 
     */
    public java.math.BigDecimal getDiscountAmount()
    {
        return getBigDecimal("discountAmount");
    }
    public void setDiscountAmount(java.math.BigDecimal item)
    {
        setBigDecimal("discountAmount", item);
    }
    /**
     * Object:特殊折扣优惠单's 优惠后房间总价property 
     */
    public java.math.BigDecimal getDiscountAfAmount()
    {
        return getBigDecimal("discountAfAmount");
    }
    public void setDiscountAfAmount(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfAmount", item);
    }
    /**
     * Object:特殊折扣优惠单's 管理优惠比例property 
     */
    public java.math.BigDecimal getDiscountPercent()
    {
        return getBigDecimal("discountPercent");
    }
    public void setDiscountPercent(java.math.BigDecimal item)
    {
        setBigDecimal("discountPercent", item);
    }
    /**
     * Object:特殊折扣优惠单's 优惠后建筑单价property 
     */
    public java.math.BigDecimal getDiscountAfBPrice()
    {
        return getBigDecimal("discountAfBPrice");
    }
    public void setDiscountAfBPrice(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfBPrice", item);
    }
    /**
     * Object:特殊折扣优惠单's 优惠后套内单价property 
     */
    public java.math.BigDecimal getDiscountAfRPrice()
    {
        return getBigDecimal("discountAfRPrice");
    }
    public void setDiscountAfRPrice(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfRPrice", item);
    }
    /**
     * Object: 特殊折扣优惠单 's 房间 property 
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
     * Object: 特殊折扣优惠单 's 付款方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payType", item);
    }
    /**
     * Object: 特殊折扣优惠单 's 租售项目 property 
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
     * Object:特殊折扣优惠单's 情况说明property 
     */
    public String getCaseInfo()
    {
        return getString("caseInfo");
    }
    public void setCaseInfo(String item)
    {
        setString("caseInfo", item);
    }
    /**
     * Object:特殊折扣优惠单's 状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getBizState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object:特殊折扣优惠单's 审批时间property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object:特殊折扣优惠单's 总经理折扣property 
     */
    public java.math.BigDecimal getManagerAgio()
    {
        return getBigDecimal("managerAgio");
    }
    public void setManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("managerAgio", item);
    }
    /**
     * Object:特殊折扣优惠单's 案场经理折扣property 
     */
    public java.math.BigDecimal getSceneManagerAgio()
    {
        return getBigDecimal("sceneManagerAgio");
    }
    public void setSceneManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("sceneManagerAgio", item);
    }
    /**
     * Object:特殊折扣优惠单's 营销总监折扣property 
     */
    public java.math.BigDecimal getSalesDirectorAgio()
    {
        return getBigDecimal("salesDirectorAgio");
    }
    public void setSalesDirectorAgio(java.math.BigDecimal item)
    {
        setBigDecimal("salesDirectorAgio", item);
    }
    /**
     * Object:特殊折扣优惠单's 客户关系property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountRelateEnum getRelate()
    {
        return com.kingdee.eas.fdc.sellhouse.SpecialDiscountRelateEnum.getEnum(getString("relate"));
    }
    public void setRelate(com.kingdee.eas.fdc.sellhouse.SpecialDiscountRelateEnum item)
    {
		if (item != null) {
        setString("relate", item.getValue());
		}
    }
    /**
     * Object:特殊折扣优惠单's 底价property 
     */
    public java.math.BigDecimal getBaseStandardPrice()
    {
        return getBigDecimal("baseStandardPrice");
    }
    public void setBaseStandardPrice(java.math.BigDecimal item)
    {
        setBigDecimal("baseStandardPrice", item);
    }
    /**
     * Object:特殊折扣优惠单's 折扣说明property 
     */
    public String getAgioDesc()
    {
        return getString("agioDesc");
    }
    public void setAgioDesc(String item)
    {
        setString("agioDesc", item);
    }
    /**
     * Object: 特殊折扣优惠单 's 折扣分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryCollection getAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryCollection)get("agioEntry");
    }
    /**
     * Object:特殊折扣优惠单's 委托人property 
     */
    public String getOnePerson()
    {
        return getString("onePerson");
    }
    public void setOnePerson(String item)
    {
        setString("onePerson", item);
    }
    /**
     * Object:特殊折扣优惠单's 被委托人property 
     */
    public String getTwoPerson()
    {
        return getString("twoPerson");
    }
    public void setTwoPerson(String item)
    {
        setString("twoPerson", item);
    }
    /**
     * Object: 特殊折扣优惠单 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductTyupe()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productTyupe");
    }
    public void setProductTyupe(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productTyupe", item);
    }
    /**
     * Object:特殊折扣优惠单's 特殊折扣优惠比例property 
     */
    public java.math.BigDecimal getBasePercent()
    {
        return getBigDecimal("basePercent");
    }
    public void setBasePercent(java.math.BigDecimal item)
    {
        setBigDecimal("basePercent", item);
    }
    /**
     * Object:特殊折扣优惠单's 单价差额property 
     */
    public java.math.BigDecimal getSubPrice()
    {
        return getBigDecimal("subPrice");
    }
    public void setSubPrice(java.math.BigDecimal item)
    {
        setBigDecimal("subPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6012F8DF");
    }
}