package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasureDefaultSplitTypeSetInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractMeasureDefaultSplitTypeSetInfo()
    {
        this("id");
    }
    protected AbstractMeasureDefaultSplitTypeSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 默认测算分摊设置 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:默认测算分摊设置's 分摊类型property 
     */
    public String getSplitType()
    {
        return getString("splitType");
    }
    public void setSplitType(String item)
    {
        setString("splitType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7703A388");
    }
}