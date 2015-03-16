package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplAreaCollection extends AbstractObjectCollection 
{
    public FDCSplAreaCollection()
    {
        super(FDCSplAreaInfo.class);
    }
    public boolean add(FDCSplAreaInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplAreaCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplAreaInfo item)
    {
        return removeObject(item);
    }
    public FDCSplAreaInfo get(int index)
    {
        return(FDCSplAreaInfo)getObject(index);
    }
    public FDCSplAreaInfo get(Object key)
    {
        return(FDCSplAreaInfo)getObject(key);
    }
    public void set(int index, FDCSplAreaInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplAreaInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplAreaInfo item)
    {
        return super.indexOf(item);
    }
}