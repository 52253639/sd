package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DecorationStandardCollection extends AbstractObjectCollection 
{
    public DecorationStandardCollection()
    {
        super(DecorationStandardInfo.class);
    }
    public boolean add(DecorationStandardInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DecorationStandardCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DecorationStandardInfo item)
    {
        return removeObject(item);
    }
    public DecorationStandardInfo get(int index)
    {
        return(DecorationStandardInfo)getObject(index);
    }
    public DecorationStandardInfo get(Object key)
    {
        return(DecorationStandardInfo)getObject(key);
    }
    public void set(int index, DecorationStandardInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DecorationStandardInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DecorationStandardInfo item)
    {
        return super.indexOf(item);
    }
}