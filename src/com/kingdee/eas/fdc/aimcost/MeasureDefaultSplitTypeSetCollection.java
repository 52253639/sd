package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureDefaultSplitTypeSetCollection extends AbstractObjectCollection 
{
    public MeasureDefaultSplitTypeSetCollection()
    {
        super(MeasureDefaultSplitTypeSetInfo.class);
    }
    public boolean add(MeasureDefaultSplitTypeSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureDefaultSplitTypeSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureDefaultSplitTypeSetInfo item)
    {
        return removeObject(item);
    }
    public MeasureDefaultSplitTypeSetInfo get(int index)
    {
        return(MeasureDefaultSplitTypeSetInfo)getObject(index);
    }
    public MeasureDefaultSplitTypeSetInfo get(Object key)
    {
        return(MeasureDefaultSplitTypeSetInfo)getObject(key);
    }
    public void set(int index, MeasureDefaultSplitTypeSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureDefaultSplitTypeSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureDefaultSplitTypeSetInfo item)
    {
        return super.indexOf(item);
    }
}