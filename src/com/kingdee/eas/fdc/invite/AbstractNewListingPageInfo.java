package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListingPageInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewListingPageInfo()
    {
        this("id");
    }
    protected AbstractNewListingPageInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.NewListingEntryCollection());
        put("columns", new com.kingdee.eas.fdc.invite.NewListingColumnCollection());
        put("sumEntry", new com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection());
    }
    /**
     * Object: 清单页签 's 页签 property 
     */
    public com.kingdee.eas.fdc.invite.PageHeadInfo getPageHead()
    {
        return (com.kingdee.eas.fdc.invite.PageHeadInfo)get("pageHead");
    }
    public void setPageHead(com.kingdee.eas.fdc.invite.PageHeadInfo item)
    {
        put("pageHead", item);
    }
    /**
     * Object: 清单页签 's 表头类型 property 
     */
    public com.kingdee.eas.fdc.invite.HeadTypeInfo getHeadType()
    {
        return (com.kingdee.eas.fdc.invite.HeadTypeInfo)get("headType");
    }
    public void setHeadType(com.kingdee.eas.fdc.invite.HeadTypeInfo item)
    {
        put("headType", item);
    }
    /**
     * Object: 清单页签 's 列 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingColumnCollection getColumns()
    {
        return (com.kingdee.eas.fdc.invite.NewListingColumnCollection)get("columns");
    }
    /**
     * Object: 清单页签 's 模板子目 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.NewListingEntryCollection)get("entrys");
    }
    /**
     * Object: 清单页签 's 清单模板头 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.NewListingInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.NewListingInfo item)
    {
        put("head", item);
    }
    /**
     * Object:清单页签's 表格二进制数据property 
     */
    public byte[] getTableData()
    {
        return (byte[])get("tableData");
    }
    public void setTableData(byte[] item)
    {
        put("tableData", item);
    }
    /**
     * Object:清单页签's 页签汇总数property 
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
     * Object: 清单页签 's 报价汇总数 property 
     */
    public com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection getSumEntry()
    {
        return (com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection)get("sumEntry");
    }
    /**
     * Object:清单页签's 描述property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC989D97");
    }
}