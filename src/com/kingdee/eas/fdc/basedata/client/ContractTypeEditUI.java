/*jadclipse*/package com.kingdee.eas.fdc.basedata.client;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.PayContentTypeInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import java.awt.event.*;
import java.util.Map;
import org.apache.log4j.Logger;
public class ContractTypeEditUI extends AbstractContractTypeEditUI
{
            public ContractTypeEditUI()
                throws Exception
            {


































/*  66*/        parentNumber = null;

/*  68*/        strTemp = null;
            }
            public SelectorItemCollection getSelectors()
            {









/*  81*/        SelectorItemCollection sic = new SelectorItemCollection();
/*  82*/        sic.add(new SelectorItemInfo("*"));
/*  83*/        sic.add(new SelectorItemInfo("longNumber"));
/*  84*/        sic.add(new SelectorItemInfo("isEnabled"));
/*  85*/        sic.add(new SelectorItemInfo("description"));
/*  86*/        sic.add(new SelectorItemInfo("name"));
/*  87*/        sic.add(new SelectorItemInfo("isCost"));
/*  88*/        sic.add(new SelectorItemInfo("number"));
/*  89*/        sic.add(new SelectorItemInfo("parent.*"));
/*  90*/        sic.add(new SelectorItemInfo("CU.id"));
/*  91*/        sic.add(new SelectorItemInfo("payScale"));
/*  92*/        sic.add(new SelectorItemInfo("dutyOrgUnit.*"));
/*  93*/        sic.add(new SelectorItemInfo("entry.*"));
/*  94*/        sic.add(new SelectorItemInfo("entry.payContentType.*"));
/*  95*/        sic.add(new SelectorItemInfo("contractWFTypeEntry.*"));
/*  96*/        sic.add(new SelectorItemInfo("contractWFTypeEntry.contractWFType.*"));
/*  97*/        sic.add(new SelectorItemInfo("inviteTypeEntry.*"));
/*  98*/        sic.add(new SelectorItemInfo("inviteTypeEntry.inviteType.*"));

/* 100*/        return sic;
            }
            public void storeFields()
            {



/* 107*/        editData.getEntry().clear();
/* 108*/        if(prmtEntry.getValue() != null)
                {/* 109*/            Object value[] = (Object[])prmtEntry.getValue();
/* 110*/            for(int i = 0; i < value.length; i++)
/* 111*/                if(value[i] != null && (value[i] instanceof PayContentTypeInfo))
                        {/* 112*/                    PayContentTypeEntryInfo entry = new PayContentTypeEntryInfo();
/* 113*/                    entry.setPayContentType((PayContentTypeInfo)value[i]);
/* 114*/                    editData.getEntry().add(entry);
                        }
                }

/* 118*/        editData.getContractWFTypeEntry().clear();
/* 119*/        if(prmtContractWFTypeEntry.getValue() != null)
                {/* 120*/            Object value[] = (Object[])prmtContractWFTypeEntry.getValue();
/* 121*/            for(int i = 0; i < value.length; i++)
/* 122*/                if(value[i] != null && (value[i] instanceof ContractWFTypeInfo))
                        {/* 123*/                    ContractWFEntryInfo entry = new ContractWFEntryInfo();
/* 124*/                    entry.setContractWFType((ContractWFTypeInfo)value[i]);
/* 125*/                    editData.getContractWFTypeEntry().add(entry);
                        }
                }


/* 130*/        editData.getInviteTypeEntry().clear();
/* 131*/        if(prmtInviteType.getValue() != null)
                {/* 132*/            Object value[] = (Object[])prmtInviteType.getValue();
/* 133*/            for(int i = 0; i < value.length; i++)
/* 134*/                if(value[i] != null && (value[i] instanceof InviteTypeInfo))
                        {/* 135*/                    ContractInviteTypeEntryInfo entry = new ContractInviteTypeEntryInfo();
/* 136*/                    entry.setInviteType((InviteTypeInfo)value[i]);
/* 137*/                    editData.getInviteTypeEntry().add(entry);
                        }
                }

/* 141*/        super.storeFields();
            }
            protected IObjectValue createNewData()
            {
/* 145*/        ContractTypeInfo contractTypeInfo = new ContractTypeInfo();

/* 147*/        contractTypeInfo.setIsCost(true);
/* 148*/        return contractTypeInfo;
            }
            protected ICoreBase getBizInterface()
                throws Exception
            {/* 152*/        return ContractTypeFactory.getRemoteInstance();
            }
            public void loadFields()
            {



/* 159*/        super.loadFields();
/* 160*/        parentInfo = (ContractTypeInfo)getUIContext().get("ParentNode");
/* 161*/        setDataObject(editData);
/* 162*/        if(getUIContext().get("ParentNode") != null)
                {/* 163*/            strTemp = parentInfo.getLongNumber();
/* 164*/            strTemp = strTemp.replace('!', '.');
/* 165*/            parentNumber = strTemp;
/* 166*/            if("ADDNEW".equals(getOprtState()))
/* 167*/                txtLongNumber.setText((new StringBuilder(String.valueOf(strTemp))).append(".").toString());
/* 168*/            else/* 168*/            if("EDIT".equals(getOprtState()))
                    {/* 169*/                strTemp = editData.getLongNumber();
/* 170*/                strTemp = strTemp.replace('!', '.');

/* 172*/                txtLongNumber.setText(strTemp);
/* 173*/                if(editData.isIsEnabled())
                        {/* 174*/                    btnCancel.setVisible(true);
/* 175*/                    btnCancel.setEnabled(true);
/* 176*/                    btnCancelCancel.setVisible(false);
                        } else
                        {
/* 179*/                    btnCancel.setVisible(false);
/* 180*/                    btnCancelCancel.setVisible(true);
/* 181*/                    btnCancelCancel.setEnabled(true);
                        }
                    } else/* 183*/            if("VIEW".equals(getOprtState()))
                    {/* 184*/                strTemp = editData.getLongNumber();
/* 185*/                strTemp = strTemp.replace('!', '.');

/* 187*/                txtLongNumber.setText(strTemp);
/* 188*/                if(editData.isIsEnabled())
                        {/* 189*/                    btnCancel.setVisible(true);
/* 190*/                    btnCancel.setEnabled(true);
/* 191*/                    btnCancelCancel.setVisible(false);
                        } else
                        {
/* 194*/                    btnCancel.setVisible(false);
/* 195*/                    btnCancelCancel.setVisible(true);
/* 196*/                    btnCancelCancel.setEnabled(true);
                        }
                    }
                } else

/* 201*/        if(!"ADDNEW".equals(getOprtState()))

/* 203*/            if("EDIT".equals(getOprtState()))
                    {/* 204*/                strTemp = editData.getLongNumber();
/* 205*/                strTemp = strTemp.replace('!', '.');
/* 206*/                if(strTemp.lastIndexOf(".") >= 0)
/* 207*/                    parentNumber = strTemp.substring(0, strTemp.lastIndexOf("."));

/* 209*/                txtLongNumber.setText(strTemp);
/* 210*/                if(editData.isIsEnabled())
                        {/* 211*/                    btnCancel.setVisible(true);
/* 212*/                    btnCancel.setEnabled(true);
/* 213*/                    btnCancelCancel.setVisible(false);
                        } else
                        {
/* 216*/                    btnCancel.setVisible(false);
/* 217*/                    btnCancelCancel.setVisible(true);
/* 218*/                    btnCancelCancel.setEnabled(true);
                        }
/* 220*/                if("11111111-1111-1111-1111-111111111111CCE7AED4".equals(editData.getCU().getId().toString()))
/* 221*/                    btnRemove.setEnabled(false);
                    } else
/* 223*/            if("VIEW".equals(getOprtState()))
                    {/* 224*/                strTemp = editData.getLongNumber();
/* 225*/                strTemp = strTemp.replace('!', '.');
/* 226*/                if(strTemp.lastIndexOf(".") >= 0)
/* 227*/                    parentNumber = strTemp.substring(0, strTemp.lastIndexOf("."));

/* 229*/                txtLongNumber.setText(strTemp);
/* 230*/                if(editData.isIsEnabled())
                        {/* 231*/                    btnCancel.setVisible(true);
/* 232*/                    btnCancel.setEnabled(true);
/* 233*/                    btnCancelCancel.setVisible(false);
                        } else
                        {
/* 236*/                    btnCancel.setVisible(false);
/* 237*/                    btnCancelCancel.setVisible(true);
/* 238*/                    btnCancelCancel.setEnabled(true);
                        }
                    }


/* 243*/        if("VIEW".equals(getOprtState()))
/* 244*/            chkIsCost.setEnabled(false);

/* 246*/        Object value[] = new Object[editData.getEntry().size()];
/* 247*/        for(int i = 0; i < editData.getEntry().size(); i++)
/* 248*/            value[i] = editData.getEntry().get(i).getPayContentType();


/* 251*/        java.util.EventListener listeners[] = prmtEntry.getListeners(DataChangeListener.class);

/* 253*/        for(int i = 0; i < listeners.length; i++)
/* 254*/            prmtEntry.removeDataChangeListener((DataChangeListener)listeners[i]);

/* 256*/        prmtEntry.setValue(((Object) (value)));
/* 257*/        if(listeners != null && listeners.length > 0)
                {/* 258*/            for(int i = 0; i < listeners.length; i++)
/* 259*/                prmtEntry.addDataChangeListener((DataChangeListener)listeners[i]);
                }


/* 263*/        Object wfvalue[] = new Object[editData.getContractWFTypeEntry().size()];
/* 264*/        for(int i = 0; i < editData.getContractWFTypeEntry().size(); i++)
/* 265*/            wfvalue[i] = editData.getContractWFTypeEntry().get(i).getContractWFType();


/* 268*/        java.util.EventListener wflisteners[] = prmtContractWFTypeEntry.getListeners(DataChangeListener.class);

/* 270*/        for(int i = 0; i < listeners.length; i++)
/* 271*/            prmtContractWFTypeEntry.removeDataChangeListener((DataChangeListener)wflisteners[i]);

/* 273*/        prmtContractWFTypeEntry.setValue(((Object) (wfvalue)));
/* 274*/        if(wflisteners != null && wflisteners.length > 0)
                {/* 275*/            for(int i = 0; i < wflisteners.length; i++)
/* 276*/                prmtContractWFTypeEntry.addDataChangeListener((DataChangeListener)wflisteners[i]);
                }



/* 281*/        Object invitevalue[] = new Object[editData.getInviteTypeEntry().size()];
/* 282*/        for(int i = 0; i < editData.getInviteTypeEntry().size(); i++)
/* 283*/            invitevalue[i] = editData.getInviteTypeEntry().get(i).getInviteType();

/* 285*/        prmtInviteType.setValue(((Object) (invitevalue)));
            }
            protected void verifyInput(ActionEvent e)
                throws Exception
            {



/* 293*/        String longNumber = txtLongNumber.getText().trim();
/* 294*/        if(longNumber == null || longNumber.trim().length() < 1 || longNumber.lastIndexOf(".") + 1 == longNumber.length() || longNumber.indexOf(".") == 0 || longNumber.lastIndexOf("!") + 1 == longNumber.length() || longNumber.indexOf("!") == 0)
                {
/* 296*/            txtLongNumber.requestFocus(true);
/* 297*/            throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
                }
/* 299*/        if(getOprtState().equals(OprtState.ADDNEW))
                {/* 300*/            FDCBaseTypeValidator.validate(((ContractTypeListUI)getUIContext().get("Owner")).getMainTable(), txtLongNumber, bizName, getSelectBOID());

/* 302*/            if(parentNumber != null && (longNumber.equals((new StringBuilder(String.valueOf(parentNumber))).append(".").toString()) || longNumber.length() < parentNumber.length() + 1))
                    {
/* 304*/                txtLongNumber.requestFocus(true);
/* 305*/                throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_2);
                    }
                }
/* 308*/        if(parentNumber != null && (!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('.') >= 0 || !longNumber.substring(0, parentNumber.length()).equals(parentNumber)))
                {

/* 311*/            txtLongNumber.requestFocus(true);
/* 312*/            throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
                }


/* 316*/        if(parentNumber != null && (!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('!') >= 0 || !longNumber.substring(0, parentNumber.length()).equals(parentNumber)))
                {

/* 319*/            txtLongNumber.requestFocus(true);
/* 320*/            throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
                }
/* 322*/        editData.setNumber(longNumber.substring(longNumber.lastIndexOf(".") + 1, longNumber.length()));

/* 324*/        longNumber = longNumber.replace('.', '!');
/* 325*/        editData.setLongNumber(longNumber);


/* 328*/        boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, editData, "name");
/* 329*/        if(flag)
                {/* 330*/            bizName.requestFocus(true);
/* 331*/            throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
                } else
                {
/* 334*/            return;
                }
            }
            private void setTitle()
            {
/* <-MISALIGNED-> */ /* 337*/        FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ContractType"));
            }
            public void onLoad()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 341*/        super.onLoad();
