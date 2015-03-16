package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitNoConCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitNoConCollection()
    {
        super(FDCProDepSplitNoConInfo.class);
    }
    public boolean add(FDCProDepSplitNoConInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitNoConCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitNoConInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitNoConInfo get(int index)
    {
        return(FDCProDepSplitNoConInfo)getObject(index);
    }
    public FDCProDepSplitNoConInfo get(Object key)
    {
        return(FDCProDepSplitNoConInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitNoConInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitNoConInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitNoConInfo item)
    {
        return super.indexOf(item);
    }
}