package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTransactionInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTransactionInfo()
    {
        this("id");
    }
    protected AbstractTransactionInfo(String pkField)
    {
        super(pkField);
        put("tranStateHis", new com.kingdee.eas.fdc.sellhouse.TranStateHisCollection());
        put("tranBusinessOverView", new com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection());
    }
    /**
     * Object:交易主线's 最新单据IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getBillId()
    {
        return getBOSUuid("billId");
    }
    public void setBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("billId", item);
    }
    /**
     * Object:交易主线's 交易发生日期property 
     */
    public java.util.Date getTranDate()
    {
        return getDate("tranDate");
    }
    public void setTranDate(java.util.Date item)
    {
        setDate("tranDate", item);
    }
    /**
     * Object:交易主线's 上一环节property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getPreLink()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("preLink"));
    }
    public void setPreLink(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("preLink", item.getValue());
		}
    }
    /**
     * Object:交易主线's 当前环节property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getCurrentLink()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("currentLink"));
    }
    public void setCurrentLink(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("currentLink", item.getValue());
		}
    }
    /**
     * Object: 交易主线 's 步骤环节历史 property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranStateHisCollection getTranStateHis()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranStateHisCollection)get("tranStateHis");
    }
    /**
     * Object:交易主线's 是否作废property 
     */
    public boolean isIsValid()
    {
        return getBoolean("isValid");
    }
    public void setIsValid(boolean item)
    {
        setBoolean("isValid", item);
    }
    /**
     * Object: 交易主线 's 房间 property 
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
     * Object: 交易主线 's 业务总览 property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection getTranBusinessOverView()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection)get("tranBusinessOverView");
    }
    /**
     * Object:交易主线's 客户名称property 
     */
    public String getCustomerNames()
    {
        return getString("customerNames");
    }
    public void setCustomerNames(String item)
    {
        setString("customerNames", item);
    }
    /**
     * Object: 交易主线 's 客户商机 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BB402523");
    }
}