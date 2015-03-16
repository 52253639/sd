package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EnterpriseSchemeCollection extends AbstractObjectCollection 
{
    public EnterpriseSchemeCollection()
    {
        super(EnterpriseSchemeInfo.class);
    }
    public boolean add(EnterpriseSchemeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EnterpriseSchemeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EnterpriseSchemeInfo item)
    {
        return removeObject(item);
    }
    public EnterpriseSchemeInfo get(int index)
    {
        return(EnterpriseSchemeInfo)getObject(index);
    }
    public EnterpriseSchemeInfo get(Object key)
    {
        return(EnterpriseSchemeInfo)getObject(key);
    }
    public void set(int index, EnterpriseSchemeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EnterpriseSchemeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EnterpriseSchemeInfo item)
    {
        return super.indexOf(item);
    }
}