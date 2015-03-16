package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShePayTypeHisCollection extends AbstractObjectCollection 
{
    public ShePayTypeHisCollection()
    {
        super(ShePayTypeHisInfo.class);
    }
    public boolean add(ShePayTypeHisInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShePayTypeHisCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShePayTypeHisInfo item)
    {
        return removeObject(item);
    }
    public ShePayTypeHisInfo get(int index)
    {
        return(ShePayTypeHisInfo)getObject(index);
    }
    public ShePayTypeHisInfo get(Object key)
    {
        return(ShePayTypeHisInfo)getObject(key);
    }
    public void set(int index, ShePayTypeHisInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShePayTypeHisInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShePayTypeHisInfo item)
    {
        return super.indexOf(item);
    }
}