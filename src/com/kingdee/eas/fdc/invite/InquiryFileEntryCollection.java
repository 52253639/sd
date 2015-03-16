package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InquiryFileEntryCollection extends AbstractObjectCollection 
{
    public InquiryFileEntryCollection()
    {
        super(InquiryFileEntryInfo.class);
    }
    public boolean add(InquiryFileEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InquiryFileEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InquiryFileEntryInfo item)
    {
        return removeObject(item);
    }
    public InquiryFileEntryInfo get(int index)
    {
        return(InquiryFileEntryInfo)getObject(index);
    }
    public InquiryFileEntryInfo get(Object key)
    {
        return(InquiryFileEntryInfo)getObject(key);
    }
    public void set(int index, InquiryFileEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InquiryFileEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InquiryFileEntryInfo item)
    {
        return super.indexOf(item);
    }
}