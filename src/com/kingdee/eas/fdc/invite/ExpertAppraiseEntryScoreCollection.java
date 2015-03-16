package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpertAppraiseEntryScoreCollection extends AbstractObjectCollection 
{
    public ExpertAppraiseEntryScoreCollection()
    {
        super(ExpertAppraiseEntryScoreInfo.class);
    }
    public boolean add(ExpertAppraiseEntryScoreInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpertAppraiseEntryScoreCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpertAppraiseEntryScoreInfo item)
    {
        return removeObject(item);
    }
    public ExpertAppraiseEntryScoreInfo get(int index)
    {
        return(ExpertAppraiseEntryScoreInfo)getObject(index);
    }
    public ExpertAppraiseEntryScoreInfo get(Object key)
    {
        return(ExpertAppraiseEntryScoreInfo)getObject(key);
    }
    public void set(int index, ExpertAppraiseEntryScoreInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpertAppraiseEntryScoreInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpertAppraiseEntryScoreInfo item)
    {
        return super.indexOf(item);
    }
}