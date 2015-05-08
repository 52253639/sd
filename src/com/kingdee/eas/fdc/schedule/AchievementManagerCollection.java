package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AchievementManagerCollection extends AbstractObjectCollection 
{
    public AchievementManagerCollection()
    {
        super(AchievementManagerInfo.class);
    }
    public boolean add(AchievementManagerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AchievementManagerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AchievementManagerInfo item)
    {
        return removeObject(item);
    }
    public AchievementManagerInfo get(int index)
    {
        return(AchievementManagerInfo)getObject(index);
    }
    public AchievementManagerInfo get(Object key)
    {
        return(AchievementManagerInfo)getObject(key);
    }
    public void set(int index, AchievementManagerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AchievementManagerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AchievementManagerInfo item)
    {
        return super.indexOf(item);
    }
}