/* <-MISALIGNED-> */ /* 342*/        setTitle();
/* <-MISALIGNED-> */ /* 343*/        setBtnStatus();
/* <-MISALIGNED-> */ /* 344*/        AdminF7 adminF7 = new AdminF7();/* 347*/        bizDutyOrgUnit.setSelector(adminF7);


/* 350*/        FDCClientUtils.setRespDeptF7(bizDutyOrgUnit, this);

/* 352*/        kdftxtPayScale.setHorizontalAlignment(4);
/* 353*/        kdftxtPayScale.setPrecision(2);
/* 354*/        kdftxtPayScale.setSupportedEmpty(true);
/* 355*/        kdftxtPayScale.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
/* 356*/        kdftxtPayScale.setMaximumValue(FDCHelper.MAX_DECIMAL);
/* 357*/        txtStampTaxRate.setHorizontalAlignment(4);
/* 358*/        txtStampTaxRate.setSupportedEmpty(true);
/* 359*/        FDCClientHelper.setValueScopeForPercentCtrl(kdftxtPayScale);
/* 360*/        FDCClientHelper.setValueScopeForPercentCtrl(txtStampTaxRate);
/* 361*/        if(parentInfo != null && parentInfo.isIsEnabled())
/* 362*/            chkIsEnabled.setSelected(true);




