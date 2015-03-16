package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCReceivingBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCReceivingBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCReceivingBillEntryInfo(String pkField)
    {
        super(pkField);
        put("sourceEntries", new com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection());
    }
    /**
     * Object: 房地产收款单分录 's 房地产收款单 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:房地产收款单分录's 收款金额property 
     */
    public java.math.BigDecimal getRevAmount()
    {
        return getBigDecimal("revAmount");
    }
    public void setRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("revAmount", item);
    }
    /**
     * Object:房地产收款单分录's 本位币金额property 
     */
    public java.math.BigDecimal getRevLocAmount()
    {
        return getBigDecimal("revLocAmount");
    }
    public void setRevLocAmount(java.math.BigDecimal item)
    {
        setBigDecimal("revLocAmount", item);
    }
    /**
     * Object: 房地产收款单分录 's 结算方式 property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:房地产收款单分录's 结算号property 
     */
    public String getSettlementNumber()
    {
        return getString("settlementNumber");
    }
    public void setSettlementNumber(String item)
    {
        setString("settlementNumber", item);
    }
    /**
     * Object: 房地产收款单分录 's 收款科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getRevAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("revAccount");
    }
    public void setRevAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("revAccount", item);
    }
    /**
     * Object: 房地产收款单分录 's 对方科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOppAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("oppAccount");
    }
    public void setOppAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("oppAccount", item);
    }
    /**
     * Object: 房地产收款单分录 's 款项 property 
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
     * Object: 房地产收款单分录 's 收款银行帐户 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getRevAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("revAccountBank");
    }
    public void setRevAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("revAccountBank", item);
    }
    /**
     * Object:房地产收款单分录's 客户账号property 
     */
    public String getBankNumber()
    {
        return getString("bankNumber");
    }
    public void setBankNumber(String item)
    {
        setString("bankNumber", item);
    }
    /**
     * Object:房地产收款单分录's 收款明细IDproperty 
     */
    public String getRevListId()
    {
        return getString("revListId");
    }
    public void setRevListId(String item)
    {
        setString("revListId", item);
    }
    /**
     * Object:房地产收款单分录's 收款明细类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RevListTypeEnum getRevListType()
    {
        return com.kingdee.eas.fdc.basecrm.RevListTypeEnum.getEnum(getString("revListType"));
    }
    public void setRevListType(com.kingdee.eas.fdc.basecrm.RevListTypeEnum item)
    {
		if (item != null) {
        setString("revListType", item.getValue());
		}
    }
    /**
     * Object: 房地产收款单分录 's 转款来源 property 
     */
    public com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection getSourceEntries()
    {
        return (com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection)get("sourceEntries");
    }
    /**
     * Object: 房地产收款单分录 's 房间 property 
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
     * Object: 房地产收款单分录 's 代收组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:房地产收款单分录's 代收说明property 
     */
    public String getSupplyDes()
    {
        return getString("supplyDes");
    }
    public void setSupplyDes(String item)
    {
        setString("supplyDes", item);
    }
    /**
     * Object:房地产收款单分录's 备注property 
     */
    public String getReMark()
    {
        return getString("reMark");
    }
    public void setReMark(String item)
    {
        setString("reMark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("26EEC414");
    }
}