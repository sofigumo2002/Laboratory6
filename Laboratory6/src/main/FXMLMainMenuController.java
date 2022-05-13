/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package main;

import domain.ArrayStack;
import domain.LinkedStack;
import domain.StackException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class FXMLMainMenuController implements Initializable {

    private Label label;
    @FXML
    private BorderPane bp;
    @FXML
    private VBox vBox;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnCheckExpression;
    @FXML
    private Button btnExit;
    @FXML
    private GridPane gridCheck;
    @FXML
    private Button btnCheck;
    @FXML
    private Button btnClean;
    @FXML
    private GridPane gridAritmetic;
    @FXML
    private Button btnConverter;
    @FXML
    private AnchorPane ap;
    @FXML
    private TextField textFieldParenthesis;
    @FXML
    private TextField textFieldConverter;
    @FXML
    private Button btnAritmeticConverter;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void home(MouseEvent event) {
        this.bp.setCenter(ap);

    }

    @FXML
    private void btnCheckExpression(ActionEvent event) {
        //Limpia
        this.gridAritmetic.setVisible(false);
        this.btnConverter.setVisible(false);

        //Hace
        this.gridCheck.setVisible(true);
        this.btnCheck.setVisible(true);
    }

    @FXML
    private void btnAritmeticConverter(ActionEvent event) {
        //Limpia
        this.gridCheck.setVisible(false);
        this.btnCheck.setVisible(false);

        //Hace
        this.gridAritmetic.setVisible(true);
        this.btnConverter.setVisible(true);
    }

    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);

    }

    @FXML
    private void btnCheck(ActionEvent event) throws StackException {

        if (checkParenthesis(textFieldParenthesis.getText())) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Expresion: " + this.textFieldParenthesis.getText() + " es correcta.");

            alert.showAndWait();

        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Expresion: " + this.textFieldParenthesis.getText() + " no es correcta.");

            alert.showAndWait();
        }
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
    private void btnConverter(ActionEvent event) throws StackException {

        if (!textFieldConverter.equals("")) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Expresion Postfija: " + infixNotation(textFieldConverter.getText()));
            alert.showAndWait();
        } else {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Debe indicar una expresion PostFija!!");
            alert.showAndWait();

        }

    }
    
    public static String infixNotation(String text) throws StackException {
        //text = text.replaceAll("\s", "");//Quita espacios

        ArrayStack temp = new ArrayStack(text.length());
        String auxi = "";

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != '/' && text.charAt(i) != '*' && text.charAt(i) != '-' && text.charAt(i) != '+') {
                temp.push(text.charAt(i));
            } else {
                //Operando operador operando
                auxi += ')';
                auxi += temp.pop();
                auxi += text.charAt(i);
                auxi += temp.pop();
                auxi += '(';
                temp.push(auxi);
                auxi = "";
            }
        }

        //Se hace para darle vuelta, porque como es una pila esta al reves
        String sortText = "";
        String unsortedText = "";
        unsortedText = (String) temp.top();//Castea a String
        for (int i = unsortedText.length() - 1; i >= 0; i--) {
            sortText += unsortedText.charAt(i);
        }

        //Se evalua para ver si la expresion tiene letras o numeros, si es numerico muestra el resultado de la operacion
        if (detectNumbers(sortText)) {
            return text + " = " + sortText + " = " + mathResolver(sortText);//Numeros
        } else {
            return text + " = " + sortText;//Letras
        }

    }

    public static boolean detectNumbers(String text) {//Detecta si el string tiene un numero
        Pattern pattern = Pattern.compile("[0-9]");//Se le denota el rango del patron, en este caso se le coloca que vaya de 0 a 9
        Matcher matcher = pattern.matcher(text);//Matcher es un motor para realizar operacion utilizando una secuencia de caracteres, implementa un pattern
        return matcher.find();//En este caso matcher usa el metodo find que va de la mano con el pattern para hacer una busqueda y ver si text contiene los caracteres del pattern
    }

    public static int mathResolver(String text) throws StackException {
        LinkedStack nums = new LinkedStack();
        LinkedStack simbols = new LinkedStack();
        String result = "";
        double num1 = 0;
        double num2 = 0;
        char simbol = 0;
        double finalResult = 0;
        for (int i = 0; i < text.length(); i++) {
            while (text.charAt(i) != '-' && text.charAt(i) != '*' && text.charAt(i) != '+' && text.charAt(i) != ')' && text.charAt(i) != '/') {
                nums.push(text.charAt(i));
                if (i < text.length() - 1) {
                    i++;
                } else {
                    break;
                }
            }
            if (text.charAt(i) == '+' || text.charAt(i) == '-' || text.charAt(i) == '/' || text.charAt(i) == '*') {
                simbols.push(text.charAt(i));
            } else {
                simbol = (Character) simbols.pop();
                num1 = Double.valueOf(nums.pop().toString());
                num2 = Double.valueOf(nums.pop().toString());
                result = "";
                switch (simbol) {
                    case '-':
                        finalResult = num2 - num1;
                        num1 = 0;
                        num2 = 0;
                        simbol = 0;
                        break;
                    case '*':
                        finalResult = num1 * num2;
                        num1 = 0;
                        num2 = 0;
                        simbol = 0;
                        break;
                    case '+':
                        finalResult = num1 + num2;
                        num1 = 0;
                        num2 = 0;
                        simbol = 0;
                        break;
                    case '/':
                        finalResult = num2 / num1;
                        num1 = 0;
                        num2 = 0;
                        simbol = 0;
                        break;
                }
                if (!nums.isEmpty()) {
                    nums.pop();
                }
                if (!nums.isEmpty()) {
                    nums.push(finalResult);
                    finalResult = 0;
                } else {
                    i = text.length();
                }
            }
        }
        return (int) finalResult;
    }

    @FXML
    private void btnClean(ActionEvent event) {
        
    }

    @FXML
    private void btnHome(ActionEvent event) {
    }


    @FXML
    private void btnExit(ActionEvent event) {
    }

    @FXML
    private void textFieldParenthesis(ActionEvent event) {
    }

    @FXML
    private void textFieldConverter(ActionEvent event) {
    }

    @FXML
    private void btnCheckExpression(MouseEvent event) {
    }

    @FXML
    private void btnAritmeticConverter(MouseEvent event) {
    }

    

    
}
