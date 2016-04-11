package project_exercise;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class MyCounter extends JFrame implements ActionListener {
	private JLabel label, label1;
	private JButton button;
	private int count = 0;
	
	public MyCounter() {
		JPanel panel = new JPanel();
		label = new JLabel("Counter");
		panel.add(label);
		
		label1 = new JLabel(" " + count);
		label1.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 100));
		panel.add(label1);
		
		button = new JButton("카운터 증가");
		panel.add(button);
		button.addActionListener(this);
		
		add(panel);
		setSize(300, 200);
		setTitle("My Counter");
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		count++;
		label1.setText(count + " ");
	}
}

public class Event {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new MyCounter();

	}

}
