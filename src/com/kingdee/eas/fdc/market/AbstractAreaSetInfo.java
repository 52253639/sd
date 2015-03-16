package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAreaSetInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractAreaSetInfo()
    {
        this("id");
    }
    protected AbstractAreaSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:面积段设定's minAreaproperty 
     */
    public java.math.BigDecimal getMinArea()
    {
        return getBigDecimal("minArea");
    }
    public void setMinArea(java.math.BigDecimal item)
    {
        setBigDecimal("minArea", item);
    }
    /**
     * Object:面积段设定's maxAreaproperty 
     */
    public java.math.BigDecimal getMaxArea()
    {
        return getBigDecimal("maxArea");
    }
    public void setMaxArea(java.math.BigDecimal item)
    {
        setBigDecimal("maxArea", item);
    }
    /**
     * Object:面积段设定's 比较符一property 
     */
    public com.kingdee.eas.fdc.market.MarketCompareEnum getMinCompare()
    {
        return com.kingdee.eas.fdc.market.MarketCompareEnum.getEnum(getString("minCompare"));
    }
    public void setMinCompare(com.kingdee.eas.fdc.market.MarketCompareEnum item)
    {
		if (item != null) {
        setString("minCompare", item.getValue());
		}
    }
    /**
     * Object:面积段设定's 比较符二property 
     */
    public com.kingdee.eas.fdc.market.MarketCompareEnum getMaxCompare()
    {
        return com.kingdee.eas.fdc.market.MarketCompareEnum.getEnum(getString("maxCompare"));
    }
    public void setMaxCompare(com.kingdee.eas.fdc.market.MarketCompareEnum item)
    {
		if (item != null) {
        setString("maxCompare", item.getValue());
		}
    }
    /**
     * Object:面积段设定's 最小面积值property 
     */
    public String getMinAreaVal()
    {
        return getString("minAreaVal");
    }
    public void setMinAreaVal(String item)
    {
        setString("minAreaVal", item);
    }
    /**
     * Object:面积段设定's 最大面积值property 
     */
    public String getMaxAreaVal()
    {
        return getString("maxAreaVal");
    }
    public void setMaxAreaVal(String item)
    {
        setString("maxAreaVal", item);
    }
    /**
     * Object: 面积段设定 's 所属组织ID property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getCompanyId()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("companyId");
    }
    public void setCompanyId(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("companyId", item);
    }
    /**
     * Object:面积段设定's 所属组织编码property 
     */
    public String getCompanyNum()
    {
        return getString("companyNum");
    }
    public void setCompanyNum(String item)
    {
        setString("companyNum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("617A29A4");
    }
}