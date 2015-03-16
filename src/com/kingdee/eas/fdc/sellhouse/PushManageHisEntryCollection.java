package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PushManageHisEntryCollection extends AbstractObjectCollection 
{
    public PushManageHisEntryCollection()
    {
        super(PushManageHisEntryInfo.class);
    }
    public boolean add(PushManageHisEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PushManageHisEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PushManageHisEntryInfo item)
    {
        return removeObject(item);
    }
    public PushManageHisEntryInfo get(int index)
    {
        return(PushManageHisEntryInfo)getObject(index);
    }
    public PushManageHisEntryInfo get(Object key)
    {
        return(PushManageHisEntryInfo)getObject(key);
    }
    public void set(int index, PushManageHisEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PushManageHisEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PushManageHisEntryInfo item)
    {
        return super.indexOf(item);
    }
}