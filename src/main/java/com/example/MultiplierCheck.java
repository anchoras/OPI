package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    private TextField input1;
    private TextField input2;
    private TextField input3;
    private TextField output;
    private Button calculateButton;
    
    private int maxWindowWidth = 420;
    private int maxWindowHeight = 520;
    private int maxTextFieldWidth = Math.ceilDiv(maxWindowWidth,2);
    private int maxButtonWidth = Math.ceilDiv(maxWindowWidth,2);
    private String integerRegex = "^[+-]?\\d+$";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Проверка знания таблицы умножения");

        input1 = new TextField();
        input1.setPromptText("Введите первый множитель");
        input1.setMaxWidth(maxTextFieldWidth);

        input2 = new TextField();
        input2.setPromptText("Введите второй множитель");
        input2.setMaxWidth(maxTextFieldWidth);

        input3 = new TextField();
        input3.setPromptText("Введите свой вариант ответа");
        input3.setMaxWidth(maxTextFieldWidth);

        output = new TextField();
        output.setPromptText("Результат");
        output.setMaxWidth(maxTextFieldWidth);
        output.setEditable(false);

        calculateButton = new Button("Проверить");
        calculateButton.setDisable(true);
        calculateButton.setMaxWidth(maxButtonWidth);

        input1.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState());
        input2.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState());
        input3.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState());

        input1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(integerRegex)) {
                input1.setText(oldValue);
            }
        });
        input2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(integerRegex)) {
                input2.setText(oldValue);
            }
        });
        input3.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(integerRegex)) {
                input3.setText(oldValue);
            }
        });

        calculateButton.setOnAction(e -> calculate());

        Label label1 = new Label("Первый множитель:");
        Label label2 = new Label("Второй множитель:");
        Label label3 = new Label("Свой вариант ответа:");
        Label labelOutput = new Label("Проверка:");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
            label1, input1,
            label2, input2,
            label3, input3,
            calculateButton,
            labelOutput, output
        );

        Scene scene = new Scene(root, maxWindowWidth, maxWindowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateButtonState() {
        boolean hasEmptyFields = input1.getText().trim().isEmpty() 
            || input2.getText().trim().isEmpty() 
            || input3.getText().trim().isEmpty();
        calculateButton.setDisable(hasEmptyFields);
    }

    private void calculate() {
        try {
            long num1 = Long.parseLong(input1.getText().trim());
            long num2 = Long.parseLong(input2.getText().trim());
            long num3 = Long.parseLong(input3.getText().trim());

            if (num1 <= 0 || num2 <= 0 || num3 <= 0) {
                output.setText("Числа должны быть натуральными");
                return;
            }

            long product = num1 * num2;

            String result = (product == num3) 
                ? "Верно"
                : "Неверно (правильный ответ: " + product + ")";

            output.setText(result);
        } catch (NumberFormatException e) {
            output.setText("Ошибка: некорректные введённые данные");
        } catch (ArithmeticException e) {
            output.setText("Ошибка вычислений");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
