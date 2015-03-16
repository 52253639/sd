package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplQualificationAuditEntryCollection extends AbstractObjectCollection 
{
    public FDCSplQualificationAuditEntryCollection()
    {
        super(FDCSplQualificationAuditEntryInfo.class);
    }
    public boolean add(FDCSplQualificationAuditEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplQualificationAuditEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplQualificationAuditEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCSplQualificationAuditEntryInfo get(int index)
    {
        return(FDCSplQualificationAuditEntryInfo)getObject(index);
    }
    public FDCSplQualificationAuditEntryInfo get(Object key)
    {
        return(FDCSplQualificationAuditEntryInfo)getObject(key);
    }
    public void set(int index, FDCSplQualificationAuditEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplQualificationAuditEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplQualificationAuditEntryInfo item)
    {
        return super.indexOf(item);
    }
}