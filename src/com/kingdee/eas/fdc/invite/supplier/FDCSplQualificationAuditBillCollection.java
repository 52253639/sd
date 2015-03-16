package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplQualificationAuditBillCollection extends AbstractObjectCollection 
{
    public FDCSplQualificationAuditBillCollection()
    {
        super(FDCSplQualificationAuditBillInfo.class);
    }
    public boolean add(FDCSplQualificationAuditBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplQualificationAuditBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplQualificationAuditBillInfo item)
    {
        return removeObject(item);
    }
    public FDCSplQualificationAuditBillInfo get(int index)
    {
        return(FDCSplQualificationAuditBillInfo)getObject(index);
    }
    public FDCSplQualificationAuditBillInfo get(Object key)
    {
        return(FDCSplQualificationAuditBillInfo)getObject(key);
    }
    public void set(int index, FDCSplQualificationAuditBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplQualificationAuditBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplQualificationAuditBillInfo item)
    {
        return super.indexOf(item);
    }
}