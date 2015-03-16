package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SubstituteTransfOutCollection extends AbstractObjectCollection 
{
    public SubstituteTransfOutCollection()
    {
        super(SubstituteTransfOutInfo.class);
    }
    public boolean add(SubstituteTransfOutInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SubstituteTransfOutCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SubstituteTransfOutInfo item)
    {
        return removeObject(item);
    }
    public SubstituteTransfOutInfo get(int index)
    {
        return(SubstituteTransfOutInfo)getObject(index);
    }
    public SubstituteTransfOutInfo get(Object key)
    {
        return(SubstituteTransfOutInfo)getObject(key);
    }
    public void set(int index, SubstituteTransfOutInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SubstituteTransfOutInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SubstituteTransfOutInfo item)
    {
        return super.indexOf(item);
    }
}