package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCReceiveBillInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractFDCReceiveBillInfo()
    {
        this("id");
    }
    protected AbstractFDCReceiveBillInfo(String pkField)
    {
        super(pkField);
        put("distillCommisionEntry", new com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection());
        put("customerEntrys", new com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection());
    }
    /**
     * Object: 收款单 's 销售项目 property 
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
     * Object: 收款单 's 房间 property 
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
     * Object: 收款单 's 认购单 property 
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
     * Object: 收款单 's 款项名称 property 
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
     * Object: 收款单 's 发票 property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvoiceInfo getInvoice()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvoiceInfo)get("invoice");
    }
    public void setInvoice(com.kingdee.eas.fdc.sellhouse.InvoiceInfo item)
    {
        put("invoice", item);
    }
    /**
     * Object:收款单's 付款方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum getPayQuomodo()
    {
        return com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum.getEnum(getString("payQuomodo"));
    }
    public void setPayQuomodo(com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum item)
    {
		if (item != null) {
        setString("payQuomodo", item.getValue());
		}
    }
    /**
     * Object: 收款单 's 收据 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeInfo getCheque()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeInfo)get("cheque");
    }
    public void setCheque(com.kingdee.eas.fdc.sellhouse.ChequeInfo item)
    {
        put("cheque", item);
    }
    /**
     * Object:收款单's 收据号property 
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
     * Object: 收款单 's 客户分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection getCustomerEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection)get("customerEntrys");
    }
    /**
     * Object:收款单's 款项所属系统property 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getMoneySysType()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("moneySysType"));
    }
    public void setMoneySysType(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("moneySysType", item.getValue());
		}
    }
    /**
     * Object: 收款单 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyContract()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyContract");
    }
    public void setTenancyContract(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyContract", item);
    }
    /**
     * Object:收款单's 收款明细序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:收款单's 单据类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum getBillTypeEnum()
    {
        return com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum.getEnum(getString("billTypeEnum"));
    }
    public void setBillTypeEnum(com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum item)
    {
		if (item != null) {
        setString("billTypeEnum", item.getValue());
		}
    }
    /**
     * Object:收款单's 收款对象property 
     */
    public com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum getGatheringObject()
    {
        return com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum.getEnum(getString("gatheringObject"));
    }
    public void setGatheringObject(com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum item)
    {
		if (item != null) {
        setString("gatheringObject", item.getValue());
		}
    }
    /**
     * Object: 收款单 's 结转单 property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillInfo getSettlementBill()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillInfo)get("settlementBill");
    }
    public void setSettlementBill(com.kingdee.eas.fi.cas.ReceivingBillInfo item)
    {
        put("settlementBill", item);
    }
    /**
     * Object: 收款单 's 租赁配套资源 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo getTenAttach()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo)get("tenAttach");
    }
    public void setTenAttach(com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo item)
    {
        put("tenAttach", item);
    }
    /**
     * Object: 收款单 's 诚意认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getSinPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("sinPurchase");
    }
    public void setSinPurchase(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("sinPurchase", item);
    }
    /**
     * Object: 收款单 's 诚意预留单 property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getSinObligate()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("sinObligate");
    }
    public void setSinObligate(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("sinObligate", item);
    }
    /**
     * Object:收款单's 收款对象(新)property 
     */
    public com.kingdee.eas.fdc.sellhouse.GatheringEnum getGathering()
    {
        return com.kingdee.eas.fdc.sellhouse.GatheringEnum.getEnum(getString("gathering"));
    }
    public void setGathering(com.kingdee.eas.fdc.sellhouse.GatheringEnum item)
    {
		if (item != null) {
        setString("gathering", item.getValue());
		}
    }
    /**
     * Object: 收款单 's 对应的自定义核算项目 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getAsstActType()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("asstActType");
    }
    public void setAsstActType(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("asstActType", item);
    }
    /**
     * Object: 收款单 's 分单分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection getDistillCommisionEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection)get("distillCommisionEntry");
    }
    /**
     * Object:收款单's 是否已作废property 
     */
    public boolean isIsBlankOut()
    {
        return getBoolean("isBlankOut");
    }
    public void setIsBlankOut(boolean item)
    {
        setBoolean("isBlankOut", item);
    }
    /**
     * Object: 收款单 's 调整原单据 property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillInfo getAdjustSrcBill()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillInfo)get("adjustSrcBill");
    }
    public void setAdjustSrcBill(com.kingdee.eas.fi.cas.ReceivingBillInfo item)
    {
        put("adjustSrcBill", item);
    }
    /**
     * Object:收款单's 收据状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum getReceiptState()
    {
        return com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum.getEnum(getString("receiptState"));
    }
    public void setReceiptState(com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum item)
    {
		if (item != null) {
        setString("receiptState", item.getValue());
		}
    }
    /**
     * Object:收款单's 打印次数property 
     */
    public long getPrintCount()
    {
        return getLong("printCount");
    }
    public void setPrintCount(long item)
    {
        setLong("printCount", item);
    }
    /**
     * Object:收款单's 结转类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.SettleMentTypeEnum getSettleMentType()
    {
        return com.kingdee.eas.fdc.sellhouse.SettleMentTypeEnum.getEnum(getString("settleMentType"));
    }
    public void setSettleMentType(com.kingdee.eas.fdc.sellhouse.SettleMentTypeEnum item)
    {
		if (item != null) {
        setString("settleMentType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9412AC80");
    }
}