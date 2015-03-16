package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuitRoomInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractQuitRoomInfo()
    {
        this("id");
    }
    protected AbstractQuitRoomInfo(String pkField)
    {
        super(pkField);
        put("quitEntrys", new com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection());
    }
    /**
     * Object: 退房单 's 认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    /**
     * Object: 退房单 's 房间 property 
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
     * Object:退房单's 退房日期property 
     */
    public java.util.Date getQuitDate()
    {
        return getDate("quitDate");
    }
    public void setQuitDate(java.util.Date item)
    {
        setDate("quitDate", item);
    }
    /**
     * Object:退房单's 备注property 
     */
    public String getQuitReason()
    {
        return getString("quitReason");
    }
    public void setQuitReason(String item)
    {
        setString("quitReason", item);
    }
    /**
     * Object:退房单's 新套内单价property 
     */
    public java.math.BigDecimal getNewRoomPrice()
    {
        return getBigDecimal("newRoomPrice");
    }
    public void setNewRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newRoomPrice", item);
    }
    /**
     * Object:退房单's 新建筑单价property 
     */
    public java.math.BigDecimal getNewBuildingPrice()
    {
        return getBigDecimal("newBuildingPrice");
    }
    public void setNewBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newBuildingPrice", item);
    }
    /**
     * Object:退房单's 是否调价property 
     */
    public boolean isIsAdjustPrice()
    {
        return getBoolean("isAdjustPrice");
    }
    public void setIsAdjustPrice(boolean item)
    {
        setBoolean("isAdjustPrice", item);
    }
    /**
     * Object: 退房单 's 可退金额分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection getQuitEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection)get("quitEntrys");
    }
    /**
     * Object: 退房单 's 费用款项 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getFeeMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("feeMoneyDefine");
    }
    public void setFeeMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("feeMoneyDefine", item);
    }
    /**
     * Object: 退房单 's 费用科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getFeeAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("feeAccount");
    }
    public void setFeeAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("feeAccount", item);
    }
    /**
     * Object:退房单's 费用金额property 
     */
    public java.math.BigDecimal getFeeAmount()
    {
        return getBigDecimal("feeAmount");
    }
    public void setFeeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("feeAmount", item);
    }
    /**
     * Object:退房单's 是否结算property 
     */
    public int getIsBlance()
    {
        return getInt("isBlance");
    }
    public void setIsBlance(int item)
    {
        setInt("isBlance", item);
    }
    /**
     * Object: 退房单 's 退房原因 property 
     */
    public com.kingdee.eas.fdc.sellhouse.QuitRoomReasonInfo getQuitRoomReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.QuitRoomReasonInfo)get("quitRoomReason");
    }
    public void setQuitRoomReason(com.kingdee.eas.fdc.sellhouse.QuitRoomReasonInfo item)
    {
        put("quitRoomReason", item);
    }
    /**
     * Object:退房单's 退房状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.QuitRoomStateEnum getQuitRoomState()
    {
        return com.kingdee.eas.fdc.sellhouse.QuitRoomStateEnum.getEnum(getString("quitRoomState"));
    }
    public void setQuitRoomState(com.kingdee.eas.fdc.sellhouse.QuitRoomStateEnum item)
    {
		if (item != null) {
        setString("quitRoomState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("771FC2CB");
    }
}