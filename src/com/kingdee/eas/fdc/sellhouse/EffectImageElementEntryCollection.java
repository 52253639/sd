package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EffectImageElementEntryCollection extends AbstractObjectCollection 
{
    public EffectImageElementEntryCollection()
    {
        super(EffectImageElementEntryInfo.class);
    }
    public boolean add(EffectImageElementEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EffectImageElementEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EffectImageElementEntryInfo item)
    {
        return removeObject(item);
    }
    public EffectImageElementEntryInfo get(int index)
    {
        return(EffectImageElementEntryInfo)getObject(index);
    }
    public EffectImageElementEntryInfo get(Object key)
    {
        return(EffectImageElementEntryInfo)getObject(key);
    }
    public void set(int index, EffectImageElementEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EffectImageElementEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EffectImageElementEntryInfo item)
    {
        return super.indexOf(item);
    }
}