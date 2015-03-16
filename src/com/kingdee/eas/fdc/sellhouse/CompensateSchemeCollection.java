package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompensateSchemeCollection extends AbstractObjectCollection 
{
    public CompensateSchemeCollection()
    {
        super(CompensateSchemeInfo.class);
    }
    public boolean add(CompensateSchemeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompensateSchemeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompensateSchemeInfo item)
    {
        return removeObject(item);
    }
    public CompensateSchemeInfo get(int index)
    {
        return(CompensateSchemeInfo)getObject(index);
    }
    public CompensateSchemeInfo get(Object key)
    {
        return(CompensateSchemeInfo)getObject(key);
    }
    public void set(int index, CompensateSchemeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompensateSchemeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompensateSchemeInfo item)
    {
        return super.indexOf(item);
    }
}