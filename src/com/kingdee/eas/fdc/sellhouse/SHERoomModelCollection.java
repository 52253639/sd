package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHERoomModelCollection extends AbstractObjectCollection 
{
    public SHERoomModelCollection()
    {
        super(SHERoomModelInfo.class);
    }
    public boolean add(SHERoomModelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHERoomModelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHERoomModelInfo item)
    {
        return removeObject(item);
    }
    public SHERoomModelInfo get(int index)
    {
        return(SHERoomModelInfo)getObject(index);
    }
    public SHERoomModelInfo get(Object key)
    {
        return(SHERoomModelInfo)getObject(key);
    }
    public void set(int index, SHERoomModelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHERoomModelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHERoomModelInfo item)
    {
        return super.indexOf(item);
    }
}