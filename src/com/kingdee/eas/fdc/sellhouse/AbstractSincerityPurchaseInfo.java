package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSincerityPurchaseInfo extends com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo implements Serializable 
{
    public AbstractSincerityPurchaseInfo()
    {
        this("id");
    }
    protected AbstractSincerityPurchaseInfo(String pkField)
    {
        super(pkField);
        put("changeRecordEntryTwo", new com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoCollection());
        put("intentionRooms", new com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryCollection());
        put("saleMansEntry", new com.kingdee.eas.fdc.sellhouse.SinPurSaleMansEntryCollection());
        put("sincerPriceEntrys", new com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection());
        put("customer", new com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection());
    }
    /**
     * Object: 预约排号 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection)get("customer");
    }
    /**
     * Object: 预约排号 's 盘次 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellOrderInfo getSellOrder()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellOrderInfo)get("sellOrder");
    }
    public void setSellOrder(com.kingdee.eas.fdc.sellhouse.SellOrderInfo item)
    {
        put("sellOrder", item);
    }
    /**
     * Object:预约排号's 盘次排号property 
     */
    public int getSellOrderArrangeNum()
    {
        return getInt("sellOrderArrangeNum");
    }
    public void setSellOrderArrangeNum(int item)
    {
        setInt("sellOrderArrangeNum", item);
    }
    /**
     * Object:预约排号's 房间排号property 
     */
    public int getRoomArrangeNum()
    {
        return getInt("roomArrangeNum");
    }
    public void setRoomArrangeNum(int item)
    {
        setInt("roomArrangeNum", item);
    }
    /**
     * Object:预约排号's 登记时间property 
     */
    public java.util.Date getBookDate()
    {
        return getDate("bookDate");
    }
    public void setBookDate(java.util.Date item)
    {
        setDate("bookDate", item);
    }
    /**
     * Object: 预约排号 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:预约排号's 诚意金property 
     */
    public java.math.BigDecimal getSincerityAmount()
    {
        return getBigDecimal("sincerityAmount");
    }
    public void setSincerityAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sincerityAmount", item);
    }
    /**
     * Object:预约排号's 状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum getSincerityState()
    {
        return com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum.getEnum(getString("sincerityState"));
    }
    public void setSincerityState(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum item)
    {
		if (item != null) {
        setString("sincerityState", item.getValue());
		}
    }
    /**
     * Object:预约排号's 有效时间property 
     */
    public java.util.Date getValidDate()
    {
        return getDate("validDate");
    }
    public void setValidDate(java.util.Date item)
    {
        setDate("validDate", item);
    }
    /**
     * Object: 预约排号 's 意向户型类别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo getRoomModelType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo)get("roomModelType");
    }
    public void setRoomModelType(com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo item)
    {
        put("roomModelType", item);
    }
    /**
     * Object:预约排号's 是否收款property 
     */
    public boolean isIsRev()
    {
        return getBoolean("isRev");
    }
    public void setIsRev(boolean item)
    {
        setBoolean("isRev", item);
    }
    /**
     * Object:预约排号's 收款日期property 
     */
    public java.util.Date getRevDate()
    {
        return getDate("revDate");
    }
    public void setRevDate(java.util.Date item)
    {
        setDate("revDate", item);
    }
    /**
     * Object:预约排号's 是否收款入账property 
     */
    public boolean isIsReceiveEnterAccount()
    {
        return getBoolean("isReceiveEnterAccount");
    }
    public void setIsReceiveEnterAccount(boolean item)
    {
        setBoolean("isReceiveEnterAccount", item);
    }
    /**
     * Object:预约排号's 收款标志property 
     */
    public boolean isReceiveSymobl()
    {
        return getBoolean("receiveSymobl");
    }
    public void setReceiveSymobl(boolean item)
    {
        setBoolean("receiveSymobl", item);
    }
    /**
     * Object: 预约排号 's 诚意认购单收款明细分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection getSincerPriceEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection)get("sincerPriceEntrys");
    }
    /**
     * Object: 预约排号 's 商机 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    /**
     * Object: 预约排号 's 更名记录分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoCollection getChangeRecordEntryTwo()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoCollection)get("changeRecordEntryTwo");
    }
    /**
     * Object: 预约排号 's 意向房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryCollection getIntentionRooms()
    {
        return (com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryCollection)get("intentionRooms");
    }
    /**
     * Object:预约排号's 收款金额property 
     */
    public java.math.BigDecimal getEntrysRevAmountSum()
    {
        return getBigDecimal("entrysRevAmountSum");
    }
    public void setEntrysRevAmountSum(java.math.BigDecimal item)
    {
        setBigDecimal("entrysRevAmountSum", item);
    }
    /**
     * Object:预约排号's 意向房间串property 
     */
    public String getIntentionRoomsStr()
    {
        return getString("intentionRoomsStr");
    }
    public void setIntentionRoomsStr(String item)
    {
        setString("intentionRoomsStr", item);
    }
    /**
     * Object:预约排号's 预约电话property 
     */
    public String getAppointmentPhone()
    {
        return getString("appointmentPhone");
    }
    public void setAppointmentPhone(String item)
    {
        setString("appointmentPhone", item);
    }
    /**
     * Object:预约排号's 项目排号property 
     */
    public int getProjectNum()
    {
        return getInt("projectNum");
    }
    public void setProjectNum(int item)
    {
        setInt("projectNum", item);
    }
    /**
     * Object:预约排号's 失效日期property 
     */
    public java.util.Date getInvalidationDate()
    {
        return getDate("invalidationDate");
    }
    public void setInvalidationDate(java.util.Date item)
    {
        setDate("invalidationDate", item);
    }
    /**
     * Object:预约排号's 预约人property 
     */
    public String getAppointmentPeople()
    {
        return getString("appointmentPeople");
    }
    public void setAppointmentPeople(String item)
    {
        setString("appointmentPeople", item);
    }
    /**
     * Object:预约排号's 客户组合property 
     */
    public String getCusStr()
    {
        return getString("cusStr");
    }
    public void setCusStr(String item)
    {
        setString("cusStr", item);
    }
    /**
     * Object: 预约排号 's 线索客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getCluesCus()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("cluesCus");
    }
    public void setCluesCus(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("cluesCus", item);
    }
    /**
     * Object: 预约排号 's 置业顾问分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SinPurSaleMansEntryCollection getSaleMansEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SinPurSaleMansEntryCollection)get("saleMansEntry");
    }
    /**
     * Object:预约排号's 置业顾问名称property 
     */
    public String getSaleManStr()
    {
        return getString("saleManStr");
    }
    public void setSaleManStr(String item)
    {
        setString("saleManStr", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4994CEDC");
    }
}