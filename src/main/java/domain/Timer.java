package domain;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Timer {
    protected int period;
    protected Road rd;
    protected Thread th;
}
