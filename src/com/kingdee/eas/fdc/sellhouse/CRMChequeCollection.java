package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CRMChequeCollection extends AbstractObjectCollection 
{
    public CRMChequeCollection()
    {
        super(CRMChequeInfo.class);
    }
    public boolean add(CRMChequeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CRMChequeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CRMChequeInfo item)
    {
        return removeObject(item);
    }
    public CRMChequeInfo get(int index)
    {
        return(CRMChequeInfo)getObject(index);
    }
    public CRMChequeInfo get(Object key)
    {
        return(CRMChequeInfo)getObject(key);
    }
    public void set(int index, CRMChequeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CRMChequeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CRMChequeInfo item)
    {
        return super.indexOf(item);
    }
}