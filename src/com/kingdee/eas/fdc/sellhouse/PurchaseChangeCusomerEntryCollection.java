package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeCusomerEntryCollection extends AbstractObjectCollection 
{
    public PurchaseChangeCusomerEntryCollection()
    {
        super(PurchaseChangeCusomerEntryInfo.class);
    }
    public boolean add(PurchaseChangeCusomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeCusomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeCusomerEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeCusomerEntryInfo get(int index)
    {
        return(PurchaseChangeCusomerEntryInfo)getObject(index);
    }
    public PurchaseChangeCusomerEntryInfo get(Object key)
    {
        return(PurchaseChangeCusomerEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeCusomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeCusomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeCusomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}