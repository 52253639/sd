package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionPaperDefineInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractQuestionPaperDefineInfo()
    {
        this("id");
    }
    protected AbstractQuestionPaperDefineInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.QuestionPaperDefineEntryCollection());
    }
    /**
     * Object: 定义问卷 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperDefineEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperDefineEntryCollection)get("entrys");
    }
    /**
     * Object:定义问卷's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("fivouchered", item);
    }
    /**
     * Object:定义问卷's 启用日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object: 定义问卷 's 问卷类型 property 
     */
    public com.kingdee.eas.fdc.market.QuestionTypeInfo getPaperType()
    {
        return (com.kingdee.eas.fdc.market.QuestionTypeInfo)get("paperType");
    }
    public void setPaperType(com.kingdee.eas.fdc.market.QuestionTypeInfo item)
    {
        put("paperType", item);
    }
    /**
     * Object:定义问卷's 文档IDproperty 
     */
    public String getDocumentId()
    {
        return getString("documentId");
    }
    public void setDocumentId(String item)
    {
        setString("documentId", item);
    }
    /**
     * Object:定义问卷's 问卷主题property 
     */
    public String getTopric()
    {
        return getString("topric");
    }
    public void setTopric(String item)
    {
        setString("topric", item);
    }
    /**
     * Object:定义问卷's 截止日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:定义问卷's 业务场景property 
     */
    public com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum getBizScene()
    {
        return com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum.getEnum(getString("bizScene"));
    }
    public void setBizScene(com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum item)
    {
		if (item != null) {
        setString("bizScene", item.getValue());
		}
    }
    /**
     * Object: 定义问卷 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("82FA1D30");
    }
}