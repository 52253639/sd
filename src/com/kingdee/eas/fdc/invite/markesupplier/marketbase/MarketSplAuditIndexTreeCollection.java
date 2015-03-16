package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSplAuditIndexTreeCollection extends AbstractObjectCollection 
{
    public MarketSplAuditIndexTreeCollection()
    {
        super(MarketSplAuditIndexTreeInfo.class);
    }
    public boolean add(MarketSplAuditIndexTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSplAuditIndexTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSplAuditIndexTreeInfo item)
    {
        return removeObject(item);
    }
    public MarketSplAuditIndexTreeInfo get(int index)
    {
        return(MarketSplAuditIndexTreeInfo)getObject(index);
    }
    public MarketSplAuditIndexTreeInfo get(Object key)
    {
        return(MarketSplAuditIndexTreeInfo)getObject(key);
    }
    public void set(int index, MarketSplAuditIndexTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSplAuditIndexTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSplAuditIndexTreeInfo item)
    {
        return super.indexOf(item);
    }
}