package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanisphereElementEntryCollection extends AbstractObjectCollection 
{
    public PlanisphereElementEntryCollection()
    {
        super(PlanisphereElementEntryInfo.class);
    }
    public boolean add(PlanisphereElementEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanisphereElementEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanisphereElementEntryInfo item)
    {
        return removeObject(item);
    }
    public PlanisphereElementEntryInfo get(int index)
    {
        return(PlanisphereElementEntryInfo)getObject(index);
    }
    public PlanisphereElementEntryInfo get(Object key)
    {
        return(PlanisphereElementEntryInfo)getObject(key);
    }
    public void set(int index, PlanisphereElementEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanisphereElementEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanisphereElementEntryInfo item)
    {
        return super.indexOf(item);
    }
}