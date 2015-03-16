package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpertAppraiseResultCollection extends AbstractObjectCollection 
{
    public ExpertAppraiseResultCollection()
    {
        super(ExpertAppraiseResultInfo.class);
    }
    public boolean add(ExpertAppraiseResultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpertAppraiseResultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpertAppraiseResultInfo item)
    {
        return removeObject(item);
    }
    public ExpertAppraiseResultInfo get(int index)
    {
        return(ExpertAppraiseResultInfo)getObject(index);
    }
    public ExpertAppraiseResultInfo get(Object key)
    {
        return(ExpertAppraiseResultInfo)getObject(key);
    }
    public void set(int index, ExpertAppraiseResultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpertAppraiseResultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpertAppraiseResultInfo item)
    {
        return super.indexOf(item);
    }
}