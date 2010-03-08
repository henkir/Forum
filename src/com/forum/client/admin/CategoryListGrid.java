package com.forum.client.admin;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * A subclass of ListGrid specialized in displaying categories and allowing the
 * user to edit, add and delete categories.
 * 
 * @author henrik
 * 
 */
public class CategoryListGrid extends ListGrid {

	/**
	 * Admin service connection.
	 */
	private AdminServiceAsync adminSvc;

	/**
	 * Callback for getting all categories.
	 */
	private AsyncCallback<AdminCategory[]> callbackGetCategories;
	/**
	 * Callback for setting position of a category.
	 */
	private AsyncCallback<Boolean> callbackSetCategories;
	private String sid;

	/**
	 * Creates a new CategoryListGrid that uses the given service to communicate
	 * with the server.
	 * 
	 * @param adminService
	 *            the service to use
	 */
	public CategoryListGrid(AdminServiceAsync adminService, String sid) {
		super();
		this.adminSvc = adminService;
		this.sid = sid;

		createCallbacks();
		init();
		getCategories();
	}

	/**
	 * Create the callbacks used.
	 */
	private void createCallbacks() {
		callbackGetCategories = new AsyncCallback<AdminCategory[]>() {

			@Override
			public void onFailure(Throwable caught) {
				setEmptyMessage("Failed getting categories");
				System.out.println("Failure3: " + caught.getMessage());
			}

			@Override
			public void onSuccess(AdminCategory[] result) {
				if (result != null) {
					setEmptyMessage("No categories");
					selectAllRecords();
					removeSelectedData();
					ListGridRecord record;
					for (AdminCategory category : result) {
						record = new ListGridRecord();
						record.setAttribute("categoryId", category.getId());
						record.setAttribute("categoryName", category.getName());
						record.setAttribute("categoryDescription", category
								.getDescription());
						addData(record);
					}
				} else {
					setEmptyMessage("Failed getting categories.");
				}
			}
		};

		callbackSetCategories = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Update failure",
						"Failed to update order of categories.");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (!result) {
					adminSvc.getCategories(sid, callbackGetCategories);
					SC.say("Update failure", "Failed to update categories.");
				} else {
					SC.say("Update successful",
							"Successfully updated categories.");
				}
			}
		};
	}

	/**
	 * Get all categories.
	 */
	public void getCategories() {
		adminSvc.getCategories(sid, callbackGetCategories);
	}

	/**
	 * Initializes the component, setting various properties.
	 */
	private void init() {
		setWidth(500);
		setHeight(250);
		setCanRemoveRecords(true);

		setCanEdit(true);
		setEditByCell(true);
		setEditEvent(ListGridEditEvent.DOUBLECLICK);
		setEmptyMessage("Loading...");
		setCanReorderRecords(true);
		ListGridField idField = new ListGridField("categoryId");
		idField.setHidden(true);
		ListGridField categoryField = new ListGridField("categoryName", "Name",
				100);
		ListGridField descriptionField = new ListGridField(
				"categoryDescription", "Description", 300);
		setFields(categoryField, descriptionField);
		categoryField.setValidateOnChange(true);
		LengthRangeValidator categoryValidator = new LengthRangeValidator();
		categoryValidator.setMin(3);
		categoryValidator.setMax(20);
		categoryField.setValidators(categoryValidator);

		LengthRangeValidator descriptionValidator = new LengthRangeValidator();
		descriptionValidator.setMin(0);
		descriptionValidator.setMax(100);
		descriptionField.setValidators(descriptionValidator);

	}

	/**
	 * Saves the categories as they currently are.
	 */
	public void saveCategories() {
		ListGridRecord[] records = getRecords();
		AdminCategory[] cats = new AdminCategory[records.length];
		String name;
		String description;
		int id;
		for (int i = 0; i < records.length; i++) {
			id = records[i].getAttributeAsInt("categoryId");
			name = records[i].getAttribute("categoryName");
			description = records[i].getAttribute("categoryDescription");
			cats[i] = new AdminCategory(id, name, description);
		}
		adminSvc.setCategories(cats, sid, callbackSetCategories);
	}

}
