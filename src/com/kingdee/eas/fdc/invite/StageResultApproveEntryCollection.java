package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StageResultApproveEntryCollection extends AbstractObjectCollection 
{
    public StageResultApproveEntryCollection()
    {
        super(StageResultApproveEntryInfo.class);
    }
    public boolean add(StageResultApproveEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StageResultApproveEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StageResultApproveEntryInfo item)
    {
        return removeObject(item);
    }
    public StageResultApproveEntryInfo get(int index)
    {
        return(StageResultApproveEntryInfo)getObject(index);
    }
    public StageResultApproveEntryInfo get(Object key)
    {
        return(StageResultApproveEntryInfo)getObject(key);
    }
    public void set(int index, StageResultApproveEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StageResultApproveEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StageResultApproveEntryInfo item)
    {
        return super.indexOf(item);
    }
}