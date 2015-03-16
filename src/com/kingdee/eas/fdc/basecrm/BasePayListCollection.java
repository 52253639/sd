package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BasePayListCollection extends AbstractObjectCollection 
{
    public BasePayListCollection()
    {
        super(BasePayListInfo.class);
    }
    public boolean add(BasePayListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BasePayListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BasePayListInfo item)
    {
        return removeObject(item);
    }
    public BasePayListInfo get(int index)
    {
        return(BasePayListInfo)getObject(index);
    }
    public BasePayListInfo get(Object key)
    {
        return(BasePayListInfo)getObject(key);
    }
    public void set(int index, BasePayListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BasePayListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BasePayListInfo item)
    {
        return super.indexOf(item);
    }
}