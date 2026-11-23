package com.mycompany.academictaskorganizer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AcademicTaskOrganizer extends JFrame implements ActionListener {

    private JPanel main, details, fields, buttons, tasksPanel;
    private JLabel l1, l2, l3, l4;
    private JTextField title, course;
    private JComboBox<String> priority, status;
    private JButton addButton, updateButton;
    private JButton deleteButton, clearButton;
    private JButton saveButton, loadButton;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scroll;

    private int nextId = 1;

    public AcademicTaskOrganizer() {

        setTitle("Academic Task Organizer");
        setSize(720, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main = new JPanel();
        details = new JPanel();
        fields = new JPanel();
        buttons = new JPanel();
        tasksPanel = new JPanel();

        main.setLayout(new BorderLayout());
        details.setLayout(new BorderLayout());
        fields.setLayout(new GridLayout(4, 2, 8, 8));
        buttons.setLayout(new GridLayout(3, 2, 5, 5));
        tasksPanel.setLayout(new BorderLayout());

        // LABELS

        l1 = new JLabel("Task Title:");
        l2 = new JLabel("Course Code:");
        l3 = new JLabel("Priority:");
        l4 = new JLabel("Status:");

        // TEXT FIELDS

        title = new JTextField(15);
        course = new JTextField(15);

        // COMBO BOXES

        String[] priorities = {"High", "Medium", "Low"};

        String[] statuses = {
            "Pending", "In Progress", "Completed"
        };

        priority = new JComboBox<>(priorities);
        status = new JComboBox<>(statuses);

        fields.add(l1);
        fields.add(title);

        fields.add(l2);
        fields.add(course);

        fields.add(l3);
        fields.add(priority);

        fields.add(l4);
        fields.add(status);

     

        addButton = new JButton("Add Task");
        updateButton = new JButton("Update Task");
        deleteButton = new JButton("Delete Task");
        clearButton = new JButton("Clear Fields");
        saveButton = new JButton("Save to File");
        loadButton = new JButton("Load from File");

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(clearButton);
        buttons.add(saveButton);
        buttons.add(loadButton);

       

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);



        saveButton.addActionListener(this);
        loadButton.addActionListener(this);

    

        details.add(fields, BorderLayout.CENTER);
        details.add(buttons, BorderLayout.EAST);

        details.setPreferredSize(new Dimension(700, 190));


        String[] columns = {
            "ID",
            "Task Title",
            "Course Code",
            "Priority",
            "Status"
        };

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        scroll = new JScrollPane(table);

        tasksPanel.add(scroll, BorderLayout.CENTER);

        main.add(details, BorderLayout.NORTH);
        main.add(tasksPanel, BorderLayout.CENTER);

        add(main);

        setVisible(true);
    }

   

    @Override
    public void actionPerformed(ActionEvent e) {

      

        if (e.getSource() == addButton) {

            String taskTitle = title.getText().trim();
            String courseCode = course.getText().trim();

            if (taskTitle.isEmpty() || courseCode.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please enter Task Title and Course Code");

            } else if (taskTitle.contains("|") || courseCode.contains("|")
                    || taskTitle.contains("\n") || courseCode.contains("\n")
                    || taskTitle.contains("\r") || courseCode.contains("\r")) {

                JOptionPane.showMessageDialog(this,
                        "Invalid characters in input");

            } else {

                String p = priority.getSelectedItem().toString();
                String s = status.getSelectedItem().toString();

                model.addRow(new Object[]{
                    nextId, taskTitle, courseCode, p, s
                });

                nextId++;
            }
        }

  

        else if (e.getSource() == updateButton) {

            int row = table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(this,
                        "Please select a task to update");

            } else {

                String taskTitle = title.getText().trim();
                String courseCode = course.getText().trim();

                if (taskTitle.isEmpty() || courseCode.isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Please enter Task Title and Course Code");

                } else if (taskTitle.contains("|") || courseCode.contains("|")
                        || taskTitle.contains("\n") || courseCode.contains("\n")
                        || taskTitle.contains("\r") || courseCode.contains("\r")) {

                    JOptionPane.showMessageDialog(this,
                            "Invalid characters in input");

                } else {

                    model.setValueAt(taskTitle, row, 1);
                    model.setValueAt(courseCode, row, 2);

                    model.setValueAt(priority.getSelectedItem(), row, 3);
                    model.setValueAt(status.getSelectedItem(), row, 4);
                }
            }
        }

      
        else if (e.getSource() == deleteButton) {

            int row = table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(this,
                        "Please select a task to delete");

            } else {

                int answer = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this task?",
                        "Delete Task",
                        JOptionPane.YES_NO_OPTION
                );

                if (answer == JOptionPane.YES_OPTION) {

                    model.removeRow(row);
                }
            }
        }


        else if (e.getSource() == clearButton) {

            title.setText("");
            course.setText("");

            priority.setSelectedIndex(0);
            status.setSelectedIndex(0);

            table.clearSelection();
        }


        else if (e.getSource() == saveButton) {

            saveTasks();
        }

       

        else if (e.getSource() == loadButton) {

            loadTasks();
        }
    }

   

    public void saveTasks() {

        try {

            BufferedWriter file =
                    new BufferedWriter(new FileWriter("tasks.txt"));

            for (int i = 0; i < model.getRowCount(); i++) {

                String line = "";

                for (int j = 0; j < model.getColumnCount(); j++) {

                    line += model.getValueAt(i, j).toString();

                    if (j < model.getColumnCount() - 1) {
                        line += "|";
                    }
                }

                file.write(line);
                file.newLine();
            }

            file.close();

            JOptionPane.showMessageDialog(this,
                    "Tasks saved successfully");

        }

    
        catch (IOException e) {

            JOptionPane.showMessageDialog(this,
                    "Error saving tasks: " + e.getMessage());
        }
    }

 

    public void loadTasks() {

        try {

            BufferedReader file =
                    new BufferedReader(new FileReader("tasks.txt"));

            model.setRowCount(0);
            nextId = 1;

            String line;

            while ((line = file.readLine()) != null) {

                String[] data = line.split("\\|", -1);

                if (data.length == 5) {

                    try {

                        int id = Integer.parseInt(data[0]);

                        if (id <= 0
                                || data[1].trim().isEmpty()
                                || data[2].trim().isEmpty()) {
                            continue;
                        }

                        boolean validPriority =
                                data[3].equals("High")
                                || data[3].equals("Medium")
                                || data[3].equals("Low");

                        boolean validStatus =
                                data[4].equals("Pending")
                                || data[4].equals("In Progress")
                                || data[4].equals("Completed");

                        if (!validPriority || !validStatus) {
                            continue;
                        }

                        boolean duplicate = false;

                        for (int i = 0; i < model.getRowCount(); i++) {

                            int oldId =
                                    Integer.parseInt(
                                            model.getValueAt(i, 0).toString()
                                    );

                            if (oldId == id) {
                                duplicate = true;
                            }
                        }

                        if (!duplicate) {

                            model.addRow(new Object[]{
                                id,
                                data[1],
                                data[2],
                                data[3],
                                data[4]
                            });

                            if (id >= nextId) {
                                nextId = id + 1;
                            }
                        }

                    }

        
                    catch (NumberFormatException e) {

                    }
                }
            }

            file.close();

            JOptionPane.showMessageDialog(this,
                    "Tasks loaded successfully");

        }



        catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(this,
                    "File tasks.txt not found");

        } catch (IOException e) {

            JOptionPane.showMessageDialog(this,
                    "Error loading tasks: " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        AcademicTaskOrganizer window =
                new AcademicTaskOrganizer();
    }
}