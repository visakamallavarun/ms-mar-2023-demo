package com.example.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.Stack;

class EvaluateString
{
    public static Integer evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++)
        {
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuilder buff = new StringBuilder();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    buff.append(tokens[i++]);
                values.push(Integer.parseInt(buff.toString()));
                i--;
            }
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/')
            {
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.push(tokens[i]);
            }
        }

        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        return values.pop();
    }

    public static boolean hasPrecedence(char op1, char op2)
    {
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    public static int applyOp(char op, int b, int a)
    {
        switch (op) {
            case '+' -> { return a + b; }
            case '-' -> { return a - b; }
            case '*' -> { return a * b; }
            case '/' -> { if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero"); return a / b; }
        }
        return 0;
    }

}
public class HelloController {

    @FXML
    private Label result;

    @FXML
    public void onEqualToClicked() {
        result.setText(EvaluateString.evaluate(result.getText()).toString());
    }

    @FXML
    public void onClicked(MouseEvent mouseEvent) {
        String a=((Pane)mouseEvent.getSource()).getId().replace("btn","");
        switch (a) {
            case "Plus" -> result.setText(result.getText() + "+");
            case "Minus" -> result.setText(result.getText() + "-");
            case "Multiply" -> result.setText(result.getText() + "*");
            case "Divide" -> result.setText(result.getText() + "/");
            default -> result.setText(result.getText() + a);
        }
    }

    @FXML
    public void onCClicked() {
        result.setText("");
    }
}