package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChequeDetailEntryCollection extends AbstractObjectCollection 
{
    public ChequeDetailEntryCollection()
    {
        super(ChequeDetailEntryInfo.class);
    }
    public boolean add(ChequeDetailEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChequeDetailEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChequeDetailEntryInfo item)
    {
        return removeObject(item);
    }
    public ChequeDetailEntryInfo get(int index)
    {
        return(ChequeDetailEntryInfo)getObject(index);
    }
    public ChequeDetailEntryInfo get(Object key)
    {
        return(ChequeDetailEntryInfo)getObject(key);
    }
    public void set(int index, ChequeDetailEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChequeDetailEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChequeDetailEntryInfo item)
    {
        return super.indexOf(item);
    }
}