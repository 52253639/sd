package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSplAuditIndexCollection extends AbstractObjectCollection 
{
    public MarketSplAuditIndexCollection()
    {
        super(MarketSplAuditIndexInfo.class);
    }
    public boolean add(MarketSplAuditIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSplAuditIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSplAuditIndexInfo item)
    {
        return removeObject(item);
    }
    public MarketSplAuditIndexInfo get(int index)
    {
        return(MarketSplAuditIndexInfo)getObject(index);
    }
    public MarketSplAuditIndexInfo get(Object key)
    {
        return(MarketSplAuditIndexInfo)getObject(key);
    }
    public void set(int index, MarketSplAuditIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSplAuditIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSplAuditIndexInfo item)
    {
        return super.indexOf(item);
    }
}