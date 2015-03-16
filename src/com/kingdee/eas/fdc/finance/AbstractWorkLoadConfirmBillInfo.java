package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkLoadConfirmBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractWorkLoadConfirmBillInfo()
    {
        this("id");
    }
    protected AbstractWorkLoadConfirmBillInfo(String pkField)
    {
        super(pkField);
        put("reftaskentry", new com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskCollection());
        put("entrys", new com.kingdee.eas.fdc.finance.WorkLoadConfirmBillEntryCollection());
        put("costVoucherEntrys", new com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryCollection());
        put("payVoucherEntrys", new com.kingdee.eas.fdc.finance.WorkLoadPayVoucherEntryCollection());
        put("prjFillBillEntry", new com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryCollection());
    }
    /**
     * Object: 工程量确认单 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillEntryCollection)get("entrys");
    }
    /**
     * Object: 工程量确认单 's 工程项目 property 
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
     * Object: 工程量确认单 's 合同 property 
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
     * Object:工程量确认单's 确认工程量property 
     */
    public java.math.BigDecimal getWorkLoad()
    {
        return getBigDecimal("workLoad");
    }
    public void setWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("workLoad", item);
    }
    /**
     * Object:工程量确认单's 确认日期property 
     */
    public java.util.Date getConfirmDate()
    {
        return getDate("confirmDate");
    }
    public void setConfirmDate(java.util.Date item)
    {
        setDate("confirmDate", item);
    }
    /**
     * Object: 工程量确认单 's 凭证 property 
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
     * Object:工程量确认单's 是否生成凭证property 
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
     * Object: 工程量确认单 's 凭证成本分录 property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryCollection getCostVoucherEntrys()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryCollection)get("costVoucherEntrys");
    }
    /**
     * Object: 工程量确认单 's 凭证付款分录 property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadPayVoucherEntryCollection getPayVoucherEntrys()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadPayVoucherEntryCollection)get("payVoucherEntrys");
    }
    /**
     * Object: 工程量确认单 's 公司 property 
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
     * Object:工程量确认单's 拆分状态property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object:工程量确认单's 是否结算后生成property 
     */
    public boolean isHasSettled()
    {
        return getBoolean("hasSettled");
    }
    public void setHasSettled(boolean item)
    {
        setBoolean("hasSettled", item);
    }
    /**
     * Object: 工程量确认单 's 工程量填报详细 property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryCollection getPrjFillBillEntry()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryCollection)get("prjFillBillEntry");
    }
    /**
     * Object: 工程量确认单 's 关联任务填报分录 property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskCollection getReftaskentry()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskCollection)get("reftaskentry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E4A3CD61");
    }
}