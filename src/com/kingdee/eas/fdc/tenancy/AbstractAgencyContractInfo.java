package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgencyContractInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractAgencyContractInfo()
    {
        this("id");
    }
    protected AbstractAgencyContractInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 中介代理合同 's 营销项目 property 
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
     * Object: 中介代理合同 's 中介机构 property 
     */
    public com.kingdee.eas.fdc.tenancy.AgencyInfo getAgency()
    {
        return (com.kingdee.eas.fdc.tenancy.AgencyInfo)get("agency");
    }
    public void setAgency(com.kingdee.eas.fdc.tenancy.AgencyInfo item)
    {
        put("agency", item);
    }
    /**
     * Object: 中介代理合同 's 签订人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSigner()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("signer");
    }
    public void setSigner(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("signer", item);
    }
    /**
     * Object:中介代理合同's 代理内容property 
     */
    public String getActingContent()
    {
        return getString("actingContent");
    }
    public void setActingContent(String item)
    {
        setString("actingContent", item);
    }
    /**
     * Object:中介代理合同's 特殊条款property 
     */
    public String getSpecialClause()
    {
        return getString("specialClause");
    }
    public void setSpecialClause(String item)
    {
        setString("specialClause", item);
    }
    /**
     * Object:中介代理合同's 佣金标准property 
     */
    public com.kingdee.eas.fdc.tenancy.CommisionStandardEnum getCommisionStandard()
    {
        return com.kingdee.eas.fdc.tenancy.CommisionStandardEnum.getEnum(getString("commisionStandard"));
    }
    public void setCommisionStandard(com.kingdee.eas.fdc.tenancy.CommisionStandardEnum item)
    {
		if (item != null) {
        setString("commisionStandard", item.getValue());
		}
    }
    /**
     * Object:中介代理合同's 金额(百分比)property 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    /**
     * Object:中介代理合同's 应付日期类型property 
     */
    public com.kingdee.eas.fdc.tenancy.AppPayDateTypeEnum getAppPayDateType()
    {
        return com.kingdee.eas.fdc.tenancy.AppPayDateTypeEnum.getEnum(getString("appPayDateType"));
    }
    public void setAppPayDateType(com.kingdee.eas.fdc.tenancy.AppPayDateTypeEnum item)
    {
		if (item != null) {
        setString("appPayDateType", item.getValue());
		}
    }
    /**
     * Object:中介代理合同's 偏移天数property 
     */
    public int getPayOffsetDays()
    {
        return getInt("payOffsetDays");
    }
    public void setPayOffsetDays(int item)
    {
        setInt("payOffsetDays", item);
    }
    /**
     * Object:中介代理合同's 启用日期property 
     */
    public java.util.Date getStartUsingDate()
    {
        return getDate("startUsingDate");
    }
    public void setStartUsingDate(java.util.Date item)
    {
        setDate("startUsingDate", item);
    }
    /**
     * Object:中介代理合同's 终止日期property 
     */
    public java.util.Date getStopUsingDate()
    {
        return getDate("stopUsingDate");
    }
    public void setStopUsingDate(java.util.Date item)
    {
        setDate("stopUsingDate", item);
    }
    /**
     * Object: 中介代理合同 's 终止人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getStopper()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("stopper");
    }
    public void setStopper(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("stopper", item);
    }
    /**
     * Object:中介代理合同's 合同状态property 
     */
    public com.kingdee.eas.fdc.tenancy.ContractStateEnum getContractState()
    {
        return com.kingdee.eas.fdc.tenancy.ContractStateEnum.getEnum(getString("contractState"));
    }
    public void setContractState(com.kingdee.eas.fdc.tenancy.ContractStateEnum item)
    {
		if (item != null) {
        setString("contractState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("93E8D220");
    }
}