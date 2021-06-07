package com.pattern.interpreter;

// 解释器模式
public class InterpreterPatternDemo {

    // 规则：Robert 和 John 是男性
    public static Expression getMalExpression() {
        Expression robert = new TerminalExpression("Robert");
        Expression john = new TerminalExpression("John");
        return new OrExpression(robert, john);
    }

    // 规则：Julie是一个已婚女性
    public static Expression getMarriedWomanExpression() {
        Expression julie = new TerminalExpression("Julie");
        Expression married = new TerminalExpression("Mattied");
        return new AndExpression(julie, married);
    }

    public static void main(String[] args) {
        Expression isMale = getMalExpression();
        Expression isMarriedWoman = getMarriedWomanExpression();
        System.out.println("John is male? " + isMale.interpret("John"));
        System.out.println("Julie is a married women? " + isMarriedWoman.interpret("Married Julie"));
    }


}
