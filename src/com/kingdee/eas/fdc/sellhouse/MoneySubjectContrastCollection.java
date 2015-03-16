package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MoneySubjectContrastCollection extends AbstractObjectCollection 
{
    public MoneySubjectContrastCollection()
    {
        super(MoneySubjectContrastInfo.class);
    }
    public boolean add(MoneySubjectContrastInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MoneySubjectContrastCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MoneySubjectContrastInfo item)
    {
        return removeObject(item);
    }
    public MoneySubjectContrastInfo get(int index)
    {
        return(MoneySubjectContrastInfo)getObject(index);
    }
    public MoneySubjectContrastInfo get(Object key)
    {
        return(MoneySubjectContrastInfo)getObject(key);
    }
    public void set(int index, MoneySubjectContrastInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MoneySubjectContrastInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MoneySubjectContrastInfo item)
    {
        return super.indexOf(item);
    }
}