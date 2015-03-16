package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialSampleCollection extends AbstractObjectCollection 
{
    public MaterialSampleCollection()
    {
        super(MaterialSampleInfo.class);
    }
    public boolean add(MaterialSampleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialSampleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialSampleInfo item)
    {
        return removeObject(item);
    }
    public MaterialSampleInfo get(int index)
    {
        return(MaterialSampleInfo)getObject(index);
    }
    public MaterialSampleInfo get(Object key)
    {
        return(MaterialSampleInfo)getObject(key);
    }
    public void set(int index, MaterialSampleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialSampleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialSampleInfo item)
    {
        return super.indexOf(item);
    }
}