package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillInfo implements Serializable 
{
    public AbstractFDCProDepSplitInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitInfo(String pkField)
    {
        super(pkField);
        put("noCons", new com.kingdee.eas.fdc.finance.FDCProDepSplitNoConCollection());
        put("unCons", new com.kingdee.eas.fdc.finance.FDCProDepSplitUnConCollection());
        put("hasCons", new com.kingdee.eas.fdc.finance.FDCProDepSplitHasConCollection());
    }
    /**
     * Object: 滚动付款计划拆分 's 拆分人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSplitUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("splitUser");
    }
    public void setSplitUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("splitUser", item);
    }
    /**
     * Object: 滚动付款计划拆分 's 滚动付款计划 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo getFdcProDep()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo)get("fdcProDep");
    }
    public void setFdcProDep(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo item)
    {
        put("fdcProDep", item);
    }
    /**
     * Object:滚动付款计划拆分's 拆分日期property 
     */
    public java.util.Date getSplitDate()
    {
        return getDate("splitDate");
    }
    public void setSplitDate(java.util.Date item)
    {
        setDate("splitDate", item);
    }
    /**
     * Object: 滚动付款计划拆分 's 已签订合同拆分分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitHasConCollection getHasCons()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitHasConCollection)get("hasCons");
    }
    /**
     * Object: 滚动付款计划拆分 's 待签订合同拆分分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitUnConCollection getUnCons()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitUnConCollection)get("unCons");
    }
    /**
     * Object: 滚动付款计划拆分 's 无合同拆分分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitNoConCollection getNoCons()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitNoConCollection)get("noCons");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A544F042");
    }
}