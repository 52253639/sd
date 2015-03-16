package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBudgetAcctInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCBudgetAcctInfo()
    {
        this("id");
    }
    protected AbstractFDCBudgetAcctInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房地产科目预算基类 's 工程项目 property 
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
     * Object:房地产科目预算基类's 版本号property 
     */
    public float getVerNumber()
    {
        return getFloat("verNumber");
    }
    public void setVerNumber(float item)
    {
        setFloat("verNumber", item);
    }
    /**
     * Object:房地产科目预算基类's 最新版本property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object: 房地产科目预算基类 's 房地产预算期间 property 
     */
    public com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo getFdcPeriod()
    {
        return (com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo)get("fdcPeriod");
    }
    public void setFdcPeriod(com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo item)
    {
        put("fdcPeriod", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("335D258E");
    }
}