txtLongNumber.addKeyListener(new KeyListener() {
	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
		ContractTypeInfo parent = (ContractTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		if (/*STATUS_VIEW.equals(getOprtState()) ||*/ parent == null)
			return;
		String longNumber = parent.getLongNumber().replace('!', '.') + '.';
		if (!txtLongNumber.getText().startsWith(longNumber)) {
			txtLongNumber.setText(longNumber);
			txtLongNumber.setSelectionStart(longNumber.length());
		}
	}
	public void keyTyped(KeyEvent e) {}
});

/* <-MISALIGNED-> */ /* 383*/        EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 384*/        FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 385*/        filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
/* <-MISALIGNED-> */ /* 386*/        view.setFilter(filter);
/* <-MISALIGNED-> */ /* 387*/        prmtEntry.setEntityViewInfo(view);
/* <-MISALIGNED-> */ /* 388*/        prmtContractWFTypeEntry.setEntityViewInfo(view);
            }
            private void setBtnStatus()
            {
/* <-MISALIGNED-> */ /* 392*/        if("ADDNEW".equals(getOprtState()))
                {
/* <-MISALIGNED-> */ /* 393*/            btnCancelCancel.setVisible(false);
/* <-MISALIGNED-> */ /* 394*/            btnCancel.setVisible(false);
                } else
/* <-MISALIGNED-> */ /* 395*/        if("EDIT".equals(getOprtState()))
                {
/* <-MISALIGNED-> */ /* 396*/            if(editData.isIsEnabled())
                    {
/* <-MISALIGNED-> */ /* 397*/                btnCancel.setVisible(true);
/* <-MISALIGNED-> */ /* 398*/                btnCancel.setEnabled(true);
/* <-MISALIGNED-> */ /* 399*/                btnCancelCancel.setVisible(false);
                    } else
                    {
/* <-MISALIGNED-> */ /* 401*/                btnCancelCancel.setVisible(true);
/* <-MISALIGNED-> */ /* 402*/                btnCancelCancel.setEnabled(true);
/* <-MISALIGNED-> */ /* 403*/                btnCancel.setEnabled(false);
                    }
/* <-MISALIGNED-> */ /* 405*/            if("11111111-1111-1111-1111-111111111111CCE7AED4".equals(editData.getCU().getId().toString()))
/* <-MISALIGNED-> */ /* 406*/                btnRemove.setEnabled(false);
                } else
/* <-MISALIGNED-> */ /* 408*/        if("VIEW".equals(getOprtState()))
                {
/* <-MISALIGNED-> */ /* 409*/            if("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()))
                    {
/* <-MISALIGNED-> */ /* 410*/                if(editData.isIsEnabled())
                        {
/* <-MISALIGNED-> */ /* 411*/                    btnCancel.setVisible(true);
/* <-MISALIGNED-> */ /* 412*/                    btnCancel.setEnabled(true);
/* <-MISALIGNED-> */ /* 413*/                    btnCancelCancel.setVisible(false);
                        } else
                        {
/* <-MISALIGNED-> */ /* 415*/                    btnCancelCancel.setVisible(true);
/* <-MISALIGNED-> */ /* 416*/                    btnCancelCancel.setEnabled(true);
/* <-MISALIGNED-> */ /* 417*/                    btnCancel.setEnabled(false);
                        }
/* <-MISALIGNED-> */ /* 419*/                btnAddNew.setEnabled(true);
/* <-MISALIGNED-> */ /* 420*/                btnEdit.setEnabled(true);
/* <-MISALIGNED-> */ /* 421*/                menuItemAddNew.setEnabled(true);
/* <-MISALIGNED-> */ /* 422*/                menuItemEdit.setEnabled(true);
/* <-MISALIGNED-> */ /* 423*/                menuItemRemove.setEnabled(true);
                    } else
                    {
/* <-MISALIGNED-> */ /* 425*/                btnAddNew.setEnabled(false);
/* <-MISALIGNED-> */ /* 426*/                btnEdit.setEnabled(false);
/* <-MISALIGNED-> */ /* 427*/                btnRemove.setEnabled(false);
/* <-MISALIGNED-> */ /* 428*/                btnCancel.setVisible(false);
/* <-MISALIGNED-> */ /* 429*/                btnCancelCancel.setVisible(false);
/* <-MISALIGNED-> */ /* 430*/                menuItemAddNew.setEnabled(false);
/* <-MISALIGNED-> */ /* 431*/                menuItemEdit.setEnabled(false);
/* <-MISALIGNED-> */ /* 432*/                menuItemRemove.setEnabled(false);
                    }
/* <-MISALIGNED-> */ /* 434*/            if("11111111-1111-1111-1111-111111111111CCE7AED4".equals(editData.getCU().getId().toString()))
                    {
/* <-MISALIGNED-> */ /* 435*/                btnAddNew.setEnabled(false);
/* <-MISALIGNED-> */ /* 437*/                btnRemove.setEnabled(false);
/* <-MISALIGNED-> */ /* 438*/                btnCancel.setVisible(false);
/* <-MISALIGNED-> */ /* 439*/                btnCancelCancel.setVisible(false);
/* <-MISALIGNED-> */ /* 440*/                menuItemAddNew.setEnabled(false);
/* <-MISALIGNED-> */ /* 441*/                menuItemEdit.setEnabled(false);
/* <-MISALIGNED-> */ /* 442*/                menuItemRemove.setEnabled(false);
                    }
                }
            }
            protected void initOldData(IObjectValue dataObject)
            {
/* <-MISALIGNED-> */ /* 449*/        parentInfo = (ContractTypeInfo)getUIContext().get("ParentNode");
/* <-MISALIGNED-> */ /* 450*/        if(getUIContext().get("ParentNode") != null)
/* <-MISALIGNED-> */ /* 451*/            if("ADDNEW".equals(getOprtState()))
                    {
/* <-MISALIGNED-> */ /* 454*/                String strTemp = parentInfo.getLongNumber();
/* <-MISALIGNED-> */ /* 455*/                strTemp = strTemp.replace('!', '.');
/* <-MISALIGNED-> */ /* 457*/                ((ContractTypeInfo)dataObject).setLongNumber((new StringBuilder(String.valueOf(strTemp))).append(".").toString());
                    } else
/* <-MISALIGNED-> */ /* 458*/            if("EDIT".equals(getOprtState()))
                    {
/* <-MISALIGNED-> */ /* 459*/                String strTemp = ((ContractTypeInfo)dataObject).getLongNumber();
/* <-MISALIGNED-> */ /* 460*/                strTemp = strTemp.replace('!', '.');
/* <-MISALIGNED-> */ /* 462*/                ((ContractTypeInfo)dataObject).setLongNumber(strTemp);
                    }
            }
            public void actionCancel_actionPerformed(ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 476*/        if(editData != null && editData.getId() != null)
                {
/* <-MISALIGNED-> */ /* 477*/            com.kingdee.bos.dao.IObjectPK pk = new ObjectStringPK(editData.getId().toString());
/* <-MISALIGNED-> */ /* 478*/            if(((IContractType)getBizInterface()).disEnabled(pk))
                    {
/* <-MISALIGNED-> */ /* 479*/                showResultMessage(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "DisEnabled_OK"));
/* <-MISALIGNED-> */ /* 480*/                setDataObject(getValue(new ObjectUuidPK(editData.getId())));
/* <-MISALIGNED-> */ /* 481*/                loadFields();
/* <-MISALIGNED-> */ /* 482*/                setSave(true);
/* <-MISALIGNED-> */ /* 483*/                setSaved(true);
                    }
                }
            }
            protected void showResultMessage(String message)
            {
/* <-MISALIGNED-> */ /* 496*/        setMessageText(message);
/* <-MISALIGNED-> */ /* 499*/        showMessage();
            }
            public void actionCancelCancel_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 511*/        if(editData != null && editData.getId() != null)
                {/* 512*/            com.kingdee.bos.dao.IObjectPK pk = new ObjectStringPK(editData.getId().toString());
/* 513*/            if(((IContractType)getBizInterface()).enabled(pk))
                    {/* 514*/                showResultMessage(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Enabled_OK"));
/* 515*/                setDataObject(getValue(new ObjectUuidPK(editData.getId())));
/* 516*/                loadFields();
/* 517*/                setSave(true);
/* 518*/                setSaved(true);
                    }
                }
            }
            public void actionEdit_actionPerformed(ActionEvent e)
                throws Exception
            {







/* 532*/        super.actionEdit_actionPerformed(e);
/* 533*/        if("11111111-1111-1111-1111-111111111111CCE7AED4".equals(editData.getCU().getId().toString()))
/* 534*/            btnRemove.setEnabled(false);

/* 536*/        chkIsCost.setEnabled(true);
            }
            protected void prmtEntry_dataChanged(DataChangeEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 539*/        if(e.getNewValue() != null)
                {
/* <-MISALIGNED-> */ /* 540*/            Object value[] = (Object[])prmtEntry.getValue();
/* <-MISALIGNED-> */ /* 541*/            editData.getEntry().clear();
/* <-MISALIGNED-> */ /* 542*/            for(int i = 0; i < value.length; i++)
/* <-MISALIGNED-> */ /* 543*/                if(value[i] != null && (value[i] instanceof PayContentTypeInfo) && !((PayContentTypeInfo)value[i]).isIsLeaf())
                        {
/* <-MISALIGNED-> */ /* 544*/                    MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
/* <-MISALIGNED-> */ /* 545*/                    prmtEntry.setValue(null);
/* <-MISALIGNED-> */ /* 546*/                    return;
                        }
                }
            }
            protected void prmtContractWFTypeEntry_dataChanged(DataChangeEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 552*/        if(e.getNewValue() != null)
                {
/* <-MISALIGNED-> */ /* 553*/            Object value[] = (Object[])prmtContractWFTypeEntry.getValue();
/* <-MISALIGNED-> */ /* 554*/            editData.getContractWFTypeEntry().clear();
/* <-MISALIGNED-> */ /* 555*/            for(int i = 0; i < value.length; i++)
/* <-MISALIGNED-> */ /* 556*/                if(value[i] != null && (value[i] instanceof ContractWFTypeInfo) && !((ContractWFTypeInfo)value[i]).isIsLeaf())
                        {
/* <-MISALIGNED-> */ /* 557*/                    MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
/* <-MISALIGNED-> */ /* 558*/                    prmtContractWFTypeEntry.setValue(null);
/* <-MISALIGNED-> */ /* 559*/                    return;
                        }
                }
            }
            private static final Logger logger = CoreUIObject.getLogger(com.kingdee.eas.fdc.basedata.client.ContractTypeEditUI.class);
            private ContractTypeInfo parentInfo;
            private String parentNumber;
            private String strTemp;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_server.jar
	Total time: 29 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/