package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AppraiseGuideLineTypeCollection extends AbstractObjectCollection 
{
    public AppraiseGuideLineTypeCollection()
    {
        super(AppraiseGuideLineTypeInfo.class);
    }
    public boolean add(AppraiseGuideLineTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AppraiseGuideLineTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AppraiseGuideLineTypeInfo item)
    {
        return removeObject(item);
    }
    public AppraiseGuideLineTypeInfo get(int index)
    {
        return(AppraiseGuideLineTypeInfo)getObject(index);
    }
    public AppraiseGuideLineTypeInfo get(Object key)
    {
        return(AppraiseGuideLineTypeInfo)getObject(key);
    }
    public void set(int index, AppraiseGuideLineTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AppraiseGuideLineTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AppraiseGuideLineTypeInfo item)
    {
        return super.indexOf(item);
    }
}