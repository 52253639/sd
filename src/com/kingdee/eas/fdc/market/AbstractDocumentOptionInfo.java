package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDocumentOptionInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractDocumentOptionInfo()
    {
        this("id");
    }
    protected AbstractDocumentOptionInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:DocumentOption's √Ë ˆ01property 
     */
    public String getDesc01()
    {
        return getString("desc01");
    }
    public void setDesc01(String item)
    {
        setString("desc01", item);
    }
    /**
     * Object:DocumentOption's topicproperty 
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
     * Object:DocumentOption's xLengthproperty 
     */
    public int getXLength()
    {
        return getInt("xLength");
    }
    public void setXLength(int item)
    {
        setInt("xLength", item);
    }
    /**
     * Object:DocumentOption's xHeightproperty 
     */
    public int getXHeight()
    {
        return getInt("xHeight");
    }
    public void setXHeight(int item)
    {
        setInt("xHeight", item);
    }
    /**
     * Object:DocumentOption's  «∑Ò÷˜Ã‚µπ÷√property 
     */
    public boolean isIsTopicInverse()
    {
        return getBoolean("isTopicInverse");
    }
    public void setIsTopicInverse(boolean item)
    {
        setBoolean("isTopicInverse", item);
    }
    /**
     * Object:DocumentOption's √Ë ˆ02property 
     */
    public String getDesc02()
    {
        return getString("desc02");
    }
    public void setDesc02(String item)
    {
        setString("desc02", item);
    }
    /**
     * Object: DocumentOption 's itemId property 
     */
    public com.kingdee.eas.fdc.market.DocumentItemInfo getItemId()
    {
        return (com.kingdee.eas.fdc.market.DocumentItemInfo)get("itemId");
    }
    public void setItemId(com.kingdee.eas.fdc.market.DocumentItemInfo item)
    {
        put("itemId", item);
    }
    /**
     * Object:DocumentOption's xFontSizeproperty 
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
     * Object:DocumentOption's xFontNameproperty 
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
     * Object:DocumentOption's optionNumberproperty 
     */
    public int getOptionNumber()
    {
        return getInt("optionNumber");
    }
    public void setOptionNumber(int item)
    {
        setInt("optionNumber", item);
    }
    /**
     * Object:DocumentOption's ∏®÷˙◊ ¡œidproperty 
     */
    public String getRelationId()
    {
        return getString("relationId");
    }
    public void setRelationId(String item)
    {
        setString("relationId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CFF22981");
    }
}