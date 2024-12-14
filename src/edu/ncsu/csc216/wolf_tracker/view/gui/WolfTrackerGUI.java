package edu.ncsu.csc216.wolf_tracker.view.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.wolf_tracker.model.io.ProjectReader;
import edu.ncsu.csc216.wolf_tracker.model.log.AbstractTaskLog;
import edu.ncsu.csc216.wolf_tracker.model.log.AllTasksLog;
import edu.ncsu.csc216.wolf_tracker.model.project.Project;
import edu.ncsu.csc216.wolf_tracker.model.task.Task;

/**
 * Container for the Wolf Tracker project that provides the user the ability
 * to interact with projects and logged tasks.
 * 
 * @author Dr. Sarah Heckman
 */
public class WolfTrackerGUI extends JFrame implements ActionListener {
	
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Wolf Tracker";
	/** Text for the File Menu. */
	private static final String FILE_MENU_TITLE = "File";
	/** Text for the New menu item. */
	private static final String NEW_TITLE = "New Project";
	/** Text for the Load menu item. */
	private static final String LOAD_TITLE = "Load Project";
	/** Text for the Save menu item. */
	private static final String SAVE_TITLE = "Save Project";
	/** Text for the Export Summary Statistics menu item. */
	private static final String EXPORT_SUMMARY_STATS_TITLE = "Export Summary Statistics";
	/** Text for the Quit menu item. */
	private static final String QUIT_TITLE = "Quit";
	/** Menu bar for the GUI that contains Menus. */
	private JMenuBar menuBar;
	/** Menu for the GUI. */
	private JMenu menu;
	/** Menu item for creating a new project. */
	private JMenuItem itemNew;
	/** Menu item for loading a project file. */
	private JMenuItem itemLoad;
	/** Menu item for saving a project to a file. */
	private JMenuItem itemSave;
	/** Menu item for exporting summary statistics. */
	private JMenuItem itemExportSummaryStats;
	/** Menu item for quitting the program. */
	private JMenuItem itemQuit;
	
	/** JPanel for project */
	private ProjectPanel pnlProject;
	/** JPanel for category info */
	private CategoryPanel pnlCategory;
	/** JPanel for summary stats for a category */
	private StatsPanel pnlStats;
	/** JPanel for Task */
	private TaskPanel pnlTask;
	/** JPanel for Logged Tasks */
	private LoggedTasksPanel pnlLoggedTasks;
	
	
	/** Current project - null if no project created. */
	private Project project;
	
	/**
	 * Constructs a WolfTrackerGUI object that will contain a JMenuBar and a
	 * JPanel that will hold different possible views for the Wolf Tracker application.
	 */
	public WolfTrackerGUI() {
		super();
		
		//Set up general GUI info
		setSize(1200, 700);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpMenuBar();
		
		//Add panel to the container
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());
		
		pnlProject = new ProjectPanel();
		TitledBorder projectBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Project");
		pnlProject.setBorder(projectBorder);
		pnlProject.setToolTipText("Project");
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.BOTH;
		
		c.add(pnlProject, constraints);
		
