package com.solvd.laba.ui;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Road;
import com.solvd.laba.service.navigator.impl.NavigatorService;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.solvd.laba.ui.config.WindowSizeValues.WINDOW_HEIGHT;
import static com.solvd.laba.ui.config.WindowSizeValues.WINDOW_WIDTH;

public class MainPanel extends JPanel {
    private final NavigatorService navigatorService = new NavigatorService();
    private final List<City> cities = new ArrayList<>();
    private List<City> shortestPath = new ArrayList<>();

    public MainPanel() {
        this.setBackground(Color.gray);

        JLabel fromLabel = new JLabel("From:");
        this.add(fromLabel);

        JTextField fromTextField = new JTextField();
        fromTextField.setPreferredSize(new Dimension(48, 24));
        this.add(fromTextField);

        JLabel throughLabel = new JLabel("Through:");
        this.add(throughLabel);

        JTextField throughTextField = new JTextField();
        throughTextField.setPreferredSize(new Dimension(48, 24));
        this.add(throughTextField);

        JLabel toLabel = new JLabel("To:");
        this.add(toLabel);

        JTextField toTextField = new JTextField();
        toTextField.setPreferredSize(new Dimension(48, 24));
        this.add(toTextField);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> {

            City startCity = null;
            City endCity = null;
            try{
                startCity = cities.stream()
                        .filter(city -> city.getName().equals(fromTextField.getText()))
                        .findFirst().get();
            } catch (NoSuchElementException ex){
                JOptionPane.showMessageDialog(this, "There is no such city! " + fromTextField.getText());
            }

            try {
                endCity = cities.stream()
                        .filter(city -> city.getName().equals(toTextField.getText()))
                        .findFirst().get();
            } catch (NoSuchElementException ex){
                JOptionPane.showMessageDialog(this, "There is no such city! " + toTextField.getText());
            }




            if(!throughTextField.getText().isEmpty()) {

                City intermediateCity = null;
                try {
                    intermediateCity = cities.stream()
                            .filter(city -> city.getName().equals(throughTextField.getText()))
                            .findFirst().get();
                } catch (NoSuchElementException ex) {
                    JOptionPane.showMessageDialog(this, "There is no such city! " + throughTextField.getText());
                }

                shortestPath.clear();  // Clear the previous shortestPath
                shortestPath = navigatorService.findShortestPathWithStop(startCity, intermediateCity, endCity);

                if (navigatorService.getRoadLength(shortestPath) == 0) {
                    JOptionPane.showMessageDialog(this, "Cannot find this route! ");
                } else {
                    repaint();

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Shortest path length: " + navigatorService.getRoadLength(shortestPath));
                    });
                }


            } else {
                // TODO:
                try {
                    shortestPath = navigatorService.findShortestPath(startCity, endCity);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                if(navigatorService.getRoadLength(shortestPath) == 0){
                    JOptionPane.showMessageDialog(this, "Cannot find this route! ");
                } else {
                    repaint();

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Shortest path length: " + navigatorService.getRoadLength(shortestPath));
                    });
                }
                repaint();
                }
            });
        this.add(applyButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g.setFont(new Font("Calibri", Font.BOLD, 16));

        int max_x = cities.stream().mapToInt(city -> (int) Math.round(city.getxPos())).max().getAsInt();
        int ratio_x = WINDOW_WIDTH / max_x;

        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        int taskBarHeight = scrnSize.height - winSize.height;
        int max_y = cities.stream().mapToInt(city -> (int) Math.round(city.getyPos())).max().getAsInt();
        int ratio_y = (WINDOW_HEIGHT - taskBarHeight) / max_y;

        for (City city : cities) {
            int x = (int) Math.round(city.getxPos());
            int start_x = (x * ratio_x) - 25;

            int y = (int) Math.round(-city.getyPos() + max_y);
            int start_y = (y * ratio_y) + 50;

            for (Road road : city.getRoads()) {
                City endCity = cities.stream().filter(c -> c.getId().equals(road.getEndCityId())).findFirst().get();

                int x1 = (int) Math.round(endCity.getxPos());
                int end_x = (x1 * ratio_x) - 25;

                int y1 = (int) Math.round(-endCity.getyPos() + max_y);
                int end_y = (y1 * ratio_y) + 50;

                g.drawString(String.valueOf(road.getDistance()), (start_x + end_x) / 2, ((start_y + end_y) / 2) - 5);
                g.drawLine(start_x + 8, start_y + 8, end_x + 8, end_y + 8);
            }

            g.drawString(city.getName(), start_x + 5, start_y - 5);
            Ellipse2D.Double circle = new Ellipse2D.Double(start_x, start_y, 15, 15);

            if (shortestPath.stream().anyMatch(c -> c.getId().equals(city.getId()))) {
                g2d.setColor(new Color(255, 0, 0));
            }

            g2d.fill(circle);
            g2d.setColor(new Color(0, 0, 0));
        }

        g.setColor(new Color(255, 0, 0));

        for (int i = 0; i < shortestPath.size() - 1; i++) {
            City startCity = shortestPath.get(i);
            City endCity = shortestPath.get(i + 1);

            int x = (int) Math.round(startCity.getxPos());
            int start_x = (x * ratio_x) - 25;

            int y = (int) Math.round(-startCity.getyPos() + max_y);
            int start_y = (y * ratio_y) + 50;

            int x1 = (int) Math.round(endCity.getxPos());
            int end_x = (x1 * ratio_x) - 25;

            int y1 = (int) Math.round(-endCity.getyPos() + max_y);
            int end_y = (y1 * ratio_y) + 50;

            g.drawLine(start_x + 8, start_y + 8, end_x + 8, end_y + 8);
        }

        g.setColor(new Color(0, 0, 0));

        g.setColor(new Color(236, 236, 236));
        g.fillRect(0, 0, 300, 100);

    }

    public void addCity(City city) {
        cities.add(city);
    }

}