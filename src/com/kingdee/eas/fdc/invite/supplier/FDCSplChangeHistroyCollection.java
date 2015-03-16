package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplChangeHistroyCollection extends AbstractObjectCollection 
{
    public FDCSplChangeHistroyCollection()
    {
        super(FDCSplChangeHistroyInfo.class);
    }
    public boolean add(FDCSplChangeHistroyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplChangeHistroyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplChangeHistroyInfo item)
    {
        return removeObject(item);
    }
    public FDCSplChangeHistroyInfo get(int index)
    {
        return(FDCSplChangeHistroyInfo)getObject(index);
    }
    public FDCSplChangeHistroyInfo get(Object key)
    {
        return(FDCSplChangeHistroyInfo)getObject(key);
    }
    public void set(int index, FDCSplChangeHistroyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplChangeHistroyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplChangeHistroyInfo item)
    {
        return super.indexOf(item);
    }
}