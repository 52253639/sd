package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EyeSignhtCollection extends AbstractObjectCollection 
{
    public EyeSignhtCollection()
    {
        super(EyeSignhtInfo.class);
    }
    public boolean add(EyeSignhtInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EyeSignhtCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EyeSignhtInfo item)
    {
        return removeObject(item);
    }
    public EyeSignhtInfo get(int index)
    {
        return(EyeSignhtInfo)getObject(index);
    }
    public EyeSignhtInfo get(Object key)
    {
        return(EyeSignhtInfo)getObject(key);
    }
    public void set(int index, EyeSignhtInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EyeSignhtInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EyeSignhtInfo item)
    {
        return super.indexOf(item);
    }
}