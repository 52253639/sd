package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepConPayPlanNoContractInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepConPayPlanNoContractInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepConPayPlanNoContractInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryCollection());
    }
    /**
     * Object: null 's 父 property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object:null's 是否打回property 
     */
    public boolean isIsBack()
    {
        return getBoolean("isBack");
    }
    public void setIsBack(boolean item)
    {
        setBoolean("isBack", item);
    }
    /**
     * Object:null's 付款事项编码property 
     */
    public String getPayMatters()
    {
        return getString("payMatters");
    }
    public void setPayMatters(String item)
    {
        setString("payMatters", item);
    }
    /**
     * Object:null's 付款事项名称property 
     */
    public String getPayMattersName()
    {
        return getString("payMattersName");
    }
    public void setPayMattersName(String item)
    {
        setString("payMattersName", item);
    }
    /**
     * Object: null 's  property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryCollection)get("entrys");
    }
    /**
     * Object: null 's 部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("department");
    }
    public void setDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("department", item);
    }
    /**
     * Object: null 's 合同滚动计划对应分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo getDepPlan()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo)get("depPlan");
    }
    public void setDepPlan(com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo item)
    {
        put("depPlan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("60C5993A");
    }
}