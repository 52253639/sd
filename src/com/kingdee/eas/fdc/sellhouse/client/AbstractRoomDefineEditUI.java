/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractRoomDefineEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomDefineEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnBuildingClearedUp;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner unitAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllSelected;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllDrop;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLineRoomDes;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelLineRoomDes;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBuiding;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomUsage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilStruct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCalcType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPosseStd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEyeSignht;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDecoraStd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoise;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDirection;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFlatArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUseArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCarbarnArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGuardenArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomUsage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilStruct;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboChangeState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCalcType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSellType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPosseStd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEyeSignht;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDecoraStd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNoise;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDirection;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFlatArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUseArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCarbarnArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGuardenArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtIbasement;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtinnside;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtIbaInnside;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtIbameasured;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreateRoom;
    protected com.kingdee.eas.fdc.sellhouse.RoomDesInfo editData = null;
    protected ActionCreateRoom actionCreateRoom = null;
    /**
     * output class constructor
     */
    public AbstractRoomDefineEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomDefineEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCreateRoom
        this.actionCreateRoom = new ActionCreateRoom(this);
        getActionManager().registerAction("actionCreateRoom", actionCreateRoom);
         this.actionCreateRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnBuildingClearedUp = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.unitAmount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.prmtBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnAllSelected = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAllDrop = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddLineRoomDes = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelLineRoomDes = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblBuiding = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contRoomUsage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilStruct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCalcType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPosseStd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEyeSignht = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDecoraStd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoise = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDirection = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFlatArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUseArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCarbarnArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGuardenArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoomUsage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuilStruct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPlanRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlanBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboChangeState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboCalcType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboSellType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtPosseStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtEyeSignht = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDecoraStd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtNoise = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDirection = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtBuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFlatArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUseArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCarbarnArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGuardenArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtIbasement = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinnside = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtIbaInnside = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtIbameasured = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnCreateRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contUnit.setName("contUnit");
        this.contBuilding.setName("contBuilding");
        this.kDContainer1.setName("kDContainer1");
        this.btnBuildingClearedUp.setName("btnBuildingClearedUp");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.unitAmount.setName("unitAmount");
        this.prmtBuilding.setName("prmtBuilding");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnAllSelected.setName("btnAllSelected");
        this.btnAllDrop.setName("btnAllDrop");
        this.btnAddLineRoomDes.setName("btnAddLineRoomDes");
        this.btnDelLineRoomDes.setName("btnDelLineRoomDes");
        this.tblBuiding.setName("tblBuiding");
        this.kDPanel1.setName("kDPanel1");
        this.kDSeparator9.setName("kDSeparator9");
        this.contRoomUsage.setName("contRoomUsage");
        this.contBuilStruct.setName("contBuilStruct");
        this.contPlanRoomArea.setName("contPlanRoomArea");
        this.contPlanBuildingArea.setName("contPlanBuildingArea");
        this.contChangeState.setName("contChangeState");
        this.contCalcType.setName("contCalcType");
        this.contSellType.setName("contSellType");
        this.contPosseStd.setName("contPosseStd");
        this.contEyeSignht.setName("contEyeSignht");
        this.contDecoraStd.setName("contDecoraStd");
        this.contNoise.setName("contNoise");
        this.contProductType.setName("contProductType");
        this.contSight.setName("contSight");
        this.contDirection.setName("contDirection");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contFlatArea.setName("contFlatArea");
        this.contUseArea.setName("contUseArea");
        this.contCarbarnArea.setName("contCarbarnArea");
        this.contGuardenArea.setName("contGuardenArea");
        this.contBalconyArea.setName("contBalconyArea");
        this.contRoomArea.setName("contRoomArea");
        this.contBuildingArea.setName("contBuildingArea");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.prmtRoomUsage.setName("prmtRoomUsage");
        this.prmtBuilStruct.setName("prmtBuilStruct");
        this.txtPlanRoomArea.setName("txtPlanRoomArea");
        this.txtPlanBuildingArea.setName("txtPlanBuildingArea");
        this.comboChangeState.setName("comboChangeState");
        this.comboCalcType.setName("comboCalcType");
        this.comboSellType.setName("comboSellType");
        this.prmtPosseStd.setName("prmtPosseStd");
        this.prmtEyeSignht.setName("prmtEyeSignht");
        this.prmtDecoraStd.setName("prmtDecoraStd");
        this.prmtNoise.setName("prmtNoise");
        this.prmtProductType.setName("prmtProductType");
        this.prmtSight.setName("prmtSight");
        this.prmtDirection.setName("prmtDirection");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.prmtBuildingProperty.setName("prmtBuildingProperty");
        this.txtFlatArea.setName("txtFlatArea");
        this.txtUseArea.setName("txtUseArea");
        this.txtCarbarnArea.setName("txtCarbarnArea");
        this.txtGuardenArea.setName("txtGuardenArea");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtIbasement.setName("txtIbasement");
        this.txtinnside.setName("txtinnside");
        this.txtIbaInnside.setName("txtIbaInnside");
        this.txtIbameasured.setName("txtIbameasured");
        this.btnCreateRoom.setName("btnCreateRoom");
        // CoreUI		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setEnabled(false);		
        this.btnSave.setVisible(false);		
        this.btnSubmit.setEnabled(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);
        // contUnit		
        this.contUnit.setBoundLabelText(resHelper.getString("contUnit.boundLabelText"));		
        this.contUnit.setBoundLabelLength(100);		
        this.contUnit.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // btnBuildingClearedUp		
        this.btnBuildingClearedUp.setText(resHelper.getString("btnBuildingClearedUp.text"));
        this.btnBuildingClearedUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnBuildingClearedUp_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDTabbedPane1
        // unitAmount
        this.unitAmount.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    unitAmount_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.unitAmount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    unitAmount_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtBuilding		
        this.prmtBuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.prmtBuilding.setEnabled(false);		
        this.prmtBuilding.setDisplayFormat("$name$");		
        this.prmtBuilding.setEditFormat("$number$");		
        this.prmtBuilding.setCommitFormat("$name$");
        // kDScrollPane1
        // btnAllSelected		
        this.btnAllSelected.setText(resHelper.getString("btnAllSelected.text"));
        this.btnAllSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAllSelected_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAllDrop		
        this.btnAllDrop.setText(resHelper.getString("btnAllDrop.text"));
        this.btnAllDrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAllDrop_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddLineRoomDes		
        this.btnAddLineRoomDes.setText(resHelper.getString("btnAddLineRoomDes.text"));
        this.btnAddLineRoomDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddLineRoomDes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelLineRoomDes		
        this.btnDelLineRoomDes.setText(resHelper.getString("btnDelLineRoomDes.text"));
        this.btnDelLineRoomDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelLineRoomDes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblBuiding
		String tblBuidingStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"buildingId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"isSelected\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"unitSeq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"unitName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"defineInfo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"floorFrom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" /><t:Column t:key=\"floorTo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"6\" /><t:Column t:key=\"serialNumFrom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"7\" /><t:Column t:key=\"serialNumTo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"8\" /><t:Column t:key=\"isConvertToChar\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"unitId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"billId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{buildingId}</t:Cell><t:Cell>$Resource{isSelected}</t:Cell><t:Cell>$Resource{unitSeq}</t:Cell><t:Cell>$Resource{unitName}</t:Cell><t:Cell>$Resource{defineInfo}</t:Cell><t:Cell>$Resource{floorFrom}</t:Cell><t:Cell>$Resource{floorTo}</t:Cell><t:Cell>$Resource{serialNumFrom}</t:Cell><t:Cell>$Resource{serialNumTo}</t:Cell><t:Cell>$Resource{isConvertToChar}</t:Cell><t:Cell>$Resource{unitId}</t:Cell><t:Cell>$Resource{billId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblBuiding.setFormatXml(resHelper.translateString("tblBuiding",tblBuidingStrXML));
        this.tblBuiding.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblBuiding_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblBuiding.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    tblBuiding_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblBuiding.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBuiding_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBuiding_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBuiding_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kDPanel1
        // kDSeparator9
        // contRoomUsage		
        this.contRoomUsage.setBoundLabelText(resHelper.getString("contRoomUsage.boundLabelText"));		
        this.contRoomUsage.setBoundLabelLength(100);		
        this.contRoomUsage.setBoundLabelUnderline(true);
        // contBuilStruct		
        this.contBuilStruct.setBoundLabelText(resHelper.getString("contBuilStruct.boundLabelText"));		
        this.contBuilStruct.setBoundLabelLength(100);		
        this.contBuilStruct.setBoundLabelUnderline(true);
        // contPlanRoomArea		
        this.contPlanRoomArea.setBoundLabelText(resHelper.getString("contPlanRoomArea.boundLabelText"));		
        this.contPlanRoomArea.setBoundLabelLength(100);		
        this.contPlanRoomArea.setBoundLabelUnderline(true);
        // contPlanBuildingArea		
        this.contPlanBuildingArea.setBoundLabelText(resHelper.getString("contPlanBuildingArea.boundLabelText"));		
        this.contPlanBuildingArea.setBoundLabelLength(100);		
        this.contPlanBuildingArea.setBoundLabelUnderline(true);
        // contChangeState		
        this.contChangeState.setBoundLabelText(resHelper.getString("contChangeState.boundLabelText"));		
        this.contChangeState.setBoundLabelLength(100);		
        this.contChangeState.setBoundLabelUnderline(true);
        // contCalcType		
        this.contCalcType.setBoundLabelText(resHelper.getString("contCalcType.boundLabelText"));		
        this.contCalcType.setBoundLabelLength(100);		
        this.contCalcType.setBoundLabelUnderline(true);
        // contSellType		
        this.contSellType.setBoundLabelText(resHelper.getString("contSellType.boundLabelText"));		
        this.contSellType.setBoundLabelLength(100);		
        this.contSellType.setBoundLabelUnderline(true);
        // contPosseStd		
        this.contPosseStd.setBoundLabelText(resHelper.getString("contPosseStd.boundLabelText"));		
        this.contPosseStd.setBoundLabelLength(100);		
        this.contPosseStd.setBoundLabelUnderline(true);
        // contEyeSignht		
        this.contEyeSignht.setBoundLabelText(resHelper.getString("contEyeSignht.boundLabelText"));		
        this.contEyeSignht.setBoundLabelLength(100);		
        this.contEyeSignht.setBoundLabelUnderline(true);
        // contDecoraStd		
        this.contDecoraStd.setBoundLabelText(resHelper.getString("contDecoraStd.boundLabelText"));		
        this.contDecoraStd.setBoundLabelLength(100);		
        this.contDecoraStd.setBoundLabelUnderline(true);
        // contNoise		
        this.contNoise.setBoundLabelText(resHelper.getString("contNoise.boundLabelText"));		
        this.contNoise.setBoundLabelLength(100);		
        this.contNoise.setBoundLabelUnderline(true);
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // contSight		
        this.contSight.setBoundLabelText(resHelper.getString("contSight.boundLabelText"));		
        this.contSight.setBoundLabelLength(100);		
        this.contSight.setBoundLabelUnderline(true);
        // contDirection		
        this.contDirection.setBoundLabelText(resHelper.getString("contDirection.boundLabelText"));		
        this.contDirection.setBoundLabelLength(100);		
        this.contDirection.setBoundLabelUnderline(true);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelLength(100);		
        this.contActualRoomArea.setBoundLabelUnderline(true);
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelLength(100);		
        this.contActualBuildingArea.setBoundLabelUnderline(true);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // contFlatArea		
        this.contFlatArea.setBoundLabelText(resHelper.getString("contFlatArea.boundLabelText"));		
        this.contFlatArea.setBoundLabelLength(100);		
        this.contFlatArea.setBoundLabelUnderline(true);
        // contUseArea		
        this.contUseArea.setBoundLabelText(resHelper.getString("contUseArea.boundLabelText"));		
        this.contUseArea.setBoundLabelLength(100);		
        this.contUseArea.setBoundLabelUnderline(true);
        // contCarbarnArea		
        this.contCarbarnArea.setBoundLabelText(resHelper.getString("contCarbarnArea.boundLabelText"));		
        this.contCarbarnArea.setBoundLabelLength(100);		
        this.contCarbarnArea.setBoundLabelUnderline(true);
        // contGuardenArea		
        this.contGuardenArea.setBoundLabelText(resHelper.getString("contGuardenArea.boundLabelText"));		
        this.contGuardenArea.setBoundLabelLength(100);		
        this.contGuardenArea.setBoundLabelUnderline(true);
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelLength(100);		
        this.contBalconyArea.setBoundLabelUnderline(true);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(100);		
        this.contRoomArea.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(100);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(100);
        // prmtRoomUsage		
        this.prmtRoomUsage.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");		
        this.prmtRoomUsage.setCommitFormat("$name$");		
        this.prmtRoomUsage.setDisplayFormat("$name$");		
        this.prmtRoomUsage.setEditFormat("$number$");
        // prmtBuilStruct		
        this.prmtBuilStruct.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingStructureQuery");
        // txtPlanRoomArea
        // txtPlanBuildingArea
        // comboChangeState		
        this.comboChangeState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChangeStateEnum").toArray());
        // comboCalcType		
        this.comboCalcType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CalcTypeEnum").toArray());
        // comboSellType		
        this.comboSellType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SellTypeEnum").toArray());
        // prmtPosseStd		
        this.prmtPosseStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtEyeSignht		
        this.prmtEyeSignht.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtDecoraStd		
        this.prmtDecoraStd.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtNoise		
        this.prmtNoise.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtProductType		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");		
        this.prmtProductType.setRequired(true);
        // prmtSight		
        this.prmtSight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");
        // prmtDirection		
        this.prmtDirection.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // txtActualRoomArea
        // txtActualBuildingArea
        // prmtBuildingProperty		
        this.prmtBuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.prmtBuildingProperty.setRequired(true);
        // txtFlatArea
        // txtUseArea
        // txtCarbarnArea
        // txtGuardenArea
        // txtBalconyArea
        // txtRoomArea
        // txtBuildingArea
        // txtIbasement
        // txtinnside
        // txtIbaInnside
        // txtIbameasured
        // btnCreateRoom
        this.btnCreateRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreateRoom.setText(resHelper.getString("btnCreateRoom.text"));		
        this.btnCreateRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_others"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 530));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 530));
        contUnit.setBounds(new Rectangle(376, 8, 270, 19));
        this.add(contUnit, new KDLayout.Constraints(376, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuilding.setBounds(new Rectangle(23, 10, 270, 19));
        this.add(contBuilding, new KDLayout.Constraints(23, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(13, 40, 993, 168));
        this.add(kDContainer1, new KDLayout.Constraints(13, 40, 993, 168, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnBuildingClearedUp.setBounds(new Rectangle(762, 8, 198, 21));
        this.add(btnBuildingClearedUp, new KDLayout.Constraints(762, 8, 198, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(9, 268, 997, 255));
        this.add(kDTabbedPane1, new KDLayout.Constraints(9, 268, 997, 255, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contUnit
        contUnit.setBoundEditor(unitAmount);
        //contBuilding
        contBuilding.setBoundEditor(prmtBuilding);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(13, 40, 993, 168));        kDScrollPane1.setBounds(new Rectangle(3, 28, 985, 114));
        kDContainer1.getContentPane().add(kDScrollPane1, new KDLayout.Constraints(3, 28, 985, 114, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAllSelected.setBounds(new Rectangle(519, 4, 59, 19));
        kDContainer1.getContentPane().add(btnAllSelected, new KDLayout.Constraints(519, 4, 59, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAllDrop.setBounds(new Rectangle(589, 4, 58, 19));
        kDContainer1.getContentPane().add(btnAllDrop, new KDLayout.Constraints(589, 4, 58, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddLineRoomDes.setBounds(new Rectangle(657, 4, 149, 19));
        kDContainer1.getContentPane().add(btnAddLineRoomDes, new KDLayout.Constraints(657, 4, 149, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDelLineRoomDes.setBounds(new Rectangle(818, 4, 149, 19));
        kDContainer1.getContentPane().add(btnDelLineRoomDes, new KDLayout.Constraints(818, 4, 149, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDScrollPane1
        kDScrollPane1.getViewport().add(tblBuiding, null);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 996, 222));        kDSeparator9.setBounds(new Rectangle(1, 97, 986, 8));
        kDPanel1.add(kDSeparator9, new KDLayout.Constraints(1, 97, 986, 8, 0));
        contRoomUsage.setBounds(new Rectangle(9, 74, 270, 19));
        kDPanel1.add(contRoomUsage, new KDLayout.Constraints(9, 74, 270, 19, 0));
        contBuilStruct.setBounds(new Rectangle(716, 4, 270, 19));
        kDPanel1.add(contBuilStruct, new KDLayout.Constraints(716, 4, 270, 19, 0));
        contPlanRoomArea.setBounds(new Rectangle(11, 126, 270, 19));
        kDPanel1.add(contPlanRoomArea, new KDLayout.Constraints(11, 126, 270, 19, 0));
        contPlanBuildingArea.setBounds(new Rectangle(11, 103, 270, 19));
        kDPanel1.add(contPlanBuildingArea, new KDLayout.Constraints(11, 103, 270, 19, 0));
        contChangeState.setBounds(new Rectangle(287, 494, 270, 19));
        kDPanel1.add(contChangeState, new KDLayout.Constraints(287, 494, 270, 19, 0));
        contCalcType.setBounds(new Rectangle(-3, 494, 270, 19));
        kDPanel1.add(contCalcType, new KDLayout.Constraints(-3, 494, 270, 19, 0));
        contSellType.setBounds(new Rectangle(287, 464, 270, 19));
        kDPanel1.add(contSellType, new KDLayout.Constraints(287, 464, 270, 19, 0));
        contPosseStd.setBounds(new Rectangle(363, 27, 270, 19));
        kDPanel1.add(contPosseStd, new KDLayout.Constraints(363, 27, 270, 19, 0));
        contEyeSignht.setBounds(new Rectangle(9, 51, 270, 19));
        kDPanel1.add(contEyeSignht, new KDLayout.Constraints(9, 51, 270, 19, 0));
        contDecoraStd.setBounds(new Rectangle(716, 27, 270, 19));
        kDPanel1.add(contDecoraStd, new KDLayout.Constraints(716, 27, 270, 19, 0));
        contNoise.setBounds(new Rectangle(716, 50, 270, 19));
        kDPanel1.add(contNoise, new KDLayout.Constraints(716, 50, 270, 19, 0));
        contProductType.setBounds(new Rectangle(9, 5, 270, 19));
        kDPanel1.add(contProductType, new KDLayout.Constraints(9, 5, 270, 19, 0));
        contSight.setBounds(new Rectangle(363, 50, 270, 19));
        kDPanel1.add(contSight, new KDLayout.Constraints(363, 50, 270, 19, 0));
        contDirection.setBounds(new Rectangle(9, 28, 270, 19));
        kDPanel1.add(contDirection, new KDLayout.Constraints(9, 28, 270, 19, 0));
        contActualRoomArea.setBounds(new Rectangle(720, 126, 270, 19));
        kDPanel1.add(contActualRoomArea, new KDLayout.Constraints(720, 126, 270, 19, 0));
        contActualBuildingArea.setBounds(new Rectangle(720, 103, 270, 19));
        kDPanel1.add(contActualBuildingArea, new KDLayout.Constraints(720, 103, 270, 19, 0));
        contBuildingProperty.setBounds(new Rectangle(363, 5, 270, 19));
        kDPanel1.add(contBuildingProperty, new KDLayout.Constraints(363, 5, 270, 19, 0));
        contFlatArea.setBounds(new Rectangle(11, 195, 270, 19));
        kDPanel1.add(contFlatArea, new KDLayout.Constraints(11, 195, 270, 19, 0));
        contUseArea.setBounds(new Rectangle(367, 195, 270, 19));
        kDPanel1.add(contUseArea, new KDLayout.Constraints(367, 195, 270, 19, 0));
        contCarbarnArea.setBounds(new Rectangle(367, 172, 270, 19));
        kDPanel1.add(contCarbarnArea, new KDLayout.Constraints(367, 172, 270, 19, 0));
        contGuardenArea.setBounds(new Rectangle(720, 195, 270, 19));
        kDPanel1.add(contGuardenArea, new KDLayout.Constraints(720, 195, 270, 19, 0));
        contBalconyArea.setBounds(new Rectangle(720, 172, 270, 19));
        kDPanel1.add(contBalconyArea, new KDLayout.Constraints(720, 172, 270, 19, 0));
        contRoomArea.setBounds(new Rectangle(367, 126, 270, 19));
        kDPanel1.add(contRoomArea, new KDLayout.Constraints(367, 126, 270, 19, 0));
        contBuildingArea.setBounds(new Rectangle(367, 103, 270, 19));
        kDPanel1.add(contBuildingArea, new KDLayout.Constraints(367, 103, 270, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(11, 149, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(11, 149, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(11, 172, 270, 19));
        kDPanel1.add(kDLabelContainer2, new KDLayout.Constraints(11, 172, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(367, 149, 270, 19));
        kDPanel1.add(kDLabelContainer3, new KDLayout.Constraints(367, 149, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(720, 149, 270, 19));
        kDPanel1.add(kDLabelContainer4, new KDLayout.Constraints(720, 149, 270, 19, 0));
        //contRoomUsage
        contRoomUsage.setBoundEditor(prmtRoomUsage);
        //contBuilStruct
        contBuilStruct.setBoundEditor(prmtBuilStruct);
        //contPlanRoomArea
        contPlanRoomArea.setBoundEditor(txtPlanRoomArea);
        //contPlanBuildingArea
        contPlanBuildingArea.setBoundEditor(txtPlanBuildingArea);
        //contChangeState
        contChangeState.setBoundEditor(comboChangeState);
        //contCalcType
        contCalcType.setBoundEditor(comboCalcType);
        //contSellType
        contSellType.setBoundEditor(comboSellType);
        //contPosseStd
        contPosseStd.setBoundEditor(prmtPosseStd);
        //contEyeSignht
        contEyeSignht.setBoundEditor(prmtEyeSignht);
        //contDecoraStd
        contDecoraStd.setBoundEditor(prmtDecoraStd);
        //contNoise
        contNoise.setBoundEditor(prmtNoise);
        //contProductType
        contProductType.setBoundEditor(prmtProductType);
        //contSight
        contSight.setBoundEditor(prmtSight);
        //contDirection
        contDirection.setBoundEditor(prmtDirection);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtActualRoomArea);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtActualBuildingArea);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(prmtBuildingProperty);
        //contFlatArea
        contFlatArea.setBoundEditor(txtFlatArea);
        //contUseArea
        contUseArea.setBoundEditor(txtUseArea);
        //contCarbarnArea
        contCarbarnArea.setBoundEditor(txtCarbarnArea);
        //contGuardenArea
        contGuardenArea.setBoundEditor(txtGuardenArea);
        //contBalconyArea
        contBalconyArea.setBoundEditor(txtBalconyArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtIbasement);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtinnside);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtIbaInnside);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtIbameasured);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
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
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnCreateRoom);
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
		dataBinder.registerBinding("building", com.kingdee.eas.fdc.sellhouse.BuildingInfo.class, this.prmtBuilding, "data");
		dataBinder.registerBinding("newRoomUsage", com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo.class, this.prmtRoomUsage, "data");
		dataBinder.registerBinding("buildStruct", com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo.class, this.prmtBuilStruct, "data");
		dataBinder.registerBinding("planRoomArea", java.math.BigDecimal.class, this.txtPlanRoomArea, "value");
		dataBinder.registerBinding("planBuildingArea", java.math.BigDecimal.class, this.txtPlanBuildingArea, "value");
		dataBinder.registerBinding("newPossstd", com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo.class, this.prmtPosseStd, "data");
		dataBinder.registerBinding("newEyeSight", com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo.class, this.prmtEyeSignht, "data");
		dataBinder.registerBinding("newDecorastd", com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo.class, this.prmtDecoraStd, "data");
		dataBinder.registerBinding("newNoise", com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo.class, this.prmtNoise, "data");
		dataBinder.registerBinding("productType", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.prmtProductType, "data");
		dataBinder.registerBinding("newSight", com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo.class, this.prmtSight, "data");
		dataBinder.registerBinding("direction", com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo.class, this.prmtDirection, "data");
		dataBinder.registerBinding("actualRoomArea", java.math.BigDecimal.class, this.txtActualRoomArea, "value");
		dataBinder.registerBinding("actualBuildingArea", java.math.BigDecimal.class, this.txtActualBuildingArea, "value");
		dataBinder.registerBinding("buildingProperty", com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo.class, this.prmtBuildingProperty, "data");
		dataBinder.registerBinding("flatArea", java.math.BigDecimal.class, this.txtFlatArea, "value");
		dataBinder.registerBinding("userArea", java.math.BigDecimal.class, this.txtUseArea, "value");
		dataBinder.registerBinding("carbarnArea", java.math.BigDecimal.class, this.txtCarbarnArea, "value");
		dataBinder.registerBinding("guardenArea", java.math.BigDecimal.class, this.txtGuardenArea, "value");
		dataBinder.registerBinding("balconyArea", java.math.BigDecimal.class, this.txtBalconyArea, "value");
		dataBinder.registerBinding("roomArea", java.math.BigDecimal.class, this.txtRoomArea, "value");
		dataBinder.registerBinding("buildingArea", java.math.BigDecimal.class, this.txtBuildingArea, "value");
		dataBinder.registerBinding("Ibasement", java.math.BigDecimal.class, this.txtIbasement, "value");
		dataBinder.registerBinding("Insside", java.math.BigDecimal.class, this.txtinnside, "value");
		dataBinder.registerBinding("IbaInnside", java.math.BigDecimal.class, this.txtIbaInnside, "value");
		dataBinder.registerBinding("Ibameasured", java.math.BigDecimal.class, this.txtIbameasured, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomDefineEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomDesInfo)ov;
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
		getValidateHelper().registerBindProperty("building", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newRoomUsage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildStruct", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planRoomArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planBuildingArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newPossstd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newEyeSight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newDecorastd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newNoise", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("productType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newSight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("direction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualRoomArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualBuildingArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildingProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("flatArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("userArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("carbarnArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guardenArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("balconyArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildingArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ibasement", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Insside", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("IbaInnside", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ibameasured", ValidateHelper.ON_SAVE);    		
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
     * output btnBuildingClearedUp_actionPerformed method
     */
    protected void btnBuildingClearedUp_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output unitAmount_stateChanged method
     */
    protected void unitAmount_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output unitAmount_propertyChange method
     */
    protected void unitAmount_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output btnAllSelected_actionPerformed method
     */
    protected void btnAllSelected_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAllDrop_actionPerformed method
     */
    protected void btnAllDrop_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddLineRoomDes_actionPerformed method
     */
    protected void btnAddLineRoomDes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelLineRoomDes_actionPerformed method
     */
    protected void btnDelLineRoomDes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblBuiding_editStopping method
     */
    protected void tblBuiding_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblBuiding_tableClicked method
     */
    protected void tblBuiding_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblBuiding_activeCellChanged method
     */
    protected void tblBuiding_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output tblBuiding_editStopped method
     */
    protected void tblBuiding_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblBuiding_editValueChanged method
     */
    protected void tblBuiding_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("building.*"));
        sic.add(new SelectorItemInfo("newRoomUsage.*"));
        sic.add(new SelectorItemInfo("buildStruct.*"));
        sic.add(new SelectorItemInfo("planRoomArea"));
        sic.add(new SelectorItemInfo("planBuildingArea"));
        sic.add(new SelectorItemInfo("newPossstd.*"));
        sic.add(new SelectorItemInfo("newEyeSight.*"));
        sic.add(new SelectorItemInfo("newDecorastd.*"));
        sic.add(new SelectorItemInfo("newNoise.*"));
        sic.add(new SelectorItemInfo("productType.*"));
        sic.add(new SelectorItemInfo("newSight.*"));
        sic.add(new SelectorItemInfo("direction.*"));
        sic.add(new SelectorItemInfo("actualRoomArea"));
        sic.add(new SelectorItemInfo("actualBuildingArea"));
        sic.add(new SelectorItemInfo("buildingProperty.*"));
        sic.add(new SelectorItemInfo("flatArea"));
        sic.add(new SelectorItemInfo("userArea"));
        sic.add(new SelectorItemInfo("carbarnArea"));
        sic.add(new SelectorItemInfo("guardenArea"));
        sic.add(new SelectorItemInfo("balconyArea"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("buildingArea"));
        sic.add(new SelectorItemInfo("Ibasement"));
        sic.add(new SelectorItemInfo("Insside"));
        sic.add(new SelectorItemInfo("IbaInnside"));
        sic.add(new SelectorItemInfo("Ibameasured"));
        return sic;
    }        
    	

    /**
     * output actionCreateRoom_actionPerformed method
     */
    public void actionCreateRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCreateRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateRoom() {
    	return false;
    }

    /**
     * output ActionCreateRoom class
     */     
    protected class ActionCreateRoom extends ItemAction {     
    
        public ActionCreateRoom()
        {
            this(null);
        }

        public ActionCreateRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCreateRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomDefineEditUI.this, "ActionCreateRoom", "actionCreateRoom_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomDefineEditUI");
    }




}