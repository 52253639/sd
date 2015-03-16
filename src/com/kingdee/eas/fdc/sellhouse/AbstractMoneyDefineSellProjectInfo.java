package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMoneyDefineSellProjectInfo extends com.kingdee.eas.fdc.propertymgmt.PPMProjectDataBaseInfo implements Serializable 
{
    public AbstractMoneyDefineSellProjectInfo()
    {
        this("id");
    }
    protected AbstractMoneyDefineSellProjectInfo(String pkField)
    {
        super(pkField);
        put("reverseEntry", new com.kingdee.eas.fdc.sellhouse.MoneyDefineReverseEntryCollection());
    }
    /**
     * Object:款项定义项目设置's 备注property 
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
     * Object: 款项定义项目设置 's 红冲款项分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineReverseEntryCollection getReverseEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineReverseEntryCollection)get("reverseEntry");
    }
    /**
     * Object: 款项定义项目设置 's 款型定义 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A10D4667");
    }
}