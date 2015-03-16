package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSubjectLevelSubjectInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractSubjectLevelSubjectInfo()
    {
        this("id");
    }
    protected AbstractSubjectLevelSubjectInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 科目 's null property 
     */
    public com.kingdee.eas.fdc.finance.SubjectLevelInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.SubjectLevelInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.SubjectLevelInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:科目's 选择property 
     */
    public boolean isIsSelected()
    {
        return getBoolean("isSelected");
    }
    public void setIsSelected(boolean item)
    {
        setBoolean("isSelected", item);
    }
    /**
     * Object: 科目 's 科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getAccountItem()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("accountItem");
    }
    public void setAccountItem(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("accountItem", item);
    }
    /**
     * Object:科目's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AE5D46E5");
    }
}