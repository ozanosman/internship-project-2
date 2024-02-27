package Windows;

import Utilities.DbConnection;
import Utilities.MyModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class MainWindow extends JFrame
{
    private Connection conn = null;
    private PreparedStatement state = null;
    private ResultSet result;

    private DbConnection DbConnection;

    private int carId = -1;
    private int serviceId = -1;
    private int appointmentId = -1;

    private boolean buttonState = false;

    private JPanel mainPanel;

    private JTabbedPane tabbedPane;

    private JPanel carsPanel;
    private JPanel servicesPanel;
    private JPanel appointmentsPanel;

    private JTable carsTable;
    private JTable servicesTable;
    private JTable appointmentsTable;

    private JTextField brandTextField;
    private JTextField modelTextField;
    private JTextField registrationNumberTextField;
    private JTextField yearManufacturedTextField;
    private JTextField ownerTextField;

    private JTextField searchBrandTextField;
    private JTextField searchModelTextField;
    private JTextField searchRegistrationNumberTextField;

    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField priceTextField;

    private JComboBox carComboBox;
    private JComboBox serviceComboBox;
    private JComboBox statusComboBox;

    private JButton addCarButton;
    private JButton editCarButton;
    private JButton deleteCarButton;
    private JButton searchCarButton;
    private JButton resetCarTableButton;

    private JButton addServiceButton;
    private JButton editServiceButton;
    private JButton deleteServiceButton;

    private JButton addAppointmentButton;
    private JButton editAppointmentButton;
    private JButton deleteAppointmentButton;
    private JButton sortTableByDateAndTimeButton;

    public MainWindow()
    {
        this.setContentPane(tabbedPane);

        refreshCarComboBox();
        refreshServiceComboBox();

        carsTable.addMouseListener(new CarsMouseAction());
        servicesTable.addMouseListener(new ServicesMouseAction());
        appointmentsTable.addMouseListener(new AppointmentsMouseAction());

        addCarButton.addActionListener(new AddCarAction());
        addServiceButton.addActionListener(new AddServiceAction());
        addAppointmentButton.addActionListener(new AddAppointmentAction());

        editCarButton.addActionListener(new EditCarAction());
        editServiceButton.addActionListener(new EditServiceAction());
        editAppointmentButton.addActionListener(new EditAppointmentAction());

        deleteCarButton.addActionListener(new DeleteCarAction());
        deleteServiceButton.addActionListener(new DeleteServiceAction());
        deleteAppointmentButton.addActionListener(new DeleteAppointmentAction());

        searchCarButton.addActionListener(new SearchCarAction());

        resetCarTableButton.addActionListener(new ResetCarTableAction());

        sortTableByDateAndTimeButton.addActionListener(new SortTableByDateAndTimeAction());

        refreshCarsTable();
        refreshServicesTable();
        refreshAppointmentsTable();

        this.setTitle("Car Service Management System");
        this.setSize(1000, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void refreshCarComboBox()
    {
        carComboBox.removeAllItems();

        String item = "";
        String sql = "select id, brand, model, registration_number, year_manufactured, owner from cars";

        conn = DbConnection.getConnection();

        try
        {
            state = conn.prepareStatement(sql);
            result = state.executeQuery();

            while (result.next())
            {
                item = result.getObject(1).toString() + ". " + result.getObject(2).toString() + " | " + result.getObject(3).toString() + " | " + result.getObject(4).toString() + " | " + result.getObject(5).toString() + " | " + result.getObject(6).toString();

                carComboBox.addItem(item);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void refreshServiceComboBox()
    {
        serviceComboBox.removeAllItems();

        String item = "";
        String sql = "select id, name, description, price from services";

        conn = DbConnection.getConnection();

        try
        {
            state = conn.prepareStatement(sql);
            result = state.executeQuery();

            while (result.next())
            {
                item = result.getObject(1).toString() + ". " + result.getObject(2).toString() + " | " + result.getObject(3).toString() + " | " + result.getObject(4).toString();

                serviceComboBox.addItem(item);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void clearCarsForm()
    {
        brandTextField.setText("");
        modelTextField.setText("");
        registrationNumberTextField.setText("");
        yearManufacturedTextField.setText("");
        ownerTextField.setText("");
    }

    public void clearSearchCarsForm()
    {
        searchBrandTextField.setText("");
        searchModelTextField.setText("");
        searchRegistrationNumberTextField.setText("");
    }

    public void clearServicesForm()
    {
        nameTextField.setText("");
        descriptionTextField.setText("");
        priceTextField.setText("");
    }

    class CarsMouseAction implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int row = carsTable.getSelectedRow();

            carId = Integer.parseInt(carsTable.getValueAt(row, 0).toString());

            if (e.getClickCount() > 1)
            {
                brandTextField.setText(carsTable.getValueAt(row, 1).toString());
                modelTextField.setText(carsTable.getValueAt(row, 2).toString());
                registrationNumberTextField.setText(carsTable.getValueAt(row, 3).toString());
                yearManufacturedTextField.setText(carsTable.getValueAt(row, 4).toString());
                ownerTextField.setText(carsTable.getValueAt(row, 5).toString());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {

        }
    }

    class ServicesMouseAction implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int row = servicesTable.getSelectedRow();

            serviceId = Integer.parseInt(servicesTable.getValueAt(row, 0).toString());

            if (e.getClickCount() > 1)
            {
                nameTextField.setText(servicesTable.getValueAt(row, 1).toString());
                descriptionTextField.setText(servicesTable.getValueAt(row, 2).toString());
                priceTextField.setText(servicesTable.getValueAt(row, 3).toString());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {

        }
    }

    class AppointmentsMouseAction implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int row = appointmentsTable.getSelectedRow();

            appointmentId = Integer.parseInt(appointmentsTable.getValueAt(row, 0).toString());

            if (e.getClickCount() > 1)
            {
                int carId = Integer.parseInt(appointmentsTable.getValueAt(row, 1).toString());
                int carItemCount = carComboBox.getItemCount();

                for (int i = 0; i < carItemCount; i++)
                {
                    String item = carComboBox.getItemAt(i).toString();

                    if (item.startsWith(carId + ". "))
                    {
                        carComboBox.setSelectedItem(item);
                        break;
                    }
                }

                int serviceId = Integer.parseInt(appointmentsTable.getValueAt(row, 2).toString());
                int serviceItemCount = serviceComboBox.getItemCount();

                for (int i = 0; i < serviceItemCount; i++)
                {
                    String item = serviceComboBox.getItemAt(i).toString();

                    if (item.startsWith(serviceId + ". "))
                    {
                        serviceComboBox.setSelectedItem(item);
                        break;
                    }
                }

                String status = appointmentsTable.getValueAt(row, 4).toString();
                int selectedIndex;

                switch (status)
                {
                    case "Scheduled": selectedIndex = 0;
                        break;
                    case "Completed": selectedIndex = 1;
                        break;
                    default: selectedIndex = 2;
                        break;
                }

                statusComboBox.setSelectedIndex(selectedIndex);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {

        }
    }

    class AddCarAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            if (brandTextField.getText().isEmpty() || modelTextField.getText().isEmpty() || registrationNumberTextField.getText().isEmpty() || yearManufacturedTextField.getText().isEmpty() || ownerTextField.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please fill all the text fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                conn = DbConnection.getConnection();

                String sql = "insert into cars (brand, model, registration_number, year_manufactured, owner) values (?, ?, ?, ?, ?)";

                try
                {
                    state = conn.prepareStatement(sql);

                    state.setString(1, brandTextField.getText());
                    state.setString(2, modelTextField.getText());
                    state.setString(3, registrationNumberTextField.getText());
                    state.setInt(4, Integer.parseInt(yearManufacturedTextField.getText()));
                    state.setString(5, ownerTextField.getText());

                    state.execute();

                    refreshCarsTable();
                    refreshCarComboBox();
                    clearCarsForm();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    class AddServiceAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            if (nameTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || priceTextField.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please fill all the text fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                conn = DbConnection.getConnection();

                String sql = "insert into services (name, description, price) values (?, ?, ?)";

                try
                {
                    state = conn.prepareStatement(sql);

                    state.setString(1, nameTextField.getText());
                    state.setString(2, descriptionTextField.getText());
                    state.setDouble(3, Double.parseDouble(priceTextField.getText()));

                    state.execute();

                    refreshServicesTable();
                    refreshServiceComboBox();
                    clearServicesForm();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public String idExtractorForCar()
    {
        String value = carComboBox.getSelectedItem().toString();
        String[] parts = value.split("\\.");

        return parts[0];
    }

    public String idExtractorForService()
    {
        String value = serviceComboBox.getSelectedItem().toString();
        String[] parts = value.split("\\.");

        return parts[0];
    }

    class AddAppointmentAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DbConnection.getConnection();

            String sql = "insert into appointments (car_id, service_id, date_and_time, status) values (?, ?, current_timestamp, ?)";

            try
            {
                state = conn.prepareStatement(sql);

                state.setInt(1, Integer.parseInt(idExtractorForCar()));
                state.setInt(2, Integer.parseInt(idExtractorForService()));
                state.setString(3, statusComboBox.getSelectedItem().toString());

                state.execute();

                refreshAppointmentsTable();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class EditCarAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DbConnection.getConnection();

            String sql = "update cars set brand=?, model=?, registration_number=?, year_manufactured=?, owner=? where id=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setString(1, brandTextField.getText());
                state.setString(2, modelTextField.getText());
                state.setString(3, registrationNumberTextField.getText());
                state.setInt(4, Integer.parseInt(yearManufacturedTextField.getText()));
                state.setString(5, ownerTextField.getText());
                state.setInt(6, carId);

                state.execute();

                refreshCarsTable();
                refreshCarComboBox();
                clearCarsForm();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class EditServiceAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DbConnection.getConnection();

            String sql = "update services set name=?, description=?, price=? where id=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setString(1, nameTextField.getText());
                state.setString(2, descriptionTextField.getText());
                state.setDouble(3, Double.parseDouble(priceTextField.getText()));
                state.setInt(4, serviceId);

                state.execute();

                refreshServicesTable();
                refreshServiceComboBox();
                clearServicesForm();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class EditAppointmentAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DbConnection.getConnection();

            String sql = "update appointments set car_id=?, service_id=?, date_and_time=current_timestamp, status=? where id=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setInt(1, Integer.parseInt(idExtractorForCar()));
                state.setInt(2, Integer.parseInt(idExtractorForService()));
                state.setString(3, statusComboBox.getSelectedItem().toString());
                state.setInt(4, appointmentId);

                state.execute();

                refreshAppointmentsTable();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class DeleteCarAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DbConnection.getConnection();

            String sql = "delete from cars where id=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setInt(1, carId);

                state.execute();

                refreshCarsTable();
                refreshCarComboBox();
                clearCarsForm();

                carId = -1;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class DeleteServiceAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DbConnection.getConnection();

            String sql = "delete from services where id=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setInt(1, serviceId);

                state.execute();

                refreshServicesTable();
                refreshServiceComboBox();
                clearServicesForm();

                serviceId = -1;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class DeleteAppointmentAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DbConnection.getConnection();

            String sql = "delete from appointments where id=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setInt(1, appointmentId);

                state.execute();

                refreshAppointmentsTable();

                appointmentId = -1;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class SearchCarAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DbConnection.getConnection();

            String sql = "select * from cars where brand=? or model=? or registration_number=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setString(1, searchBrandTextField.getText());
                state.setString(2, searchModelTextField.getText());
                state.setString(3, searchRegistrationNumberTextField.getText());

                state.execute();

                result = state.executeQuery();
                carsTable.setModel(new MyModel(result));

                clearSearchCarsForm();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    class ResetCarTableAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            try
            {
                refreshCarsTable();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    class SortTableByDateAndTimeAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            try
            {
                if (buttonState == false)
                {
                    conn = DbConnection.getConnection();

                    try
                    {
                        state = conn.prepareStatement("select id, car_id, service_id, to_char(date_and_time, 'yyyy-mm-dd hh24:mi:ss') as date_and_time, status from appointments order by date_and_time desc");

                        result = state.executeQuery();
                        appointmentsTable.setModel(new MyModel(result));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    buttonState = true;
                }
                else
                {
                    refreshAppointmentsTable();

                    buttonState = false;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void refreshCarsTable()
    {
        conn = DbConnection.getConnection();

        try
        {
            state = conn.prepareStatement("select * from cars");

            result = state.executeQuery();
            carsTable.setModel(new MyModel(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void refreshServicesTable()
    {
        conn = DbConnection.getConnection();

        try
        {
            state = conn.prepareStatement("select * from services");

            result = state.executeQuery();
            servicesTable.setModel(new MyModel(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void refreshAppointmentsTable()
    {
        conn = DbConnection.getConnection();

        try
        {
            state = conn.prepareStatement("select id, car_id, service_id, to_char(date_and_time, 'yyyy-mm-dd hh24:mi:ss') as date_and_time, status from appointments");

            result = state.executeQuery();
            appointmentsTable.setModel(new MyModel(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}