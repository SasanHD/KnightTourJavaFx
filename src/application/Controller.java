package application;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {
	
	@FXML
	Label label00,label10,label20,label30,label40,label50,label60,label70;
	@FXML
	Label label01,label11,label21,label31,label41,label51,label61,label71;
	@FXML
	Label label02,label12,label22,label32,label42,label52,label62,label72;
	@FXML
	Label label03,label13,label23,label33,label43,label53,label63,label73;
	@FXML
	Label label04,label14,label24,label34,label44,label54,label64,label74;
	@FXML
	Label label05,label15,label25,label35,label45,label55,label65,label75;
	@FXML
	Label label06,label16,label26,label36,label46,label56,label66,label76;
	@FXML
	Label label07,label17,label27,label37,label47,label57,label67,label77;
	@FXML
	Label tourLabel;
	@FXML
	Button setButton,startButton;
	
	private Parent root;
	private Stage stage;
	private Scene scene;

	//Initialize Chess board.
	int[][] chessBoard = new int[8][8];
	
	//Initialize Heuristic board.
	int[][] heuristicBoard = new int[][]{
		  { 2, 3, 4, 4, 4, 4, 3, 2},
		  { 3, 4, 6, 6, 6, 6, 4, 3},
		  { 4, 6, 8, 8, 8, 8, 6, 4},
		  { 4, 6, 8, 8, 8, 8, 6, 4},
		  { 4, 6, 8, 8, 8, 8, 6, 4},
		  { 4, 6, 8, 8, 8, 8, 6, 4},
		  { 3, 4, 6, 6, 6, 6, 4, 3},
		  { 2, 3, 4, 4, 4, 4, 3, 2}
		};
			
	//Initialize possible Vertical and Horizontal moves.
	int[] horizontal = {2,1,-1,-2,-2,-1,1,2};
	int[] vertical = {-1,-2,-2,-1,1,2,2,1};
			
	//Knight's position is 1
	int knightPosition=1;
	
	//Current row and column set to 0
	int currentRow=0;
	int currentColumn=0;
	
	//Initialize the minimum heuristic number to a highest value.
	int minPos=10;
		
	//Initialize the row and column, for the smallest heuristic move.
	int minCurrentRow=0;
	int minCurrentColumn=0;
	
	//Initialize variable to keep track of no possible moves left.
	int noMovesLeftCounter=0;
	
	//Initialize counter variable.
	static int tries=1;
	
	
	public void startTour(ActionEvent e) {
		
		//Set Knight's starting position on the Chess board.
		chessBoard[currentRow][currentColumn]= knightPosition;
				
		//Update the Heuristic board's visited location to 10.
		heuristicBoard[currentRow][currentColumn]=minPos;
	
		//Update the next move's Row and Column.
		updateHeuristicBoard();
		
		new Thread(()->{ 
			
			startButton.setDisable(true);
			tries=1;
					
					do {

						//Assign the new moving coordinates.
						currentRow=minCurrentRow;
						currentColumn=minCurrentColumn;
						
						//Update the Knight's position on the Heuristic board.
						heuristicBoard[currentRow][currentColumn]=10; 
						
						//Move Knight to the new location, and update Chess board.
						++knightPosition;
						chessBoard[currentRow][currentColumn]= knightPosition;
						
					    //Platform.runLater(() -> System.out.print(""));
				        try {Thread.sleep(750);} catch (InterruptedException ex) { ex.printStackTrace();}
				        Platform.runLater(() -> updateBoard());
						
						//Update the next move's row and column.
						updateHeuristicBoard();
						
						//End the loop if there are no more valid moves left.
						if(noMovesLeftCounter==8) {
							break;
						}
						
						//Increment the iteration variable.
						++tries;
						
					}while(tries!=64);
					
					Platform.runLater(() -> startButton.setDisable(true));
					Platform.runLater(() -> setButton.setDisable(false));
					
					Platform.runLater(() -> tourLabel.setText("The tour ended with "+(tries+1)+" moves!"));
					
				
	    }).start();
		
	
	}


	public void setBoard(ActionEvent e) {
		
		//Initialize Chess board values to 0.
		for (int[] row: chessBoard)
			Arrays.fill(row, 0);
		
		//Initialize Heuristic board values to default.
		heuristicBoard = new int[][]{
			  { 2, 3, 4, 4, 4, 4, 3, 2},
			  { 3, 4, 6, 6, 6, 6, 4, 3},
			  { 4, 6, 8, 8, 8, 8, 6, 4},
			  { 4, 6, 8, 8, 8, 8, 6, 4},
			  { 4, 6, 8, 8, 8, 8, 6, 4},
			  { 4, 6, 8, 8, 8, 8, 6, 4},
			  { 3, 4, 6, 6, 6, 6, 4, 3},
			  { 2, 3, 4, 4, 4, 4, 3, 2}
			};
		
		//Knight's position is set to 1.
		knightPosition=1;
				
		//Give the Knight a random starting position.
		Random starting = new Random();
		int startingPos= starting.nextInt(8);
		currentRow=startingPos;
		currentColumn=startingPos;
		chessBoard[currentRow][currentColumn]= knightPosition;
		
		//Initialize the minimum Heuristic number to a highest value.
		minPos=10;
			
		//Initialize the Row and Column, for the smallest Heuristic move.
		minCurrentRow=0;
		minCurrentColumn=0;
		
		//Initialize the variable to keep track of no possible moves left.
		noMovesLeftCounter=0;
		
		//Update the Chess board, and change buttons.
		updateBoard();
		tourLabel.setText("");
		setButton.setDisable(true);
		startButton.setDisable(false);
	}
	
	public void aboutTour (ActionEvent event) throws IOException {
		
		root = FXMLLoader.load(getClass().getResource("About.fxml"));
		stage = new Stage();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("about.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("About");
		stage.setResizable(false);
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("chess.png")));
		stage.show();
	}
	
	public void closeTour (ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
	
	public void updateHeuristicBoard() {
		
		//Initialize the ending variable.
		noMovesLeftCounter=0; 
		
		//Initialize the minimum heuristic position to 10. 
		minPos=10;
		
		//Check all possible 8 moves to get new moving coordinates.
		for(int move=0;move<8;move++) {
			currentRow += vertical [move] ;
			currentColumn += horizontal [move] ;
			
			//If the move is invalid, return current Row and Column to the original position.
			if(currentRow<0 || currentColumn<0 || currentRow>7 || currentColumn>7 || chessBoard[currentRow][currentColumn] != 0 || heuristicBoard[currentRow][currentColumn] == 10)  {
				currentRow -= vertical [move] ;
				currentColumn -= horizontal [move] ;
				++noMovesLeftCounter; 
				
			//If the move is valid, get the new minimum position and coordinates.
			}
			else {
				if(heuristicBoard[currentRow][currentColumn]<=minPos) {
					minPos=heuristicBoard[currentRow][currentColumn];
					minCurrentRow=currentRow;
					minCurrentColumn=currentColumn;			
				}
				
				//Since the move is valid, reduce accessibility from our starting position to update the Heuristic board.
				heuristicBoard[currentRow][currentColumn]-=1;
				
				//Return the current Row and Column to the starting point, for checking a next possible move.
				currentRow -= vertical [move] ;
				currentColumn -= horizontal [move] ;
			}
		}
	}
	
	public void updateBoard() {
			
		//Update all Label's of the Chess Board row-by-row.
		
		//Row 0.
		label00.setText(String.valueOf(chessBoard[0][0]));
		label10.setText(String.valueOf(chessBoard[0][1]));
		label20.setText(String.valueOf(chessBoard[0][2]));
		label30.setText(String.valueOf(chessBoard[0][3]));
		label40.setText(String.valueOf(chessBoard[0][4]));
		label50.setText(String.valueOf(chessBoard[0][5]));
		label60.setText(String.valueOf(chessBoard[0][6]));
		label70.setText(String.valueOf(chessBoard[0][7]));
			
		//Row 1.
		label01.setText(String.valueOf(chessBoard[1][0]));
		label11.setText(String.valueOf(chessBoard[1][1]));
		label21.setText(String.valueOf(chessBoard[1][2]));		
		label31.setText(String.valueOf(chessBoard[1][3]));
		label41.setText(String.valueOf(chessBoard[1][4]));
		label51.setText(String.valueOf(chessBoard[1][5]));
		label61.setText(String.valueOf(chessBoard[1][6]));
		label71.setText(String.valueOf(chessBoard[1][7]));
			
		//Row 2.
		label02.setText(String.valueOf(chessBoard[2][0]));
		label12.setText(String.valueOf(chessBoard[2][1]));
		label22.setText(String.valueOf(chessBoard[2][2]));	
		label32.setText(String.valueOf(chessBoard[2][3]));
		label42.setText(String.valueOf(chessBoard[2][4]));
		label52.setText(String.valueOf(chessBoard[2][5]));
		label62.setText(String.valueOf(chessBoard[2][6]));
		label72.setText(String.valueOf(chessBoard[2][7]));
			
		//Row 3.
		label03.setText(String.valueOf(chessBoard[3][0]));
		label13.setText(String.valueOf(chessBoard[3][1]));
		label23.setText(String.valueOf(chessBoard[3][2]));	
		label33.setText(String.valueOf(chessBoard[3][3]));
		label43.setText(String.valueOf(chessBoard[3][4]));
		label53.setText(String.valueOf(chessBoard[3][5]));
		label63.setText(String.valueOf(chessBoard[3][6]));
		label73.setText(String.valueOf(chessBoard[3][7]));
			
		//Row 4.
		label04.setText(String.valueOf(chessBoard[4][0]));
		label14.setText(String.valueOf(chessBoard[4][1]));
		label24.setText(String.valueOf(chessBoard[4][2]));	
		label34.setText(String.valueOf(chessBoard[4][3]));
		label44.setText(String.valueOf(chessBoard[4][4]));
		label54.setText(String.valueOf(chessBoard[4][5]));
		label64.setText(String.valueOf(chessBoard[4][6]));
		label74.setText(String.valueOf(chessBoard[4][7]));
			
		//Row 5.
		label05.setText(String.valueOf(chessBoard[5][0]));
		label15.setText(String.valueOf(chessBoard[5][1]));
		label25.setText(String.valueOf(chessBoard[5][2]));	
		label35.setText(String.valueOf(chessBoard[5][3]));
		label45.setText(String.valueOf(chessBoard[5][4]));
		label55.setText(String.valueOf(chessBoard[5][5]));
		label65.setText(String.valueOf(chessBoard[5][6]));
		label75.setText(String.valueOf(chessBoard[5][7]));
			
		//Row 6.
		label06.setText(String.valueOf(chessBoard[6][0]));
		label16.setText(String.valueOf(chessBoard[6][1]));
		label26.setText(String.valueOf(chessBoard[6][2]));	
		label36.setText(String.valueOf(chessBoard[6][3]));
		label46.setText(String.valueOf(chessBoard[6][4]));
		label56.setText(String.valueOf(chessBoard[6][5]));
		label66.setText(String.valueOf(chessBoard[6][6]));
		label76.setText(String.valueOf(chessBoard[6][7]));
			
		//Row 7.
		label07.setText(String.valueOf(chessBoard[7][0]));
		label17.setText(String.valueOf(chessBoard[7][1]));
		label27.setText(String.valueOf(chessBoard[7][2]));	
		label37.setText(String.valueOf(chessBoard[7][3]));
		label47.setText(String.valueOf(chessBoard[7][4]));
		label57.setText(String.valueOf(chessBoard[7][5]));
		label67.setText(String.valueOf(chessBoard[7][6]));
		label77.setText(String.valueOf(chessBoard[7][7]));
			
		//Mark beginning and ending position of the Knight with ♞
		if(currentRow==0 && currentColumn==0) {
				label00.setText("♞");
			}else if(currentRow==0 && currentColumn==1) {
				label10.setText("♞");
			}else if(currentRow==0 && currentColumn==2) {
				label20.setText("♞");
			}else if(currentRow==0 && currentColumn==3) {
				label30.setText("♞");
			}else if(currentRow==0 && currentColumn==4) {
				label40.setText("♞");
			}else if(currentRow==0 && currentColumn==5) {
				label50.setText("♞");
			}else if(currentRow==0 && currentColumn==6) {
				label60.setText("♞");
			}else if(currentRow==0 && currentColumn==7) {
				label70.setText("♞");
			}else if(currentRow==1 && currentColumn==0) {
				label01.setText("♞");
			}else if(currentRow==1 && currentColumn==1) {
				label11.setText("♞");
			}else if(currentRow==1 && currentColumn==2) {
				label21.setText("♞");
			}else if(currentRow==1 && currentColumn==3) {
				label31.setText("♞");
			}else if(currentRow==1 && currentColumn==4) {
				label41.setText("♞");
			}else if(currentRow==1 && currentColumn==5) {
				label51.setText("♞");
			}else if(currentRow==1 && currentColumn==6) {
				label61.setText("♞");
			}else if(currentRow==1 && currentColumn==7) {
				label71.setText("♞");
			}else if(currentRow==2 && currentColumn==0) {
				label02.setText("♞");
			}else if(currentRow==2 && currentColumn==1) {
				label12.setText("♞");
			}else if(currentRow==2 && currentColumn==2) {
				label22.setText("♞");
			}else if(currentRow==2 && currentColumn==3) {
				label32.setText("♞");
			}else if(currentRow==2 && currentColumn==4) {
				label42.setText("♞");
			}else if(currentRow==2 && currentColumn==5) {
				label52.setText("♞");
			}else if(currentRow==2 && currentColumn==6) {
				label62.setText("♞");
			}else if(currentRow==2 && currentColumn==7) {
				label72.setText("♞"); 
			}else if(currentRow==3 && currentColumn==0) {
				label03.setText("♞");
			}else if(currentRow==3 && currentColumn==1) {
				label13.setText("♞");
			}else if(currentRow==3 && currentColumn==2) {
				label23.setText("♞");
			}else if(currentRow==3 && currentColumn==3) {
				label33.setText("♞");
			}else if(currentRow==3 && currentColumn==4) {
				label43.setText("♞");
			}else if(currentRow==3 && currentColumn==5) {
				label53.setText("♞");
			}else if(currentRow==3 && currentColumn==6) {
				label63.setText("♞");
			}else if(currentRow==3 && currentColumn==7) {
				label73.setText("♞"); 
			}else if(currentRow==4 && currentColumn==0) {
				label04.setText("♞");
			}else if(currentRow==4 && currentColumn==1) {
				label14.setText("♞");
			}else if(currentRow==4 && currentColumn==2) {
				label24.setText("♞");
			}else if(currentRow==4 && currentColumn==3) {
				label34.setText("♞");
			}else if(currentRow==4 && currentColumn==4) {
				label44.setText("♞");
			}else if(currentRow==4 && currentColumn==5) {
				label54.setText("♞");
			}else if(currentRow==4 && currentColumn==6) {
				label64.setText("♞");
			}else if(currentRow==4 && currentColumn==7) {
				label74.setText("♞"); 
			}else if(currentRow==5 && currentColumn==0) {
				label05.setText("♞");
			}else if(currentRow==5 && currentColumn==1) {
				label15.setText("♞");
			}else if(currentRow==5 && currentColumn==2) {
				label25.setText("♞");
			}else if(currentRow==5 && currentColumn==3) {
				label35.setText("♞");
			}else if(currentRow==5 && currentColumn==4) {
				label45.setText("♞");
			}else if(currentRow==5 && currentColumn==5) {
				label55.setText("♞");
			}else if(currentRow==5 && currentColumn==6) {
				label65.setText("♞");
			}else if(currentRow==5 && currentColumn==7) {
				label75.setText("♞"); 
			}else if(currentRow==6 && currentColumn==0) {
				label06.setText("♞");
			}else if(currentRow==6 && currentColumn==1) {
				label16.setText("♞");
			}else if(currentRow==6 && currentColumn==2) {
				label26.setText("♞");
			}else if(currentRow==6 && currentColumn==3) {
				label36.setText("♞");
			}else if(currentRow==6 && currentColumn==4) {
				label46.setText("♞");
			}else if(currentRow==6 && currentColumn==5) {
				label56.setText("♞");
			}else if(currentRow==6 && currentColumn==6) {
				label66.setText("♞");
			}else if(currentRow==6 && currentColumn==7) {
				label76.setText("♞"); //
			}else if(currentRow==7 && currentColumn==0) {
				label07.setText("♞");
			}else if(currentRow==7 && currentColumn==1) {
				label17.setText("♞");
			}else if(currentRow==7 && currentColumn==2) {
				label27.setText("♞");
			}else if(currentRow==7 && currentColumn==3) {
				label37.setText("♞");
			}else if(currentRow==7 && currentColumn==4) {
				label47.setText("♞");
			}else if(currentRow==7 && currentColumn==5) {
				label57.setText("♞");
			}else if(currentRow==7 && currentColumn==6) {
				label67.setText("♞");
			}else if(currentRow==7 && currentColumn==7) {
				label77.setText("♞");
			}
		}
}
