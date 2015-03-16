package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitEntryCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitEntryCollection()
    {
        super(FDCProDepSplitEntryInfo.class);
    }
    public boolean add(FDCProDepSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitEntryInfo get(int index)
    {
        return(FDCProDepSplitEntryInfo)getObject(index);
    }
    public FDCProDepSplitEntryInfo get(Object key)
    {
        return(FDCProDepSplitEntryInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}