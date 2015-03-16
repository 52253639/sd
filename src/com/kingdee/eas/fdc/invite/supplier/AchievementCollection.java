package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AchievementCollection extends AbstractObjectCollection 
{
    public AchievementCollection()
    {
        super(AchievementInfo.class);
    }
    public boolean add(AchievementInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AchievementCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AchievementInfo item)
    {
        return removeObject(item);
    }
    public AchievementInfo get(int index)
    {
        return(AchievementInfo)getObject(index);
    }
    public AchievementInfo get(Object key)
    {
        return(AchievementInfo)getObject(key);
    }
    public void set(int index, AchievementInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AchievementInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AchievementInfo item)
    {
        return super.indexOf(item);
    }
}