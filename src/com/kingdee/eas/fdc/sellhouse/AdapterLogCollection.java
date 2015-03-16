package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AdapterLogCollection extends AbstractObjectCollection 
{
    public AdapterLogCollection()
    {
        super(AdapterLogInfo.class);
    }
    public boolean add(AdapterLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AdapterLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AdapterLogInfo item)
    {
        return removeObject(item);
    }
    public AdapterLogInfo get(int index)
    {
        return(AdapterLogInfo)getObject(index);
    }
    public AdapterLogInfo get(Object key)
    {
        return(AdapterLogInfo)getObject(key);
    }
    public void set(int index, AdapterLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AdapterLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AdapterLogInfo item)
    {
        return super.indexOf(item);
    }
}