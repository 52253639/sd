package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompetePriceRecordInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCompetePriceRecordInfo()
    {
        this("id");
    }
    protected AbstractCompetePriceRecordInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 竞争价格分录 's 竞争对手 property 
     */
    public com.kingdee.eas.fdc.market.CompeterInfo getCompeter()
    {
        return (com.kingdee.eas.fdc.market.CompeterInfo)get("competer");
    }
    public void setCompeter(com.kingdee.eas.fdc.market.CompeterInfo item)
    {
        put("competer", item);
    }
    /**
     * Object:竞争价格分录's 调查日期property 
     */
    public java.util.Date getInvestDate()
    {
        return getDate("investDate");
    }
    public void setInvestDate(java.util.Date item)
    {
        setDate("investDate", item);
    }
    /**
     * Object:竞争价格分录's 租金类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getRendtype()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("rendtype"));
    }
    public void setRendtype(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("rendtype", item.getValue());
		}
    }
    /**
     * Object:竞争价格分录's 最新均价property 
     */
    public java.math.BigDecimal getLastAveragePrice()
    {
        return getBigDecimal("lastAveragePrice");
    }
    public void setLastAveragePrice(java.math.BigDecimal item)
    {
        setBigDecimal("lastAveragePrice", item);
    }
    /**
     * Object:竞争价格分录's 优惠方式property 
     */
    public String getFavourableType()
    {
        return getString("favourableType");
    }
    public void setFavourableType(String item)
    {
        setString("favourableType", item);
    }
    /**
     * Object:竞争价格分录's 备注property 
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
        return new BOSObjectType("3BD02FE4");
    }
}