package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEShareOrgUnitCollection extends AbstractObjectCollection 
{
    public SHEShareOrgUnitCollection()
    {
        super(SHEShareOrgUnitInfo.class);
    }
    public boolean add(SHEShareOrgUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEShareOrgUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEShareOrgUnitInfo item)
    {
        return removeObject(item);
    }
    public SHEShareOrgUnitInfo get(int index)
    {
        return(SHEShareOrgUnitInfo)getObject(index);
    }
    public SHEShareOrgUnitInfo get(Object key)
    {
        return(SHEShareOrgUnitInfo)getObject(key);
    }
    public void set(int index, SHEShareOrgUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEShareOrgUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEShareOrgUnitInfo item)
    {
        return super.indexOf(item);
    }
}