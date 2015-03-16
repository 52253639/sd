package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeployTypeCollection extends AbstractObjectCollection 
{
    public DeployTypeCollection()
    {
        super(DeployTypeInfo.class);
    }
    public boolean add(DeployTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeployTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeployTypeInfo item)
    {
        return removeObject(item);
    }
    public DeployTypeInfo get(int index)
    {
        return(DeployTypeInfo)getObject(index);
    }
    public DeployTypeInfo get(Object key)
    {
        return(DeployTypeInfo)getObject(key);
    }
    public void set(int index, DeployTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeployTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeployTypeInfo item)
    {
        return super.indexOf(item);
    }
}