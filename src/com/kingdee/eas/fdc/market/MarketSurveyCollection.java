package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSurveyCollection extends AbstractObjectCollection 
{
    public MarketSurveyCollection()
    {
        super(MarketSurveyInfo.class);
    }
    public boolean add(MarketSurveyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSurveyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSurveyInfo item)
    {
        return removeObject(item);
    }
    public MarketSurveyInfo get(int index)
    {
        return(MarketSurveyInfo)getObject(index);
    }
    public MarketSurveyInfo get(Object key)
    {
        return(MarketSurveyInfo)getObject(key);
    }
    public void set(int index, MarketSurveyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSurveyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSurveyInfo item)
    {
        return super.indexOf(item);
    }
}