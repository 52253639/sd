package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StageResultApproveCollection extends AbstractObjectCollection 
{
    public StageResultApproveCollection()
    {
        super(StageResultApproveInfo.class);
    }
    public boolean add(StageResultApproveInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StageResultApproveCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StageResultApproveInfo item)
    {
        return removeObject(item);
    }
    public StageResultApproveInfo get(int index)
    {
        return(StageResultApproveInfo)getObject(index);
    }
    public StageResultApproveInfo get(Object key)
    {
        return(StageResultApproveInfo)getObject(key);
    }
    public void set(int index, StageResultApproveInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StageResultApproveInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StageResultApproveInfo item)
    {
        return super.indexOf(item);
    }
}