		//Set the GUI visible
		setVisible(true);
	}
	
	/**
	 * Makes the GUI Menu bar that contains options working with logged 
	 * tasks, exporting summary stats, and quitting the application.
	 */
	private void setUpMenuBar() {
		//Construct Menu items
		menuBar = new JMenuBar();
		menu = new JMenu(FILE_MENU_TITLE);
		itemNew = new JMenuItem(NEW_TITLE);
		itemLoad = new JMenuItem(LOAD_TITLE);
		itemSave = new JMenuItem(SAVE_TITLE);
		itemExportSummaryStats = new JMenuItem(EXPORT_SUMMARY_STATS_TITLE);
		itemQuit = new JMenuItem(QUIT_TITLE);
		itemNew.addActionListener(this);
		itemLoad.addActionListener(this);
		itemSave.addActionListener(this);
		itemExportSummaryStats.addActionListener(this);
		itemQuit.addActionListener(this);
		
		//Start with save button disabled
		itemSave.setEnabled(false);
		
		//Build Menu and add to GUI
		menu.add(itemNew);
		menu.add(itemLoad);
		menu.add(itemSave);
		menu.add(itemExportSummaryStats);
		menu.add(itemQuit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	/**
	 * Performs actions when user interacts with GUI components.
	 * @param e ActionEvent associated with the user action
	 */
	@Override
	public void actionPerformed(ActionEvent e) { 
		if (e.getSource() == itemNew) {
			if (project != null && project.isChanged()) {
				int select = JOptionPane.showConfirmDialog(null, "Current Project is unsaved. Would you like to save before creating a new Project?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (select == 1) {
					promptForName();
				} 
			} else {
				promptForName();
			}
		} else if (e.getSource() == itemLoad) {
			try {
				if (project != null && project.isChanged()) {
					int select = JOptionPane.showConfirmDialog(null, "Current Project is unsaved. Would you like to save before creating a new Project?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (select == 1) {
						project = ProjectReader.readProjectFile(new File(getFileName(true)));
					}
				} else {
					project = ProjectReader.readProjectFile(new File(getFileName(true)));
				}
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, "Unable to load file.");
			} catch (IllegalStateException ise) {
				//ignore the exception
			}
		} else if (e.getSource() == itemSave) {
			//Save current project
			try {
				project.saveProject(new File(getFileName(false)));
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to save file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		} else if (e.getSource() == itemExportSummaryStats) {
			try {
				project.saveStats(new File(getFileName(false)));
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to save file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		} else if (e.getSource() == itemQuit) {
			if (project != null && project.isChanged()) {
				//Quit the program
				try {
					project.saveProject(new File(getFileName(false)));
					System.exit(0);  //Ignore SpotBugs warning here - this is the only place to quit the program!
				} catch (IllegalArgumentException exp) {
					JOptionPane.showMessageDialog(this, "Unable to save file.");
				} catch (IllegalStateException exp) {
					//Don't do anything - user canceled (or error)
				}
			} else {
				System.exit(0);
			}
		}
		
		itemSave.setEnabled(project != null && project.isChanged());
		
		String projectName = "";
		if (project != null) {
			projectName = project.getProjectName();
		}
		
		pnlProject.setProjectName(projectName);
		pnlCategory.updateCategories();
		pnlLoggedTasks.updateLoggedTasks(false);
		pnlStats.updateStats();
		
		repaint();
		validate();
		
	}
	
	/**
	 * Prompts the user for the project name.
	 */
	private void promptForName() {
		String projectName = (String) JOptionPane.showInputDialog(this, "Project Name?");
		if (projectName == null) {
			return; //no need to do anything
		}
		project = new Project(projectName);
	}
	
	/**
	 * Returns a file name generated through interactions with a JFileChooser
	 * object.
	 * @param load true if loading a file, false if saving
	 * @return the file name selected through JFileChooser
	 * @throws IllegalStateException if no file name provided
	 */
	private String getFileName(boolean load) {
		JFileChooser fc = new JFileChooser("./");  //Open JFileChooser to current working directory
		int returnVal = Integer.MIN_VALUE;
		if (load) {
			returnVal = fc.showOpenDialog(this);
		} else {
			returnVal = fc.showSaveDialog(this);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//Error or user canceled, either way no file name.
			throw new IllegalStateException();
		}
		File filename = fc.getSelectedFile();
		return filename.getAbsolutePath();
	}
	
	/**
	 * Starts the GUI for the WolfTracker application.
	 * @param args command line arguments
	 */
	public static void main(String [] args) {
		new WolfTrackerGUI();
	}
	
	/**
	 * JPanel for the project, category, stats, and tasks.
	 */
	private class ProjectPanel extends JPanel {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Project name label */
		private JLabel lblProjectName;
		
		/** Constant for project name label */
		private static final String PROJECT_NAME_PROMPT = "Project Name: ";
		
		public ProjectPanel() {
			setLayout(new GridBagLayout());
			
			lblProjectName = new JLabel(PROJECT_NAME_PROMPT);
			
			pnlCategory = new CategoryPanel();
			TitledBorder categoryBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Category");
			pnlCategory.setBorder(categoryBorder);
			pnlCategory.setToolTipText("Category");
			
			pnlStats = new StatsPanel();
			TitledBorder statsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Summary Statistics");
			pnlStats.setBorder(statsBorder);
			pnlStats.setToolTipText("Summary Statistics");
			
			pnlTask = new TaskPanel();
			TitledBorder taskBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Task");
			pnlTask.setBorder(taskBorder);
			pnlTask.setToolTipText("Task");
			
			pnlLoggedTasks = new LoggedTasksPanel();
			TitledBorder loggedTasksBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Logged Tasks");
			pnlLoggedTasks.setBorder(loggedTasksBorder);
			pnlLoggedTasks.setToolTipText("Logged Tasks");
			
			
			GridBagConstraints constraints = new GridBagConstraints();
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = .5;
			constraints.weighty = .05;
			constraints.anchor = GridBagConstraints.LINE_START;
			constraints.fill = GridBagConstraints.NONE;
			
			add(lblProjectName, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.weightx = .5;
			constraints.weighty = .31;
			constraints.anchor = GridBagConstraints.LINE_START;
			constraints.fill = GridBagConstraints.BOTH;
			add(pnlCategory, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.weightx = .5;
			constraints.weighty = .32;
			constraints.anchor = GridBagConstraints.LINE_START;
			constraints.fill = GridBagConstraints.BOTH;
			add(pnlStats, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 3;
			constraints.weightx = .5;
			constraints.weighty = .32;
			constraints.anchor = GridBagConstraints.LINE_START;
			constraints.fill = GridBagConstraints.BOTH;
			add(pnlTask, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 0;
			constraints.weightx = .5;
			constraints.weighty = 1;
			constraints.gridheight = 4;
			constraints.anchor = GridBagConstraints.LINE_START;
			constraints.fill = GridBagConstraints.BOTH;
			add(pnlLoggedTasks, constraints);
		}
		
		/**
		 * Sets the project name.
		 * @param projectName name to set
		 */
		public void setProjectName(String projectName) {
			lblProjectName.setText(PROJECT_NAME_PROMPT + projectName);
		}

	}
	
	/**
	 * JPanel for Categories.
	 */
	private class CategoryPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Label for selecting current task log */
		private JLabel lblCurrentCategory;
		/** Combo box for categories */
		private JComboBox<String> comboCategoryList;
		
		/** Button to add a category*/
		private JButton btnAddCategory;
		/** Button to edit the selected category */
		private JButton btnEditCategory;
		/** Button to remove the selected category */
		private JButton btnRemoveCategory;
		
		/**
		 * Constructs the category panel
		 */
		public CategoryPanel() {
			setLayout(new GridBagLayout());
			
			lblCurrentCategory = new JLabel("Current Category");
			comboCategoryList = new JComboBox<String>();
			comboCategoryList.addActionListener(this);
			
			btnAddCategory = new JButton("Add Category");
			btnEditCategory = new JButton("Edit Category");
			btnRemoveCategory = new JButton("Remove Category");
			
			btnAddCategory.addActionListener(this);
			btnEditCategory.addActionListener(this);
			btnRemoveCategory.addActionListener(this);
			
			JPanel pnlCategoryInfo = new JPanel();
			pnlCategoryInfo.setLayout(new GridLayout(1, 2));
			pnlCategoryInfo.add(lblCurrentCategory);
			pnlCategoryInfo.add(comboCategoryList);
			
			JPanel pnlCategoryActions = new JPanel();
			pnlCategoryActions.setLayout(new GridLayout(1, 3));
			pnlCategoryActions.add(btnAddCategory);
			pnlCategoryActions.add(btnEditCategory);
			pnlCategoryActions.add(btnRemoveCategory);
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = .5;
			c.weighty = .5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlCategoryInfo, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = .5;
			c.weighty = .5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlCategoryActions, c);
		}

		/**
		 * Performs actions when user interacts with GUI components.
		 * @param e ActionEvent associated with the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboCategoryList) {
				int idx = comboCategoryList.getSelectedIndex();
				
				if (idx != -1) {
					String logName = comboCategoryList.getItemAt(idx);
					project.setCurrentTaskLog(logName);
				}
			} else if (e.getSource() == btnAddCategory) {
				try {
					String categoryName = (String) JOptionPane.showInputDialog(this, "Category Name?", "Create new Category", JOptionPane.QUESTION_MESSAGE);
					project.addCategoryLog(categoryName);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTrackerGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnEditCategory) {
				try {
					if (project.getCurrentLog() instanceof AllTasksLog) {
						JOptionPane.showMessageDialog(WolfTrackerGUI.this, "The All Tasks Log may not be edited");
					} else {
						String categoryName = (String) JOptionPane.showInputDialog(this, "Update Category Name?", "Edit Category", JOptionPane.QUESTION_MESSAGE, null, null, project.getCurrentLog().getName());
						project.editCategoryLogName(categoryName);
					}
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTrackerGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnRemoveCategory) {
				try {
					if (project.getCurrentLog() instanceof AllTasksLog) {
						JOptionPane.showMessageDialog(WolfTrackerGUI.this, "The All Tasks Log may not be edited");
					} else {
						int selection = JOptionPane.showOptionDialog(this, "Are you sure you want to delete " + project.getCurrentLog().getName() + "?", "Delete Category", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (selection == 0) { //Yes
							project.removeCategoryLog();
						}
					}
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTrackerGUI.this, iae.getMessage());
				}
			}
			
			itemSave.setEnabled(project != null && project.isChanged());
						
			pnlCategory.updateCategories();
			pnlLoggedTasks.updateLoggedTasks(false);
			pnlStats.updateStats();
			pnlTask.setTask(-1);
			
			WolfTrackerGUI.this.repaint();
			WolfTrackerGUI.this.validate();
		}
		
		/**
		 * Updates the category list combo box.
		 */
		public void updateCategories() {
			if (project != null) {
				comboCategoryList.removeAllItems();
				
				String currentCategory = project.getCurrentLog().getName();
				
				int selectionIdx = 0;
				String [] categoryNames = project.getCategoryNames();
				for (int i = 0; i < categoryNames.length; i++) {
					comboCategoryList.addItem(categoryNames[i]);
					if (currentCategory.equals(categoryNames[i])) {
						selectionIdx = i;
					}
				}
				
				comboCategoryList.setSelectedIndex(selectionIdx);
				
				pnlTask.enableButtons(selectionIdx > 0);
			}
		}
		
		/**
		 * Enable or disable all buttons 
		 * @param enable true if enable, false if disabled
		 */
		public void enableButtons(boolean enable) {
			btnAddCategory.setEnabled(enable); 
			btnEditCategory.setEnabled(enable);
			btnRemoveCategory.setEnabled(enable);
		}
	}
	
	/**
	 * JPanel for summary stats for a category.
	 */
	private class StatsPanel extends JPanel {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Label for number of logged tasks */
		private JLabel lblNumLoggedTasks;
		/** Label for min duration */
		private JLabel lblMinDuration;
		/** Label for max duration */
		private JLabel lblMaxDuration;
		/** Label for average duration */
		private JLabel lblAvgDuration;
		
		/** Constant for leading string info for number of logged tasks */
		private static final String NUM_LOGGED_TASKS = "Number of Logged Tasks: ";
		/** Constant for leading string info for min duration */
		private static final String MIN_DURATION = "Min Duration: ";
		/** Constant for leading string info for max duration */
		private static final String MAX_DURATION = "Max Duration: ";
		/** Constant for leading string info for average duration */
		private static final String AVG_DURATION = "Average Duration: ";
		
		/**
		 * Constructs the Stats Panel.
		 */
		public StatsPanel() {
			setLayout(new GridBagLayout());
			
			lblNumLoggedTasks = new JLabel(NUM_LOGGED_TASKS);
			lblMinDuration = new JLabel(MIN_DURATION);
			lblMaxDuration = new JLabel(MAX_DURATION);
			lblAvgDuration = new JLabel(AVG_DURATION);
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(lblNumLoggedTasks, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(lblMinDuration, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(lblMaxDuration, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(lblAvgDuration, c);
			
		}
		
		/**
		 * Updates the stats from the current task log.
		 */
		public void updateStats() {
			if (project != null && project.getCurrentLog() != null) {
				AbstractTaskLog log = project.getCurrentLog();
				int numLoggedTasks = log.getTaskCount();
				int minDuration = log.getMinDuration();
				int maxDuration = log.getMaxDuration();
				double avgDuration = log.getAvgDuration();
				
				if (numLoggedTasks > 0) {
					lblNumLoggedTasks.setText(NUM_LOGGED_TASKS + numLoggedTasks);
					lblMinDuration.setText(MIN_DURATION + minDuration);
					lblMaxDuration.setText(MAX_DURATION + maxDuration);
					lblAvgDuration.setText(AVG_DURATION + avgDuration);
				} else {
					lblNumLoggedTasks.setText(NUM_LOGGED_TASKS + numLoggedTasks);
					lblMinDuration.setText(MIN_DURATION);
					lblMaxDuration.setText(MAX_DURATION);
					lblAvgDuration.setText(AVG_DURATION);
				}
			}
		}
	}
	
	/**
	 * JPanel for Tasks.
	 */
	private class TaskPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Label - task title */
		private JLabel lblTaskTitle;
		/** TextField - task title */
		private JTextField txtTaskTitle;
		
		/** Label - task duration */
		private JLabel lblDuration;
		/** TextField - task duration */
		private JTextField txtDuration;	
				
		/** Label - description */
		private JLabel lblDescription;
		/** Text Area - description */
		private JTextArea txtDescription;
		
		/** Button - add/edit */
		private JButton btnAddEdit;
		/** Button - remove */
		private JButton btnRemove;
		
		/**
		 * Constructs the panel for task information.
		 */
		public TaskPanel() {
			setLayout(new GridBagLayout());
			
			lblTaskTitle = new JLabel("Task Title");
			txtTaskTitle = new JTextField(25);
			
			lblDuration = new JLabel("Duration");
			txtDuration = new JTextField(25);
			
			lblDescription = new JLabel("Description");
			txtDescription = new JTextArea(10, 50);
			
			btnAddEdit = new JButton("Add / Edit");
			btnRemove = new JButton("Remove");
			
			btnAddEdit.addActionListener(this);
			btnRemove.addActionListener(this);
			
			enableButtons(false);
			
			JScrollPane scrollDescription = new JScrollPane(txtDescription);
			scrollDescription.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
						
			GridBagConstraints c = new GridBagConstraints();
			
			JPanel pnlTitle = new JPanel();
			pnlTitle.setLayout(new GridLayout(1, 2));
			pnlTitle.add(lblTaskTitle);
			pnlTitle.add(txtTaskTitle);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = .5;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlTitle, c);
			
			JPanel pnlDuration = new JPanel();
			pnlDuration.setLayout(new GridLayout(1, 2));
			pnlDuration.add(lblDuration);
			pnlDuration.add(txtDuration);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = .5;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlDuration, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = .5;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(lblDescription, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = .5;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = GridBagConstraints.REMAINDER;
			add(txtDescription, c);
			
			JPanel pnlButtons = new JPanel();
			pnlButtons.setLayout(new GridLayout(1, 2));
			pnlButtons.add(btnAddEdit);
			pnlButtons.add(btnRemove);
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = .5;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlButtons, c);
			
		}

		/**
		 * Performs actions when user interacts with GUI components.
		 * @param e ActionEvent associated with the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int idx = pnlLoggedTasks.tableTasks.getSelectedRow();
			
			if (e.getSource() == btnAddEdit) {
				try {
					if (idx == -1) {
						Task t = new Task(txtTaskTitle.getText(), Integer.parseInt(txtDuration.getText()), txtDescription.getText());
						project.addTask(t);
					} else {
						project.editTask(idx, txtTaskTitle.getText(), Integer.parseInt(txtDuration.getText()), txtDescription.getText());
					}
					setTask(-1);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTrackerGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnRemove) {
				try {
					project.removeTask(idx);
					setTask(-1);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfTrackerGUI.this, iae.getMessage());
				} catch (IndexOutOfBoundsException ioobe) {
					JOptionPane.showMessageDialog(WolfTrackerGUI.this, "No task selected.");
				}
			}
			itemSave.setEnabled(project != null && project.isChanged());
			pnlLoggedTasks.updateLoggedTasks(false);
			pnlStats.updateStats();
			
			WolfTrackerGUI.this.repaint();
			WolfTrackerGUI.this.validate();
		}
		
		/**
		 * Sets the information for the selected task and enables the buttons.
		 * @param idx index of selected task
		 */
		public void setTask(int idx) {
			try {
				Task t = project.getCurrentLog().getTask(idx);
				
				txtTaskTitle.setText(t.getTaskTitle());
				txtDuration.setText("" + t.getTaskDuration());
				txtDescription.setText(t.getTaskDetails());
				
				enableButtons(true);
			} catch (IndexOutOfBoundsException e) {
				txtTaskTitle.setText("");
				txtDuration.setText("");
				txtDescription.setText("");
				
				pnlLoggedTasks.tableTasks.getSelectionModel().clearSelection();
			}
		}
		
		/**
		 * Enable or disable all buttons 
		 * @param enable true if enable, false if disabled
		 */
		public void enableButtons(boolean enable) {
			btnAddEdit.setEnabled(enable); 
			btnRemove.setEnabled(enable);
		}

	}
	
	/**
	 * JPanel for Logged Tasks.
	 */
	private class LoggedTasksPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** JTable for displaying the task logs */
		private JTable tableTasks;
		/** TableModel for Logged Tasks */
		private TaskTableModel tableModel;
		/** Button for listing mostly recently logged tasks for each category */
		private JButton btnMostRecent;
		/** True if looking at most recent tasks */
		private boolean isMostRecent;

		/**
		 * Constructs the logged tasks panel
		 */
		public LoggedTasksPanel() {
			setLayout(new GridBagLayout());			
			
			tableModel = new TaskTableModel();
			tableTasks = new JTable(tableModel);
			tableTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableTasks.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tableTasks.setFillsViewportHeight(true);
			tableTasks.getColumnModel().getColumn(0).setPreferredWidth(25);
			tableTasks.getColumnModel().getColumn(1).setPreferredWidth(15);
			tableTasks.getColumnModel().getColumn(1).setPreferredWidth(35);
			tableTasks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!isMostRecent) {
						int idx = tableTasks.getSelectedRow();
						pnlTask.setTask(idx);
					}
				}
				
			});
			
			btnMostRecent = new JButton("Most Recent Logged Tasks");
			btnMostRecent.addActionListener(this);
			
			JScrollPane listScrollPane = new JScrollPane(tableTasks);
			listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			GridBagConstraints c = new GridBagConstraints();
						
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 20;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			add(listScrollPane, c);
			
			c.gridx = 0;
			c.gridy = 10;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(btnMostRecent, c);
		}
		
		/**
		 * Performs actions when user interacts with GUI components.
		 * @param e ActionEvent associated with the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnMostRecent) {
				updateLoggedTasks(true);
				pnlTask.enableButtons(false);
				pnlTask.setTask(-1);
			}
			WolfTrackerGUI.this.repaint();
			WolfTrackerGUI.this.validate();
		}
		
		
		/**
		 * Updates the logged task information
		 * @param isMostRecent true if most recent tasks are requested
		 */
		public void updateLoggedTasks(boolean isMostRecent) {
			this.isMostRecent = isMostRecent;
			pnlCategory.enableButtons(project != null);
			tableModel.updateData(isMostRecent);
		}
		
		/**
		 * TaskTableModel is the object underlying the JTable object that displays
		 * the list of logged Tasks to the user.
		 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
		 */
		private class TaskTableModel extends AbstractTableModel {
			
			/** ID number used for object serialization. */
			private static final long serialVersionUID = 1L;
			/** Column names for the table */
			private String [] columnNames = {"Task Title", "Duration", "Category"};
			/** Data stored in the table */
			private Object [][] data;
			
			/**
			 * Constructs the IncidentTableModel by requesting the latest information
			 * from the IncidentTableModel.
			 */
			public TaskTableModel() {
				updateData(false);
			}

			/**
			 * Returns the number of columns in the table.
			 * @return the number of columns in the table.
			 */
			public int getColumnCount() {
				return columnNames.length;
			}

			/**
			 * Returns the number of rows in the table.
			 * @return the number of rows in the table.
			 */
			public int getRowCount() {
				if (data == null) 
					return 0;
				return data.length;
			}
			
			/**
			 * Returns the column name at the given index.
			 * @param col the column index
			 * @return the column name at the given column.
			 */
			public String getColumnName(int col) {
				return columnNames[col];
			}

			/**
			 * Returns the data at the given {row, col} index.
			 * @param row the row index
			 * @param col the column index
			 * @return the data at the given location.
			 */
			public Object getValueAt(int row, int col) {
				if (data == null)
					return null;
				return data[row][col];
			}
			
			/**
			 * Sets the given value to the given {row, col} location.
			 * @param value Object to modify in the data.
			 * @param row the row index
			 * @param col the column index
			 */
			public void setValueAt(Object value, int row, int col) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
			
			/**
			 * Updates the given model with Tasks information from the current log.
			 * @param isMostRecent true if requesting most recent logged tasks
			 */
			private void updateData(boolean isMostRecent) {
				if (project != null) {
					if (isMostRecent) {
						data = project.getMostRecentTasks();
					} else {
						AbstractTaskLog currentLoggedTasks = project.getCurrentLog();
						data = currentLoggedTasks.getTasksAsArray();
					}
				}
			}
		}
	}
	
}