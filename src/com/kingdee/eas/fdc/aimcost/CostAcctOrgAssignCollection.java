package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostAcctOrgAssignCollection extends AbstractObjectCollection 
{
    public CostAcctOrgAssignCollection()
    {
        super(CostAcctOrgAssignInfo.class);
    }
    public boolean add(CostAcctOrgAssignInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostAcctOrgAssignCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostAcctOrgAssignInfo item)
    {
        return removeObject(item);
    }
    public CostAcctOrgAssignInfo get(int index)
    {
        return(CostAcctOrgAssignInfo)getObject(index);
    }
    public CostAcctOrgAssignInfo get(Object key)
    {
        return(CostAcctOrgAssignInfo)getObject(key);
    }
    public void set(int index, CostAcctOrgAssignInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostAcctOrgAssignInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostAcctOrgAssignInfo item)
    {
        return super.indexOf(item);
    }
}