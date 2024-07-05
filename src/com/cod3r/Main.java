package com.cod3r;

import java.io.IOException;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {

	public static void main(String[] args) throws IOException {
		FlatLightLaf.setup();
		new Home();
//		Tools.getDevice();
	}
}
