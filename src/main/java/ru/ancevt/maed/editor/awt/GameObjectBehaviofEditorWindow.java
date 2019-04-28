package ru.ancevt.maed.editor.awt;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameObjectBehaviofEditorWindow extends JFrame implements ActionListener {

//	public static void main(String[] args) {
//		try {
//			final String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
//			UIManager.setLookAndFeel(systemLookAndFeelClassName);
//		} catch (Exception e) {}
//		
//		SwingUtilities.invokeLater(()->{
//			final GameObjectEditWindow textWindow = new GameObjectEditWindow(null);
//			textWindow.setTitle("(floating)");
//			textWindow.setLocationByPlatform(true);
//			textWindow.setPreferredSize(new Dimension(300, 600));
//			textWindow.setSize(new Dimension(300, 600));
//			textWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			textWindow.setVisible(true);
//		});
//		
//	}

	private static final long serialVersionUID = -3571530962461822363L;
	private final JTextArea textArea;
	private final JButton buttonCancel;
	private final JButton buttonOK;

	public GameObjectBehaviofEditorWindow(String dataLineString) {
		setLocationByPlatform(true);
		setPreferredSize(new Dimension(500, 600));
		setSize(new Dimension(500, 600));
		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		scrollPane.setPreferredSize(new Dimension(200,530));
		
		buttonCancel = new JButton("Cancel");
		buttonOK = new JButton("OK");
		
		textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) ok(); else
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) dispose();
			}
		});
		
		// textArea.setPreferredSize(new Dimension(272, 220));
		final GridBagLayout gbl = new GridBagLayout();
		getContentPane().setLayout(gbl);
		
		final GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 10;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 8;
		c.insets = new Insets(10, 10, 5, 10);
		getContentPane().add(scrollPane, c);
		
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.ipadx = 0;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(buttonCancel, c);

		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(buttonOK, c);
		
		pack();
		
		buttonOK.addActionListener(this);
		buttonCancel.addActionListener(this);
		
		if(dataLineString != null) {
			textArea.setText(dataLineTextConvertIn(dataLineString));
		}
		
		textArea.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final JButton button = (JButton) e.getSource();
		
		if(button == buttonOK) {
			ok();
		} else {
			dispose();
		}
	}
	
	@Override
	public void dispose() {
		buttonOK.removeActionListener(this);
		buttonCancel.removeActionListener(this);
		super.dispose();
	}
	
	private final void ok() {
		onOKButtonPressed();
		dispose();
	}
	
	public void onOKButtonPressed() {
		
	}
	
	private final String dataLineTextConvertIn(String dataLineString) {
		final String[] splitted = dataLineString.split("\\|");
		final StringBuilder s = new StringBuilder();
		
		for(final String line : splitted) {
			if(line.contains("=")) {
				s.append(line.trim());
				s.append('\n');
			}
		}
		
		return s.toString();
	}
	
	protected final String getDataLineString() {
		final String dataLineString = textArea.getText();
		
		final String[] splitted = dataLineString.split("\n");
		final StringBuilder s = new StringBuilder();
		
		for(final String line : splitted) {
			s.append(line.trim());
			s.append('|');
		}
		
		return s.toString();
	}
}































