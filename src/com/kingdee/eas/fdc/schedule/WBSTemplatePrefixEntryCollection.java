package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WBSTemplatePrefixEntryCollection extends AbstractObjectCollection 
{
    public WBSTemplatePrefixEntryCollection()
    {
        super(WBSTemplatePrefixEntryInfo.class);
    }
    public boolean add(WBSTemplatePrefixEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WBSTemplatePrefixEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WBSTemplatePrefixEntryInfo item)
    {
        return removeObject(item);
    }
    public WBSTemplatePrefixEntryInfo get(int index)
    {
        return(WBSTemplatePrefixEntryInfo)getObject(index);
    }
    public WBSTemplatePrefixEntryInfo get(Object key)
    {
        return(WBSTemplatePrefixEntryInfo)getObject(key);
    }
    public void set(int index, WBSTemplatePrefixEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WBSTemplatePrefixEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WBSTemplatePrefixEntryInfo item)
    {
        return super.indexOf(item);
    }
}