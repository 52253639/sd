package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommerceChanceTrackInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCommerceChanceTrackInfo()
    {
        this("id");
    }
    protected AbstractCommerceChanceTrackInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 商机跟进 's 销售项目 property 
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
     * Object: 商机跟进 's 商机 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    /**
     * Object:商机跟进's 跟进日期property 
     */
    public java.util.Date getTrackDate()
    {
        return getDate("trackDate");
    }
    public void setTrackDate(java.util.Date item)
    {
        setDate("trackDate", item);
    }
    /**
     * Object:商机跟进's 交互方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum getInteractionType()
    {
        return com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum.getEnum(getString("interactionType"));
    }
    public void setInteractionType(com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum item)
    {
		if (item != null) {
        setString("interactionType", item.getValue());
		}
    }
    /**
     * Object:商机跟进's 跟进事件property 
     */
    public com.kingdee.eas.fdc.sellhouse.TrackEventEnum getTrackEvent()
    {
        return com.kingdee.eas.fdc.sellhouse.TrackEventEnum.getEnum(getString("trackEvent"));
    }
    public void setTrackEvent(com.kingdee.eas.fdc.sellhouse.TrackEventEnum item)
    {
		if (item != null) {
        setString("trackEvent", item.getValue());
		}
    }
    /**
     * Object: 商机跟进 's 置业顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSaleMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("saleMan");
    }
    public void setSaleMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("saleMan", item);
    }
    /**
     * Object:商机跟进's 跟进内容property 
     */
    public String getTrackContent()
    {
        return getString("trackContent");
    }
    public void setTrackContent(String item)
    {
        setString("trackContent", item);
    }
    /**
     * Object: 商机跟进 's 线索管理 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getClues()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("clues");
    }
    public void setClues(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("clues", item);
    }
    /**
     * Object: 商机跟进 's 商机阶段 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getCommerceChanceStage()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("commerceChanceStage");
    }
    public void setCommerceChanceStage(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("commerceChanceStage", item);
    }
    /**
     * Object: 商机跟进 's 媒体渠道 property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeInfo getClassify()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeInfo)get("classify");
    }
    public void setClassify(com.kingdee.eas.fdc.market.ChannelTypeInfo item)
    {
        put("classify", item);
    }
    /**
     * Object: 商机跟进 's 商机级别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getCommerceLevel()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("commerceLevel");
    }
    public void setCommerceLevel(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("commerceLevel", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B0E101E1");
    }
}