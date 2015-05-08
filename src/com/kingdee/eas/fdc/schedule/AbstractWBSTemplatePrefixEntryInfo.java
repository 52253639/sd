package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWBSTemplatePrefixEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWBSTemplatePrefixEntryInfo()
    {
        this("id");
    }
    protected AbstractWBSTemplatePrefixEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: WBS模板前置任务 's WBS模板分录 property 
     */
    public com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:WBS模板前置任务's 搭接关系property 
     */
    public com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum getLinkType()
    {
        return com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum.getEnum(getString("linkType"));
    }
    public void setLinkType(com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum item)
    {
		if (item != null) {
        setString("linkType", item.getValue());
		}
    }
    /**
     * Object:WBS模板前置任务's 搭接时间property 
     */
    public int getLinkDay()
    {
        return getInt("linkDay");
    }
    public void setLinkDay(int item)
    {
        setInt("linkDay", item);
    }
    /**
     * Object: WBS模板前置任务 's 前置WBS模板分录 property 
     */
    public com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo getPrefixNode()
    {
        return (com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo)get("prefixNode");
    }
    public void setPrefixNode(com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo item)
    {
        put("prefixNode", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4F1FB754");
    }
}