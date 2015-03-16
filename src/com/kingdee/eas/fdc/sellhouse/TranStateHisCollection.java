package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TranStateHisCollection extends AbstractObjectCollection 
{
    public TranStateHisCollection()
    {
        super(TranStateHisInfo.class);
    }
    public boolean add(TranStateHisInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TranStateHisCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TranStateHisInfo item)
    {
        return removeObject(item);
    }
    public TranStateHisInfo get(int index)
    {
        return(TranStateHisInfo)getObject(index);
    }
    public TranStateHisInfo get(Object key)
    {
        return(TranStateHisInfo)getObject(key);
    }
    public void set(int index, TranStateHisInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TranStateHisInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TranStateHisInfo item)
    {
        return super.indexOf(item);
    }
}