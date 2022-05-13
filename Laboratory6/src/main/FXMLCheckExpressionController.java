/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.LinkedStack;
import domain.StackException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author sofia
 */
public class FXMLCheckExpressionController implements Initializable {

    @FXML
    private Text txtTitle;
    @FXML
    private Text txtTitle2;
    @FXML
    private TextField txtFieldParenthesis;
    @FXML
    private Text txtMessage;
    @FXML
    private Button btnParenthesis;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private boolean checkParenthesis(String expression) throws StackException {
        expression = expression.replaceAll("\\s", " ");//limpio la cadena de String

        //Cerifico que no inicie con parentesis cerrado y que no termine con parentesis abierto
        if (expression.charAt(0) == ')' || expression.charAt(expression.length() - 1) == '(') {
            return false;
        }
        //Definir la pila
        LinkedStack linkedStack = new LinkedStack();

        for (int i = 0; i < expression.length(); i++) {
            //Cada vez que encuentra un paréntesis abierto, agréguelo a una pila 
            if (expression.charAt(i) == '(') {//Regla 1
                linkedStack.push(expression.charAt(i)); //aquí lo agrega 
            } else {
                //Cada vez que encuentre un paréntesis cerrado, desapile el elemento del tope de la pila 
                if (expression.charAt(i) == ')') {//Regla 2
                    if (linkedStack.isEmpty()) {
                        return false; ///retorna false si la pila es incorrecta
                    }
                    linkedStack.pop();
                }
            }

        }
        return linkedStack.isEmpty(); //queda vacia al terminar de recorrer toda la cadena
    }

    @FXML
    private void txtFieldParenthesis(ActionEvent event) {
    }

    @FXML
    private void btnParenthesis(ActionEvent event) throws StackException {
        if (checkParenthesis(txtFieldParenthesis.getText())) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Expresion: " + this.txtFieldParenthesis.getText() + " es correcta.");

            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Expresion: " + this.txtFieldParenthesis.getText() + " no es correcta.");

            alert.showAndWait();
        }
    }
}
