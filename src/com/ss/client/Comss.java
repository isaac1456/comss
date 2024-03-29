package com.ss.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IPickTreeItem;
import com.smartgwt.client.widgets.form.fields.MultiComboBoxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.tree.Tree;
import com.ss.shared.DataSources;
import com.ss.shared.Funciones;
import com.ss.shared.dataAppObj;
import com.ss.shared.dataAppObjVersion;
import com.ss.shared.dataAppReg;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Comss implements EntryPoint {

	public void onModuleLoad() {

		final GreetingServiceAsync gsa = GWT.create(GreetingService.class);

		final TabSet tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setWidth("90%");
		tabSet.setHeight100();
		final VLayout layoutApp = new VLayout(10);
		layoutApp.setWidth("100%");

		tabSet.setStyleName("containertabs");
		Tab tTabApp = new Tab("Aplicaciones", "");
		Tab tTabResult = new Tab("Resultados", "");
		Tab tTabRegisteer = new Tab("Registro", "");

		tabSet.addTab(tTabApp);
		tabSet.addTab(tTabResult);
		tabSet.addTab(tTabRegisteer);
		CheckboxItem chkaddVerApp = new CheckboxItem();
		// ListGridField lgfidApp = new ListGridField("idApp");
		ListGridField lgfNameApp = new ListGridField("appName");
		ListGridField lgfidVersion = new ListGridField("idVersion");
		ListGridField lgfVersion = new ListGridField("versionName");

		final DynamicForm formApp = new DynamicForm();
		formApp.setWidth(500);
		final TextItem txtNameCiclo = new TextItem();
		final SelectItem cbxappsReg = new SelectItem("apps");
		final SelectItem cbxapps = new SelectItem("apps");
		final SelectItem cbxVersion = new SelectItem();
		final SelectItem cbxappsResult = new SelectItem("apps");
		final SelectItem cbxVersionResult = new SelectItem();
		// DataSource dataVersion = null;
		gsa.selectApp(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				DataSource dataApp = dataAppObj.getInstance();
				// DataSource dataVersion = dataAppObjVersion.getInstance();

				cbxapps.setOptionDataSource(dataApp);
				cbxappsReg.setOptionDataSource(dataApp);
				cbxappsResult.setOptionDataSource(dataApp);
				

				// cbxVersion.setOptionDataSource(dataVersion);

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		cbxapps.setWidth(240);
		cbxapps.setTitle("Apps");
		cbxapps.setValueField("idApp");
		cbxapps.setDisplayField("appName");
		cbxapps.setName("cbxAppName");
		cbxapps.setRequired(true);

		cbxapps.setPickListFields(lgfNameApp);
		cbxapps.setPickListWidth(240);

		cbxapps.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				gsa.validarVersion("" + event.getValue(), new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) { // TODO Auto-generated

					}

					@Override
					public void onSuccess(String result) {

					//	formApp.clearValue("cbxVersionName");
						cbxVersion.clearValue();

						DataSource dataVersion = dataAppObjVersion.getInstance();
						cbxVersion.setOptionDataSource(dataVersion);
						// SC.say(dataVersion.getAttribute("idVersion"));

					}
				});
			}
		});

		cbxVersion.setWidth(240);
		cbxVersion.setName("cbxVersionName");
		cbxVersion.setTitle("Version");
		cbxVersion.setValueField("idVersion");
		cbxVersion.setDisplayField("versionName");
		cbxVersion.setPickListFields(lgfidVersion, lgfVersion);
		cbxVersion.setPickListWidth(240);
		cbxVersion.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				txtNameCiclo.setDisabled(false);

			}
		});

		/*
		 * cbxVersion.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
		 * 
		 * @Override public Criteria getCriteria(FormItemFunctionContext itemContext) {
		 * String version = (String) cbxapps.getValue(); Criteria criteria = new
		 * Criteria("idApp", version); return criteria; } });
		 */

		tTabApp.setPane(layoutApp);

		txtNameCiclo.setTitle("Nombre del ciclo");
		txtNameCiclo.setWidth(240);
		txtNameCiclo.setRequired(true);
		// txtNameCiclo.setIcons(Functions.iconTextHelp(DataSources.HELPTEXT));
		// txtNameCiclo.setShowIcons(true);
		txtNameCiclo.setDisabled(true);

		final MultiComboBoxItem mcbxmetrics = new MultiComboBoxItem();
		// mcbxmetrics.setWidth(240);
		mcbxmetrics.setUseInsertionOrder(false);
		mcbxmetrics.setName("Metricas");
		mcbxmetrics.setShowPending(true);

		// mcbxmetrics.setIcons(Functions.iconTextHelp(DataSources.HELPTEXT));
		// mcbxmetrics.setShowIcons(true);
		mcbxmetrics.setValueMap(new String[] { "Efectividad", "% Bug faltantes", "Requerimientos Funcionales",
				"Eficacia de la Eliminación de Defectos" });

		final Label label = new Label();
		label.setHeight(30);
		label.setPadding(10);
		label.setAlign(Alignment.CENTER);
		label.setValign(VerticalAlignment.CENTER);
		label.setWrap(false);

		final DynamicForm formResult = new DynamicForm();
		formResult.setWidth(500);
		VLayout layoutRsult = new VLayout(15);
		Tree tree = new Tree();
		tree.setRoot(DataSources.appRoot);

		
		// DataSource dataVersion = null;
			cbxappsResult.setWidth(240);
		cbxappsResult.setTitle("Apps");
		cbxappsResult.setValueField("idApp");
		cbxappsResult.setDisplayField("appName");
		cbxappsResult.setName("cbxAppName");
		cbxappsResult.setRequired(true);

		cbxappsResult.setPickListFields(lgfNameApp);
		cbxappsResult.setPickListWidth(240);

		cbxappsResult.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				gsa.validarVersion("" + event.getValue(), new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) { // TODO Auto-generated

					}

					@Override
					public void onSuccess(String result) {

						//formApp.clearValue("cbxVersionName");
						cbxVersionResult.clearValue();

						DataSource dataVersion = dataAppObjVersion.getInstance();
						cbxVersionResult.setOptionDataSource(dataVersion);
						// SC.say(dataVersion.getAttribute("idVersion"));

					}
				});
			}
		});

		cbxVersionResult.setWidth(240);
		cbxVersionResult.setName("cbxVersionName");
		cbxVersionResult.setTitle("Version");
		cbxVersionResult.setValueField("idVersion");
		cbxVersionResult.setDisplayField("versionName");
		cbxVersionResult.setPickListFields(lgfidVersion, lgfVersion);
		cbxVersionResult.setPickListWidth(240);
