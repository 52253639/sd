package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitHasConEntryCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitHasConEntryCollection()
    {
        super(FDCProDepSplitHasConEntryInfo.class);
    }
    public boolean add(FDCProDepSplitHasConEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitHasConEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitHasConEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitHasConEntryInfo get(int index)
    {
        return(FDCProDepSplitHasConEntryInfo)getObject(index);
    }
    public FDCProDepSplitHasConEntryInfo get(Object key)
    {
        return(FDCProDepSplitHasConEntryInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitHasConEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitHasConEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitHasConEntryInfo item)
    {
        return super.indexOf(item);
    }
}