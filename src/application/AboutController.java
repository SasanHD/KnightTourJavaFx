package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AboutController implements Initializable {
	
	@FXML
	Label tourLabel,descLabelOne,descLabelTwo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		descLabelOne.setText("The Knight’s Tour, which was a problem originally proposed by the mathematician Euler,"
				+ " investigates whether a Knight piece can move around an empty chess-board, and touch each of the 64 squares once and only once."
				+ " Here, each move is represented by a number on the chessboard. Thus, a full tour is representative of 64 total moves. ");
		
		descLabelTwo.setText("With the aid of a heuristic guide, our Knight moves only to coordinates that are least accessible first. "
				+ "This ensures areas that are hard to reach will be visited first. "
				+ "You can reset the board and start the tour from a new location after every completed tour. Have fun testing the tour with different starting positions! ");
		
	}

	
}
