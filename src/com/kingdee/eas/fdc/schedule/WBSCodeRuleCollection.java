package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WBSCodeRuleCollection extends AbstractObjectCollection 
{
    public WBSCodeRuleCollection()
    {
        super(WBSCodeRuleInfo.class);
    }
    public boolean add(WBSCodeRuleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WBSCodeRuleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WBSCodeRuleInfo item)
    {
        return removeObject(item);
    }
    public WBSCodeRuleInfo get(int index)
    {
        return(WBSCodeRuleInfo)getObject(index);
    }
    public WBSCodeRuleInfo get(Object key)
    {
        return(WBSCodeRuleInfo)getObject(key);
    }
    public void set(int index, WBSCodeRuleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WBSCodeRuleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WBSCodeRuleInfo item)
    {
        return super.indexOf(item);
    }
}