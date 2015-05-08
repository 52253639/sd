package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AchievementTypeCollection extends AbstractObjectCollection 
{
    public AchievementTypeCollection()
    {
        super(AchievementTypeInfo.class);
    }
    public boolean add(AchievementTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AchievementTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AchievementTypeInfo item)
    {
        return removeObject(item);
    }
    public AchievementTypeInfo get(int index)
    {
        return(AchievementTypeInfo)getObject(index);
    }
    public AchievementTypeInfo get(Object key)
    {
        return(AchievementTypeInfo)getObject(key);
    }
    public void set(int index, AchievementTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AchievementTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AchievementTypeInfo item)
    {
        return super.indexOf(item);
    }
}