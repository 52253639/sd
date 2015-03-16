package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LoanDataEntryCollection extends AbstractObjectCollection 
{
    public LoanDataEntryCollection()
    {
        super(LoanDataEntryInfo.class);
    }
    public boolean add(LoanDataEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LoanDataEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LoanDataEntryInfo item)
    {
        return removeObject(item);
    }
    public LoanDataEntryInfo get(int index)
    {
        return(LoanDataEntryInfo)getObject(index);
    }
    public LoanDataEntryInfo get(Object key)
    {
        return(LoanDataEntryInfo)getObject(key);
    }
    public void set(int index, LoanDataEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LoanDataEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LoanDataEntryInfo item)
    {
        return super.indexOf(item);
    }
}