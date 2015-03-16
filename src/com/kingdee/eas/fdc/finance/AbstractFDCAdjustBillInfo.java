package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCAdjustBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCAdjustBillInfo()
    {
        this("id");
    }
    protected AbstractFDCAdjustBillInfo(String pkField)
    {
        super(pkField);
        put("infos", new com.kingdee.eas.fdc.finance.FDCAdjustBillAllInfoCollection());
        put("entrys", new com.kingdee.eas.fdc.finance.FDCAdjustBillEntryCollection());
    }
    /**
     * Object: 调整单 's 工程项目 property 
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
     * Object: 调整单 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object:调整单's 调整原因property 
     */
    public com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum getAdjustReason()
    {
        return com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum.getEnum(getString("adjustReason"));
    }
    public void setAdjustReason(com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum item)
    {
		if (item != null) {
        setString("adjustReason", item.getValue());
		}
    }
    /**
     * Object:调整单's 是否成本调整单property 
     */
    public boolean isIsCost()
    {
        return getBoolean("isCost");
    }
    public void setIsCost(boolean item)
    {
        setBoolean("isCost", item);
    }
    /**
     * Object:调整单's 是否需要凭证处理property 
     */
    public boolean isIsNeedTraceVoucher()
    {
        return getBoolean("isNeedTraceVoucher");
    }
    public void setIsNeedTraceVoucher(boolean item)
    {
        setBoolean("isNeedTraceVoucher", item);
    }
    /**
     * Object:调整单's 是否已生成凭证property 
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
     * Object: 调整单 's 凭证 property 
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
     * Object: 调整单 's 全信息 property 
     */
    public com.kingdee.eas.fdc.finance.FDCAdjustBillAllInfoCollection getInfos()
    {
        return (com.kingdee.eas.fdc.finance.FDCAdjustBillAllInfoCollection)get("infos");
    }
    /**
     * Object: 调整单 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCAdjustBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCAdjustBillEntryCollection)get("entrys");
    }
    /**
     * Object: 调整单 's 财务成本一体化科目 property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeforeAccountView()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beforeAccountView");
    }
    public void setBeforeAccountView(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beforeAccountView", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D24B04CC");
    }
}