//		cbxVersionResult.addChangedHandler(new ChangedHandler() {
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				txtNameCiclo.setDisabled(false);
//
//			}
//		});

		final ListGrid resultGrid = new ListGrid();
		resultGrid.setWidth100();
		resultGrid.setHeight100();
		resultGrid.setShowAllRecords(true);
		resultGrid.setCanResizeFields(true);

		final ButtonItem btnSearchCiclos = new ButtonItem();
		btnSearchCiclos.setName("btnSearchCiclos");
		btnSearchCiclos.setTitle("Buscar Ciclo");
		btnSearchCiclos.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int idVers = Integer.parseInt(cbxVersionResult.getValues()[0]);
				//SC.say(""+idVers);
				
				gsa.reporteCiclos(1, idVers, new AsyncCallback<String[]>() {

					@Override
					public void onSuccess(String[] result) {
						String[] headColumn = result[0].split(",");
						ListGridField[] fields= new ListGridField[headColumn.length];
							for (int i = 0; i < headColumn.length; i++) {
								fields[i] = new ListGridField(headColumn[i],headColumn[i] ); 
							}
								
						resultGrid.setFields(fields);
						resultGrid.setData(createListGridRecords(result));
					//	resultGrid.refreshFields();
						

					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

			}
		});

		formResult.setItems(new FormItem[] { cbxappsResult, cbxVersionResult, btnSearchCiclos });
		layoutRsult.addMember(formResult);
		layoutRsult.addMember(resultGrid);

		tTabResult.setPane(layoutRsult);
		VLayout layoutRegister = new VLayout(15);
		final ListGrid listGrid = new ListGrid();
		final DynamicForm formRegisterApp = new DynamicForm();
		formRegisterApp.setWidth(500);

		final TextItem nameApp = new TextItem();
		nameApp.setTitle("Nombre de la App");
		nameApp.setWidth(240);
		nameApp.setRequired(true);

		final TextItem version = new TextItem();
		version.setTitle("Version");
		version.setWidth(240);
		version.setRequired(true);

		ButtonItem btnsaveApp = new ButtonItem();
		btnsaveApp.setName("btnsaveApp");
		btnsaveApp.setTitle("Registrar");

		btnsaveApp.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (formRegisterApp.validate()) {

					boolean aux = true;
					String name = "";
					String name2 = "";
					String vers = version.getDisplayValue();
					if (nameApp.isDisabled()) {

						name = cbxappsReg.getDisplayValue();

					} else if(cbxappsReg.isDisabled()) {
						name = nameApp.getDisplayValue();
						aux = false;
					}

					// SC.say(name + "-" + aux);

					gsa.saveAppVersion(name, vers, aux, new AsyncCallback<String>() {

						@Override
						public void onSuccess(String result) {

							// TODO Auto-generated method stub formRegisterApp.resetValues();
							SC.say(result);
							gsa.selectAppaVersion(new AsyncCallback<String>() {

								@Override
								public void onFailure(Throwable caught) { // TODO Auto-generated

								}

								@Override
								public void onSuccess(String result) { // DataSource dataSAppReg =
									// dataAppReg.getInstance(); //
									// listGrid.setDataSource(dataAppReg.getInstance());

									listGrid.refreshData();
									formRegisterApp.reset();
								}
							});

						}

						@Override
						public void onFailure(Throwable caught) { // TODO Auto-generated
							SC.warn(caught + "");
						}
					});

				}
			}
		});

		cbxappsReg.setWidth(240);
		cbxappsReg.setTitle("Apps");
		cbxappsReg.setValueField("appName");
		cbxappsReg.setDisplayField("idApp");
		cbxappsReg.setName("cbxAppName");
		cbxappsReg.setDisabled(true);

		cbxappsReg.setPickListFields(lgfNameApp);
		cbxappsReg.setPickListWidth(240);

		cbxappsReg.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				/*
				 * gsa.validarVersion("" + event.getValue(), new AsyncCallback<String>() {
				 * 
				 * @Override public void onFailure(Throwable caught) { // TODO Auto-generated
				 * 
				 * }
				 * 
				 * @Override public void onSuccess(String result) {
				 * 
				 * formApp.clearValue("cbxVersionName"); DataSource dataVersion =
				 * dataAppObjVersion.getInstance(); cbxVersion.setOptionDataSource(dataVersion);
				 * } });
				 */
			}
		});

		chkaddVerApp.setName("addVersion");
		chkaddVerApp.setTitle("Agregar version a una App");
		chkaddVerApp.setValue(false);
		chkaddVerApp.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				cbxappsReg.setDisabled(!((Boolean) event.getValue()));
				cbxappsReg.setRequired(true);
				nameApp.setDisabled(((Boolean) event.getValue()));
				nameApp.setRequired(false);
			}
		});

		formRegisterApp.setFields(new FormItem[] { cbxappsReg, nameApp, version, chkaddVerApp, btnsaveApp });

		layoutRegister.addMember(formRegisterApp);

		// layoutRegister.addMember(save);

		listGrid.setWidth(500);
		listGrid.setHeight100();
		listGrid.setDataSource(dataAppReg.getInstance());
		listGrid.setAutoFetchData(true);
		listGrid.setShowAllRecords(true);

		tTabRegisteer.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// SC.say("entro en tab");

				gsa.selectAppaVersion(new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(String result) {

						// DataSource dataSAppReg = dataAppReg.getInstance();
						// listGrid.setDataSource(dataAppReg.getInstance());

						listGrid.refreshData();
						;
					}
				});

				gsa.selectApp(new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						cbxappsReg.clearValue();
						DataSource dataApp = dataAppObj.getInstance();
						// DataSource dataVersion = dataAppObjVersion.getInstance();

						cbxappsReg.setOptionDataSource(dataApp);

						// cbxVersion.setOptionDataSource(dataVersion);

					}

					@Override
					public void onFailure(Throwable caught) {
						SC.warn("Hubo un erro con la Base de Datos");

					}
				});

			}
		});

		tTabApp.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				gsa.selectApp(new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						cbxapps.clearValue();
						DataSource dataApp = dataAppObj.getInstance();
						// DataSource dataVersion = dataAppObjVersion.getInstance();

						cbxapps.setOptionDataSource(dataApp);
						// cbxVersion.setOptionDataSource(dataVersion);
						cbxVersion.clearValue();

					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

			}
		});
		tTabResult.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				gsa.selectApp(new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						cbxappsResult.clearValue();
						DataSource dataApp = dataAppObj.getInstance();
						// DataSource dataVersion = dataAppObjVersion.getInstance();

						cbxappsResult.setOptionDataSource(dataApp);
						cbxVersionResult.clearValue();
						// cbxVersion.setOptionDataSource(dataVersion);

					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

			}
		});
		layoutRegister.addMember(listGrid);
