package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WBSTemplateCollection extends AbstractObjectCollection 
{
    public WBSTemplateCollection()
    {
        super(WBSTemplateInfo.class);
    }
    public boolean add(WBSTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WBSTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WBSTemplateInfo item)
    {
        return removeObject(item);
    }
    public WBSTemplateInfo get(int index)
    {
        return(WBSTemplateInfo)getObject(index);
    }
    public WBSTemplateInfo get(Object key)
    {
        return(WBSTemplateInfo)getObject(key);
    }
    public void set(int index, WBSTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WBSTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WBSTemplateInfo item)
    {
        return super.indexOf(item);
    }
}