package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeRoomInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractChangeRoomInfo()
    {
        this("id");
    }
    protected AbstractChangeRoomInfo(String pkField)
    {
        super(pkField);
        put("changeEntrys", new com.kingdee.eas.fdc.sellhouse.ChangeEntryCollection());
    }
    /**
     * Object: 换房 's 老房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getOldRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("oldRoom");
    }
    public void setOldRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("oldRoom", item);
    }
    /**
     * Object: 换房 's 老认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getOldPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("oldPurchase");
    }
    public void setOldPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("oldPurchase", item);
    }
    /**
     * Object: 换房 's 新房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getNewRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("newRoom");
    }
    public void setNewRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("newRoom", item);
    }
    /**
     * Object: 换房 's 新认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getNewPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("newPurchase");
    }
    public void setNewPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("newPurchase", item);
    }
    /**
     * Object:换房's 换房日期property 
     */
    public java.util.Date getChangeDate()
    {
        return getDate("changeDate");
    }
    public void setChangeDate(java.util.Date item)
    {
        setDate("changeDate", item);
    }
    /**
     * Object:换房's 原实收总额property 
     */
    public java.math.BigDecimal getPreActTotalAmount()
    {
        return getBigDecimal("preActTotalAmount");
    }
    public void setPreActTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preActTotalAmount", item);
    }
    /**
     * Object:换房's 转款金额property 
     */
    public java.math.BigDecimal getTransferAmount()
    {
        return getBigDecimal("transferAmount");
    }
    public void setTransferAmount(java.math.BigDecimal item)
    {
        setBigDecimal("transferAmount", item);
    }
    /**
     * Object: 换房 's 转入款项 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getTrsfMonDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("trsfMonDefine");
    }
    public void setTrsfMonDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("trsfMonDefine", item);
    }
    /**
     * Object: 换房 's 转款科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getTrsfAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("trsfAccount");
    }
    public void setTrsfAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("trsfAccount", item);
    }
    /**
     * Object:换房's 费用结算对象property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum getFeeDealObject()
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum.getEnum(getString("feeDealObject"));
    }
    public void setFeeDealObject(com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum item)
    {
		if (item != null) {
        setString("feeDealObject", item.getValue());
		}
    }
    /**
     * Object:换房's 费用金额property 
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
     * Object: 换房 's 费用款项 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getFeeMonDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("feeMonDefine");
    }
    public void setFeeMonDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("feeMonDefine", item);
    }
    /**
     * Object: 换房 's 费用科目 property 
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
     * Object:换房's 新认购单property 
     */
    public byte[] getTempPurChaseObj()
    {
        return (byte[])get("tempPurChaseObj");
    }
    public void setTempPurChaseObj(byte[] item)
    {
        put("tempPurChaseObj", item);
    }
    /**
     * Object: 换房 's 换房分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeEntryCollection getChangeEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeEntryCollection)get("changeEntrys");
    }
    /**
     * Object:换房's 已结算property 
     */
    public boolean isHasSettleMent()
    {
        return getBoolean("hasSettleMent");
    }
    public void setHasSettleMent(boolean item)
    {
        setBoolean("hasSettleMent", item);
    }
    /**
     * Object: 换房 's 换房原因 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SwapRoomReasonInfo getSwapReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.SwapRoomReasonInfo)get("swapReason");
    }
    public void setSwapReason(com.kingdee.eas.fdc.sellhouse.SwapRoomReasonInfo item)
    {
        put("swapReason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BCA781C6");
    }
}