//"", "", "Requerimientos Funcionales",
		// "Eficacia de la Eliminación de Defectos" }

		tTabRegisteer.setPane(layoutRegister);

		final DynamicForm formmetricsField = new DynamicForm();
		formmetricsField.setIsGroup(true);
		formmetricsField.setGroupTitle("Metricas");
		formmetricsField.setWidth100();
		formmetricsField.setPadding(3);
		formmetricsField.setTitleOrientation(TitleOrientation.LEFT);

		IntegerRangeValidator integerRangeValidator = new IntegerRangeValidator();
		integerRangeValidator.setMin(0);
		integerRangeValidator.setMax(100);

		final TextItem casesSuccess = new TextItem();
		casesSuccess.setTitle("Casos Exitosos");
		casesSuccess.setWidth(240);
		casesSuccess.hide();
		casesSuccess.setKeyPressFilter("[0-9.]");
		casesSuccess.setValidators(integerRangeValidator);

		final TextItem casesFailed = new TextItem();
		casesFailed.setTitle("Casos Fallidos");
		casesFailed.setWidth(240);
		casesFailed.hide();
		casesFailed.setKeyPressFilter("[0-9.]");
		casesFailed.setValidators(integerRangeValidator);

		final TextItem bugFound = new TextItem();
		bugFound.setTitle("Bugs Encontrados");
		bugFound.setWidth(240);
		bugFound.hide();
		bugFound.setKeyPressFilter("[0-9.]");
		bugFound.setValidators(integerRangeValidator);
		bugFound.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub

			}
		});

		final TextItem bugFixed = new TextItem();
		bugFixed.setTitle("Bugs Corregidos");
		bugFixed.setWidth(240);
		bugFixed.hide();
		bugFixed.setKeyPressFilter("[0-9.]");
		bugFixed.setValidators(integerRangeValidator);

		final TextItem porBugFixed = new TextItem();
		porBugFixed.setTitle("% Bug Corregidos");
		porBugFixed.setWidth(240);
		porBugFixed.hide();
		porBugFixed.setCanEdit(false);

		final TextItem reqComplete = new TextItem();
		reqComplete.setTitle("Requerimientos Completados");
		reqComplete.setWidth(240);
		reqComplete.hide();
		reqComplete.setKeyPressFilter("[0-9.]");
		reqComplete.setValidators(integerRangeValidator);

		final TextItem reqInComplete = new TextItem();
		reqInComplete.setTitle(" Requerimientos faltantes  ");
		reqInComplete.setWidth(240);
		reqInComplete.hide();
		reqInComplete.setKeyPressFilter("[0-9.]");
		reqComplete.setValidators(integerRangeValidator);

		final TextItem porComplete = new TextItem();
		porComplete.setTitle("% Requerimientos Funcionales");
		porComplete.setWidth(240);
		porComplete.hide();
		porComplete.setCanEdit(false);

		final TextItem errorFoundBef = new TextItem();
		errorFoundBef.setTitle("Errores encontrados antes");
		errorFoundBef.setWidth(240);
		errorFoundBef.hide();
		errorFoundBef.setKeyPressFilter("[0-9.]");
		errorFoundBef.setValidators(integerRangeValidator);

		final TextItem errorFoundAft = new TextItem();
		errorFoundAft.setTitle("Errores encontrados depues");
		errorFoundAft.setWidth(240);
		errorFoundAft.hide();
		errorFoundAft.setKeyPressFilter("[0-9.]");
		errorFoundAft.setValidators(integerRangeValidator);

		final TextItem EED = new TextItem();
		EED.setTitle("Eficacia de la Eliminacion de Defectos ");
		EED.setWidth(240);
		EED.hide();

		mcbxmetrics.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				SC.say("" + mcbxmetrics.getSelectedRecord());

			}
		});

		/*
		 * FormItem[] formItem = new FormItem[] { casesSuccess,casesFailed, porEfect,
		 * bugFound,bugFixed,porBugFixed, reqComplete,reqInComplete,
		 * porComplete,errorFoundBef, errorFoundAft, EED
		 * 
		 * };
		 * 
		 * formmetricsField.setFields(formItem);
		 */
		final CheckboxItem chkEfec = new CheckboxItem();
		chkEfec.setName("chkEfectividad");
		chkEfec.setTitle("Efectividad");
		chkEfec.setValue(false);
		chkEfec.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if ((Boolean) event.getValue()) {
					casesSuccess.show();
					casesFailed.show();

				} else {
					casesSuccess.hide();
					casesFailed.hide();

				}

			}
		});

		final CheckboxItem chkBug = new CheckboxItem();
		chkBug.setName("chkBug");
		chkBug.setTitle("Bug");
		chkBug.setValue(false);
		chkBug.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if ((Boolean) event.getValue()) {
					bugFound.show();
					bugFixed.show();
					porBugFixed.show();
				} else {
					bugFound.hide();
					bugFixed.hide();
					porBugFixed.hide();
				}

			}
		});

		final CheckboxItem chkReqFun = new CheckboxItem();
		chkReqFun.setName("chkReqFun");
		chkReqFun.setTitle("Requerimientos Funcionales");
		chkReqFun.setValue(false);
		chkReqFun.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if ((Boolean) event.getValue()) {
					reqComplete.show();
					reqInComplete.show();
					porComplete.show();
				} else {
					reqComplete.hide();
					reqInComplete.hide();
					porComplete.hide();
				}

			}
		});

		final CheckboxItem chkEDD = new CheckboxItem();
		chkEDD.setName("chkEDD");
		chkEDD.setTitle("Eficacia de la Eliminacion de Defectos");
		chkEDD.setValue(false);
		chkEDD.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if ((Boolean) event.getValue()) {
					errorFoundAft.show();
					errorFoundBef.show();
					EED.show();
				} else {
					errorFoundAft.hide();
					errorFoundBef.hide();
					EED.hide();
				}

			}
		});

		final CheckboxItem chkFE = new CheckboxItem();
		chkEDD.setName("chkEDD");
		chkEDD.setTitle("Frecuencia de errores");
		chkEDD.setValue(false);
		chkEDD.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if ((Boolean) event.getValue()) {
					errorFoundAft.show();
					errorFoundBef.show();
					EED.show();
				} else {
					errorFoundAft.hide();
					errorFoundBef.hide();
					EED.hide();
				}

			}
		});
		final ButtonItem btnSaveData = new ButtonItem();
		formApp.setFields(
				new FormItem[] { cbxapps, cbxVersion, txtNameCiclo, chkEfec, chkBug, chkReqFun, chkEDD, btnSaveData });

		final FormItem[] fiMetrics = new FormItem[] { casesSuccess, casesFailed, bugFixed, bugFound, reqComplete,
				reqInComplete, errorFoundBef, errorFoundAft };

		btnSaveData.setName("btnSaveData");
		btnSaveData.setTitle("Registrar Ciclo");
		btnSaveData.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				formApp.validate();
				formmetricsField.validate();

				String nameCiclo = txtNameCiclo.getDisplayValue();
				int idVers = Integer.parseInt(cbxVersion.getValues()[0]);

				// SC.say(idVersion+" - "+nameCiclo);
				gsa.insertCiclos(nameCiclo, idVers, new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						String aux = "";
						String nameMetric = "";
						for (int i = 0; i < fiMetrics.length; i++) {
							TextItem formItem = (TextItem) fiMetrics[i];

							if (formItem.isVisible()) {
								if (formItem.getDisplayValue().equalsIgnoreCase("")) {
									formItem.setErrors("Is Requerid");

								} /*
									 * else { aux = aux + " -" + formItem.getTitle() +
									 * " - "+formItem.getDisplayValue(); gsa.insertMetrics(formItem.getTitle(),
									 * Float.parseFloat(formItem.getDisplayValue()), new AsyncCallback<String>() {
									 * 
									 * @Override public void onSuccess(String result) { // TODO Auto-generated
									 * method stub
									 * 
									 * }
									 * 
									 * @Override public void onFailure(Throwable caught) { // TODO Auto-generated
									 * method stub
									 * 
									 * } }); }
									 */

							}
							// SC.say(aux);

						}
						if (chkEfec.getValue().equals(true)) {

							nameMetric = "Efectividad ";
							float A = Float.parseFloat(casesSuccess.getDisplayValue());
							float B = Float.parseFloat(casesFailed.getDisplayValue());
							float por = Funciones.calEfeciencia(A, B);
							gsa.insertMetrics(nameMetric, por, new AsyncCallback<String>() {

								@Override
								public void onSuccess(String result) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});
							gsa.insertMetrics("Total de casos ejecutados", A + B, new AsyncCallback<String>() {

								@Override
								public void onSuccess(String result) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});
						}
						if (chkReqFun.getValue().equals(true)) {

							float A = Float.parseFloat(reqComplete.getDisplayValue());
							float B = Float.parseFloat(reqInComplete.getDisplayValue());
							float por = Funciones.calEfeciencia(A, B);
							gsa.insertMetrics("% de Funciones Completadas", por, new AsyncCallback<String>() {

								@Override
								public void onSuccess(String result) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});
							gsa.insertMetrics("N. Total de Requerimientos Funcionales", A + B,
									new AsyncCallback<String>() {

										@Override
										public void onSuccess(String result) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}
									});
						}
						if (chkBug.getValue().equals(true)) {
							nameMetric = "% de defectos ";
							float A = Float.parseFloat(bugFixed.getDisplayValue());
							float B = Float.parseFloat(bugFound.getDisplayValue());
							float por = Funciones.calBugs(A, B);
							gsa.insertMetrics(nameMetric, por, new AsyncCallback<String>() {

								@Override
								public void onSuccess(String result) {

								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});
							gsa.insertMetrics("N. Bug Corregidos", A, new AsyncCallback<String>() {

								@Override
								public void onSuccess(String result) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});

						}
						if (chkEDD.getValue().equals(true)) {
							nameMetric = "Eficacia de la Eliminacion de Defectos";
							float E = Float.parseFloat(errorFoundBef.getDisplayValue());
							float D = Float.parseFloat(errorFoundAft.getDisplayValue());
							float por = Funciones.EED(E, D);
							gsa.insertMetrics(nameMetric, por, new AsyncCallback<String>() {

								@Override
								public void onSuccess(String result) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});

							// SC.say(aux);
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

				// formApp.reset();
				// formApp.redraw();
				// formmetricsField.cle
				SC.say("Se Registraron las Metricas");
				//formApp.reset();
				//formmetricsField.resetValues();
				
			}
		});

		/*
		 * formmetricsField.setFields(new FormItem[] { casesSuccess, casesFailed,
		 * porEfect, bugFound, bugFixed, porBugFixed, reqComplete, reqInComplete,
		 * porComplete, errorFoundBef, errorFoundAft, EED });
		 * 
		 */

		formmetricsField.setFields(fiMetrics);

		layoutApp.setMembers(formApp, formmetricsField);

		RootPanel.get("containerMain").add(tabSet);

		System.out.println("Result on post+result+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	}

	public ListGridRecord[] createListGridRecords(String[] records) {
		ListGridRecord[] result = new ListGridRecord[records.length];
		String[] fieldNames = records[0].split(",");
		for (int recordIndex = 1; recordIndex < records.length; ++recordIndex) {
			String[] fieldValues = records[recordIndex].split(",");
			result[recordIndex] = new ListGridRecord();
			for (int fieldIndex = 0; fieldIndex < fieldValues.length; ++fieldIndex) {
				result[recordIndex].setAttribute(fieldNames[fieldIndex], fieldValues[fieldIndex]);
			}
		}
		return result;
	}
	
	
	
	
}
