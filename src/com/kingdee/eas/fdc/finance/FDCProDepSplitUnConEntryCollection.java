package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitUnConEntryCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitUnConEntryCollection()
    {
        super(FDCProDepSplitUnConEntryInfo.class);
    }
    public boolean add(FDCProDepSplitUnConEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitUnConEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitUnConEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitUnConEntryInfo get(int index)
    {
        return(FDCProDepSplitUnConEntryInfo)getObject(index);
    }
    public FDCProDepSplitUnConEntryInfo get(Object key)
    {
        return(FDCProDepSplitUnConEntryInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitUnConEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitUnConEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitUnConEntryInfo item)
    {
        return super.indexOf(item);
    }
}