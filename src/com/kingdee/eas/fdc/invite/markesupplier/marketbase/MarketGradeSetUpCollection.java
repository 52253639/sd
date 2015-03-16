package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketGradeSetUpCollection extends AbstractObjectCollection 
{
    public MarketGradeSetUpCollection()
    {
        super(MarketGradeSetUpInfo.class);
    }
    public boolean add(MarketGradeSetUpInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketGradeSetUpCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketGradeSetUpInfo item)
    {
        return removeObject(item);
    }
    public MarketGradeSetUpInfo get(int index)
    {
        return(MarketGradeSetUpInfo)getObject(index);
    }
    public MarketGradeSetUpInfo get(Object key)
    {
        return(MarketGradeSetUpInfo)getObject(key);
    }
    public void set(int index, MarketGradeSetUpInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketGradeSetUpInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketGradeSetUpInfo item)
    {
        return super.indexOf(item);
    }
}