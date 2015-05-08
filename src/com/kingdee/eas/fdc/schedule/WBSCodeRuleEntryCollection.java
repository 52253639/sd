package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WBSCodeRuleEntryCollection extends AbstractObjectCollection 
{
    public WBSCodeRuleEntryCollection()
    {
        super(WBSCodeRuleEntryInfo.class);
    }
    public boolean add(WBSCodeRuleEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WBSCodeRuleEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WBSCodeRuleEntryInfo item)
    {
        return removeObject(item);
    }
    public WBSCodeRuleEntryInfo get(int index)
    {
        return(WBSCodeRuleEntryInfo)getObject(index);
    }
    public WBSCodeRuleEntryInfo get(Object key)
    {
        return(WBSCodeRuleEntryInfo)getObject(key);
    }
    public void set(int index, WBSCodeRuleEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WBSCodeRuleEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WBSCodeRuleEntryInfo item)
    {
        return super.indexOf(item);
    }
}