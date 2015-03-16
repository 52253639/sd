package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitEntryEntryCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitEntryEntryCollection()
    {
        super(FDCProDepSplitEntryEntryInfo.class);
    }
    public boolean add(FDCProDepSplitEntryEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitEntryEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitEntryEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitEntryEntryInfo get(int index)
    {
        return(FDCProDepSplitEntryEntryInfo)getObject(index);
    }
    public FDCProDepSplitEntryEntryInfo get(Object key)
    {
        return(FDCProDepSplitEntryEntryInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitEntryEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitEntryEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitEntryEntryInfo item)
    {
        return super.indexOf(item);
    }
}