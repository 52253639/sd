package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractVoucherForDynamicInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractVoucherForDynamicInfo()
    {
        this("id");
    }
    protected AbstractVoucherForDynamicInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:目标成本调整凭证's 是否已生成凭证property 
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
     * Object: 目标成本调整凭证 's 财务成本一体化科目 property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beAccount");
    }
    public void setBeAccount(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beAccount", item);
    }
    /**
     * Object: 目标成本调整凭证 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: 目标成本调整凭证 's 产品 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object: 目标成本调整凭证 's 调整分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo getAdjustEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo)get("adjustEntry");
    }
    public void setAdjustEntry(com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo item)
    {
        put("adjustEntry", item);
    }
    /**
     * Object: 目标成本调整凭证 's 凭证 property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    /**
     * Object: 目标成本调整凭证 's 产品结算单 property 
     */
    public com.kingdee.eas.fdc.finance.FIProductSettleBillInfo getSettBill()
    {
        return (com.kingdee.eas.fdc.finance.FIProductSettleBillInfo)get("settBill");
    }
    public void setSettBill(com.kingdee.eas.fdc.finance.FIProductSettleBillInfo item)
    {
        put("settBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5E6400D");
    }
}