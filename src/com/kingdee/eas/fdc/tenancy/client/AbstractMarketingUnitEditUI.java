/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractMarketingUnitEditUI extends com.kingdee.eas.framework.client.TreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketingUnitEditUI.class);
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlBaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsPPM;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsSHE;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsTEN;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlDutyPerson;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlMember;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSellProject;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSellroject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelDutyPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddDutyPerson;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblDutyPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelSellProject;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMember;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddMember;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelMember;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contParentUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtParentUnit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsMAR;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsPRO;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsINS;
    protected com.kingdee.eas.fdc.tenancy.MarketingUnitInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMarketingUnitEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractMarketingUnitEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(true);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pnlBaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCreateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsPPM = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsSHE = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsTEN = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.pnlDutyPerson = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlMember = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlSellProject = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblSellroject = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnDelDutyPerson = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddDutyPerson = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblDutyPerson = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelSellProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblMember = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddMember = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelMember = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contParentUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtParentUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.chkIsMAR = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsPRO = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsINS = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtCreator.setName("prmtCreator");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.pnlBaseInfo.setName("pnlBaseInfo");
        this.contCreateDate.setName("contCreateDate");
        this.contCreator.setName("contCreator");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.chkIsPPM.setName("chkIsPPM");
        this.chkIsSHE.setName("chkIsSHE");
        this.chkIsTEN.setName("chkIsTEN");
        this.pnlDutyPerson.setName("pnlDutyPerson");
        this.pnlMember.setName("pnlMember");
        this.pnlSellProject.setName("pnlSellProject");
        this.tblSellroject.setName("tblSellroject");
        this.btnDelDutyPerson.setName("btnDelDutyPerson");
        this.btnAddDutyPerson.setName("btnAddDutyPerson");
        this.tblDutyPerson.setName("tblDutyPerson");
        this.btnAddProject.setName("btnAddProject");
        this.btnDelSellProject.setName("btnDelSellProject");
        this.tblMember.setName("tblMember");
        this.btnAddMember.setName("btnAddMember");
        this.btnDelMember.setName("btnDelMember");
        this.pkCreateDate.setName("pkCreateDate");
        this.contOrgUnit.setName("contOrgUnit");
        this.contParentUnit.setName("contParentUnit");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.prmtParentUnit.setName("prmtParentUnit");
        this.chkIsMAR.setName("chkIsMAR");
        this.chkIsPRO.setName("chkIsPRO");
        this.chkIsINS.setName("chkIsINS");
        // CoreUI		
        this.setBorder(null);
        // prmtCreator		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(40);		
        this.txtName.setRequired(true);
        // txtNumber		
        this.txtNumber.setMaxLength(40);		
        this.txtNumber.setRequired(true);
        // pnlBaseInfo		
        this.pnlBaseInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlBaseInfo.border.title")));
        // contCreateDate		
        this.contCreateDate.setBoundLabelText(resHelper.getString("contCreateDate.boundLabelText"));		
        this.contCreateDate.setBoundLabelLength(100);		
        this.contCreateDate.setBoundLabelUnderline(true);		
        this.contCreateDate.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // chkIsPPM		
        this.chkIsPPM.setText(resHelper.getString("chkIsPPM.text"));
        this.chkIsPPM.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsPPM_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkIsSHE		
        this.chkIsSHE.setText(resHelper.getString("chkIsSHE.text"));
        this.chkIsSHE.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsSHE_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkIsTEN		
        this.chkIsTEN.setText(resHelper.getString("chkIsTEN.text"));
        this.chkIsTEN.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsTEN_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pnlDutyPerson		
        this.pnlDutyPerson.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlDutyPerson.border.title")));
        // pnlMember		
        this.pnlMember.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlMember.border.title")));
        // pnlSellProject		
        this.pnlSellProject.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlSellProject.border.title")));
        // tblSellroject		
        this.tblSellroject.setFormatXml(resHelper.getString("tblSellroject.formatXml"));
        this.tblSellroject.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblSellroject_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblSellroject.putBindContents("editData",new String[] {"sellProject.name","sellProject.number","sellProject.isForSHE","sellProject.isForTen","sellProject.isForPPM"});


        // btnDelDutyPerson		
        this.btnDelDutyPerson.setText(resHelper.getString("btnDelDutyPerson.text"));
        this.btnDelDutyPerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelDutyPerson_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddDutyPerson		
        this.btnAddDutyPerson.setText(resHelper.getString("btnAddDutyPerson.text"));
        this.btnAddDutyPerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddDutyPerson_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblDutyPerson		
        this.tblDutyPerson.setFormatXml(resHelper.getString("tblDutyPerson.formatXml"));
        this.tblDutyPerson.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblDutyPerson_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnAddProject		
        this.btnAddProject.setText(resHelper.getString("btnAddProject.text"));
        this.btnAddProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddProject_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelSellProject		
        this.btnDelSellProject.setText(resHelper.getString("btnDelSellProject.text"));
        this.btnDelSellProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelSellProject_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblMember		
        this.tblMember.setFormatXml(resHelper.getString("tblMember.formatXml"));
        this.tblMember.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMember_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblMember.putBindContents("editData",new String[] {"member.name","accessionDate","dimissionDate","isSellFunction","isTenancyFunction","isWuYeFunction","isMarketFunction","isProjectFunction","isInsideFunction"});


        // btnAddMember		
        this.btnAddMember.setText(resHelper.getString("btnAddMember.text"));
        this.btnAddMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddMember_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelMember		
        this.btnDelMember.setText(resHelper.getString("btnDelMember.text"));
        this.btnDelMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelMember_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pkCreateDate		
        this.pkCreateDate.setEnabled(false);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setEnabled(false);
        // contParentUnit		
        this.contParentUnit.setBoundLabelText(resHelper.getString("contParentUnit.boundLabelText"));		
        this.contParentUnit.setBoundLabelLength(100);		
        this.contParentUnit.setBoundLabelUnderline(true);		
        this.contParentUnit.setEnabled(false);
        // prmtOrgUnit		
        this.prmtOrgUnit.setCommitFormat("$number$");		
        this.prmtOrgUnit.setEditFormat("$number$");		
        this.prmtOrgUnit.setDisplayFormat("$name$");		
        this.prmtOrgUnit.setQueryInfo("com.kingdee.eas.qm.common.app.F7FullOrgUnitQuery");		
        this.prmtOrgUnit.setEnabled(false);
        // prmtParentUnit		
        this.prmtParentUnit.setDisplayFormat("$name$");		
        this.prmtParentUnit.setEditFormat("$number$");		
        this.prmtParentUnit.setCommitFormat("$number$");		
        this.prmtParentUnit.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.MarketingUnitQuery");		
        this.prmtParentUnit.setEnabled(false);
        // chkIsMAR		
        this.chkIsMAR.setText(resHelper.getString("chkIsMAR.text"));
        this.chkIsMAR.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsMAR_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkIsPRO		
        this.chkIsPRO.setText(resHelper.getString("chkIsPRO.text"));
        this.chkIsPRO.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsPRO_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkIsINS		
        this.chkIsINS.setText(resHelper.getString("chkIsINS.text"));
        this.chkIsINS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsINS_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 625, 600));
        this.setLayout(null);
        pnlBaseInfo.setBounds(new Rectangle(1, 3, 624, 127));
        this.add(pnlBaseInfo, null);
        pnlDutyPerson.setBounds(new Rectangle(1, 253, 624, 132));
        this.add(pnlDutyPerson, null);
        pnlMember.setBounds(new Rectangle(1, 391, 624, 209));
        this.add(pnlMember, null);
        pnlSellProject.setBounds(new Rectangle(1, 135, 624, 113));
        this.add(pnlSellProject, null);
        //pnlBaseInfo
        pnlBaseInfo.setLayout(null);        contCreateDate.setBounds(new Rectangle(333, 70, 270, 19));
        pnlBaseInfo.add(contCreateDate, null);
        contCreator.setBounds(new Rectangle(16, 70, 270, 19));
        pnlBaseInfo.add(contCreator, null);
        contNumber.setBounds(new Rectangle(16, 43, 270, 19));
        pnlBaseInfo.add(contNumber, null);
        contName.setBounds(new Rectangle(333, 43, 270, 19));
        pnlBaseInfo.add(contName, null);
        chkIsPPM.setBounds(new Rectangle(218, 97, 79, 19));
        pnlBaseInfo.add(chkIsPPM, null);
        chkIsSHE.setBounds(new Rectangle(16, 97, 79, 19));
        pnlBaseInfo.add(chkIsSHE, null);
        chkIsTEN.setBounds(new Rectangle(117, 97, 79, 19));
        pnlBaseInfo.add(chkIsTEN, null);
        contOrgUnit.setBounds(new Rectangle(16, 16, 270, 19));
        pnlBaseInfo.add(contOrgUnit, null);
        contParentUnit.setBounds(new Rectangle(333, 16, 270, 19));
        pnlBaseInfo.add(contParentUnit, null);
        chkIsMAR.setBounds(new Rectangle(319, 97, 79, 20));
        pnlBaseInfo.add(chkIsMAR, null);
        chkIsPRO.setBounds(new Rectangle(420, 97, 79, 19));
        pnlBaseInfo.add(chkIsPRO, null);
        chkIsINS.setBounds(new Rectangle(523, 97, 79, 19));
        pnlBaseInfo.add(chkIsINS, null);
        //contCreateDate
        contCreateDate.setBoundEditor(pkCreateDate);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contParentUnit
        contParentUnit.setBoundEditor(prmtParentUnit);
        //pnlDutyPerson
        pnlDutyPerson.setLayout(null);        btnDelDutyPerson.setBounds(new Rectangle(530, 13, 80, 19));
        pnlDutyPerson.add(btnDelDutyPerson, null);
        btnAddDutyPerson.setBounds(new Rectangle(445, 13, 80, 19));
        pnlDutyPerson.add(btnAddDutyPerson, null);
        tblDutyPerson.setBounds(new Rectangle(11, 36, 599, 82));
        pnlDutyPerson.add(tblDutyPerson, null);
        //pnlMember
        pnlMember.setLayout(null);        tblMember.setBounds(new Rectangle(12, 38, 597, 157));
        pnlMember.add(tblMember, null);
        btnAddMember.setBounds(new Rectangle(445, 14, 80, 19));
        pnlMember.add(btnAddMember, null);
        btnDelMember.setBounds(new Rectangle(530, 14, 80, 19));
        pnlMember.add(btnDelMember, null);
        //pnlSellProject
        pnlSellProject.setLayout(null);        tblSellroject.setBounds(new Rectangle(12, 35, 598, 68));
        pnlSellProject.add(tblSellroject, null);
        btnAddProject.setBounds(new Rectangle(445, 12, 80, 19));
        pnlSellProject.add(btnAddProject, null);
        btnDelSellProject.setBounds(new Rectangle(530, 12, 80, 19));
        pnlSellProject.add(btnDelSellProject, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("isWuYeFunction", boolean.class, this.chkIsPPM, "selected");
		dataBinder.registerBinding("isSellFunction", boolean.class, this.chkIsSHE, "selected");
		dataBinder.registerBinding("isTenancyFunction", boolean.class, this.chkIsTEN, "selected");
		dataBinder.registerBinding("sellProject.sellProject.isForSHE", boolean.class, this.tblSellroject, "isForSHE.text");
		dataBinder.registerBinding("sellProject.sellProject.name", String.class, this.tblSellroject, "sellProject.text");
		dataBinder.registerBinding("sellProject.sellProject.number", String.class, this.tblSellroject, "number.text");
		dataBinder.registerBinding("sellProject.sellProject.isForTen", boolean.class, this.tblSellroject, "isForTEN.text");
		dataBinder.registerBinding("sellProject.sellProject.isForPPM", boolean.class, this.tblSellroject, "isForPPM.text");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectInfo.class, this.tblSellroject, "userObject");
		dataBinder.registerBinding("member", com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo.class, this.tblMember, "userObject");
		dataBinder.registerBinding("member.member.name", String.class, this.tblMember, "member.text");
		dataBinder.registerBinding("member.accessionDate", java.util.Date.class, this.tblMember, "accessionDate.text");
		dataBinder.registerBinding("member.dimissionDate", java.util.Date.class, this.tblMember, "dimissionDate.text");
		dataBinder.registerBinding("member.isSellFunction", boolean.class, this.tblMember, "isSHE.text");
		dataBinder.registerBinding("member.isTenancyFunction", boolean.class, this.tblMember, "isTEN.text");
		dataBinder.registerBinding("member.isWuYeFunction", boolean.class, this.tblMember, "isPPM.text");
		dataBinder.registerBinding("member.isMarketFunction", boolean.class, this.tblMember, "isMAR.text");
		dataBinder.registerBinding("member.isProjectFunction", boolean.class, this.tblMember, "isPRO.text");
		dataBinder.registerBinding("member.isInsideFunction", boolean.class, this.tblMember, "isINS.text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateDate, "value");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("parent", com.kingdee.eas.fdc.tenancy.MarketingUnitInfo.class, this.prmtParentUnit, "data");
		dataBinder.registerBinding("isMarketFunction", boolean.class, this.chkIsMAR, "selected");
		dataBinder.registerBinding("isProjectFunction", boolean.class, this.chkIsPRO, "selected");
		dataBinder.registerBinding("isInsideFunction", boolean.class, this.chkIsINS, "selected");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.MarketingUnitEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.tenancy.MarketingUnitInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isWuYeFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSellFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isTenancyFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject.sellProject.isForSHE", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject.sellProject.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject.sellProject.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject.sellProject.isForTen", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject.sellProject.isForPPM", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.member.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.accessionDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.dimissionDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.isSellFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.isTenancyFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.isWuYeFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.isMarketFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.isProjectFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("member.isInsideFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isMarketFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isProjectFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isInsideFunction", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output chkIsPPM_stateChanged method
     */
    protected void chkIsPPM_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsSHE_stateChanged method
     */
    protected void chkIsSHE_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsTEN_stateChanged method
     */
    protected void chkIsTEN_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output tblSellroject_editStopped method
     */
    protected void tblSellroject_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnDelDutyPerson_actionPerformed method
     */
    protected void btnDelDutyPerson_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddDutyPerson_actionPerformed method
     */
    protected void btnAddDutyPerson_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblDutyPerson_editStopped method
     */
    protected void tblDutyPerson_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnAddProject_actionPerformed method
     */
    protected void btnAddProject_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelSellProject_actionPerformed method
     */
    protected void btnDelSellProject_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblMember_editStopped method
     */
    protected void tblMember_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnAddMember_actionPerformed method
     */
    protected void btnAddMember_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelMember_actionPerformed method
     */
    protected void btnDelMember_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsMAR_stateChanged method
     */
    protected void chkIsMAR_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsPRO_stateChanged method
     */
    protected void chkIsPRO_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsINS_stateChanged method
     */
    protected void chkIsINS_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isWuYeFunction"));
        sic.add(new SelectorItemInfo("isSellFunction"));
        sic.add(new SelectorItemInfo("isTenancyFunction"));
    sic.add(new SelectorItemInfo("sellProject.sellProject.isForSHE"));
    sic.add(new SelectorItemInfo("sellProject.sellProject.name"));
    sic.add(new SelectorItemInfo("sellProject.sellProject.number"));
    sic.add(new SelectorItemInfo("sellProject.sellProject.isForTen"));
    sic.add(new SelectorItemInfo("sellProject.sellProject.isForPPM"));
        sic.add(new SelectorItemInfo("sellProject.*"));
//        sic.add(new SelectorItemInfo("sellProject.number"));
        sic.add(new SelectorItemInfo("member.*"));
//        sic.add(new SelectorItemInfo("member.number"));
    sic.add(new SelectorItemInfo("member.member.name"));
    sic.add(new SelectorItemInfo("member.accessionDate"));
    sic.add(new SelectorItemInfo("member.dimissionDate"));
    sic.add(new SelectorItemInfo("member.isSellFunction"));
    sic.add(new SelectorItemInfo("member.isTenancyFunction"));
    sic.add(new SelectorItemInfo("member.isWuYeFunction"));
    sic.add(new SelectorItemInfo("member.isMarketFunction"));
    sic.add(new SelectorItemInfo("member.isProjectFunction"));
    sic.add(new SelectorItemInfo("member.isInsideFunction"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("parent.*"));
        sic.add(new SelectorItemInfo("isMarketFunction"));
        sic.add(new SelectorItemInfo("isProjectFunction"));
        sic.add(new SelectorItemInfo("isInsideFunction"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "MarketingUnitEditUI");
    }




}