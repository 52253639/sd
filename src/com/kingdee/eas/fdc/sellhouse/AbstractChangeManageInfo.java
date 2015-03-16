package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeManageInfo extends com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo implements Serializable 
{
    public AbstractChangeManageInfo()
    {
        this("id");
    }
    protected AbstractChangeManageInfo(String pkField)
    {
        super(pkField);
        put("customerEntry", new com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection());
        put("payListEntry", new com.kingdee.eas.fdc.sellhouse.ChangePayListEntryCollection());
        put("roomAttachEntry", new com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryCollection());
        put("agioEntry", new com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection());
    }
    /**
     * Object:变更管理's 新单据IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getNewId()
    {
        return getBOSUuid("newId");
    }
    public void setNewId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("newId", item);
    }
    /**
     * Object:变更管理's 业务类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum getBizType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum.getEnum(getString("bizType"));
    }
    public void setBizType(com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum item)
    {
		if (item != null) {
        setString("bizType", item.getValue());
		}
    }
    /**
     * Object: 变更管理 's 客户分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection getCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection)get("customerEntry");
    }
    /**
     * Object: 变更管理 's 费用项目 property 
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
     * Object:变更管理's 实收总额property 
     */
    public java.math.BigDecimal getActAmount()
    {
        return getBigDecimal("actAmount");
    }
    public void setActAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actAmount", item);
    }
    /**
     * Object:变更管理's 费用金额property 
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
     * Object:变更管理's 可退金额property 
     */
    public java.math.BigDecimal getQuitAmount()
    {
        return getBigDecimal("quitAmount");
    }
    public void setQuitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("quitAmount", item);
    }
    /**
     * Object:变更管理's 面积补差property 
     */
    public java.math.BigDecimal getAreaCompensate()
    {
        return getBigDecimal("areaCompensate");
    }
    public void setAreaCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("areaCompensate", item);
    }
    /**
     * Object:变更管理's 规划补差property 
     */
    public java.math.BigDecimal getPlanningCompensate()
    {
        return getBigDecimal("planningCompensate");
    }
    public void setPlanningCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("planningCompensate", item);
    }
    /**
     * Object:变更管理's 现售补差property 
     */
    public java.math.BigDecimal getCashSalesCompensate()
    {
        return getBigDecimal("cashSalesCompensate");
    }
    public void setCashSalesCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("cashSalesCompensate", item);
    }
    /**
     * Object: 变更管理 's 折扣分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection getAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection)get("agioEntry");
    }
    /**
     * Object: 变更管理 's 附属房产分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryCollection getRoomAttachEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryCollection)get("roomAttachEntry");
    }
    /**
     * Object: 变更管理 's 客户付款分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangePayListEntryCollection getPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangePayListEntryCollection)get("payListEntry");
    }
    /**
     * Object:变更管理's 产生费用Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getNewEntryId()
    {
        return getBOSUuid("newEntryId");
    }
    public void setNewEntryId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("newEntryId", item);
    }
    /**
     * Object:变更管理's 处理状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.DealStateEnum getDealState()
    {
        return com.kingdee.eas.fdc.sellhouse.DealStateEnum.getEnum(getString("dealState"));
    }
    public void setDealState(com.kingdee.eas.fdc.sellhouse.DealStateEnum item)
    {
		if (item != null) {
        setString("dealState", item.getValue());
		}
    }
    /**
     * Object:变更管理's 原客户联系电话property 
     */
    public String getSrcCustomerPhone()
    {
        return getString("srcCustomerPhone");
    }
    public void setSrcCustomerPhone(String item)
    {
        setString("srcCustomerPhone", item);
    }
    /**
     * Object:变更管理's 原客户名称property 
     */
    public String getSrcCustomerNames()
    {
        return getString("srcCustomerNames");
    }
    public void setSrcCustomerNames(String item)
    {
        setString("srcCustomerNames", item);
    }
    /**
     * Object: 变更管理 's 原房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getSrcRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("srcRoom");
    }
    public void setSrcRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("srcRoom", item);
    }
    /**
     * Object:变更管理's 销售总价property 
     */
    public java.math.BigDecimal getSellAmount()
    {
        return getBigDecimal("sellAmount");
    }
    public void setSellAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sellAmount", item);
    }
    /**
     * Object:变更管理's 预估面积property 
     */
    public java.math.BigDecimal getPlanningArea()
    {
        return getBigDecimal("planningArea");
    }
    public void setPlanningArea(java.math.BigDecimal item)
    {
        setBigDecimal("planningArea", item);
    }
    /**
     * Object:变更管理's 预售面积property 
     */
    public java.math.BigDecimal getPreArea()
    {
        return getBigDecimal("preArea");
    }
    public void setPreArea(java.math.BigDecimal item)
    {
        setBigDecimal("preArea", item);
    }
    /**
     * Object:变更管理's 实测面积property 
     */
    public java.math.BigDecimal getActualArea()
    {
        return getBigDecimal("actualArea");
    }
    public void setActualArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualArea", item);
    }
    /**
     * Object:变更管理's 变更日期property 
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
     * Object: 变更管理 's 变更原因 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo getChangeReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo)get("changeReason");
    }
    public void setChangeReason(com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo item)
    {
        put("changeReason", item);
    }
    /**
     * Object: 变更管理 's 收款单 property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillInfo getSheRevBill()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillInfo)get("sheRevBill");
    }
    public void setSheRevBill(com.kingdee.eas.fdc.basecrm.SHERevBillInfo item)
    {
        put("sheRevBill", item);
    }
    /**
     * Object:变更管理's 变更详细说明property 
     */
    public String getDetails()
    {
        return getString("details");
    }
    public void setDetails(String item)
    {
        setString("details", item);
    }
    /**
     * Object:变更管理's 是否签约更名property 
     */
    public boolean isIsSignChangeName()
    {
        return getBoolean("isSignChangeName");
    }
    public void setIsSignChangeName(boolean item)
    {
        setBoolean("isSignChangeName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("27800F90");
    }
}