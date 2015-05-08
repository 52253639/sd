package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CtrlItemWBSEntrysCollection extends AbstractObjectCollection 
{
    public CtrlItemWBSEntrysCollection()
    {
        super(CtrlItemWBSEntrysInfo.class);
    }
    public boolean add(CtrlItemWBSEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CtrlItemWBSEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CtrlItemWBSEntrysInfo item)
    {
        return removeObject(item);
    }
    public CtrlItemWBSEntrysInfo get(int index)
    {
        return(CtrlItemWBSEntrysInfo)getObject(index);
    }
    public CtrlItemWBSEntrysInfo get(Object key)
    {
        return(CtrlItemWBSEntrysInfo)getObject(key);
    }
    public void set(int index, CtrlItemWBSEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CtrlItemWBSEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CtrlItemWBSEntrysInfo item)
    {
        return super.indexOf(item);
    }
}