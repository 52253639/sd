package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EffectImageCollection extends AbstractObjectCollection 
{
    public EffectImageCollection()
    {
        super(EffectImageInfo.class);
    }
    public boolean add(EffectImageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EffectImageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EffectImageInfo item)
    {
        return removeObject(item);
    }
    public EffectImageInfo get(int index)
    {
        return(EffectImageInfo)getObject(index);
    }
    public EffectImageInfo get(Object key)
    {
        return(EffectImageInfo)getObject(key);
    }
    public void set(int index, EffectImageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EffectImageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EffectImageInfo item)
    {
        return super.indexOf(item);
    }
}