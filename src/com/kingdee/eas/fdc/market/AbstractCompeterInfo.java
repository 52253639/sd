package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompeterInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCompeterInfo()
    {
        this("id");
    }
    protected AbstractCompeterInfo(String pkField)
    {
        super(pkField);
        put("priceRecordList", new com.kingdee.eas.fdc.market.CompetePriceRecordCollection());
    }
    /**
     * Object:竟争对手's 所属系统property 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getSysType()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("sysType"));
    }
    public void setSysType(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("sysType", item.getValue());
		}
    }
    /**
     * Object:竟争对手's 租金类型property 
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
     * Object:竟争对手's 最新均价property 
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
     * Object:竟争对手's 优势property 
     */
    public String getAdvantage()
    {
        return getString("advantage");
    }
    public void setAdvantage(String item)
    {
        setString("advantage", item);
    }
    /**
     * Object:竟争对手's 劣势property 
     */
    public String getDisAdvantage()
    {
        return getString("disAdvantage");
    }
    public void setDisAdvantage(String item)
    {
        setString("disAdvantage", item);
    }
    /**
     * Object: 竟争对手 's 价格记录 property 
     */
    public com.kingdee.eas.fdc.market.CompetePriceRecordCollection getPriceRecordList()
    {
        return (com.kingdee.eas.fdc.market.CompetePriceRecordCollection)get("priceRecordList");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0D5D3DFC");
    }
}