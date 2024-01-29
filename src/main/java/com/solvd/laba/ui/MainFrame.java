package com.solvd.laba.ui;

import com.solvd.laba.model.City;
import javax.swing.*;
import java.awt.HeadlessException;
import java.util.List;

import static com.solvd.laba.ui.config.WindowSizeValues.WINDOW_HEIGHT;
import static com.solvd.laba.ui.config.WindowSizeValues.WINDOW_WIDTH;



public class MainFrame extends JFrame{
    private final MainPanel mainPanel;

    public MainFrame(String title, List<City> cities) throws HeadlessException {
        super(title);

        mainPanel = new MainPanel();

        for (City city : cities) {
            mainPanel.addCity(city);
        }

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);
        this.add(mainPanel);
    }

    public void addCity(City city){
        mainPanel.addCity(city);
        mainPanel.repaint();
    }

    public void setCities(List<City> cities) {
        mainPanel.setCities(cities);
        mainPanel.repaint();
    }
}
