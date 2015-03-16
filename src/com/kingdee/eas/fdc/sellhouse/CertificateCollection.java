package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CertificateCollection extends AbstractObjectCollection 
{
    public CertificateCollection()
    {
        super(CertificateInfo.class);
    }
    public boolean add(CertificateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CertificateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CertificateInfo item)
    {
        return removeObject(item);
    }
    public CertificateInfo get(int index)
    {
        return(CertificateInfo)getObject(index);
    }
    public CertificateInfo get(Object key)
    {
        return(CertificateInfo)getObject(key);
    }
    public void set(int index, CertificateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CertificateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CertificateInfo item)
    {
        return super.indexOf(item);
    }
}