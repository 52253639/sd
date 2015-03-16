package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteBackAnalysisEntrysCollection extends AbstractObjectCollection 
{
    public InviteBackAnalysisEntrysCollection()
    {
        super(InviteBackAnalysisEntrysInfo.class);
    }
    public boolean add(InviteBackAnalysisEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteBackAnalysisEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteBackAnalysisEntrysInfo item)
    {
        return removeObject(item);
    }
    public InviteBackAnalysisEntrysInfo get(int index)
    {
        return(InviteBackAnalysisEntrysInfo)getObject(index);
    }
    public InviteBackAnalysisEntrysInfo get(Object key)
    {
        return(InviteBackAnalysisEntrysInfo)getObject(key);
    }
    public void set(int index, InviteBackAnalysisEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteBackAnalysisEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteBackAnalysisEntrysInfo item)
    {
        return super.indexOf(item);
    }
}