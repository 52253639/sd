package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BatchManageCollection extends AbstractObjectCollection 
{
    public BatchManageCollection()
    {
        super(BatchManageInfo.class);
    }
    public boolean add(BatchManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BatchManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BatchManageInfo item)
    {
        return removeObject(item);
    }
    public BatchManageInfo get(int index)
    {
        return(BatchManageInfo)getObject(index);
    }
    public BatchManageInfo get(Object key)
    {
        return(BatchManageInfo)getObject(key);
    }
    public void set(int index, BatchManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BatchManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BatchManageInfo item)
    {
        return super.indexOf(item);
    }
}