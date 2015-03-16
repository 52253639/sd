package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHERevbillTwoEntryCollection extends AbstractObjectCollection 
{
    public SHERevbillTwoEntryCollection()
    {
        super(SHERevbillTwoEntryInfo.class);
    }
    public boolean add(SHERevbillTwoEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHERevbillTwoEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHERevbillTwoEntryInfo item)
    {
        return removeObject(item);
    }
    public SHERevbillTwoEntryInfo get(int index)
    {
        return(SHERevbillTwoEntryInfo)getObject(index);
    }
    public SHERevbillTwoEntryInfo get(Object key)
    {
        return(SHERevbillTwoEntryInfo)getObject(key);
    }
    public void set(int index, SHERevbillTwoEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHERevbillTwoEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHERevbillTwoEntryInfo item)
    {
        return super.indexOf(item);
    }
}