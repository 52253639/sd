package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValueBreakInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractValueBreakInfo()
    {
        this("id");
    }
    protected AbstractValueBreakInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.ValueBreakEntryCollection());
    }
    /**
     * Object:货值年度分解's 测算类型property 
     */
    public com.kingdee.eas.fdc.basedata.PhaseTypeEnum getMeasureType()
    {
        return com.kingdee.eas.fdc.basedata.PhaseTypeEnum.getEnum(getString("measureType"));
    }
    public void setMeasureType(com.kingdee.eas.fdc.basedata.PhaseTypeEnum item)
    {
		if (item != null) {
        setString("measureType", item.getValue());
		}
    }
    /**
     * Object:货值年度分解's 版本号property 
     */
    public String getVersion()
    {
        return getString("version");
    }
    public void setVersion(String item)
    {
        setString("version", item);
    }
    /**
     * Object:货值年度分解's 版本名称property 
     */
    public String getVersionName()
    {
        return getString("versionName");
    }
    public void setVersionName(String item)
    {
        setString("versionName", item);
    }
    /**
     * Object: 货值年度分解 's 销售项目 property 
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
     * Object: 货值年度分解 's 测算阶段 property 
     */
    public com.kingdee.eas.fdc.basedata.MeasurePhaseInfo getMeasureStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasurePhaseInfo)get("measureStage");
    }
    public void setMeasureStage(com.kingdee.eas.fdc.basedata.MeasurePhaseInfo item)
    {
        put("measureStage", item);
    }
    /**
     * Object:货值年度分解's 周期property 
     */
    public int getCycle()
    {
        return getInt("cycle");
    }
    public void setCycle(int item)
    {
        setInt("cycle", item);
    }
    /**
     * Object:货值年度分解's 年度property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:货值年度分解's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: 货值年度分解 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.ValueBreakEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.ValueBreakEntryCollection)get("entrys");
    }
    /**
     * Object:货值年度分解's 是否最新版本property 
     */
    public boolean isIsNewVersoin()
    {
        return getBoolean("isNewVersoin");
    }
    public void setIsNewVersoin(boolean item)
    {
        setBoolean("isNewVersoin", item);
    }
    /**
     * Object:货值年度分解's 版本类型property 
     */
    public com.kingdee.eas.fdc.market.VersionTypeEnum getVersionType()
    {
        return com.kingdee.eas.fdc.market.VersionTypeEnum.getEnum(getString("versionType"));
    }
    public void setVersionType(com.kingdee.eas.fdc.market.VersionTypeEnum item)
    {
		if (item != null) {
        setString("versionType", item.getValue());
		}
    }
    /**
     * Object:货值年度分解's 年度版所在年份property 
     */
    public String getEditYear()
    {
        return getString("editYear");
    }
    public void setEditYear(String item)
    {
        setString("editYear", item);
    }
    /**
     * Object:货值年度分解's 总货值property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CE9E2A9F");
    }
}