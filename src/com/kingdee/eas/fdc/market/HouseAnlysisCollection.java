package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HouseAnlysisCollection extends AbstractObjectCollection 
{
    public HouseAnlysisCollection()
    {
        super(HouseAnlysisInfo.class);
    }
    public boolean add(HouseAnlysisInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HouseAnlysisCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HouseAnlysisInfo item)
    {
        return removeObject(item);
    }
    public HouseAnlysisInfo get(int index)
    {
        return(HouseAnlysisInfo)getObject(index);
    }
    public HouseAnlysisInfo get(Object key)
    {
        return(HouseAnlysisInfo)getObject(key);
    }
    public void set(int index, HouseAnlysisInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HouseAnlysisInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HouseAnlysisInfo item)
    {
        return super.indexOf(item);
    }
}