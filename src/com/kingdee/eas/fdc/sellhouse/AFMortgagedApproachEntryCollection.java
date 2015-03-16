package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AFMortgagedApproachEntryCollection extends AbstractObjectCollection 
{
    public AFMortgagedApproachEntryCollection()
    {
        super(AFMortgagedApproachEntryInfo.class);
    }
    public boolean add(AFMortgagedApproachEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AFMortgagedApproachEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AFMortgagedApproachEntryInfo item)
    {
        return removeObject(item);
    }
    public AFMortgagedApproachEntryInfo get(int index)
    {
        return(AFMortgagedApproachEntryInfo)getObject(index);
    }
    public AFMortgagedApproachEntryInfo get(Object key)
    {
        return(AFMortgagedApproachEntryInfo)getObject(key);
    }
    public void set(int index, AFMortgagedApproachEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AFMortgagedApproachEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AFMortgagedApproachEntryInfo item)
    {
        return super.indexOf(item);
    }
}