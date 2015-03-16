package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BasePriceControlCollection extends AbstractObjectCollection 
{
    public BasePriceControlCollection()
    {
        super(BasePriceControlInfo.class);
    }
    public boolean add(BasePriceControlInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BasePriceControlCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BasePriceControlInfo item)
    {
        return removeObject(item);
    }
    public BasePriceControlInfo get(int index)
    {
        return(BasePriceControlInfo)getObject(index);
    }
    public BasePriceControlInfo get(Object key)
    {
        return(BasePriceControlInfo)getObject(key);
    }
    public void set(int index, BasePriceControlInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BasePriceControlInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BasePriceControlInfo item)
    {
        return super.indexOf(item);
    }
}