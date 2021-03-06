/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL.Validations;

import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import jsonconverter.BE.Config;

/**
 *
 * @author Pepe15224
 */
public class Validations {

    /* checks if config with this name aleready exists */
    public boolean checkIfConfigExists(Config config, List<Config> configList) {
        for (Config configInList : configList) {
         
            if (configInList.getConfigName().equals(config.getConfigName())) {
                return false;            
            }
            else if(configInList.getConfigName().endsWith(" ")&& configInList.getConfigName().length()==20 )
            {
                String checkToBeSure=config.getConfigName();
                for(int i = config.getConfigName().length(); i<20;i++)
                {
                    checkToBeSure=checkToBeSure+" ";
                }
                if (configInList.getConfigName().equals(checkToBeSure)) {
                return false;            
            }
            }
        }
        return true;
    }

    /* makes background of field red when it is filled incorrectly */
    public void changeColorIfWrong(Node node, String fieldText, List<String> headersList) {
        if (((JFXTextField) node).getId().equals("siteNameField")
                || ((JFXTextField) node).getId().equals("assetSerialNumberField")
                || ((JFXTextField) node).getId().equals("createdOnField")
                || ((JFXTextField) node).getId().equals("createdByField")
                || ((JFXTextField) node).getId().equals("statusField")
                || ((JFXTextField) node).getId().equals("estimatedTimeField")
                || ((JFXTextField) node).getId().contains("Empty")) {
            if (!headersList.contains(fieldText) && !fieldText.equals("")) {
                ((JFXTextField) node).setStyle("-fx-background-color : red");
            } else {
                ((JFXTextField) node).setStyle("-fx-background-color :");
            }
        } else if(((JFXTextField) node).getId().equals("headerNameField"))
        {}    
        else {
            if (fieldText.isEmpty() || !headersList.contains(fieldText)) {
                ((JFXTextField) node).setStyle("-fx-background-color : red");
            } else {
                ((JFXTextField) node).setStyle("-fx-background-color :");
            }
        }
    }

    /* checks if fields have red background */
    public boolean wrongInputValidation(AnchorPane pane) {
        for (Node node : pane.getChildren()) {
            if (node instanceof JFXTextField) {

                if (((JFXTextField) node).getStyle().equals("-fx-background-color : red")) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /* checks if config matches file */
    public boolean checkIfFileMatchesConfig(Config config,List<String> fileHeaders){
        int checkForErrors = 0;
        int i = 0;
        i = 0;
        checkForErrors = 0;
        while (i < 15) {
            String configString = config.getAllGetters(i);

            if (configString.contains("&&") && !configString.equals("")) {
                String[] splitedConfig = configString.split("&&");
                if (!fileHeaders.contains(splitedConfig[0])) {
                    checkForErrors++;
                }
                if (!fileHeaders.contains(splitedConfig[1])) {
                    checkForErrors++;
                }
            } else if (!fileHeaders.contains(configString) && !configString.equals("")) {
                checkForErrors++;
            }
            i++;
        }
        if (checkForErrors == 0) {
            return true;
        }
        return false;
    }
    
    /* removes configs that dont match chosen file */
    public List<Config> checkIfYouCanUseConfig(List<String> fileHeaders,List<Config> configs) {
        List<Config> superList = new ArrayList();
        int checkForErrors = 0;
        int i = 0;
        for (Config config : configs) {
            i = 0;
            checkForErrors = 0;
            while (i < 15) {
                String configString = config.getAllGetters(i);

                if (configString.contains("&&") && !configString.equals("")) {
                    String[] splitedConfig = configString.split("&&");
                    if (!fileHeaders.contains(splitedConfig[0])) {
                        checkForErrors++;
                    }
                    if (!fileHeaders.contains(splitedConfig[1])) {
                        checkForErrors++;
                    }
                } else if (!fileHeaders.contains(configString) && !configString.equals("")) {
                    checkForErrors++;
                }
                i++;
            }
            if (checkForErrors == 0) {
                superList.add(config);
            }
        }
        return superList;
    }
}
