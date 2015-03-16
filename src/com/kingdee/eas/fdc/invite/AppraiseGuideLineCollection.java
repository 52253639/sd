package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AppraiseGuideLineCollection extends AbstractObjectCollection 
{
    public AppraiseGuideLineCollection()
    {
        super(AppraiseGuideLineInfo.class);
    }
    public boolean add(AppraiseGuideLineInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AppraiseGuideLineCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AppraiseGuideLineInfo item)
    {
        return removeObject(item);
    }
    public AppraiseGuideLineInfo get(int index)
    {
        return(AppraiseGuideLineInfo)getObject(index);
    }
    public AppraiseGuideLineInfo get(Object key)
    {
        return(AppraiseGuideLineInfo)getObject(key);
    }
    public void set(int index, AppraiseGuideLineInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AppraiseGuideLineInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AppraiseGuideLineInfo item)
    {
        return super.indexOf(item);
    }
}