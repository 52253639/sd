package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSubstituteAdjustEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSubstituteAdjustEntryInfo()
    {
        this("id");
    }
    protected AbstractSubstituteAdjustEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo getParent()
    {
        return (com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 关联单据类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RelatBizType getRelateBizType()
    {
        return com.kingdee.eas.fdc.basecrm.RelatBizType.getEnum(getString("relateBizType"));
    }
    public void setRelateBizType(com.kingdee.eas.fdc.basecrm.RelatBizType item)
    {
		if (item != null) {
        setString("relateBizType", item.getValue());
		}
    }
    /**
     * Object:分录's 关联单据编码property 
     */
    public String getRelateBizNumber()
    {
        return getString("relateBizNumber");
    }
    public void setRelateBizNumber(String item)
    {
        setString("relateBizNumber", item);
    }
    /**
     * Object:分录's 关联交易单据明细idproperty 
     */
    public String getRelateBizEntryId()
    {
        return getString("relateBizEntryId");
    }
    public void setRelateBizEntryId(String item)
    {
        setString("relateBizEntryId", item);
    }
    /**
     * Object: 分录 's 房间 property 
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
     * Object:分录's 签署日期property 
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
     * Object:分录's 合同总价property 
     */
    public java.math.BigDecimal getContactAmount()
    {
        return getBigDecimal("contactAmount");
    }
    public void setContactAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contactAmount", item);
    }
    /**
     * Object:分录's 原代收费用金额property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object:分录's 客户property 
     */
    public String getCustomer()
    {
        return getString("customer");
    }
    public void setCustomer(String item)
    {
        setString("customer", item);
    }
    /**
     * Object:分录's 关联业务单据idproperty 
     */
    public String getRelateBizId()
    {
        return getString("relateBizId");
    }
    public void setRelateBizId(String item)
    {
        setString("relateBizId", item);
    }
    /**
     * Object: 分录 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:分录's 新代收费用金额property 
     */
    public java.math.BigDecimal getNewPayAmount()
    {
        return getBigDecimal("newPayAmount");
    }
    public void setNewPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newPayAmount", item);
    }
    /**
     * Object:分录's 操作property 
     */
    public String getModifyType()
    {
        return getString("modifyType");
    }
    public void setModifyType(String item)
    {
        setString("modifyType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C112974D");
    }
}