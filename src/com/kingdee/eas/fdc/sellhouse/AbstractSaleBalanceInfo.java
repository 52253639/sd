package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSaleBalanceInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractSaleBalanceInfo()
    {
        this("id");
    }
    protected AbstractSaleBalanceInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 销售结算 's 销售项目 property 
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
     * Object:销售结算's 操作类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum getOperateType()
    {
        return com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum.getEnum(getString("operateType"));
    }
    public void setOperateType(com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum item)
    {
		if (item != null) {
        setString("operateType", item.getValue());
		}
    }
    /**
     * Object:销售结算's 当期结算截止日property 
     */
    public java.util.Date getLastBalancDate()
    {
        return getDate("lastBalancDate");
    }
    public void setLastBalancDate(java.util.Date item)
    {
        setDate("lastBalancDate", item);
    }
    /**
     * Object:销售结算's 起始日期property 
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
     * Object:销售结算's 结束日期property 
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
     * Object:销售结算's 结算说明property 
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
     * Object:销售结算's 结算日期property 
     */
    public java.util.Date getBalanceDate()
    {
        return getDate("balanceDate");
    }
    public void setBalanceDate(java.util.Date item)
    {
        setDate("balanceDate", item);
    }
    /**
     * Object:销售结算's 认购合同总额property 
     */
    public java.math.BigDecimal getPurchContractAmount()
    {
        return getBigDecimal("purchContractAmount");
    }
    public void setPurchContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("purchContractAmount", item);
    }
    /**
     * Object:销售结算's 销售合同总额property 
     */
    public java.math.BigDecimal getSaleContractAmount()
    {
        return getBigDecimal("saleContractAmount");
    }
    public void setSaleContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("saleContractAmount", item);
    }
    /**
     * Object:销售结算's 预定合同金额property 
     */
    public java.math.BigDecimal getContractBookAmount()
    {
        return getBigDecimal("contractBookAmount");
    }
    public void setContractBookAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractBookAmount", item);
    }
    /**
     * Object: 销售结算 's 会计期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("85E60ADA");
    }
}