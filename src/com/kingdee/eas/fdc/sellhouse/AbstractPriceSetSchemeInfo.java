package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPriceSetSchemeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractPriceSetSchemeInfo()
    {
        this("id");
    }
    protected AbstractPriceSetSchemeInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryCollection());
    }
    /**
     * Object: 定价方案 's 租售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:定价方案's 方案日期property 
     */
    public java.util.Date getSchemeDate()
    {
        return getDate("schemeDate");
    }
    public void setSchemeDate(java.util.Date item)
    {
        setDate("schemeDate", item);
    }
    /**
     * Object: 定价方案 's 因素分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryCollection)get("entrys");
    }
    /**
     * Object:定价方案's 方案类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceSetSchemenTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceSetSchemenTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.PriceSetSchemenTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:定价方案's 是否引入已售property 
     */
    public boolean isIsImportDeal()
    {
        return getBoolean("isImportDeal");
    }
    public void setIsImportDeal(boolean item)
    {
        setBoolean("isImportDeal", item);
    }
    /**
     * Object:定价方案's 是否按套内面积计算property 
     */
    public boolean isIsCalByRoomArea()
    {
        return getBoolean("isCalByRoomArea");
    }
    public void setIsCalByRoomArea(boolean item)
    {
        setBoolean("isCalByRoomArea", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0FAF28F9");
    }
}