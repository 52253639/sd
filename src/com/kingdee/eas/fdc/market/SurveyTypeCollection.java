package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SurveyTypeCollection extends AbstractObjectCollection 
{
    public SurveyTypeCollection()
    {
        super(SurveyTypeInfo.class);
    }
    public boolean add(SurveyTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SurveyTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SurveyTypeInfo item)
    {
        return removeObject(item);
    }
    public SurveyTypeInfo get(int index)
    {
        return(SurveyTypeInfo)getObject(index);
    }
    public SurveyTypeInfo get(Object key)
    {
        return(SurveyTypeInfo)getObject(key);
    }
    public void set(int index, SurveyTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SurveyTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SurveyTypeInfo item)
    {
        return super.indexOf(item);
    }
}