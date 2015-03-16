package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDocumentInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractDocumentInfo()
    {
        this("id");
    }
    protected AbstractDocumentInfo(String pkField)
    {
        super(pkField);
        put("subjects", new com.kingdee.eas.fdc.market.DocumentSubjectCollection());
    }
    /**
     * Object:ÊÔ¾í's nullproperty 
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
     * Object: ÊÔ¾í 's  property 
     */
    public com.kingdee.eas.fdc.market.DocumentSubjectCollection getSubjects()
    {
        return (com.kingdee.eas.fdc.market.DocumentSubjectCollection)get("subjects");
    }
    /**
     * Object:ÊÔ¾í's ×ÖÌåÃû³Æproperty 
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
     * Object:ÊÔ¾í's ×ÖÌå´óÐ¡property 
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
     * Object:ÊÔ¾í's Ò³Ãæ¿í¶Èproperty 
     */
    public int getWidth()
    {
        return getInt("width");
    }
    public void setWidth(int item)
    {
        setInt("width", item);
    }
    /**
     * Object:ÊÔ¾í's Ò³Ãæ¸ß¶Èproperty 
     */
    public int getHeight()
    {
        return getInt("height");
    }
    public void setHeight(int item)
    {
        setInt("height", item);
    }
    /**
     * Object:ÊÔ¾í's ÉÏ±ß¾àproperty 
     */
    public int getTopMarge()
    {
        return getInt("topMarge");
    }
    public void setTopMarge(int item)
    {
        setInt("topMarge", item);
    }
    /**
     * Object:ÊÔ¾í's ÓÒ±ß¾àproperty 
     */
    public int getRightMarge()
    {
        return getInt("rightMarge");
    }
    public void setRightMarge(int item)
    {
        setInt("rightMarge", item);
    }
    /**
     * Object:ÊÔ¾í's ÏÂ±ß¾àproperty 
     */
    public int getBottomMarge()
    {
        return getInt("bottomMarge");
    }
    public void setBottomMarge(int item)
    {
        setInt("bottomMarge", item);
    }
    /**
     * Object:ÊÔ¾í's ×ó±ß¾àproperty 
     */
    public int getLeftMarge()
    {
        return getInt("leftMarge");
    }
    public void setLeftMarge(int item)
    {
        setInt("leftMarge", item);
    }
    /**
     * Object:ÊÔ¾í's Ò³Ã¼property 
     */
    public String getHeader()
    {
        return getString("header");
    }
    public void setHeader(String item)
    {
        setString("header", item);
    }
    /**
     * Object:ÊÔ¾í's Ò³½Åproperty 
     */
    public String getFooter()
    {
        return getString("footer");
    }
    public void setFooter(String item)
    {
        setString("footer", item);
    }
    /**
     * Object:ÊÔ¾í's ÁÐÊýproperty 
     */
    public int getColumnCount()
    {
        return getInt("columnCount");
    }
    public void setColumnCount(int item)
    {
        setInt("columnCount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6477D5EC");
    }
}