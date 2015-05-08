package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CtrlItemWBSEntryCollection extends AbstractObjectCollection 
{
    public CtrlItemWBSEntryCollection()
    {
        super(CtrlItemWBSEntryInfo.class);
    }
    public boolean add(CtrlItemWBSEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CtrlItemWBSEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CtrlItemWBSEntryInfo item)
    {
        return removeObject(item);
    }
    public CtrlItemWBSEntryInfo get(int index)
    {
        return(CtrlItemWBSEntryInfo)getObject(index);
    }
    public CtrlItemWBSEntryInfo get(Object key)
    {
        return(CtrlItemWBSEntryInfo)getObject(key);
    }
    public void set(int index, CtrlItemWBSEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CtrlItemWBSEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CtrlItemWBSEntryInfo item)
    {
        return super.indexOf(item);
    }
}