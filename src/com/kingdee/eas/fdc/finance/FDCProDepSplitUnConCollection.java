package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepSplitUnConCollection extends AbstractObjectCollection 
{
    public FDCProDepSplitUnConCollection()
    {
        super(FDCProDepSplitUnConInfo.class);
    }
    public boolean add(FDCProDepSplitUnConInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepSplitUnConCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepSplitUnConInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepSplitUnConInfo get(int index)
    {
        return(FDCProDepSplitUnConInfo)getObject(index);
    }
    public FDCProDepSplitUnConInfo get(Object key)
    {
        return(FDCProDepSplitUnConInfo)getObject(key);
    }
    public void set(int index, FDCProDepSplitUnConInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepSplitUnConInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepSplitUnConInfo item)
    {
        return super.indexOf(item);
    }
}