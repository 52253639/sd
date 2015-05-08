package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCWBSCollection extends AbstractObjectCollection 
{
    public FDCWBSCollection()
    {
        super(FDCWBSInfo.class);
    }
    public boolean add(FDCWBSInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCWBSCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCWBSInfo item)
    {
        return removeObject(item);
    }
    public FDCWBSInfo get(int index)
    {
        return(FDCWBSInfo)getObject(index);
    }
    public FDCWBSInfo get(Object key)
    {
        return(FDCWBSInfo)getObject(key);
    }
    public void set(int index, FDCWBSInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCWBSInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCWBSInfo item)
    {
        return super.indexOf(item);
    }
}