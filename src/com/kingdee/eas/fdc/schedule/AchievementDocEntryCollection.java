package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AchievementDocEntryCollection extends AbstractObjectCollection 
{
    public AchievementDocEntryCollection()
    {
        super(AchievementDocEntryInfo.class);
    }
    public boolean add(AchievementDocEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AchievementDocEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AchievementDocEntryInfo item)
    {
        return removeObject(item);
    }
    public AchievementDocEntryInfo get(int index)
    {
        return(AchievementDocEntryInfo)getObject(index);
    }
    public AchievementDocEntryInfo get(Object key)
    {
        return(AchievementDocEntryInfo)getObject(key);
    }
    public void set(int index, AchievementDocEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AchievementDocEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AchievementDocEntryInfo item)
    {
        return super.indexOf(item);
    }
}