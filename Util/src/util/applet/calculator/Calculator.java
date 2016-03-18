/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) �����Ķ��Ļ��������޹�˾  All Rights Reserved.
 */
package util.applet.calculator;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * @author <a href="mailto:zhuhao.567@163.com.">���</a> 2014��12��4������8:18:04
 * @version 1.0
 * 
 * ʹ��Java AppletС���򣬴���һ��ͼ���û����棬
1. �ܽ�������������Ϊ���ӵļ� +���� -���� *���� /���� ȡģ %  �����㣬
2. ��ʾ��������
3. �ܲ�׽�����쳣������ JOptionPane ��� �Ի�������ʾ�쳣
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
�����һ������   
����ڶ�������
������
ѡ������� �� �� �� �� ģ
�����ַ����쳣ʱ����ʾ���Ϊ�������������ַ�����������������������
 */
public class Calculator extends JFrame implements ActionListener {
	private boolean dotExist, operated, equaled; // ��������Ĳ�������
	private double storedNumber; // Ŀǰ�Ľ��
	private char lastOperator; // ��ʾ��һ�����
	private JTextField operation; // �����
	private JButton dot, plus, minus, multi, div, sqrt, equal, changePN, clear; // �����
	private JButton[] numbers; // ����

	// ������
	public Calculator() {
		setTitle("Calculator");
		// ��ʼ������
		dotExist = false; // ��ʾ��ǰ�����Ƿ���С����
		operated = false; // ��ʾ����������Ƿ񱻰���
		equaled = false; // ��ʾ�Ⱥ��Ƿ񱻰���
		storedNumber = 0;
		lastOperator = '?';
		// ��ʼ�����ڱ���
		operation = new JTextField("0");
		operation.setEditable(false);
		numbers = new JButton[10];
		for (int i = 0; i < 10; i++)
			numbers[i] = new JButton("" + i);
		dot = new JButton(".");
		plus = new JButton("+");
		minus = new JButton("-");
		multi = new JButton("*");
		div = new JButton("/");
		sqrt = new JButton("��");
		equal = new JButton("=");
		changePN = new JButton("��");
		clear = new JButton("AC");
		// ������������봰��
		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);
		addComponent(layout, operation, 0, 0, 4, 1);
		addComponent(layout, numbers[1], 1, 0, 1, 1);
		addComponent(layout, numbers[2], 1, 1, 1, 1);
		addComponent(layout, numbers[3], 1, 2, 1, 1);
		addComponent(layout, numbers[4], 2, 0, 1, 1);
		addComponent(layout, numbers[5], 2, 1, 1, 1);
		addComponent(layout, numbers[6], 2, 2, 1, 1);
		addComponent(layout, numbers[7], 3, 0, 1, 1);
		addComponent(layout, numbers[8], 3, 1, 1, 1);
		addComponent(layout, numbers[9], 3, 2, 1, 1);
		addComponent(layout, dot, 4, 0, 1, 1);
		addComponent(layout, numbers[0], 4, 1, 1, 1);
		addComponent(layout, sqrt, 4, 2, 1, 1);
		addComponent(layout, plus, 1, 3, 1, 1);
		addComponent(layout, minus, 2, 3, 1, 1);
		addComponent(layout, multi, 3, 3, 1, 1);
		addComponent(layout, div, 4, 3, 1, 1);
		addComponent(layout, equal, 5, 0, 2, 1);
		addComponent(layout, changePN, 5, 2, 1, 1);
		addComponent(layout, clear, 5, 3, 1, 1);
	}

	// �԰�ť���з�Ӧ�ķ���
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn == clear) {
			operation.setText("0");
			dotExist = false;
			storedNumber = 0;
			lastOperator = '?';
		} else if (btn == equal) {
			operate('=');
			equaled = true;
		} else if (btn == plus) {
			operate('+');
			equaled = false;
		} else if (btn == minus) {
			operate('-');
			equaled = false;
		} else if (btn == multi) {
			operate('*');
			equaled = false;
		} else if (btn == div) {
			operate('/');
			equaled = false;
		} else if (btn == changePN) {
			operate('p');
			operate('=');
			equaled = true;
		} else if (btn == sqrt) {
			operate('s');
			operate('=');
			equaled = true;
		} else {
			if (equaled)
				storedNumber = 0;
			for (int i = 0; i < 10; i++)
				if (btn == numbers[i]) {
					if (operation.getText().equals("0"))
						operation.setText("" + i);
					else if (!operated)
						operation.setText(operation.getText() + i);
					else {
						operation.setText("" + i);
						operated = false;
					}
				}
			if (btn == dot && !dotExist) {
				operation.setText(operation.getText() + ".");
				dotExist = true;
			}
		}
	}

	// ��������ķ���
	private void operate(char operator) {
		double currentNumber = Double.valueOf(operation.getText())
				.doubleValue();
		if (lastOperator == '?')
			storedNumber = currentNumber;
		else if (lastOperator == '+')
			storedNumber += currentNumber;
		else if (lastOperator == '-')
			storedNumber -= currentNumber;
		else if (lastOperator == '*')
			storedNumber *= currentNumber;
		else if (lastOperator == '/')
			storedNumber /= currentNumber;
		else if (lastOperator == 'p')
			storedNumber *= -1;
		else if (lastOperator == 's')
			storedNumber = Math.sqrt(currentNumber);
		else if (lastOperator == '=' && equaled)
			storedNumber = currentNumber;
		operation.setText("" + storedNumber);
		operated = true;
		lastOperator = operator;
	}

	// ���ʹ��GridBagLayout�ķ���
	private void addComponent(GridBagLayout layout, Component component,
			int row, int col, int width, int height) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 2, 10, 2);
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridx = col;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		layout.setConstraints(component, constraints);
		if (component instanceof JButton)
			((JButton) component).addActionListener(this);
		getContentPane().add(component);
	}

	// ��������ʼ������ʾ����
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		calc.setSize(290, 400);
		calc.setVisible(true);
	}
}
