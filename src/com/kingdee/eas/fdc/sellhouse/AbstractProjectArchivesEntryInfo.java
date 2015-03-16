package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectArchivesEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractProjectArchivesEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectArchivesEntryInfo(String pkField)
    {
        super(pkField);
        put("archivesEntrys", new com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection());
    }
    /**
     * Object: 项目档案分录 's 营销项目 property 
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
     * Object: 项目档案分录 's 具体档案属性分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection getArchivesEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection)get("archivesEntrys");
    }
    /**
     * Object:项目档案分录's 范围类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum getScopeType()
    {
        return com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum.getEnum(getString("scopeType"));
    }
    public void setScopeType(com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum item)
    {
		if (item != null) {
        setString("scopeType", item.getValue());
		}
    }
    /**
     * Object:项目档案分录's 档案名称property 
     */
    public String getArchivesName()
    {
        return getString("archivesName");
    }
    public void setArchivesName(String item)
    {
        setString("archivesName", item);
    }
    /**
     * Object:项目档案分录's 档案所属类别property 
     */
    public com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum getBaseDataProperty()
    {
        return com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum.getEnum(getString("baseDataProperty"));
    }
    public void setBaseDataProperty(com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum item)
    {
		if (item != null) {
        setString("baseDataProperty", item.getValue());
		}
    }
    /**
     * Object:项目档案分录's 档案分类property 
     */
    public com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum getArchivesType()
    {
        return com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum.getEnum(getString("archivesType"));
    }
    public void setArchivesType(com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum item)
    {
		if (item != null) {
        setString("archivesType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("92090FC3");
    }
}