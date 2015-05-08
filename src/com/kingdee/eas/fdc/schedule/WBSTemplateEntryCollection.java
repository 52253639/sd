package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WBSTemplateEntryCollection extends AbstractObjectCollection 
{
    public WBSTemplateEntryCollection()
    {
        super(WBSTemplateEntryInfo.class);
    }
    public boolean add(WBSTemplateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WBSTemplateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WBSTemplateEntryInfo item)
    {
        return removeObject(item);
    }
    public WBSTemplateEntryInfo get(int index)
    {
        return(WBSTemplateEntryInfo)getObject(index);
    }
    public WBSTemplateEntryInfo get(Object key)
    {
        return(WBSTemplateEntryInfo)getObject(key);
    }
    public void set(int index, WBSTemplateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WBSTemplateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WBSTemplateEntryInfo item)
    {
        return super.indexOf(item);
    }
}