package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ActionEvaluateCollection extends AbstractObjectCollection 
{
    public ActionEvaluateCollection()
    {
        super(ActionEvaluateInfo.class);
    }
    public boolean add(ActionEvaluateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ActionEvaluateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ActionEvaluateInfo item)
    {
        return removeObject(item);
    }
    public ActionEvaluateInfo get(int index)
    {
        return(ActionEvaluateInfo)getObject(index);
    }
    public ActionEvaluateInfo get(Object key)
    {
        return(ActionEvaluateInfo)getObject(key);
    }
    public void set(int index, ActionEvaluateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ActionEvaluateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ActionEvaluateInfo item)
    {
        return super.indexOf(item);
    }
}