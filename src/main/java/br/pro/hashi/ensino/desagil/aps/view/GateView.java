package br.pro.hashi.ensino.desagil.aps.view;
import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GateView extends JPanel implements ActionListener {

    private final Gate gate;

    private final JCheckBox A_receiverBox;
    private final JCheckBox B_receiverBox;
    private final JCheckBox emitterBox;
    private final Switch signal_A;
    private final Switch signal_B;


    public GateView(Gate gate) {
        this.gate = gate;
        this.signal_A = new Switch();
        this.signal_B = new Switch();
        JLabel receiverLabel = new JLabel("Entrada");
        A_receiverBox = new JCheckBox();
        B_receiverBox = new JCheckBox();
        JLabel emmiterLabel = new JLabel("Saída");
        emitterBox = new JCheckBox();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(receiverLabel);
        add(A_receiverBox);
        if (gate.getInputSize() > 1){
            add(B_receiverBox);
        }
        add(emmiterLabel);
        add(emitterBox);

        A_receiverBox.addActionListener(this);
        B_receiverBox.addActionListener(this);
        emitterBox.setEnabled(false);

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
}
