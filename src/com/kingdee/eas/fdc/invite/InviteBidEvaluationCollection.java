package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteBidEvaluationCollection extends AbstractObjectCollection 
{
    public InviteBidEvaluationCollection()
    {
        super(InviteBidEvaluationInfo.class);
    }
    public boolean add(InviteBidEvaluationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteBidEvaluationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteBidEvaluationInfo item)
    {
        return removeObject(item);
    }
    public InviteBidEvaluationInfo get(int index)
    {
        return(InviteBidEvaluationInfo)getObject(index);
    }
    public InviteBidEvaluationInfo get(Object key)
    {
        return(InviteBidEvaluationInfo)getObject(key);
    }
    public void set(int index, InviteBidEvaluationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteBidEvaluationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteBidEvaluationInfo item)
    {
        return super.indexOf(item);
    }
}