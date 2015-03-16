package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDocumentSubjectInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractDocumentSubjectInfo()
    {
        this("id");
    }
    protected AbstractDocumentSubjectInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.market.DocumentItemCollection());
    }
    /**
     * Object: DocumentSubject 's documentId property 
     */
    public com.kingdee.eas.fdc.market.DocumentInfo getDocumentId()
    {
        return (com.kingdee.eas.fdc.market.DocumentInfo)get("documentId");
    }
    public void setDocumentId(com.kingdee.eas.fdc.market.DocumentInfo item)
    {
        put("documentId", item);
    }
    /**
     * Object:DocumentSubject's topicproperty 
     */
    public String getTopic()
    {
        return getString("topic");
    }
    public void setTopic(String item)
    {
        setString("topic", item);
    }
    /**
     * Object: DocumentSubject 's items property 
     */
    public com.kingdee.eas.fdc.market.DocumentItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.market.DocumentItemCollection)get("items");
    }
    /**
     * Object:DocumentSubject's 题型property 
     */
    public com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum getSubjectType()
    {
        return com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum.getEnum(getInt("subjectType"));
    }
    public void setSubjectType(com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum item)
    {
		if (item != null) {
        setInt("subjectType", item.getValue());
		}
    }
    /**
     * Object:DocumentSubject's 对齐方式property 
     */
    public com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum getAlignType()
    {
        return com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum.getEnum(getInt("alignType"));
    }
    public void setAlignType(com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum item)
    {
		if (item != null) {
        setInt("alignType", item.getValue());
		}
    }
    /**
     * Object:DocumentSubject's 水平对齐方式property 
     */
    public com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum getHorizontalAlign()
    {
        return com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum.getEnum(getInt("horizontalAlign"));
    }
    public void setHorizontalAlign(com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum item)
    {
		if (item != null) {
        setInt("horizontalAlign", item.getValue());
		}
    }
    /**
     * Object:DocumentSubject's 宽度倍数property 
     */
    public int getColumnCount()
    {
        return getInt("columnCount");
    }
    public void setColumnCount(int item)
    {
        setInt("columnCount", item);
    }
    /**
     * Object:DocumentSubject's 序号property 
     */
    public int getSubjectNumber()
    {
        return getInt("subjectNumber");
    }
    public void setSubjectNumber(int item)
    {
        setInt("subjectNumber", item);
    }
    /**
     * Object:DocumentSubject's 显示的号码property 
     */
    public int getShowNumber()
    {
        return getInt("showNumber");
    }
    public void setShowNumber(int item)
    {
        setInt("showNumber", item);
    }
    /**
     * Object:DocumentSubject's 是否显示号码property 
     */
    public int getIsShowNumber()
    {
        return getInt("isShowNumber");
    }
    public void setIsShowNumber(int item)
    {
        setInt("isShowNumber", item);
    }
    /**
     * Object:DocumentSubject's 生成器property 
     */
    public String getXCellCreter()
    {
        return getString("xCellCreter");
    }
    public void setXCellCreter(String item)
    {
        setString("xCellCreter", item);
    }
    /**
     * Object:DocumentSubject's xFontNameproperty 
     */
    public String getXFontName()
    {
        return getString("xFontName");
    }
    public void setXFontName(String item)
    {
        setString("xFontName", item);
    }
    /**
     * Object:DocumentSubject's xFontSizeproperty 
     */
    public int getXFontSize()
    {
        return getInt("xFontSize");
    }
    public void setXFontSize(int item)
    {
        setInt("xFontSize", item);
    }
    /**
     * Object:DocumentSubject's 分组类型property 
     */
    public com.kingdee.eas.fdc.market.DocSubItemTypeEnum getSubItemType()
    {
        return com.kingdee.eas.fdc.market.DocSubItemTypeEnum.getEnum(getString("subItemType"));
    }
    public void setSubItemType(com.kingdee.eas.fdc.market.DocSubItemTypeEnum item)
    {
		if (item != null) {
        setString("subItemType", item.getValue());
		}
    }
    /**
     * Object: DocumentSubject 's 客户辅助资料的所属项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProjectId()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProjectId");
    }
    public void setSellProjectId(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProjectId", item);
    }
    /**
     * Object:DocumentSubject's 是否必填property 
     */
    public int getIsRequired()
    {
        return getInt("isRequired");
    }
    public void setIsRequired(int item)
    {
        setInt("isRequired", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0976D5A0");
    }
}