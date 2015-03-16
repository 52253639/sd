package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitHasConCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitHasConCollection()
    {
        super(FDCProDepSplitHasConInfo.class);
    }
    public boolean add(FDCProDepSplitHasConInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitHasConCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitHasConInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitHasConInfo get(int index)
    {
        return(FDCProDepSplitHasConInfo)getObject(index);
    }
    public FDCProDepSplitHasConInfo get(Object key)
    {
        return(FDCProDepSplitHasConInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitHasConInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitHasConInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitHasConInfo item)
    {
        return super.indexOf(item);
    }
}