package klient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ServerNotActiveException;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class App extends JFrame implements ClientInterface {
  public static void main(String[] args) {
    try {
      new App();
    } catch (NotBoundException e) {
      e.printStackTrace();
      System.out.println("nyoom");
    }
  }

  private ServerInterface server;
  private JTextField inputTextField;
  private JTextArea messagesTextArea;
  private JLabel nicknameLabel;
  private String nickname;

  public App() throws NotBoundException {
    initComponents();
    try {
      //this.clientSocket = new Socket("localhost", 12345);
      
              Remote r = LocateRegistry.getRegistry("localhost", 12345).lookup("Chat");
              System.out.println(r instanceof ServerInterface);

              server = (ServerInterface) r;

      System.out.println("Připojeno k serveru.");

    } catch (IOException e) {
      JOptionPane.showMessageDialog(this, "Nepodařilo se připojit k serveru.", "Chyba", JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
      this.setVisible(false);
      System.exit(0);
    }

      nickname = JOptionPane.showInputDialog(this, "Zadej nickname");
      if (nickname == null) {
        setVisible(false);
        return;
      }
      this.nicknameLabel.setText((nickname) + ">"); // in.nextLine();
      this.setTitle("ChadChat " + nickname);
      try {
        server.connect(this, nickname);
      } catch (RemoteException e) {
        JOptionPane.showMessageDialog(this, "it bad");
        e.printStackTrace();
      } catch (ServerNotActiveException e) {
        JOptionPane.showMessageDialog(this, "it bad");
        e.printStackTrace();
      }
  }

  private void initComponents() {   //GUI things
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    JPanel inputPanel = new JPanel(new BorderLayout());
    nicknameLabel = new JLabel("you");
    inputTextField = new JTextField();
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(evt -> {
        String zprava = inputTextField.getText();
        try {
          server.sendMsg(zprava);
        } catch (RemoteException e) {
          JOptionPane.showMessageDialog(this, "it bad");
          e.printStackTrace();
        } catch (ServerNotActiveException e) {
          JOptionPane.showMessageDialog(this, "it bad");
          e.printStackTrace();
        }
        //pridatZpravu(nickname + "> " + zprava);   //the server will handle this in callbacks
        inputTextField.setText("");
    });
    inputPanel.add(nicknameLabel, BorderLayout.WEST);
    inputPanel.add(inputTextField, BorderLayout.CENTER);
    inputPanel.add(sendButton, BorderLayout.EAST);

    messagesTextArea = new JTextArea();  
    messagesTextArea.setEditable(false);
    JScrollPane scroll = new JScrollPane(messagesTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(inputPanel, BorderLayout.SOUTH);
    this.getContentPane().add(scroll, BorderLayout.CENTER);

    setVisible(true);
  }
  @Override
  public synchronized void sendMsg(String usrname, String msg) {
    messagesTextArea.append("\r\n" + usrname + "> " + msg);
  }
}
