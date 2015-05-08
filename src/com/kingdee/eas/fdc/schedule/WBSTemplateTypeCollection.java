package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WBSTemplateTypeCollection extends AbstractObjectCollection 
{
    public WBSTemplateTypeCollection()
    {
        super(WBSTemplateTypeInfo.class);
    }
    public boolean add(WBSTemplateTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WBSTemplateTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WBSTemplateTypeInfo item)
    {
        return removeObject(item);
    }
    public WBSTemplateTypeInfo get(int index)
    {
        return(WBSTemplateTypeInfo)getObject(index);
    }
    public WBSTemplateTypeInfo get(Object key)
    {
        return(WBSTemplateTypeInfo)getObject(key);
    }
    public void set(int index, WBSTemplateTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WBSTemplateTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WBSTemplateTypeInfo item)
    {
        return super.indexOf(item);
    }
}