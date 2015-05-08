package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AchievementProfessionCollection extends AbstractObjectCollection 
{
    public AchievementProfessionCollection()
    {
        super(AchievementProfessionInfo.class);
    }
    public boolean add(AchievementProfessionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AchievementProfessionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AchievementProfessionInfo item)
    {
        return removeObject(item);
    }
    public AchievementProfessionInfo get(int index)
    {
        return(AchievementProfessionInfo)getObject(index);
    }
    public AchievementProfessionInfo get(Object key)
    {
        return(AchievementProfessionInfo)getObject(key);
    }
    public void set(int index, AchievementProfessionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AchievementProfessionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AchievementProfessionInfo item)
    {
        return super.indexOf(item);
    }
}