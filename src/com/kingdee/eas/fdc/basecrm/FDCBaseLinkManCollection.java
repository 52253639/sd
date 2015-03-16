package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBaseLinkManCollection extends AbstractObjectCollection 
{
    public FDCBaseLinkManCollection()
    {
        super(FDCBaseLinkManInfo.class);
    }
    public boolean add(FDCBaseLinkManInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBaseLinkManCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBaseLinkManInfo item)
    {
        return removeObject(item);
    }
    public FDCBaseLinkManInfo get(int index)
    {
        return(FDCBaseLinkManInfo)getObject(index);
    }
    public FDCBaseLinkManInfo get(Object key)
    {
        return(FDCBaseLinkManInfo)getObject(key);
    }
    public void set(int index, FDCBaseLinkManInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBaseLinkManInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBaseLinkManInfo item)
    {
        return super.indexOf(item);
    }
}