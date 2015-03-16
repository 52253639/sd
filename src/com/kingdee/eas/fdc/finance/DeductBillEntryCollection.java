package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeductBillEntryCollection extends AbstractObjectCollection 
{
    public DeductBillEntryCollection()
    {
        super(DeductBillEntryInfo.class);
    }
    public boolean add(DeductBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeductBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeductBillEntryInfo item)
    {
        return removeObject(item);
    }
    public DeductBillEntryInfo get(int index)
    {
        return(DeductBillEntryInfo)getObject(index);
    }
    public DeductBillEntryInfo get(Object key)
    {
        return(DeductBillEntryInfo)getObject(key);
    }
    public void set(int index, DeductBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeductBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeductBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}