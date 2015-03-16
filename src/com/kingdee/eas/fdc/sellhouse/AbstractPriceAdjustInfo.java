package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPriceAdjustInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPriceAdjustInfo()
    {
        this("id");
    }
    protected AbstractPriceAdjustInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryCollection());
    }
    /**
     * Object: 房间调价 's 调价分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryCollection)get("entrys");
    }
    /**
     * Object:房间调价's 是否执行property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object: 房间调价 's 销售项目 property 
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
     * Object:房间调价's 是否自动取整property 
     */
    public boolean isIsAutoToInteger()
    {
        return getBoolean("isAutoToInteger");
    }
    public void setIsAutoToInteger(boolean item)
    {
        setBoolean("isAutoToInteger", item);
    }
    /**
     * Object:房间调价's 取整类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getToIntegerType()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("toIntegerType"));
    }
    public void setToIntegerType(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("toIntegerType", item.getValue());
		}
    }
    /**
     * Object:房间调价's 位数property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getDigit()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("digit"));
    }
    public void setDigit(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("digit", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1F8B4222");
    }
}