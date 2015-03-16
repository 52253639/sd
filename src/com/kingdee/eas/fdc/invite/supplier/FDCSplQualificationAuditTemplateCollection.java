package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplQualificationAuditTemplateCollection extends AbstractObjectCollection 
{
    public FDCSplQualificationAuditTemplateCollection()
    {
        super(FDCSplQualificationAuditTemplateInfo.class);
    }
    public boolean add(FDCSplQualificationAuditTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplQualificationAuditTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplQualificationAuditTemplateInfo item)
    {
        return removeObject(item);
    }
    public FDCSplQualificationAuditTemplateInfo get(int index)
    {
        return(FDCSplQualificationAuditTemplateInfo)getObject(index);
    }
    public FDCSplQualificationAuditTemplateInfo get(Object key)
    {
        return(FDCSplQualificationAuditTemplateInfo)getObject(key);
    }
    public void set(int index, FDCSplQualificationAuditTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplQualificationAuditTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplQualificationAuditTemplateInfo item)
    {
        return super.indexOf(item);
    }
}