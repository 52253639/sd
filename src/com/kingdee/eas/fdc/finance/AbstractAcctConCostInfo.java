package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAcctConCostInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAcctConCostInfo()
    {
        this("id");
    }
    protected AbstractAcctConCostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:科目合同已完工's 科目property 
     */
    public String getAccount()
    {
        return getString("account");
    }
    public void setAccount(String item)
    {
        setString("account", item);
    }
    /**
     * Object:科目合同已完工's 合同property 
     */
    public String getContract()
    {
        return getString("contract");
    }
    public void setContract(String item)
    {
        setString("contract", item);
    }
    /**
     * Object:科目合同已完工's 已完工property 
     */
    public java.math.BigDecimal getLstCost()
    {
        return getBigDecimal("lstCost");
    }
    public void setLstCost(java.math.BigDecimal item)
    {
        setBigDecimal("lstCost", item);
    }
    /**
     * Object:科目合同已完工's 头property 
     */
    public String getHead()
    {
        return getString("head");
    }
    public void setHead(String item)
    {
        setString("head", item);
    }
    /**
     * Object:科目合同已完工's 工程项目property 
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
     * Object:科目合同已完工's 期间property 
     */
    public String getFdcPeriod()
    {
        return getString("fdcPeriod");
    }
    public void setFdcPeriod(String item)
    {
        setString("fdcPeriod", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CC31746D");
    }
}