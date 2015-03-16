package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LoanDataCollection extends AbstractObjectCollection 
{
    public LoanDataCollection()
    {
        super(LoanDataInfo.class);
    }
    public boolean add(LoanDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LoanDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LoanDataInfo item)
    {
        return removeObject(item);
    }
    public LoanDataInfo get(int index)
    {
        return(LoanDataInfo)getObject(index);
    }
    public LoanDataInfo get(Object key)
    {
        return(LoanDataInfo)getObject(key);
    }
    public void set(int index, LoanDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LoanDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LoanDataInfo item)
    {
        return super.indexOf(item);
    }
}