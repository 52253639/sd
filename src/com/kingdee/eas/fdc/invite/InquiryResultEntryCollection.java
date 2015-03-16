package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InquiryResultEntryCollection extends AbstractObjectCollection 
{
    public InquiryResultEntryCollection()
    {
        super(InquiryResultEntryInfo.class);
    }
    public boolean add(InquiryResultEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InquiryResultEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InquiryResultEntryInfo item)
    {
        return removeObject(item);
    }
    public InquiryResultEntryInfo get(int index)
    {
        return(InquiryResultEntryInfo)getObject(index);
    }
    public InquiryResultEntryInfo get(Object key)
    {
        return(InquiryResultEntryInfo)getObject(key);
    }
    public void set(int index, InquiryResultEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InquiryResultEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InquiryResultEntryInfo item)
    {
        return super.indexOf(item);
    }
}