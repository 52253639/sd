package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CtrlItemCollection extends AbstractObjectCollection 
{
    public CtrlItemCollection()
    {
        super(CtrlItemInfo.class);
    }
    public boolean add(CtrlItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CtrlItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CtrlItemInfo item)
    {
        return removeObject(item);
    }
    public CtrlItemInfo get(int index)
    {
        return(CtrlItemInfo)getObject(index);
    }
    public CtrlItemInfo get(Object key)
    {
        return(CtrlItemInfo)getObject(key);
    }
    public void set(int index, CtrlItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CtrlItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CtrlItemInfo item)
    {
        return super.indexOf(item);
    }
}