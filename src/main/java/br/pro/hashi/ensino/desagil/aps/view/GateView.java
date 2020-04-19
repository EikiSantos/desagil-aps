package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
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
    private final Switch signal_A;
    private final Switch signal_B;
    private final Image image;
    private final Light light;
    private Color color;

    public GateView(Gate gate) {

        super(230,140);

        this.gate = gate;
        this.signal_A = new Switch();
        this.signal_B = new Switch();
        A_receiverBox = new JCheckBox();
        B_receiverBox = new JCheckBox();

        add(A_receiverBox, 3, 62, 15, 15);
        if (gate.getInputSize() > 1) {
            add(B_receiverBox, 3, 90, 15, 15);
            add(A_receiverBox, 3, 30, 15, 15);
        }

        this.light = new Light(255, 0, 0);

        String name = gate.toString() + ".jpg";
        System.out.println(name);
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        A_receiverBox.addActionListener(this);
        B_receiverBox.addActionListener(this);


        addMouseListener(this);
        update();
    }

    private void update() {
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
        light.connect(0, gate);


        color = light.getColor();
        repaint();


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        if (light.getColor() != Color.BLACK) {
                if ((x - 225) * (x - 225) + (y - 80) * (y - 80) < 25*25) {
                color = JColorChooser.showDialog(this, null, color);
                light.setColor(color);
                repaint();

            }
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
        g.drawImage(image, 5, 5, 221, 126, this);
        // Desenha um quadrado cheio.
        g.setColor(color);

        g.fillOval(205, 55, 25, 25);
        //g.fillRect(190, 180, 25, 25);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}

