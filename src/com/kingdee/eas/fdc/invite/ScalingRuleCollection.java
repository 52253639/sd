package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScalingRuleCollection extends AbstractObjectCollection 
{
    public ScalingRuleCollection()
    {
        super(ScalingRuleInfo.class);
    }
    public boolean add(ScalingRuleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScalingRuleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScalingRuleInfo item)
    {
        return removeObject(item);
    }
    public ScalingRuleInfo get(int index)
    {
        return(ScalingRuleInfo)getObject(index);
    }
    public ScalingRuleInfo get(Object key)
    {
        return(ScalingRuleInfo)getObject(key);
    }
    public void set(int index, ScalingRuleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScalingRuleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScalingRuleInfo item)
    {
        return super.indexOf(item);
    }
}