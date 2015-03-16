package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OperationSetupCollection extends AbstractObjectCollection 
{
    public OperationSetupCollection()
    {
        super(OperationSetupInfo.class);
    }
    public boolean add(OperationSetupInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OperationSetupCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OperationSetupInfo item)
    {
        return removeObject(item);
    }
    public OperationSetupInfo get(int index)
    {
        return(OperationSetupInfo)getObject(index);
    }
    public OperationSetupInfo get(Object key)
    {
        return(OperationSetupInfo)getObject(key);
    }
    public void set(int index, OperationSetupInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OperationSetupInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OperationSetupInfo item)
    {
        return super.indexOf(item);
    }
}