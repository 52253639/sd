package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AreaCompensateRevListCollection extends AbstractObjectCollection 
{
    public AreaCompensateRevListCollection()
    {
        super(AreaCompensateRevListInfo.class);
    }
    public boolean add(AreaCompensateRevListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AreaCompensateRevListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AreaCompensateRevListInfo item)
    {
        return removeObject(item);
    }
    public AreaCompensateRevListInfo get(int index)
    {
        return(AreaCompensateRevListInfo)getObject(index);
    }
    public AreaCompensateRevListInfo get(Object key)
    {
        return(AreaCompensateRevListInfo)getObject(key);
    }
    public void set(int index, AreaCompensateRevListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AreaCompensateRevListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AreaCompensateRevListInfo item)
    {
        return super.indexOf(item);
    }
}