package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListTempletEntryInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractNewListTempletEntryInfo()
    {
        this("id");
    }
    protected AbstractNewListTempletEntryInfo(String pkField)
    {
        super(pkField);
        put("values", new com.kingdee.eas.fdc.invite.NewListTempletValueCollection());
    }
    /**
     * Object: 清单模板分录 's 页签 property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletPageInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletPageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.NewListTempletPageInfo item)
    {
        put("head", item);
    }
    /**
     * Object:清单模板分录's 子目名称property 
     */
    public String getItemName()
    {
        return getString("itemName");
    }
    public void setItemName(String item)
    {
        setString("itemName", item);
    }
    /**
     * Object:清单模板分录's 子目编码property 
     */
    public String getItemNumber()
    {
        return getString("itemNumber");
    }
    public void setItemNumber(String item)
    {
        setString("itemNumber", item);
    }
    /**
     * Object:清单模板分录's 序列property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object: 清单模板分录 's 子目 property 
     */
    public com.kingdee.eas.fdc.invite.ListingItemInfo getItem()
    {
        return (com.kingdee.eas.fdc.invite.ListingItemInfo)get("item");
    }
    public void setItem(com.kingdee.eas.fdc.invite.ListingItemInfo item)
    {
        put("item", item);
    }
    /**
     * Object:清单模板分录's 是否关键子目property 
     */
    public boolean isIsKey()
    {
        return getBoolean("isKey");
    }
    public void setIsKey(boolean item)
    {
        setBoolean("isKey", item);
    }
    /**
     * Object:清单模板分录's 是否单价构成property 
     */
    public boolean isIsCompose()
    {
        return getBoolean("isCompose");
    }
    public void setIsCompose(boolean item)
    {
        setBoolean("isCompose", item);
    }
    /**
     * Object: 清单模板分录 's 单元值 property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletValueCollection getValues()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletValueCollection)get("values");
    }
    /**
     * Object:清单模板分录's 是零星项目行property 
     */
    public boolean isIsCanZeroProAmount()
    {
        return getBoolean("isCanZeroProAmount");
    }
    public void setIsCanZeroProAmount(boolean item)
    {
        setBoolean("isCanZeroProAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DC1036C5");
    }
}