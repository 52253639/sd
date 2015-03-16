package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PossessionStandardCollection extends AbstractObjectCollection 
{
    public PossessionStandardCollection()
    {
        super(PossessionStandardInfo.class);
    }
    public boolean add(PossessionStandardInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PossessionStandardCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PossessionStandardInfo item)
    {
        return removeObject(item);
    }
    public PossessionStandardInfo get(int index)
    {
        return(PossessionStandardInfo)getObject(index);
    }
    public PossessionStandardInfo get(Object key)
    {
        return(PossessionStandardInfo)getObject(key);
    }
    public void set(int index, PossessionStandardInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PossessionStandardInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PossessionStandardInfo item)
    {
        return super.indexOf(item);
    }
}