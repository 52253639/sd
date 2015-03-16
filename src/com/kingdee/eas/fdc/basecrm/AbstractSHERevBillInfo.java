package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHERevBillInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSHERevBillInfo()
    {
        this("id");
    }
    protected AbstractSHERevBillInfo(String pkField)
    {
        super(pkField);
        put("customerEntry", new com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection());
    }
    /**
     * Object: 售楼收付款单 's 分录 property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection)get("entrys");
    }
    /**
     * Object:售楼收付款单's 是否生成凭证property 
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
     * Object: 售楼收付款单 's 销售组织 property 
     */
    public com.kingdee.eas.basedata.org.SaleOrgUnitInfo getSaleOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.SaleOrgUnitInfo)get("saleOrgUnit");
    }
    public void setSaleOrgUnit(com.kingdee.eas.basedata.org.SaleOrgUnitInfo item)
    {
        put("saleOrgUnit", item);
    }
    /**
     * Object: 售楼收付款单 's 币别 property 
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
     * Object: 售楼收付款单 's 项目 property 
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
     * Object:售楼收付款单's 汇率property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("exchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchangeRate", item);
    }
    /**
     * Object:售楼收付款单's 收款金额property 
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
     * Object: 售楼收付款单 's 房源 property 
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
     * Object:售楼收付款单's 本位币金额property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    /**
     * Object:售楼收付款单's 收款单单据类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RevBillTypeEnum getRevBillType()
    {
        return com.kingdee.eas.fdc.basecrm.RevBillTypeEnum.getEnum(getString("revBillType"));
    }
    public void setRevBillType(com.kingdee.eas.fdc.basecrm.RevBillTypeEnum item)
    {
		if (item != null) {
        setString("revBillType", item.getValue());
		}
    }
    /**
     * Object:售楼收付款单's 关联业务单据类型property 
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
     * Object:售楼收付款单's 关联业务单据idproperty 
     */
    public String getRelateBizBillId()
    {
        return getString("relateBizBillId");
    }
    public void setRelateBizBillId(String item)
    {
        setString("relateBizBillId", item);
    }
    /**
     * Object:售楼收付款单's 关联业务单据编码property 
     */
    public String getRelateBizBillNumber()
    {
        return getString("relateBizBillNumber");
    }
    public void setRelateBizBillNumber(String item)
    {
        setString("relateBizBillNumber", item);
    }
    /**
     * Object:售楼收付款单's 关联的交易主线idproperty 
     */
    public String getRelateTransId()
    {
        return getString("relateTransId");
    }
    public void setRelateTransId(String item)
    {
        setString("relateTransId", item);
    }
    /**
     * Object:售楼收付款单's 转出关联单据类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RelatBizType getRelateFromBizType()
    {
        return com.kingdee.eas.fdc.basecrm.RelatBizType.getEnum(getString("relateFromBizType"));
    }
    public void setRelateFromBizType(com.kingdee.eas.fdc.basecrm.RelatBizType item)
    {
		if (item != null) {
        setString("relateFromBizType", item.getValue());
		}
    }
    /**
     * Object:售楼收付款单's 转出关联单据idproperty 
     */
    public String getRelateFromBizId()
    {
        return getString("relateFromBizId");
    }
    public void setRelateFromBizId(String item)
    {
        setString("relateFromBizId", item);
    }
    /**
     * Object:售楼收付款单's 转出关联单据编码property 
     */
    public String getRelateFromBizNumber()
    {
        return getString("relateFromBizNumber");
    }
    public void setRelateFromBizNumber(String item)
    {
        setString("relateFromBizNumber", item);
    }
    /**
     * Object:售楼收付款单's 转出关联单据的交易主线idproperty 
     */
    public String getRelateFromTransId()
    {
        return getString("relateFromTransId");
    }
    public void setRelateFromTransId(String item)
    {
        setString("relateFromTransId", item);
    }
    /**
     * Object: 售楼收付款单 's 转出款项客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getRelateFromCust()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("relateFromCust");
    }
    public void setRelateFromCust(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("relateFromCust", item);
    }
    /**
     * Object:售楼收付款单's 交款人property 
     */
    public String getPayCustomerName()
    {
        return getString("payCustomerName");
    }
    public void setPayCustomerName(String item)
    {
        setString("payCustomerName", item);
    }
    /**
     * Object: 售楼收付款单 's 收据 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo getReceipt()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo)get("receipt");
    }
    public void setReceipt(com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo item)
    {
        put("receipt", item);
    }
    /**
     * Object:售楼收付款单's 收据号property 
     */
    public String getReceiptNumber()
    {
        return getString("receiptNumber");
    }
    public void setReceiptNumber(String item)
    {
        setString("receiptNumber", item);
    }
    /**
     * Object: 售楼收付款单 's 发票 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo getInvoice()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo)get("invoice");
    }
    public void setInvoice(com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo item)
    {
        put("invoice", item);
    }
    /**
     * Object:售楼收付款单's 发票号property 
     */
    public String getInvoiceNumber()
    {
        return getString("invoiceNumber");
    }
    public void setInvoiceNumber(String item)
    {
        setString("invoiceNumber", item);
    }
    /**
     * Object: 售楼收付款单 's 系统客户 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getSysCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("sysCustomer");
    }
    public void setSysCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("sysCustomer", item);
    }
    /**
     * Object:售楼收付款单's 审批时间property 
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
     * Object:售楼收付款单's 单据状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:售楼收付款单's 多客户名称property 
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
     * Object:售楼收付款单's 多客户IDproperty 
     */
    public String getCustomerIds()
    {
        return getString("customerIds");
    }
    public void setCustomerIds(String item)
    {
        setString("customerIds", item);
    }
    /**
     * Object: 售楼收付款单 's 客户分录 property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection getCustomerEntry()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection)get("customerEntry");
    }
    /**
     * Object:售楼收付款单's 转款生成的收款单IDproperty 
     */
    public String getTrsToGatherId()
    {
        return getString("trsToGatherId");
    }
    public void setTrsToGatherId(String item)
    {
        setString("trsToGatherId", item);
    }
    /**
     * Object:售楼收付款单's 转款生成的退款单IDproperty 
     */
    public String getTrsToRefundId()
    {
        return getString("trsToRefundId");
    }
    public void setTrsToRefundId(String item)
    {
        setString("trsToRefundId", item);
    }
    /**
     * Object:售楼收付款单's 是否是转款单自动生成property 
     */
    public boolean isIsTansCreate()
    {
        return getBoolean("isTansCreate");
    }
    public void setIsTansCreate(boolean item)
    {
        setBoolean("isTansCreate", item);
    }
    /**
     * Object:售楼收付款单's 是否已汇总property 
     */
    public boolean isIsGathered()
    {
        return getBoolean("isGathered");
    }
    public void setIsGathered(boolean item)
    {
        setBoolean("isGathered", item);
    }
    /**
     * Object: 售楼收付款单 's 收款账户 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("accountBank");
    }
    public void setAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("accountBank", item);
    }
    /**
     * Object: 售楼收付款单 's 收款科目 property 
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
     * Object: 售楼收付款单 's 收款银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("bank");
    }
    public void setBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("bank", item);
    }
    /**
     * Object: 售楼收付款单 's 结算方式 property 
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
     * Object:售楼收付款单's 结算号property 
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
     * Object:售楼收付款单's 款项类型property 
     */
    public String getMoneyDefine()
    {
        return getString("moneyDefine");
    }
    public void setMoneyDefine(String item)
    {
        setString("moneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DFC518D6");
    }
}