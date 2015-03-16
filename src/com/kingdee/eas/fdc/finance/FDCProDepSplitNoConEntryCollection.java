package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitNoConEntryCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitNoConEntryCollection()
    {
        super(FDCProDepSplitNoConEntryInfo.class);
    }
    public boolean add(FDCProDepSplitNoConEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitNoConEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitNoConEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitNoConEntryInfo get(int index)
    {
        return(FDCProDepSplitNoConEntryInfo)getObject(index);
    }
    public FDCProDepSplitNoConEntryInfo get(Object key)
    {
        return(FDCProDepSplitNoConEntryInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitNoConEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitNoConEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitNoConEntryInfo item)
    {
        return super.indexOf(item);
    }
}