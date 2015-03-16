package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InquiryResultCollection extends AbstractObjectCollection 
{
    public InquiryResultCollection()
    {
        super(InquiryResultInfo.class);
    }
    public boolean add(InquiryResultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InquiryResultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InquiryResultInfo item)
    {
        return removeObject(item);
    }
    public InquiryResultInfo get(int index)
    {
        return(InquiryResultInfo)getObject(index);
    }
    public InquiryResultInfo get(Object key)
    {
        return(InquiryResultInfo)getObject(key);
    }
    public void set(int index, InquiryResultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InquiryResultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InquiryResultInfo item)
    {
        return super.indexOf(item);
    }
}