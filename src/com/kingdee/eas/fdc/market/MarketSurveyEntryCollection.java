package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSurveyEntryCollection extends AbstractObjectCollection 
{
    public MarketSurveyEntryCollection()
    {
        super(MarketSurveyEntryInfo.class);
    }
    public boolean add(MarketSurveyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSurveyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSurveyEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSurveyEntryInfo get(int index)
    {
        return(MarketSurveyEntryInfo)getObject(index);
    }
    public MarketSurveyEntryInfo get(Object key)
    {
        return(MarketSurveyEntryInfo)getObject(key);
    }
    public void set(int index, MarketSurveyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSurveyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSurveyEntryInfo item)
    {
        return super.indexOf(item);
    }
}