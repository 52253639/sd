package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSurveyInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractMarketSurveyInfo()
    {
        this("id");
    }
    protected AbstractMarketSurveyInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.MarketSurveyEntryCollection());
    }
    /**
     * Object: 市场调查管理 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.MarketSurveyEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.MarketSurveyEntryCollection)get("entrys");
    }
    /**
     * Object:市场调查管理's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:市场调查管理's 调查名称property 
     */
    public String getSurveyName()
    {
        return getString("surveyName");
    }
    public void setSurveyName(String item)
    {
        setString("surveyName", item);
    }
    /**
     * Object: 市场调查管理 's 省市 property 
     */
    public com.kingdee.eas.basedata.assistant.ProvinceInfo getProvince()
    {
        return (com.kingdee.eas.basedata.assistant.ProvinceInfo)get("province");
    }
    public void setProvince(com.kingdee.eas.basedata.assistant.ProvinceInfo item)
    {
        put("province", item);
    }
    /**
     * Object: 市场调查管理 's 区域 property 
     */
    public com.kingdee.eas.basedata.assistant.CityInfo getArea()
    {
        return (com.kingdee.eas.basedata.assistant.CityInfo)get("area");
    }
    public void setArea(com.kingdee.eas.basedata.assistant.CityInfo item)
    {
        put("area", item);
    }
    /**
     * Object: 市场调查管理 's 调查人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSurveyPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("surveyPerson");
    }
    public void setSurveyPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("surveyPerson", item);
    }
    /**
     * Object:市场调查管理's 调查月份property 
     */
    public com.kingdee.eas.hr.train.MonthEnum getSurveyMonth()
    {
        return com.kingdee.eas.hr.train.MonthEnum.getEnum(getString("surveyMonth"));
    }
    public void setSurveyMonth(com.kingdee.eas.hr.train.MonthEnum item)
    {
		if (item != null) {
        setString("surveyMonth", item.getValue());
		}
    }
    /**
     * Object:市场调查管理's 调查年度property 
     */
    public int getSurveyYear()
    {
        return getInt("surveyYear");
    }
    public void setSurveyYear(int item)
    {
        setInt("surveyYear", item);
    }
    /**
     * Object: 市场调查管理 's 县 property 
     */
    public com.kingdee.eas.basedata.assistant.RegionInfo getRegion()
    {
        return (com.kingdee.eas.basedata.assistant.RegionInfo)get("region");
    }
    public void setRegion(com.kingdee.eas.basedata.assistant.RegionInfo item)
    {
        put("region", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6A863F07");
    }
}