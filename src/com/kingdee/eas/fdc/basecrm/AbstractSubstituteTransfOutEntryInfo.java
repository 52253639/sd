package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSubstituteTransfOutEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSubstituteTransfOutEntryInfo()
    {
        this("id");
    }
    protected AbstractSubstituteTransfOutEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo getParent()
    {
        return (com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 关联单据类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RelatBizType getRevBillType()
    {
        return com.kingdee.eas.fdc.basecrm.RelatBizType.getEnum(getString("revBillType"));
    }
    public void setRevBillType(com.kingdee.eas.fdc.basecrm.RelatBizType item)
    {
		if (item != null) {
        setString("revBillType", item.getValue());
		}
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
     * Object:分录's 支付金额property 
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
     * Object:分录's 关联单据编码property 
     */
    public String getRelateBillNumber()
    {
        return getString("relateBillNumber");
    }
    public void setRelateBillNumber(String item)
    {
        setString("relateBillNumber", item);
    }
    /**
     * Object:分录's 关联交易单据明细idproperty 
     */
    public String getRelateBillEntryId()
    {
        return getString("relateBillEntryId");
    }
    public void setRelateBillEntryId(String item)
    {
        setString("relateBillEntryId", item);
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
     * Object:分录's 实收金额property 
     */
    public java.math.BigDecimal getActRevAmount()
    {
        return getBigDecimal("actRevAmount");
    }
    public void setActRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actRevAmount", item);
    }
    /**
     * Object:分录's 应收金额property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EC719538");
    }
}