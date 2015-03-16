package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCOrgStructureCollection extends AbstractObjectCollection 
{
    public FDCOrgStructureCollection()
    {
        super(FDCOrgStructureInfo.class);
    }
    public boolean add(FDCOrgStructureInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCOrgStructureCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCOrgStructureInfo item)
    {
        return removeObject(item);
    }
    public FDCOrgStructureInfo get(int index)
    {
        return(FDCOrgStructureInfo)getObject(index);
    }
    public FDCOrgStructureInfo get(Object key)
    {
        return(FDCOrgStructureInfo)getObject(key);
    }
    public void set(int index, FDCOrgStructureInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCOrgStructureInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCOrgStructureInfo item)
    {
        return super.indexOf(item);
    }
}