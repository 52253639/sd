package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLiquidatedManageInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractLiquidatedManageInfo()
    {
        this("id");
    }
    protected AbstractLiquidatedManageInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 违约金管理 's 租售项目 property 
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
     * Object: 违约金管理 's 租凭合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object: 违约金管理 's 款项名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object:违约金管理's 应收日期property 
     */
    public java.util.Date getReceiveDate()
    {
        return getDate("receiveDate");
    }
    public void setReceiveDate(java.util.Date item)
    {
        setDate("receiveDate", item);
    }
    /**
     * Object:违约金管理's 费用起始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:违约金管理's 费用结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:违约金管理's 违约金比例property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:违约金管理's 违约金比例日期property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getRateDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("rateDate"));
    }
    public void setRateDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("rateDate", item.getValue());
		}
    }
    /**
     * Object:违约金管理's 违约金金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:违约金管理's 违约金计算起始日期property 
     */
    public java.util.Date getLiqStartDate()
    {
        return getDate("liqStartDate");
    }
    public void setLiqStartDate(java.util.Date item)
    {
        setDate("liqStartDate", item);
    }
    /**
     * Object:违约金管理's 违约金计算截止日期property 
     */
    public java.util.Date getLiqEndDate()
    {
        return getDate("liqEndDate");
    }
    public void setLiqEndDate(java.util.Date item)
    {
        setDate("liqEndDate", item);
    }
    /**
     * Object:违约金管理's 是否生成应收property 
     */
    public boolean isIsGenerate()
    {
        return getBoolean("isGenerate");
    }
    public void setIsGenerate(boolean item)
    {
        setBoolean("isGenerate", item);
    }
    /**
     * Object:违约金管理's 应收IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getTenBillOtherPayID()
    {
        return getBOSUuid("tenBillOtherPayID");
    }
    public void setTenBillOtherPayID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("tenBillOtherPayID", item);
    }
    /**
     * Object:违约金管理's 生成其他应收IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getGenOtherPayID()
    {
        return getBOSUuid("genOtherPayID");
    }
    public void setGenOtherPayID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("genOtherPayID", item);
    }
    /**
     * Object:违约金管理's 减免金额property 
     */
    public java.math.BigDecimal getReliefAmount()
    {
        return getBigDecimal("reliefAmount");
    }
    public void setReliefAmount(java.math.BigDecimal item)
    {
        setBigDecimal("reliefAmount", item);
    }
    /**
     * Object:违约金管理's 剩余金额property 
     */
    public java.math.BigDecimal getActAmount()
    {
        return getBigDecimal("actAmount");
    }
    public void setActAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("06DE99FC");
    }
}