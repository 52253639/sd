package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBusinessOverViewInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractBusinessOverViewInfo()
    {
        this("id");
    }
    protected AbstractBusinessOverViewInfo(String pkField)
    {
        super(pkField);
        put("shePayTypeHis", new com.kingdee.eas.fdc.sellhouse.ShePayTypeHisCollection());
    }
    /**
     * Object: 业务总览实体 's 房间 property 
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
     * Object:业务总览实体's 业务名称property 
     */
    public com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum getBusinessName()
    {
        return com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum.getEnum(getString("businessName"));
    }
    public void setBusinessName(com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum item)
    {
		if (item != null) {
        setString("businessName", item.getValue());
		}
    }
    /**
     * Object:业务总览实体's 应完成时间property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object:业务总览实体's 是否完成property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    /**
     * Object:业务总览实体's 实际完成时间property 
     */
    public java.util.Date getActualFinishDate()
    {
        return getDate("actualFinishDate");
    }
    public void setActualFinishDate(java.util.Date item)
    {
        setDate("actualFinishDate", item);
    }
    /**
     * Object:业务总览实体's 是否作废property 
     */
    public boolean isIsInvalid()
    {
        return getBoolean("isInvalid");
    }
    public void setIsInvalid(boolean item)
    {
        setBoolean("isInvalid", item);
    }
    /**
     * Object:业务总览实体's 单据当前状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranLinkEnum getState()
    {
        return com.kingdee.eas.fdc.sellhouse.TranLinkEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.sellhouse.TranLinkEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:业务总览实体's 付款明细id/业务明细idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getBusId()
    {
        return getBOSUuid("busId");
    }
    public void setBusId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("busId", item);
    }
    /**
     * Object:业务总览实体's 来源类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.BusTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.BusTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.BusTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:业务总览实体's 付款方案idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getShePayTypeId()
    {
        return getBOSUuid("shePayTypeId");
    }
    public void setShePayTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("shePayTypeId", item);
    }
    /**
     * Object: 业务总览实体 's 付款方案快照 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShePayTypeHisCollection getShePayTypeHis()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShePayTypeHisCollection)get("shePayTypeHis");
    }
    /**
     * Object: 业务总览实体 's 款项 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: 业务总览实体 's 交易单id property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D0101654");
    }
}