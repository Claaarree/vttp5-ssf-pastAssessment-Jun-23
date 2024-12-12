package sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.model;

import java.util.Random;

public class Captcha {
    private Double first;
    private Double second;
    private Double answer;
    private String operator;
    private String question;

    Random rand = new Random();
    
    public Captcha() {
        this.first = (double)rand.nextInt(50) + 1;
        this.second = (double)rand.nextInt(50) + 1;
        Integer randomNumber = rand.nextInt(4) + 1;
        switch (randomNumber) {
            case 1:
                this.answer = add();
                this.operator = "+";
                break;
    
            case 2:
                this.answer = subtract();
                this.operator = "-";
                break;
            case 3:
                this.answer = multiply();
                this.operator = "x";
                break;
            case 4:
                this.answer = divide();
                this.operator = "/";
                break;
            default:
                break;
        }
        this.question = first + operator + second;
    }

    public Double add() {
        return first + second;
    }

    public Double subtract() {
        return first - second;
    }
    
    public Double multiply() {
        return first * second;
    }
    
    public Double divide() {
        return first / second;
    }

    public Double getFirst() {
        return first;
    }

    public void setFirst(Double first) {
        this.first = first;
    }

    public Double getSecond() {
        return second;
    }

    public void setSecond(Double second) {
        this.second = second;
    }

    public Double getAnswer() {
        return answer;
    }

    public void setAnswer(Double answer) {
        this.answer = answer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    

    
}
