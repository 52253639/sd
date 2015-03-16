package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeCusomerOldEntryCollection extends AbstractObjectCollection 
{
    public PurchaseChangeCusomerOldEntryCollection()
    {
        super(PurchaseChangeCusomerOldEntryInfo.class);
    }
    public boolean add(PurchaseChangeCusomerOldEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeCusomerOldEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeCusomerOldEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeCusomerOldEntryInfo get(int index)
    {
        return(PurchaseChangeCusomerOldEntryInfo)getObject(index);
    }
    public PurchaseChangeCusomerOldEntryInfo get(Object key)
    {
        return(PurchaseChangeCusomerOldEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeCusomerOldEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeCusomerOldEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeCusomerOldEntryInfo item)
    {
        return super.indexOf(item);
    }
}