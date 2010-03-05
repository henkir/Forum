package com.forum.client.admin;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.ListGridEditEvent;
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
	private AsyncCallback<AdminCategory[]> callbackCategories;

	/**
	 * Creates a new CategoryListGrid that uses the given service to communicate
	 * with the server.
	 * 
	 * @param adminService
	 *            the service to use
	 */
	public CategoryListGrid(AdminServiceAsync adminService) {
		super();
		this.adminSvc = adminService;

		createCallbacks();
		init();
		getCategories();
	}

	/**
	 * Initializes the component, setting various properties.
	 */
	private void init() {
		setWidth(500);
		setHeight(250);
		setCanEdit(true);
		setEditByCell(true);
		setEditEvent(ListGridEditEvent.DOUBLECLICK);
		setEmptyMessage("Loading...");
		setCanReorderRecords(true);
		ListGridField categoryField = new ListGridField("categoryName", "Name",
				100);
		ListGridField descriptionField = new ListGridField(
				"categoryDescription", "Description", 300);
		setFields(categoryField, descriptionField);
	}

	/**
	 * Get all categories.
	 */
	public void getCategories() {
		adminSvc.getCategories(callbackCategories);
	}

	/**
	 * Create the callbacks used.
	 */
	private void createCallbacks() {
		callbackCategories = new AsyncCallback<AdminCategory[]>() {

			@Override
			public void onSuccess(AdminCategory[] result) {
				setEmptyMessage("No categories");
				ListGridRecord record;
				for (AdminCategory category : result) {
					record = new ListGridRecord();
					record.setAttribute("categoryName", category.getName());
					record.setAttribute("categoryDescription", category
							.getDescription());
					addData(record);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				setEmptyMessage("Failed getting categories");
				System.out.println("Failure3: " + caught.getMessage());
			}
		};
	}
}
