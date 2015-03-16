package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TranBusinessOverViewCollection extends AbstractObjectCollection 
{
    public TranBusinessOverViewCollection()
    {
        super(TranBusinessOverViewInfo.class);
    }
    public boolean add(TranBusinessOverViewInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TranBusinessOverViewCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TranBusinessOverViewInfo item)
    {
        return removeObject(item);
    }
    public TranBusinessOverViewInfo get(int index)
    {
        return(TranBusinessOverViewInfo)getObject(index);
    }
    public TranBusinessOverViewInfo get(Object key)
    {
        return(TranBusinessOverViewInfo)getObject(key);
    }
    public void set(int index, TranBusinessOverViewInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TranBusinessOverViewInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TranBusinessOverViewInfo item)
    {
        return super.indexOf(item);
    }
}