package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCReceivingBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCReceivingBillInfo()
    {
        this("id");
    }
    protected AbstractFDCReceivingBillInfo(String pkField)
    {
        super(pkField);
        put("fdcCustomers", new com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection());
        put("entries", new com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection());
    }
    /**
     * Object: 房地产收款单 's 公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object: 房地产收款单 's 营销项目 property 
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
     * Object: 房地产收款单 's 币别 property 
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
     * Object: 房地产收款单 's 房地产收款单分录 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection)get("entries");
    }
    /**
     * Object:房地产收款单's 汇率property 
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
     * Object:房地产收款单's 收款单据类型property 
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
     * Object:房地产收款单's 收款业务类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RevBizTypeEnum getRevBizType()
    {
        return com.kingdee.eas.fdc.basecrm.RevBizTypeEnum.getEnum(getString("revBizType"));
    }
    public void setRevBizType(com.kingdee.eas.fdc.basecrm.RevBizTypeEnum item)
    {
		if (item != null) {
        setString("revBizType", item.getValue());
		}
    }
    /**
     * Object: 房地产收款单 's 收据 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeInfo getReceipt()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeInfo)get("receipt");
    }
    public void setReceipt(com.kingdee.eas.fdc.sellhouse.ChequeInfo item)
    {
        put("receipt", item);
    }
    /**
     * Object:房地产收款单's 收据号property 
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
     * Object: 房地产收款单 's 发票 property 
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
     * Object:房地产收款单's 收据状态property 
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
     * Object: 房地产收款单 's 认购收款对象 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchaseObj()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchaseObj");
    }
    public void setPurchaseObj(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchaseObj", item);
    }
    /**
     * Object: 房地产收款单 's 诚意认购对象 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getSincerityObj()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("sincerityObj");
    }
    public void setSincerityObj(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("sincerityObj", item);
    }
    /**
     * Object: 房地产收款单 's 租赁诚意预留收款对象 property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getObligateObj()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("obligateObj");
    }
    public void setObligateObj(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("obligateObj", item);
    }
    /**
     * Object: 房地产收款单 's 租赁合同收款对象 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyObj()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyObj");
    }
    public void setTenancyObj(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyObj", item);
    }
    /**
     * Object: 房地产收款单 's 交费客户 property 
     */
    public com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection getFdcCustomers()
    {
        return (com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection)get("fdcCustomers");
    }
    /**
     * Object: 房地产收款单 's 收款客户 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:房地产收款单's 单据状态property 
     */
    public com.kingdee.eas.fdc.basecrm.RevBillStatusEnum getBillStatus()
    {
        return com.kingdee.eas.fdc.basecrm.RevBillStatusEnum.getEnum(getInt("billStatus"));
    }
    public void setBillStatus(com.kingdee.eas.fdc.basecrm.RevBillStatusEnum item)
    {
		if (item != null) {
        setInt("billStatus", item.getValue());
		}
    }
    /**
     * Object:房地产收款单's 是否已生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object:房地产收款单's 房间长编码property 
     */
    public String getRoomLongNumber()
    {
        return getString("roomLongNumber");
    }
    public void setRoomLongNumber(String item)
    {
        setString("roomLongNumber", item);
    }
    /**
     * Object: 房地产收款单 's 房间 property 
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
     * Object: 房地产收款单 's 租赁顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTenancyUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("tenancyUser");
    }
    public void setTenancyUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("tenancyUser", item);
    }
    /**
     * Object: 房地产收款单 's 代收源收款单 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo getSrcRevBill()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo)get("srcRevBill");
    }
    public void setSrcRevBill(com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo item)
    {
        put("srcRevBill", item);
    }
    /**
     * Object:房地产收款单's 是否生成出纳收款单property 
     */
    public boolean isIsCreateBill()
    {
        return getBoolean("isCreateBill");
    }
    public void setIsCreateBill(boolean item)
    {
        setBoolean("isCreateBill", item);
    }
    /**
     * Object:房地产收款单's 是否托收property 
     */
    public boolean isIsCollection()
    {
        return getBoolean("isCollection");
    }
    public void setIsCollection(boolean item)
    {
        setBoolean("isCollection", item);
    }
    /**
     * Object:房地产收款单's 是否已月结property 
     */
    public boolean isIsMonthSettled()
    {
        return getBoolean("isMonthSettled");
    }
    public void setIsMonthSettled(boolean item)
    {
        setBoolean("isMonthSettled", item);
    }
    /**
     * Object:房地产收款单's 违约金收款property 
     */
    public java.math.BigDecimal getDeditAmount()
    {
        return getBigDecimal("deditAmount");
    }
    public void setDeditAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deditAmount", item);
    }
    /**
     * Object: 房地产收款单 's 费用所属期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getChargePeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("chargePeriod");
    }
    public void setChargePeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("chargePeriod", item);
    }
    /**
     * Object:房地产收款单's 是否按客户收款property 
     */
    public boolean isIsByCustomer()
    {
        return getBoolean("isByCustomer");
    }
    public void setIsByCustomer(boolean item)
    {
        setBoolean("isByCustomer", item);
    }
    /**
     * Object:房地产收款单's 多合同冗余字段property 
     */
    public String getTennacyStr()
    {
        return getString("tennacyStr");
    }
    public void setTennacyStr(String item)
    {
        setString("tennacyStr", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F12182FE");
    }
}