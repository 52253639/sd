package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteBackAnalysisCollection extends AbstractObjectCollection 
{
    public InviteBackAnalysisCollection()
    {
        super(InviteBackAnalysisInfo.class);
    }
    public boolean add(InviteBackAnalysisInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteBackAnalysisCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteBackAnalysisInfo item)
    {
        return removeObject(item);
    }
    public InviteBackAnalysisInfo get(int index)
    {
        return(InviteBackAnalysisInfo)getObject(index);
    }
    public InviteBackAnalysisInfo get(Object key)
    {
        return(InviteBackAnalysisInfo)getObject(key);
    }
    public void set(int index, InviteBackAnalysisInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteBackAnalysisInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteBackAnalysisInfo item)
    {
        return super.indexOf(item);
    }
}