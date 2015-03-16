package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HopePitchCollection extends AbstractObjectCollection 
{
    public HopePitchCollection()
    {
        super(HopePitchInfo.class);
    }
    public boolean add(HopePitchInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HopePitchCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HopePitchInfo item)
    {
        return removeObject(item);
    }
    public HopePitchInfo get(int index)
    {
        return(HopePitchInfo)getObject(index);
    }
    public HopePitchInfo get(Object key)
    {
        return(HopePitchInfo)getObject(key);
    }
    public void set(int index, HopePitchInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HopePitchInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HopePitchInfo item)
    {
        return super.indexOf(item);
    }
}