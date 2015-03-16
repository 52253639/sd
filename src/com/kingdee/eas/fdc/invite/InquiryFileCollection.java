package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InquiryFileCollection extends AbstractObjectCollection 
{
    public InquiryFileCollection()
    {
        super(InquiryFileInfo.class);
    }
    public boolean add(InquiryFileInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InquiryFileCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InquiryFileInfo item)
    {
        return removeObject(item);
    }
    public InquiryFileInfo get(int index)
    {
        return(InquiryFileInfo)getObject(index);
    }
    public InquiryFileInfo get(Object key)
    {
        return(InquiryFileInfo)getObject(key);
    }
    public void set(int index, InquiryFileInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InquiryFileInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InquiryFileInfo item)
    {
        return super.indexOf(item);
    }
}