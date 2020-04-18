package br.pro.hashi.ensino.desagil.aps.view;
import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {


    private final Gate gate;

    private final JCheckBox A_receiverBox;
    private final JCheckBox B_receiverBox;
    private final JCheckBox emitterBox;
    private final Switch signal_A;
    private final Switch signal_B;

    private final Image image;
    private Color color;

    public GateView(Gate gate) {

        super(245, 346);

        this.gate = gate;
        this.signal_A = new Switch();
        this.signal_B = new Switch();
        JLabel receiverLabel = new JLabel("Entrada");
        A_receiverBox = new JCheckBox();
        B_receiverBox = new JCheckBox();
        JLabel emmiterLabel = new JLabel("Saída");
        emitterBox = new JCheckBox();


        add(receiverLabel, 10,10,75,25);
        add(A_receiverBox,85,10,150,25);
        if (gate.getInputSize() > 1){
            add(B_receiverBox,10,45,75,25);
        }
        add(emmiterLabel,85,45,150,25);
        add(emitterBox,10,311,75,25);

        color = Color.BLACK;

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        A_receiverBox.addActionListener(this);
        B_receiverBox.addActionListener(this);
        emitterBox.setEnabled(false);


        addMouseListener(this);
        update();
    }

    private void update(){
        signal_A.turnOff();
        signal_B.turnOff();
        if (A_receiverBox.isSelected()) {
            signal_A.turnOn();
        }

        if (B_receiverBox.isSelected()) {
            signal_B.turnOn();
        }

        gate.connect(0, signal_A);

        if (gate.getInputSize() > 1) {
            gate.connect(1, signal_B);
        }

        if (gate.read()) {
            emitterBox.setSelected(true);
        }
        else {
            emitterBox.setSelected(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        int x = event.getX();
        int y = event.getY();

        if (x >= 210 && x < 235 && y >= 311 && y < 336) {

            color = JColorChooser.showDialog(this, null, color);

            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 10, 80, 221, 221, this);

        // Desenha um quadrado cheio.
        g.setColor(color);
        g.fillRect(210, 311, 25, 25);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}

