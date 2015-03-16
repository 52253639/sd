package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChequeRevListEntryCollection extends AbstractObjectCollection 
{
    public ChequeRevListEntryCollection()
    {
        super(ChequeRevListEntryInfo.class);
    }
    public boolean add(ChequeRevListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChequeRevListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChequeRevListEntryInfo item)
    {
        return removeObject(item);
    }
    public ChequeRevListEntryInfo get(int index)
    {
        return(ChequeRevListEntryInfo)getObject(index);
    }
    public ChequeRevListEntryInfo get(Object key)
    {
        return(ChequeRevListEntryInfo)getObject(key);
    }
    public void set(int index, ChequeRevListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChequeRevListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChequeRevListEntryInfo item)
    {
        return super.indexOf(item);
    }
}