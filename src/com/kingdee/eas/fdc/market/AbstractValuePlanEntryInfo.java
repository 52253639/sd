package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValuePlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractValuePlanEntryInfo()
    {
        this("id");
    }
    protected AbstractValuePlanEntryInfo(String pkField)
    {
        super(pkField);
        put("detialEntry", new com.kingdee.eas.fdc.market.ValuePlanDetialEntryCollection());
    }
    /**
     * Object: 销售计划分录 's 头 property 
     */
    public com.kingdee.eas.fdc.market.ValuePlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.market.ValuePlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.market.ValuePlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object:销售计划分录's 项目property 
     */
    public String getProject()
    {
        return getString("project");
    }
    public void setProject(String item)
    {
        setString("project", item);
    }
    /**
     * Object:销售计划分录's 分期property 
     */
    public String getOperationPhases()
    {
        return getString("operationPhases");
    }
    public void setOperationPhases(String item)
    {
        setString("operationPhases", item);
    }
    /**
     * Object:销售计划分录's 供应批次property 
     */
    public String getBatch()
    {
        return getString("batch");
    }
    public void setBatch(String item)
    {
        setString("batch", item);
    }
    /**
     * Object:销售计划分录's 物业形态property 
     */
    public String getProductType()
    {
        return getString("productType");
    }
    public void setProductType(String item)
    {
        setString("productType", item);
    }
    /**
     * Object:销售计划分录's 装修标准property 
     */
    public com.kingdee.eas.fdc.market.DecorateEnum getDecorate()
    {
        return com.kingdee.eas.fdc.market.DecorateEnum.getEnum(getString("decorate"));
    }
    public void setDecorate(com.kingdee.eas.fdc.market.DecorateEnum item)
    {
		if (item != null) {
        setString("decorate", item.getValue());
		}
    }
    /**
     * Object:销售计划分录's 套数property 
     */
    public int getAccount()
    {
        return getInt("account");
    }
    public void setAccount(int item)
    {
        setInt("account", item);
    }
    /**
     * Object:销售计划分录's 面积property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:销售计划分录's 均价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:销售计划分录's 金额property 
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
     * Object:销售计划分录's 套数property 
     */
    public int getSaccount()
    {
        return getInt("saccount");
    }
    public void setSaccount(int item)
    {
        setInt("saccount", item);
    }
    /**
     * Object:销售计划分录's 面积property 
     */
    public java.math.BigDecimal getSarea()
    {
        return getBigDecimal("sarea");
    }
    public void setSarea(java.math.BigDecimal item)
    {
        setBigDecimal("sarea", item);
    }
    /**
     * Object:销售计划分录's 均价property 
     */
    public java.math.BigDecimal getSprice()
    {
        return getBigDecimal("sprice");
    }
    public void setSprice(java.math.BigDecimal item)
    {
        setBigDecimal("sprice", item);
    }
    /**
     * Object:销售计划分录's 金额property 
     */
    public java.math.BigDecimal getSamount()
    {
        return getBigDecimal("samount");
    }
    public void setSamount(java.math.BigDecimal item)
    {
        setBigDecimal("samount", item);
    }
    /**
     * Object:销售计划分录's 套数property 
     */
    public int getDaccount()
    {
        return getInt("daccount");
    }
    public void setDaccount(int item)
    {
        setInt("daccount", item);
    }
    /**
     * Object:销售计划分录's 面积property 
     */
    public java.math.BigDecimal getDarea()
    {
        return getBigDecimal("darea");
    }
    public void setDarea(java.math.BigDecimal item)
    {
        setBigDecimal("darea", item);
    }
    /**
     * Object:销售计划分录's 均价property 
     */
    public java.math.BigDecimal getDprice()
    {
        return getBigDecimal("dprice");
    }
    public void setDprice(java.math.BigDecimal item)
    {
        setBigDecimal("dprice", item);
    }
    /**
     * Object:销售计划分录's 金额property 
     */
    public java.math.BigDecimal getDamount()
    {
        return getBigDecimal("damount");
    }
    public void setDamount(java.math.BigDecimal item)
    {
        setBigDecimal("damount", item);
    }
    /**
     * Object:销售计划分录's 套数property 
     */
    public int getPaccount()
    {
        return getInt("paccount");
    }
    public void setPaccount(int item)
    {
        setInt("paccount", item);
    }
    /**
     * Object:销售计划分录's 面积property 
     */
    public java.math.BigDecimal getParea()
    {
        return getBigDecimal("parea");
    }
    public void setParea(java.math.BigDecimal item)
    {
        setBigDecimal("parea", item);
    }
    /**
     * Object:销售计划分录's 均价property 
     */
    public java.math.BigDecimal getPprice()
    {
        return getBigDecimal("pprice");
    }
    public void setPprice(java.math.BigDecimal item)
    {
        setBigDecimal("pprice", item);
    }
    /**
     * Object:销售计划分录's 金额property 
     */
    public java.math.BigDecimal getPamount()
    {
        return getBigDecimal("pamount");
    }
    public void setPamount(java.math.BigDecimal item)
    {
        setBigDecimal("pamount", item);
    }
    /**
     * Object:销售计划分录's 说明property 
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
     * Object: 销售计划分录 's 明细分录 property 
     */
    public com.kingdee.eas.fdc.market.ValuePlanDetialEntryCollection getDetialEntry()
    {
        return (com.kingdee.eas.fdc.market.ValuePlanDetialEntryCollection)get("detialEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("95632069");
    }
}