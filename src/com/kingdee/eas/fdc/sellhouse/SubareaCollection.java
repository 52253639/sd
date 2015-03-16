package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SubareaCollection extends AbstractObjectCollection 
{
    public SubareaCollection()
    {
        super(SubareaInfo.class);
    }
    public boolean add(SubareaInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SubareaCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SubareaInfo item)
    {
        return removeObject(item);
    }
    public SubareaInfo get(int index)
    {
        return(SubareaInfo)getObject(index);
    }
    public SubareaInfo get(Object key)
    {
        return(SubareaInfo)getObject(key);
    }
    public void set(int index, SubareaInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SubareaInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SubareaInfo item)
    {
        return super.indexOf(item);
    }
}