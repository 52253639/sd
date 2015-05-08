package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AchievementStageCollection extends AbstractObjectCollection 
{
    public AchievementStageCollection()
    {
        super(AchievementStageInfo.class);
    }
    public boolean add(AchievementStageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AchievementStageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AchievementStageInfo item)
    {
        return removeObject(item);
    }
    public AchievementStageInfo get(int index)
    {
        return(AchievementStageInfo)getObject(index);
    }
    public AchievementStageInfo get(Object key)
    {
        return(AchievementStageInfo)getObject(key);
    }
    public void set(int index, AchievementStageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AchievementStageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AchievementStageInfo item)
    {
        return super.indexOf(item);
    }
}