package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteBidEvaluationEntrysCollection extends AbstractObjectCollection 
{
    public InviteBidEvaluationEntrysCollection()
    {
        super(InviteBidEvaluationEntrysInfo.class);
    }
    public boolean add(InviteBidEvaluationEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteBidEvaluationEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteBidEvaluationEntrysInfo item)
    {
        return removeObject(item);
    }
    public InviteBidEvaluationEntrysInfo get(int index)
    {
        return(InviteBidEvaluationEntrysInfo)getObject(index);
    }
    public InviteBidEvaluationEntrysInfo get(Object key)
    {
        return(InviteBidEvaluationEntrysInfo)getObject(key);
    }
    public void set(int index, InviteBidEvaluationEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteBidEvaluationEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteBidEvaluationEntrysInfo item)
    {
        return super.indexOf(item);
    }
}