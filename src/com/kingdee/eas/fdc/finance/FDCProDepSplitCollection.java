package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitCollection()
    {
        super(FDCProDepSplitInfo.class);
    }
    public boolean add(FDCProDepSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitInfo get(int index)
    {
        return(FDCProDepSplitInfo)getObject(index);
    }
    public FDCProDepSplitInfo get(Object key)
    {
        return(FDCProDepSplitInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitInfo item)
    {
        return super.indexOf(item);
    }
}